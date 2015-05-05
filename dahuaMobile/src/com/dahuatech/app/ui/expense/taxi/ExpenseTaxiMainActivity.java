package com.dahuatech.app.ui.expense.taxi;

import java.util.Calendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.common.AmapConstants;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.CustomDialog;


/**
 * @ClassName ExpenseMainActivity
 * @Description �򳵱�����ҳ��
 * @author 21291
 * @date 2014��5��13�� ����2:08:31
 */
public class ExpenseTaxiMainActivity extends MenuActivity implements AMapLocationListener {
	private TextView mLocationMeter,mLocationAddress,mLocationStatus;
	private String locationMeter,locationAddress;
	private ImageButton imgbtnJurney;							  //�˳�����
	
	private String fItemNumber,startTime,endTime,startAddress,endAddress;
	private String amount="0";
	
	private SharedPreferences sp; 
	private Editor editor;
	private AppContext appContext;  							  //����ȫ�ֱ�������ѡ�����

	private boolean isLocation=false;							  //�ж��Ƿ�λ�ɹ�
	private boolean isJurney;								 	  //�Ƿ��Ѿ������򳵿���
	
	private static ExpenseTaxiMainActivity mInstance;
	public static ExpenseTaxiMainActivity getInstance() {
		return mInstance;
	}
	
	private LocationManagerProxy mLocationManagerProxy;          //�ߵµ�ͼ��λ
	private Random mRandom=new Random();						 //�����
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);	
		mInstance = this;
		setContentView(R.layout.expense_taximain);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		appContext = (AppContext)this.getApplication(); //��ʼ��ȫ�ֱ���
		if(!appContext.isNetworkConnected()){ 	//�ж��Ƿ�����������
			UIHelper.ToastMessage(ExpenseTaxiMainActivity.this,getResources().getString(R.string.network_not_connected));
			return;
		}
		Bundle extras = getIntent().getExtras(); //��ȡ������Ϣ
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);
		editor=sp.edit();
		locationMeter=locationAddress="";
		imgbtnJurney = (ImageButton) this.findViewById(R.id.imgbtnJurney);
		mLocationMeter=(TextView)findViewById(R.id.GPS_Meter);
		mLocationAddress=(TextView)findViewById(R.id.Addr_Desc);
		mLocationStatus=(TextView)findViewById(R.id.GPS_Status);
		mLocationStatus.setText(getResources().getString(R.string.expense_taxi_addr_querying));
	
		isJurney=sp.getBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, false);
		if(isJurney){ //�ж��Ƿ��Ѿ����ô򳵰�ť
			imgbtnJurney.setBackgroundResource(R.drawable.btn_on);
			startAddress=sp.getString(AppContext.IS_EXPENSE_ADDRESS_KEY, "");
			startTime=sp.getString(AppContext.IS_EXPENSE_TIME_KEY, "");
		}
		imgbtnJurney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�ж��Ƿ�λ
				if (!isLocation && StringUtils.isEmpty(locationAddress)){
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_query_addr_failed));
					return;
				}
				if(imgbtnJurney.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.btn_off).getConstantState()))
				{
					//�򿪿��ؿ�ʼ��
					imgbtnJurney.setBackgroundResource(R.drawable.btn_on);
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_start_taxi));
					isJurney=true;
					
					//���濪ʼ���ݵ����ݿ�
					startAddress = locationAddress;
					startTime = DateFormat.format("yyyy-MM-dd",Calendar.getInstance().getTime()).toString();
					editor.putBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, true);
					editor.putString(AppContext.IS_EXPENSE_ADDRESS_KEY, startAddress);
					editor.putString(AppContext.IS_EXPENSE_TIME_KEY, startTime);
					editor.commit();
				}
				else {
					//�رտ��ؽ�����
					imgbtnJurney.setBackgroundResource(R.drawable.btn_off);
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_end_taxi));
					isJurney=false;
					new Handler().postDelayed(new Runnable() { // �ӳ�1��ִ��
			            @Override
			            public void run() {
			            	showAlertDialog();	
			            }
			        },1000);	
				}	
			}
		});	
		initLocation();
		startRequestLocation();
		sendLogs("access");	//������־��Ϣ����ͳ��
	}
	
	/** 
	* @Title: initLocation 
	* @Description: ��ʼ����λ��Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����10:43:42
	*/
	private void initLocation(){
		// ��ʼ����λ�����û�϶�λ
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(true);	
	}
	
	/** 
	* @Title: startRequestLocation 
	* @Description: ��ʼ����λ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����10:46:07
	*/
	private void startRequestLocation(){
		if(mLocationManagerProxy!=null){
			//�˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����ÿ��10����������λ�������볬��15��ʱ������������λ
			mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND, AmapConstants.FIXMETER, this);
		}
	}
	
	/** 
	* @Title: stopRequestLocation 
	* @Description: ֹͣ��λ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����10:48:55
	*/
	private void stopRequestLocation(){
		if(mLocationManagerProxy!=null){	
			mLocationManagerProxy.removeUpdates(this); //�Ƴ���λ����	
			mLocationManagerProxy.destroy(); // ���ٶ�λ
		}
		mLocationManagerProxy=null;
	}
	
	@Override
	public void onLocationChanged(Location location) {
	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
			// ��λ�ɹ��ص���Ϣ�����������Ϣ
			locationMeter=amapLocation.getLatitude() + "," + amapLocation.getLongitude();
			locationAddress=amapLocation.getAddress();
			isLocation=true;
		}
		initLocationView();
	}
	
	/** 
	* @Title: initLocationView 
	* @Description: ��ʼ��λ��ͼֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����10:56:15
	*/
	private void initLocationView(){  
		mLocationMeter.setText(locationMeter);
		mLocationAddress.setText(getResources().getString(R.string.expense_taxi_addr_descs)+locationAddress);
		if(isLocation){
			mLocationStatus.setText(getResources().getString(R.string.expense_taxi_query_addr_success));
		}
		else{
			mLocationStatus.setText(getResources().getString(R.string.expense_taxi_addr_querying));
		}
	}
	
	/** 
	* @Title: showAlertDialog 
	* @Description: ��ʾ��ʾ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��24�� ����11:39:00
	*/
	public void showAlertDialog(){
		final CustomDialog.Builder builder = new CustomDialog.Builder(ExpenseTaxiMainActivity.this);
		builder.setTitle(getResources().getString(R.string.expense_taxi_amount));
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//������Ĳ�������
				if(!StringUtils.isEmpty(builder.getEditTextStr()))
					amount =builder.getEditTextStr();
				newOffBtnJurney();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton(R.string.cancle,new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				amount="0";
				editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY);
				editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY);
				editor.remove(AppContext.IS_EXPENSE_TIME_KEY);
				editor.commit();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/** 
	* @Title: newOffBtnJurney 
	* @Description: �򳵼�¼��ת����ˮ��дҳ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��13�� ����5:24:27
	*/
	private void newOffBtnJurney(){
		endAddress = locationAddress;
		endTime = DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
		editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY);
		editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY);
		editor.remove(AppContext.IS_EXPENSE_TIME_KEY);
		editor.commit();
		redirectToFlow(amount,startAddress,endAddress,startTime,endTime);
	}
    
    /** 
    * @Title: redirectToFlow 
    * @Description: ��ת����ˮҳ��
    * @param @param amount
    * @param @param fStartAddress
    * @param @param fEndAddress
    * @param @param fStartTime
    * @param @param fEndTime     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��10��13�� ����4:58:12
    */
    private void redirectToFlow(String amount,String fStartAddress,String fEndAddress,String fStartTime,String fEndTime){
    	UIHelper.redirectToExpenseFlow(ExpenseTaxiMainActivity.this,"Taxi",fItemNumber,"",amount,fStartAddress,fEndAddress,fStartTime,fEndTime);
    }
    
	/** 
	* @Title: alertExit 
	* @Description: �˳���ҳ�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��15�� ����11:31:05
	*/
	@SuppressLint("InlinedApi")
	private void alertExit(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseTaxiMainActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(getResources().getString(R.string.expense_taxi_gps_exit));
		builder.setPositiveButton(R.string.sure,
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
					dialog.dismiss();
				}
			});
		
		builder.setNegativeButton(R.string.cancle,new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ����򳵱���ʱ��������־��¼��������
	* @param @param typeName ��������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����2:45:02
	*/
	private void sendLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_taxi));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseTaxiMainActivity.this,logInfo);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if(isJurney){ //ֻ�����ڴ򳵵�ʱ���˳�����
					alertExit();
				}
				else{
					commonMenu.toggle();
				}
				return true;				
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		//���͹㲥,�ж��Ƿ�����״̬�����ı�
		Intent intent = new Intent("com.dahuatech.app.action.APPWIFI_CHANGE");  
		sendBroadcast(intent);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		stopRequestLocation();
		super.onPause();
	}

	@Override
	protected void onRestart() {
		initLocation();
		mLocationManagerProxy.removeUpdates(this);
		int randomTime=mRandom.nextInt(1000);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND+randomTime, AmapConstants.FIXMETER, this);
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		if(mInstance!=null){
			mInstance=null;
		}
		super.onDestroy();	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //���ؼ�
			if(isJurney){ //ֻ�����ڴ򳵵�ʱ���˳�����
				alertExit();
			}	
    	}
		return super.onKeyDown(keyCode, event);
	}
}
