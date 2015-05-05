package com.dahuatech.app.ui.develop.hour;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.develophour.DHDetailInfo;
import com.dahuatech.app.bean.develophour.DHDetailParamInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName DHDetailActivity
 * @Description �з���ʱ����ҳ��
 * @author 21291
 * @date 2014��10��21�� ����5:34:36
 */
public class DHDetailActivity extends MenuActivity {
	private ProgressDialog dialog,uploadDialog;     		//������
	private TextView fWeekDateTView,fProjectCodeTView,fProjectNameTView,fTypeIdTView,fTypeNameTView;
	private EditText fHoursEdit,fNoteEdit;
	private ImageView fWeekDateImage,fProjectNameImage,fTypeNameImage,fHoursImage,fNoteImage;
	private Button btnUpload,btnCancle;   					//�ϴ���ȡ��
	
	private String fWeekDate,fProjectCode,fProjectName,fTypeId,fTypeName,fHours,fNote;  					
	
	private DHDetailInfo dhDetailInfo;						//����ʵ����
	private DevelopHourBusiness dBusiness;					//ҵ���߼���
	
	private String fActionType;								//��������  "Add"��"Edit"
	private String fAccess;									//�����������,�а�ť���,��ʱ�б����,��Ŀ�б����,���������б����
	private String fItemNumber;  							//Ա����
	
	private String detailUrl,uploadUrl;  					//�����ַ,�ϴ������ַ
	private AppContext appContext; 							//ȫ��Context
	private InputMethodManager imm;							//��������
	
	private Calendar cal;									//������
	private int fBillId;									//�ܵ���ID
	private String fWeekValue;								//ÿ��ֵ
	private String fPassWeekDate;							//���ݹ����Ĺ�����ʱ��
	private String fPassProjectCode;						//���ݹ�������Ŀ���
	private String fPassProjectName;						//���ݹ�������Ŀ����
	private String fPassTypeId;								//���ݹ�������������Id
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.dh_detail);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fBillId=extras.getInt(AppContext.DEVELOP_HOURS_WEEK_BILLID,0);
			fWeekValue=extras.getString(AppContext.DEVELOP_HOURS_WEEK_VALUE);
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fActionType=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE);
			fAccess=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_ACCESS);
			fPassWeekDate=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_WEEKDATE);		
			fPassProjectCode=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE);
			fPassProjectName=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTNAME);
			fPassTypeId=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_TYPEID);
		}
		//��ȡ�����ַ
		detailUrl=AppUrl.URL_API_HOST_ANDROID_DHDETAILACTIVITY;	
		uploadUrl=AppUrl.URL_API_HOST_ANDROID_UPLOADDHDETAILACTIVITY;
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initView();
		setOnListener();
		bindView();
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��29�� ����4:57:06
	*/
	private void initView(){
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		fWeekDateTView=(TextView)findViewById(R.id.dh_detail_FWeekDate);
		fProjectCodeTView=(TextView)findViewById(R.id.dh_detail_FProjectCode);
		fProjectNameTView=(TextView)findViewById(R.id.dh_detail_FProjectName);
		fTypeIdTView=(TextView)findViewById(R.id.dh_detail_FTypeId);
		fTypeNameTView=(TextView)findViewById(R.id.dh_detail_FTypeName);
		
		fHoursEdit=(EditText)findViewById(R.id.dh_detail_FHours);
		fNoteEdit=(EditText)findViewById(R.id.dh_detail_FNote);	
		
		btnUpload=(Button)findViewById(R.id.dh_detail_button_FUpload);
		btnCancle=(Button)findViewById(R.id.dh_detail_button_FCancle);
		
		fWeekDateImage=(ImageView)findViewById(R.id.dh_detail_FWeekDate_ImageView);
		fProjectNameImage=(ImageView)findViewById(R.id.dh_detail_FProjectName_ImageView);
		fTypeNameImage=(ImageView)findViewById(R.id.dh_detail_FTypeName_ImageView);
		fHoursImage=(ImageView)findViewById(R.id.dh_detail_FHours_ImageView);
		fNoteImage=(ImageView)findViewById(R.id.dh_detail_FNote_ImageView);
		
		dhDetailInfo=DHDetailInfo.getDHDetailInfo();
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHDetailActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
		
		fWeekDate=fProjectCode=fProjectName=fTypeId=fTypeName=fHours=fNote="";  
		cal = new GregorianCalendar(); 			//����ʵ����
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: ���ÿؼ������¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��29�� ����5:28:46
	*/
	private void setOnListener(){
		fWeekDateImage.setOnClickListener(new OnClickListener() {  //ѡ��ʱ��
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(DHDetailActivity.this ,weekDateListener,
						cal.get(Calendar.YEAR ),   
			            cal.get(Calendar.MONTH ),   
			            cal.get(Calendar.DAY_OF_MONTH )).show();   
			}
		});
		
		fProjectNameImage.setOnClickListener(new OnClickListener() {  //ѡ����Ŀ
			
			@Override
			public void onClick(View v) {
				UIHelper.showDHProjectSearch(DHDetailActivity.this);
			}
		});
		
		fTypeNameImage.setOnClickListener(new OnClickListener() {  //ѡ����������
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(fProjectCodeTView.getText().toString())&&!StringUtils.isEmpty(fProjectNameTView.getText().toString())){
					UIHelper.showDHTypeList(DHDetailActivity.this,fProjectCodeTView.getText().toString());
				}
				else {
					UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_select_FProject));
				}
			}
		});
		
		fHoursImage.setOnClickListener(new OnClickListener() {  //�ɱ༭
			
			@Override
			public void onClick(View v) {
				focusHoursEdit();
			}
		});
		
		fHoursEdit.addTextChangedListener(new TextWatcher() {   //������λС����
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void afterTextChanged(Editable edt) {
				String temp = edt.toString();
		        int posDot = temp.indexOf(".");
		        if (posDot <= 0) return;
		        if (temp.length() - posDot - 1 > 2)
		        {
		            edt.delete(posDot + 3, posDot + 4);
		        }
			}
		});
		
		fNoteImage.setOnClickListener(new OnClickListener() {  //�ɱ༭
			
			@Override
			public void onClick(View v) {
				String content=fNoteEdit.getText().toString();
				fNoteEdit.setCursorVisible(true);	 //��ʾ���
				fNoteEdit.requestFocus();  //��ȡ����
				fNoteEdit.setSelection(content.length());
				//��ʾ�������
				imm.showSoftInput(fNoteEdit,InputMethodManager.SHOW_IMPLICIT);
			}
		});
		
		//�ϴ�����
		btnUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				uploadSave();
			}
		});
				
		//ȡ������
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/** 
	* @Title: focusHoursEdit 
	* @Description: �۽���Сʱ�ı���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��25�� ����5:02:08
	*/
	private void focusHoursEdit(){
		String content=fHoursEdit.getText().toString();
		fHoursEdit.setCursorVisible(true);	 //��ʾ���
		fHoursEdit.requestFocus();  //��ȡ����
		fHoursEdit.setSelection(content.length());
		imm.showSoftInput(fHoursEdit,InputMethodManager.SHOW_IMPLICIT); 		//��ʾ�������
	}
	
	//�������ڼ�����
	private DatePickerDialog.OnDateSetListener weekDateListener = new DatePickerDialog.OnDateSetListener(){		
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  fWeekDateTView.setText(StringUtils.toShortDateString(cal.getTime()));
		}
	};
	
	/** 
	* @Title: bindView 
	* @Description: ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����9:25:07
	*/
	private void bindView(){
		if("Add".equals(fActionType)){  //˵������������
			if("DhAdd".equals(fAccess)){  //��ť���
				fWeekDate=StringUtils.toShortDateString(cal.getTime());  //Ĭ��Ϊ��������
			}
			
			if("DhList".equals(fAccess)){  //��ʱ�б����
				fWeekDate=fPassWeekDate;  //���ݹ���������
				fWeekDateImage.setEnabled(false);
			}
			
			if("DhListProject".equals(fAccess)){ //��Ŀ�б����  ʱ�䲻���޸�
				fWeekDate=fPassWeekDate;
				fWeekDateImage.setEnabled(false);
			}

			if("DhListType".equals(fAccess)){ //���������б����  ʱ�䲻���޸�  ��ĿҲ�����޸�
				fWeekDate=fPassWeekDate;
				fWeekDateImage.setEnabled(false);
				
				fProjectCode=fPassProjectCode;
				fProjectName=fPassProjectName;
				fProjectCodeTView.setText(fProjectCode);
				fProjectNameTView.setText(fProjectName);
				fProjectNameImage.setEnabled(false);
			}				
			fWeekDateTView.setText(fWeekDate);
		}
		else{ //˵�����޸Ĳ���
			fWeekDate=fPassWeekDate;
			new getDetailAsync().execute();
		}
	}

	/**
	 * @ClassName getDetailAsync
	 * @Description �첽��ȡʵ����Ϣ
	 * @author 21291
	 * @date 2014��10��30�� ����10:16:09
	 */
	private class getDetailAsync extends AsyncTask<Void, Void, DHDetailInfo>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected DHDetailInfo doInBackground(Void... params) {
			return getDetailByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(DHDetailInfo result) {
			super.onPostExecute(result);
			renderDetailView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getDetailByPost 
	* @Description: ��ȡ����ʵ����Ϣ
	* @param @param fDetailId
	* @param @return     
	* @return DHDetailInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����10:46:22
	*/
	private DHDetailInfo getDetailByPost(){
		dBusiness.setServiceUrl(detailUrl);
		dBusiness.setDhDetailInfo(new DHDetailInfo());
		
		DHDetailParamInfo dhDetailParamInfo=DHDetailParamInfo.getDHDetailParamInfo();
		dhDetailParamInfo.setFBillId(fBillId);
		dhDetailParamInfo.setFWeekDate(fWeekDate);
		dhDetailParamInfo.setFProjectCode(fPassProjectCode);
		dhDetailParamInfo.setFTypeId(fPassTypeId);
		return dBusiness.getDHDetailInfo(dhDetailParamInfo);
	}
	
	/** 
	* @Title: renderDetailView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param @param dhDetailInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����10:48:30
	*/
	private void renderDetailView(final DHDetailInfo dhDetailInfo){	
		if(!StringUtils.isEmpty(dhDetailInfo.getFWeekDate())){
			fWeekDate=dhDetailInfo.getFWeekDate();
			fWeekDateTView.setText(fWeekDate);
		}	
		if(!StringUtils.isEmpty(dhDetailInfo.getFProjectCode())){
			fProjectCode=dhDetailInfo.getFProjectCode();
			fProjectCodeTView.setText(fProjectCode);
		}	
		if(!StringUtils.isEmpty(dhDetailInfo.getFProjectName())){
			fProjectName=dhDetailInfo.getFProjectName();
			fProjectNameTView.setText(fProjectName);
		}	
		if(!StringUtils.isEmpty(dhDetailInfo.getFTypeId())){
			fTypeId=dhDetailInfo.getFTypeId();
			fTypeIdTView.setText(fTypeId);
		}		
		if(!StringUtils.isEmpty(dhDetailInfo.getFTypeName())){
			fTypeName=dhDetailInfo.getFTypeName();
			fTypeNameTView.setText(fTypeName);
		}	
		if(!StringUtils.isEmpty(dhDetailInfo.getFHours())){
			fHours=dhDetailInfo.getFHours();
			fHoursEdit.setText(fHours);
		}	
		if(!StringUtils.isEmpty(dhDetailInfo.getFNote())){
			fNote=dhDetailInfo.getFNote();
			fNoteEdit.setText(fNote);
		}	
	}
	
	/** 
	* @Title: verify 
	* @Description: ����֮ǰ��֤
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����4:27:52
	*/
	private boolean verify(){
		if(StringUtils.isEmpty(fWeekDateTView.getText().toString())){
			UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FWeekDate));
			return false;
		}
		if(StringUtils.isEmpty(fProjectCodeTView.getText().toString())){
			UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FProjectCode));
			return false;
		}
		if(StringUtils.isEmpty(fProjectNameTView.getText().toString())){
			UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FProjectName));
			return false;
		}
		if(StringUtils.isEmpty(fTypeNameTView.getText().toString())){
			UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FTypeName));
			return false;
		}
		
		if(StringUtils.isEmpty(fHoursEdit.getText().toString())){
			UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FHours));
			return false;
		}
		else{
			Double double1=Double.valueOf(fHoursEdit.getText().toString());
			if(double1 > 24){
				UIHelper.ToastMessage(DHDetailActivity.this, getResources().getString(R.string.dh_detail_verify_FHours_NotSurpass));
				focusHoursEdit();
				return false;
			}
		}
		
		return true;
	}
	
	/** 
	* @Title: getViewValue 
	* @Description: ��ȡ��ͼ�ؼ�ֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����1:18:08
	*/
	private void getViewValue(){
		fWeekDate=fWeekDateTView.getText().toString(); 
		fProjectCode=fProjectCodeTView.getText().toString();
		fProjectName=fProjectNameTView.getText().toString();
		fTypeId=fTypeIdTView.getText().toString();
		fTypeName=fTypeNameTView.getText().toString();
		fHours=fHoursEdit.getText().toString();
		fNote=fNoteEdit.getText().toString();
	}
	
	/** 
	* @Title: setModel 
	* @Description: ����ʵ������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����1:44:50
	*/
	private void setModel(){
		dhDetailInfo.setFBillId(fBillId);
		dhDetailInfo.setFItemNumber(fItemNumber);
		dhDetailInfo.setFWeekDate(fWeekDate);
		dhDetailInfo.setFProjectCode(fProjectCode);
		dhDetailInfo.setFProjectName(fProjectName);
		dhDetailInfo.setFTypeId(fTypeId);
		dhDetailInfo.setFTypeName(fTypeName);
		dhDetailInfo.setFHours(fHours);
		dhDetailInfo.setFNote(fNote);
	}
	
	/** 
	* @Title: uploadSave 
	* @Description: �ϴ�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����1:48:59
	*/
	private void uploadSave(){
		if(verify()){  //˵����֤ͨ��
			uploadDialog = new ProgressDialog(this);
			uploadDialog.setMessage(getResources().getString(R.string.dialog_uploading));
			uploadDialog.setCancelable(false);
			
			getViewValue();
			setModel();
			new uploadDetailAsync().execute(dhDetailInfo);
		}
	}
	
	/**
	 * @ClassName uploadDetailAsync
	 * @Description �ϴ����ݵ�������
	 * @author 21291
	 * @date 2014��10��30�� ����1:58:01
	 */
	private class uploadDetailAsync extends AsyncTask<DHDetailInfo,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			uploadDialog.show();
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(DHDetailInfo... params) {
			return uploadServer(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			uploadDialog.dismiss();
			showUploadResult(result);	
		}	
	}

	/** 
	* @Title: uploadServer 
	* @Description: ����ҵ���߼����ϴ�����
	* @param @param dhDetailInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����1:58:59
	*/
	private ResultMessage uploadServer(DHDetailInfo dhDetailInfo){
		dBusiness.setServiceUrl(uploadUrl);
		return dBusiness.uploadDHDetailInfo(dhDetailInfo);
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: �����ϴ���UI���
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:33:19
	*/
	private void showUploadResult(ResultMessage resultMessage){
		UIHelper.ToastMessage(DHDetailActivity.this, resultMessage.getResult());
		if(resultMessage.isIsSuccess()){  //˵���ϴ��ɹ�
			sendLogs(); //������־��Ϣ����ͳ��
		}

		// �ӳ�2�������б�ҳ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	if("Add".equals(fActionType)){  //˵������������
        			if("DhAdd".equals(fAccess)){  //��ť���
        				UIHelper.showDHList(DHDetailActivity.this,fItemNumber);
        			}
        			
        			if("DhList".equals(fAccess)){  //��ʱ�б����
        				Intent intent = new Intent();
		    			setResult(RESULT_OK, intent);
        			}
        			
        			if("DhListProject".equals(fAccess)){ //��Ŀ�б���� 
        				backIntent(fWeekValue,fPassWeekDate);
        			}

        			if("DhListType".equals(fAccess)){ //���������б����  
        				backIntent(fWeekValue,fPassWeekDate);
        			}					
        		}
        		else{ //˵�����޸Ĳ���
        			backIntent(DateHelper.getWeekOfDate(cal,StringUtils.toShortDate(fWeekDate)),fWeekDate);
        		}
        		finish();     
            }
        }, 2000);
	}
	
	/** 
	* @Title: backIntent 
	* @Description: ���ص�Intent
	* @param @param fWeekValue ������ֵ
	* @param @param fWeekDate  ��������   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��18�� ����4:14:49
	*/
	private void backIntent(final String fWeekValue,final String fWeekDate){
		if(DHListProjectActivity.getInstance()!=null){
			DHListProjectActivity.getInstance().finish();
		}
		UIHelper.showDHProjectList(DHDetailActivity.this,fItemNumber,fBillId,fWeekValue,fWeekDate,"edit");	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case AppContext.DEVELOP_HOURS_DETAIL_PROJECT:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fProjectCodeTView.setText(extras.getString(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE));
						fProjectNameTView.setText(extras.getString(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME));
						fTypeIdTView.setText("");
						fTypeNameTView.setText("");
					}
				}		
				break;
				
			case AppContext.DEVELOP_HOURS_DETAIL_TYPE:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fTypeIdTView.setText(extras.getString(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPEID));
						fTypeNameTView.setText(extras.getString(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPENAME));
					}
				}	
				break;	
			default:
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;				
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �ϴ���ʱ��Ϣ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����6:46:58
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_develop_hour));
		logInfo.setFActionName("add");
		logInfo.setFNote("dhdetail");
		UIHelper.sendLogs(DHDetailActivity.this,logInfo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHDetailActivity.this);
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
