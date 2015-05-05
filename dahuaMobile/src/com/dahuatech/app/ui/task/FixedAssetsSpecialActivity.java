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
import com.dahuatech.app.bean.mytask.FixedAssetsSpecialTBodyInfo;
import com.dahuatech.app.bean.mytask.FixedAssetsSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.FixedAssetsSpecialBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName FixedAssetsSpecialActivity
 * @Description �̶��ʲ���������ɹ����󵥾�Activity
 * @author 21291
 * @date 2014��8��19�� ����2:01:58
 */
public class FixedAssetsSpecialActivity extends MenuActivity implements ITaskContext,ICheckNextNode{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fRequireType,fReason,fApplyCause;
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
		setContentView(R.layout.fixed_assets_special_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_FIXEDASSETSSPECIALACTIVITY;	
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

		fSubLinearLayout=(LinearLayout)findViewById(R.id.fixed_assets_special_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(FixedAssetsSpecialActivity.this, FixedAssetsSpecialActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(FixedAssetsSpecialActivity.this, R.string.fixedassetsspecial_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(FixedAssetsSpecialActivity.this);
		FixedAssetsSpecialBusiness eBusiness= (FixedAssetsSpecialBusiness)factoryBusiness.getInstance("FixedAssetsSpecialBusiness",serviceUrl);
		return eBusiness.getFixedAssetsSpecialTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		FixedAssetsSpecialTHeaderInfo fInfo=(FixedAssetsSpecialTHeaderInfo)base;
		if(!StringUtils.isEmpty(fInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.fixed_assets_special_FBillNo);
			fBillNo.setText(fInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.fixed_assets_special_FApplyName);
			fApplyName.setText(fInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.fixed_assets_special_FApplyDate);
			fApplyDate.setText(fInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.fixed_assets_special_FApplyDept);
			fApplyDept.setText(fInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFRequireType())){
			fRequireType=(TextView)findViewById(R.id.fixed_assets_special_FRequireType);
			fRequireType.setText(fInfo.getFRequireType());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFReason())){
			fReason=(TextView)findViewById(R.id.fixed_assets_special_FReason);
			fReason.setText(fInfo.getFReason());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFApplyCause())){
			fApplyCause=(TextView)findViewById(R.id.fixed_assets_special_FApplyCause);
			fApplyCause.setText(fInfo.getFApplyCause());
		}
		
		if(!StringUtils.isEmpty(fInfo.getFSubEntrys())){
			showSubEntrys(fInfo.getFSubEntrys());
		}
		initApprove();
	}
	
	/** 
	* @Title: showSubEntrys 
	* @Description:  �������༯��
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��19�� ����2:10:47
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<FixedAssetsSpecialTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<FixedAssetsSpecialTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (FixedAssetsSpecialTBodyInfo item : tBodyInfos) {
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
	* @date 2014��8��19�� ����2:10:39
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final FixedAssetsSpecialTBodyInfo tBodyInfo,final int i){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.fixed_assets_special_tbody, null);
		 
		TextView fLine=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FLine);
		fLine.setText(String.valueOf(i));
		 
		TextView fBillNo=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FBillNo);
		fBillNo.setText(tBodyInfo.getFBillNo());
		 
		TextView fName=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FName);
		fName.setText(tBodyInfo.getFName());
		 
		TextView fModel=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FModel);
		fModel.setText(tBodyInfo.getFModel());
		 
		TextView fNumber=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FNumber);
		fNumber.setText(tBodyInfo.getFNumber());
		 
		TextView fDate=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FDate);
		fDate.setText(tBodyInfo.getFDate());
		 
		TextView fPerson=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FPerson);
		fPerson.setText(tBodyInfo.getFPerson());
		 
		TextView fNote=(TextView)linearLayout.findViewById(R.id.fixed_assets_special_body_FNote);
		fNote.setText(tBodyInfo.getFNote());
		 
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
					UIHelper.showPlusCopy(FixedAssetsSpecialActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fixedassetsspecial_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(FixedAssetsSpecialActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.fixedassetsspecial_title));
				}
			 });
			 
			  //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(FixedAssetsSpecialActivity.this,FixedAssetsSpecialActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(FixedAssetsSpecialActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.fixedassetsspecial_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(FixedAssetsSpecialActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, FixedAssetsSpecialActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.fixedassetsspecial_title));
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
		commonMenu.setContext(FixedAssetsSpecialActivity.this);
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
