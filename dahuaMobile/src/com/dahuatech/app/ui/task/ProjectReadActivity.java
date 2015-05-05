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
import com.dahuatech.app.bean.mytask.ProjectReadTBodyInfo;
import com.dahuatech.app.bean.mytask.ProjectReadTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.ProjectReadBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ProjectReadActivity
 * @Description ��Ŀ��Ϣ�Ķ�Ȩ�����뵥��Activity
 * @author 21291
 * @date 2014��9��23�� ����2:53:36
 */
public class ProjectReadActivity extends MenuActivity implements ITaskContext,ICheckNextNode{	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fTel,fPermissionType,fProjectManage,fProgramPublic;
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
		setContentView(R.layout.project_read_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_PROJECTREADACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.project_read_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ProjectReadActivity.this, ProjectReadActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ProjectReadActivity.this, R.string.projectread_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ProjectReadActivity.this);
		ProjectReadBusiness eBusiness= (ProjectReadBusiness)factoryBusiness.getInstance("ProjectReadBusiness",serviceUrl);
		return eBusiness.getProjectReadTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ProjectReadTHeaderInfo pInfo=(ProjectReadTHeaderInfo)base;
		if(!StringUtils.isEmpty(pInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.project_read_FBillNo);
			fBillNo.setText(pInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.project_read_FApplyName);
			fApplyName.setText(pInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(pInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.project_read_FApplyDate);
			fApplyDate.setText(pInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.project_read_FApplyDept);
			fApplyDept.setText(pInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFTel())){
			fTel=(TextView)findViewById(R.id.project_read_FTel);
			fTel.setText(pInfo.getFTel());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFPermissionType())){
			fPermissionType=(TextView)findViewById(R.id.project_read_FPermissionType);
			fPermissionType.setText(pInfo.getFPermissionType());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFProjectManage())){
			fProjectManage=(TextView)findViewById(R.id.project_read_FProjectManage);
			fProjectManage.setText(pInfo.getFProjectManage());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFProgramPublic())){
			fProgramPublic=(TextView)findViewById(R.id.project_read_FProgramPublic);
			fProgramPublic.setText(pInfo.getFProgramPublic());
		}
		
		if(!StringUtils.isEmpty(pInfo.getFSubEntrys())){
			showSubEntrys(pInfo.getFSubEntrys());
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
	* @date 2014��9��23�� ����3:01:14
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<ProjectReadTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ProjectReadTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ProjectReadTBodyInfo item : tBodyInfos) {
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
	* @date 2014��8��28�� ����5:26:40
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final ProjectReadTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.project_read_tbody, null);
		
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView fApplyNumber=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FApplyNumber);
		 fApplyNumber.setText(tBodyInfo.getFApplyNumber());
		 
		 TextView fApplyName=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FApplyName);
		 fApplyName.setText(tBodyInfo.getFApplyName());
		 
		 TextView fApplyDept=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FApplyDept);
		 fApplyDept.setText(tBodyInfo.getFApplyDept());
		 
		 TextView fEmail=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FEmail);
		 fEmail.setText(tBodyInfo.getFEmail());
		 
		 TextView fApplyReason=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FApplyReason);
		 fApplyReason.setText(tBodyInfo.getFApplyReason());
		 
		 TextView fApplyType=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FApplyType);
		 fApplyType.setText(tBodyInfo.getFApplyType());
		 
		 TextView fProgramPublic=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FProgramPublic);
		 fProgramPublic.setText(tBodyInfo.getFProgramPublic());
		 
		 TextView fStartProject=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FStartProject);
		 fStartProject.setText(tBodyInfo.getFStartProject());
		 
		 TextView fEndProject=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FEndProject);
		 fEndProject.setText(tBodyInfo.getFEndProject());
		 
		 TextView fProductPublic=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FProductPublic);
		 fProductPublic.setText(tBodyInfo.getFProductPublic());
		 
		 TextView fMarketPublic=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FMarketPublic);
		 fMarketPublic.setText(tBodyInfo.getFMarketPublic());
		 
		 TextView fRisk=(TextView)linearLayout.findViewById(R.id.project_read_tbody_FRisk);
		 fRisk.setText(tBodyInfo.getFRisk());
		 
		 fSubLinearLayout.addView(linearLayout);
	}
	
	/** 
	* @Title: initApprove 
	* @Description: ����������ť��ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��27�� ����10:24:55
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
		 else {
			 handleLayout.setVisibility(View.VISIBLE);
			 Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			 Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			 lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			 
			 //��ǩ����
			 plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(ProjectReadActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.projectread_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(ProjectReadActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.projectread_title));
				}
			 });
			 
			 //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(ProjectReadActivity.this,ProjectReadActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ProjectReadActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.projectread_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(ProjectReadActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, ProjectReadActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.projectread_title));
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
		commonMenu.setContext(ProjectReadActivity.this);
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
