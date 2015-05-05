package com.dahuatech.app.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppExpiration;
import com.dahuatech.app.AppGuide;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.AppWelcome;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.SettingBusiness;
import com.dahuatech.app.ui.attendance.AdCheckInActivity;
import com.dahuatech.app.ui.attendance.AdListActivity;
import com.dahuatech.app.ui.barcode.CaptureActivity;
import com.dahuatech.app.ui.contacts.ContactsMainActivity;
import com.dahuatech.app.ui.develop.hour.DHConfirmListActivity;
import com.dahuatech.app.ui.develop.hour.DHConfirmListPersonActivity;
import com.dahuatech.app.ui.develop.hour.DHDetailActivity;
import com.dahuatech.app.ui.develop.hour.DHListActivity;
import com.dahuatech.app.ui.develop.hour.DHListProjectActivity;
import com.dahuatech.app.ui.develop.hour.DHMainActivity;
import com.dahuatech.app.ui.develop.hour.DHProjectSearchActivity;
import com.dahuatech.app.ui.develop.hour.DHTypeListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseClientSearchListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowDetailActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowLocalListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseProjectSearchListActivity;
import com.dahuatech.app.ui.expense.main.ExpenseMainActivity;
import com.dahuatech.app.ui.expense.taxi.ExpenseTaxiMainActivity;
import com.dahuatech.app.ui.main.LoginActivity;
import com.dahuatech.app.ui.main.LoginLockActivity;
import com.dahuatech.app.ui.main.LoginLockSetPwdActivity;
import com.dahuatech.app.ui.main.MainActivity;
import com.dahuatech.app.ui.main.SettingActivity;
import com.dahuatech.app.ui.main.SmsNoticeActivity;
import com.dahuatech.app.ui.market.MarketBidSearchActivity;
import com.dahuatech.app.ui.market.MarketContractSearchActivity;
import com.dahuatech.app.ui.market.MarketMainActivity;
import com.dahuatech.app.ui.market.MarketProductSearchActivity;
import com.dahuatech.app.ui.market.MarketWorkflowActivity;
import com.dahuatech.app.ui.meeting.MeetingDetailActivity;
import com.dahuatech.app.ui.meeting.MeetingListActivity;
import com.dahuatech.app.ui.meeting.MeetingPersonListActivity;
import com.dahuatech.app.ui.meeting.MeetingRoomListActivity;
import com.dahuatech.app.ui.task.ApplyDaysOffActivity;
import com.dahuatech.app.ui.task.ApplyDaysOffDevelopActivity;
import com.dahuatech.app.ui.task.ApplyLeaveActivity;
import com.dahuatech.app.ui.task.ApplyOverTimeActivity;
import com.dahuatech.app.ui.task.ApplyResumeActivity;
import com.dahuatech.app.ui.task.ContributionAwardActivity;
import com.dahuatech.app.ui.task.DaHuaAssumeCostActivity;
import com.dahuatech.app.ui.task.DevelopInquiryActivity;
import com.dahuatech.app.ui.task.DevelopTestNetworkActivity;
import com.dahuatech.app.ui.task.DevelopTravelActivity;
import com.dahuatech.app.ui.task.DocumentApproveActivity;
import com.dahuatech.app.ui.task.DoorPermissionActivity;
import com.dahuatech.app.ui.task.EmailOpenActivity;
import com.dahuatech.app.ui.task.EngineeringActivity;
import com.dahuatech.app.ui.task.ExAttendanceActivity;
import com.dahuatech.app.ui.task.ExpenseCostTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseMarketBidTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseMarketPayTHeaderActivity;
import com.dahuatech.app.ui.task.ExpensePrivateTHeaderActivity;
import com.dahuatech.app.ui.task.ExpensePublicTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseSpecialTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseSpecialThingHeaderActivity;
import com.dahuatech.app.ui.task.FeDestroyActivity;
import com.dahuatech.app.ui.task.FeEngravingActivity;
import com.dahuatech.app.ui.task.FeTakeOutActivity;
import com.dahuatech.app.ui.task.FeTransferActivity;
import com.dahuatech.app.ui.task.FeUpdateActivity;
import com.dahuatech.app.ui.task.FixedAssetsSpecialActivity;
import com.dahuatech.app.ui.task.LowConsumableActivity;
import com.dahuatech.app.ui.task.LowerNodeApproveActivity;
import com.dahuatech.app.ui.task.MemRequreActivity;
import com.dahuatech.app.ui.task.NetworkPermissionActivity;
import com.dahuatech.app.ui.task.NewProductLibActivity;
import com.dahuatech.app.ui.task.NewProductReworkActivity;
import com.dahuatech.app.ui.task.PlusCopyActivity;
import com.dahuatech.app.ui.task.PlusCopyPersonActivity;
import com.dahuatech.app.ui.task.ProjectReadActivity;
import com.dahuatech.app.ui.task.PurchaseStockActivity;
import com.dahuatech.app.ui.task.SvnPermissionActivity;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.dahuatech.app.ui.task.TdBorrowActivity;
import com.dahuatech.app.ui.task.TdPermissionActivity;
import com.dahuatech.app.ui.task.TrainComputerActivity;
import com.dahuatech.app.ui.task.TravelApprovalActivity;

public class UIHelper {
	
	public static final int ACTIVITY_ENGINEERING = 0x00;
	public static final int ACTIVITY_EXPENSEPRIVATE = 0x01;
	public static final int ACTIVITY_EXPENSEPUBLIC = 0x02;
	
	//�ҵ���ˮ ��ͬ�б���ת����ˮ����ҳ��
	public static final int ACTIVITY_EXPENSEDETAIL_LOCAL = 0x03;			 //���ϴ��б�
	public static final int ACTIVITY_EXPENSEDETAIL_SERVER = 0x04;			 //�������б�
	
	private static UIHelper mInstance;
	static{
		mInstance=new UIHelper();
	}
	
	public UIHelper(){}
	
	public static UIHelper getInstance(){
		return mInstance;
	}
	
	public static Context mContext;

	/** 
	* @Title: showGuide 
	* @Description: ��ʾ��������ҳ
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����11:05:34
	*/
	public static void showGuide(Context context) {
		Intent intent = new Intent(context, AppGuide.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showWelcome 
	* @Description: ��ʾ��ʼ������ӭ����
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����11:03:19
	*/
	public static void showWelcome(Context context) {
		Intent intent = new Intent(context, AppWelcome.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpiration 
	* @Description: ��ʾ����ʱ���ж�ҳ��
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����2:23:05
	*/
	public static void showExpiration(Context context) {
		Intent intent = new Intent(context, AppExpiration.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLogin 
	* @Description: ��ʾ��¼����
	* @param @param context ������
	* @param @param fLoginShowNotice �Ƿ���֪ͨ����Ϣ  
	* @param @param fFirstLogin �Ƿ��״ε�½     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����3:03:35
	*/
	public static void showLogin(Context context,boolean fLoginShowNotice,boolean fFirstLogin) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	
		intent.putExtra(AppContext.FNOTICE_LOGIN_IS_SHOW_KEY, fLoginShowNotice);	
		intent.putExtra(AppContext.FLOGIN_IS_FIRST_KEY, fFirstLogin);	
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLock 
	* @Description: ��ʾ��½����ҳ��
	* @param @param context ������
	* @param @param fItemNumber Ա����
	* @param @param fItemName Ա������    
	* @param @param fFirstLogin �Ƿ��״ε�½         
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����4:04:29
	*/
	public static void showLock(Context context,String fItemNumber,String fItemName,boolean fFirstLogin) {
		Intent intent = new Intent(context, LoginLockActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);
		intent.putExtra(AppContext.FLOGIN_IS_FIRST_KEY, fFirstLogin);
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLockSetPwd 
	* @Description: �������������½ҳ��
	* @param @param context ������
	* @param @param fItemNumber Ա����
	* @param @param fItemName Ա������        
	* @param @param source ��Դ
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����10:40:58
	*/
	public static void showLockSetPwd(Context context,String fItemNumber,String fItemName,String source) {
		Intent intent = new Intent(context, LoginLockSetPwdActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);	
		intent.putExtra(AppContext.GESTURES_PASSWORD_SOURCE_KEY, source);	
		context.startActivity(intent);
	}

	/** 
	* @Title: showHome 
	* @Description: ��ʾ��ҳ
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����3:01:46
	*/
	public static void showHome(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showHome 
	* @Description: ��ʾ��ҳ
	* @param @param context
	* @param @param fItemNumber Ա����
	* @param @param fItemName Ա������    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����10:14:23
	*/
	public static void showHome(Context context,String fItemNumber,String fItemName) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);	
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		((Activity)context).finish();
	}
	
	/** 
	* @Title: showSetting 
	* @Description: ��ʾϵͳ���ý���
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����3:18:33
	*/
	public static void showSetting(Context context) {
		Intent intent = new Intent(context, SettingActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showSmsInvite 
	* @Description: ���Ͷ�������ҳ��
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��16�� ����3:54:55
	*/
	public static void showSmsInvite(Context context) {
		Intent intent = new Intent(context, SmsNoticeActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMyTask 
	* @Description: ��ʾ�ҵ��������
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����4:38:02
	*/
	public static void showMyTask(Context context,String fItemNumber) {
		Intent intent = new Intent(context, TaskListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.FSTATUS_KEY, "0");
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseMain 
	* @Description: ��ʾ������ˮ����
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��21�� ����4:24:40
	*/
	public static void showExpenseMain(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseTaxi 
	* @Description: ��ʾ�����򳵽���
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����4:40:25
	*/
	public static void showExpenseTaxi(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseTaxiMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowList 
	* @Description: ��ʾ�ҵ���ˮ�б�ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��27�� ����2:10:32
	*/
	public static void showExpenseFlowList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseFlowListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowLocalList 
	* @Description: ��ʾ�ҵ���ˮ���ϴ��б�ҳ��
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����3:08:11
	*/
	public static void showExpenseFlowLocalList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseFlowLocalListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowDetail 
	* @Description: ��ת����ˮ����ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����
	* @param @param listType �б�����
	* @param @param eDetailInfo ����ʵ��         
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����11:58:24
	*/
	public static void showExpenseFlowDetail(Context context,String fItemNumber,String listType,ExpenseFlowDetailInfo eDetailInfo){
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, eDetailInfo.getFPayType());
		//��������
		intent.putExtra(DbManager.KEY_ROWID, eDetailInfo.getFLocalId());
		intent.putExtra(DbManager.KEY_DETAIL_FSERVERID, eDetailInfo.getFServerId());
		//��ˮ����������Ϣ
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTIME, eDetailInfo.getFExpendTime());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTYPEPARENT, eDetailInfo.getFExpendTypeParent());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTYCHILD, eDetailInfo.getFExpendTypeChild());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDADDRESS, eDetailInfo.getFExpendAddress());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDAMOUNT, eDetailInfo.getFExpendAmount());
		intent.putExtra(DbManager.KEY_DETAIL_FCAUSE, eDetailInfo.getFCause());	
		intent.putExtra(DbManager.KEY_DETAIL_FCLIENTID, eDetailInfo.getFClientId());
		intent.putExtra(DbManager.KEY_DETAIL_FPROJECTID, eDetailInfo.getFProjectId());
		intent.putExtra(DbManager.KEY_DETAIL_FCLIENT, eDetailInfo.getFClient());
		intent.putExtra(DbManager.KEY_DETAIL_FPROJECT, eDetailInfo.getFProject());
		intent.putExtra(DbManager.KEY_DETAIL_FACCOMPANY, eDetailInfo.getFAccompany());
		intent.putExtra(DbManager.KEY_DETAIL_FACCOMPANYREASON, eDetailInfo.getFAccompanyReason());
		//��ˮ����������Ϣ
		intent.putExtra(DbManager.KEY_DETAIL_FSTART, eDetailInfo.getFStart());
		intent.putExtra(DbManager.KEY_DETAIL_FDESTINATION, eDetailInfo.getFDestination());
		intent.putExtra(DbManager.KEY_DETAIL_FSTARTTIME, eDetailInfo.getFStartTime());
		intent.putExtra(DbManager.KEY_DETAIL_FENDTIME, eDetailInfo.getFEndTime());
		intent.putExtra(DbManager.KEY_DETAIL_FBUSINESSLEVEL, eDetailInfo.getFBusinessLevel());
		intent.putExtra(DbManager.KEY_DETAIL_FREASON, eDetailInfo.getFReason());
		intent.putExtra(DbManager.KEY_DETAIL_FDESCRIPTION, eDetailInfo.getFDescription());
		
		if("local".equals(listType)){  //�����б���ת
			((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEDETAIL_LOCAL);
		}
		else{  //�������б���ת
			((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEDETAIL_SERVER);
		}
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowDetail 
	* @Description: ��ʾ�ҵ���ˮ����ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա���� 
	* @param @param defaultExpendTime Ĭ��ʱ��     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��15�� ����3:48:51
	*/
	public static void showExpenseFlowDetail(Context context,String fItemNumber,String defaultExpendTime) {
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, "�ֽ�");
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME, defaultExpendTime);
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: redirectToExpenseFlow 
	* @Description: �򳵱�����ת����ˮ��дҳ��
	* @param @param context �����Ļ���
	* @param @param source ��ת��Դ
	* @param @param fItemNumber Ա����  
	* @param @param defaultExpendTime  Ĭ��ʱ��     
	* @param @param fExpendAmount �򳵽��
	* @param @param fStartAddress ����ʼ��ַ
	* @param @param fEndAddress �򳵽�����ַ
	* @param @param fStartTime �򳵿�ʼʱ��
	* @param @param fEndTime �򳵽���ʱ��     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��13�� ����5:30:11
	*/
	public static void redirectToExpenseFlow(Context context,String source,String fItemNumber,String defaultExpendTime,String fExpendAmount,String fStartAddress,String fEndAddress,String fStartTime,String fEndTime) {
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME, defaultExpendTime);
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_SKIP_SOURCE, source);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, "�ֽ�");
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDAMOUNT, fExpendAmount);
		intent.putExtra(DbManager.KEY_DETAIL_FSTART, fStartAddress);
		intent.putExtra(DbManager.KEY_DETAIL_FDESTINATION, fEndAddress);
		intent.putExtra(DbManager.KEY_DETAIL_FSTARTTIME, fStartTime);
		intent.putExtra(DbManager.KEY_DETAIL_FENDTIME, fEndTime);		
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		((Activity)context).finish();
	}
	
	/** 
	* @Title: showExpenseFlowClientList 
	* @Description: 
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����2:50:03
	*/
	public static void showExpenseFlowClientList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseClientSearchListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.EXPENSE_FLOW_DETAIL_CLIENT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowProjectList 
	* @Description: ��ת����ˮ��Ŀ����ҳ��
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����2:50:57
	*/
	public static void showExpenseFlowProjectList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseProjectSearchListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.EXPENSE_FLOW_DETAIL_PROJECT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: showContacts 
	* @Description: ��ʾͨѶ¼����
	* @param @param context �����Ļ���  
	* @param @param fItemNumber Ա����   
	* @param @param fSourceType ��Դ����     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��15�� ����2:23:39
	*/
	public static void showContacts(Context context,String fItemNumber,String fSourceType){
		Intent intent = new Intent(context, ContactsMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.CONTACTS_SOURCE_TYPE, fSourceType);
		/*����ͬ�¹��ܣ���Ҫ�ش�����������Ա*/
		((Activity)context).startActivityForResult(intent,AppContext.CONTACTS_SMS_SEARCH);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeeting 
	* @Description: ��ʾ�ҵĻ����б����
	* @param @param context �����Ļ���     
	* @param @param fItemNumber  Ա����       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����11:37:13
	*/
	public static void showMeetingList(Context context,String fItemNumber){
		Intent intent = new Intent(context, MeetingListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingDetail 
	* @Description: ��ʾ�ҵĻ����������
	* @param @param context �����Ļ���  
	* @param @param fItemNumber Ա����
	* @param @param fOrderId ������������    
	* @param @param fStatus ״̬    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����1:39:41
	*/
	public static void showMeetingDetail(Context context,String fItemNumber,String fOrderId,String fStatus){
		Intent intent = new Intent(context, MeetingDetailActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_FSTATUS, fStatus);
		intent.putExtra(AppContext.MEETING_DETAIL_FORDERID, fOrderId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_INFO);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingRoomSearch 
	* @Description: ��ʾ�����������б����
	* @param @param context �����Ļ���     
	* @param @param fItemNumber Ա����
	* @param @param fSelectedDate ѡ�е�����
	* @param @param fSelectedStart ѡ�еĿ�ʼ����
	* @param @param fSelectedEnd  ѡ�еĽ�������        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����5:10:25
	*/
	public static void showMeetingRoomSearch(Context context,String fItemNumber,String fSelectedDate,String fSelectedStart,String fSelectedEnd){
		Intent intent = new Intent(context, MeetingRoomListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDDATE, fSelectedDate);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDSTART, fSelectedStart);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDEND, fSelectedEnd);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_ROOM);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: showMeetingPersonSearch 
	* @Description: ��ʾ��Ա�����б����
	* @param @param context �����Ļ���  
	* @param @param personList ��Ա�����ַ���
	* @param @param fLag ���ݵ�״̬��־        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����5:46:31
	*/
	public static void showMeetingPersonSearch(Context context,String personList,String fLag){
		Intent intent = new Intent(context, MeetingPersonListActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST, personList);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG, fLag);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_PERSON);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingMasterSearch 
	* @Description: ��ȡ�����������б����
	* @param @param context �����Ļ���     
	* @param @param personList ��Ա����
	* @param @param fLag ���ݵ�״̬��־       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����9:01:40
	*/
	public static void showMeetingMasterSearch(Context context,String personList,String fLag){
		Intent intent = new Intent(context, MeetingPersonListActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST, personList);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG, fLag);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_MASTER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showCapture 
	* @Description: ��ʾɨһɨ����
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����4:33:56
	*/
	public static void showCapture(Context context) {
		Intent intent = new Intent(context, CaptureActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showTaskDetail 
	* @Description: �����жϣ�����ϵͳID�͵������� ����ʾ��ͬ������ϸ��Ϣ
	* @param @param context ����������
	* @param @param task ����ʵ��
	* @param @param fStatus ����״̬    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��7�� ����7:58:44
	*/
	public static void showTaskDetail(Context context, TaskInfo task,String fStatus){
		String fMenuId=String.valueOf(task.getFMenuID());
		String fSystemType=String.valueOf(task.getFSystemType());
		String fClassTypeId=String.valueOf(task.getFClassTypeID());	
		String fBillId=String.valueOf(task.getFBillID());
		Intent intent=null;
		
		if("19".equals(fSystemType) && "280002156".equals(fClassTypeId)){
			//������Ȧ������ʵ��
			intent = new Intent(context, EngineeringActivity.class);	
		} 
		
		if("8".equals(fSystemType) && "20140001".equals(fClassTypeId)){
			//������˽����ʵ��
			intent = new Intent(context, ExpensePrivateTHeaderActivity.class);	
		}
		 
		if("8".equals(fSystemType) && "20140002".equals(fClassTypeId)){
			//�����Թ�����ʵ��
			intent = new Intent(context, ExpensePublicTHeaderActivity.class);
		}
		
		if("8".equals(fSystemType) && "20140003".equals(fClassTypeId)){
			//�������񵥾�ʵ��
			intent = new Intent(context, ExpenseSpecialTHeaderActivity.class);
		}
		
		if("8".equals(fSystemType) && "20140008".equals(fClassTypeId)){
			//�������񵥾�ʵ��
			intent = new Intent(context, ExpenseSpecialThingHeaderActivity.class);
		}
		 
		if("8".equals(fSystemType) && "20140004".equals(fClassTypeId)){
			//�������뵥��ʵ��
		    intent = new Intent(context, ExpenseCostTHeaderActivity.class);
		}
		 
		if("8".equals(fSystemType) && "20140006".equals(fClassTypeId)){
			//�г�Ͷ��֧������ʵ��
			intent = new Intent(context, ExpenseMarketPayTHeaderActivity.class);
		}
	   
		if("8".equals(fSystemType) && "20140007".equals(fClassTypeId)){
			//�г�Ͷ�걨������ʵ��
			intent = new Intent(context, ExpenseMarketBidTHeaderActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000001".equals(fClassTypeId)){
			//������뵥��ʵ��
			intent = new Intent(context, ApplyLeaveActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000002".equals(fClassTypeId)){
			//�������뵥��ʵ��
			intent = new Intent(context, ApplyResumeActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000003".equals(fClassTypeId)){
			//�Ӱ����뵥��ʵ��
			intent = new Intent(context, ApplyOverTimeActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000004".equals(fClassTypeId)){
			//��ͨ���ŵ������뵥��ʵ��
			intent = new Intent(context, ApplyDaysOffActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000006".equals(fClassTypeId)){
			//�з����ŵ������뵥��ʵ��
			intent = new Intent(context, ApplyDaysOffDevelopActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000007".equals(fClassTypeId)){
			//�쳣���ڵ������뵥��ʵ��
			intent = new Intent(context, ExAttendanceActivity.class);
		}
		
		if ("38".equals(fSystemType) && "290002186".equals(fClassTypeId))
		{
			//���ڹ��׽�������
			intent = new Intent(context, ContributionAwardActivity.class);
		}
	    
		if("18".equals(fSystemType) && "200001053".equals(fClassTypeId)){
			//����Ȩ�����뵥��ʵ��
			intent = new Intent(context, NetworkPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002216".equals(fClassTypeId)){
			//�з���Ŀ����Ȩ�����뵥��ʵ��
			intent = new Intent(context, DevelopTestNetworkActivity.class);
		}
		
		if("18".equals(fSystemType) && "100000018".equals(fClassTypeId)){
			//�󻪳е��������뵥��ʵ��
			intent = new Intent(context, DaHuaAssumeCostActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002160".equals(fClassTypeId)){
			//�з�����ѯ�����뵥��ʵ��
			intent = new Intent(context, DevelopInquiryActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002238".equals(fClassTypeId)){
			//MEM�������뵥��ʵ��
			intent = new Intent(context, MemRequreActivity.class);
		}
		
		if("18".equals(fSystemType) && "230001132".equals(fClassTypeId)){
			//�ļ����������뵥��ʵ��
			intent = new Intent(context, DocumentApproveActivity.class);
		}
		
		if("18".equals(fSystemType) && "230001092".equals(fClassTypeId)){
			//SVNȨ�����뵥��ʵ��
			intent = new Intent(context, SvnPermissionActivity.class);
		}
		 
		if("18".equals(fSystemType) && "200001021".equals(fClassTypeId)){
			//�²�Ʒת�����뵥��ʵ��
			intent = new Intent(context, NewProductLibActivity.class);
		}
		
		if("18".equals(fSystemType) && "100000011".equals(fClassTypeId)){
			//�з�������ǲ���뵥��ʵ��
			intent = new Intent(context, DevelopTravelActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001081".equals(fClassTypeId)){
			//�ɹ��������뵥��ʵ��
			intent = new Intent(context, PurchaseStockActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001040".equals(fClassTypeId)){
			//���俪ͨ���뵥��ʵ��
			intent = new Intent(context, EmailOpenActivity.class);
		}
		
		if("18".equals(fSystemType) && "230002046".equals(fClassTypeId)){
			//�̶��ʲ���������ɹ��������뵥��ʵ��
			intent = new Intent(context, FixedAssetsSpecialActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001035".equals(fClassTypeId)){
			//��ֵ�׺����ϴ������뵥��ʵ��
			intent = new Intent(context, LowConsumableActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001054".equals(fClassTypeId)){
			//��ѵ���㻯�������뵥��ʵ��
			intent = new Intent(context, TrainComputerActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002154".equals(fClassTypeId)){
			//�����������뵥��ʵ��
			intent = new Intent(context, TravelApprovalActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002152".equals(fClassTypeId)){
			//�²�Ʒ�������뵥��ʵ��
			intent = new Intent(context, NewProductReworkActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001052".equals(fClassTypeId)){
			//�Ž�Ȩ�����뵥��ʵ��
			intent = new Intent(context, DoorPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002167".equals(fClassTypeId)){
			//�����ļ��������뵥��ʵ��
			intent = new Intent(context, TdBorrowActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001081".equals(fClassTypeId)){
			//TDȨ�����뵥��ʵ��
			intent = new Intent(context, TdPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001083".equals(fClassTypeId)){
			//��Ŀ��Ϣ�Ķ�Ȩ�����뵥��ʵ��
			intent = new Intent(context, ProjectReadActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001068".equals(fClassTypeId)){
			//ӡ���������뵥��ʵ��
			intent = new Intent(context, FeDestroyActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001059".equals(fClassTypeId)){
			//ӡ���������뵥��ʵ��
			intent = new Intent(context, FeEngravingActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001060".equals(fClassTypeId)){
			//ӡ��������뵥��ʵ��
			intent = new Intent(context, FeTakeOutActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001070".equals(fClassTypeId)){
			//ӡ���ƽ����뵥��ʵ��
			intent = new Intent(context, FeTransferActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001069".equals(fClassTypeId)){
			//ӡ���������뵥��ʵ��
			intent = new Intent(context, FeUpdateActivity.class);
		}
		if(intent!=null){
			showIntent(context,intent,fMenuId,fSystemType,fClassTypeId,fBillId,fStatus);
		}
		else {
			ToastMessageLong(context,R.string.tasklist_notfind);
		}
	}
	
	/** 
	* @Title: showIntent 
	* @Description: ��ʾ��ͼ��ת
	* @param @param context
	* @param @param intent
	* @param @param fMenuId
	* @param @param fSystemType
	* @param @param fClassTypeId
	* @param @param fBillId
	* @param @param fStatus     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��13�� ����4:55:41
	*/
	private static void showIntent(final Context context,final Intent intent,final String fMenuId,final String fSystemType,final String fClassTypeId,final String fBillId,final String fStatus){
		intent.putExtra(AppContext.FSTATUS_KEY, fStatus);
		intent.putExtra(AppContext.FMENUID_KEY, fMenuId);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemType);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEPUBLIC);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showPlusCopy 
	* @Description: ��ʾ��ǩ/����ҳ��
	* @param @param context �����Ļ���
	* @param @param fType ��ǩ���߳��� "0"-�����ǩ  "1"-������
	* @param @param fSystemId ϵͳID
	* @param @param fClassTypeId ��������ID
	* @param @param fBillId ����ID 
	* @param @param fItemNumber Ա����    
	* @param @param fBillName ��������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����5:22:41
	*/
	public static void showPlusCopy(Context context,String fType,String fSystemId,String fClassTypeId,String fBillId,String fItemNumber,String fBillName){
		Intent intent = new Intent(context, PlusCopyActivity.class);
		intent.putExtra(AppContext.FPLUSCOPY_TYPE_KEY, fType);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, fBillName);
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showPlusCopyPersonSearch 
	* @Description: ��ʾ��ǩ/����ģ����Ա�����б����
	* @param @param context �����Ļ���  
	* @param @param personList  �Ѿ�ѡ�����Ա�����ַ���    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����1:44:18
	*/
	public static void showPlusCopyPersonSearch(Context context,String personList){
		Intent intent = new Intent(context, PlusCopyPersonActivity.class);
		intent.putExtra(AppContext.PLUSCOPY_PERSON_LIST, personList);
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLowerNodeApp 
	* @Description: ��ʾ�¼��ڵ�ҳ��
	* @param @param context  	�����Ļ���
	* @param @param fSystemId 	ϵͳID
	* @param @param fClassTypeId ��������ID
	* @param @param fBillId ����ID 
	* @param @param fItemNumber Ա����  
	* @param @param fBillName ��������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��13�� ����2:35:13
	*/
	public static void showLowerNodeApp(Context context,String fSystemId,String fClassTypeId,String fBillId,String fItemNumber,String fBillName){
		Intent intent = new Intent(context, LowerNodeApproveActivity.class);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, fBillName);
		
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDH 
	* @Description: ��ʾ�з���ʱ��ҳ��
	* @param @param context  �����Ļ���
	* @param @param fItemNumber Ա����   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����11:49:50
	*/
	public static void showDH(Context context,String fItemNumber){
		Intent intent = new Intent(context, DHMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHList 
	* @Description: ��ʾ��ʱ�б�ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��22�� ����9:26:44
	*/
	public static void showDHList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, DHListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHProjectList 
	* @Description: ��ʾÿ���������Ŀ�б�ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����    
	* @param @param fBillId �ܵ���ID
	* @param @param fWeekValue ÿ��ֵ
	* @param @param fWeekDate ÿ��ʱ��ֵ        
	* @param @param type ����   show���ͺ�edit����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����10:15:14
	*/
	public static void showDHProjectList(Context context,String fItemNumber,Integer fBillId,String fWeekValue,String fWeekDate,String type) {
		Intent intent = new Intent(context, DHListProjectActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_DATE, fWeekDate);
		intent.putExtra(AppContext.DEVELOP_HOURS_LIST_PROJECT_TYPE, type);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHDetail 
	* @Description: ��ʾ��ʱ����ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����
	* @param @param fActionType ��������  "Add"��"Edit"
	* @param @param fAccess �����������,�а�ť���,��ʱ�б����,��Ŀ�б����,���������б����       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��18�� ����4:53:29
	*/
	public static void showDHDetail(Context context,String fItemNumber,String fActionType,String fAccess ) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHListDetail 
	* @Description: ��ʾ��ʱ����ҳ��
	* @param @param context �����Ļ���
	* @param @param fBillId �����Ļ���
	* @param @param fWeekValue ÿ��ֵ  
	* @param @param fItemNumber Ա���� 
	* @param @param fActionType ��������  "Add"��"Edit"
	* @param @param fAccess  �����������,�а�ť���,��ʱ�б����,��Ŀ�б����,���������б����  
	* @param @param fWeekDate  ���ݹ����Ĺ�ʱ����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��18�� ����4:49:45
	*/
	public static void showDHListDetail(Context context,Integer fBillId,String fWeekValue,String fItemNumber,String fActionType,String fAccess,String fWeekDate) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_WEEKDATE, fWeekDate);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHListProjectDetail 
	* @Description: ��ʾ��ʱ����ҳ��
	* @param @param context �����Ļ���
	* @param @param fBillId �ܵ���ID
	* @param @param fWeekValue ÿ��ֵ  
	* @param @param fItemNumber Ա���� 
	* @param @param fActionType��������  "Add"��"Edit"
	* @param @param fAccess �����������,�а�ť���,��ʱ�б����,��Ŀ�б����,���������б����  
	* @param @param fWeekDate ���ݹ����Ĺ�ʱ����
	* @param @param fProjectCode ���ݹ�������Ŀ���
	* @param @param fProjectName ���ݹ�������Ŀ����
	* @param @param fTypeId ���ݹ�������������ID       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��18�� ����3:50:58
	*/
	public static void showDHListProjectDetail(Context context,Integer fBillId,String fWeekValue,String fItemNumber,String fActionType,String fAccess,String fWeekDate,String fProjectCode,String fProjectName,String fTypeId) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_WEEKDATE, fWeekDate);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE, fProjectCode);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTNAME, fProjectName);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_TYPEID, fTypeId);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHProjectSearch 
	* @Description: ��ʾ��Ŀ����ҳ��
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��4�� ����11:20:59
	*/
	public static void showDHProjectSearch(Context context) {
		Intent intent = new Intent(context, DHProjectSearchActivity.class);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL_PROJECT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHTypeList 
	* @Description: ��ʾ��������б�
	* @param @param context �����Ļ��� 
	* @param @param fProjectCode ��Ŀ����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��4�� ����11:23:11
	*/
	public static void showDHTypeList(Context context,String fProjectCode) {
		Intent intent = new Intent(context, DHTypeListActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE, fProjectCode);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL_TYPE);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHConfirmList 
	* @Description: ��ʾ��ʱȷ���б�ҳ��
	* @param @param context �����Ļ���
	* @param @param fItemNumber Ա����    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����11:48:20
	*/
	public static void showDHConfirmList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, DHConfirmListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHConfirmListPerson 
	* @Description: ��ʾ��ʱȷ���б������Ա��Ϣ
	* @param @param context �����Ļ���
	* @param @param fProjectNumber ��Ŀ����Ա����
	* @param @param fWeekIndex �ܴ�
	* @param @param fYear ���
	* @param @param fProjectCode ��Ŀ����
	* @param @param fConfrimNumber ȷ����ԱԱ����     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����9:50:48
	*/
	public static void showDHConfirmListPerson(Context context,String fProjectNumber,Integer fWeekIndex,Integer fYear,String fProjectCode,String fConfrimNumber) {
		Intent intent = new Intent(context, DHConfirmListPersonActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER, fProjectNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX, fWeekIndex);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_YEAR, fYear);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE, fProjectCode);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER, fConfrimNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showAttendanceList 
	* @Description: ��ʾ�ҵĿ����б����
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��17�� ����4:25:10
	*/
	public static void showAttendanceList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, AdListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showAttendanceCheck 
	* @Description: ��ʾǩ��/ǩ���򿨽���
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:27:57
	*/
	public static void showAttendanceCheck(Context context,String fItemNumber) {
		Intent intent = new Intent(context, AdCheckInActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketMain 
	* @Description: ��ʾ�ҵ�������ҳ
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��26�� ����1:59:45
	*/
	public static void showMarketMain(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	

	/** 
	* @Title: showMarketBidSearch 
	* @Description: ��ʾ���۲�ѯ
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��26�� ����2:01:09
	*/
	public static void showMarketBidSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketBidSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketContractSearch 
	* @Description: ��ʾ��ͬ��ѯ
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��26�� ����2:01:53
	*/
	public static void showMarketContractSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketContractSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketProductSearch 
	* @Description: ��ʾ��Ʒ��ѯ
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��30�� ����9:30:52
	*/
	public static void showMarketProductSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketProductSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketWorkflow 
	* @Description: ��ʾ����ģ�鹤������Ϣ
	* @param @param context
	* @param @param fItemNumber
	* @param @param fSystemType
	* @param @param fClassTypeId
	* @param @param fBillID
	* @param @param type     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��2�� ����11:42:09
	*/
	public static void showMarketWorkflow(Context context,String fItemNumber,String fSystemType,String fClassTypeId,String fBillID,String type) {
		Intent intent = new Intent(context, MarketWorkflowActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
		intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
		intent.putExtra(AppContext.MARKET_WORKFLOW_TYPE, type);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: sendBroadCast 
	* @Description: ����֪ͨ�㲥
	* @param @param context
	* @param @param notice ֪ͨ��    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��8�� ����9:32:14
	*/
	public static void sendBroadCast(Context context, int notice) {
		if (notice ==0)
			return;
		Intent intent = new Intent("com.dahuatech.app.action.APPWIDGET_UPDATE");
		intent.putExtra(AppContext.BROADCAST_WAITTASKCOUNT_KEY, notice);
		context.sendBroadcast(intent);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ������־ͳ����Ϣ
	* @param @param context ����������
	* @param @param logInfo ��־��Ϣ      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����11:32:42
	*/
	public static void sendLogs(Context context,LogsRecordInfo logInfo){
		mContext=context;
		mInstance.new logRecordAsync().execute(logInfo);
	}
	
	/**
	 * @ClassName logRecordAsync
	 * @Description ��־ͳ���첽����
	 * @author 21291
	 * @date 2014��7��31�� ����10:46:26
	 */
	private class logRecordAsync extends AsyncTask<LogsRecordInfo , Void, Void> {
		
	   @Override
	   protected Void doInBackground(LogsRecordInfo... params) {
		   String serviceUrl=AppUrl.URL_API_HOST_ANDROID_LOGSRECORD;  
		   FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(mContext);
		   SettingBusiness sBusiness =(SettingBusiness)factoryBusiness.getInstance("SettingBusiness",serviceUrl);   
		   sBusiness.SendLogRecord(params[0]);
		   return null;   
	   }

	   @Override
	   protected void onPostExecute(Void result) { }
	}

	/** 
	* @Title: Exit 
	* @Description: �˳�����
	* @param @param cont     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����1:40:47
	*/
	public static void Exit(final Context cont) {
		TypedValue typedValue = new TypedValue();
		cont.getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(typedValue.resourceId);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						AppManager.getAppManager().AppExit(cont); // �˳�
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
	
	/** 
	* @Title: sendAppCrashReport 
	* @Description: ����App�쳣��������
	* @param @param cont ������
	* @param @param crashReport ���������Ϣ 
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����1:40:00
	*/
	@SuppressLint("InlinedApi")
	public static void sendAppCrashReport(final Context cont,final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// �����쳣����
						Intent i = new Intent(Intent.ACTION_SEND);
						//i.setType("text/plain"); //ģ����
						i.setType("message/rfc822"); // ���
						i.putExtra(Intent.EXTRA_EMAIL,new String[] { "22292436@qq.com" });
						i.putExtra(Intent.EXTRA_SUBJECT,"���ƶ��칫Android�ͻ��� - ���󱨸�");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "���ʹ��󱨸�"));
						// �˳�
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// �˳�
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}
	
	/**
	 * ����Toast��Ϣ
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void ToastMessageLong(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	
	/** 
	* @Title: finish 
	* @Description: ������ؼ����¼�
	* @param @param activity
	* @param @return     
	* @return View.OnClickListener    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����3:37:33
	*/
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/** 
	* @Title: loginOrLogout 
	* @Description: �û���¼��ע��
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����3:26:58
	*/
	public static void loginOrLogout(Activity activity) {
		AppContext ac = (AppContext) activity.getApplication();
		if (ac.isLogin()) {
			ac.Logout();
			ToastMessage(activity, "���˳���¼");
		} else {
			showLogin(activity,false,true);
		}
	}
	
	/** 
	* @Title: clearAppCache 
	* @Description: ���app����
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����3:27:06
	*/
	public static void clearAppCache(Activity activity) {
		final AppContext ac = (AppContext) activity.getApplication();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					ToastMessage(ac, "��������ɹ�");
				} else {
					ToastMessage(ac, "�������ʧ��");
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					ac.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
}
