package com.dahuatech.app.ui.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.dahuatech.app.R;

/**
 * @ClassName NetworkStateReceiver
 * @Description ����״̬�ı�֪ͨ
 * @author 21291
 * @date 2014��6��10�� ����1:33:58
 */
public class NetworkStateReceiver extends BroadcastReceiver {
	
	@SuppressLint("ShowToast")
	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		if("com.dahuatech.app.action.APPWIFI_CHANGE".equals(ACTION_NAME)){
			if(!isNetworkConnected(context)){ //�ж��Ƿ���������
				Toast.makeText(context, R.string.network_not_connected, 0).show();  
			}
		}
	}
	
	/** 
	* @Title: isNetworkConnected 
	* @Description: ��������Ƿ����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����10:56:31
	*/
	private boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){
				// ��ǰ���������ӵ�
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// ��ǰ�����ӵ��������
					return true;
				}
			}
		}
		return false;
	}

}
