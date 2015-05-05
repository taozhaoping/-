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
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.DevelopTravelTBodyOneInfo;
import com.dahuatech.app.bean.mytask.DevelopTravelTBodyTwoInfo;
import com.dahuatech.app.bean.mytask.DevelopTravelTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.DevelopTravelBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DevelopTravelActivity
 * @Description �з�������ǲ����Activity
 * @author 21291
 * @date 2014��8��15�� ����9:43:48
 */
public class DevelopTravelActivity extends MenuActivity implements ITaskContext,ICheckNextNode {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fAssumeCost,fAssumeDept,fTravelAim,fTravelAddress,fTravelStartTime,fTravelEndTime,fProjectName,fProjectCode,fTravelReason,fTravelWay;
	private Button appButton,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	private int fSubEntrysOneCount=1;
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.develop_travel_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DEVELOPTRAVELACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.develop_travel_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(DevelopTravelActivity.this, DevelopTravelActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(DevelopTravelActivity.this, R.string.dveloptravel_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DevelopTravelActivity.this);
		DevelopTravelBusiness eBusiness= (DevelopTravelBusiness)factoryBusiness.getInstance("DevelopTravelBusiness",serviceUrl);
		return eBusiness.getDevelopTravelTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		DevelopTravelTHeaderInfo dInfo=(DevelopTravelTHeaderInfo)base;
		if(!StringUtils.isEmpty(dInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.develop_travel_FBillNo);
			fBillNo.setText(dInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.develop_travel_FApplyName);
			fApplyName.setText(dInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.develop_travel_FApplyDate);
			fApplyDate.setText(dInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFAssumeCost())){
			fAssumeCost=(TextView)findViewById(R.id.develop_travel_FAssumeCost);
			fAssumeCost.setText(dInfo.getFAssumeCost());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFAssumeDept())){
			fAssumeDept=(TextView)findViewById(R.id.develop_travel_FAssumeDept);
			fAssumeDept.setText(dInfo.getFAssumeDept());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFTravelAim())){
			fTravelAim=(TextView)findViewById(R.id.develop_travel_FTravelAim);
			fTravelAim.setText(dInfo.getFTravelAim());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFTravelAddress())){
			fTravelAddress=(TextView)findViewById(R.id.develop_travel_FTravelAddress);
			fTravelAddress.setText(dInfo.getFTravelAddress());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFTravelStartTime())){
			fTravelStartTime=(TextView)findViewById(R.id.develop_travel_FTravelStartTime);
			fTravelStartTime.setText(dInfo.getFTravelStartTime());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFTravelEndTime())){
			fTravelEndTime=(TextView)findViewById(R.id.develop_travel_FTravelEndTime);
			fTravelEndTime.setText(dInfo.getFTravelEndTime());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFProjectCode())){
			fProjectCode=(TextView)findViewById(R.id.develop_travel_FProjectCode);
			fProjectCode.setText(dInfo.getFProjectCode());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFProjectName())){
			fProjectName=(TextView)findViewById(R.id.develop_travel_FProjectName);
			fProjectName.setText(dInfo.getFProjectName());
		}
		if(!StringUtils.isEmpty(dInfo.getFTravelReason())){
			fTravelReason=(TextView)findViewById(R.id.develop_travel_FTravelReason);
			fTravelReason.setText(dInfo.getFTravelReason());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFTravelWay())){
			fTravelWay=(TextView)findViewById(R.id.develop_travel_FTravelWay);
			fTravelWay.setText(dInfo.getFTravelWay());
		}

		if(!StringUtils.isEmpty(dInfo.getFSubEntrysOne())){
			showSubEntrysOne(dInfo.getFSubEntrysOne());
		}
		
		if(!StringUtils.isEmpty(dInfo.getFSubEntrysTwo())){
			showSubEntrysTwo(dInfo.getFSubEntrysTwo());
		}
		initApprove();
	}
	
	/** 
	* @Title: showSubEntrysOne 
	* @Description: �������༯��1
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����10:08:12
	*/
	private void showSubEntrysOne(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<DevelopTravelTBodyOneInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DevelopTravelTBodyOneInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				fSubEntrysOneCount=tBodyInfos.size()+1;
				for (DevelopTravelTBodyOneInfo item : tBodyInfos) {
					attachSubEntryOne(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: showSubEntrysTwo 
	* @Description: �������༯��2
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����10:08:31
	*/
	private void showSubEntrysTwo(final String fSubEntrys){
		try {
			int i=fSubEntrysOneCount;
			Type listType = new TypeToken<ArrayList<DevelopTravelTBodyTwoInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DevelopTravelTBodyTwoInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (DevelopTravelTBodyTwoInfo item : tBodyInfos) {
					attachSubEntryTwo(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/** 
	* @Title: attachSubEntryOne 
	* @Description: ��̬����������ͼ1
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����10:09:48
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntryOne(final DevelopTravelTBodyOneInfo tBodyInfo,final int i){
		
		View linearLayoutOne = LayoutInflater.from(this).inflate(R.layout.develop_travel_tbody_one, null);
		 
		TextView fLine=(TextView)linearLayoutOne.findViewById(R.id.dveloptravel_one_body_FLine);
		fLine.setText(String.valueOf(i));
		 
		TextView fSchedule=(TextView)linearLayoutOne.findViewById(R.id.dveloptravel_one_body_FSchedule);
		fSchedule.setText(tBodyInfo.getFSchedule());
		
		TextView fStartTime=(TextView)linearLayoutOne.findViewById(R.id.dveloptravel_one_body_FStartTime);
		fStartTime.setText(tBodyInfo.getFStartTime());
		
		TextView fEndTime=(TextView)linearLayoutOne.findViewById(R.id.dveloptravel_one_body_FEndTime);
		fEndTime.setText(tBodyInfo.getFEndTime());
		
		TextView fNote=(TextView)linearLayoutOne.findViewById(R.id.dveloptravel_one_body_FNote);
		fNote.setText(tBodyInfo.getFNote());
 
		fSubLinearLayout.addView(linearLayoutOne);
	}
	
	/** 
	* @Title: attachSubEntryTwo 
	* @Description: ��̬����������ͼ2
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����10:10:28
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntryTwo(final DevelopTravelTBodyTwoInfo tBodyInfo,final int i){
		View linearLayoutTwo = LayoutInflater.from(this).inflate(R.layout.develop_travel_tbody_two, null);
		 
		TextView fLine=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FLine);
		fLine.setText(String.valueOf(i));
		 
		TextView fTravelName=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FTravelName);
		fTravelName.setText(tBodyInfo.getFTravelName());
		
		TextView fTravelDept=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FTravelDept);
		fTravelDept.setText(tBodyInfo.getFTravelDept());
		
		TextView fDeptManager=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FDeptManager);
		fDeptManager.setText(tBodyInfo.getFDeptManager());
		
		TextView fStartTime=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FStartTime);
		fStartTime.setText(tBodyInfo.getFStartTime());
		
		TextView fEndTime=(TextView)linearLayoutTwo.findViewById(R.id.dveloptravel_two_body_FEndTime);
		fEndTime.setText(tBodyInfo.getFEndTime());
 
		fSubLinearLayout.addView(linearLayoutTwo);
	}
	
	/** 
	* @Title: initApprove 
	* @Description: ����������ť��ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��13�� ����11:08:30
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
					UIHelper.showPlusCopy(DevelopTravelActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.dveloptravel_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopTravelActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.dveloptravel_title));
				}
			 });
			 
			 //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(DevelopTravelActivity.this,DevelopTravelActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(DevelopTravelActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.dveloptravel_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(DevelopTravelActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, DevelopTravelActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.dveloptravel_title));
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
		commonMenu.setContext(DevelopTravelActivity.this);
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
