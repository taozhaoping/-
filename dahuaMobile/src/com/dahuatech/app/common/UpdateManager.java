package com.dahuatech.app.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dahuatech.app.AppException;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.UpdateInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.SettingBusiness;

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class UpdateManager {

	private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
	
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    
	private String serviceUrl;  //���·����ַ
	
	private Context mContext;
	//֪ͨ�Ի���
	private Dialog noticeDialog;
	//���ضԻ���
	private Dialog downloadDialog;
	//'�Ѿ�������' ���� '�޷���ȡ���°汾' �ĶԻ���
	private Dialog latestOrFailDialog;
    //������
    private ProgressBar mProgress;
    //��ʾ������ֵ
    private TextView mProgressText;
    //��ѯ����
    private ProgressDialog mProDialog;
    //����ֵ
    private int progress;
    //�����߳�
    private Thread downLoadThread;
    //��ֹ���
    private boolean interceptFlag;
	//��ʾ��
	private String updateMsg = "";
	//���صİ�װ��url
	private String apkUrl = "";
	//���ذ�����·��
    private String savePath = "";
	//apk��������·��
	private String apkFilePath = "";
	//��ʱ�����ļ�·��
	private String tmpFilePath = "";
	//�����ļ���С
	private String apkFileSize;
	//�������ļ���С
	private String tmpFileSize;
	
	@SuppressWarnings("unused")
	private String curVersionName;
	private int curVersionCode;
	private UpdateInfo mUpdate;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
    		switch (msg.what) {
				case DOWN_UPDATE:
					mProgress.setProgress(progress);
					mProgressText.setText(tmpFileSize + "/" + apkFileSize);
					break;
				case DOWN_OVER:
					downloadDialog.dismiss();
					installApk();
					break;
				case DOWN_NOSDCARD:
					downloadDialog.dismiss();
					Toast.makeText(mContext, R.string.soft_update_sdcard, 3000).show();
					break;
			}
    	};
	 };
		
	/** 
	* @Name: CommonMenu 
	* @Description: �ڲ��൥��ģʽ 
	*/
	private UpdateManager(){}
	private static class SingletonHolder {  
        private static UpdateManager instance = new UpdateManager();  
    }  
	public static UpdateManager getUpdateManager(Context context,String url) {
		SingletonHolder.instance.interceptFlag=false;
		SingletonHolder.instance.mContext=context;
		SingletonHolder.instance.serviceUrl=url;
		return SingletonHolder.instance;
	}
	
	/** 
	* @Title: checkAppUpdate 
	* @Description: ���App����
	* @param @param context ����������
	* @param @param isShowMsg  �Ƿ���ʾ��ʾ��Ϣ   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��23�� ����10:53:32
	*/
	public void checkAppUpdate(final boolean isShowMsg){
		getCurrentVersion();
		if(isShowMsg){
			if(mProDialog == null)
				mProDialog = ProgressDialog.show(mContext, null, "���ڼ�⣬���Ժ�...", true, true);
			else if(mProDialog.isShowing() || (latestOrFailDialog!=null && latestOrFailDialog.isShowing()))
				return;
		}
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				if(mProDialog != null && !mProDialog.isShowing()){ 	//�������Ի�����ʾ - �����Ҳ����ʾ
					return;
				}
				//�رղ��ͷ��ͷŽ������Ի���
				if(isShowMsg && mProDialog != null){
					mProDialog.dismiss();
					mProDialog = null;
				}
				//��ʾ�����
				if(msg.what == 1){
					mUpdate = (UpdateInfo)msg.obj;
					if(mUpdate != null){
						if(curVersionCode < mUpdate.getVersionCode()){
							apkUrl = mUpdate.getDownloadUrl();
							updateMsg = mUpdate.getUpdateLog();
							showNoticeDialog();
						}else if(isShowMsg){
							showLatestOrFailDialog(DIALOG_TYPE_LATEST);
						}
					}
				}else if(isShowMsg){
					showLatestOrFailDialog(DIALOG_TYPE_FAIL);
				}
			}
		};
		new Thread(){
			public void run() {
				Message msg = new Message();
				try {	
					FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(mContext);
					SettingBusiness settingBusiness= (SettingBusiness)factoryBusiness.getInstance("SettingBusiness",serviceUrl);
					UpdateInfo update = settingBusiness.checkVersion();
					msg.what = 1;
					msg.obj = update;
				} catch (AppException e) {
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}			
		}.start();		
	}	
	
	/**
	 * ��ʾ'�Ѿ�������'����'�޷���ȡ�汾��Ϣ'�Ի���
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			//�رղ��ͷ�֮ǰ�ĶԻ���
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage(R.string.soft_update_no);
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage(R.string.soft_update_cannotget);
		}
		builder.setPositiveButton(R.string.soft_update_sure, null);
		latestOrFailDialog = builder.create();
		latestOrFailDialog.show();
	}

	/**
	 * ��ȡ��ǰ�ͻ��˰汾��Ϣ
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	curVersionName = info.versionName;
        	curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * ��ʾ�汾����֪ͨ�Ի���
	 */
	private void showNoticeDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(updateMsg);
		builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		builder.setNegativeButton(R.string.soft_update_later, new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}
	
	/**
	 * ��ʾ���ضԻ���
	 */
	@SuppressLint("InflateParams")
	private void showDownloadDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		
		builder.setView(v);
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();
		
		downloadApk();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				String apkName = "dahuaMobile_"+mUpdate.getVersionName()+".apk";
				String tmpApk = "dahuaMobile_"+mUpdate.getVersionName()+".tmp";
				
				//�ж��Ƿ������SD��
				String storageState = Environment.getExternalStorageState();		
				if(storageState.equals(Environment.MEDIA_MOUNTED)){
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dahuaMobile/Update/";
					File file = new File(savePath);
					if(!file.exists()){
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					tmpFilePath = savePath + tmpApk;
				}
				
				//û�й���SD�����޷������ļ�
				if(StringUtils.isEmpty(apkFilePath)){
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}
				
				File ApkFile = new File(apkFilePath);
				
				//�Ƿ������ظ����ļ�
				if(ApkFile.exists()){
					downloadDialog.dismiss();
					installApk();
					return;
				}
				
				//�����ʱ�����ļ�
				File tmpFile = new File(tmpFilePath);
				FileOutputStream fos = new FileOutputStream(tmpFile);
				
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				//��ʾ�ļ���С��ʽ��2��С������ʾ
		    	DecimalFormat df = new DecimalFormat("0.00");
		    	//������������ʾ�����ļ���С
		    	apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    		//������������ʾ�ĵ�ǰ�����ļ���С
		    		tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
		    		//��ǰ����ֵ
		    	    progress =(int)(((float)count / length) * 100);
		    	    //���½���
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//������� - ����ʱ�����ļ�ת��APK�ļ�
						if(tmpFile.renameTo(ApkFile)){
							//֪ͨ��װ
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//���ȡ����ֹͣ����
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}	
		}
	};
		
	/** 
	* @Title: downloadApk 
	* @Description: ����apk
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����3:05:22
	*/
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/** 
	* @Title: installApk 
	* @Description: ��װapk
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����3:05:31
	*/
	private void installApk(){
		File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
	}
}
