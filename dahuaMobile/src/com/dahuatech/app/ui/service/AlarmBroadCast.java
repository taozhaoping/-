package com.dahuatech.app.ui.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;

import com.dahuatech.app.AppContext;

/**
 * @ClassName BroadCast
 * @Description ֪ͨ��Ϣ�㲥������ (��Ҫ��Ϊ����ʾ���ж���������������Ϣδ����)
 * @author 21291
 * @date 2014��4��23�� ����8:04:35
 */
public class AlarmBroadCast extends BroadcastReceiver {
	private final static String TAG="AlarmBroadCast";
	private int waitTaskCount; //������������
	private PowerManager.WakeLock moWakeLock;
	private WifiManager.WifiLock moWifiLock;

	@Override
	public void onReceive(Context context, Intent intent) {
		acquireLocks(context);
		String ACTION_NAME = intent.getAction();
		if("com.dahuatech.app.action.APPWIDGET_UPDATE".equals(ACTION_NAME)){
			waitTaskCount = intent.getIntExtra(AppContext.BROADCAST_WAITTASKCOUNT_KEY, 0);
			new NotificationShow().showNotification(context, waitTaskCount); //֪ͨ����ʾ
		}
		releaseLocks();
	} 
	
	/** 
	* @Title: setAlarm 
	* @Description: �������Ӷ�ʱ֪ͨ ����ظ�����  Ĭ�ϼ��ʱ��Ϊ1��Сʱ
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����4:46:09
	*/
	@SuppressWarnings("static-access")
	public void setAlarm(Context context){
		try {
			Intent mIntent = new Intent(context, NoticeService.class);   
			PendingIntent sender=PendingIntent.getService(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
			Calendar calendar = Calendar.getInstance();  
			calendar.setTimeInMillis(System.currentTimeMillis());
			int mHour = calendar.get(Calendar.HOUR_OF_DAY);  //��ǰСʱ
			int mMinute = calendar.get(Calendar.MINUTE); //��ǰ����
			int mSecond=calendar.get(Calendar.SECOND);	 //��ǰ������
			int mDelayed=60 * 60;                       
			
			// ����ʱ����Ҫ����һ�£���Ȼ����8��Сʱ��ʱ���  
			calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			calendar.set(Calendar.MINUTE, mMinute);
		 	calendar.set(Calendar.HOUR_OF_DAY, mHour);
		 	calendar.set(Calendar.SECOND, mSecond);
		 	calendar.set(Calendar.MILLISECOND, 0);
		 
			calendar.add(calendar.SECOND, mDelayed);  //��һ��Сʱ��ʼִ��
			long atTimeInMillis = calendar.getTimeInMillis();   //����ʱ��
			long intervalDay = 1000L * 60 * 60;  //���һ��Сʱ
			
			AlarmManager alarmMgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);		
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, atTimeInMillis, intervalDay, sender);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
			Log.i(TAG,df.format(calendar.getTime())); 
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "error:"+e.toString()); 
		}
	}
	
	/** 
	* @Title: cancelAlarm 
	* @Description: ȡ������
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����2:15:51
	*/
	public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NoticeService.class);
        PendingIntent sender = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
    /** 
    * @Title: acquireLocks 
    * @Description: ������Ļ����
    * @param @param context     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��6��3�� ����4:38:58
    */
    private void acquireLocks(Context context)
    {
        try {
            // Acquire a wake lock to prevent the device from entering "deep sleep"
            PowerManager oPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            this.moWakeLock = oPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            this.moWakeLock.acquire();
            // Acquire a WiFi lock to ensure WiFi is enabled
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            this.moWifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, TAG);
            this.moWifiLock.acquire();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /** 
    * @Title: releaseLocks 
    * @Description: �ͷ���Ļ������Դ
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��6��3�� ����4:39:38
    */
    private void releaseLocks()
    {
        try {
            this.moWakeLock.release();
            this.moWifiLock.release();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
