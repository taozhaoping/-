package com.dahuatech.app.ui.attendance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.attendance.AdCheckInfo;
import com.dahuatech.app.bean.attendance.AdCheckStatusInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.AmapConstants;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.AdAmapSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AdCheckInActivity
 * @Description ǩ��ǩ������
 * @author 21291
 * @date 2014��12��16�� ����2:11:39
 */
public class AdCheckInActivity extends MenuActivity implements LocationSource,AMapLocationListener {
	private static AdCheckInActivity mInstance;
	public static AdCheckInActivity getInstance(){
		return mInstance;
	}
	private static final String GEOFENCE_BROADCAST_ACTION = "geo_fence.broadcast_action";
	
	private AMap mAMap;										//��ͼ��ʾ��
	private LocationManagerProxy mLocationManagerProxy;		//��λʵ��
	private PendingIntent mpIntent;							//��װ�ķ�����ͼ
	private Circle mCircle;									//ԲȦ				
	private OnLocationChangedListener mListener;			//�����¼�
	private Random mRandom=new Random();					//�����
	private List<AdAmapInfo> adAmapList;					//�����ĵص㼯��
	
	private String fAddress;								//��λ��ַ
	private double fLatitude,fLongitude;					//��λ��γ��
	private int fAttendId,fAttendStatus;					//����ID,����״̬	
	private String fCheckInTime,fCheckOutTime;				//ǩ��/ǩ��ʱ��
	
	private AttendanceBusiness aBusiness;					//ҵ���߼���
	private AdCheckInfo aCheckInfo;							//�ϴ�ʵ����
	
	private String fItemNumber;  							//Ա����
	private String serviceUrl,uploadUrl;  					//ǩ��/ǩ����ַ,�ϴ���ַ
	private ProgressDialog uploadDialog,amapDialog;     	//�ϴ�������,��ͼ����ͼ
	
	private Button checkInBtn,btnAdList;					//�򿨰�ť,��ϸ����
	private TextView checkText;								//ǩ��/ǩ�����
	private boolean isMockLocation;							//ģ��λ���Ƿ��� Ĭ�ϲ�����
	
	private SharedPreferences aMapSp;						//�����ļ�
	private AppContext appContext; 							//ȫ��Context
	private int checkInCount=0;								//�򿨴����� Ĭ��Ϊ0
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.attendance_check_in);
		
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
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_GETCHECKSTATUSACTIVITY;	//��ȡǩ��/ǩ����ַ
		uploadUrl=AppUrl.URL_API_HOST_ANDROID_UPLOADCHECKACTIVITY;		//�����ϴ���ַ
		aMapSp=getSharedPreferences(AppContext.ADCHECKINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_verifying));
		uploadDialog.setCancelable(false);
		
		amapDialog = new ProgressDialog(this);
		amapDialog.setMessage(getResources().getString(R.string.dialog_amaploading));
		amapDialog.setCancelable(false);
		
		initAdAmap();				//��ʼ���򿨵ص㼯��
		initView();					//��ʼ����ͼ�ؼ�
		initCheckStatus();			//��ʼ������״̬	
		initAMap();					//��ʼ��AMap����
		initMarker();				//��ʼ����λ��־
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:37:30
	*/
	private void initView(){
		checkInBtn=(Button)findViewById(R.id.attendance_check_btn);
		checkInBtn.setEnabled(false);
		btnAdList=(Button)findViewById(R.id.attendance_check_list);
		checkText=(TextView)findViewById(R.id.attendance_check_text);
		setViewEvent();
		
		aCheckInfo=AdCheckInfo.getAdCheckInfo();
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(AdCheckInActivity.this);
		aBusiness= (AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness","");
	}
	
	/** 
	* @Title: initAdAmap 
	* @Description: ��ʼ���򿨵ص㼯��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����11:33:03
	*/
	private void initAdAmap(){
		adAmapList=new ArrayList<AdAmapInfo>();
		String aMapAddress=aMapSp.getString(AppContext.AD_AMAP_ADDRESS_KEY, "");
		if(!StringUtils.isEmpty(aMapAddress)){  //��Ϊ��
			try {
				Type listType = new TypeToken<ArrayList<AdAmapInfo>>(){}.getType();
				Gson gson = GsonHelper.getInstance();
				JSONArray jsonArray= new JSONArray(aMapAddress);
				adAmapList=gson.fromJson(jsonArray.toString(), listType);
						
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	/** 
	* @Title: setViewEvent 
	* @Description: ������ͼ�ؼ��¼�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����4:56:13
	*/
	private void setViewEvent(){
		//��ǩ��/ǩ��
		checkInBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isMockLocation){   //˵��ģ��λ��û��
					final AdAmapSpinnerDialog mSpinnerDialog=new AdAmapSpinnerDialog(AdCheckInActivity.this,adAmapList,new ISpinnerListener(){

						@Override
						public void rejectOk(int n, RejectNodeInfo reInfo) {}

						@Override
						public void cancelled() {}

						@Override
						public void dHWeekOk(int n, String itemText,DHWeekInfo dhWeekInfo) {}

						@Override
						public void adAmapOk(int n, AdAmapInfo adAmapInfo) {
							uploadDialog.show();
							checkInCount=0;
							float radius=Float.valueOf(adAmapInfo.getFRadius()); 
							verifyGeo(getLatLng(adAmapInfo.getFLatitude(),adAmapInfo.getFLongitude()),radius);
						}	
					});
					mSpinnerDialog.setTitle(getResources().getString(R.string.ad_check_title));
					mSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
					mSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
					mSpinnerDialog.show();	
				}
				else{
					UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_open_mock_location));
				}
			}
		});
		
		//��ϸ
		btnAdList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showAttendanceList(AdCheckInActivity.this,fItemNumber);
			}
		});
	}
	
	/** 
	* @Title: getLatLng 
	* @Description: ��ȡ��γ��������
	* @param @param fLatitude γ������
	* @param @param fLongitude ��������
	* @param @return     
	* @return LatLng    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����2:58:53
	*/
	private LatLng getLatLng(String fLatitude,String fLongitude){
		return new LatLng(StringUtils.toDouble(fLatitude, 0), StringUtils.toDouble(fLongitude, 0));
	}

	/** 
	* @Title: initCheckStatus 
	* @Description: �ж�ǩ��/ǩ��״̬
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����11:50:37
	*/
	private void initCheckStatus(){
		new getCheckAsync().execute();
	}
	
	/**
	 * @ClassName getCheckAsync
	 * @Description ��ȡ�Ƿ��Ѿ�ǩ��/ǩ��
	 * @author 21291
	 * @date 2014��12��22�� ����11:44:40
	 */
	private class getCheckAsync extends AsyncTask<Void, Void, AdCheckStatusInfo>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected AdCheckStatusInfo doInBackground(Void... params) {
			return getCheckByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(AdCheckStatusInfo result) {
			super.onPostExecute(result);
			renderCheckView(result);	
		}	
	}
	
	/** 
	* @Title: getCheckByPost 
	* @Description: ��ȡʵ����Ϣ
	* @param @return     
	* @return AdCheckStatusInfo    
	* @throws 
	* @author 21291
	* @date 2014��12��30�� ����9:38:43
	*/
	private  AdCheckStatusInfo getCheckByPost(){
		aBusiness.setServiceUrl(serviceUrl);
		return aBusiness.getCheckStausData(fItemNumber);
	}
	
	/** 
	* @Title: renderCheckView 
	* @Description: ��ȡ������ǩ��/ǩ��״̬
	* @param @param aStatusInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��30�� ����9:39:18
	*/
	private void renderCheckView(AdCheckStatusInfo aStatusInfo){
		fAttendId=aStatusInfo.getFAttendId();
		fAttendStatus=aStatusInfo.getFStatus();
		fCheckInTime=aStatusInfo.getFCheckInTime();
		fCheckOutTime=aStatusInfo.getFCheckOutTime();
		setAttendStatus();
	}
	
	/** 
	* @Title: setAttendStatus 
	* @Description: ���ÿ���״̬
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��30�� ����2:08:39
	*/
	private void setAttendStatus(){
		switch (fAttendStatus) {
			case 0:	//����δ�� 
				checkInBtn.setText(getResources().getString(R.string.attendance_check_in));
				checkText.setText("�ϰ�δǩ��,�°�δǩ��");
				break;
				
			case 1: //��������δǩ�� ������ǩ��
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText("�ϰ�δǩ��,"+fCheckOutTime+"ǩ��");
				break;
	
			case 2:	//�����δǩ��
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText(fCheckInTime+"ǩ��,�°�δǩ��");
				break;

			case 3:	//�����Ѵ���ǩ��
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText(fCheckInTime+"ǩ��,"+fCheckOutTime+"ǩ��");
				break;
	
			default:
				break;
		}
	}
	
	/** 
	* @Title: initAmap 
	* @Description: ��ʼ��AMap������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����2:32:18
	*/
	private void initAMap() {
		if (mAMap == null) {
			mAMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.attendance_check_map)).getMap();			
		}
		MyLocationStyle myLocationStyle = new MyLocationStyle();		//�Զ���ϵͳ��λС����
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));// ����С�����ͼ��
		myLocationStyle.strokeColor(Color.BLACK);						//����Բ�εı߿���ɫ
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180)); 	//����Բ�ε������ɫ
		myLocationStyle.strokeWidth(0.1f);								//����Բ�εı߿��ϸ
		mAMap.setMyLocationStyle(myLocationStyle);
		mAMap.setLocationSource(this);									//���ö�λ����
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);			//����Ĭ�϶�λ��ť�Ƿ���ʾ
		mAMap.setMyLocationEnabled(true);								//����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
		mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);   			//���ö�λ���ͣ�ֻ�ڵ�һ�ζ�λ�ƶ�����ͼ����	
	}
	
	/** 
	* @Title: initMarker 
	* @Description: ��ʼ����Ƕ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����4:29:35
	*/
	private void initMarker() {
		if(adAmapList.size() > 0){  //˵�������ĵ�
			MarkerOptions markOptions = new MarkerOptions(); //������λλ�ñ�־
			BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location_point1));
			markOptions.icon(bitmapDescriptor);
			markOptions.anchor(0.5f, 0.5f);
			
			for (AdAmapInfo item : adAmapList) {
				Marker marker=mAMap.addMarker(markOptions);
				marker.setPosition(getLatLng(item.getFLatitude(),item.getFLongitude()));
			}
		}
	}
	
	/** 
	* @Title: initBroadcast 
	* @Description: ��ʼ���㲥�¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��17�� ����10:02:12
	*/
	private void initBroadcast(){
		IntentFilter fliter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(GEOFENCE_BROADCAST_ACTION);
		registerReceiver(mReceiver, fliter);
		Intent mIntent = new Intent(GEOFENCE_BROADCAST_ACTION);
		mpIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0,mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/** 
	* @Title: verifyGeo 
	* @Description: ��֤�򿨵ص�Χ������ʾ
	* @param @param latLng
	* @param @param radius �뾶    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��5�� ����4:24:13
	*/
	private void verifyGeo(LatLng latLng,float radius){
		mLocationManagerProxy.removeGeoFenceAlert(mpIntent);  
		//���õ���Χ����λ�á��뾶����ʱʱ�䡢�����¼�
		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,latLng.longitude, radius,AmapConstants.EXPIRATION_TIME, mpIntent);
		//����һ��Բ,������Χ����ӵ���ͼ����ʾ
		if (mCircle != null) {
			mCircle.remove();
		}
		CircleOptions circle = new CircleOptions();
		circle.center(latLng);								//�������ĵ�
		circle.radius(radius);				//���ð뾶
		circle.fillColor(Color.argb(180, 224, 171, 10));	//����Բ�ε������ɫ
		circle.strokeColor(Color.RED);	 					//����Բ�εı߿���ɫ
		mCircle = mAMap.addCircle(circle); 					//��ʾ�ڵ�ͼ
	}

	/** 
	* @Fields mReceiver : �򿨴����¼�
	*/ 
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ���ܹ㲥
			if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
				Bundle bundle = intent.getExtras();
				// ���ݹ㲥��status��ȷ�����������ڻ�����������
				int status = bundle.getInt("status");
				if (status == 1) {
					if(checkInCount==0){  //˵�����״ν��ܵ��㲥��Ϣ
						uploadServer();
					}
					else{
						uploadDialog.dismiss();
					}
				}
				else{
					uploadDialog.dismiss();
					UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_not_check_in));
				}
				checkInCount++;
			}
		}
	};
	
	/** 
	* @Title: uploadServer 
	* @Description: �ϴ�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��19�� ����10:04:06
	*/
	private void uploadServer(){
		setModel();
		new uploadAsync().execute(aCheckInfo);
	}
	
	/** 
	* @Title: setModel 
	* @Description: ���ô��ϴ�ʵ��ֵ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:49:31
	*/
	private void setModel(){
		SharedPreferences sp=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aCheckInfo.setFAttendId(fAttendId);
		aCheckInfo.setFAttendStatus(fAttendStatus);
		aCheckInfo.setFItemNumber(fItemNumber);
		aCheckInfo.setFItemName(sp.getString(AppContext.FITEMNAME_KEY, ""));
		aCheckInfo.setFLatitude(String.valueOf(fLatitude));
		aCheckInfo.setFLongitude(String.valueOf(fLongitude));
		aCheckInfo.setFAddress(fAddress); 
	}
	
	/**
	 * @ClassName uploadDetailAsync
	 * @Description �ϴ����ݵ�������
	 * @author 21291
	 * @date 2014��10��30�� ����1:58:01
	 */
	private class uploadAsync extends AsyncTask<AdCheckInfo,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(AdCheckInfo... params) {
			return upload(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);	
			showResult(result);	
			uploadDialog.dismiss();
		}	
	}
	
	/** 
	* @Title: upload 
	* @Description: �ϴ�����
	* @param @param adCheckInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��12��19�� ����10:36:58
	*/
	private ResultMessage upload(AdCheckInfo adCheckInfo){
		aBusiness.setServiceUrl(uploadUrl);
		return aBusiness.checkHandle(adCheckInfo);
	}
	
	/** 
	* @Title: showResult 
	* @Description: �����ϴ����
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��19�� ����10:37:49
	*/
	private void showResult(ResultMessage result){
		if(result.isIsSuccess()){  //˵���ϴ��ɹ�
			UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_upload_success));
			Gson gson=GsonHelper.getInstance();
			AdCheckStatusInfo adCheckStatusInfo = gson.fromJson(result.getResult(), AdCheckStatusInfo.class);
			fAttendId=adCheckStatusInfo.getFAttendId();
			fAttendStatus=adCheckStatusInfo.getFStatus();
			fCheckInTime=adCheckStatusInfo.getFCheckInTime();
			fCheckOutTime=adCheckStatusInfo.getFCheckOutTime();
			setAttendStatus();
			sendLogs();
		}
		else{
			UIHelper.ToastMessage(AdCheckInActivity.this,getResources().getString(R.string.attendance_upload_failure));
		}
	}
	
	//���λ
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		amapDialog.show();
		startLocation();
	}

	@Override
	public void deactivate() {
		mListener = null;
		destroyLocation();
	}
	
	/** 
	* @Title: initLocation 
	* @Description: ��ʼ����λʵ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����12:22:49
	*/
	private void initLocation(){
		if(mLocationManagerProxy==null){
			mLocationManagerProxy = LocationManagerProxy.getInstance(AdCheckInActivity.this);     //������λʵ��
			mLocationManagerProxy.setGpsEnable(true);	
		}
	}
	
	/** 
	* @Title: startLocation 
	* @Description: ��ʼ����λ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����10:46:07
	*/
	private void startLocation(){
		initLocation();
		//�˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����ÿ��5����������λ�������볬��15��ʱ������������λ
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND, AmapConstants.FIXMETER, this);
	}
	
	/** 
	* @Title: destroyLocation 
	* @Description: ��������λ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��16�� ����4:44:24
	*/
	private void destroyLocation(){
		if(mLocationManagerProxy!=null){ // ���ٶ�λ
			mLocationManagerProxy.removeGeoFenceAlert(mpIntent);
			mLocationManagerProxy.removeUpdates(this);
			mLocationManagerProxy.destroy();
			mLocationManagerProxy=null;
		}
		if(mReceiver!=null){
			unregisterReceiver(mReceiver);  
		}
		
		if(amapDialog.isShowing()){
			amapDialog.dismiss();
		}
	} 
	
	//��λ�ӿ�ʵ��

	/**
	 * �˷����Ѿ�����
	 */
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
		if (mListener != null && amapLocation!=null && amapLocation.getAMapException().getErrorCode() == 0) {  //˵����λ�ɹ� 
			amapDialog.dismiss();
			checkInBtn.setEnabled(true);
			mListener.onLocationChanged(amapLocation);//��ʾϵͳС����
			fLatitude=amapLocation.getLatitude();
			fLongitude=amapLocation.getLongitude();
			fAddress=amapLocation.getAddress();
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ����ҵĿ���ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����3:34:49
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_attendance));
		logInfo.setFActionName("check");
		logInfo.setFNote("note");
		UIHelper.sendLogs(AdCheckInActivity.this,logInfo);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		initLocation();
		mLocationManagerProxy.removeUpdates(this);
		int randomTime=mRandom.nextInt(1000);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND+randomTime, AmapConstants.FIXMETER, this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		commonMenu.setContext(AdCheckInActivity.this);
		commonMenu.setMarginTouchMode();
		isMockLocation=Settings.Secure.getInt(this.getContentResolver(),Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
	}
	
	//��ͼ��������
	@Override
	protected void onResume() {
		super.onResume();
		initBroadcast();
	}

	@Override
	protected void onPause() {
		super.onPause();
		destroyLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
