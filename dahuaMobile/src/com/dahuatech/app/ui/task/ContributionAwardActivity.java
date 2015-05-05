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
import com.dahuatech.app.bean.mytask.ContributionAwardInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ContributionAwardBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MemRequreBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * ���ڹ��׽�����������
 * 
 * @Title: ContributionAwardActivity.java
 * @Package com.dahuatech.app.ui.task
 * @Description: TODO
 * @date 2015-3-16 ����4:10:54
 * @author taozhaoping 26078
 * @author mail taozhaoping@gmail.com
 * @version V1.0
 */
@SuppressLint("CutPasteId")
public class ContributionAwardActivity extends MenuActivity implements ITaskContext {

	private String fMenuID, fSystemType, fBillID, fClassTypeId, fStatus,
			fItemNumber;
	private TextView fBillNo,fBillerName,fApplyDate,fBillerDeptName,fDate,fInteger,fAmount,fText,fText2,fCheckBox;
	private Button btnApprove;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	private TaskParamInfo taskParam;  //������
	private String serviceUrl; // �����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp; // ��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.contributionawardactivity);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ��ʼ��ȫ�ֱ���
		appContext = (AppContext) getApplication();
		// ��������
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}

		// ��ȡ�����ַ
		serviceUrl = AppUrl.URL_API_HOST_ANDROID_CONTRIBUTIONAWARDACTIVITY;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			fMenuID = extras.getString(AppContext.FMENUID_KEY);
			fSystemType = extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID = extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId = extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus = extras.getString(AppContext.FSTATUS_KEY);
		}
		

		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,
				MODE_PRIVATE);
		fItemNumber = sp.getString(AppContext.USER_NAME_KEY, ""); // ��ȡԱ����
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.contributionaward_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ContributionAwardActivity.this, ContributionAwardActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ContributionAwardActivity.this, R.string.contributionaward_netparseerror);
			return;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ContributionAwardActivity.this);
		ContributionAwardBusiness eBusiness= (ContributionAwardBusiness)factoryBusiness.getInstance("ContributionAwardBusiness",serviceUrl);
		return eBusiness.getContributionAwardInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		// ��ʼ��ҳ�����
 		ContributionAwardInfo contributionAwardInfo = (ContributionAwardInfo) base;
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFBillNo())){
			fBillNo = (TextView)findViewById(R.id.contributionaward_FBillNo);
			fBillNo.setText(contributionAwardInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFBillerName())){
			fBillerName = (TextView)findViewById(R.id.contributionaward_FBillerName);
			fBillerName.setText(contributionAwardInfo.getFBillerName());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFApplyDate())){
			fApplyDate = (TextView)findViewById(R.id.contributionaward_FApplyDate);
			fApplyDate.setText(contributionAwardInfo.getFApplyDate());
		}
	
		if(!StringUtils.isEmpty(contributionAwardInfo.getFBillerDeptName())){
			fBillerDeptName = (TextView)findViewById(R.id.contributionaward_FBillerDeptName);
			fBillerDeptName.setText(contributionAwardInfo.getFBillerDeptName());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFDate())){
			fDate = (TextView)findViewById(R.id.contributionaward_FDate);
			fDate.setText(contributionAwardInfo.getFDate());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFInteger())){
			fInteger = (TextView)findViewById(R.id.contributionaward_FInteger);
			fInteger.setText(contributionAwardInfo.getFInteger());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFAmount())){
			fAmount = (TextView)findViewById(R.id.contributionaward_FAmount);
			fAmount.setText(contributionAwardInfo.getFAmount());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFText())){
			fText = (TextView)findViewById(R.id.contributionaward_FText);
			fText.setText(contributionAwardInfo.getFText());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFText2())){
			fText2 = (TextView)findViewById(R.id.contributionaward_FText2);
			fText2.setText(contributionAwardInfo.getFText2());
		}
		
		if(!StringUtils.isEmpty(contributionAwardInfo.getFCheckBox())){
			fCheckBox = (TextView)findViewById(R.id.contributionaward_FCheckBox);
			fCheckBox.setText(contributionAwardInfo.getFCheckBox());
		}
		
		//�ؼ���ʼ��
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
		 }

		 handleLayout.setVisibility(View.GONE);
		 
		 //��������
		 btnApprove.setOnClickListener(new OnClickListener() {
			 @Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ContributionAwardActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.contributionaward_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(ContributionAwardActivity.this, WorkFlowBeenActivity.class);
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
					}			
					break;
				case AppContext.ACTIVITY_WORKFLOWBEEN:
					break;
				default:
					break;
			}
		}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		commonMenu.setContext(ContributionAwardActivity.this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		return super.onKeyDown(keyCode, event);
	}

}
