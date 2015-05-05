package com.dahuatech.app.ui.task;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.dahuatech.app.bean.mytask.ApplyDaysOffDevelopInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyDaysOffDevelopBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ApplyDaysOffDevelopActivity
 * @Description �з����ŵ������뵥��Activity
 * @author 21291
 * @date 2014��7��23�� ����3:23:08
 */
public class ApplyDaysOffDevelopActivity extends MenuActivity {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fTypeName,fSumDays,fStartTime,fEndTime,fReason;
	private ProgressDialog dialog;    //������
	private TaskParamInfo taskParam;  //������
	private Button btnApprove; 	 	  //������ť
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private String serviceUrl;  	  //�����ַ
	private AppContext appContext;	  // ȫ��Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applydaysoff_develop);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYDAYSOFFDEVELOPACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applydaysoffdevelop_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);			
			new ApplyDaysOffAsync().execute(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyDaysOffDevelopActivity.this, R.string.applydaysoffdevelop_netparseerror);
			return;
		}
	}
	
	/**
	 * @ClassName ApplyDaysOffAsync
	 * @Description �첽ִ������,��ȡ����ʵ��
	 * @author 21291
	 * @date 2014��7��25�� ����2:58:35
	 */
	private class ApplyDaysOffAsync extends AsyncTask<String, integer, ApplyDaysOffDevelopInfo> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ��ʾ�ȴ���
			dialog.show();
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ApplyDaysOffDevelopInfo result) {
			super.onPostExecute(result);
			initApplyDaysOffDevelopInfo(result);
			// ���ٵȴ���
			dialog.dismiss();
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ApplyDaysOffDevelopInfo doInBackground(String... params) {
			return getDataByPost(params[0]);
		}
	}
	
	/** 
	* @Title: getDataByPost 
	* @Description: �첽��ȡʵ����Ϣ
	* @param @param serviceUrl
	* @param @return     
	* @return ApplyDaysOffDevelopInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����2:58:56
	*/
	private ApplyDaysOffDevelopInfo getDataByPost(String serviceUrl){
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyDaysOffDevelopActivity.this);
		ApplyDaysOffDevelopBusiness eBusiness= (ApplyDaysOffDevelopBusiness)factoryBusiness.getInstance("ApplyDaysOffDevelopBusiness",serviceUrl);
		return eBusiness.getApplyDaysOffDevelopInfo(taskParam);
	}
	
	/** 
	* @Title: initApplyDaysOffDevelopInfo 
	* @Description: �з����ŵ������뵥�ݳ�ʼ��
	* @param @param adeInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����3:00:02
	*/
	private void initApplyDaysOffDevelopInfo(ApplyDaysOffDevelopInfo adInfo){
		if(!StringUtils.isEmpty(adInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applydaysoffdevelop_FBillNo);
			fBillNo.setText(adInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyName);
			fApplyName.setText(adInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyDate);
			fApplyDate.setText(adInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyDept);
			fApplyDept.setText(adInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFTypeName())){
			fTypeName=(TextView)findViewById(R.id.applydaysoffdevelop_FTypeName);
			fTypeName.setText(adInfo.getFTypeName());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFStartTime())){
			fStartTime=(TextView)findViewById(R.id.applydaysoffdevelop_FStartTime);
			fStartTime.setText(adInfo.getFStartTime());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFEndTime())){
			fEndTime=(TextView)findViewById(R.id.applydaysoffdevelop_FEndTime);
			fEndTime.setText(adInfo.getFEndTime());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFSumDays())){
			fSumDays=(TextView)findViewById(R.id.applydaysoffdevelop_FSumDays);
			fSumDays.setText(adInfo.getFSumDays());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFReason())){
			fReason=(TextView)findViewById(R.id.applydaysoffdevelop_FReason);
			fReason.setText(adInfo.getFReason());
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
		 handleLayout.setVisibility(View.GONE);
		 if("1".equals(fStatus)){  //˵��������������
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
		 }
		 
		 btnApprove.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ApplyDaysOffDevelopActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applydaysoffdevelop_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//��ʾ������ҳ��
					Intent intent = new Intent(ApplyDaysOffDevelopActivity.this, WorkFlowBeenActivity.class);
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
		commonMenu.setContext(ApplyDaysOffDevelopActivity.this);
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
