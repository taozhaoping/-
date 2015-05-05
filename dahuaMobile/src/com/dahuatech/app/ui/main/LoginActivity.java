package com.dahuatech.app.ui.main;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.UserLoginBussiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.ui.service.AlarmBroadCast;

/**
 * @ClassName LoginActivity
 * @Description ��½Activity
 * @author 21291
 * @date 2014��4��21�� ����9:12:58
 */
public class LoginActivity extends BaseActivity {
	
	private Button btnLogin; 							//�ύ��ť
	private EditText fItemNumberEdit,fPasswordEdit; 	//Ա����,����
	private ProgressDialog dialog; 						//��ʾ��
	private String fItemNumber,fPassword,fItemName;	
	private CheckBox chb_rememberMe;
	private boolean isRememberMe;
	private boolean fLoginShowNotice=false; 			//�Ƿ���֪ͨ
	private boolean isNetWork=true;  					//�Ƿ�������
	private boolean fFirstLogin=true;					//Ӧ�ó����Ƿ��״ε�½
	
	private String serviceUrl;  						//�����ַ
	private AppContext appContext;						//ȫ��Context
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		appContext = (AppContext)getApplication(); 	//��ʼ��ȫ�ֱ���		
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_LOGINACTIVITY;	 //��ȡ�����ַ

		//�ؼ���ʼ��
		fItemNumberEdit = (EditText) this.findViewById(R.id.login_editUserName);
		fPasswordEdit = (EditText) this.findViewById(R.id.login_editPassWord);
        chb_rememberMe=(CheckBox)findViewById(R.id.login_checkBox);
        chb_rememberMe.setChecked(true);
        
		btnLogin = (Button) this.findViewById(R.id.login_btnLogin);	
        btnLogin.setOnClickListener(btnLoginClick);
        
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.dialog_logining));
        dialog.setCancelable(false); 
		
        Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fLoginShowNotice=extras.getBoolean(AppContext.FNOTICE_LOGIN_IS_SHOW_KEY, false);	 
			fFirstLogin=extras.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);	  
		}
		
		if(!appContext.isNetworkConnected()) //��������	
		{
			isNetWork=false;
			UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
			return;
		}
		
		if(isNetWork){  //������������
			checkSoftUpdate(); //����Ƿ��и��³���
			if(fLoginShowNotice){ //����Ǵ�֪ͨ������	
				UIHelper.ToastMessage(LoginActivity.this, R.string.msg_login_error);
			}
			appContext.clearSharedPreferences(sp);
			//�Ƿ���ʾ��¼��Ϣ
	        UserInfo user=appContext.getLoginInfo();
	        if(user==null || !user.isIsRememberMe()) return;
	        if(!StringUtils.isEmpty(user.getFItemNumber())){
	        	fItemNumberEdit.setText(user.getFItemNumber());
	        	fItemNumberEdit.selectAll();
	        	chb_rememberMe.setChecked(user.isIsRememberMe());
	        }
		}
	}
	
	//���԰�ť
	public void OnTestClick(View v)  
    {  
		if(isNetWork){
			fItemNumber = AppContext.TEMPTEST_FITEMNUMBER;
			fItemName=AppContext.TEMPTEST_FITEMNAME;
			fPassword = "123456";
			isRememberMe = true;
			
			UserInfo user=UserInfo.getUserInfo();
			user.setFItemNumber(fItemNumber);
			user.setFItemName(fItemName);
			user.setFPassword(fPassword);
			user.setIsRememberMe(isRememberMe);
			
			appContext.saveLoginInfo(user);//�����¼��Ϣ
			//���ݳ־û� �����½������Ϣ ˽�����ԣ�ֻ�б���Ŀ���ܴ�ȡ��һ������ָ����XML�ļ�
			if(updateUserInfo())
			{
				new AlarmBroadCast().setAlarm(LoginActivity.this); //��½�ɹ��󣬷���֪ͨ����Ϣ  
				//sendLogs();	//������־��Ϣ����ͳ��
				//��ӭ�����ӳ�2�����������
		        new Handler().postDelayed(new Runnable() {
		            @Override
		            public void run() {
		            	if(fFirstLogin){
		            		//��������ҳ��
		            		UIHelper.showLock(LoginActivity.this,fItemNumber,fItemName,fFirstLogin);
		            	}
		            	else{
		            		// ��ת����ҳ��
			            	UIHelper.showHome(LoginActivity.this,fItemNumber,fItemName);
		            	}
		            }
		        }, 2000);
			}
		}
		else{
			UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
		}
    }  

	//��½�¼�������
	OnClickListener btnLoginClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
			//���������
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
			
			if(isNetWork){  //�����������½��е�½��֤
				fItemNumber = fItemNumberEdit.getText().toString();
				fPassword = fPasswordEdit.getText().toString();
				isRememberMe = chb_rememberMe.isChecked();
				
				//��֤�˺���û������
				if(StringUtils.isEmpty(fItemNumber)){
					UIHelper.ToastMessage(v.getContext(), R.string.useinfo_login_tipusername);
					return ;
				}
				//��֤������û������
				if(StringUtils.isEmpty(fPassword)){
					UIHelper.ToastMessage(v.getContext(), R.string.useinfo_login_tippassword);
					return ;
				}
				loginAsyncTask loginTask=new loginAsyncTask();
				loginTask.execute(serviceUrl,fItemNumber,fPassword);
			}
			else{
				UIHelper.ToastMessage(LoginActivity.this, R.string.network_not_connected);
			}	
		}
    };
	
	/**
	 * @ClassName loginAsyncTask
	 * @Description �첽��½����
	 * @author 21291
	 * @date 2014��3��31�� ����1:57:35
	 */
	private class loginAsyncTask extends AsyncTask<String, Void, UserInfo> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(UserInfo user) {
			super.onPostExecute(user);
			// ���ٵȴ���
			dialog.dismiss();
			if(user!=null){ //˵����½�ɹ�
				//��½��Ϣ��ʾ
				UIHelper.ToastMessage(getApplicationContext(), R.string.useinfo_login_success);
				//�����½��Ϣ
				appContext = (AppContext)getApplication();
				appContext.saveLoginInfo(user);
	
				//���ݳ־û� �����½������Ϣ ˽�����ԣ�ֻ�б���Ŀ���ܴ�ȡ��һ������ָ����XML�ļ�
				if(updateUserInfo())
				{
					new AlarmBroadCast().setAlarm(LoginActivity.this); //��½�ɹ��󣬷���֪ͨ����Ϣ  
					sendLogs();	//������־��Ϣ����ͳ��
					
					//��ӭ�����ӳ�2�����������
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	if(fFirstLogin){
			            		//��������ҳ��
			            		UIHelper.showLock(LoginActivity.this,fItemNumber,fItemName,fFirstLogin);
			            	}
			            	else{
			            		// ��ת����ҳ��
				            	UIHelper.showHome(LoginActivity.this,fItemNumber,fItemName);
			            	}
			            }
			        }, 2000);
				}
			}
			else {
				UIHelper.ToastMessage(getApplicationContext(), R.string.useinfo_login_faile);
			}
		}
		
		@Override
		protected UserInfo doInBackground(String... params) {			
			return loginVerify(params[0], params[1],params[2]);
		}
	}
	
	/** 
	* @Title: loginVerify 
	* @Description: ��ȡ����ʵ��
	* @param @param serviceUrl
	* @param @param fItemNumber
	* @param @param fPassword
	* @param @return     
	* @return UserInfo    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����12:06:12
	*/
	private UserInfo loginVerify(String serviceUrl,String fItemNumber,String fPassword){		
		UserInfo returnUser=null;
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LoginActivity.this);
		UserLoginBussiness userLoginBussiness=(UserLoginBussiness)factoryBusiness.getInstance("UserLoginBussiness",serviceUrl);
		UserInfo user=UserInfo.getUserInfo(fItemNumber, fPassword, isRememberMe);
		fItemName=userLoginBussiness.loginVerify(user);
		if(!StringUtils.isEmpty(fItemName)){
			returnUser=UserInfo.getUserInfo();
			returnUser.setFItemNumber(fItemNumber);
			returnUser.setFPassword(fPassword);
			returnUser.setFItemName(fItemName);
			returnUser.setIsRememberMe(isRememberMe);
		}
		return returnUser;
	}
	
	/** 
	* @Title: updateUserInfo 
	* @Description: �����û���Ϣ
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��12��12�� ����3:08:59
	*/
	private boolean updateUserInfo() {
		Editor editor = sp.edit();
		editor.putString(AppContext.USER_NAME_KEY, fItemNumber);
		editor.putString(AppContext.FITEMNAME_KEY, fItemName);
		editor.putBoolean(AppContext.FLOGIN_IS_FIRST_KEY, false);  //���õ�½��һ��
		return editor.commit();
	}
	
	/** 
	* @Title: checkSoftUpdate 
	* @Description: 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��22�� ����12:36:58
	*/
	private void checkSoftUpdate(){
		UpdateManager.getUpdateManager(LoginActivity.this,AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY).checkAppUpdate(false);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �����¼ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����11:05:52
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_login_module));
		logInfo.setFActionName("login");
		logInfo.setFNote("note");
		UIHelper.sendLogs(LoginActivity.this,logInfo);
	}
	
	@Override
	protected void onRestart() {
		if(!appContext.isNetworkConnected()) //�ж���������
		{
			isNetWork=false;
		}
		else{
			isNetWork=true;
		}
		super.onRestart();		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //���ؼ�
			AppManager.getAppManager().AppExit(LoginActivity.this); // �˳�
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
