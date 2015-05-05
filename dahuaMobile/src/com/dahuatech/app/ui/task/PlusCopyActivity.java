package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.PlusCopyInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.RejectNodeBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName PlusCopyActivity
 * @Description ��ǩ/������ҳ,ֻ���µĵ���ƽ̨������������ ϵͳ��Ϊ18��
 * @author 21291
 * @date 2014��9��22�� ����9:13:48
 */
public class PlusCopyActivity extends MenuActivity {
	private TextView fPlusTView,fItemNumbersTView,fItemNamesTView;
	private EditText fContentEText;
	private ImageView fInImageView,fCIView;
	private Button fConfirm,fCancle;
	private LinearLayout chkLayout;							//���ӽڵ㲼��ʵ��
	private ProgressDialog dialog,appDialog;    			//Ĭ�ϵ�����,����������
	
	private String fType,fSystemId,fBillId,fClassTypeId; 	//fType����  "0"-�����ǩ  "1"-������
	private String fPlusCopyParam;
	private List<RejectNodeInfo> rArrayList;				//ѡ�еĸ��ӽڵ�
	private RejectNodeBusiness rBusiness;					//ҵ���߼���
	private PlusCopyInfo plusCopyInfo;						//��ǩ����ʵ����
	private List<PlusCopyPersonInfo> personList;			//ѡ����Ա������
	
	private HashMap<String, String> plusCopyHashMap; 		//������Ϣ
	private AppContext appContext; 							//ȫ��Context
	private String nodeUrl;  								//��ȡ���ӽڵ�����ַ
	private String appUrl;  								//���������ַ			
	private String fItemNumber; 							//Ա����
	private String fBillName=""; 							//��������
	private String showResult; 								//��ʾ�������
	
	private Gson gson;										//gson������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pluscopy_main);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
				
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fType=extras.getString(AppContext.FPLUSCOPY_TYPE_KEY);
			fSystemId=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fBillId=extras.getString(AppContext.FBILLID_KEY);	
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fBillName=extras.getString(AppContext.WORKFLOW_FBILLNAME_KEY);
		}	
			
		//��ȡ�°��ǩ/���ͽڵ�������Ϣ
		plusCopyHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "PlusCopyNodeRepository");
		nodeUrl=AppUrl.URL_API_HOST_ANDROID_REJECTNODEREPOSITORY;	//��ȡ���ӽڵ�����ַ
		appUrl=AppUrl.URL_API_HOST_ANDROID_PLUSCOPYAPPURL;	    	//���������ַ 	
		
		initView();
		setListener();
		new getNodeAsync().execute(fPlusCopyParam);
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����2:31:55
	*/
	private void initView(){
		fPlusTView=(TextView)findViewById(R.id.pluscopy_main_FPlus);
		if("1".equals(fType)){  //˵���ǳ��ͽ���
			setTitle(getResources().getString(R.string.copy_main_title));
			fPlusTView.setText(getResources().getString(R.string.pluscopy_main_copy));
			showResult=getResources().getString(R.string.pluscopy_main_copy_app_result);
		}
		else{
			showResult=getResources().getString(R.string.pluscopy_main_plus_app_result);
		}
		fItemNumbersTView=(TextView)findViewById(R.id.pluscopy_main_FItemNumbers);
		fItemNamesTView=(TextView)findViewById(R.id.pluscopy_main_FItemNames);
		fContentEText=(EditText)findViewById(R.id.pluscopy_main_FContent);
		fInImageView=(ImageView)findViewById(R.id.pluscopy_main_FItemNumbers_ImageView);
		fCIView=(ImageView)findViewById(R.id.pluscopy_main_FContent_ImageView);
		chkLayout=(LinearLayout)findViewById(R.id.pluscopy_main_checkBox);
		fConfirm=(Button)findViewById(R.id.pluscopy_main_FConfirm);
		fCancle=(Button)findViewById(R.id.pluscopy_main_FCancle);
		
		//���ݼ���
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//��������
		appDialog = new ProgressDialog(this);
		appDialog.setMessage(getResources().getString(R.string.dialog_approveing));
		appDialog.setCancelable(false);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(PlusCopyActivity.this);
		rBusiness= (RejectNodeBusiness)factoryBusiness.getInstance("RejectNodeBusiness",nodeUrl);
		
		rArrayList=new ArrayList<RejectNodeInfo>();
		fPlusCopyParam=fSystemId+","+fClassTypeId+","+fBillId;
		personList=new ArrayList<PlusCopyPersonInfo>();
		plusCopyInfo=PlusCopyInfo.getPlusCopyInfo();
		
		gson=GsonHelper.getInstance();
	}
	
	/** 
	* @Title: setListener 
	* @Description: ������ͼ�ؼ��¼�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����2:45:02
	*/
	private void setListener(){
		//������Ա��������
		fInImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showPlusCopyPersonSearch(PlusCopyActivity.this,PlusCopyPersonInfo.ConvertToJson(personList));			
			}
		});
		
		//��ʾ���ɱ༭״̬
		fCIView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content=fContentEText.getText().toString();
				fContentEText.setCursorVisible(true);	 //��ʾ���
				fContentEText.requestFocus();  			 //��ȡ����
				fContentEText.setSelection(content.length());
				
				//��ʾ�������
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(fContentEText,InputMethodManager.SHOW_IMPLICIT);		
			}
		});
		
		//ȷ������
		fConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verify()){
					setModel();
					new plusCopyAppAsync().execute(plusCopyInfo);
				}
			}
		});
		
		//ȡ��
		fCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		
			}
		});
	}
	
	/**
	 * @ClassName getNodeAsync
	 * @Description  �첽��ȡ���ӽڵ�ʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��9��24�� ����2:54:16
	 */
	private class getNodeAsync extends AsyncTask<String, Void, List<RejectNodeInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<RejectNodeInfo> doInBackground(String... params) {
			return getNodeByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<RejectNodeInfo> result) {
			super.onPostExecute(result);
			renderNodeView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getNodeByPost 
	* @Description: ��ȡ���ӽڵ㼯��
	* @param @param paramStr ���ݵĲ���ֵ
	* @param @return     
	* @return List<RejectNodeInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����4:55:43
	*/
	private List<RejectNodeInfo> getNodeByPost(String paramStr){ 
		// ����ʵ��-�ִ��� ������
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(plusCopyHashMap.get("ClassType"));
		repository.setIsTest(plusCopyHashMap.get("IsTest"));
		repository.setServiceName(plusCopyHashMap.get("ServiceName"));
		repository.setServiceType(plusCopyHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(plusCopyHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(plusCopyHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);		
		return rBusiness.getRejectNodeInfo(repository,paramStr);
	}
	
	/** 
	* @Title: renderNodeView 
	* @Description: ��Ӹ��ӽڵ�ؼ�
	* @param @param rNodeInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����4:42:06
	*/
	@SuppressLint("InflateParams")
	private void renderNodeView(final List<RejectNodeInfo> rNodeInfos){
		if(rNodeInfos.size() > 0){  //˵���и��ӽڵ�
			int i=1;
			for (RejectNodeInfo item : rNodeInfos) {
				 View linearLayout = LayoutInflater.from(this).inflate(R.layout.pluscopy_main_checkbox, null);
				 
				 final TextView fChkValue=(TextView)linearLayout.findViewById(R.id.pluscopy_main_checkBox_value);
				 fChkValue.setText(item.getFNodeKey());
				 
				 final CheckBox fChkName=(CheckBox)linearLayout.findViewById(R.id.pluscopy_main_checkBox_name);
				 fChkName.setText(item.getFNodeValue());
				 
				 if(i==1){
					 fChkName.setChecked(true); 
					 rArrayList.add(item);
				 }	
				 //��ѡ��ѡ���¼�
				 fChkName.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						RejectNodeInfo rNodeInfo=new RejectNodeInfo();
						rNodeInfo.setFNodeKey(fChkValue.getText().toString());
						rNodeInfo.setFNodeValue(fChkName.getText().toString());
						if(isChecked){  //˵����ѡ�е�״̬
							rArrayList.add(rNodeInfo);
						}
						else{  //�Ƴ�ѡ�еĽڵ�
							for (RejectNodeInfo item : rArrayList) {
								//˵������ڵ��Ѿ���ѡ��
								if(item.getFNodeKey().equals(rNodeInfo.getFNodeKey()))  
								{
									rArrayList.remove(item);
								}
							}	
						}
					}
				 });
				 
				 chkLayout.addView(linearLayout);
				 i++;
			}
		}
	}
	
	
	/** 
	* @Title: verify 
	* @Description: ȷ��֮ǰ��֤
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��9��26�� ����9:37:14
	*/
	private boolean verify(){
		if(rArrayList.size()<=0){  //���ӽڵ�
			UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_nodes_error));
			return false;
		}
		if(StringUtils.isEmpty(fItemNumbersTView.getText().toString()) || StringUtils.isEmpty(fItemNamesTView.getText().toString())){
			if("0".equals(fType)){  //��ǩ
				UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_plus_error));
			}
			else{  //����
				UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_copy_error));
			}
			return false;
		}	
		return true;
	}
	
	/** 
	* @Title: setModel 
	* @Description: ����ʵ������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��26�� ����9:50:27
	*/
	private void setModel(){
		int i=0;
		int rCount=rArrayList.size();
		StringBuffer sb = new StringBuffer();	
		for (RejectNodeInfo item : rArrayList) {
			if(i==rCount-1){
				sb.append(item.getFNodeKey());
			}
			else{
				sb.append(item.getFNodeKey()).append(",");
			}
			i++;
		}
		
		plusCopyInfo.setFAppKey(AppContext.WORKFLOW_NEWOFFICE_KEY);
		plusCopyInfo.setFSystemId(fSystemId);
		plusCopyInfo.setFClassTypeId(fClassTypeId);
		plusCopyInfo.setFBillId(fBillId);
		plusCopyInfo.setFItemNumber(fItemNumber);
		plusCopyInfo.setFType(fType);
		plusCopyInfo.setFNodeIds(sb.toString());
		plusCopyInfo.setFPersonNumbers(fItemNumbersTView.getText().toString());
		plusCopyInfo.setFContent(fContentEText.getText().toString());
	}
	
	/**
	 * @ClassName plusCopyAppAsync
	 * @Description ��ǩ/������������
	 * @author 21291
	 * @date 2014��9��26�� ����10:03:24
	 */
	private class plusCopyAppAsync extends AsyncTask<PlusCopyInfo,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			appDialog.show(); // ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(PlusCopyInfo... params) {
			return plusCopyServer(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showAppResult(result);	
			appDialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: plusCopyServer 
	* @Description: �첽��������ҵ���߼�����
	* @param @param pCopyInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��26�� ����10:08:38
	*/
	private ResultMessage plusCopyServer(PlusCopyInfo pCopyInfo){
		rBusiness.setServiceUrl(appUrl);
		return rBusiness.plusCopyApp(pCopyInfo);
	}
	
	/** 
	* @Title: showAppResult 
	* @Description: ��ʾ��ǩ/���Ͳ������
	* @param @param result     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��26�� ����10:15:12
	*/
	private void showAppResult(ResultMessage result){
		if(!result.isIsSuccess()){  //˵������ʧ��
			showResult=result.getResult();
		}
		UIHelper.ToastMessage(PlusCopyActivity.this, showResult);
		//sendLogs(fType); 	//������־��Ϣ����ͳ��
		
		// �ӳ�2������
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        		finish();     
            }
        }, 2000);
	}
	
	
	// ����Աҳ��,�ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.FPLUSCOPY_PERSON_KEY: //��Ա�б�
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						//��ȡ������Ϣ
						String transferStr=extras.getString(AppContext.PLUSCOPY_PERSON_LIST);
						personList.clear();
						personList.addAll(getPersonList(transferStr));
						personList=ListHelper.rDPlusCopyPerson(personList); //ȥ��
						getAttendPerson(personList);
					}
				}	
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: getPersonList 
	* @Description: ��ȡ��Ա�б�ѡ�����Ա
	* @param @param fTempList
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:46:25
	*/
	private List<PlusCopyPersonInfo> getPersonList(final String fTempList){
		List<PlusCopyPersonInfo> resultList=new ArrayList<PlusCopyPersonInfo>();
		if(!StringUtils.isEmpty(fTempList)){
			try {
				Type listType = new TypeToken<ArrayList<PlusCopyPersonInfo>>(){}.getType();
				JSONArray jsonArray= new JSONArray(fTempList);
				resultList=gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	/** 
	* @Title: getAttendPerson 
	* @Description: ��ȡ������Ա����
	* @param @param personList
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:01:28
	*/
	private void getAttendPerson(final List<PlusCopyPersonInfo> personList){
		int i=0;
		String strItemNumbers="";
		String strNames="";
		int personCount=personList.size();
		if(personCount > 0){
			StringBuffer sb1 = new StringBuffer();	
			StringBuffer sb2 = new StringBuffer();	
			for (PlusCopyPersonInfo item : personList) {
				if(i==personCount-1){
					sb1.append(item.getFItemNumber());
					sb2.append(item.getFItemName());
				}
				else{
					sb1.append(item.getFItemNumber()).append(",");
					sb2.append(item.getFItemName()).append(",");
				}
				i++;
			}
			strItemNumbers=sb1.toString();
			strNames=sb2.toString();
		}
		fItemNumbersTView.setText(strItemNumbers);
		fItemNamesTView.setText(strNames);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ��ǩ/���Ͳ���ʱ��������־��¼��������
	* @param @param fType ��������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����5:20:22
	*//*
	private void sendLogs(String fType){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		if("0".equals(fType)){  //��ǩ
			logInfo.setFActionName("plus");
		}
		else{//����
			logInfo.setFActionName("copy");
		}
	
		logInfo.setFNote(fBillName);
		UIHelper.sendLogs(PlusCopyActivity.this,logInfo);
	}*/
	
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
