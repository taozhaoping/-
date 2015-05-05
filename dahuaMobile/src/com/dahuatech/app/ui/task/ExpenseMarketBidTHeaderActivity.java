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
import com.dahuatech.app.bean.mytask.ExpenseMarketBidTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExpenseMarketBidTHeaderBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseMarketBidTHeaderActivity
 * @Description �г�Ͷ�걨������Activity
 * @author 21291
 * @date 2014��6��25�� ����2:34:55
 */
public class ExpenseMarketBidTHeaderActivity extends MenuActivity implements ITaskContext{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fConSmName,fCommitDate,fAmountAll,fPubPayNo,fRecAccName,fProjectName,fConSmTypeName,fCaseType,fSetOffType;
	private TaskParamInfo taskParam;  //������
	private Button btnApprove;	//������ť
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private AsyncTaskContext aTaskContext;		//�첽���������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expensemarketbidtheader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEMARKETBIDTHEADERACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}	
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.expensemarketbidtheader_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExpenseMarketBidTHeaderActivity.this, ExpenseMarketBidTHeaderActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExpenseMarketBidTHeaderActivity.this, R.string.expensemarketbidtheader_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseMarketBidTHeaderActivity.this);	
		ExpenseMarketBidTHeaderBusiness eBusiness= (ExpenseMarketBidTHeaderBusiness)factoryBusiness.getInstance("ExpenseMarketBidTHeaderBusiness",serviceUrl);
		return eBusiness.getExpenseMarketBidTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ExpenseMarketBidTHeaderInfo exMarketBid=(ExpenseMarketBidTHeaderInfo)base;
		if(!StringUtils.isEmpty(exMarketBid.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.expensemarketbidtheader_FBillNo);
			fBillNo.setText(exMarketBid.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFConSmName())){
			fConSmName=(TextView)findViewById(R.id.expensemarketbidtheader_FConSmName);
			fConSmName.setText(exMarketBid.getFConSmName());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFCommitDate())){
			fCommitDate=(TextView)findViewById(R.id.expensemarketbidtheader_FCommitDate);
			fCommitDate.setText(exMarketBid.getFCommitDate());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFAmountAll())){
			fAmountAll=(TextView)findViewById(R.id.expensemarketbidtheader_FAmountAll);
			fAmountAll.setText(exMarketBid.getFAmountAll());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFPubPayNo())){
			fPubPayNo=(TextView)findViewById(R.id.expensemarketbidtheader_FPubPayNo);
			fPubPayNo.setText(exMarketBid.getFPubPayNo());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFRecAccName())){
			fRecAccName=(TextView)findViewById(R.id.expensemarketbidtheader_FRecAccName);
			fRecAccName.setText(exMarketBid.getFRecAccName());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFProjectName())){
			fProjectName=(TextView)findViewById(R.id.expensemarketbidtheader_FProjectName);
			fProjectName.setText(exMarketBid.getFProjectName());
		}
		
		if(!StringUtils.isEmpty(exMarketBid.getFConSmTypeName())){
			fConSmTypeName=(TextView)findViewById(R.id.expensemarketbidtheader_FConSmTypeName);
			fConSmTypeName.setText(exMarketBid.getFConSmTypeName());
		}	
		
		if(!StringUtils.isEmpty(exMarketBid.getFCaseType())){
			fCaseType=(TextView)findViewById(R.id.expensemarketbidtheader_FCaseType);
			fCaseType.setText(exMarketBid.getFCaseType());
		}	
		
		if(!StringUtils.isEmpty(exMarketBid.getFSetOffType())){
			fSetOffType=(TextView)findViewById(R.id.expensemarketbidtheader_FSetOffType);
			fSetOffType.setText(exMarketBid.getFSetOffType());
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
		 handleLayout.setVisibility(View.GONE);
		 btnApprove=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
			
		 if("1".equals(fStatus)){  //˵��������������
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
		 }
		 btnApprove.setOnClickListener(new OnClickListener() {
			 
			 @Override
		 	 public void onClick(View v) {
				 if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ExpenseMarketBidTHeaderActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.expensemarketbidtheader_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				 } 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(ExpenseMarketBidTHeaderActivity.this, WorkFlowBeenActivity.class);
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
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpenseMarketBidTHeaderActivity.this);
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
