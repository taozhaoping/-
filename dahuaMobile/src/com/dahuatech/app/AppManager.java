package com.dahuatech.app;

import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @ClassName AppManager
 * @Description Ӧ�ó���Activity�����࣬����Activity�����Ӧ�ó����˳�
 * @author 21291
 * @date 2014��4��16�� ����10:37:33
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	public AppManager() {}

	/** 
	* @Title: getAppManager 
	* @Description: ��һʵ��
	* @param @return     
	* @return AppManager    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:38:47
	*/
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	
	
	/** 
	* @Title: addActivity 
	* @Description: ���Activity����ջ��
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:39:55
	*/
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	
	/** 
	* @Title: currentActivity 
	* @Description: ��ȡ��ǰActivity(��ջ�����һ��ѹ���)
	* @param @return     
	* @return Activity    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:40:48
	*/
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: ������ǰActivity����ջ�����һ��ѹ��ģ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:44:06
	*/
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: ����ָ����Activity
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:44:18
	*/
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: ����ָ��������Activity
	* @param @param cls     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:45:36
	*/
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	
	/** 
	* @Title: finishAllActivity 
	* @Description: ��������Activity
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:46:08
	*/
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/** 
	* @Title: AppExit 
	* @Description: �˳�Ӧ�ó���
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����10:47:57
	*/
	@SuppressLint("NewApi")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
