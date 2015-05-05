package com.dahuatech.app.ui.task;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.WorkFlowAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.RejectNodeBusiness;
import com.dahuatech.app.business.WorkFlowBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.RejectSpinnerDialog;

/**
 * @ClassName WorkFlowActivity
 * @Description ����������Activity��
 * @author 21291
 * @date 2014��4��25�� ����1:34:59
 */
public class WorkFlowActivity extends MenuActivity {

	private ListView mListView; 	// �б�ؼ�����
	private ProgressDialog dialog,dialogPass;  //���̿�
	private Button approvePass,approveReject;  //�����Ͳ��صİ�ť
	private EditText fEditTextComment; //�����ı��� 
	
	private String fSystemType,fClassTypeID,fBillID,fItemNumber;  //ϵͳ���ͣ�����ID��������������,Ա����
	private String fComment;   //�������,������������
	private String workFlowKey; //����keyֵ
	private String paramStr;  // �����ַ���
	private int fSystemNum;
	private String fBillName=""; //��������
	private HashMap<String, String> oldHashMap,newHashMap,appHashMap,rejectNodeHashMap; // ������Ϣ
	private WorkFlowBusiness workFlowBusiness; //ҵ���߼���
	private String serviceUrl,oldWorkAppUrl,newWorkAppUrl,rejectNodeUrl; // �����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workflow);	
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);	

		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fSystemType=extras.getString(AppContext.WORKFLOW_FSYSTEMTYPE_KEY);
			fClassTypeID=extras.getString(AppContext.WORKFLOW_FCLASSTYPEID_KEY);
			fBillID=extras.getString(AppContext.WORKFLOW_FBILLID_KEY);	
			fBillName=extras.getString(AppContext.WORKFLOW_FBILLNAME_KEY);
		}	
		iniHasMap();  //��ʼ�������ַ
		
		//��ȡԱ����
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, "");
		fComment="ͬ��-�����ƶ���";
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(WorkFlowActivity.this);
		workFlowBusiness=(WorkFlowBusiness)factoryBusiness.getInstance("WorkFlowBusiness",""); 
		
		if(!StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fClassTypeID) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fItemNumber)){	
			fSystemNum=Integer.valueOf(fSystemType);
			switch (fSystemNum) {
				case 19: //������Ȧ����������key
					workFlowKey=AppContext.WORKFLOW_ENGINEERING_KEY;
					break;
	
				case 8:  //�����湤����ƽ̨����key
					workFlowKey=AppContext.WORKFLOW_EXPENSEPRIVATE_KEY;
					break;
					
				case 18: //�°湤����ƽ̨����key
					workFlowKey=AppContext.WORKFLOW_NEWOFFICE_KEY;
					break;
					
				case 38: //���ڵ�������key(HR��)
					workFlowKey=AppContext.WORKFLOW_ATTENDANCE_HR_KEY;
					break;
			}	
			// ����һ���ȴ�������ʾ�û��ȴ�
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);		
			new WorkFlowAsyncClient().execute(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(WorkFlowActivity.this, R.string.workflow_paramerror);
			return;
		}
		
		dialogPass = new ProgressDialog(this);
		dialogPass.setMessage(getResources().getString(R.string.dialog_approveing));
		dialogPass.setCancelable(false);		
		fEditTextComment=(EditText) findViewById(R.id.workflow_FComment);	
		//������ť����
		approvePass=(Button) findViewById(R.id.workflow_imgbtnPass);
		approvePass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(fEditTextComment.getText().toString()))
				{
					fComment=fEditTextComment.getText().toString()+"-"+"�����ƶ���";	
				}	
				switch (fSystemNum) {
					case 19://������Ȧ����������
						paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+"pass"+","+fComment;
						new WorkFlowHandleAsync().execute(oldWorkAppUrl,paramStr,"WorkFlowAPP");			
						break;
	
					case 18://�°湤����ƽ̨��������
						paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment;
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"NewWorkFlowAPP");			
						break;
						
					case 38://HR�湤����ƽ̨��������
						paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment;
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"HrWorkFlowAPP");			
						break;
				
					case 8://�����湤����ƽ̨��������
						paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment;
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"EpWorkFlowAPP");			
						break;
						
					default:
						UIHelper.ToastMessage(WorkFlowActivity.this,R.string.workflow_pass_false);
						break;
				}
			}
		});	
		
		//���ذ�ť����
		approveReject=(Button) findViewById(R.id.workflow_imgbtnReject);
		approveReject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fComment=fEditTextComment.getText().toString();
				if(StringUtils.isEmpty(fComment))
				{
					UIHelper.ToastMessage(WorkFlowActivity.this,R.string.workflow_reject_notEmpty);
				}
				else {
					fComment=fComment+"-�����ƶ���";
					switch (fSystemNum) {
						case 19: //������Ȧ�����ݲ���
							paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment;
							new WorkFlowHandleAsync().execute(oldWorkAppUrl,paramStr,"WorkFlowReject");	
							break;
						
						case 18://�°湤����ƽ̨���ݲ���
							paramStr=workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber;
							new rejectNodeAsyncClient().execute(rejectNodeUrl,paramStr);
							break;
							
						case 38://HR�湤����ƽ̨���ݲ���
							paramStr=workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber;
							new rejectNodeAsyncClient().execute(rejectNodeUrl,paramStr);
							break;
							
						case 8://�����湤����ƽ̨���ݲ���
							paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber;
							new rejectNodeAsyncClient().execute(rejectNodeUrl,paramStr);
							break;
							
						default:
							UIHelper.ToastMessage(WorkFlowActivity.this,R.string.workflow_reject_false);
							break;
					}
				}
			}
		});	
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
		//������Ȧ�����ݷ����ַ
		if("19".equals(fSystemType)){
			oldHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "OldWorkFlowActivity");
			//��ȡ�ɰ湤�������������ַ
			oldWorkAppUrl=AppUrl.URL_API_HOST_ANDROID_OLDWORKFLOWAPPSERVICEURL;	
		}
		
		//�°湤����ƽ̨���ݷ����ַ
		if("18".equals(fSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "NewWorkFlowActivity");
			//��ȡ�°湤�������������ַ
			newWorkAppUrl=AppUrl.URL_API_HOST_ANDROID_NEWWORKFLOWAPPSERVICEURL;	
			//��ȡ�°沵�ؽڵ�������Ϣ
			rejectNodeHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "RejectNodeRepository");
			rejectNodeUrl=AppUrl.URL_API_HOST_ANDROID_REJECTNODEREPOSITORY;	 //��ȡ�°沵�ؽڵ�����ַ
		}
		
		//HR�湤����ƽ̨���ݷ����ַ
		if("38".equals(fSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "HrWorkFlowActivity");
			//��ȡHR�湤�������������ַ
			newWorkAppUrl=AppUrl.URL_API_HOST_ANDROID_HRWORKFLOWAPPSERVICEURL;	
			//��ȡHR�沵�ؽڵ�������Ϣ
			rejectNodeHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "HrRejectNodeRepository");
			rejectNodeUrl=AppUrl.URL_API_HOST_ANDROID_HRREJECTNODEREPOSITORY;	 //��ȡHR�沵�ؽڵ�����ַ
		}
		
		//�����湤����ƽ̨���ݷ����ַ
		if("8".equals(fSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "EpWorkFlowActivity");
			//��ȡ�����湤�������������ַ
			newWorkAppUrl=AppUrl.URL_API_HOST_ANDROID_EPWORKFLOWAPPSERVICEURL;	
			//��ȡ�����沵�ؽڵ�������Ϣ
			rejectNodeHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "EpRejectNodeRepository");
			rejectNodeUrl=AppUrl.URL_API_HOST_ANDROID_EPREJECTNODEREPOSITORY;	 //��ȡ�����沵�ؽڵ�����ַ
		}
	}
	
	/**
	 * @ClassName WorkFlowAsyncClient
	 * @Description �첽����������¼����
	 * @author 21291
	 * @date 2014��4��28�� ����9:41:21
	 */
	private class WorkFlowAsyncClient extends AsyncTask<String, Integer, List<WorkFlowInfo>> {
		
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
	* @date 2014��4��28�� ����9:42:39
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
	* @param @param sqlParameters  sql������    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��28�� ����9:55:48
	*/
	private void WorkFlowParam(RepositoryInfo repository,SqlParametersInfo sqlParameters) {
		//Ĭ���Ǿɰ湤����������Ϣ
		HashMap<String, String> hashMap=oldHashMap;
		if("18".equals(fSystemType) || "38".equals(fSystemType) || "8".equals(fSystemType))  //������°��HR�������
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
		sqlParameters.setParameter1(fSystemType);
		sqlParameters.setParameter2(fClassTypeID);
		sqlParameters.setParameter3(fBillID);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ����������
	* @param @param arrayList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��28�� ����10:02:17
	*/
	private void renderListView(List<WorkFlowInfo> arrayList) {
		if(arrayList.size()==0){ //�����ȡ��������
			UIHelper.ToastMessage(WorkFlowActivity.this, R.string.workflow_netparseerror);		
			return;
		}
		// ʵ����ListView
		mListView = (ListView) findViewById(R.id.workflow_listView);
		// ʹ�����ݼ�����adapter����
		WorkFlowAdapter adapter = new WorkFlowAdapter(this, arrayList,R.layout.workflowlayout);
		// ����ListView��adapter
		mListView.setAdapter(adapter);
	}
	
	/**
	 * @ClassName WorkFlowHandleAsync
	 * @Description �������ݲ���
	 * @author 21291
	 * @date 2014��4��28�� ����9:35:34
	 */
	public class WorkFlowHandleAsync extends AsyncTask<String, Void, ResultMessage> {

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialogPass.show();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			// ���ٵȴ���
			dialogPass.dismiss();
			UIHelper.ToastMessage(WorkFlowActivity.this, result.getResult());
			sendLogs(); //������־��Ϣ����ͳ��
			
			// �ӳ�2�����������
	        new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	Intent mIntent = new Intent();
	            	setResult(RESULT_OK, mIntent);
	            	finish();
	            }
	        }, 2000);		
		}
		
		@Override
		protected ResultMessage doInBackground(String... params) {		
			return workFlowHandle(params[0],params[1],params[2]);
		}
	}	
	
	/** 
	* @Title: workFlowHandle 
	* @Description: �������������
	* @param @param serviceUrl �����ַ
	* @param @param paramStr ����
	* @param @param typeName ������������
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��4��30�� ����1:57:14
	*/
	private ResultMessage workFlowHandle(String serviceUrl,String paramStr,String typeName){
		appHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), typeName);
		
		// ����ʵ��-�ִ��� ������
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(appHashMap.get("ClassType"));
		repository.setIsTest(appHashMap.get("IsTest"));
		repository.setServiceName(appHashMap.get("ServiceName"));
		repository.setServiceType(appHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(appHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(appHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);

		workFlowBusiness.setServiceUrl(serviceUrl);
		return workFlowBusiness.approveHandle(repository, paramStr);
	}
	
	
	/**
	 * @ClassName rejectNodeAsyncClient
	 * @Description �첽���ز��ؽڵ���Ϣ
	 * @author 21291
	 * @date 2014��7��29�� ����10:29:31
	 */
	private class rejectNodeAsyncClient extends AsyncTask<String, Integer, List<RejectNodeInfo>> {
		
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<RejectNodeInfo> result) {
			super.onPostExecute(result);
			rejectNodeRender(result);
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<RejectNodeInfo> doInBackground(String... params) {
			return rejectNodeHandle(params[0],params[1]);
		}
	}
	
	/** 
	* @Title: rejectNodeHandle 
	* @Description: ���ز��ؽڵ���Ϣ����
	* @param @param serviceUrl
	* @param @param paramStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��29�� ����10:32:11
	*/
	private List<RejectNodeInfo> rejectNodeHandle(String serviceUrl,String paramStr){
		// ����ʵ��-�ִ��� ������
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(rejectNodeHashMap.get("ClassType"));
		repository.setIsTest(rejectNodeHashMap.get("IsTest"));
		repository.setServiceName(rejectNodeHashMap.get("ServiceName"));
		repository.setServiceType(rejectNodeHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(rejectNodeHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(rejectNodeHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(WorkFlowActivity.this);
		RejectNodeBusiness eBusiness= (RejectNodeBusiness)factoryBusiness.getInstance("RejectNodeBusiness",serviceUrl);
		return eBusiness.getRejectNodeInfo(repository, paramStr);
	}
	
	/** 
	* @Title: rejectNodeRender 
	* @Description: ���ز��ؽڵ���Ϣ
	* @param @param reList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��29�� ����10:44:39
	*/
	private void rejectNodeRender(List<RejectNodeInfo> reList){
		if(reList.size()==0){ //�����ȡ��������,˵�����������Զ����ؽڵ㣬����Ҫѡ�񲵻ؽڵ�
			paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment;
			if("18".equals(fSystemType))  //������°�
			{
				new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"NewWorkFlowReject");		
			}
			
			if("38".equals(fSystemType))  //�����HR��
			{
				new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"HrWorkFlowReject");		
			}	
			
			if("8".equals(fSystemType))  //����Ǳ�����
			{
				new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"EpWorkFlowReject");		
			}	
		}
		else{
			final RejectSpinnerDialog mSpinnerDialog=new RejectSpinnerDialog(WorkFlowActivity.this,reList,new ISpinnerListener(){

				@Override
				public void rejectOk(int n, RejectNodeInfo reInfo) {  //ѡ����һ���ڵ�
					paramStr = workFlowKey+","+fSystemType+","+fClassTypeID+","+fBillID+","+fItemNumber+","+fComment+","+reInfo.getFNodeValue();
					if("18".equals(fSystemType))  //������°�
					{
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"NewWorkFlowReject");	
					}
					
					if("38".equals(fSystemType))  //�����HR��
					{
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"HrWorkFlowReject");	
					}
					
					if("8".equals(fSystemType))  //����Ǳ�����
					{
						new WorkFlowHandleAsync().execute(newWorkAppUrl,paramStr,"EpWorkFlowReject");	
					}
				}

				@Override
				public void cancelled() {}
				
				@Override
				public void adAmapOk(int n, AdAmapInfo adAmapInfo) {}	

				@Override
				public void dHWeekOk(int n, String itemText,DHWeekInfo dhWeekInfo) {}

			});
			mSpinnerDialog.setTitle(getResources().getString(R.string.reject_title));
			mSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
			mSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
			mSpinnerDialog.show();	
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ��������ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����11:05:52
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		logInfo.setFActionName("approve");
		logInfo.setFNote(fBillName);
		UIHelper.sendLogs(WorkFlowActivity.this,logInfo);
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
