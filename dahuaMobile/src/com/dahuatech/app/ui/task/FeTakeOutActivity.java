package com.dahuatech.app.ui.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.FeTakeOutTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.FeTakeOutBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName FeTakeOutActivity
 * @Description ӡ��������뵥��Activity
 * @author 21291
 * @date 2014��10��11�� ����4:54:05
 */
public class FeTakeOutActivity extends MenuActivity implements ITaskContext,ICheckNextNode{
	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fTel,fApplyDept,feName,feCode,fStartTime,fEndTime,feCarry,feCarryTel,fDestination;
	private TextView fProjectName,fSealData,feUseCarry,feUseDestination;
	private Button appButton,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fe_takeout_theader);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_FETAKEOUTACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}

		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, ""); 	//��ȡԱ����

		fSubLinearLayout=(LinearLayout)findViewById(R.id.fe_takeout_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(FeTakeOutActivity.this, FeTakeOutActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(FeTakeOutActivity.this, R.string.fetakeout_netparseerror);
			return;
		}
	}
	
	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(FeTakeOutActivity.this);
		FeTakeOutBusiness eBusiness= (FeTakeOutBusiness)factoryBusiness.getInstance("FeTakeOutBusiness",serviceUrl);
		return eBusiness.getFeTakeOutTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		FeTakeOutTHeaderInfo fInfo=(FeTakeOutTHeaderInfo)base;
		if(!StringUtils.isEmpty(fInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.fe_takeout_FBillNo);
			fBillNo.setText(fInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.fe_takeout_FApplyName);
			fApplyName.setText(fInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(fInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.fe_takeout_FApplyDate);
			fApplyDate.setText(fInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFTel())){
			fTel=(TextView)findViewById(R.id.fe_takeout_FTel);
			fTel.setText(fInfo.getFTel());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.fe_takeout_FApplyDept);
			fApplyDept.setText(fInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeCode())){
			feCode=(TextView)findViewById(R.id.fe_takeout_FeCode);
			feCode.setText(fInfo.getFeCode());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeName())){
			feName=(TextView)findViewById(R.id.fe_takeout_FeName);
			feName.setText(fInfo.getFeName());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFStartTime())){
			fStartTime=(TextView)findViewById(R.id.fe_takeout_FStartTime);
			fStartTime.setText(fInfo.getFStartTime());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFEndTime())){
			fEndTime=(TextView)findViewById(R.id.fe_takeout_FEndTime);
			fEndTime.setText(fInfo.getFEndTime());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeCarry())){
			feCarry=(TextView)findViewById(R.id.fe_takeout_FeCarry);
			feCarry.setText(fInfo.getFeCarry());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeCarryTel())){
			feCarryTel=(TextView)findViewById(R.id.fe_takeout_FeCarryTel);
			feCarryTel.setText(fInfo.getFeCarryTel());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFDestination())){
			fDestination=(TextView)findViewById(R.id.fe_takeout_FDestination);
			fDestination.setText(fInfo.getFDestination());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFProjectName())){
			fProjectName=(TextView)findViewById(R.id.fe_takeout_FProjectName);
			fProjectName.setText(fInfo.getFProjectName());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFSealData())){
			fSealData=(TextView)findViewById(R.id.fe_takeout_FSealData);
			fSealData.setText(fInfo.getFSealData());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeUseCarry())){
			feUseCarry=(TextView)findViewById(R.id.fe_takeout_FeUseCarry);
			feUseCarry.setText(fInfo.getFeUseCarry());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFeUseDestination())){
			feUseDestination=(TextView)findViewById(R.id.fe_takeout_FeUseDestination);
			feUseDestination.setText(fInfo.getFeUseDestination());
		}
		initApprove();
	}
	
	/** 
	* @Title: initApprove 
	* @Description: ����������ť��ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��13�� ����11:36:08
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		 if("1".equals(fStatus)){  //˵��������������
			 appButton.setText(R.string.approve_imgbtnApprove_record);
			 handleLayout.setVisibility(View.GONE);
		 }
		 else{
			 handleLayout.setVisibility(View.VISIBLE);
			 Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			 Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			 lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			 
			 //��ǩ����
			 plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(FeTakeOutActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fetakeout_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(FeTakeOutActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fetakeout_title));
				}
			 });
			 
			  //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(FeTakeOutActivity.this,FeTakeOutActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(FeTakeOutActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.fetakeout_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{ //��ʾ������ҳ��
					Intent intent = new Intent(FeTakeOutActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
		 	}
		 });
		
		 fSubLinearLayout.addView(buttonLayout);
	}
	
	@Override
	public void setCheckResult(ResultMessage resultMessage) {
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, FeTakeOutActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.fetakeout_title));
	}
	
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.ACTIVITY_WORKFLOW:
				if(resultCode==RESULT_OK){
					fStatus="1";  //�Ӵ�����ҳ����� ˵���������򲵻�ͨ��
					SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);					
					sp.edit().putString(AppContext.TA_APPROVE_FSTATUS, fStatus).commit();
					appButton.setText(R.string.approve_imgbtnApprove_record);
					
					handleLayout.setVisibility(View.GONE);
				}			
				break;
			case AppContext.ACTIVITY_WORKFLOWBEEN:
				break;
			default:
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(FeTakeOutActivity.this);
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
