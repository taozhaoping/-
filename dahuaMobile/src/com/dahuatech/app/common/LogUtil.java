package com.dahuatech.app.common;

import android.util.Log;

/**
 * @ClassName LogUtil
 * @Description ��־������
 * @author 21291
 * @date 2015��1��8�� ����4:22:34
 */
public class LogUtil {

	public static final int VERBOSE=1;
	public static final int DEBUG=2;
	public static final int INFO=3;
	public static final int WARN=4;
	public static final int ERROR=5;
	public static final int NOTHING=6;
	
	public static final int LEVEL=NOTHING;			//�����°汾ʱ�� �ȼ�Ϊ6
	
	public static void v(String tag,String msg){
		if(LEVEL <=VERBOSE){
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag,String msg){
		if(LEVEL <=DEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag,String msg){
		if(LEVEL <=INFO){
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(LEVEL <=WARN){
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(LEVEL <=ERROR){
			Log.e(tag, msg);
		}
	}
}
