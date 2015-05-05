package com.dahuatech.app.ui.expense.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseFlowDetailActivity
 * @Description ��ˮ��ϸҳ��
 * @author 21291
 * @date 2014��8��22�� ����11:28:38
 */
@SuppressLint("InflateParams")
public class ExpenseFlowDetailActivity extends MenuActivity {
	private ArrayAdapter<String> childAdapter;  	 		//����������
	
	private String[] fTravelLabelArray;						//���÷ѱ�ǩֵ����
	private String[] fSocializeLabelArray;					//����Ӧ��ѱ�ǩֵ����
	private String[] fExpendTypeValueArray;					//��������ʵ��ֵ����
	private String[] fTravelValueArray;						//���÷�ʵ��ֵ����
	private String[] fSocializeValueArray;					//����Ӧ���ʵ��ֵ����
	private String[] fBusinesslevelValueArray;				//����ؼ���ʵ��ֵ����
	private String[] fNocardValueArray;						//δˢ��ԭ��ʵ��ֵ����
	
	private int fExpenseType;								//�������� 0-���÷�,1-����Ӧ���
	private int fBindParentPosition = 0;					//������λ��
	private int fBindChildPosition = 0;						//�����λ��
	
	private LinearLayout fSubLinearLayout; 					//���಼��ȫ�ֱ���
	private AppContext appContext; 							//ȫ��Context
	private String fItemNumber;  							//Ա����
	private String fPayType;  								//֧������  ֵΪ�����ÿ�����Я�̡����������޸�����ʱ�䣬�ص㣬���
	private String serviceUrl;  							//�ϴ���������ַ
	private String fDefaultExpendTime;  					//Ĭ������ʱ��
	private String fSkipSource;  							//��ת��Դ����ֱ�Ӵ���ת��������������
	
	private Calendar cal;									//������
	
	private ExpenseBusiness eBusiness;						//ҵ���߼���
	private ExpenseFlowDetailInfo exDetailInfo;				//����ʵ����
	private DbManager mDbHelper;					//���ݿ������
	private Boolean btnResult;								//��ť�������
	private String showLocalResult;							//��ʾ�ݴ�������
	private String showUploadResult;						//��ʾ�ϴ��������
	private ProgressDialog uploadDialog;      				//�ϴ����񵯳���
	
	private EditText fExpendTime,fExpendAmount,fExpendAddress,fCause,fAccompanyReason; //����ؼ�
	private Spinner parentSpinner,childSpinner;				//���������
	private TextView fClientId,fProjectId,fClient,fProject;	//�ͻ�����ĿID������
	private TextView fAccompanylabel;						//��ͬ��Ա��ǩ
	private RadioGroup fAccompany;							//������ͬ��Ա
	private RadioButton fRadioYes,fRadioNo;					//�л���
	private Button btnTemp,btnUpload;						//�ݴ桢�ϴ���ť
	private ImageView fExpendAddressIV,fCauseIV,fClientIV,fProjectIV,fAccompanyReasonIV;
	
	private EditText fStartAddress,fStartTime,fEndAddress,fEndTime,fDescription;  //����ؼ�
	private Spinner fBusinessLevelSpinner,fReasonSpinner;	
	private ImageView fStartAddressIV,fStartTimeIV,fEndAddressIV,fEndTimeIV,fDescriptionIV;
	
	private int fLocalId=0; 								//������������ ��ˮ����״̬ 0-����,1-�޸�
	private int fServerId=0; 								//�������������� ��ˮ����״̬ 0-����,1-�޸�
	private String fExpendTimeValue,fExpendAmountValue,fExpendAddressValue,fCauseValue,fAccompanyReasonValue; //����ؼ�ֵ
	private String fSpinnerParent,fSpinnerChild;			//��������  ����ֵ ����ֵ
	private String fClientIdValue,fProjectIdValue;			//�ͻ�����ĿIDֵ
	private String fClientName,fProjectName;				//�ͻ�����Ŀ����ֵ
	private String fAccompanyValue;                    	 	//������ͬ��Աֵ
	
	private String fStartAddressValue,fStartTimeValue,fEndAddressValue,fEndTimeValue,fDescriptionValue; //����ؼ�ֵ
	private String fBusinessLevelValue,fReasonValue;		//����ؼ�Spinner����ֵ	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		setContentView(R.layout.expense_flowdetail);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWUPLOADACTIVITY;	
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			//Ĭ�Ϻͱ�Ҫ����ֵ
			fPayType=extras.getString(DbManager.KEY_DETAIL_FPAYTYPE);
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fDefaultExpendTime=extras.getString(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME);
			fSkipSource=extras.getString(AppContext.EXPENSE_FLOW_DETAIL_SKIP_SOURCE);
			
			fLocalId=extras.getInt(DbManager.KEY_ROWID,0);
			fServerId=extras.getInt(DbManager.KEY_DETAIL_FSERVERID,0);
			//��ȡ��ˮ����������Ϣ
			fExpendTimeValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDTIME);
			fSpinnerParent=extras.getString(DbManager.KEY_DETAIL_FEXPENDTYPEPARENT);
			fSpinnerChild=extras.getString(DbManager.KEY_DETAIL_FEXPENDTYCHILD);
			fExpendAddressValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDADDRESS);
			fExpendAmountValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDAMOUNT);
			fCauseValue=extras.getString(DbManager.KEY_DETAIL_FCAUSE);
			fClientIdValue=extras.getString(DbManager.KEY_DETAIL_FCLIENTID);
			fProjectIdValue=extras.getString(DbManager.KEY_DETAIL_FPROJECTID);
			fClientName=extras.getString(DbManager.KEY_DETAIL_FCLIENT);
			fProjectName=extras.getString(DbManager.KEY_DETAIL_FPROJECT);
			fAccompanyValue=extras.getString(DbManager.KEY_DETAIL_FACCOMPANY);
			fAccompanyReasonValue=extras.getString(DbManager.KEY_DETAIL_FACCOMPANYREASON);
			//��ȡ��ˮ����������Ϣ
			fStartAddressValue=extras.getString(DbManager.KEY_DETAIL_FSTART);
			fEndAddressValue=extras.getString(DbManager.KEY_DETAIL_FDESTINATION);
			fStartTimeValue=extras.getString(DbManager.KEY_DETAIL_FSTARTTIME);
			fEndTimeValue=extras.getString(DbManager.KEY_DETAIL_FENDTIME);
			fBusinessLevelValue=extras.getString(DbManager.KEY_DETAIL_FBUSINESSLEVEL);
			fReasonValue=extras.getString(DbManager.KEY_DETAIL_FREASON);
			fDescriptionValue=extras.getString(DbManager.KEY_DETAIL_FDESCRIPTION);
		}
		fSubLinearLayout=(LinearLayout)findViewById(R.id.expense_flowdetail_LinearLayout);
		addChildView();	 //���������ͼ
		
		initView();  	//��ʼ����ͼ
		if(fLocalId > 0 || fServerId > 0){  //�޸�ҳ��
			bindView();
		}
		else {   //����ҳ��
			setView();
		}
		setParentListener();		//���ø���ؼ������¼�
		setChildListener();			//��������ؼ������¼�		
		setChildView();	            //��ʾ������ͼ
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����2:19:58
	*/
	private void initView(){
		//����Դ
		fTravelLabelArray=this.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=this.getResources().getStringArray(R.array.socialize_labels_array);
		fExpendTypeValueArray=this.getResources().getStringArray(R.array.expendtype_value_array);
		fTravelValueArray=this.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=this.getResources().getStringArray(R.array.socialize_value_array);
		fBusinesslevelValueArray=this.getResources().getStringArray(R.array.businesslevel_value_array);
		fNocardValueArray=this.getResources().getStringArray(R.array.nocard_value_array);
		
		//ҵ���߼���ʵ����
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowDetailActivity.this);
		eBusiness= (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
		
		//ʵ����
		exDetailInfo=ExpenseFlowDetailInfo.getExpenseFlowDetailInfo();
		
		//����ؼ�
		fExpendTime=(EditText)findViewById(R.id.expense_flowdetail_FExpendTime);
		fExpendTime.setInputType(InputType.TYPE_NULL);
		fExpendAmount=(EditText)findViewById(R.id.expense_flowdetail_FExpendAmount);
		
		parentSpinner=(Spinner)findViewById(R.id.expense_flowdetail_Spinner_Parent);
		childSpinner=(Spinner)findViewById(R.id.expense_flowdetail_Spinner_Child);
		
		fExpendAddress=(EditText)findViewById(R.id.expense_flowdetail_FExpendAddress);
		fClientId=(TextView)findViewById(R.id.expense_flowdetail_FClient_FId);
	    fProjectId=(TextView)findViewById(R.id.expense_flowdetail_FProject_FId);
		fClient=(TextView)findViewById(R.id.expense_flowdetail_FClient);
		fProject=(TextView)findViewById(R.id.expense_flowdetail_FProject);
		fCause=(EditText)findViewById(R.id.expense_flowdetail_FCause);
		
		fAccompany = (RadioGroup) findViewById(R.id.expense_flow_detail_FAccompany);
		fRadioYes = (RadioButton) findViewById(R.id.expense_flow_detail_FAccompany_yes);
		fRadioNo = (RadioButton) findViewById(R.id.expense_flow_detail_FAccompany_no); 
		fAccompanylabel=(TextView)findViewById(R.id.expense_flow_detail_FAccompanylabel);
		fAccompanyReason=(EditText)findViewById(R.id.expense_flow_detail_FAccompanyReason);
		
		//����ͼ��ؼ�
		fExpendAddressIV=(ImageView)findViewById(R.id.expense_flowdetail_FExpendAddress_ImageView);
		fCauseIV=(ImageView)findViewById(R.id.expense_flowdetail_FCause_ImageView);
		fClientIV=(ImageView)findViewById(R.id.expense_flowdetail_FClient_ImageView);
		fProjectIV=(ImageView)findViewById(R.id.expense_flowdetail_FProject_ImageView);
		fAccompanyReasonIV=(ImageView)findViewById(R.id.expense_flow_detail_FAccompanyReason_ImageView);
		
		//���÷�����ؼ�
		fStartAddress=(EditText)findViewById(R.id.expense_flow_detail_travel_FStartAddress);
		fStartTime=(EditText)findViewById(R.id.expense_flow_detail_travel_FStartTime);
		fStartTime.setInputType(InputType.TYPE_NULL);
		fEndAddress=(EditText)findViewById(R.id.expense_flow_detail_travel_FEndAddress);
		fEndTime=(EditText)findViewById(R.id.expense_flow_detail_travel_FEndTime);
		fEndTime.setInputType(InputType.TYPE_NULL);
		fBusinessLevelSpinner=(Spinner)findViewById(R.id.expense_flow_detail_travel_FBusinessLevel);
		//���÷�����ͼ��ؼ�
		fStartAddressIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FStartAddress_ImageView);
		fStartTimeIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FStartTime_ImageView);
		fEndAddressIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FEndAddress_ImageView);
		fEndTimeIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FEndTime_ImageView);
		
		//����Ӧ�������ؼ�
		fReasonSpinner=(Spinner)findViewById(R.id.expense_flow_detail_socialize_FReason);
		fDescription=(EditText)findViewById(R.id.expense_flowdetail_socialize_FDescription);
		//����Ӧ�������ͼ��ؼ�
		fDescriptionIV=(ImageView)findViewById(R.id.expense_flowdetail_socialize_FDescription_ImageView);
		
		//�ݴ���ϴ��ؼ�
		btnTemp=(Button)findViewById(R.id.expense_flow_detail_button_temp);
		btnUpload=(Button)findViewById(R.id.expense_flow_detail_button_upload);
		
		//����
		cal = Calendar.getInstance();
		
		//��ʼ�����ؽ��
		btnResult=false;
		showLocalResult=getResources().getString(R.string.expense_flow_detail_show_local_failure);
		showUploadResult=getResources().getString(R.string.expense_flow_detail_show_upload_failure);
	
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_uploading));
		uploadDialog.setCancelable(false);
	}
	
	/** 
	* @Title: setView 
	* @Description: ��������-������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��5�� ����1:48:59
	*/
	private void setView(){
		//��ʼ��Ĭ��ֵ
		if(StringUtils.isEmpty(fDefaultExpendTime)){
			fDefaultExpendTime=StringUtils.toShortDateString(cal.getTime());
		}
		fExpendTime.setText(fDefaultExpendTime);	
		
		parentSpinner.setSelection(fBindParentPosition,true);	//Ĭ��ѡ�в��÷�
		childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);		
		childSpinner.setAdapter(childAdapter); 
		childSpinner.setSelection(fBindChildPosition,true); 	//��������ѡ����
		
		//Ĭ����������
		fSpinnerParent = getResources().getStringArray(R.array.expendtype_value_array)[parentSpinner.getSelectedItemPosition()];
		fSpinnerChild = getResources().getStringArray(R.array.travel_value_array)[childSpinner.getSelectedItemPosition()];
		
		//Ĭ��ѡ������ͬ��Ա
		fAccompany.check(fRadioYes.getId()); 
		fAccompanyValue="��";
		
		//Ĭ������ֵ
		fBusinessLevelValue = getResources().getStringArray(R.array.businesslevel_value_array)[fBusinessLevelSpinner.getSelectedItemPosition()];
		fReasonValue = getResources().getStringArray(R.array.nocard_value_array)[fReasonSpinner.getSelectedItemPosition()];
	
		//�򳵱���������ˮ
		if(!StringUtils.isEmpty(fExpendAmountValue)){
			fExpendAmount.setText(fExpendAmountValue);
		}
		if(!StringUtils.isEmpty(fStartAddressValue)){
			fStartAddress.setText(fStartAddressValue);
		}
		if(!StringUtils.isEmpty(fStartTimeValue)){
			fStartTime.setText(fStartTimeValue);
		}
		if(!StringUtils.isEmpty(fEndAddressValue)){
			fEndAddress.setText(fEndAddressValue);
		}
		if(!StringUtils.isEmpty(fEndTimeValue)){
			fEndTime.setText(fEndTimeValue);
		}
	}
	
	/** 
	* @Title: bindView 
	* @Description:  �޸Ľ���-����ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��5�� ����1:49:18
	*/
	private void bindView(){
		//״̬����  ���ÿ�/Я�̲����޸�����ʱ�䡢�ص㡢���
		if("���ÿ�".equals(fPayType) || "Я��".equals(fPayType)){
			fExpendTime.setEnabled(false);
			fExpendAmount.setEnabled(false);
			fExpendAddress.setEnabled(false);
			fExpendAddressIV.setVisibility(View.GONE);
		}
		
		//����ؼ���ֵ
		if(!StringUtils.isEmpty(fExpendTimeValue)){
			fExpendTime.setText(fExpendTimeValue);
		}		
		if(!StringUtils.isEmpty(fExpendAmountValue)){
			fExpendAmount.setText(fExpendAmountValue);
		}	
		if("2006".equals(fSpinnerParent)){  //���÷�
			childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);		
			fBindChildPosition=Arrays.asList(fTravelValueArray).indexOf(fSpinnerChild);
		}
		else{  //����Ӧ���
			childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fSocializeLabelArray);
			fBindChildPosition=Arrays.asList(fSocializeValueArray).indexOf(fSpinnerChild);
		}
		fBindParentPosition=Arrays.asList(fExpendTypeValueArray).indexOf(fSpinnerParent);	
		childSpinner.setAdapter(childAdapter);  //����������ֵ
		
		parentSpinner.setSelection(fBindParentPosition,true);	 //���ø���ѡ����
		childSpinner.setSelection(fBindChildPosition,true);  	 //��������ѡ����
			
		if(!StringUtils.isEmpty(fExpendAddressValue)){
			fExpendAddress.setText(fExpendAddressValue);
		}		
		if(!StringUtils.isEmpty(fExpendAddressValue)){
			fExpendAddress.setText(fExpendAddressValue);
		}	
		if(!StringUtils.isEmpty(fCauseValue)){
			fCause.setText(fCauseValue);
		}	
		if(!StringUtils.isEmpty(fClientIdValue)){
			fClientId.setText(fClientIdValue);
		}	
		if(!StringUtils.isEmpty(fProjectIdValue)){
			fProjectId.setText(fProjectIdValue);
		}
		if(!StringUtils.isEmpty(fClientName)){
			fClient.setText(fClientName);
		}	
		if(!StringUtils.isEmpty(fProjectName)){
			fProject.setText(fProjectName);
		}
		
		if("1".equals(fAccompanyValue)){
			fAccompany.check(fRadioYes.getId()); 
		}
		else {
			fAccompany.check(fRadioNo.getId()); 
		}	
		
		if(!StringUtils.isEmpty(fAccompanyReasonValue)){
			fAccompanyReason.setText(fAccompanyReasonValue);
		}
		
		//����ؼ���ֵ
		if(!StringUtils.isEmpty(fStartAddressValue)){
			fStartAddress.setText(fStartAddressValue);
		}
		if(!StringUtils.isEmpty(fStartTimeValue)){
			fStartTime.setText(fStartTimeValue);
		}
		if(!StringUtils.isEmpty(fEndAddressValue)){
			fEndAddress.setText(fEndAddressValue);
		}
		if(!StringUtils.isEmpty(fEndTimeValue)){
			fEndTime.setText(fEndTimeValue);
		}
		int fBindBusinessLevelPosition=Arrays.asList(fBusinesslevelValueArray).indexOf(fBusinessLevelValue);
		fBusinessLevelSpinner.setSelection(fBindBusinessLevelPosition,true);	 //���ó���ؼ���ѡ����
		
		int fBindReasonPosition=Arrays.asList(fNocardValueArray).indexOf(fReasonValue);
		fReasonSpinner.setSelection(fBindReasonPosition,true);	 				//����δˢ��ԭ��ѡ����
		if(!StringUtils.isEmpty(fDescriptionValue)){
			fDescription.setText(fDescriptionValue);
		}	
	}
	
	/** 
	* @Title: setControlListener 
	* @Description: ���ø���ؼ������¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����2:21:46
	*/
	private void setParentListener(){
		
		//����ʱ��������
		fExpendTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {	
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,expendTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
				}	
		});
		
		//�������������
		parentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			// ��̬�ı�ؼ��������İ�ֵ
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				if(position==0){ //���÷�
					fExpenseType=0;
					childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);	
					hiddenSocializeView();
					showTravelView();
				}
				else{  //����Ӧ���
					fExpenseType=1;
					childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fSocializeLabelArray);
					hiddenTravelView();
					showSocializeView();
				}
				childSpinner.setAdapter(childAdapter);
				childSpinner.setSelection(0,true);  
				fSpinnerParent = getResources().getStringArray(R.array.expendtype_value_array)[parentSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
				
		//�Ӽ����������
		childSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				switch (fExpenseType) {
					case 0:  //���÷�
						fSpinnerChild = getResources().getStringArray(R.array.travel_value_array)[childSpinner.getSelectedItemPosition()];
						break;
					case 1:  //����Ӧ���
						fSpinnerChild = getResources().getStringArray(R.array.socialize_value_array)[childSpinner.getSelectedItemPosition()];
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//������ͬ��Ա����¼�
		fAccompany.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==fRadioYes.getId())
			    	{ 
				     	fAccompanylabel.setText(getResources().getString(R.string.expense_flow_detail_FAccompanyPerson));
				     	fAccompanyValue=fRadioYes.getText().toString();
			    	} 
			      	else
			      	{ 
				     	fAccompanylabel.setText(getResources().getString(R.string.expense_flow_detail_FAccompanyReason));
			      		fAccompanyValue=fRadioNo.getText().toString();
			      	}       
			}
		});
		
		//�ݴ��¼�
		btnTemp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verifyControlValue()){  //��֤ͨ��
					getControlValue();
					setModel();
					tempSave();
				}
			}
		});
		
		//�ϴ��¼�
		btnUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verifyControlValue()){  //��֤ͨ��
					getControlValue();
					setModel();
					uploadSave();
				}
			}
		});
		
		//���ѵص�
		fExpendAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fExpendAddress);
			}
		});
		
		//����ԭ��
		fCauseIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fCause);
			}
		});
		
		//����ԭ��
		fCauseIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fCause);
			}
		});
		
		//�ͻ�����
		fClientIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseFlowClientList(ExpenseFlowDetailActivity.this,fItemNumber);
			}
		});
		
		//��Ŀ����
		fProjectIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseFlowProjectList(ExpenseFlowDetailActivity.this,fItemNumber);
			}
		});
		
		//������ͬԭ��/��Ա
		fAccompanyReasonIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fAccompanyReason);
			}
		});	
	}
	
	/** 
	* @Title: setChildListener 
	* @Description: ��������ؼ������¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����2:24:05
	*/
	private void setChildListener(){		
		//����ؼ������������
		fBusinessLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				fBusinessLevelValue = getResources().getStringArray(R.array.businesslevel_value_array)[fBusinessLevelSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//����Ӧ�������ؼ�����	 
		//δˢ��ԭ�����������
		fReasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				fReasonValue = getResources().getStringArray(R.array.nocard_value_array)[fReasonSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//�����ص�
		fStartAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fStartAddress);
			}
		});	
		
		//Ŀ�ĵ�
		fEndAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fEndAddress);
			}
		});	
		
		//����ʱ��
		fStartTimeIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,startTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
			}
		});	
		
		//����ʱ��
		fEndTimeIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,endTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
			}
		});	
		
		//˵��
		fDescriptionIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fDescription);
			}
		});	
	}
	
	/** 
	* @Title: showInputBox 
	* @Description: ��ʾ���������ı���
	* @param @param editText     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����11:49:12
	*/
	private void showInputBox(final EditText editText){
		String content=editText.getText().toString();
		editText.setCursorVisible(true);	 //��ʾ���
		editText.requestFocus();  //��ȡ����
		editText.setSelection(content.length());
		//��ʾ�������
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
	}
	
	/** 
	* @Title: addChildView 
	* @Description: ���������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����5:05:22
	*/
	private void addChildView(){
		View travelLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flow_travel, null);
		View socializeLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flow_socialize, null);
		View buttonLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flowdetail_button, null);
		fSubLinearLayout.addView(travelLinearLayout);
		fSubLinearLayout.addView(socializeLinearLayout);
		fSubLinearLayout.addView(buttonLinearLayout);
	}
	
	/** 
	* @Title: setChildView 
	* @Description: ����������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��3�� ����7:03:44
	*/
	private void setChildView(){
		if(fBindParentPosition==0){  //���ؽ���Ӧ���������ͼ
			hiddenSocializeView();
		}
		else{  //���ز��÷�������ͼ
			hiddenTravelView();
		}
	}
	
	/** 
	* @Title: showTravelView 
	* @Description: ��ʾ���÷�������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��3�� ����7:41:45
	*/
	private void showTravelView(){
		LinearLayout showLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_travel);
		showLayout.setVisibility(View.VISIBLE);
	}
	
	/** 
	* @Title: showSocializeView 
	* @Description: ��ʾ����Ӧ���������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��3�� ����7:41:47
	*/
	private void showSocializeView(){
		LinearLayout showLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_socialize);
		showLayout.setVisibility(View.VISIBLE);
	}
	
	/** 
	* @Title: hiddenTravelView 
	* @Description: ���ز��÷�������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��3�� ����5:30:52
	*/
	private void hiddenTravelView(){
		LinearLayout hiddenLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_travel);
		hiddenLayout.setVisibility(View.GONE);
	}
	
	/** 
	* @Title: hiddenSocializeView 
	* @Description: ���ؽ���Ӧ���������ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��3�� ����7:26:03
	*/
	private void hiddenSocializeView(){
		LinearLayout hiddenLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_socialize);
		hiddenLayout.setVisibility(View.GONE);
	}
	
	//����ʱ�������
	private DatePickerDialog.OnDateSetListener expendTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  setDate(fExpendTime);
		}
	};
	
	//����ʱ�������
	private DatePickerDialog.OnDateSetListener startTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);  
			  setDate(fStartTime);
		}
	};
		
	//����ʱ�������
	private DatePickerDialog.OnDateSetListener endTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);   
			  setDate(fEndTime);
		}
	};

	/** 
	* @Title: setDate 
	* @Description: ��������
	* @param @param editText     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����1:50:35
	*/
	private void setDate(EditText editText){
		editText.setText(StringUtils.toShortDateString(cal.getTime()));
	}
	
	// �ӿͻ�/��Ŀҳ��,�ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.EXPENSE_FLOW_DETAIL_CLIENT:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fClientId.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID));
						fClient.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME));
					}
				}			
				break;
			case AppContext.EXPENSE_FLOW_DETAIL_PROJECT:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fProjectId.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID));
						fProject.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME));
					}
				}	
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: getControlValue 
	* @Description: ��ȡ�ؼ�ֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����3:02:53
	*/
	private void getControlValue(){
		//����ֵ
		fExpendTimeValue=fExpendTime.getText().toString();
		fExpendAmountValue=fExpendAmount.getText().toString();
		fExpendAddressValue=fExpendAddress.getText().toString();
		fCauseValue=fCause.getText().toString();
		fAccompanyReasonValue=fAccompanyReason.getText().toString();	
		fClientIdValue=fClientId.getText().toString();
		fProjectIdValue=fProjectId.getText().toString();
		fClientName=fClient.getText().toString();
		fProjectName=fProject.getText().toString();
		
		//����ֵ
		fStartAddressValue=fStartAddress.getText().toString();
		fStartTimeValue=fStartTime.getText().toString();
		fEndAddressValue=fEndAddress.getText().toString();		
		fEndTimeValue=fEndTime.getText().toString();
		fDescriptionValue=fDescription.getText().toString();
	}
	
	/** 
	* @Title: verifyControlValue 
	* @Description: ��֤�����ֶ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����7:58:33
	*/
	private boolean  verifyControlValue(){
		boolean result=true;
		if(StringUtils.isEmpty(fExpendAmount.getText().toString())){
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FExpendAmount));
			result= false;
		}else if (StringUtils.isEmpty(fExpendAddress.getText().toString())) {
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FExpendAddress));
			result= false; 	
		}else if(StringUtils.isEmpty(fCause.getText().toString())) {
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FCause));
			result= false;
		}
		return result;
	}
	
	/** 
	* @Title: setModel 
	* @Description: ����ʵ������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��5�� ����10:52:09
	*/
	private void setModel(){
		exDetailInfo.setFLocalId(fLocalId);
		exDetailInfo.setFServerId(fServerId);
		//����
		exDetailInfo.setFPayType(fPayType);
		exDetailInfo.setFItemNumber(fItemNumber);
		exDetailInfo.setFExpendTime(fExpendTimeValue);
		exDetailInfo.setFExpendTypeParent(fSpinnerParent);
		exDetailInfo.setFExpendTypeChild(fSpinnerChild);
		exDetailInfo.setFExpendAddress(fExpendAddressValue);
		exDetailInfo.setFExpendAmount(fExpendAmountValue);
		exDetailInfo.setFCause(fCauseValue);
		exDetailInfo.setFClientId(fClientIdValue);
		exDetailInfo.setFProjectId(fProjectIdValue);
		exDetailInfo.setFClient(fClientName);
		exDetailInfo.setFProject(fProjectName);
		if("��".equals(fAccompanyValue)){
			exDetailInfo.setFAccompany("1");
		}
		else {
			exDetailInfo.setFAccompany("2");
		}	
		exDetailInfo.setFAccompanyReason(fAccompanyReasonValue);
		//���÷�����
		exDetailInfo.setFStart(fStartAddressValue);
		exDetailInfo.setFDestination(fEndAddressValue);
		exDetailInfo.setFStartTime(fStartTimeValue);
		exDetailInfo.setFEndTime(fEndTimeValue);	
		exDetailInfo.setFBusinessLevel(fBusinessLevelValue);
		//����Ӧ�������
		exDetailInfo.setFReason(fReasonValue);
		exDetailInfo.setFDescription(fDescriptionValue);
	}

	
	/** 
	* @Title: tempSave 
	* @Description: �ݴ汣��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����8:05:38
	*/
	private void tempSave(){	
		exDetailInfo.setFUploadFlag("0");
		if(fLocalId > 0){ //�޸Ĳ���
			btnResult=mDbHelper.updateFlowDetail(exDetailInfo);
		}
		else{ //��������
			btnResult=mDbHelper.insertFlowDetail(exDetailInfo);
		}
		if(btnResult){
			showLocalResult=getResources().getString(R.string.expense_flow_detail_show_local_success);	
		}
		UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, showLocalResult);
		tempBack();
	}
	
	/** 
	* @Title: tempBack 
	* @Description: �ݴ淵��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����5:13:09
	*/
	private void tempBack(){
		// �ӳ�2�������б�ҳ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDbHelper.closeSqlLite();
        		if(fLocalId > 0){  //�޸�
        			Intent intent = new Intent();
        			setResult(RESULT_OK, intent);
        		}
        		else{//����
        			UIHelper.showExpenseFlowLocalList(ExpenseFlowDetailActivity.this, fItemNumber);   		
        		}
        		finish();     
            }
        }, 2000);	
	}
	
	/** 
	* @Title: uploadSave 
	* @Description: �ϴ�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��4�� ����8:05:36
	*/
	@SuppressWarnings("unchecked")
	private void uploadSave(){
		exDetailInfo.setFUploadFlag("1");
		List<ExpenseFlowDetailInfo> intendList=new ArrayList<ExpenseFlowDetailInfo>();
		intendList.add(exDetailInfo);
		new detailUploadAsync().execute(intendList);
	}
	
	/**
	 * @ClassName detailUploadAsync
	 * @Description �ϴ����ݵ�������
	 * @author 21291
	 * @date 2014��9��10�� ����3:57:00
	 */
	private class detailUploadAsync extends AsyncTask<List<ExpenseFlowDetailInfo>,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			uploadDialog.show(); 	// ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(List<ExpenseFlowDetailInfo>... params) {
			return uploadServer(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showUploadResult(result);	
			uploadDialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: uploadServer 
	* @Description: �ϴ���ˮ�����¼��������
	* @param @param uploadList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����4:00:38
	*/
	private ResultMessage uploadServer(List<ExpenseFlowDetailInfo> uploadList){
		return eBusiness.flowBatchUpload(uploadList);
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: �����ϴ���ˮ��¼UI�����
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����4:02:23
	*/
	private void showUploadResult(ResultMessage resultMessage){
		if(resultMessage.isIsSuccess()){  //˵���ϴ��ɹ�
			sendLogs(); //������־��Ϣ����ͳ��
			showUploadResult=getResources().getString(R.string.expense_flow_detail_show_upload_success);
		}
		UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, showUploadResult);
		uploadBack();
	}
	
	/** 
	* @Title: uploadBack 
	* @Description: �ϴ�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����5:12:42
	*/
	private void uploadBack(){
		
		// �ӳ�2�������б�ҳ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDbHelper.closeSqlLite();
        		if(StringUtils.isEmpty(fSkipSource)){
        			Intent intent = new Intent();
        			setResult(RESULT_OK, intent);      		
        		}
        		else{
        			UIHelper.showExpenseFlowList(ExpenseFlowDetailActivity.this,fItemNumber);
        		}
        		finish();  
            }
        }, 2000);	
	
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �������ʱ��������־��¼��������
	* @param @param fOrderId     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����6:56:25
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_flow));
		logInfo.setFActionName("upload");
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseFlowDetailActivity.this,logInfo);
	}
	
	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}   
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
