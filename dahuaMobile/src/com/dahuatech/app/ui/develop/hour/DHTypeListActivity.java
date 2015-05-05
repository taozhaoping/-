package com.dahuatech.app.ui.develop.hour;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHTypeAdapter;
import com.dahuatech.app.bean.develophour.DHTypeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName DHTypeListActivity
 * @Description ���������б�
 * @author 21291
 * @date 2014��10��29�� ����10:46:25
 */
public class DHTypeListActivity extends MenuActivity {

	private ListView mListView; 					//�б�ؼ�����
	
	private ProgressDialog dialog;      			//������
	private DevelopHourBusiness dBusiness;			//ҵ���߼���
	private DHTypeAdapter dAdapter;					//��������	
	private DHTypeInfo dhTypeInfo;					//���ʵ��
	private List<DHTypeInfo> dArrayList;			//����Դ
	
	private String fProjectCode;					//��Ŀ���
	private String serviceUrl;						//�����ַ
	private AppContext appContext; 					//ȫ��Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.dh_type_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
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
			fProjectCode=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE);
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHTYPELISTACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		initView();
		new getListAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��4�� ����10:12:41
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.dh_type_list);
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHTypeListActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness",serviceUrl);
		dArrayList=new ArrayList<DHTypeInfo>();
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description �첽��ȡ����������
	 * @author 21291
	 * @date 2014��11��4�� ����10:30:15
	 */
	private class getListAsync extends AsyncTask<Void,Void,List<DHTypeInfo>>{
		@Override
		protected void onPreExecute() { // ��ʾ����ִ��֮ǰ�Ĳ���
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHTypeInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHTypeInfo> result) {
			super.onPostExecute(result);
			renderList(result);
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡ�������ͷ�����ʵ�弯��
	* @param @return     
	* @return List<DHTypeInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��4�� ����11:02:27
	*/
	private List<DHTypeInfo> getListByPost(){
		return dBusiness.getDHType(fProjectCode);
	}
	
	private void renderList(final List<DHTypeInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(DHTypeListActivity.this, getString(R.string.dh_type_list_netparseerror),3000);
			return;
		}
		dArrayList.addAll(listData);
		dAdapter=new DHTypeAdapter(DHTypeListActivity.this,dArrayList,R.layout.dh_type_list_item);
		mListView.setAdapter(dAdapter);
		//�������¼�
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==-1) { //���������Ч
					return;	
				}
				
				dhTypeInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					dhTypeInfo=(DHTypeInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_type_list_item);
					dhTypeInfo=(DHTypeInfo)reLayout.getTag();
				}
				if(dhTypeInfo==null){
					return;	
				}
				Intent intent = new Intent();
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPEID, dhTypeInfo.getFTypeId());
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPENAME, dhTypeInfo.getFTypeName());	
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
