package com.dahuatech.app.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppExpiration;
import com.dahuatech.app.R;

/**
 * @ClassName NotificationShow
 * @Description ֪ͨ����ʾ������
 * @author 21291
 * @date 2014��6��3�� ����10:22:34
 */
public class NotificationShow {

	private final static int NOTIFICATION_ID = 1;  //����ID
	
	/** 
	* @Name: NotificationShowService 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public NotificationShow(){}

	/** 
	* @Title: showNotification 
	* @Description: ��ʾ֪ͨ����Ϣ
	* @param @param context
	* @param @param noticeCount     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����10:16:31
	*/
	public void showNotification(Context context,int noticeCount){
		//����֪ͨ Notification
		Notification notification = null;
		//���� NotificationManager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		//�Ƿ񷢳�֪ͨ
		if(noticeCount==0){
			notificationManager.cancelAll();
			return;
		}	
		String contentTitle = context.getString(R.string.app_name);
		String contentText = "������ " + noticeCount + " ����������";
		//���õ��֪ͨ��ת
		Intent intent = new Intent(context, AppExpiration.class);
		intent.putExtra(AppContext.FNOTICE_ALARM_KEY, true);	
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		//����֪ͨ��ʾΪĬ��View  
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);	
				
		NotificationCompat.Builder builder= new NotificationCompat.Builder(context);			
		builder.setContentTitle(contentTitle);
		builder.setContentText(contentText);
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setAutoCancel(true);  //�������֪ͨ
		builder.setContentIntent(contentIntent);
		builder.setOngoing(true);  
		notification=builder.build();
		
		//���õ�����֪ͨ
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon=R.drawable.ic_launcher;
		
		//����ΪĬ������
		notification.defaults |=Notification.DEFAULT_SOUND;			
		//����ΪĬ������
		notification.defaults |=Notification.DEFAULT_LIGHTS;				
		//����Ϊ��   <��Ҫ�����û�Ȩ��android.permission.VIBRATE>
		notification.vibrate = new long[]{100, 250, 100, 500,100,750,100,1000};
	
		//����֪ͨ
		notificationManager.notify(NOTIFICATION_ID, notification);	
	}
	 
	/** 
	* @Title: removeNotification 
	* @Description: ɾ��֪ͨ����Ϣ
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����10:16:44
	*/
	public void removeNotification(Context context) {  
	    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
	    manager.cancel(NOTIFICATION_ID);  
	}  
}
