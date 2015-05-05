package com.dahuatech.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.CacheManager;

import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.common.MethodsCompat;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName AppContext
 * @Description ��Ӧ�ó����ࣺ���ڱ���͵���ȫ��Ӧ�����ü�������������
 * @author 21291
 * @date 2014��4��16�� ����1:43:29
 */
@SuppressLint("DefaultLocale")
public class AppContext extends Application {
	//����Ա��
	public static final String TEMPTEST_FITEMNUMBER="27060";  //��ʱ���Ե�Ա����
	public static final String TEMPTEST_FITEMNAME="27060";  //��ʱ���Ե�Ա������

	//����ҳ�ͻ�ӭҳ��ѡ�������ļ�
	public static final String GUIDEANDWELCOME_CONFIG_FILE = "GUIDE_WELCOME_CONFIG_FILE"; 
	public static final String IS_FIRST_COUNT_KEY="Is_First_Count_Key"; //����ҳ��½����
	
	/**************��½���ֿ�ʼ*****************/
	
	//LoginActivity��ѡ�������ļ�
	public static final long LOGINACTIVITY_SESSION = 5;	 								//��½����ʱ������ ����Ϊ��λ Ĭ��5����
	public static final String LOGINACTIVITY_CONFIG_FILE = "LOGINACTIVITY_CONFIG_FILE"; 
	public static final String USER_NAME_KEY="userName_Key"; 							//Ա����key
	public static final String PASSWORD_KEY="password_Key"; 							//����key
	public static final String FITEMNAME_KEY="fItemName_Key"; 							//Ա������key
	public static final String ACCURATE_EXPIRES_TIME = "accurate_expires_time";  		//session key�����ϵĹ���ʱ��
	public static final String FNOTICE_ALARM_KEY="fNotice_Alarm_Key"; 			 		//֪ͨ����Ϣ
	public static final String FNOTICE_LOGIN_IS_SHOW_KEY="fNotice_Login_Is_show_Key"; 	//��½�Ƿ���֪ͨ����Ϣ
	public static final String FLOGIN_IS_FIRST_KEY="fLogin_Is_First_Key"; 				//�Ƿ��״ε�½
	
	/**************��½���ֽ���*****************/
	
	/**************��ҳ���ֿ�ʼ*****************/
	
	//MainActivity��ѡ�������ļ� 
	public static final String FITEMNUMBER_KEY="fItemNumber_Key"; 						//Ա����
	/**************��ҳ���ֽ���*****************/

	/**************���ò��ֿ�ʼ*****************/
	
	//SettingActivity��ѡ�������ļ�
	public static final String SETTINGACTIVITY_CONFIG_FILE = "SETTINGACTIVITY_CONFIG_FILE"; 
	public static final String IS_NOTICE_KEY="isNotice_Key"; 						//�Ƿ�����֪ͨ��
	public static final String IS_GESTURES_KEY="is_Gestures_key"; 					//�Ƿ�������������
	
	public static final String IS_EXPENSE_JURNEY_KEY="is_expense_Jurney_Key"; 	  	//�򳵱�����ҳ���ذ�ť
	public static final String IS_EXPENSE_ADDRESS_KEY="is_expense_address_Key"; 	//��ʼ��ַ
	public static final String IS_EXPENSE_TIME_KEY="is_expense_time_Key"; 			//��ʼʱ��
	
	/**************���ò��ֽ���*****************/
	
	/**************�������벿�ֽ���*****************/
	
	public static final String GESTURES_PASSWORD_SOURCE_KEY="gestures_password_source_key"; //����������Դ
	
	/**************�������벿�ֽ���*****************/
	
	/**************�����б��ֿ�ʼ***************/
	
	//TaskListActivity��ѡ�������ļ�
	public static final String TASKLISTACTIVITY_CONFIG_FILE = "TASKLISTACTIVITY_CONFIG_FILE"; 
	public static final String FSTATUS_KEY="fStatus_Key"; 							//Ĭ�ϱ����¼״̬ 0-������ 1-������
	public static final String FTOTALCOUNT_KEY="fTotalCount_key";  					//��ҳ��ȡ���ܼ�¼��
	public static final String PAGE_SIZE = "4";										//Ĭ�Ϸ�ҳ��С
	
	/**************�����б��ֽ���****************/
	
	/**************�򿨲��ֿ�ʼ***************/
	
	public static final String ADCHECKINACTIVITY_CONFIG_FILE = "ADCHECKINACTIVITY_CONFIG_FILE";	  //�򿨴洢�ļ�
	public static final String AD_AMAP_ADDRESS_KEY="ad_aMap_address_Key"; 						  //�����ĵ�ַ
	public static final String AD_AMAP_ADDRESS_CACHE_KEY="ad_aMap_address_cache_Key"; 			  //����汾
	
	/**************�򿨲��ֽ���****************/
	
	/**************������ϸҳ���ֿ�ʼ****************/
	
	//������ϸҳ��ѡ�������ļ�
	public static final String FMENUID_KEY="FMenuID_Key";  
	public static final String FSYSTEMTYPE_KEY="FSystemType_Key";  
	public static final String FBILLID_KEY="FBillID_Key";  
	public static final String FCLASSTYPEID_KEY="FClassTypeId_Key";  
	
	//��˽���� ��������IDֵ
	public static final String FEXPENSEPRIVATE_COSTTYPE_KEY="FExpensePrivate_CostType_key";
	
	/**************������ϸҳ���ֽ���****************/
	
	/**************������ҳ�沿�ֿ�ʼ****************/
	
	public static final String TA_APPROVE_FSTATUS = "TA_Approve_FStatus";//�����������״̬
	public static final int ACTIVITY_WORKFLOW = 0x11;  		//δ��������¼
	public static final int ACTIVITY_WORKFLOWBEEN = 0x12; 	//����������¼
	
	//WorkFlowActivity��ѡ�������ļ�
	public static final String WORKFLOW_FSYSTEMTYPE_KEY="Workflow_FSystemType_Key";  
	public static final String WORKFLOW_FCLASSTYPEID_KEY="Workflow_fClassTypeId_Key";  
	public static final String WORKFLOW_FBILLID_KEY="Workflow_fBillId_Key";  
	public static final String WORKFLOW_FBILLNAME_KEY="Workflow_fBillName_Key"; 
	//����������key
	public static final String WORKFLOW_ENGINEERING_KEY="B3-F0-C7-F6-BB-76-3A-F1-BE-91-D9-E7-4E-AB-FE-B1-99-DC-1F-1F";
	//����ϵͳ��������key
	public static final String WORKFLOW_EXPENSEPRIVATE_KEY="FE-5D-BB-CE-A5-CE-7E-29-88-B8-C6-9B-CF-DF-DE-89-04-AA-BC-1F";
	//�°湤����ƽ̨����key
	public static final String WORKFLOW_NEWOFFICE_KEY="9E-6A-55-B6-B4-56-3E-65-2A-23-BE-9D-62-3C-A5-05-5C-35-69-40";
	//���ڵ�������key(�°�)
	public static final String WORKFLOW_ATTENDANCE_HR_KEY="5B-38-4C-E3-2D-8C-DE-F0-2B-C3-A1-39-D4-CA-C0-A2-2B-B0-29-E8";
	
	//PlusCopyActivity�����ֶ�
	public static final String FPLUSCOPY_TYPE_KEY="fPlusCopy_Type_Key"; //��ǩ/���� ����
	public static final int FPLUSCOPY_PERSON_KEY = 0x20;   				//��ת����Ա�б�
	public static final String PLUSCOPY_PERSON_LIST="pluscopy_person_list";    //��Ա�б���ѡ�е���Ա
	
	/**************������ҳ�沿�ֽ���****************/
	
	/**************֪ͨ�����񲿷ֿ�ʼ****************/
	
	public static final String BROADCAST_WAITTASKCOUNT_KEY="BroadCast_WaitTaskCount_Key"; //BroadCast��ѡ�������ļ�  
	
	/**************֪ͨ�����񲿷ֽ���****************/
	
	/**************�ҵ���ˮģ�鿪ʼ****************/
	
	public static final String EXPENSE_FLOW_DETAIL_STATUS="expense_flow_detail_status_Key";  //�ҵ���ˮ����״̬ 0-����,1-�޸�
	public static final String EXPENSE_FLOW_DETAIL_EXPENDTIME="expense_flow_detail_expendtime_Key";  //�Ƿ��д��ݹ���������ʱ��
	public static final String EXPENSE_FLOW_DETAIL_SKIP_SOURCE="expense_flow_detail_skip_source_Key";  //��ת��Դ
	
	public static final String EXPENSE_FLOW_DETAIL_BACK_FID="back_fId"; 		//�ش�������
	public static final String EXPENSE_FLOW_DETAIL_BACK_FNAME="back_fName"; 	//�ش�������
	
	public static final int EXPENSE_FLOW_DETAIL_CLIENT = 0x14; 		//��ת���ͻ��б�
	public static final int EXPENSE_FLOW_DETAIL_PROJECT = 0x15; 	//��ת����Ŀ�б�
	
	/**************�ҵ���ˮģ�����****************/
	
	/**************�ҵĻ���ģ�鿪ʼ****************/	
	
	public static final String MEETING_DETAIL_FSTATUS="meeting_detail_fStatus";     //��������״̬
	public static final String MEETING_DETAIL_FORDERID="meeting_detail_fOrderId";    //������������
	
	public static final String MEETING_DETAIL_SELECTEDDATE="meeting_detail_selecteddate";    //��������ҳ���Ѿ�ѡ�е�����
	public static final String MEETING_DETAIL_SELECTEDSTART="meeting_detail_selectedstart";  //��������ҳ���Ѿ�ѡ�еĿ�ʼʱ��
	public static final String MEETING_DETAIL_SELECTEDEND="meeting_detail_selectedend";      //��������ҳ���Ѿ�ѡ�еĽ���ʱ��
	
	public static final String MEETING_DETAIL_ROOM_ID="meeting_detail_room_id";    	 //������ID
	public static final String MEETING_DETAIL_ROOM_NAME="meeting_detail_room_name";  //����������
	public static final String MEETING_DETAIL_ROOM_IP="meeting_detail_room_ip";      //������IP
	
	public static final String MEETING_DETAIL_PERSON_LIST="meeting_detail_person_list";    //��������ҳ�洫���Ѿ�ѡ�в������Ա
	public static final String MEETING_DETAIL_PERSON_FLAG="meeting_detail_person_flag";    //��Ա�����б�ҳ�������ͱ�־
	
	public static final int MEETING_DETAIL_ROOM = 0x16;   //��ת���������б�
	public static final int MEETING_DETAIL_PERSON = 0x17; //��ת����Ա�����б�
	public static final int MEETING_DETAIL_MASTER = 0x18; //��ת����Ա�����б�
	public static final int MEETING_DETAIL_INFO = 0x19;   //��ת����������ҳ
	
	/**************�ҵĻ���ģ�����****************/
	
	/**************�з���ʱģ�鿪ʼ****************/	
	
	public static final String DEVELOP_HOURS_WEEK_BILLID="develop_hours_fBillId";    					//�ܵ���ID
	public static final String DEVELOP_HOURS_WEEK_VALUE="develop_hours_fWeekValue";    					//ÿ��ֵ
	public static final String DEVELOP_HOURS_WEEK_DATE="develop_hours_fWeekDate";      					//ÿ��ʱ��ֵ
	public static final String DEVELOP_HOURS_LIST_PROJECT_TYPE="develop_hours_list_project_type";   	//��ʾ����
	
	public static final String DEVELOP_HOURS_DETAIL_ACTION_TYPE="dh_detail_action_type";     			//�����������
	public static final String DEVELOP_HOURS_DETAIL_ACCESS="dh_detail_access";      		 			//�����������
	
	public static final String DEVELOP_HOURS_DETAIL_PASS_WEEKDATE="dh_detail_pass_WeekDate";  			//���ݹ����Ĺ�����ʱ��
	public static final String DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE="dh_detail_pass_ProjectCode";  	//���ݹ�������Ŀ���
	public static final String DEVELOP_HOURS_DETAIL_PASS_PROJECTNAME="dh_detail_pass_ProjectName";  	//���ݹ�������Ŀ����
	public static final String DEVELOP_HOURS_DETAIL_PASS_TYPEID="dh_detail_pass_TypeId";  				//���ݹ�������������ID
	
	public static final int DEVELOP_HOURS_DETAIL_PROJECT = 0x31;   	//��ת����Ŀ����ҳ��
	public static final int DEVELOP_HOURS_DETAIL_TYPE = 0x32;   	//��ת���������ҳ��
	public static final int DEVELOP_HOURS_DETAIL = 0x33;   			//��ת����ʱ����ҳ��
	
	public static final String DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE="dh_detail_back_fProjectCode"; 	//��Ŀ�����б�ش�����Ŀ���
	public static final String DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME="dh_detail_back_fProjectName"; 	//��Ŀ�����б�ش�����Ŀ����
	
	public static final String DEVELOP_HOURS_DETAIL_BACK_FTYPEID="dh_detail_back_fTypeId"; 				//��������б�ش�������ID
	public static final String DEVELOP_HOURS_DETAIL_BACK_FTYPENAME="dh_detail_back_fTypeName"; 			//��������б�ش�����������
	
	public static final String DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER="dh_confirm_pass_ProjectNumber";  	//ȷ���б��ݹ�������Ŀ������
	public static final String DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX="dh_confirm_pass_WeekIndex";  			//ȷ���б��ݹ������ܴ�
	public static final String DEVELOP_HOURS_CONFIRM_PASS_YEAR="dh_confirm_pass_Year";  					//ȷ���б��ݹ��������
	public static final String DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE="dh_confirm_pass_ProjectCode";  		//ȷ���б��ݹ�������Ŀ���
	public static final String DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER="dh_confirm_pass_ConfrimNumber";  	//ȷ���б��ݹ�����ȷ����ԱԱ����

	/**************�з���ʱģ�����****************/	
	
	/**************ͨѶ¼ģ�鿪ʼ****************/	
	
	public static final String CONTACTS_SOURCE_TYPE="contacts_source_type";     //ͨѶ¼��Դ����
	public static final String CONTACTS_RETURN_VALUE="contacts_return_value";    //ͨѶ¼����ֵ
	public static final int CONTACTS_SMS_SEARCH = 0x30; 	  					//����ͬ��ҳ����ת��ͨѶ¼
	
	/**************ͨѶ¼ģ�����****************/	
	
	/**************�ҵ�����ģ�鿪ʼ****************/	
	
	public static final String MARKET_WORKFLOW_TYPE="market_workflow_type";     //�ҵ��������̼�¼����
	
	/**************�ҵ�����ģ�����****************/	
	

	/**************�����������Ϳ�ʼ****************/	
	
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	
	/**************�����������ͽ���****************/	
	
	/**************������Ϣģ�鿪ʼ****************/	
	
	private static final int CACHE_TIME = 60*60000;//����ʧЧʱ�� 1��Сʱ
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	private boolean login = false;	// ��¼״̬
	private int loginUid =0;	// ��½��Ա����

	public boolean containsProperty(String key){
		Properties props = getProperties();
		return props.containsKey(key);
	}
	
	public void setProperties(Properties ps){
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties(){
		return AppConfig.getAppConfig(this).get();
	}
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	public void removeProperty(String...key){
		AppConfig.getAppConfig(this).remove(key);
	}
	
	/**************������Ϣģ�����****************/	
	
	/**************�ٶȵ�ͼ�����ֿ�ʼ****************/
	private static AppContext mInstance;
	private static Context mContext;
	static{
		mInstance=null;
		mContext=null;
	}
	
	//��ȡ����ʵ��
	public static AppContext getInstance() {
		return mInstance;
	}
	
	//��ȡȫ��������ʵ��
	public static Context getContext() {
		return mContext;
	}
	
	/**************�ٶȵ�ͼ�����ֽ���****************/

	@Override
	public void onCreate() {
		super.onCreate();
		try {
	        Class.forName("android.os.AsyncTask");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		if(mInstance==null){   //ʵ��������
			mInstance = this;
		}
		if(mContext==null){
			mContext=getApplicationContext();
		}
		//ע��App�쳣����������
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());     
	}

	//��������app���˳�֮ǰ����mapadpi��destroy()�����������ظ���ʼ��������ʱ������
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	/** 
	* @Title: getPackageInfo 
	* @Description: ��ȡApp��װ����Ϣ
	* @param @return     
	* @return PackageInfo    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����1:47:53
	*/
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try { 
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
		if(info == null) info = new PackageInfo();
		return info;
	}
	
	/** 
	* @Title: getAppId 
	* @Description: ��ȡAppΨһ��ʶ
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��23�� ����7:13:50
	*/
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if(StringUtils.isEmpty(uniqueID)){
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}
	
	/** 
	* @Title: isLogin 
	* @Description: �û��Ƿ��¼
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����11:00:18
	*/
	public boolean isLogin() {
		return this.login;
	}
	
	/** 
	* @Title: setLogin 
	* @Description: ���õ�½״̬
	* @param @param login     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��23�� ����7:17:32
	*/
	public void setLogin(boolean login){
		this.login=login;
	}
	
	/** 
	* @Title: getLoginUid 
	* @Description: ��ȡ��¼�û�id
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:32:10
	*/
	public int getLoginUid() {
		return this.loginUid;
	}
	
	/** 
	* @Title: Logout 
	* @Description:  �û��˳�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����11:00:10
	*/
	public void Logout() {
		ApiClient.cleanCookie();
		this.cleanCookie();
		clearLoginPwd();    //�����½����
		
		clearLoginSP(getSharedPreferences(LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE)); 			 //�����½�����ļ�
		clearSettingSP(getSharedPreferences(SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE));          //������������ļ�
		clearSharedPreferences(getSharedPreferences(TASKLISTACTIVITY_CONFIG_FILE,MODE_PRIVATE)); //����ҵ����������ļ�
	}
	
	/** 
	* @Title: clearLoginSP 
	* @Description: �����½ҳ����ѡ����Ϣ
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����4:49:49
	*/
	private void clearLoginSP(SharedPreferences sp){
		Editor editor=sp.edit();
		editor.remove(AppContext.PASSWORD_KEY); //�������
		//editor.remove(AppContext.FITEMNAME_KEY); //���Ա������
		editor.remove(AppContext.ACCURATE_EXPIRES_TIME); //�������ʱ��
		editor.remove(AppContext.FNOTICE_ALARM_KEY); //���֪ͨ����Ϣ
		editor.commit();
	}
	
	/** 
	* @Title: clearSettingSP 
	* @Description: �������ҳ����ѡ����Ϣ
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��3�� ����4:49:29
	*/
	private void clearSettingSP(SharedPreferences sp){
		Editor editor=sp.edit();
		editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY); //����򳵱�����ҳ�¿��ذ�ť
		editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY); //����Ѿ����²����ĵ�ַ
		editor.remove(AppContext.IS_EXPENSE_TIME_KEY); //����Ѿ����²�����ʱ��
		editor.commit();
	}
	
	/** 
	* @Title: getLoginInfo 
	* @Description: ��ȡ��¼��Ϣ
	* @param @return     
	* @return UserInfo    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����12:59:35
	*/
	public UserInfo getLoginInfo() {		
		UserInfo user = UserInfo.getUserInfo();	
		user.setFItemNumber(getProperty("UserInfo.FItemNumber"));
		user.setFItemName(getProperty("UserInfo.FItemName"));
		user.setFPassword(getProperty("UserInfo.FPassword"));
		user.setIsRememberMe(StringUtils.toBool(getProperty("UserInfo.IsRememberMe")));
		return user;
	}
	
	/** 
	* @Title: saveLoginInfo 
	* @Description: �����¼��Ϣ
	* @param @param user     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:36:19
	*/
	@SuppressWarnings("serial")
	public void saveLoginInfo(final UserInfo user) {
		this.loginUid = Integer.valueOf(user.getFItemNumber()); 
		this.login = true;
		setProperties(new Properties(){{
			setProperty("UserInfo.FItemNumber", user.getFItemNumber());
			setProperty("UserInfo.FItemName", user.getFItemName());
			setProperty("UserInfo.FPassword",user.getFPassword());
			setProperty("UserInfo.IsRememberMe", String.valueOf(user.isIsRememberMe()));//�Ƿ��ס�ҵ���Ϣ
		}});		
	}

	/** 
	* @Title: clearLoginInfo 
	* @Description: �����¼����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��10�� ����9:15:40
	*/
	public void clearLoginPwd() {
		this.loginUid = 0;
		this.login = false;
		removeProperty("UserInfo.FPassword");
	}
	
	/** 
	* @Title: clearSharedPreferences 
	* @Description: ɾ����ѡ�������ļ�
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����2:53:49
	*/
	public void clearSharedPreferences(SharedPreferences sp){
		if(login==true){
			this.setLogin(false);
		}
		sp.edit().clear().commit();
	}

	/** 
	* @Title: isAudioNormal 
	* @Description: ��⵱ǰϵͳ�����Ƿ�Ϊ����ģʽ
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����10:55:48
	*/
	public boolean isAudioNormal() {
		AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE); 
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}
	
	/** 
	* @Title: isAppSound 
	* @Description: Ӧ�ó����Ƿ񷢳���ʾ��
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����10:56:21
	*/
	public boolean isAppSound() {
		return isAudioNormal() && isVoice();
	}
	
	/** 
	* @Title: isVoice 
	* @Description: �Ƿ񷢳���ʾ��
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:30:17
	*/
	public boolean isVoice(){
		String perf_voice = getProperty(AppConfig.CONF_VOICE);
		//Ĭ���ǿ�����ʾ����
		if(StringUtils.isEmpty(perf_voice))
			return true;
		else
			return StringUtils.toBool(perf_voice);
	}
	
	/** 
	* @Title: setConfigVoice 
	* @Description: �����Ƿ񷢳���ʾ��
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:31:47
	*/
	public void setConfigVoice(boolean b){
		setProperty(AppConfig.CONF_VOICE, String.valueOf(b));
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
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){ // ��ǰ���������ӵ�
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// ��ǰ�����ӵ��������
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	* @Title: getNetworkType 
	* @Description: ��ȡ��ǰ��������
	* @param @return 0��û������   1��WIFI����   2��WAP����    3��NET����    
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����10:57:21
	*/
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtils.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	/** 
	* @Title: isMethodsCompat 
	* @Description: �жϵ�ǰ�汾�Ƿ����Ŀ��汾�ķ���
	* @param @param VersionCode
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����10:59:21
	*/
	public boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}
	
	/** 
	* @Title: isLoadImage 
	* @Description: �Ƿ������ʾ����ͼƬ
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:27:59
	*/
	public boolean isLoadImage(){
		String perf_loadimage = getProperty(AppConfig.CONF_LOAD_IMAGE);
		//Ĭ���Ǽ��ص�
		if(StringUtils.isEmpty(perf_loadimage))
			return true;
		else
			return StringUtils.toBool(perf_loadimage);
	}
	
	/** 
	* @Title: setConfigLoadimage 
	* @Description: �����Ƿ��������ͼƬ
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:28:12
	*/
	public void setConfigLoadimage(boolean b){
		setProperty(AppConfig.CONF_LOAD_IMAGE, String.valueOf(b));
	}
	
	/** 
	* @Title: isCheckUp 
	* @Description: �Ƿ�����������
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:33:07
	*/
	public boolean isCheckUp(){
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
		//Ĭ���ǿ���
		if(StringUtils.isEmpty(perf_checkup))
			return true;
		else
			return StringUtils.toBool(perf_checkup);
	}
	
	/** 
	* @Title: setConfigCheckUp 
	* @Description: ��������������
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:33:26
	*/
	public void setConfigCheckUp(boolean b){
		setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}
	
	/** 
	* @Title: isScroll 
	* @Description: �Ƿ����һ���
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:33:51
	*/
	public boolean isScroll(){
		String perf_scroll = getProperty(AppConfig.CONF_SCROLL);
		//Ĭ���ǹر����һ���
		if(StringUtils.isEmpty(perf_scroll))
			return false;
		else
			return StringUtils.toBool(perf_scroll);
	}
	
	/** 
	* @Title: setConfigScroll 
	* @Description: �����Ƿ����һ���
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:34:21
	*/
	public void setConfigScroll(boolean b){
		setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
	}
	
	/** 
	* @Title: isHttpsLogin 
	* @Description: �Ƿ�Https��¼
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:39:29
	*/
	public boolean isHttpsLogin(){
		String perf_httpslogin = getProperty(AppConfig.CONF_HTTPS_LOGIN);
		//Ĭ����http
		if(StringUtils.isEmpty(perf_httpslogin))
			return false;
		else
			return StringUtils.toBool(perf_httpslogin);
	}
	
	/** 
	* @Title: setConfigHttpsLogin 
	* @Description: �������Ƿ�Https��¼
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:39:38
	*/
	public void setConfigHttpsLogin(boolean b){
		setProperty(AppConfig.CONF_HTTPS_LOGIN, String.valueOf(b));
	}

	/** 
	* @Title: cleanCookie 
	* @Description: �������Ļ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:40:16
	*/
	public void cleanCookie(){
		removeProperty(AppConfig.CONF_COOKIE);
	}
	
	/** 
	* @Title: isReadDataCache 
	* @Description: �жϻ��������Ƿ�ɶ�
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:40:26
	*/
	@SuppressWarnings("unused")
	private boolean isReadDataCache(String cachefile){
		return readObject(cachefile) != null;
	}
	
	/** 
	* @Title: isExistDataCache 
	* @Description: �жϻ��������Ƿ�ɶ�
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:40:35
	*/
	private boolean isExistDataCache(String cachefile){
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	/** 
	* @Title: isCacheDataFailure 
	* @Description: �жϻ����Ƿ�ʧЧ
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:40:55
	*/
	public boolean isCacheDataFailure(String cachefile){
		boolean failure = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if(!data.exists())
			failure = true;
		return failure;
	}
	
	/**
	 * ���app����
	 */
	@SuppressWarnings("deprecation")
	public void clearAppCache(){
		//���webview����
		File file = CacheManager.getCacheFileBaseDir();  
		if (file != null && file.exists() && file.isDirectory()) {  
		    for (File item : file.listFiles()) {  
		    	item.delete();  
		    }  
		    file.delete();  
		}  		  
		deleteDatabase("webview.db");  
		deleteDatabase("webview.db-shm");  
		deleteDatabase("webview.db-wal");  
		deleteDatabase("webviewCache.db");  
		deleteDatabase("webviewCache.db-shm");  
		deleteDatabase("webviewCache.db-wal");  
		//������ݻ���
		clearCacheFolder(getFilesDir(),System.currentTimeMillis());
		clearCacheFolder(getCacheDir(),System.currentTimeMillis());
		//2.2�汾���н�Ӧ�û���ת�Ƶ�sd���Ĺ���
		if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),System.currentTimeMillis());
		}
		//����༭���������ʱ����
		Properties props = getProperties();
		for(Object key : props.keySet()) {
			String _key = key.toString();
			if(_key.startsWith("temp"))
				removeProperty(_key);
		}
	}	
	
	/** 
	* @Title: clearCacheFolder 
	* @Description:  �������Ŀ¼
	* @param @param dir Ŀ¼
	* @param @param curTime ��ǰϵͳʱ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:42:32
	*/
	private int clearCacheFolder(File dir, long curTime) {          
	    int deletedFiles = 0;         
	    if (dir!= null && dir.isDirectory()) {             
	        try {                
	            for (File child:dir.listFiles()) {    
	                if (child.isDirectory()) {              
	                    deletedFiles += clearCacheFolder(child, curTime);          
	                }  
	                if (child.lastModified() < curTime) {     
	                    if (child.delete()) {                   
	                        deletedFiles++;           
	                    }    
	                }    
	            }             
	        } catch(Exception e) {       
	            e.printStackTrace();    
	        }     
	    }       
	    return deletedFiles;     
	}
	
	/** 
	* @Title: setMemCache 
	* @Description: �����󱣴浽�ڴ滺����
	* @param @param key
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:47:45
	*/
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}
	
	/** 
	* @Title: getMemCache 
	* @Description: ���ڴ滺���л�ȡ����
	* @param @param key
	* @param @return     
	* @return Object    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:47:57
	*/
	public Object getMemCache(String key){
		return memCacheRegion.get(key);
	}
	
	/** 
	* @Title: setDiskCache 
	* @Description: ������̻���
	* @param @param key
	* @param @param value
	* @param @throws IOException     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:48:05
	*/
	public void setDiskCache(String key, String value) throws IOException {
		FileOutputStream fos = null;
		try{
			fos = openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		}finally{
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: getDiskCache 
	* @Description: ��ȡ���̻�������
	* @param @param key
	* @param @return
	* @param @throws IOException     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:48:14
	*/
	public String getDiskCache(String key) throws IOException {
		FileInputStream fis = null;
		try{
			fis = openFileInput("cache_"+key+".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		}finally{
			try {
				fis.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: saveObject 
	* @Description: �������
	* @param @param ser
	* @param @param file
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:48:24
	*/
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: readObject 
	* @Description: ��ȡ����
	* @param @param file
	* @param @return     
	* @return     
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����2:48:34
	*/
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//�����л�ʧ�� - ɾ�������ļ�
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/** 
	* @Title: isHaveUploadCount 
	* @Description: �ж��Ƿ����δ�ϴ���¼�ĳ˳���Ϣ
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��19�� ����3:28:24
	*/
	public boolean isHaveUploadCount(){
		boolean flag=false;
		GPSDbAdapter mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		if(mDbHelper.getGpsdbByUploadFlagCount("0") > 0){
			mDbHelper.closeSqlLite();
			flag=true;
		}
		mDbHelper.closeSqlLite();
		return flag;
	}
}
