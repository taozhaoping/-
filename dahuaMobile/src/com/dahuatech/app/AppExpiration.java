package com.dahuatech.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppExpiration
 * @Description Ӧ�ó������ʱ����
 * @author 21291
 * @date 2014��12��9�� ����1:30:18
 */
public class AppExpiration extends Activity {
	
	private boolean fNotice=false;    			//�Ƿ���֪ͨ
	private boolean fLoginShowNotice=false;		//�����֪ͨ�������ģ�����������Ҫ֪ͨ��½ҳ��
	
	private String fItemNumber,fItemName; 		//Ա����,Ա������
	private SharedPreferences sp;	  	//�����ļ�	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fNotice=extras.getBoolean(AppContext.FNOTICE_ALARM_KEY, false);	 
		}	
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, "");
		fItemName=sp.getString(AppContext.FITEMNAME_KEY, "");
		boolean fFirstLogin=sp.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);  //�ж�Ӧ�ó����Ƿ��״ε�½
		
		if(fFirstLogin){  //˵����Ӧ�ó����״ε�½
			UIHelper.showLogin(AppExpiration.this,fLoginShowNotice,fFirstLogin);
		}
		else{
			if(fNotice){ //����Ǵ�֪ͨ������	
				fLoginShowNotice=true;
			}
			SharedPreferences setting=getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			boolean isGestures=setting.getBoolean(AppContext.IS_GESTURES_KEY, true);
			if(isGestures){  //˵��������������
				UIHelper.showLock(AppExpiration.this,fItemNumber,fItemName,fFirstLogin);
			}
			else{  //˵��û��������������
				UIHelper.showLogin(AppExpiration.this,fLoginShowNotice,fFirstLogin);
			}
		}
	}
}
