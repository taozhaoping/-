package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.DevelopInquiryTBodyInfo;
import com.dahuatech.app.bean.mytask.DevelopInquiryTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.DevelopInquiryBusiness;
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
 * @ClassName DevelopInquiryActivity
 * @Description �з�����ѯ�۵�������Activity
 * @author 21291
 * @date 2014��7��16�� ����9:24:43
 */
public class DevelopInquiryActivity extends MenuActivity implements ITaskContext,ICheckNextNode {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fDate,fApplyerDeptName,fEmployeeNumber,fMaterialType,fOfferExplain;
	private Button appButton,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	private float dip; //����  ���1.5��
	private int fixedHeight=30;	//�߶ȹ̶�ֵ
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//���ؿ����Ϣ
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developinquiry);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DEVELOPINQUIRYACTIVITY;	
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
		
		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density;  
		fSubLinearLayout=(LinearLayout)findViewById(R.id.developinquiry_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(DevelopInquiryActivity.this, DevelopInquiryActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(DevelopInquiryActivity.this, R.string.developinquiry_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DevelopInquiryActivity.this);
		DevelopInquiryBusiness eBusiness= (DevelopInquiryBusiness)factoryBusiness.getInstance("DevelopInquiryBusiness",serviceUrl);
		return eBusiness.getDevelopInquiryTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		DevelopInquiryTHeaderInfo deHeaderInfo=(DevelopInquiryTHeaderInfo)base;
		if(!StringUtils.isEmpty(deHeaderInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.developinquiry_FBillNo);
			fBillNo.setText(deHeaderInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.developinquiry_FApplyName);
			fApplyName.setText(deHeaderInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFDate())){
			fDate=(TextView)findViewById(R.id.developinquiry_FDate);
			fDate.setText(deHeaderInfo.getFDate());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFApplyerDeptName())){
			fApplyerDeptName=(TextView)findViewById(R.id.developinquiry_FApplyerDeptName);
			fApplyerDeptName.setText(deHeaderInfo.getFApplyerDeptName());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFEmployeeNumber())){
			fEmployeeNumber=(TextView)findViewById(R.id.developinquiry_FEmployeeNumber);
			fEmployeeNumber.setText(deHeaderInfo.getFEmployeeNumber());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFMaterialType())){
			fMaterialType=(TextView)findViewById(R.id.developinquiry_FMaterialType);
			fMaterialType.setText(deHeaderInfo.getFMaterialType());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFOfferExplain())){
			fOfferExplain=(TextView)findViewById(R.id.developinquiry_FOfferExplain);
			fOfferExplain.setText(deHeaderInfo.getFOfferExplain());
		}
		
		if(!StringUtils.isEmpty(deHeaderInfo.getFSubEntrys())){
			showSubEntrys(deHeaderInfo.getFSubEntrys());	
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
	* @date 2014��7��16�� ����11:49:31
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			Type listType = new TypeToken<ArrayList<DevelopInquiryTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DevelopInquiryTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (DevelopInquiryTBodyInfo item : tBodyInfos) {
					attachRelativeLayout(item);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: ��̬����������ͼ
	* @param @param tBodyInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��18�� ����9:48:08
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final DevelopInquiryTBodyInfo tBodyInfo){
		paddingLeft = 20; // margin in dips
		paddingRight = 20; 
		paddingTop = 5;
		paddingBottom = 5; 
		fixedHeight=33;	//�߶ȹ̶�ֵ
	    width = getPixelsWidth(); 
		layoutHeight=RelativeLayout.LayoutParams.WRAP_CONTENT;
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
	        layoutHeight=(int)(fixedHeight * dip);
        }

		RelativeLayout reLayoutTop=new RelativeLayout(this);  //����һ������ͷ����Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		LayoutParams reLPTop=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
		reLayoutTop.setLayoutParams(reLPTop);
		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		reLayoutTop.setGravity(Gravity.CENTER_VERTICAL);
		reLayoutTop.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		reLayoutTop.setMinimumHeight(0);
		
		//ָ��ͼ��
		ImageButton imgButton=new ImageButton(this);
		LayoutParams imgParam = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgParam.setMargins(0, 0, 10, 0);
		imgParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imgButton.setLayoutParams(imgParam);
		imgButton.setBackgroundResource(0);
		imgButton.setImageResource(R.drawable.imgbtn_arrow_list);
		imgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DevelopInquiryActivity.this,DevelopInquiryLayoutActivity.class);
				intent.putExtra(DevelopInquiryBusiness.KEY_FMASTERIALNAME,tBodyInfo.getFMasterialName());
				intent.putExtra(DevelopInquiryBusiness.KEY_FSUPPLIER,tBodyInfo.getFSupplier());
				intent.putExtra(DevelopInquiryBusiness.KEY_FMANUFACTURER,tBodyInfo.getFManufacturer());
				intent.putExtra(DevelopInquiryBusiness.KEY_FOFFER,tBodyInfo.getFOffer());
				intent.putExtra(DevelopInquiryBusiness.KEY_FCURRENCY,tBodyInfo.getFCurrency());
				intent.putExtra(DevelopInquiryBusiness.KEY_FORDERQUANTITYFROM,tBodyInfo.getFOrderQuantityFrom());
				intent.putExtra(DevelopInquiryBusiness.KEY_FORDERQUANTITYTO,tBodyInfo.getFOrderQuantityTo());
				intent.putExtra(DevelopInquiryBusiness.KEY_FUNIT,tBodyInfo.getFUnit());
				intent.putExtra(DevelopInquiryBusiness.KEY_FORDERFORWARD,tBodyInfo.getFOrderForward());
				intent.putExtra(DevelopInquiryBusiness.KEY_FMINI,tBodyInfo.getFMini());
				intent.putExtra(DevelopInquiryBusiness.KEY_FMINIORDER,tBodyInfo.getFMiniOrder());
				intent.putExtra(DevelopInquiryBusiness.KEY_FPAYMENT,tBodyInfo.getFPayment());
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		reLayoutTop.addView(imgButton);
		
		//��������
		TextView tv1 = new TextView(this);
		LayoutParams tvParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tv1.setLayoutParams(tvParam1);
		tv1.setPadding(paddingLeft, 0, 0, 0);
		tv1.setTextAppearance(DevelopInquiryActivity.this,R.style.icons);
		tv1.setText(tBodyInfo.getFMasterialName());
		reLayoutTop.addView(tv1);
		fSubLinearLayout.addView(reLayoutTop);
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: ��ȡ��Ļ���ؿ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����2:00:31
	*/
	private int getPixelsWidth(){
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
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
		 
		 if("1".equals(fStatus)){  //˵��������������
			 appButton.setText(R.string.approve_imgbtnApprove_record);
			 handleLayout.setVisibility(View.GONE);
		 }
		 else {
			 handleLayout.setVisibility(View.VISIBLE);
			 Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			 Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			 lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			 
			 plusButton.setOnClickListener(new OnClickListener() { //��ǩ����
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopInquiryActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developinquiry_title));
				}
			 });
			 
			
			 copyButton.setOnClickListener(new OnClickListener() {  //���Ͳ���
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopInquiryActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developinquiry_title));
				}
			 });
			 
			 //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(DevelopInquiryActivity.this,DevelopInquiryActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(DevelopInquiryActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.developinquiry_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(DevelopInquiryActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, DevelopInquiryActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.developinquiry_title));
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
		commonMenu.setContext(DevelopInquiryActivity.this);
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
