package com.dahuatech.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppStart
 * @Description Ӧ�ó���������
 * @author 21291
 * @date 2014��4��18�� ����11:30:25
 */
public class AppStart extends Activity {
	
/*	private int mFirstCount;
	private SharedPreferences sp;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		sp = getSharedPreferences(AppContext.GUIDEANDWELCOME_CONFIG_FILE,MODE_PRIVATE);
//	    //��ȡ�״δ򿪵��ж�ֵ,Ĭ��Ϊtrue
//		mFirstCount = sp.getInt(AppContext.IS_FIRST_COUNT_KEY,0);
//		if(mFirstCount < 2){  //С��2��  ��ʾ����ҳ
//		 	UIHelper.showGuide(AppStart.this);
//		}
//		else {//��ʾ��ӭҳ
//		 	UIHelper.showWelcome(AppStart.this);
//		}
		UIHelper.showWelcome(AppStart.this);
		finish();
	}
}
