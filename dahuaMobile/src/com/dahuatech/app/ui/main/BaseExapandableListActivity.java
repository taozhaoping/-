package com.dahuatech.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockExpandableListActivity;
import com.dahuatech.app.AppManager;

/**
 * @ClassName BaseExapandableListActivity
 * @Description ֻ��Ի���ExapandableList�б�Ļ���
 * @author 21291
 * @date 2014��8��27�� ����4:00:15
 */
public class BaseExapandableListActivity extends SherlockExpandableListActivity  {
	
	private boolean allowFullScreen=true; //�Ƿ�����ȫ��
	private boolean allowDestroy=true; //�Ƿ���������	
	@SuppressWarnings("unused")
	private View view;
	
	public boolean isAllowFullScreen() { //��ȡ�Ƿ�ȫ��
		return allowFullScreen;
	}

	public void setAllowFullScreen(boolean allowFullScreen) { //�����Ƿ�ȫ��
		this.allowFullScreen = allowFullScreen;
	}
	
	public boolean isAllowDestroy() { //��ȡ�Ƿ�����
		return allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy) { //�����Ƿ�����
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) { //�����Ƿ�����(����) 
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		AppManager.getAppManager().addActivity(this); //���Activity����ջ
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		//���͹㲥,�ж��Ƿ�����״̬�����ı�
		Intent intent = new Intent("com.dahuatech.app.action.APPWIFI_CHANGE");  
		sendBroadcast(intent);
		super.onRestart();		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();	
		AppManager.getAppManager().finishActivity(this); // ����Activity&�Ӷ�ջ���Ƴ�
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		if (keyCode == KeyEvent.KEYCODE_BACK) { //��ط��ؼ������Ұ�����ͼ���
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
