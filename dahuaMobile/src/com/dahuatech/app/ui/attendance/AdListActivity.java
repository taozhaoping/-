package com.dahuatech.app.ui.attendance;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.AttendanceListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdListInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName AttendanceListActivity
 * @Description ���ڼ�¼��ʷ�б�
 * @author 21291
 * @date 2014��12��17�� ����4:04:24
 */
public class AdListActivity extends MenuActivity {
	private ListView mListView;						//�б�ؼ�
	private List<AdListInfo> mArrayList;			//����Դ
	private AttendanceBusiness aBusiness;			//ҵ���߼���
	private AttendanceListAdapter adAdapter;		//��������
	private ProgressDialog dialog;      			//������

	private String fItemNumber;  					//Ա����
	private String serviceUrl;  					//�����ַ
	private AppContext appContext; 					//ȫ��Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.attendance_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_ATTENDANCELISTACTIVITY;	//��ȡ�����ַ
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initView();														//��ʼ����ͼ�ؼ�
		new getListAsync().execute();
		sendLogs();
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:44:27
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.attendance_list_listView);
		mArrayList=new ArrayList<AdListInfo>();
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(AdListActivity.this);
		aBusiness= (AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness",serviceUrl);
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��12��18�� ����3:01:17
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<AdListInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<AdListInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<AdListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡʵ�弯����Ϣ
	* @param @return     
	* @return List<AdListInfo>    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����3:02:10
	*/
	private  List<AdListInfo> getListByPost(){
		return aBusiness.getAdList(fItemNumber);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ������ͼ����
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����3:23:58
	*/
	private void renderListView(final List<AdListInfo> listData){
		if(listData.size()==0){ //û������
			UIHelper.ToastMessage(AdListActivity.this, getString(R.string.attendance_list_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		adAdapter=new AttendanceListAdapter(AdListActivity.this,mArrayList,R.layout.attendance_list_item);//��������������
		mListView.setAdapter(adAdapter);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ����ҵĿ���ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����3:34:49
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_attendance));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(AdListActivity.this,logInfo);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		commonMenu.setContext(AdListActivity.this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(adAdapter!=null && mArrayList.size() > 0){
			adAdapter.refreshView(mArrayList);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
