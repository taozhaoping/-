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
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdBorrowTBodyInfo;
import com.dahuatech.app.bean.mytask.TdBorrowTHeaderInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.TdBorrowBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName TdBorrowActivity
 * @Description �����ļ��������뵥��Activity
 * @author 21291
 * @date 2014��8��28�� ����5:06:46
 */
public class TdBorrowActivity extends MenuActivity implements ITaskContext,ICheckNextNode{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fSecrecyDate,fDocumentType;
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
		setContentView(R.layout.td_borrow_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_TDBORROWACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.td_borrow_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(TdBorrowActivity.this, TdBorrowActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(TdBorrowActivity.this, R.string.tdborrow_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(TdBorrowActivity.this);
		TdBorrowBusiness eBusiness= (TdBorrowBusiness)factoryBusiness.getInstance("TdBorrowBusiness",serviceUrl);
		return eBusiness.getTdBorrowTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		TdBorrowTHeaderInfo tInfo=(TdBorrowTHeaderInfo)base;
		if(!StringUtils.isEmpty(tInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.td_borrow_FBillNo);
			fBillNo.setText(tInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.td_borrow_FApplyName);
			fApplyName.setText(tInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(tInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.td_borrow_FApplyDate);
			fApplyDate.setText(tInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.td_borrow_FApplyDept);
			fApplyDept.setText(tInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFSecrecyDate())){
			fSecrecyDate=(TextView)findViewById(R.id.td_borrow_FSecrecyDate);
			fSecrecyDate.setText(tInfo.getFSecrecyDate());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFDocumentType())){
			fDocumentType=(TextView)findViewById(R.id.td_borrow_FDocumentType);
			fDocumentType.setText(tInfo.getFDocumentType());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFSubEntrys())){
			showSubEntrys(tInfo.getFSubEntrys());
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
	* @date 2014��8��28�� ����5:26:20
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<TdBorrowTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<TdBorrowTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (TdBorrowTBodyInfo item : tBodyInfos) {
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
	private void attachSubEntry(final TdBorrowTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.td_borrow_tbody, null);
		
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView fDocumentName=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FDocumentName);
		 fDocumentName.setText(tBodyInfo.getFDocumentName());
		 
		 TextView fDocumentUse=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FDocumentUse);
		 fDocumentUse.setText(tBodyInfo.getFDocumentUse());
		 
		 TextView fSupportType=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FSupportType);
		 fSupportType.setText(tBodyInfo.getFSupportType());
		 
		 TextView fVersion=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FVersion);
		 fVersion.setText(tBodyInfo.getFVersion());
		 
		 TextView fNote=(TextView)linearLayout.findViewById(R.id.td_borrow_tbody_FNote);
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
					UIHelper.showPlusCopy(TdBorrowActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.tdborrow_title));
				}
			 });
			 
			 //���Ͳ���
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(TdBorrowActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.tdborrow_title));
				}
			 });
			 
			 //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(TdBorrowActivity.this,TdBorrowActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			@Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(TdBorrowActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.tdborrow_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(TdBorrowActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, TdBorrowActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.tdborrow_title));
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
		commonMenu.setContext(TdBorrowActivity.this);
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
