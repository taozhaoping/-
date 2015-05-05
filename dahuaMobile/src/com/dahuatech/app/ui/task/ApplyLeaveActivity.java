package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

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
import com.dahuatech.app.bean.mytask.ApplyLeaveTBodyInfo;
import com.dahuatech.app.bean.mytask.ApplyLeaveTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyLeaveBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ApplyLeaveActivity
 * @Description ������뵥��
 * @author 21291
 * @date 2015��1��12�� ����9:44:33
 */
public class ApplyLeaveActivity extends MenuActivity implements ITaskContext {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept;
	private Button appButton;	//������ť
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private AsyncTaskContext aTaskContext;		//�첽���������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_leave_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYLEAVEACTIVITY;
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
			
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applyleavetheader_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ApplyLeaveActivity.this, ApplyLeaveActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyLeaveActivity.this, R.string.applyleave_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyLeaveActivity.this);
		ApplyLeaveBusiness eBusiness= (ApplyLeaveBusiness)factoryBusiness.getInstance("ApplyLeaveBusiness",serviceUrl);
		return eBusiness.getApplyLeaveTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ApplyLeaveTHeaderInfo applyInfo=(ApplyLeaveTHeaderInfo)base;
		if(!StringUtils.isEmpty(applyInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applyleavetheader_FBillNo);
			fBillNo.setText(applyInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applyleavetheader_FApplyName);
			fApplyName.setText(applyInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applyleavetheader_FApplyDate);
			fApplyDate.setText(applyInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.applyleavetheader_FApplyDept);
			fApplyDept.setText(applyInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFSubEntrys())){
			showSubEntrys(applyInfo.getFSubEntrys());
		}
		initApprove();
	}
	
	/** 
	* @Title: showSubEntrys 
	* @Description: �������༯��
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��12�� ����9:52:37
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<ApplyLeaveTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ApplyLeaveTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ApplyLeaveTBodyInfo item : tBodyInfos) {
					attachSubEntry(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachSubEntry 
	* @Description: ��̬����������ͼ
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��12�� ����9:54:18
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final ApplyLeaveTBodyInfo tBodyInfo,final int i){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.apply_leave_tbody, null);
	
		TextView fLine=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FLine);
		fLine.setText(String.valueOf(i));
 
		TextView fHolidayType=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FHolidayType);
		fHolidayType.setText(tBodyInfo.getFHolidayType());
 
		TextView fStartDate=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FStartDate);
		fStartDate.setText(tBodyInfo.getFStartDate());
		
		TextView fStartTime=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FStartTime);
		fStartTime.setText(tBodyInfo.getFStartTime());
	
		TextView fEndDate=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FEndDate);
		fEndDate.setText(tBodyInfo.getFEndDate());
		
		TextView fEndTime=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FEndTime);
		fEndTime.setText(tBodyInfo.getFEndTime());
		
		TextView fTimeLength=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FTimeLength);
		fTimeLength.setText(tBodyInfo.getFTimeLength());
		
		TextView fReason=(TextView)linearLayout.findViewById(R.id.apply_leave_tbody_FReason);
		fReason.setText(tBodyInfo.getFReason());
		
		fSubLinearLayout.addView(linearLayout);
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
		View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout);
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		handleLayout.setVisibility(View.GONE);
		if("1".equals(fStatus)){  //˵��������������
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		
		appButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ApplyLeaveActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applyleave_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//��ʾ������ҳ��
					Intent intent = new Intent(ApplyLeaveActivity.this, WorkFlowBeenActivity.class);
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
					appButton.setText(R.string.approve_imgbtnApprove_record);
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
		commonMenu.setContext(ApplyLeaveActivity.this);
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
