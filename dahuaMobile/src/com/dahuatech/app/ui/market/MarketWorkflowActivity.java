package com.dahuatech.app.ui.market;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MarketWorkFlowAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.market.MarketWorkflowInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MarketBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName MarketWorkflowActivity
 * @Description ����/��ͬ������ҳ��
 * @author 21291
 * @date 2015��1��26�� ����1:52:10
 */
public class MarketWorkflowActivity extends MenuActivity {
	private String fSystemType,fClassTypeID,fBillID,fItemNumber;  //ϵͳ���ͣ�����ID��������������,Ա����
	
	private ListView mListView; 					//�б�ؼ�����
	private ProgressDialog dialog;					//���̿�
	private MarketBusiness marketBusiness;			//ҵ���߼���
	private MarketWorkFlowAdapter mAdapter;			//��������
	
	private String serviceUrl,typeName; 			//�����ַ,������������
	private HashMap<String, String> hashMap; 		//������Ϣ
	
	private AppContext appContext; 					//ȫ��Context

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.market_workflow);
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
			fSystemType=extras.getString(AppContext.WORKFLOW_FSYSTEMTYPE_KEY);
			fClassTypeID=extras.getString(AppContext.WORKFLOW_FCLASSTYPEID_KEY);
			fBillID=extras.getString(AppContext.WORKFLOW_FBILLID_KEY);		
			typeName=extras.getString(AppContext.MARKET_WORKFLOW_TYPE);	
		}
		
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_WORKFLOWACTIVITY;
		initView();
		if(!StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fClassTypeID) && !StringUtils.isEmpty(fBillID)&& !StringUtils.isEmpty(fItemNumber)){
			// ����һ���ȴ�������ʾ�û��ȴ�
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);
			
			new MarketWorkflowAsync().execute();
			sendLogs(typeName);   //�����ռǽ���ͳ��
		}
		else {
			UIHelper.ToastMessage(MarketWorkflowActivity.this, R.string.market_workflow_paramerror);
			return;
		}
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����2:42:46
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.market_workflow_listView);
		hashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "CrmWorkFlowActivity");
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MarketWorkflowActivity.this);
		marketBusiness=(MarketBusiness)factoryBusiness.getInstance("MarketBusiness",serviceUrl); 
	}	

	/**
	 * @ClassName MarketWorkflowAsync
	 * @Description �첽����������¼����
	 * @author 21291
	 * @date 2015��1��29�� ����3:01:53
	 */
	private class MarketWorkflowAsync extends AsyncTask<Void, Void, List<MarketWorkflowInfo>> {	
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketWorkflowInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // ���ٵȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MarketWorkflowInfo> doInBackground(Void... params) {
			return getListByPost();
		}
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ͨ��POST��ʽ��ȡ����Դ
	* @param @return     
	* @return List<MarketWorkflowInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����3:11:28
	*/
	private List<MarketWorkflowInfo> getListByPost() {
		// ����ʵ��-�ִ���
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// ����ʵ��-������
		SqlParametersInfo sqlParameters= SqlParametersInfo.getSqlParametersInfo();
		setWorkFlowParam(repository,sqlParameters);
		return marketBusiness.getMarketWorkflowInfo(repository, sqlParameters);
	}

	/** 
	* @Title: setWorkFlowParam 
	* @Description: ���ù�������¼����
	* @param @param repository
	* @param @param sqlParameters     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����3:04:07
	*/
	private void setWorkFlowParam(RepositoryInfo repository,SqlParametersInfo sqlParameters) {		
		//����ֵ	
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
	* @param @param arrayList �б���     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����10:14:59
	*/
	private void renderListView(List<MarketWorkflowInfo> arrayList) {
		if(arrayList.size()==0){ //�����ȡ��������
			UIHelper.ToastMessage(MarketWorkflowActivity.this, R.string.market_workflow_netparseerror);		
			return;
		}
		// ʹ�����ݼ�����adapter����
		mAdapter = new MarketWorkFlowAdapter(this, arrayList,R.layout.market_workflow_item);
		// ����ListView��adapter
		mListView.setAdapter(mAdapter);
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
	
	/** 
	* @Title: sendLogs 
	* @Description: ������־��¼��������
	* @param @param typeName     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��2�� ����11:23:57
	*/
	private void sendLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		if("bidflowquery".equals(typeName)){  //˵���Ǳ��۲�ѯ
			logInfo.setFModuleName(getResources().getString(R.string.log_market_bid));
		}
		else{  //˵���Ǻ�ͬ��ѯ
			logInfo.setFModuleName(getResources().getString(R.string.log_market_contract));
		}
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(MarketWorkflowActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketWorkflowActivity.this);
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
