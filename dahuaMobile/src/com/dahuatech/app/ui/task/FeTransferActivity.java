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
import com.dahuatech.app.bean.mytask.FeTransferTBodyInfo;
import com.dahuatech.app.bean.mytask.FeTransferTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.FeTransferBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName FeTransferActivity
 * @Description ӡ���ƽ����뵥��Activity
 * @author 21291
 * @date 2014��10��13�� ����10:03:19
 */
public class FeTransferActivity extends MenuActivity implements ITaskContext,ICheckNextNode{
	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fTel,fApplyDept,fnKeeper,fnKeeperTel,fnKeeperDept,fnKeeperArea,fAmount;
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
		setContentView(R.layout.fe_transfer_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_FETRANSFERACTIVITY;	
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

		fSubLinearLayout=(LinearLayout)findViewById(R.id.fe_transfer_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(FeTransferActivity.this, FeTransferActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(FeTransferActivity.this, R.string.fetransfer_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(FeTransferActivity.this);
		FeTransferBusiness eBusiness= (FeTransferBusiness)factoryBusiness.getInstance("FeTransferBusiness",serviceUrl);
		return eBusiness.getFeTransferTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		FeTransferTHeaderInfo fInfo=(FeTransferTHeaderInfo)base;
		if(!StringUtils.isEmpty(fInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.fe_transfer_FBillNo);
			fBillNo.setText(fInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.fe_transfer_FApplyName);
			fApplyName.setText(fInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(fInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.fe_transfer_FApplyDate);
			fApplyDate.setText(fInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFTel())){
			fTel=(TextView)findViewById(R.id.fe_transfer_FTel);
			fTel.setText(fInfo.getFTel());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.fe_transfer_FApplyDept);
			fApplyDept.setText(fInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFnKeeper())){
			fnKeeper=(TextView)findViewById(R.id.fe_transfer_FnKeeper);
			fnKeeper.setText(fInfo.getFnKeeper());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFnKeeperTel())){
			fnKeeperTel=(TextView)findViewById(R.id.fe_transfer_FnKeeperTel);
			fnKeeperTel.setText(fInfo.getFnKeeperTel());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFnKeeperDept())){
			fnKeeperDept=(TextView)findViewById(R.id.fe_transfer_FnKeeperDept);
			fnKeeperDept.setText(fInfo.getFnKeeperDept());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFnKeeperArea())){
			fnKeeperArea=(TextView)findViewById(R.id.fe_transfer_FnKeeperArea);
			fnKeeperArea.setText(fInfo.getFnKeeperArea());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFAmount())){
			fAmount=(TextView)findViewById(R.id.fe_transfer_FAmount);
			fAmount.setText(fInfo.getFAmount());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFSubEntrys())){
			showSubEntrys(fInfo.getFSubEntrys());
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
	* @date 2014��10��13�� ����10:14:24
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<FeTransferTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<FeTransferTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (FeTransferTBodyInfo item : tBodyInfos) {
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
	* @date 2014��10��13�� ����10:14:40
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final FeTransferTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.fe_transfer_tbody, null);
		 
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView feType=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FeType);
		 feType.setText(tBodyInfo.getFeType());
		 
		 TextView feCode=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FeCode);
		 feCode.setText(tBodyInfo.getFeCode());
		 
		 TextView feName=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FeName);
		 feName.setText(tBodyInfo.getFeName());
		 
		 TextView fCompany=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FCompany);
		 fCompany.setText(tBodyInfo.getFCompany());
		 
		 TextView fStatus=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FStatus);
		 fStatus.setText(tBodyInfo.getFStatus());
		 
		 TextView fKeeper=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FKeeper);
		 fKeeper.setText(tBodyInfo.getFKeeper());
		 
		 TextView fKeeperDept=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FKeeperDept);
		 fKeeperDept.setText(tBodyInfo.getFKeeperDept());
		 
		 TextView fKeeperArea=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FKeeperArea);
		 fKeeperArea.setText(tBodyInfo.getFKeeperArea());	 
		 
		 TextView fReason=(TextView)linearLayout.findViewById(R.id.fe_transfer_body_FReason);
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
					UIHelper.showPlusCopy(FeTransferActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fetransfer_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(FeTransferActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fetransfer_title));
				}
			 });
			 
			  //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(FeTransferActivity.this,FeTransferActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(FeTransferActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.fetransfer_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{ //��ʾ������ҳ��
					Intent intent = new Intent(FeTransferActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, FeTransferActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.fetransfer_title));
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
		commonMenu.setContext(FeTransferActivity.this);
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
