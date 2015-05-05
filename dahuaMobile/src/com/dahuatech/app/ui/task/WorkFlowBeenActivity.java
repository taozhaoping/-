package com.dahuatech.app.ui.task;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.WorkFlowAdapter;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.WorkFlowBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class WorkFlowBeenActivity extends MenuActivity {

	private ListView mListView; 	// �б�ؼ�����
	private ProgressDialog dialog;		//���̿�
	
	private String FSystemType,fClassTypeID,fBillID,fItemNumber;  //ϵͳ���ͣ�����ID��������������,Ա����
	private String serviceUrl; // �����ַ
	private HashMap<String, String> oldHashMap,newHashMap; // ������Ϣ
	private WorkFlowBusiness workFlowBusiness; //ҵ���߼���
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workflow_been);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 	
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(WorkFlowBeenActivity.this);
		workFlowBusiness=(WorkFlowBusiness)factoryBusiness.getInstance("WorkFlowBusiness","");  
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			FSystemType=extras.getString(AppContext.WORKFLOW_FSYSTEMTYPE_KEY);
			fClassTypeID=extras.getString(AppContext.WORKFLOW_FCLASSTYPEID_KEY);
			fBillID=extras.getString(AppContext.WORKFLOW_FBILLID_KEY);		
		}	
		iniHasMap();   //��ʼ�������ַ
		//��ȡԱ����
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, "");
		
		if(!StringUtils.isEmpty(FSystemType) && !StringUtils.isEmpty(fClassTypeID) && !StringUtils.isEmpty(fBillID)&& !StringUtils.isEmpty(fItemNumber)){
			// ����һ���ȴ�������ʾ�û��ȴ�
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);
			
			new WorkFlowBeenAsyncClient().execute(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(WorkFlowBeenActivity.this, R.string.workflow_paramerror);
			return;
		}
	}
	
	/** 
	* @Title: iniHasMap 
	* @Description: ��ʼ�������ļ���Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��21�� ����2:23:00
	*/
	private void iniHasMap(){
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_WORKFLOWACTIVITY;
		//������Ȧ����������������ϵͳ �������������ڵ��ݷ����ַ
		if("19".equals(FSystemType)){
			oldHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "OldWorkFlowActivity");
		}
		
		//�°湤����ƽ̨���ݷ����ַ
		if("18".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "NewWorkFlowActivity");
		}
		
		//HR�湤����ƽ̨���ݷ����ַ
		if("38".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "HrWorkFlowActivity");
		}
		
		//�����湤����ƽ̨���ݷ����ַ
		if("8".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "EpWorkFlowActivity");
		}
	}
	
	/**
	 * @ClassName WorkFlowBeenAsyncClient
	 * @Description �첽����������¼����
	 * @author 21291
	 * @date 2014��5��12�� ����10:13:59
	 */
	private class WorkFlowBeenAsyncClient extends AsyncTask<String, Integer, List<WorkFlowInfo>> {	
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<WorkFlowInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // ���ٵȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<WorkFlowInfo> doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ͨ��POST��ʽ��ȡ����Դ
	* @param @param serviceUrl �����ַ
	* @param @return     
	* @return List<WorkFlowInfo>    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����10:14:14
	*/
	private List<WorkFlowInfo> getListByPost(String serviceUrl) {
		// ����ʵ��-�ִ���
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// ����ʵ��-������
		SqlParametersInfo sqlParameters= SqlParametersInfo.getSqlParametersInfo();
		WorkFlowParam(repository,sqlParameters);
		workFlowBusiness.setServiceUrl(serviceUrl);
		return workFlowBusiness.getWorkFlowInfo(repository, sqlParameters);
	}

	/** 
	* @Title: WorkFlowParam 
	* @Description: ������������¼��������
	* @param @param repository �ִ�ʵ��
	* @param @param sqlParameters sql������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����10:14:28
	*/
	private void WorkFlowParam(RepositoryInfo repository,SqlParametersInfo sqlParameters) {
		//Ĭ���Ǿɰ湤����������Ϣ
		HashMap<String, String> hashMap=oldHashMap;
		if("18".equals(FSystemType) || "38".equals(FSystemType) || "8".equals(FSystemType))
		{
			hashMap=newHashMap;
		}		
		// ����ֵ	
		repository.setClassType(hashMap.get("ClassType"));
		repository.setIsTest(hashMap.get("IsTest"));
		repository.setServiceName(hashMap.get("ServiceName"));
		repository.setServiceType(hashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(hashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(hashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
	
		sqlParameters.setParameterCount(3);
		sqlParameters.setParameter1(FSystemType);
		sqlParameters.setParameter2(fClassTypeID);
		sqlParameters.setParameter3(fBillID);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ����������
	* @param @param arrayList �б���     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����10:14:59
	*/
	private void renderListView(List<WorkFlowInfo> arrayList) {
		if(arrayList.size()==0){ //�����ȡ��������
			UIHelper.ToastMessage(WorkFlowBeenActivity.this, R.string.workflow_netparseerror);		
			return;
		}
		// ʵ����ListView
		mListView = (ListView) findViewById(R.id.workflow_been_listView);
		// ʹ�����ݼ�����adapter����
		WorkFlowAdapter adapter = new WorkFlowAdapter(this, arrayList,R.layout.workflowlayout);
		// ����ListView��adapter
		mListView.setAdapter(adapter);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;				
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
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
