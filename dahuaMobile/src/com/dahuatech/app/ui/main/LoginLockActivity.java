package com.dahuatech.app.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.ValidLogin;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.UserLoginBussiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.UpdateManager;
import com.dahuatech.app.widget.LockPatternView;
import com.dahuatech.app.widget.LockPatternView.OnCompleteListener;
import com.google.gson.Gson;

/**
 * @ClassName LoginLockActivity
 * @Description �Ź�����������ʽActivity
 * @author 21291
 * @date 2014��12��5�� ����2:56:45
 */
public class LoginLockActivity extends BaseActivity {

	private LockPatternView lpView;     			//��������ͼ�ؼ�
	private String fItemNumber,fItemName; 			//Ա����,Ա������
	private boolean fFirstLogin;		    		//�ж�Ӧ�ó����Ƿ��״ε�½
	private TextView fManager,fJump;				//�������ƺ���������		
	private SharedPreferences sp;  					//�����ļ�
	private int errorCount=5;						//�������
	
	private UserLoginBussiness userLoginBussiness;	//ҵ���߼���
	private JSONObject jsonObject;					//json����
	private String serviceUrl;  					//�����ַ
	private AppContext appContext;					//ȫ��Context
	private Gson gson;								//gsonʵ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_lock);
		
		appContext = (AppContext)getApplication(); 	//��ʼ��ȫ�ֱ���	
		if(!appContext.isNetworkConnected()) //��������	
		{
			UIHelper.ToastMessage(LoginLockActivity.this, R.string.network_not_connected);
			return;
		}
		
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.USER_NAME_KEY);
			fItemName=extras.getString(AppContext.FITEMNAME_KEY);
			fFirstLogin=extras.getBoolean(AppContext.FLOGIN_IS_FIRST_KEY, true);
		}	
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_VERIFYVALIDACTIVITY;
		loadView();	
		init();
		checkSoftUpdate(); //����Ƿ��и��³���
	}
	
	/** 
	* @Title: loadView 
	* @Description: ���ؿؼ���Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��5�� ����4:45:53
	*/
	private void loadView(){
		lpView=(LockPatternView)findViewById(R.id.login_lock_Pattern);
		fManager=(TextView)findViewById(R.id.login_lock_manager_gestures);
		fJump=(TextView)findViewById(R.id.login_lock_jump);
		
		lpView.setOnCompleteListener(new OnCompleteListener() {  //�������������¼�
			
			@Override
			public void onComplete(String password) {
				if(lpView.verifyPassword(password)){  //��֤�ɹ�
					skipToMain();
				}
				else{//��֤ʧ��
					lpView.markError();
					errorCount--;
					if(errorCount > 0){
						UIHelper.ToastMessage(LoginLockActivity.this,"�������,������������"+String.valueOf(errorCount)+"��");
					}
					else{
						alertLogin();
					}
				}
			}
		});
		
		if(fFirstLogin || lpView.isPasswordEmpty()){  //�״ε�½������Ϊ��
			lpView.resetPassWord();   //�������
			fManager.setText("");
			fJump.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog("jump");
				}
			});
		}
		else{
			fJump.setText("");
			fManager.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					alertDialog("manager");
				}
			});
		}
	}
	
	/** 
	* @Title: init 
	* @Description: ��ʼ���������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��5�� ����4:49:59
	*/
	private void init(){
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LoginLockActivity.this);
		userLoginBussiness=(UserLoginBussiness)factoryBusiness.getInstance("UserLoginBussiness",serviceUrl);
		gson=GsonHelper.getInstance();
	}
	
	/** 
	* @Title: skipToMain 
	* @Description: ��ת����ҳ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��5�� ����5:04:01
	*/
	private void skipToMain(){
		new validAsyncTask().execute();
	}

	/**
	 * @ClassName validAsyncTask
	 * @Description �첽��½��֤
	 * @author 21291
	 * @date 2015��2��5�� ����4:57:30
	 */
	private class validAsyncTask extends AsyncTask<Void, Void, ResultMessage> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		
		@Override
		protected ResultMessage doInBackground(Void... params) {			
			return userLoginBussiness.verifyValid(fItemNumber);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showResult(result);
		}
				
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: �����ϴ���UI���
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:33:19
	*/
	private void showResult(ResultMessage result){
		if(result.isIsSuccess()){  //˵����֤�ɹ�
			try {
				jsonObject = new JSONObject(result.getResult());
				ValidLogin valid=gson.fromJson(jsonObject.toString(),ValidLogin.class);
				if(valid!=null){
					fItemName=valid.getFItemName();
				}
				sendLogs(); //������־��Ϣ����ͳ��
				UIHelper.showHome(LoginLockActivity.this,fItemNumber,fItemName);		
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else{
			UIHelper.ToastMessage(LoginLockActivity.this, getResources().getString(R.string.lock_verify_faile));
		}
	}
	
	/** 
	* @Title: checkSoftUpdate 
	* @Description: ���������ʾ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��2�� ����1:35:25
	*/
	private void checkSoftUpdate(){
		UpdateManager.getUpdateManager(LoginLockActivity.this,AppUrl.URL_API_HOST_ANDROID_SETTINGACTIVITY).checkAppUpdate(false);
	}
	
	/** 
	* @Title: alertDialog 
	* @Description: �������ѿ�
	* @param @param type     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��10�� ����2:11:48
	*/
	@SuppressLint("InlinedApi")
	private void alertDialog(final String type){
		String positiveBtn=getResources().getString(R.string.jump);
		String msg=getResources().getString(R.string.gestures_setting_jump); 
		
		if("manager".equals(type)){
			 positiveBtn=getResources().getString(R.string.reload);
			 msg=getResources().getString(R.string.gestures_setting_forget); 
		}
	
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginLockActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(msg);
		builder.setPositiveButton(positiveBtn,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if("manager".equals(type)){
							UIHelper.showLogin(LoginLockActivity.this,false,true);
						}
						else{
							sp.edit().putBoolean(AppContext.IS_GESTURES_KEY, false).commit();
							UIHelper.showHome(LoginLockActivity.this,fItemNumber,fItemName);
						}
						dialog.dismiss();
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
	* @Title: alertLogin 
	* @Description: ��ת����½ҳ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��10�� ����2:13:35
	*/
	@SuppressLint("InlinedApi")
	private void alertLogin(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginLockActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(getResources().getString(R.string.gestures_pwd_error));
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						UIHelper.showLogin(LoginLockActivity.this,false,true);
						dialog.dismiss();
					}
				});
		builder.show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		View noSetPwd = (View) this.findViewById(R.id.login_lock_NoSetPwd);
		TextView getstureTv = (TextView) findViewById(R.id.login_lock_Gestures);
		if (lpView.isPasswordEmpty()) { // �������Ϊ��,�������������Ľ���
			lpView.setVisibility(View.GONE);
			noSetPwd.setVisibility(View.VISIBLE);
			getstureTv.setText(getResources().getString(R.string.login_lock_set_gestures));
			noSetPwd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {		
					UIHelper.showLockSetPwd(LoginLockActivity.this,fItemNumber,fItemName,"login");
					finish();
				}
			});
		} else {
			getstureTv.setText(getResources().getString(R.string.login_lock_gestures));
			lpView.setVisibility(View.VISIBLE);
			noSetPwd.setVisibility(View.GONE);
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ��֤�ɹ�ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��5�� ����4:59:41
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_login_module));
		logInfo.setFActionName("login");
		logInfo.setFNote("note");
		UIHelper.sendLogs(LoginLockActivity.this,logInfo);
	}

	@Override
	protected void onRestart() {
		super.onRestart();		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //���ؼ�
			AppManager.getAppManager().AppExit(LoginLockActivity.this); // �˳�
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
