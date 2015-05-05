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
import com.dahuatech.app.bean.mytask.MemRequreInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MemRequreBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName MemRequreActivity
 * @Description MEM���̵�������Activity
 * @author 21291
 * @date 2014��7��16�� ����5:03:46
 */
public class MemRequreActivity extends MenuActivity implements ITaskContext,ICheckNextNode{
	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyerName,fApplyerDeptName,fApplyDate,fVersion,fValidDate,fUpgradeReason,fOtherReason,fUpgradeNote,fSubject,fMemReasonNote,fMemProduct,fTechnology,fMemScope,fNote;
	private TaskParamInfo taskParam;  //������
	private Button btnApprove,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memrequre);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_MEMREQUREACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.mem_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(MemRequreActivity.this, MemRequreActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(MemRequreActivity.this, R.string.memrequre_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MemRequreActivity.this);
		MemRequreBusiness eBusiness= (MemRequreBusiness)factoryBusiness.getInstance("MemRequreBusiness",serviceUrl);
		return eBusiness.getMemRequreInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		MemRequreInfo meInfo=(MemRequreInfo)base;
		if(!StringUtils.isEmpty(meInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.mem_FBillNo);
			fBillNo.setText(meInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFApplyerName())){
			fApplyerName=(TextView)findViewById(R.id.mem_FApplyerName);
			fApplyerName.setText(meInfo.getFApplyerName());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.mem_FApplyDate);
			fApplyDate.setText(meInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFApplyerDeptName())){
			fApplyerDeptName=(TextView)findViewById(R.id.mem_FApplyerDeptName);
			fApplyerDeptName.setText(meInfo.getFApplyerDeptName());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFVersion())){
			fVersion=(TextView)findViewById(R.id.mem_FVersion);
			fVersion.setText(meInfo.getFVersion());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFValidDate())){
			fValidDate=(TextView)findViewById(R.id.mem_FValidDate);
			fValidDate.setText(meInfo.getFValidDate());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFUpgradeReason())){
			fUpgradeReason=(TextView)findViewById(R.id.mem_FUpgradeReason);
			fUpgradeReason.setText(meInfo.getFUpgradeReason());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFOtherReason())){
			fOtherReason=(TextView)findViewById(R.id.mem_FOtherReason);
			fOtherReason.setText(meInfo.getFOtherReason());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFUpgradeNote())){
			fUpgradeNote=(TextView)findViewById(R.id.mem_FUpgradeNote);
			fUpgradeNote.setText(meInfo.getFUpgradeNote());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFSubject())){
			fSubject=(TextView)findViewById(R.id.mem_FSubject);
			fSubject.setText(meInfo.getFSubject());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFMemReasonNote())){
			fMemReasonNote=(TextView)findViewById(R.id.mem_FMemReasonNote);
			fMemReasonNote.setText(meInfo.getFMemReasonNote());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFMemProduct())){
			fMemProduct=(TextView)findViewById(R.id.mem_FMemProduct);
			fMemProduct.setText(meInfo.getFMemProduct());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFTechnology())){
			fTechnology=(TextView)findViewById(R.id.mem_FTechnology);
			fTechnology.setText(meInfo.getFTechnology());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFMemScope())){
			fMemScope=(TextView)findViewById(R.id.mem_FMemScope);
			fMemScope.setText(meInfo.getFMemScope());
		}
		
		if(!StringUtils.isEmpty(meInfo.getFNote())){
			fNote=(TextView)findViewById(R.id.mem_FNote);
			fNote.setText(meInfo.getFNote());
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
	* @date 2014��8��13�� ����5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		//������ť
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 btnApprove=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		  
		 if("1".equals(fStatus)){  //˵��������������
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
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
					UIHelper.showPlusCopy(MemRequreActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.memrequre_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(MemRequreActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.memrequre_title));
				}
			 });
			 
			  //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(MemRequreActivity.this,MemRequreActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 btnApprove.setOnClickListener(new OnClickListener() {
			 @Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(MemRequreActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.memrequre_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(MemRequreActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, MemRequreActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.memrequre_title));
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
					btnApprove.setText(R.string.approve_imgbtnApprove_record);
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
		commonMenu.setContext(MemRequreActivity.this);
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
