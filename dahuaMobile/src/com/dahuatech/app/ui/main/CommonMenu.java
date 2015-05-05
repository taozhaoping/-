package com.dahuatech.app.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.attendance.AdCheckInActivity;
import com.dahuatech.app.ui.contacts.ContactsMainActivity;
import com.dahuatech.app.ui.develop.hour.DHMainActivity;
import com.dahuatech.app.ui.expense.main.ExpenseMainActivity;
import com.dahuatech.app.ui.expense.taxi.ExpenseTaxiMainActivity;
import com.dahuatech.app.ui.market.MarketMainActivity;
import com.dahuatech.app.ui.meeting.MeetingListActivity;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

/**
 * @ClassName CommonMenu
 * @Description ����˵���ͬ��
 * @author 21291
 * @date 2014��10��16�� ����5:24:24
 */
public class CommonMenu { 

	private String sideMenuType;			//������˵�����  "main"-��ҳ����,"other"-������ҳ����
	private SharedPreferences sp;  			//��ѡ���ļ�
	private SlidingMenu menu; 				//��ߵ����˵���
	private Context context;  				//�����Ļ���
	private int leftDimen;					//���Ĭ����ʾ���
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/** 
	* @Name: CommonMenu 
	* @Description: �ڲ��൥��ģʽ 
	*/
	private CommonMenu(){}
	private static class SingletonHolder {  
        private static CommonMenu instance = new CommonMenu();  
    }  
	public static CommonMenu getCommonMenu(Context context,SharedPreferences sp,SlidingMenu menu,String sideMenuType,int leftDimen) {
		SingletonHolder.instance.context=context;
		SingletonHolder.instance.sp=sp;
		SingletonHolder.instance.menu=menu;
		SingletonHolder.instance.sideMenuType=sideMenuType;
		SingletonHolder.instance.leftDimen=leftDimen;
		return SingletonHolder.instance;
	}
	
	/** 
	* @Title: initSlidingMenu 
	* @Description: ��ʼ����ߵ����˵���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��16�� ����4:45:07
	*/
	public void initSlidingMenu(){	
		menu.setMenu(R.layout.drawer_left_menu);				  		//������߲˵���ͼ
		menu.setShadowDrawable(R.drawable.shadow); 	   					//���������ͼ��Ӱ
		
		if("other".equals(sideMenuType)){  //˵�����������������
			menu.setSecondaryMenu(R.layout.drawer_right_menu);			//�����ұ߲˵���ͼ
			menu.setSecondaryShadowDrawable(R.drawable.shadowright);	//�����ұ���ͼ��Ӱ
			menu.setMode(SlidingMenu.LEFT_RIGHT);						//���ұ߻���
		}
		else{
			menu.setMode(SlidingMenu.LEFT);								//��߻���
		}
		
		//����SlidingMenu ������ģʽ
        //TOUCHMODE_FULLSCREEN ȫ��ģʽ��������contentҳ���У����������Դ�SlidingMenu
        //TOUCHMODE_MARGIN ��Եģʽ����contentҳ���У�������SlidingMenu,����Ҫ����Ļ��Ե�����ſ��Դ�SlidingMenu
        //TOUCHMODE_NONE ����ͨ�����ƴ�SlidingMenu
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);  					//������ӰͼƬ�Ŀ��  
		menu.setFadeEnabled(true);										//���û���ʱ�˵����Ƿ񽥱�
		menu.setFadeDegree(0.35f);										//���û���ʱ�Ľ���̶� 
		menu.setBehindScrollScale(0.0f);								//�������ű���
		menu.setBehindWidth(leftDimen);									//Ĭ�ϲ�������
		
		menu.setBehindCanvasTransformer(new CanvasTransformer(){    	//�������춯��

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(percentOpen, 1, 0, 0);
			}
		});
		menu.attachToActivity((Activity)context, SlidingMenu.SLIDING_WINDOW);  	//ռȫ��
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
	public int getPixelsWidth(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: setMarginTouchMode 
	* @Description: ���ñ�Ե����ģʽ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��19�� ����2:25:44
	*/
	public void setMarginTouchMode(){
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
	/** 
	* @Title: initLeftButton 
	* @Description: ��ʼ����߲�����˵��ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��16�� ����4:50:18
	*/
	@SuppressLint("InflateParams")
	public void initLeftButton(){
		final Button btnSetting=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_setting);
		final Button btnBack=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_back);
		final Button btnInvite=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_invite);
		final Button btnExit=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_exit);
		
		//ϵͳ����
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if(SettingActivity.getInstance()==null){  //���Ǵ�����ҳ���
					UIHelper.showSetting(context);
					if("other".equals(sideMenuType)){  //˵�����������������
						((Activity)context).finish();
					}
				}
			}
		});
				
		//�ص���ҳ
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("other".equals(sideMenuType)){  //˵�����������������
					Intent intent = new Intent();
					intent.setClass(context, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					((Activity)context).startActivity(intent);
					((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
					((Activity)context).finish();
				}
			}
		});
				
		//�������
		btnInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(SmsNoticeActivity.getInstance()==null){  //���ǴӶ�������ҳ���
					UIHelper.showSmsInvite(context);
					if("other".equals(sideMenuType)){  //˵�����������������
						((Activity)context).finish();
					}
				}
			}
		});
				
		//�˳�
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appExit();
			}
		});			
	}
	
	/** 
	* @Title: initRightButton 
	* @Description: ��ʼ���ұ߲�����˵��ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��1�� ����10:55:46
	*/
	public void initRightButton(){
		final String fItemNumber= sp.getString(AppContext.USER_NAME_KEY, "");
		final Button btnMyTask=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_myTask);
		final Button btnExpense=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_expense);
		final Button btnContacts=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_contacts);
		final Button btnMeeting=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_meeting);
		final Button btnDh=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_develop_hour);
		final Button btnAttendance=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_attendance);
		final Button btnMarket=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_market);
		final Button btnRich=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_richscan);
	
		//�ҵ�����
		btnMyTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=TaskListActivity.getInstance())
				{
					if(TaskListActivity.getInstance()!=null){
						TaskListActivity.getInstance().finish();
					}
					UIHelper.showMyTask(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//�ҵı���
		btnExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=ExpenseMainActivity.getInstance())
				{
					if(ExpenseMainActivity.getInstance()!=null){
						ExpenseMainActivity.getInstance().finish();
					}
					UIHelper.showExpenseMain(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//ͨѶ¼
		btnContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=ContactsMainActivity.getInstance())
				{
					if(ContactsMainActivity.getInstance()!=null){
						ContactsMainActivity.getInstance().finish();
					}	
					UIHelper.showContacts(context,fItemNumber,"main_search");
					((Activity)context).finish();
				}
			}
		});
		
		//�ҵĻ���
		btnMeeting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=MeetingListActivity.getInstance())
				{
					if(MeetingListActivity.getInstance()!=null){
						MeetingListActivity.getInstance().finish();
					}	
					UIHelper.showMeetingList(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//�з���ʱ
		btnDh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=DHMainActivity.getInstance())
				{
					if(DHMainActivity.getInstance()!=null){
						DHMainActivity.getInstance().finish();
					}	
					UIHelper.showDH(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//�ҵĿ���
		btnAttendance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=AdCheckInActivity.getInstance())
				{
					if(AdCheckInActivity.getInstance()!=null){
						AdCheckInActivity.getInstance().finish();
					}	
					UIHelper.showAttendanceCheck(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//�ҵ�����
		btnMarket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=MarketMainActivity.getInstance())
				{
					if(MarketMainActivity.getInstance()!=null){
						MarketMainActivity.getInstance().finish();
					}	
					UIHelper.showMarketMain(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//ɨһɨ
		btnRich.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				UIHelper.showCapture(context);
				((Activity)context).finish();
			}
		});
	}
	
	/** 
	* @Title: toggle 
	* @Description: ��߲˵����������л�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��14�� ����5:33:24
	*/
	public void toggle() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			NavUtils.navigateUpFromSameTask(((Activity)context));
			((Activity)context).finish();
		}
	}
	
	/** 
	* @Title: appExit 
	* @Description: �˳�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��16�� ����3:49:14
	*/
	public void appExit(){
		SharedPreferences settingSp = ((Activity)context).getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, Context.MODE_PRIVATE);
		//�ж��Ѿ����ô򳵰�ť
		if(settingSp.getBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, false)){
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle(R.string.expense_taxi_exit);
			builder.setMessage(R.string.expense_taxi_gps_exit);
			builder.setPositiveButton(R.string.sure,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();					
							exit();
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
		else {
			exit();
		}
	}
	
	/** 
	* @Title: exit 
	* @Description: �˳�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��20�� ����8:54:44
	*/
	private void exit(){
		AppContext appContext=(AppContext)((Activity)context).getApplication();
		appContext.Logout();
		if(ExpenseTaxiMainActivity.getInstance()!=null){
			ExpenseTaxiMainActivity.getInstance().finish();
		}
		((Activity)context).finish();
		AppManager.getAppManager().AppExit(context);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
