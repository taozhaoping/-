package com.dahuatech.app.ui.main;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.ui.service.AlarmBroadCast;
import com.dahuatech.app.ui.service.NotificationShow;

/**
 * @ClassName SettingActivity
 * @Description ϵͳ����Activity��
 * @author 21291
 * @date 2014��4��21�� ����8:07:09
 */
public class SettingActivity extends MenuActivity {
	
	private ImageView checkUpdate,versionImage,gesturesImage;
	private ToggleButton tgNotice,tgGestures;

	private String serviceUrl;  //�����ַ
	
	private AppContext appContext; //ȫ��Context
	private SharedPreferences sp;  
	
	private static SettingActivity mInstance;
	public static SettingActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.setting);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY;	
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		
		//������
		checkUpdate = (ImageView) findViewById(R.id.setting_checkUpdate_image);
		checkUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!appContext.isNetworkConnected()) {
					UIHelper.ToastMessage(SettingActivity.this, R.string.network_not_connected);
					return;
				}
				UpdateManager update=UpdateManager.getUpdateManager(SettingActivity.this,serviceUrl);
				update.checkAppUpdate(true);
			}
		});
		
		//�汾��Ϣ
		versionImage=(ImageView) findViewById(R.id.setting_version_image);
		versionImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder ua = new StringBuilder("");
				ua.append("APP�汾��ţ�"+appContext.getPackageInfo().versionCode+"\r\n");//App�汾���
				ua.append("APP�汾���ƣ�"+appContext.getPackageInfo().versionName+"\r\n");//App�汾����
				ua.append("�ֻ�ϵͳ�汾��"+"Android "+android.os.Build.VERSION.RELEASE);//�ֻ�ϵͳ�汾
				UIHelper.ToastMessage(SettingActivity.this, ua.toString());			
			}
		});
		
		boolean isNotice=sp.getBoolean(AppContext.IS_NOTICE_KEY, true);
		boolean isGestures=sp.getBoolean(AppContext.IS_GESTURES_KEY, true);   //Ĭ��Ϊ����
		
		//�Ƿ�����֪ͨ��
		tgNotice = (ToggleButton) findViewById(R.id.setting_notice_toggleButton);
		tgNotice.setChecked(isNotice);
		
		//�Ƿ�������������
		tgGestures=(ToggleButton) findViewById(R.id.setting_gestures_toggleButton);
		tgGestures.setChecked(isGestures);
		
		//������������
		gesturesImage=(ImageView) findViewById(R.id.setting_gestures_image);
		gesturesImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences loginSp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
				UIHelper.showLockSetPwd(SettingActivity.this,loginSp.getString(AppContext.USER_NAME_KEY, ""),loginSp.getString(AppContext.FITEMNAME_KEY, ""),"setting");	
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();		
	}
	
	@Override
	protected void onPause() {
		super.onPause();	
		Editor editor=sp.edit();
		editor.putBoolean(AppContext.IS_NOTICE_KEY, tgNotice.isChecked());
		editor.putBoolean(AppContext.IS_GESTURES_KEY, tgGestures.isChecked());
		editor.commit();
		if(!tgNotice.isChecked())//˵���ر�֪ͨ��
			this.cancleNotice();	
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
		return super.onKeyDown(keyCode, event);
	}
	
	/** 
	* @Title: cancleNotice 
	* @Description: ���֪ͨ����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��5�� ����4:42:49
	*/
	private void cancleNotice(){
		new NotificationShow().removeNotification(SettingActivity.this);
		new AlarmBroadCast().cancelAlarm(SettingActivity.this);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
