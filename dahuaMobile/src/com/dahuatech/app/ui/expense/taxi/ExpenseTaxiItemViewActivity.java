package com.dahuatech.app.ui.expense.taxi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.GpsInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class ExpenseTaxiItemViewActivity extends MenuActivity {

	private final int ACTIVITY_MODIFY = 1;
	private Button btnUpload,btnUpdate;
	private String id,startTime,endTime,uploadFlag,startAddress,endAddress,autoFlag,startLocation,endLocation,amount;
	private String fItemNumber;
	private GPSDbAdapter mDbHelper;
	
	private String serviceUrl;  //�����ַ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		
		setContentView(R.layout.expense_taxiitemview);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEMAINACTIVITY;	
		btnUpload=(Button) findViewById(R.id.expense_itemview_imgbtnSave); //�ϴ���ť
		btnUpdate=(Button) findViewById(R.id.expense_itemview_imgbtnReject); //�޸İ�ť
		
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			 fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			 id = extras.getString(GPSDbAdapter.KEY_ROWID);
			 startTime = extras.getString(GPSDbAdapter.KEY_STARTTIME);
			 endTime = extras.getString(GPSDbAdapter.KEY_ENDTIME);
			 uploadFlag = extras.getString(GPSDbAdapter.KEY_UPLOADFLAG);
			 startAddress = extras.getString(GPSDbAdapter.KEY_STARTADDRESS);
			 endAddress = extras.getString(GPSDbAdapter.KEY_ENDADDRESS);
			 autoFlag=extras.getString(GPSDbAdapter.KEY_AUTOFLAG);
			 startLocation=extras.getString(GPSDbAdapter.KEY_STARTLOCATION);
			 endLocation=extras.getString(GPSDbAdapter.KEY_ENDLOCATION);
			 amount=extras.getString(GPSDbAdapter.KEY_AMOUNT);
			 init();
		}
	}
	
	/** 
	* @Title: init 
	* @Description: ��ʼ����ʾ�ı���Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��14�� ����2:19:59
	*/
	private void init(){
		if(!StringUtils.isEmpty(startTime)){
			TextView startTimeText = (TextView) findViewById(R.id.expense_itemview_ivTvStartTime);
			startTimeText.setText(startTime);
		}
		
		if(!StringUtils.isEmpty(endTime)){
			TextView endTimeText = (TextView) findViewById(R.id.expense_itemview_ivTvEndTime);
			endTimeText.setText(endTime);
		}
		if(!StringUtils.isEmpty(startAddress)){
			TextView startAddressText = (TextView)findViewById(R.id.expense_itemview_ivTvStartPos);
			startAddressText.setText(startAddress);
		}
		if(!StringUtils.isEmpty(endAddress)){
			TextView endAddressText = (TextView)findViewById(R.id.expense_itemview_ivTvEndPos);
			endAddressText.setText(endAddress);
		}
		if(!StringUtils.isEmpty(amount)){
			TextView amountText = (TextView)findViewById(R.id.expense_itemview_ivTvAmount);
			amountText.setText(amount);
		}
		
		if(!StringUtils.isEmpty(uploadFlag)){
			if("1".equals(uploadFlag)){  //˵�����Ѿ��ϴ�����ϢҪ����
				btnUpload.setVisibility(View.GONE);
				btnUpdate.setVisibility(View.GONE);
			}
			else 
			{
				//�ϴ�
				btnUpload.setOnClickListener(new OnClickListener() {  
					@Override
					public void onClick(View v) {
						upload();
					}
				});
				
				//�޸�
				btnUpdate.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						update();
					}
				});
			}
		}
	}
	
	/** 
	* @Title: upload 
	* @Description: �ϴ���������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��15�� ����4:42:27
	*/
	private void upload(){
		if(!((AppContext)getApplication()).isNetworkConnected()){ //�ж��Ƿ���������
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		if(StringUtils.isEmpty(fItemNumber)){  //Ա����Ϊ��
			UIHelper.ToastMessage(this, R.string.expense_upload_fail_notLogin);
			return;	
		}
		
		if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime) && !StringUtils.isEmpty(startAddress) && !StringUtils.isEmpty(endAddress) && !StringUtils.isEmpty(amount)){
			uploadToService(fItemNumber);
		}
		else {
			UIHelper.ToastMessage(this, R.string.expense_upload_fail_recordnotFull);
			return;	
		}
	}
	
	/** 
    * @Title: uploadToService 
    * @Description: �ϴ�������ϵͳ����ȥ
    * @param @param userId     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��5��13�� ����7:42:48
    */
    private void uploadToService(final String userId){
		// ʹ��Message+Hander.sendMessage()������Ϣ
		new Thread(new Runnable() {
			@Override
			public void run() {
				//��ȡGPSʵ����
				GpsInfo info = new GpsInfo();
				info.setUserId(userId);
				info.setStartTime(startTime);
				info.setEndTime(endTime);	
				info.setStartPlace(startAddress);
				info.setEndPlace(endAddress);
				info.setAmount(amount);
				if(autoFlag.equals("manual")){
					info.setStartLocation("");
					info.setEndLocation("");
					info.setAutoFlag("manual");
				}
				else {
					info.setStartLocation(startLocation);
					info.setEndLocation(endLocation);
					info.setAutoFlag("automatic");
				}
				info.setCause("");
				
				Message msg = new Message();
				try {
					// ���������Ϣ����Ϣ������
					FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseTaxiItemViewActivity.this);
					ExpenseBusiness exBusiness  = (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
					ResultMessage res=exBusiness.upload(info);
					msg.obj = res;
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				mHandler.sendMessage(msg);
			}
		}).start();	
    }
		
	
	/** 
	* @Fields mHandler : �첽�߳���Ϣ����
	*/ 
	@SuppressLint("HandlerLeak")
	private final Handler mHandler=new Handler(){
		//��Ϣ����
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1 && msg.obj!=null){
				ResultMessage res=(ResultMessage)msg.obj;
				if(res.isIsSuccess()){
					int rowId = Integer.parseInt(id);
					//�����ϴ���־Ϊ�Ѿ��ϴ�
					mDbHelper.updateGpsdb(rowId, "1");
					UIHelper.ToastMessage(ExpenseTaxiItemViewActivity.this, R.string.expense_upload_success);
				}
				else {
					UIHelper.ToastMessage(ExpenseTaxiItemViewActivity.this, R.string.expense_upload_fail_reason+res.getResult());
				}		
			}
			else {
				UIHelper.ToastMessage(ExpenseTaxiItemViewActivity.this, R.string.expense_upload_fail);
			}
			
			// �ӳ�2�������б�ҳ��
	        new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	mDbHelper.closeSqlLite();
	            	Intent mIntent = new Intent();
	            	setResult(RESULT_OK, mIntent);
	        		finish();
	            }
	        }, 2000);	
		}
	};
	
	/** 
	* @Title: update 
	* @Description: �޸�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��15�� ����4:41:38
	*/
	private void update(){
		Intent mIntent=new Intent(ExpenseTaxiItemViewActivity.this,ExpenseTaxiInputActivity.class);
		mIntent.putExtra(GPSDbAdapter.KEY_ROWID, id);
		mIntent.putExtra(GPSDbAdapter.KEY_STARTTIME, startTime);
		mIntent.putExtra(GPSDbAdapter.KEY_ENDTIME, endTime);
		mIntent.putExtra(GPSDbAdapter.KEY_STARTADDRESS, startAddress);
		mIntent.putExtra(GPSDbAdapter.KEY_ENDADDRESS, endAddress);
		mIntent.putExtra(GPSDbAdapter.KEY_AMOUNT, amount);
		
		startActivityForResult(mIntent,ACTIVITY_MODIFY);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: flashItem 
	* @Description: ˢ�±�����¼
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��16�� ����8:50:49
	*/
	private void flashItem(){
		int rowId = Integer.parseInt(id);
		Cursor cursor = mDbHelper.getGpsdb(rowId);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			int startTimeIndex = cursor.getColumnIndex(GPSDbAdapter.KEY_STARTTIME);  
			int endTimeIndex = cursor.getColumnIndex(GPSDbAdapter.KEY_ENDTIME);
			int startAddressIndex = cursor.getColumnIndex(GPSDbAdapter.KEY_STARTADDRESS);
			int endAddressIndex = cursor.getColumnIndex(GPSDbAdapter.KEY_ENDADDRESS);
			int uploadFlagIndex=cursor.getColumnIndex(GPSDbAdapter.KEY_UPLOADFLAG);
			int amountIndex=cursor.getColumnIndex(GPSDbAdapter.KEY_AMOUNT);
					
			startTime = cursor.getString(startTimeIndex);  
			endTime = cursor.getString(endTimeIndex);
			startAddress = cursor.getString(startAddressIndex);
			endAddress = cursor.getString(endAddressIndex);	
			uploadFlag=cursor.getString(uploadFlagIndex);
			amount=cursor.getString(amountIndex);
		}
		init();
		cursor.close();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ACTIVITY_MODIFY && resultCode == RESULT_OK){
			flashItem();
		}
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
}
