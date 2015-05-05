package com.dahuatech.app.ui.meeting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TimePicker;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.meeting.MeetingDetailInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName MeetingDetailActivity
 * @Description �ҵĻ�������ҳ��
 * @author 21291
 * @date 2014��9��11�� ����10:41:46
 */
public class MeetingDetailActivity extends MenuActivity {
	private ProgressDialog dialog;     				//Ĭ�ϵ�����
	private TextView fApplyNameTView,fApplyDeptTView,fMeetingDateTView,fMeetingStartTView,fMeetingEndTView;
	private TextView fMeetingMasterIdTView,fMeetingMasterNameTView;
	private TextView fMeetingRoomIdTView,fMeetingRoomTView,fMeetingRoomIpTView;
	private TextView fMeetingPersonTView;
	private EditText fMeetingNameEText;
	private ImageView fMeetingImageView,fMasterImageView,fRoomImageView,fPersonImageView;
	private Button btnSave,btnCancle;   			//����/�޸ĺ�ȡ����ť
	
	private String fOrderId="";						//������������
	private String fApplyNumber,fApplyName,fApplyDept,fMeetingName;  					
	private String fMeetingDate,fMeetingStart,fMeetingEnd;					
	private String fMeetingMasterId,fMeetingMasterName;				
	private String fMeetingRoom,fMeetingRoomId,fMeetingRoomIp;				
	private String fSubEntrys;
	
	private MeetingBusiness mBusiness;				//ҵ���߼���
	private MeetingDetailInfo mDetailInfo;			//������ϸ��
	private List<MeetingPersonInfo> personList;		//������Ա������
	private Calendar cal;							//������
	
	private String fStatus;							//����״̬ 0-�������˴���ֻ����1-���������޸�/����
	private String fItemNumber; 					//Ա����
	private String serviceDetailUrl;  				//��������ַ
	private String serviceUploadUrl;  				//�ϴ������ַ
	private AppContext appContext; 					//ȫ��Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_detail);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fStatus=extras.getString(AppContext.MEETING_DETAIL_FSTATUS);
			fOrderId=extras.getString(AppContext.MEETING_DETAIL_FORDERID);
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		//��ȡ�����ַ  ��������,��ȡĬ�ϳ�ʼ��Ϣ
		serviceDetailUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGINITACTIVITY;	
		if(!StringUtils.isEmpty(fOrderId)){ //�޸Ľ���,��ȡ����������Ϣ
			serviceDetailUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGDETAILACTIVITY;	
		}
		serviceUploadUrl=AppUrl.URL_API_HOST_ANDROID_UPLOADMEETINGDETAIL;
		
		initView();
		setListener();
		setStatus(fStatus);
		new getDetailAsync().execute(fOrderId);
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����2:47:30
	*/
	private void initView(){
		fApplyNameTView=(TextView)findViewById(R.id.meeting_detail_FApplyName);
		fApplyDeptTView=(TextView)findViewById(R.id.meeting_detail_FApplyDept);
		
		fMeetingNameEText=(EditText)findViewById(R.id.meeting_detail_FMeetingName);	
		fMeetingImageView=(ImageView)findViewById(R.id.meeting_detail_FMeetingName_ImageView);	
		
		fMeetingDateTView=(TextView)findViewById(R.id.meeting_detail_FMeetingDate);
		fMeetingStartTView=(TextView)findViewById(R.id.meeting_detail_FMeetingStart);
		fMeetingEndTView=(TextView)findViewById(R.id.meeting_detail_FMeetingEnd);
		
		fMeetingMasterIdTView=(TextView)findViewById(R.id.meeting_detail_FMeetingMasterId);
		fMeetingMasterNameTView=(TextView)findViewById(R.id.meeting_detail_FMeetingMasterName);
		fMasterImageView=(ImageView)findViewById(R.id.meeting_detail_FMeetingMaster_ImageView);
		
		fMeetingRoomIdTView=(TextView)findViewById(R.id.meeting_detail_FMeetingRoomId);
		fMeetingRoomIpTView=(TextView)findViewById(R.id.meeting_detail_FMeetingRoomIp);
		fMeetingRoomTView=(TextView)findViewById(R.id.meeting_detail_FMeetingRoomName);
		fRoomImageView=(ImageView)findViewById(R.id.meeting_detail_FMeetingRoom_ImageView);
		
		fMeetingPersonTView=(TextView)findViewById(R.id.meeting_detail_FMeetingPerson);
		fPersonImageView=(ImageView)findViewById(R.id.meeting_detail_FMeetingPerson_ImageView);
		
		btnSave=(Button)findViewById(R.id.meeting_detail_button_FSave);
		if(!StringUtils.isEmpty(fOrderId)){ //�޸Ľ���
			btnSave.setText(getResources().getString(R.string.meeting_detail_button_FUpload));
		}
		btnCancle=(Button)findViewById(R.id.meeting_detail_button_FCancle);
		
		mDetailInfo=MeetingDetailInfo.getMeetingDetailInfo(); 
		personList=new ArrayList<MeetingPersonInfo>();
		cal = Calendar.getInstance(); //����
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MeetingDetailActivity.this);
		mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness",serviceDetailUrl);
		
		fApplyNumber=fApplyName=fApplyDept=fMeetingName="";
		fMeetingDate=fMeetingStart=fMeetingEnd="";
		fMeetingMasterId=fMeetingMasterName="";
		fMeetingRoom=fMeetingRoomId=fMeetingRoomIp=fSubEntrys="";
	}
	
	/** 
	* @Title: setListener 
	* @Description: ���ÿؼ��¼�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����3:24:31
	*/
	private void setListener(){
		
		//����۽��༭�ı���
		fMeetingImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content=fMeetingNameEText.getText().toString();
				fMeetingNameEText.setCursorVisible(true);	 //��ʾ���
				fMeetingNameEText.requestFocus();  //��ȡ����
				fMeetingNameEText.setSelection(content.length());
				//��ʾ�������
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(fMeetingNameEText,InputMethodManager.SHOW_IMPLICIT);
			}
		});
		
		//������������������
		fMasterImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showMeetingMasterSearch(MeetingDetailActivity.this,"","Single");		
			}
		});
		
		//������������������
		fRoomImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verifyRoom()){
					UIHelper.showMeetingRoomSearch(MeetingDetailActivity.this,fItemNumber,fMeetingDateTView.getText().toString(),fMeetingStartTView.getText().toString(),fMeetingEndTView.getText().toString());
				}			
			}
		});
				
		//������Ա��������
		fPersonImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showMeetingPersonSearch(MeetingDetailActivity.this,MeetingPersonInfo.ConvertToJson(personList),"All");		
			}
		});
		
		//�������޸Ĳ���
		btnSave.setOnClickListener(new OnClickListener() {
			
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
	* @Title: onMeetingDate 
	* @Description: ��������ѡ���¼�
	* @param @param v     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����3:30:39
	*/
	public void onMeetingDate(View v) {
		new DatePickerDialog(MeetingDetailActivity.this ,meetingDateListener,
				cal.get(Calendar.YEAR ),   
	            cal.get(Calendar.MONTH ),   
	            cal.get(Calendar.DAY_OF_MONTH )).show();   
	}
	
	//�������ڼ�����
	private DatePickerDialog.OnDateSetListener meetingDateListener = new DatePickerDialog.OnDateSetListener(){		
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  fMeetingDateTView.setText(StringUtils.toShortDateString(cal.getTime()));
		}
	};
	
	/** 
	* @Title: onMeetingStart 
	* @Description: ��ʼʱ��ѡ���¼�
	* @param @param v     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����3:30:50
	*/
	public void onMeetingStart(View v) {
		new TimePickerDialog(MeetingDetailActivity.this,meetingStartListener,
				cal.get(Calendar.HOUR_OF_DAY ),   
	            cal.get(Calendar.MINUTE ),true).show();
	} 
	
	//��ʼʱ�������
	private TimePickerDialog.OnTimeSetListener meetingStartListener=new TimePickerDialog.OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			 cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
			 cal.set(Calendar.MINUTE,minute);
			 cal.set(Calendar.SECOND, 0);                        
			 cal.set(Calendar.MILLISECOND, 0);   
			 fMeetingStartTView.setText(hourOfDay+":"+minute);
		}  
	};
	
	/** 
	* @Title: onMeetingEnd 
	* @Description: ����ʱ��ѡ���¼�
	* @param @param v     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����3:31:02
	*/
	public void onMeetingEnd(View v) {
		new TimePickerDialog(MeetingDetailActivity.this,meetingEndListener,
				cal.get(Calendar.HOUR_OF_DAY ),   
	            cal.get(Calendar.MINUTE ),true).show();
	} 
	
	//����ʱ�������
	private TimePickerDialog.OnTimeSetListener meetingEndListener=new TimePickerDialog.OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			 cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
			 cal.set(Calendar.MINUTE,minute);
			 cal.set(Calendar.SECOND, 0);                        
			 cal.set(Calendar.MILLISECOND, 0);   
			 fMeetingEndTView.setText(hourOfDay+":"+minute);
		}  
	};
	
	/** 
	* @Title: setStatus 
	* @Description: ��������ҳ��״̬
	* @param @param fStatus     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����12:01:53
	*/
	private void setStatus(final String fStatus){
		if("0".equals(fStatus)){  //˵�������˴����� ֻ��״̬
			fMeetingNameEText.setEnabled(false);
			fMeetingDateTView.setEnabled(false);
			fMeetingStartTView.setEnabled(false);
			fMeetingEndTView.setEnabled(false);
			
			fMeetingImageView.setVisibility(View.GONE);
			fMasterImageView.setVisibility(View.GONE);
			fRoomImageView.setVisibility(View.GONE);
			fPersonImageView.setVisibility(View.GONE);
			btnSave.setVisibility(View.GONE);
			btnCancle.setVisibility(View.GONE);
		}
	}
	
	/**
	 * @ClassName getDetailAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��9��18�� ����9:25:16
	 */
	private class getDetailAsync extends AsyncTask<String, Void, MeetingDetailInfo>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected MeetingDetailInfo doInBackground(String... params) {
			return getDetailByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(MeetingDetailInfo result) {
			super.onPostExecute(result);
			renderDetailView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getDetailByPost 
	* @Description: ��ȡĬ�ϳ�ʼֵ��Ϣ��ʵ��������Ϣ
	* @param @param fOrderId
	* @param @return     
	* @return MeetingDetailInfo    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����9:28:25
	*/
	private MeetingDetailInfo getDetailByPost(final String fOrderId){
		MeetingDetailInfo resultDetailInfo;
		mBusiness.setMeetingDetailInfo(new MeetingDetailInfo());
		if(!StringUtils.isEmpty(fOrderId)){
			resultDetailInfo= mBusiness.getMeetingDetailInfo(fOrderId);
		}
		else 
		{
			resultDetailInfo= mBusiness.getMeetingInitInfo(fItemNumber);
		}
		return resultDetailInfo;
	}
	
	/** 
	* @Title: renderDetailView 
	* @Description: ������ͼ�ؼ���ʼ����Ϣ
	* @param @param meDetailInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����9:42:23
	*/
	private void renderDetailView(final MeetingDetailInfo meDetailInfo){
		if(!StringUtils.isEmpty(meDetailInfo.getFApplyNumber())){
			fApplyNumber=meDetailInfo.getFApplyNumber();
		}		
		if(!StringUtils.isEmpty(meDetailInfo.getFApplyName())){
			fApplyName=meDetailInfo.getFApplyName();
			fApplyNameTView.setText(fApplyName);
		}				
		if(!StringUtils.isEmpty(meDetailInfo.getFApplyDept())){
			fApplyDept=meDetailInfo.getFApplyDept();
			fApplyDeptTView.setText(fApplyDept);
		}		
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingName())){
			fMeetingName=meDetailInfo.getFMeetingName();
			fMeetingNameEText.setText(fMeetingName);
		}		
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingDate())){
			fMeetingDate=meDetailInfo.getFMeetingDate();
			fMeetingDateTView.setText(fMeetingDate);
		}	
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingStart())){
			fMeetingStart=meDetailInfo.getFMeetingStart();
			fMeetingStartTView.setText(fMeetingStart);
		}	
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingEnd())){
			fMeetingEnd=meDetailInfo.getFMeetingEnd();
			fMeetingEndTView.setText(fMeetingEnd);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingMasterId())){
			fMeetingMasterId=meDetailInfo.getFMeetingMasterId();
			fMeetingMasterIdTView.setText(fMeetingMasterId);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingMasterName())){
			fMeetingMasterName=meDetailInfo.getFMeetingMasterName();
			fMeetingMasterNameTView.setText(fMeetingMasterName);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingRoom())){
			fMeetingRoom=meDetailInfo.getFMeetingRoom();
			fMeetingRoomTView.setText(fMeetingRoom);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingRoomId())){
			fMeetingRoomId=meDetailInfo.getFMeetingRoomId();
			fMeetingRoomIdTView.setText(fMeetingRoomId);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFMeetingRoomIp())){
			fMeetingRoomIp=meDetailInfo.getFMeetingRoomIp();
			fMeetingRoomIpTView.setText(fMeetingRoomIp);
		}
		if(!StringUtils.isEmpty(meDetailInfo.getFSubEntrys())){
			fSubEntrys=meDetailInfo.getFSubEntrys();
			showPersonList(fSubEntrys);
		}
	}
	
	/** 
	* @Title: verifyRoom 
	* @Description: ��������ת֮ǰ��֤
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����11:39:45
	*/
	private boolean verifyRoom(){
		if("����".equals(fMeetingDateTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_Room_FMeetingDate));
			return false;
		}
		if("��ʼʱ��".equals(fMeetingStartTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_Room_FMeetingStart));
			return false;
		}
		if("����ʱ��".equals(fMeetingEndTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_Room_FMeetingEnd));
			return false;
		}
		return true;
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
		if(StringUtils.isEmpty(fMeetingNameEText.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_FMeetingName));
			return false;
		}
		if("����".equals(fMeetingDateTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_FMeetingDate));
			return false;
		}
		if("��ʼʱ��".equals(fMeetingStartTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_FMeetingStart));
			return false;
		}
		if("����ʱ��".equals(fMeetingEndTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_FMeetingEnd));
			return false;
		}
		if(StringUtils.isEmpty(fMeetingRoomTView.getText().toString())){
			UIHelper.ToastMessage(MeetingDetailActivity.this, getResources().getString(R.string.meeting_detail_verify_FMeetingRoom));
			return false;
		}
		return true;
	}
	
	/** 
	* @Title: getControlValue 
	* @Description: ��ȡ�ؼ�ֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:15:23
	*/
	private void getControlValue(){
		fMeetingName=fMeetingNameEText.getText().toString();
		fMeetingDate=fMeetingDateTView.getText().toString();
		fMeetingStart=fMeetingStartTView.getText().toString();
		fMeetingEnd=fMeetingEndTView.getText().toString();
		fMeetingMasterId=fMeetingMasterIdTView.getText().toString();
		fMeetingMasterName=fMeetingMasterNameTView.getText().toString();
		fMeetingRoomId=fMeetingRoomIdTView.getText().toString();
		fMeetingRoom=fMeetingRoomTView.getText().toString();
		fMeetingRoomIp=fMeetingRoomIpTView.getText().toString();
		if(personList.size() >0){
			fSubEntrys=MeetingPersonInfo.ConvertToJson(personList);
		}
	}
	
	/** 
	* @Title: setModel 
	* @Description: ����ʵ������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:12:27
	*/
	private void setModel(){
		mDetailInfo.setFId(fOrderId);
		mDetailInfo.setFApplyNumber(fApplyNumber);
		mDetailInfo.setFApplyName(fApplyName);
		mDetailInfo.setFApplyDept(fApplyDept);
		mDetailInfo.setFMeetingName(fMeetingName);
		mDetailInfo.setFMeetingDate(fMeetingDate);
		mDetailInfo.setFMeetingStart(fMeetingStart);
		mDetailInfo.setFMeetingEnd(fMeetingEnd);
		mDetailInfo.setFMeetingMasterId(fMeetingMasterId);
		mDetailInfo.setFMeetingMasterName(fMeetingMasterName);
		mDetailInfo.setFMeetingRoom(fMeetingRoom);
		mDetailInfo.setFMeetingRoomId(fMeetingRoomId);
		mDetailInfo.setFMeetingRoomIp(fMeetingRoomIp);
		mDetailInfo.setFSubEntrys(fSubEntrys);
	}
	
	/** 
	* @Title: uploadSave 
	* @Description: �ϴ�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:11:09
	*/
	private void uploadSave(){
		if(verify()){  //˵����֤ͨ��
			getControlValue();
			setModel();
			new detailUploadAsync().execute(mDetailInfo);
		}
	}
	
	/**
	 * @ClassName detailUploadAsync
	 * @Description �ϴ����ݵ�������
	 * @author 21291
	 * @date 2014��9��19�� ����11:28:40
	 */
	private class detailUploadAsync extends AsyncTask<MeetingDetailInfo,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(MeetingDetailInfo... params) {
			return uploadServer(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showUploadResult(result);	
		}	
	}
	
	/** 
	* @Title: uploadServer 
	* @Description: ����ҵ���߼����ϴ�����
	* @param @param mDetailInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:30:22
	*/
	private ResultMessage uploadServer(MeetingDetailInfo mDetailInfo){
		mBusiness.setServiceUrl(serviceUploadUrl);
		return mBusiness.uploadMeetingDetail(mDetailInfo);
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
		UIHelper.ToastMessage(MeetingDetailActivity.this, resultMessage.getResult());
		if(resultMessage.isIsSuccess()){  //˵���ϴ��ɹ�
			
			sendLogs();	//������־��Ϣ����ͳ��
			// �ӳ�2�������б�ҳ��
	        new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	Intent intent = new Intent();
	    			setResult(RESULT_OK, intent);
	        		finish();     
	            }
	        }, 2000);
		}
	}
	
	/** 
	* @Title: showPersonList 
	* @Description: ���������Ա����
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����10:26:46
	*/
	private void showPersonList(final String fSubEntrys){
		try {
			Type listType = new TypeToken<ArrayList<MeetingPersonInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
		    personList=gson.fromJson(jsonArray.toString(), listType);
			fMeetingPersonTView.setText(getAttendPerson(personList));
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// �ӻ�����/��Աҳ��,�ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.MEETING_DETAIL_ROOM:  //������
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fMeetingRoomTView.setText(extras.getString(AppContext.MEETING_DETAIL_ROOM_NAME));
						fMeetingRoomIdTView.setText(extras.getString(AppContext.MEETING_DETAIL_ROOM_ID));
						fMeetingRoomIpTView.setText(extras.getString(AppContext.MEETING_DETAIL_ROOM_IP));
					}
				}			
				break;
			case AppContext.MEETING_DETAIL_PERSON: //������Ա
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						//��ȡ������Ϣ
						String transferStr=extras.getString(AppContext.MEETING_DETAIL_PERSON_LIST);
						String fFlag=extras.getString(AppContext.MEETING_DETAIL_PERSON_FLAG);
						if("All".equals(fFlag)){
							personList.clear();
						}
						personList.addAll(getPersonList(transferStr));
						personList=ListHelper.rDMeetingPerson(personList); //ȥ��
						fMeetingPersonTView.setText(getAttendPerson(personList));
					}
				}	
				break;
				
			case AppContext.MEETING_DETAIL_MASTER:  //������
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						//��ȡ������Ϣ
						String transferStr=extras.getString(AppContext.MEETING_DETAIL_PERSON_LIST);
						String fFlag=extras.getString(AppContext.MEETING_DETAIL_PERSON_FLAG);
						if("Single".equals(fFlag)){
							List<MeetingPersonInfo> transferList=getPersonList(transferStr);
							for (MeetingPersonInfo item : transferList) {
								fMeetingMasterIdTView.setText(item.getFItemNumber());
								fMeetingMasterNameTView.setText(item.getFItemName());
							}
						}
					}
				}	
				break;	
			default:
				break;
		}
	}
	
	/** 
	* @Title: getPersonList 
	* @Description: ��ȡ�Ѿ�ѡ�������Ա����
	* @param @param fTempList
	* @param @return     
	* @return List<MeetingPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����6:03:18
	*/
	private List<MeetingPersonInfo> getPersonList(final String fTempList){
		List<MeetingPersonInfo> resultList=new ArrayList<MeetingPersonInfo>();
		if(!StringUtils.isEmpty(fTempList)){
			try {
				Type listType = new TypeToken<ArrayList<MeetingPersonInfo>>(){}.getType();
				Gson gson = new GsonBuilder().create();
				JSONArray jsonArray= new JSONArray(fTempList);
				resultList=gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	/** 
	* @Title: getAttendPerson 
	* @Description: ��ȡ������Ա����
	* @param @param personList
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:01:28
	*/
	private String getAttendPerson(final List<MeetingPersonInfo> personList){
		int i=0;
		String personStr="";
		int personCount=personList.size();
		if(personCount > 0){
			StringBuffer sbBuffer = new StringBuffer();	
			for (MeetingPersonInfo item : personList) {
				if(i==personCount-1){
					sbBuffer.append(item.getFItemName());
				}
				else{
					sbBuffer.append(item.getFItemName()).append(",");
				}
				i++;
			}
			personStr=sbBuffer.toString();
		}
		return personStr;
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
		logInfo.setFModuleName(getResources().getString(R.string.log_mymeeting));
		logInfo.setFActionName("upload");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MeetingDetailActivity.this,logInfo);
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
