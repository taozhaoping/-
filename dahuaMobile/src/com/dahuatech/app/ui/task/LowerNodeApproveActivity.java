package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.LowerNodeAppBackInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppConfigInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppItemInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppResultInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppRoleInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppRoleJsonInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppSpinnerInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppUserInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.LowerNodeAppBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.MultiSelectionSpinner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName LowerNodeApproveActivity
 * @Description �¼��ڵ�������,ֻ���µĵ���ƽ̨������������ ϵͳ��Ϊ18��
 * @author 21291
 * @date 2014��11��6�� ����11:45:15
 */
@SuppressLint("InflateParams")
public class LowerNodeApproveActivity extends MenuActivity {
	
	private LinearLayout checkBoxLayout,structLayout;					//��ת�ڵ㲼��,��֯�ܹ�����
	private CheckBox[] checkBoxArray;									//���帴ѡ������
	
	private TextView fResultTView;										//ѡȡ���
	private Button fConfirm,fCancle;
	private ProgressDialog dialog,appDialog;    						//Ĭ�ϵ�����,����������				
	private String fSystemId,fBillId,fClassTypeId,fBillName; 			//��������
	private LowerNodeAppBusiness lBusiness;								//ҵ���߼���
	private HashMap<String, String> configHashMap; 						//������Ϣ
	private AppContext appContext; 										//ȫ��Context

	private String fLowerNodeAppParam;  								//ƴ�ӵ��ַ�������
	private String nodeStatusUrl,passWorkFlowUrl;  						//��ȡ�¼��ڵ����������ַ,���������ַ			
	private String fItemNumber; 										//Ա����									
	private Gson gson;													//gson������
	
	private Map<String, LowerNodeAppResultInfo> nodeValueMap;			//�ڵ�ѡȡ���ʵ�弯��
	public Map<String, LowerNodeAppResultInfo> getNodeValueMap() {
		return nodeValueMap;
	}
	private List<LowerNodeAppBackInfo> lBackList;						//ѡ���ֵ�����б�
	private LowerNodeAppConfigInfo lConfig;								//�¼��ڵ���������ʵ��
	
	//ͨ��������������������Ϣ
	private List<LowerNodeAppUserInfo> lowerNodeAppUserList;			//�����˻�����Ϣʵ�弯��
	private List<LowerNodeAppRoleInfo> lowerNodeAppRoleList;			//�����˽�ɫ��Ϣʵ�弯��
	
	private static final String spinnerUserType="User";					//������ڵ�����
	private static final String spinnerRoleType="Role";					//�������ɫ����
	private String  spinnerTypeIndex;									//����������
	private Map<String,LowerNodeAppSpinnerInfo> spinnerMap;				//�¼��ڵ�������������ʵ�弯��
	public Map<String, LowerNodeAppSpinnerInfo> getSpinnerMap() {
		return spinnerMap;
	}

	//ͨ��������ȡ��������Ϣ
	private List<PlusCopyPersonInfo> personList;						//ѡ����Ա������
	private TextView fSearchNodeName,fSearchItem;						//��������ؼ�
	private ImageView fSearchImageView;									//������ť
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lower_node_approve);
		
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
			fSystemId=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fBillId=extras.getString(AppContext.FBILLID_KEY);	
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fBillName=extras.getString(AppContext.WORKFLOW_FBILLNAME_KEY);
		}	
			
		//��ȡ�°��ǩ/���ͽڵ�������Ϣ
		configHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "LowerNodeAppRepository");
		nodeStatusUrl=AppUrl.URL_API_HOST_ANDROID_LOWERNODEAPPROVEURL;			//��ȡ�¼��ڵ����������ַ
		passWorkFlowUrl=AppUrl.URL_API_HOST_ANDROID_PASSLOWERNODEHANDLEURL;	    //�������������ַ 	
		
		initView();
		setListener();
		new getLowerNodeAppAsync().execute(fLowerNodeAppParam);
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
		checkBoxLayout=(LinearLayout)findViewById(R.id.lower_node_approve_checkBox_layout);
		structLayout=(LinearLayout)findViewById(R.id.lower_node_approve_struct_layout);
		fResultTView=(TextView)findViewById(R.id.lower_node_approve_FResult);
		
		fConfirm=(Button)findViewById(R.id.lower_node_approve_FConfirm);
		fCancle=(Button)findViewById(R.id.lower_node_approve_FCancle);
		
		//���ݼ���
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//��������
		appDialog = new ProgressDialog(this);
		appDialog.setMessage(getResources().getString(R.string.dialog_approveing));
		appDialog.setCancelable(false);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LowerNodeApproveActivity.this);
		lBusiness= (LowerNodeAppBusiness)factoryBusiness.getInstance("LowerNodeAppBusiness",nodeStatusUrl);	
		
		fLowerNodeAppParam=fSystemId+","+fClassTypeId+","+fBillId+","+fItemNumber;
		gson=GsonHelper.getInstance();
		
		nodeValueMap=new LinkedHashMap<String, LowerNodeAppResultInfo>();   //��ʼ��
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
		//ȷ������
		fConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(fResultTView.getText().toString())){
					setLowerNodeAppBack();
					if(verify()){
						new passWorkflowAsync().execute(passWorkFlowUrl);
					}
				}
				else {
					UIHelper.ToastMessage(LowerNodeApproveActivity.this, getResources().getString(R.string.lower_node_approve_result_error));
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
	 * @ClassName getLowerNodeAppAsync
	 * @Description �첽��ȡ�¼��ڵ�����ʵ�弯��
	 * @author 21291
	 * @date 2014��11��11�� ����9:19:39
	 */
	private class getLowerNodeAppAsync extends AsyncTask<String, Void, List<LowerNodeAppInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<LowerNodeAppInfo> doInBackground(String... params) {
			return getLowerNodeAppByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<LowerNodeAppInfo> result) {
			super.onPostExecute(result);
			renderLowerNodeAppView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getLowerNodeAppByPost 
	* @Description: ��ȡ�¼��ڵ������˼���
	* @param @param paramStr
	* @param @return     
	* @return List<LowerNodeAppInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����9:25:47
	*/
	private List<LowerNodeAppInfo> getLowerNodeAppByPost(final String paramStr){ 
		// ����ʵ��-�ִ��� ������
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(configHashMap.get("ClassType"));
		repository.setIsTest(configHashMap.get("IsTest"));
		repository.setServiceName(configHashMap.get("ServiceName"));
		repository.setServiceType(configHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(configHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(configHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);		
		
		return lBusiness.getLowerNodeAppInfo(repository,paramStr);
	}
	
	/** 
	* @Title: renderLowerNodeAppView 
	* @Description: ����¼��ڵ�
	* @param @param lowerNodeAppInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����9:30:13
	*/
	@SuppressLint("InflateParams")
	private void renderLowerNodeAppView(final List<LowerNodeAppInfo> lowerNodeAppInfos){
		if(lowerNodeAppInfos.size() > 0){  //˵�����¼��ڵ�
			int lnLength=lowerNodeAppInfos.size();
			checkBoxArray=new CheckBox[lnLength];
			for (int i = 0; i < lnLength; i++) {
				parseLowerNodeAppInfo(lowerNodeAppInfos.get(i),i,lnLength);
			}
		}
	}

	/** 
	* @Title: parseLowerNodeAppInfo 
	* @Description: �����¼��ڵ�����״̬ʵ��
	* @param @param lowerNodeApp �¼��ڵ�ʵ��
	* @param @param i ����   
	* @param @param lnLength �ܵĳ���    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����3:07:27
	*/
	private void parseLowerNodeAppInfo(final LowerNodeAppInfo lowerNodeApp, final int i, final int lnLength){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_checkbox, null);
	
		final CheckBox fCheckBoxName=(CheckBox)linearLayout.findViewById(R.id.lower_node_approve_checkbox_name);
		fCheckBoxName.setId(i);
		fCheckBoxName.setText(lowerNodeApp.getFNodeName());
		checkBoxArray[i]=fCheckBoxName;
		
		final TextView fCheckBoxValue=(TextView)linearLayout.findViewById(R.id.lower_node_approve_checkbox_value);
		fCheckBoxValue.setText(lowerNodeApp.getFNodeId());
		
		LowerNodeAppResultInfo lResultInfo=new LowerNodeAppResultInfo();
		lResultInfo.setFNodeId(lowerNodeApp.getFNodeId());
		lResultInfo.setFNodeName(lowerNodeApp.getFNodeName());
		lResultInfo.setFIsMust(lowerNodeApp.getFIsMust());
		nodeValueMap.put(lowerNodeApp.getFNodeName(), lResultInfo);  //ÿ���ڵ�ѡȡ��ֵ��������
		
		if(i==0){  //�׸��ڵ�Ĭ��ѡ��
			fCheckBoxName.setChecked(true);
			setSubClassView(lowerNodeApp);
		}
		else{
			fCheckBoxName.setChecked(false);
		}
		
		//��ѡ��ѡ���¼�
		fCheckBoxName.setOnCheckedChangeListener(new CheckedListener(fCheckBoxName, lowerNodeApp, lnLength));
		checkBoxLayout.addView(linearLayout);
	}
	
	/**
	 * @ClassName CheckedListener
	 * @Description ��ѡ������
	 * @author 21291
	 * @date 2014��11��11�� ����5:00:50
	 */
	private class CheckedListener implements OnCheckedChangeListener{
		private CheckBox chBox;
		private LowerNodeAppInfo lNodeAppInfo;
		private int lnLength;
		
		public CheckedListener(CheckBox checkBox, LowerNodeAppInfo lowerNodeAppInfo,int lnLength){
			this.chBox=checkBox;
			this.lNodeAppInfo=lowerNodeAppInfo;
			this.lnLength=lnLength;
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			structLayout.removeAllViews();
			if(isChecked){  //˵����ѡ�е�״̬
				for (int j = 0; j < lnLength; j++) {
					if(buttonView.getId()==j){
						chBox.setChecked(true);
					}
					else {
						checkBoxArray[j].setChecked(false);
					}
				}
				setSubClassView(lNodeAppInfo);
			}
		}	
	}
	
	/** 
	* @Title: setSubClassView 
	* @Description: ���ݽڵ�״̬�����ò�ͬ�ؼ���ʾ����
	* @param @param lowerNodeApp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����1:53:34
	*/
	private void setSubClassView(final LowerNodeAppInfo lowerNodeApp){
		if(lowerNodeApp.getFNodeStatus() ==1){  //˵����������Ϣ��ͨ������������ȡ
			personList=new ArrayList<PlusCopyPersonInfo>();
			initLowerNodeAppSearch(lowerNodeApp.getFNodeName());
		}
		
		if(lowerNodeApp.getFNodeStatus()==2){  //˵����������Ϣ�ǴӸ����������̽ڵ����õ���Ϣ����ȡ
			spinnerMap=new LinkedHashMap<String, LowerNodeAppSpinnerInfo>();  //��ʼ��
			getLowerNodeAppItem(lowerNodeApp.getFSubEntrys());
			
			List<String> lUserStringList=getUserStringList(lowerNodeAppUserList);
			if(lUserStringList.size() > 0){  //˵���������˻�����Ϣ
				initLoweNodeAppSpinnerUser(lUserStringList,lowerNodeApp.getFNodeName());
			}
			
			int roleLength=lowerNodeAppRoleList.size();	
			if(roleLength > 0){ //˵���������˽�ɫ��Ϣ
				for (int i = 0; i < roleLength; i++) {
					initLoweNodeAppSpinnerRole(i,roleLength,lowerNodeAppRoleList.get(i).getFRoleName(),getUserStringList(lowerNodeAppRoleList.get(i).getFUserList()),lowerNodeApp.getFNodeName());
				}
			}
		}
		
		if(lowerNodeApp.getFNodeStatus()==3){  //˵����������Ϣ,�Ȳ�ͨ����������ȡ��Ҳ��ͨ������������Ա����ȡ���Զ�����������
			LowerNodeAppResultInfo nodeResult=nodeValueMap.get(lowerNodeApp.getFNodeName());
			String selectResult="ϵͳ�Զ�����";
			nodeResult.setFSelectResult(selectResult);
			nodeResult.setFShowResult(lowerNodeApp.getFNodeName()+":"+selectResult);
			fResultTView.setText(MultiSelectionSpinner.getNodeValueMap(nodeValueMap));
		}
	}
	
	/** 
	* @Title: getLowerNodeAppItem 
	* @Description: ��ȡ�����˻�����Ϣ���ϰ�װ��
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����11:10:49
	*/
	private void getLowerNodeAppItem(final String fSubEntrys){
		try {
			JSONObject jsonObject=new JSONObject(fSubEntrys);
			LowerNodeAppItemInfo lowerNodeAppItem= gson.fromJson(jsonObject.toString(),LowerNodeAppItemInfo.class);
			if(lowerNodeAppItem!=null){
				lowerNodeAppUserList=parseLowerNodeAppUser(lowerNodeAppItem.getFApproveUser());
				lowerNodeAppRoleList=parseLowerNodeAppRole(lowerNodeAppItem.getFApproveRole());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	/** 
	* @Title: getUserStringList 
	* @Description: ��ȡ�����˻�����Ϣʵ��Ա������
	* @param @param userList �����˻�����Ϣ
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����11:31:15
	*/
	private List<String> getUserStringList(final List<LowerNodeAppUserInfo> userList){
		List<String> lUserString=new ArrayList<String>();
		if(userList.size()>0){
			for (LowerNodeAppUserInfo item : userList) {
				lUserString.add(item.getFItemName()+"("+item.getFItemNumber()+")");
			}
		}
		return lUserString;
	}
	
	/** 
	* @Title: parseLowerNodeAppUser 
	* @Description: ���������˻�����Ϣʵ��
	* @param @param fApproveUser
	* @param @return     
	* @return List<LowerNodeAppUserInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����11:33:46
	*/
	private List<LowerNodeAppUserInfo> parseLowerNodeAppUser(final String fApproveUser){
		List<LowerNodeAppUserInfo> resultList=new ArrayList<LowerNodeAppUserInfo>();
		try {
			Type userListType = new TypeToken<ArrayList<LowerNodeAppUserInfo>>(){}.getType();	
			JSONArray jsonArray= new JSONArray(fApproveUser);
			resultList=gson.fromJson(jsonArray.toString(), userListType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	/** 
	* @Title: parseLowerNodeAppRole 
	* @Description: ���������˽�ɫ��Ϣʵ��
	* @param @param fApproveRole
	* @param @return     
	* @return List<LowerNodeAppRoleInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����11:53:33
	*/
	private List<LowerNodeAppRoleInfo> parseLowerNodeAppRole(final String fApproveRole){
		List<LowerNodeAppRoleJsonInfo> parseList=new ArrayList<LowerNodeAppRoleJsonInfo>();
		try {
			Type roleJsonListType = new TypeToken<ArrayList<LowerNodeAppRoleJsonInfo>>(){}.getType();	
			JSONArray jsonArray= new JSONArray(fApproveRole);
			parseList=gson.fromJson(jsonArray.toString(), roleJsonListType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getLowerNodeAppRole(parseList);
	}
	
	/** 
	* @Title: getLowerNodeAppRole 
	* @Description: ��ȡ�����˽�ɫ��Ϣʵ��
	* @param @param roleJsonList
	* @param @return     
	* @return List<LowerNodeAppRoleInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��11�� ����1:17:16
	*/
	private List<LowerNodeAppRoleInfo> getLowerNodeAppRole(final List<LowerNodeAppRoleJsonInfo> roleJsonList){
		List<LowerNodeAppRoleInfo> resultList=new ArrayList<LowerNodeAppRoleInfo>();
		if(roleJsonList.size() > 0){  //˵�����ַ�����
			for (LowerNodeAppRoleJsonInfo item : roleJsonList) {
				LowerNodeAppRoleInfo roleInfo=new LowerNodeAppRoleInfo();
				roleInfo.setFRoleName(item.getFRoleName());
				roleInfo.setFUserList(parseLowerNodeAppUser(item.getFRoleApproveUser()));
				resultList.add(roleInfo);
			}
		}
		return resultList;
	}
	
	/** 
	* @Title: initLowerNodeAppSearch 
	* @Description: ��ʼ����������ͼ
	* @param @param fNodeName �ڵ�����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����2:17:24
	*/
	private void initLowerNodeAppSearch(final String fNodeName){
		View searchLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_search, null);
		
		fSearchNodeName=(TextView)searchLayout.findViewById(R.id.lower_node_approve_search_FNodeName);
		fSearchNodeName.setText(fNodeName);
		fSearchItem=(TextView)searchLayout.findViewById(R.id.lower_node_approve_search_FItem);
		fSearchImageView=(ImageView)searchLayout.findViewById(R.id.lower_node_approve_search_FImageView);

		fSearchImageView.setOnClickListener(new OnClickListener() {	//������Ա�������� 	
			@Override
			public void onClick(View v) {
				UIHelper.showPlusCopyPersonSearch(LowerNodeApproveActivity.this,PlusCopyPersonInfo.ConvertToJson(personList));			
			}
		});
		
		structLayout.addView(searchLayout);
	}
	
	/** 
	* @Title: initLoweNodeAppSpinnerUser 
	* @Description: ��ʼ���ڵ���Ա��������ͼ
	* @param @param fUserNames Ա������+Ա����
	* @param @param fNodeName  �ڵ�����   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����11:42:07
	*/
	private void initLoweNodeAppSpinnerUser(final List<String> fUserNames,final String fNodeName){
		View spinnerUserLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_spinner_user, null);
		
		final MultiSelectionSpinner mSpinnerUser=(MultiSelectionSpinner)spinnerUserLayout.findViewById(R.id.lower_node_approve_spinner_user);
		mSpinnerUser.setItems(fUserNames);
		mSpinnerUser.setlNodeApprove(this);
		mSpinnerUser.setSpinnerType(spinnerUserType);
		mSpinnerUser.setSpinnerIndex(0);
		mSpinnerUser.setRoleSpinnerCount(1);
		mSpinnerUser.setFNodeName(fNodeName);
		mSpinnerUser.setResultView(fResultTView);
		
		spinnerTypeIndex=spinnerUserType+"_"+"0";
		LowerNodeAppSpinnerInfo lNodeAppSpinner=new LowerNodeAppSpinnerInfo();
		lNodeAppSpinner.setFSpinnerType(spinnerUserType);
		lNodeAppSpinner.setFSpinnerIndex(0);
		lNodeAppSpinner.setFRoleSpinnerCount(1);
		spinnerMap.put(spinnerTypeIndex, lNodeAppSpinner);
		
		structLayout.addView(spinnerUserLayout);
	}

	/** 
	* @Title: initLoweNodeAppSpinnerRole 
	* @Description: ��ʼ���ڵ��ɫ��������ͼ
	* @param @param fRoleIndex ��ɫ����������
	* @param @param fRoleCount ��ɫ����������
	* @param @param fRoleTitle ��ɫ���������
	* @param @param fRoleNames ��ɫ��������ʾ�ַ���ֵ     
	* @param @param fNodeName  �ڵ�����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����11:51:58
	*/
	private void initLoweNodeAppSpinnerRole(final int fRoleIndex,final int fRoleCount, final String fRoleTitle, final List<String> fRoleNames,final String fNodeName){
		View spinnerRoleLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_spinner_role, null);
		
		final TextView fRoleTitleTView=(TextView)spinnerRoleLayout.findViewById(R.id.lower_node_approve_spinner_role_title);
		fRoleTitleTView.setText(fRoleTitle);
		
		final MultiSelectionSpinner mSpinnerRole=(MultiSelectionSpinner)spinnerRoleLayout.findViewById(R.id.lower_node_approve_spinner_role);
		mSpinnerRole.setItems(fRoleNames);
		mSpinnerRole.setlNodeApprove(this);
		mSpinnerRole.setSpinnerType(spinnerRoleType);
		mSpinnerRole.setSpinnerIndex(fRoleIndex);
		mSpinnerRole.setRoleSpinnerCount(fRoleCount);
		mSpinnerRole.setFNodeName(fNodeName);
		mSpinnerRole.setResultView(fResultTView);
		
		spinnerTypeIndex=spinnerRoleType+"_"+String.valueOf(fRoleIndex);
		LowerNodeAppSpinnerInfo lNodeAppSpinner=new LowerNodeAppSpinnerInfo();
		lNodeAppSpinner.setFSpinnerType(spinnerRoleType);
		lNodeAppSpinner.setFSpinnerIndex(fRoleIndex);
		lNodeAppSpinner.setFRoleSpinnerCount(fRoleCount);
		spinnerMap.put(spinnerTypeIndex, lNodeAppSpinner);
		
		structLayout.addView(spinnerRoleLayout);
	}
	
	// ��������Աҳ��,�ص�����
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
	* @date 2014��11��12�� ����2:35:21
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
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����2:35:31
	*/
	private void getAttendPerson(final List<PlusCopyPersonInfo> personList){
		String selectResult="";
		StringBuffer sb = new StringBuffer();
		boolean foundOne = false;  
		for (PlusCopyPersonInfo item : personList) {
			if(foundOne){
        		sb.append(", ");  
        	}
        	foundOne=true;
        	sb.append(item.getFItemName()+"("+item.getFItemNumber()+")");
		}
		selectResult=sb.toString();
		fSearchItem.setText(selectResult);
		setSearchSelectResult(selectResult);
	}
	
	/** 
	* @Title: setSearchSelectResult 
	* @Description: ���������ı���ѡȡ���ı�ֵ
	* @param @param selectResult     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����2:56:50
	*/
	private void setSearchSelectResult(final String selectResult){
		String fNodeName=fSearchNodeName.getText().toString();
		LowerNodeAppResultInfo nodeResult=nodeValueMap.get(fNodeName);
		nodeResult.setFSelectResult(selectResult);
		nodeResult.setFShowResult(fNodeName+":"+selectResult);
		fResultTView.setText(MultiSelectionSpinner.getNodeValueMap(nodeValueMap));
	}
	
	/** 
	* @Title: setLowerNodeAppBack 
	* @Description: ����ѡȡ���¼��ڵ㷵��ֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����4:24:54
	*/
	@SuppressWarnings("rawtypes")
	private void setLowerNodeAppBack(){
		lBackList=new ArrayList<LowerNodeAppBackInfo>();
		//����HashMap
        Iterator it = nodeValueMap.entrySet().iterator(); 
        Map.Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppResultInfo lResultInfo =nodeValueMap.get(key);
        	LowerNodeAppBackInfo lBackInfo=new LowerNodeAppBackInfo();
        	lBackInfo.setFIsMust(lResultInfo.getFIsMust());
        	lBackInfo.setFNodeId(lResultInfo.getFNodeId());
        	lBackInfo.setFNodeName(lResultInfo.getFNodeName());
        	lBackInfo.setFSelectItem(parseSelectResult(lResultInfo.getFSelectResult()));
        	lBackList.add(lBackInfo);
        }  
	}
	
	/** 
	* @Title: parseSelectResult 
	* @Description: ����ѡ�еĽ���ַ���ֵ
	* @param @param selectResult
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��11��13�� ����10:16:09
	*/
	private String parseSelectResult(final String selectResult){
		String result="";
		if(!StringUtils.isEmpty(selectResult)){
			List<String> parseList=StringUtils.convertToList(selectResult, ",");
			if(parseList.size() > 0){
				StringBuilder sb = new StringBuilder();  
				boolean foundOne = false;    
				for (String item : parseList) {
					if (foundOne) {  
						sb.append(",");  
					}  
					foundOne = true;  
					int firstIndex=item.indexOf("(");  //��ʼ���ŵ�����ֵ
					int endIndex=item.indexOf(")");    //�������ŵ�����ֵ
					if(firstIndex > 0 && endIndex > 0){
						sb.append(item.substring(firstIndex+1, endIndex)); 
					}		
				}
				result=sb.toString();
			}
		}
		return result;
	}
	
	/** 
	* @Title: verify 
	* @Description: ȷ��֮ǰ��֤
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����4:32:49
	*/
	private boolean verify(){
		boolean flag=false;	
		if(lBackList.size() > 0){
			List<Boolean> returnList=new ArrayList<Boolean>();
			for (LowerNodeAppBackInfo item : lBackList) {
				if(item.getFIsMust()==1 && StringUtils.isEmpty(item.getFSelectItem())){  //˵���Ǳ�ѡ
					UIHelper.ToastMessage(LowerNodeApproveActivity.this, item.getFNodeName()+"Ϊ���߽ڵ�,����ѡ��ֵ");
					returnList.add(true);
					break;
				}
			}
			if(returnList.size() == 0){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * @ClassName passWorkflowAsync
	 * @Description �첽������������
	 * @author 21291
	 * @date 2014��11��12�� ����5:03:29
	 */
	private class passWorkflowAsync extends AsyncTask<String, Void, ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			appDialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(String... params) {
			return passWorkflowByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			renderPassWorkView(result);	
			appDialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: passWorkflowByPost 
	* @Description: �첽�����������
	* @param @param serviceUrl
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����5:05:32
	*/
	private ResultMessage passWorkflowByPost(final String serviceUrl){
		lConfig=LowerNodeAppConfigInfo.getLowerNodeAppConfigInfo();
		lConfig.setFAppKey(AppContext.WORKFLOW_NEWOFFICE_KEY);
		lConfig.setFSystemId(Integer.valueOf(fSystemId));
		lConfig.setFClassTypeId(fClassTypeId);
		lConfig.setFBillId(fBillId);
		lConfig.setFItemNumber(fItemNumber);
		
		lBusiness.setServiceUrl(serviceUrl);
		return lBusiness.passWorkFlowHandle(lConfig, lBackList);
	}
	
	/** 
	* @Title: renderPassWorkView 
	* @Description: ���ڽ������UI����
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����5:06:25
	*/
	private void renderPassWorkView(final ResultMessage resultMessage){
		UIHelper.ToastMessage(LowerNodeApproveActivity.this, resultMessage.getResult());
		sendLogs(); //������־��Ϣ����ͳ��
		
		// �ӳ�2�����ر�
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	finish();
            }
        }, 2000);	
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
		logInfo.setFActionName("nextnode");
		logInfo.setFNote(fBillName);
		UIHelper.sendLogs(LowerNodeApproveActivity.this,logInfo);
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
