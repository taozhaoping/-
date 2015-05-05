package com.dahuatech.app.ui.main;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdAmapListInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NoticeBussiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @ClassName MainActivity
 * @Description Ӧ�ó�����ҳ
 * @author 21291
 * @date 2014��4��17�� ����3:10:14
 * 
 */
public class MainActivity extends BaseActivity {

	private TextView txtFItemName,txtWelcome,txtWaitTaskCount;
	private String fItemNumber,fItemName;     	//Ա����,Ա������
	private NoticeBussiness noticeBussiness;  	//��������ҵ���߼���
	private AttendanceBusiness aBusiness;	  	//�����ĵص�ҵ���߼���
	private Button btnMyTask,btnExpense,btnMeeting,btnContacts,btnDevelopHour;
	private Button btnAttendance,btnMarket,btnLync;
	private boolean fNotice=false;
	private String aMapCacheKey;				//����汾��
	private AppContext appContext;  		  	//ȫ��Context
	
	private SharedPreferences loginSp,aMapSp;	//�����ļ�
	private SlidingMenu menu;					//�����
	private CommonMenu commonMenu;				//�����˵�
	private Calendar cal;						//������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		if(savedInstanceState!=null){  //��������³�ʼ������ָ�����֮ǰ��ֵ
			fItemNumber=savedInstanceState.getString(AppContext.USER_NAME_KEY);
			fItemName=savedInstanceState.getString(AppContext.FITEMNAME_KEY);	
		}
		
		//���ݵ���Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.USER_NAME_KEY);
			fItemName=extras.getString(AppContext.FITEMNAME_KEY);	
		}
		
		loginSp=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aMapSp=getSharedPreferences(AppContext.ADCHECKINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aMapCacheKey=aMapSp.getString(AppContext.AD_AMAP_ADDRESS_CACHE_KEY, "0");  //��ȡ����汾�ţ�Ĭ��Ϊ0
		//���ò�߲˵���
		menu=new SlidingMenu(this);	
		int left=(int)(getPixelsWidth() * 2 / 3);					//�����ʾ���
		commonMenu=CommonMenu.getCommonMenu(MainActivity.this,loginSp,menu,"main",left);
		commonMenu.initSlidingMenu();
		commonMenu.initLeftButton();
		txtWaitTaskCount=(TextView)findViewById(R.id.main_msg_MyTask);
		setBtnEvent();
		initBusiness();
		getAmapAddress();	//��ȡ�����ĵ�ַ
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {  //�������֮ǰ �ȱ���Ա���ź�Ա������
		super.onSaveInstanceState(outState);
		outState.putString(AppContext.USER_NAME_KEY, fItemNumber);
		outState.putString(AppContext.FITEMNAME_KEY, fItemName);
	}

	/** 
	* @Title: initBusiness 
	* @Description: ��ʼ����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����4:08:19
	*/
	private void initBusiness(){
		 FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MainActivity.this);
		 noticeBussiness=(NoticeBussiness)factoryBusiness.getInstance("NoticeBussiness","");   
		 aBusiness=(AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness","");   
	}
	
	/** 
	* @Title: setBtnEvent 
	* @Description: ��ʼ����ť���¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����4:00:24
	*/
	private void setBtnEvent(){
		txtFItemName=(TextView) findViewById(R.id.main_txtFItemName);
		txtFItemName.setText(fItemName);
		
		txtWelcome=(TextView) findViewById(R.id.main_txtWelcome);
		//txtWelcome.setText(showWelcome());
		
		//�ҵ�����
		btnMyTask=(Button) findViewById(R.id.main_btnMyTask);
		btnMyTask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMyTask(MainActivity.this,fItemNumber);
			}
		});
		
		//������ˮ	
		btnExpense=(Button) findViewById(R.id.main_btnExpense);
		btnExpense.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseMain(MainActivity.this,fItemNumber);
			}
		});
		
		//ͨѶ¼
		btnContacts=(Button) findViewById(R.id.main_btnContacts);
		btnContacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showContacts(MainActivity.this,fItemNumber,"main_search");
			}
		});
		
		//�ҵĻ���
		btnMeeting=(Button) findViewById(R.id.main_btnMeeting);
		btnMeeting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMeetingList(MainActivity.this,fItemNumber);
			}
		});
		
		//�з���ʱ
		btnDevelopHour = (Button) findViewById(R.id.main_btnDevelopHour);
		btnDevelopHour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showDH(MainActivity.this,fItemNumber);
			}
		});
		
		//�ҵĿ���
		btnAttendance=(Button) findViewById(R.id.main_btnAttendance);
		btnAttendance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showAttendanceCheck(MainActivity.this,fItemNumber);
			}
		});
		
		//�ҵ�����
		btnMarket=(Button) findViewById(R.id.main_btnMarket);
		btnMarket.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketMain(MainActivity.this,fItemNumber);
			}
		});
		
		//����LYNC
		btnLync=(Button) findViewById(R.id.main_btnlync);
		btnLync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.microsoft.office.lync");
				if(launchIntent!=null){
					startActivity(launchIntent);
					sendLyncLogs("start");
				}
				else{
					alertLync();
				}
			}
		});
	}	
	
	/** 
	* @Title: showWelcome 
	* @Description: ���ݵ�ǰʱ�䣬��ʾ��ӭ��
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2015��1��16�� ����10:09:12
	*/
	private String showWelcome(){
		String welCome="";
		cal = new GregorianCalendar();
		String currentWeekDay=DateHelper.getWeekOfDate(cal,cal.getTime());
		if("FSaturday".equals(currentWeekDay) || "FSunday".equals(currentWeekDay)){  //˵����ĩ�ϰ�
			welCome=getResources().getString(R.string.main_weekend_hint);
		}
		else{  //˵���ǹ�����
			String currentYear=String.valueOf(cal.get(Calendar.YEAR)); 			//��ǰ���
			String summerTime=currentYear+"-05-01";					   			//����ʱ
			String winterTime=currentYear+"-10-01";					   			//����ʱ
			String currentDate=StringUtils.toShortDateString(cal.getTime()); 	//��ǰ����
			String nowTime=StringUtils.toDateString(cal.getTime());  			//��ǰʱ��
			String amSixTime=currentDate+" 06:00:00";				 			//����6��
			String amEightTime=currentDate+" 08:30:00";				 			//����8��30��
			String amTwelveTime=currentDate+" 12:00:00";		     			//����12��
			String pmThirteenTime="";		     					 			//����1��
			if(DateHelper.dateCompare(cal, currentDate, summerTime) < 0){
				 pmThirteenTime=currentDate+" 13:00:00";
			}
			else if(DateHelper.dateCompare(cal, currentDate, winterTime) <0){
				pmThirteenTime=currentDate+" 13:30:00";
			}
			else{
				pmThirteenTime=currentDate+" 13:00:00";
			}
			
			String pmSeventeenTime=currentDate+" 17:30:00";						//����17��30��				
			String pmEighteenTime=currentDate+" 18:00:00";						//����18��					
		
			if(DateHelper.dateCompare(cal, nowTime, amSixTime) < 0){
				welCome=getResources().getString(R.string.main_overtime_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, amEightTime) < 0){
				welCome=getResources().getString(R.string.main_morning_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, amTwelveTime) < 0){
				welCome="";
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmThirteenTime) < 0){
				welCome=getResources().getString(R.string.main_noon_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmSeventeenTime) < 0){
				welCome="";
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmEighteenTime) < 0){
				welCome=getResources().getString(R.string.main_off_hint);
			}
			else{
				welCome=getResources().getString(R.string.main_overtime_hint);
			}
		}
		return welCome;
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: ��ȡ��Ļ���ؿ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��12��2�� ����11:21:35
	*/
	private int getPixelsWidth(){
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: alertLync 
	* @Description: ����LYNC�ͻ������ص�ַ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����2:12:47
	*/
	@SuppressLint("InlinedApi")
	private void alertLync(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setTitle(R.string.lync_title);
		builder.setMessage(R.string.lync_message);
		builder.setPositiveButton(R.string.download,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						downLoadLync();
						sendLyncLogs("download");
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/** 
	* @Title: downLoadLync 
	* @Description: ����Lync�ͻ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����2:51:14
	*/
	private void downLoadLync(){
		Uri uri = Uri.parse(AppUrl.URL_API_HOST_ANDROID_LYNC_DOWNLOAD);  
		Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);  
		startActivity(downloadIntent); 
	}
	
	/** 
	* @Title: sendLyncLogs 
	* @Description: ����LYNC��־
	* @param @param typeName     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��3�� ����11:50:53
	*/
	private void sendLyncLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_lync));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(MainActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fNotice =loginSp.getBoolean(AppContext.FNOTICE_ALARM_KEY, false);		
		//˵����֪ͨ��������
		if(fNotice){
			loginSp.edit().remove(AppContext.FNOTICE_ALARM_KEY).commit();
			String fItemNumber=loginSp.getString(AppContext.USER_NAME_KEY, "");
			if(StringUtils.isEmpty(fItemNumber))
				return;
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, TaskListActivity.class);
			intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
			intent.putExtra(AppContext.FSTATUS_KEY, "0");
			startActivity(intent);
		}	
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getTaskCount();  	//ʵʱ��ȡ������������
	}

	/** 
	* @Title: getTaskCount 
	* @Description: ʵʱ��ȡ��½�˴�����������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��30�� ����12:18:33
	*/
	private void getTaskCount(){
		new taskCountAsync().execute();
	}
	
	/**
	 * @ClassName taskCountAsync
	 * @Description �첽��ȡ������������
	 * @author 21291
	 * @date 2014��7��30�� ����2:08:00
	 */
	private class taskCountAsync extends AsyncTask<Void , Void, String > {
		
	   @Override
	   protected String doInBackground(Void... params) {
		   return getPostByTaskCount();
	   }

	   @Override
	   protected void onPostExecute(String result) {
		    //��ʾ�ҵ�������ͼ��
			if(!result.equals("0")){
				txtWaitTaskCount.setVisibility(View.VISIBLE);
				txtWaitTaskCount.setText(result);
			}else{
				txtWaitTaskCount.setVisibility(View.GONE);
			}
	   }
	}
	
	/** 
	* @Title: getPostByTaskCount 
	* @Description: �첽��ȡ��������������
	* @param @param param
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����10:06:29
	*/
	private String getPostByTaskCount(){
	   noticeBussiness.setServiceUrl(AppUrl.URL_API_HOST_ANDROID_NOTICESERVICE);
	   return noticeBussiness.getTaskCount(fItemNumber);       
	}
	
	/** 
	* @Title: getAmapAddress 
	* @Description: ��ȡ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��31�� ����3:58:32
	*/
	private void getAmapAddress(){
		new AmapAddressAsync().execute();
	}

	/**
	 * @ClassName AmapAddressAsync
	 * @Description �첽��ȡ�����ĵ�ַ
	 * @author 21291
	 * @date 2014��12��31�� ����4:13:02
	 */
	private class AmapAddressAsync extends AsyncTask<Void , Void, AdAmapListInfo > {
		
	   @Override
	   protected AdAmapListInfo doInBackground(Void... params) {
		   return getPostByAmap();
	   }

	   @Override
	   protected void onPostExecute(AdAmapListInfo result) {
		   if(!StringUtils.isEmpty(result.getFCacheKey())){
			   if(!aMapCacheKey.equals(result.getFCacheKey())){  //˵�������޸Ĺ�����Ҫ�޸�
				   if(!StringUtils.isEmpty(result.getFAmapList())){  //�洢�����ĵ�ַ
						aMapSp.edit().putString(AppContext.AD_AMAP_ADDRESS_CACHE_KEY, result.getFCacheKey()).commit();
						aMapSp.edit().putString(AppContext.AD_AMAP_ADDRESS_KEY, result.getFAmapList()).commit();
					}
			   }
		   }
	   }
	}
	
	/** 
	* @Title: getPostByTaskCount 
	* @Description: �첽��ȡ��������������
	* @param @param param
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����10:06:29
	*/
	private AdAmapListInfo getPostByAmap(){
	   aBusiness.setServiceUrl(AppUrl.URL_API_HOST_ANDROID_GETNEWAMAPLISTACTIVITY);
	   return aBusiness.getAmapList(aMapCacheKey);       
	}
	
	@Override
	protected void onDestroy() {		
		appContext.Logout();   //ȫ���˳�
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();	
	}

	//�������� �Ƿ��˳�
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag=true;
    	if(keyCode == KeyEvent.KEYCODE_BACK) { //���ؼ�
    		if (menu.isMenuShowing()) {
    			menu.showContent();
    		} else {
    			UIHelper.Exit(MainActivity.this);  //�˳�Ӧ��		
    		}
    	}
    	else {
    		flag=super.onKeyDown(keyCode, event);
		}  	
    	return flag;
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
