package com.dahuatech.app.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NoticeBussiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName NoticeService
 * @Description ��̨������ѯ֪ͨ
 * @author 21291
 * @date 2014��5��29�� ����3:19:47
 */
public class NoticeService extends IntentService {

	private String fItemNumber;	  //Ա����
	private String serviceUrl;  //�����ַ
	
	public NoticeService() {
		super("NoticeService");	
	}
	
	@Override
	public void onCreate() {
		super.onCreate();	
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_NOTICESERVICE;	
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(isNetworkConnected(NoticeService.this)){ //ֻ���������������½�����Ϣ֪ͨ��֪ͨ
			isStartService(); 
		}
	}
	
	/** 
	* @Title: isNetworkConnected 
	* @Description: ��������Ƿ����
	* @param @param context
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����9:12:21
	*/
	private boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){
				// ��ǰ���������ӵ�
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// ��ǰ�����ӵ��������
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	* @Title: isStartService 
	* @Description: �����ж��Ƿ�����֪ͨ��֪ͨ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��30�� ����3:41:36
	*/
	private void isStartService(){
		SharedPreferences setting= getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE);					
		//�ж��Ƿ���֪ͨ��
		if(setting.getBoolean(AppContext.IS_NOTICE_KEY,true)){
			SharedPreferences logining=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			fItemNumber=logining.getString(AppContext.USER_NAME_KEY, "");
			if(!StringUtils.isEmpty(fItemNumber)){
				new onNoticeAsync().execute(serviceUrl);
			}	
		}
	}

	/**
	 * @ClassName onNoticeAsync
	 * @Description �첽��ȡ������������
	 * @author 21291
	 * @date 2014��7��4�� ����3:00:21
	 */
	private class onNoticeAsync extends AsyncTask<String , Void, Integer > {
		
	   @Override
	   protected Integer doInBackground(String... params) {
		   FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(NoticeService.this);
		   NoticeBussiness noticeBussiness=(NoticeBussiness)factoryBusiness.getInstance("NoticeBussiness",params[0]);   
		   return Integer.valueOf(noticeBussiness.getTaskCount(fItemNumber));       
	   }

	   @Override
	   protected void onPostExecute(Integer result) {
		   if(result > 0){  //����֪ͨ��֪ͨ��Ϣ
				UIHelper.sendBroadCast(NoticeService.this,result);
		   }
	   }
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
