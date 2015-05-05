package com.dahuatech.app.ui.barcode;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahuatech.app.AppManager;
import com.dahuatech.app.R;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.FinishListener;
import com.zxing.decoding.InactivityTimer;
import com.zxing.executor.ResultHandler;
import com.zxing.executor.ResultHandlerFactory;
import com.zxing.view.ViewfinderView;

/**
 * @ClassName CaptureActivity
 * @Description ɨ���ά��
 * @author 21291
 * @date 2014��7��10�� ����2:30:30
 */
public class CaptureActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {
	
	private ViewfinderView viewfinderView;  	// ��ά��ȡ����ͼ
	private TextView statusView;   //��ά��״̬��ͼ
	private ImageView btnBack,btnFlash;
	private ProgressDialog mProgress;
	
	//��������
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private boolean vibrate;
	private static final float BEEP_VOLUME = 0.10f;
	private static final long VIBRATE_DURATION = 200L;

	/**
	 * ������������ʡ�磬����ֻ�û�����ӵ�Դ�ߣ���ô��������������һֱ���ڲ���ʹ��״̬��÷���Ὣ��ǰactivity�رա�
	 * ������ȫ�̼��ɨ���Ծ״̬����CaptureActivity����������ͬ.ÿһ��ɨ����󶼻����øü�أ������µ���ʱ��
	 */
	private InactivityTimer inactivityTimer;
	private CameraManager cameraManager;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;// �����ʽ
	private String characterSet;                // �ַ�����
	private CaptureActivityHandler mHandler;	// �����߳�	
	
	/**
	 * ����Ƶ��������Զ���⻷������ǿ���������Ƿ��������
	 */
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return mHandler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}
	
	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
    
	
	/** 
	* @Title: playBeepSoundAndVibrate 
	* @Description: �����������������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��11�� ����11:27:09
	*/
	private void playBeepSoundAndVibrate() {
	    if (playBeep && mediaPlayer != null) {
	      mediaPlayer.start();
	    }
	    if (vibrate) {
	      Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	      vibrator.vibrate(VIBRATE_DURATION);
	    }
	}
	
	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
    /** 
    * @Title: initBeepSound 
    * @Description: ��ʼ�����������
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��7��11�� ����11:30:05
    */
    private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initSetting();
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.capture);
		initView();
	}
	
	/** 
	* @Title: initSetting 
	* @Description: ��ʼ����������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����2:49:21
	*/
	private void initSetting() {
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // ������Ļ���ڵ���״̬
		// window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ���ر�����
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ����
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����2:53:22
	*/
	private void initView() {
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		cameraManager = new CameraManager(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.capture_viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);	
		statusView = (TextView) findViewById(R.id.capture_status_view);
		resetStatusView();
		
		btnBack=(ImageView)findViewById(R.id.captureheader_imgbtnBack);
		btnFlash=(ImageView)findViewById(R.id.captureheader_imgbtnFrash);
		btnBack.setOnClickListener(this);
		btnFlash.setOnClickListener(this);
	}
	
	/**
	 * ��Ҫ��������г�ʼ������
	 */
	@Override
	protected void onResume() {
		super.onResume();
		inactivityTimer.onActivity();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			// ���SurfaceView�Ѿ���Ⱦ��ϣ���ص�surfaceCreated����surfaceCreated�е���initCamera()
			surfaceHolder.addCallback(this);
		}
		
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
		// �ָ�������
		inactivityTimer.onResume();
	}
	
	/** 
	* @Title: initCamera 
	* @Description: ��ʼ������ͷ��������ͷ���������ͷ�Ƿ񱻿������Ƿ�ռ��
	* @param @param surfaceHolder     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����3:30:05
	*/
	private void initCamera(SurfaceHolder surfaceHolder){
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the mHandler starts the preview, which can also throw a  RuntimeException.
			if (mHandler == null) {
				mHandler = new CaptureActivityHandler(this, decodeFormats,characterSet, cameraManager);
			}
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			displayFrameworkBugMessageAndExit();
		}
	}
	
	/** 
	* @Title: displayFrameworkBugMessageAndExit 
	* @Description: ��ʼ�������ʧ����ʾ����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����3:31:49
	*/
	private void displayFrameworkBugMessageAndExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.capture_msg_camera_framework_bug));
		builder.setPositiveButton("ȷ��", new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}
	
	/** 
	* @Title: handleDecode 
	* @Description: ��ȡɨ����
	* @param @param rawResult
	* @param @param barcode
	* @param @param scaleFactor     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����3:59:53
	*/
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor){
		inactivityTimer.onActivity();
		ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);	
		//��ά�벻Ϊnull
		boolean fromLiveScan = barcode != null;
		if (fromLiveScan) {
			playBeepSoundAndVibrate();
		}
		parseBarCode(rawResult.getText(),resultHandler);
	}

	/** 
	* @Title: parseBarCode 
	* @Description: ������ά��
	* @param @param msg
	* @param @param resultHandler     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��11�� ����1:03:57
	*/
	private void parseBarCode(String msg,ResultHandler resultHandler){
		if(StringUtils.isEmpty(msg)){
			UIHelper.ToastMessageLong(CaptureActivity.this, R.string.capture_scan_false);
			return;
		}	
		mProgress = ProgressDialog.show(CaptureActivity.this, null,"��ɨ�裬���ڴ�������", true, true);
		mProgress.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				restartPreviewAfterDelay(1l);
			}
		});
		showDialog(msg,resultHandler);
	}
	
	/** 
	* @Title: showDialog 
	* @Description: ɨ�����Ի���
	* @param @param msg
	* @param @param resultHandler     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��11�� ����1:12:01
	*/
	private void showDialog(final String msg,final ResultHandler resultHandler) {
		new AlertDialog.Builder(CaptureActivity.this)
			.setTitle("ɨ����").setMessage("ɨ����ά�����ݣ�" + msg)
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mProgress.dismiss();
					dialog.dismiss();		
					resultHandler.handleDeal(0);
				}
			})
			.setNegativeButton("����", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					mProgress.dismiss();
					dialog.dismiss();
					restartPreviewAfterDelay(0L);
				}
			}).show();
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.captureheader_imgbtnBack:
				AppManager.getAppManager().finishActivity(CaptureActivity.this);
				break;
			case R.id.captureheader_imgbtnFrash:
				setFlash();
				break;	
			default:
				break;
		}
	}
	
	/** 
	* @Title: setFlash 
	* @Description: ���������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��10�� ����5:02:41
	*/
	private void setFlash() {
		if (btnFlash.getTag() != null) {
			cameraManager.setTorch(true);
			btnFlash.setTag(null);
			btnFlash.setBackgroundResource(R.drawable.btn_flash_hover);
		} else {
			cameraManager.setTorch(false);
			btnFlash.setTag("1");
			btnFlash.setBackgroundResource(R.drawable.btn_flash);
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {}
	    if (!hasSurface) {
	        hasSurface = true;
	        initCamera(holder);
	    }	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;		
	}
	
	/**
	 * �ھ���һ���ӳٺ���������Խ�����һ��ɨ�衣 �ɹ�ɨ�����ɵ��ô˷�������׼�������´�ɨ��
	 * 
	 * @param delayMS
	 */
	public void restartPreviewAfterDelay(long delayMS) {
		if (mHandler != null) {
			mHandler.sendEmptyMessageDelayed(R.id.capture_restart_preview, delayMS);
		}
		resetStatusView();
	}

	/** 
	* @Title: resetStatusView 
	* @Description: Ĭ��״̬��ͼ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��11�� ����11:09:15
	*/
	private void resetStatusView() {
		statusView.setText(R.string.capture_status);
		statusView.setVisibility(View.VISIBLE);
		viewfinderView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * ��ͣ������,�ر�����ͷ
	 */
	@Override
	protected void onPause() {
		if (mHandler != null) {
			mHandler.quitSynchronously();
			mHandler = null;
		}
		// ��ͣ������
		inactivityTimer.onPause();
		// �ر�����ͷ
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		if (mProgress != null && mProgress.isShowing()) {
			mProgress.dismiss();
		}
		super.onPause();
	}

	/**
	 * ֹͣ������,�������ѡ�е�ɨ������
	 */
	@Override
	protected void onDestroy() {
		// ֹͣ������
		inactivityTimer.shutdown();
		if (mProgress != null) {
			mProgress.dismiss();
		}
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				AppManager.getAppManager().finishActivity(CaptureActivity.this);
				return true;
			case KeyEvent.KEYCODE_FOCUS:
			case KeyEvent.KEYCODE_CAMERA:
				// Handle these events so they don't launch the Camera app
				return true;
				// Use volume up/down to turn on light
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				cameraManager.setTorch(false);
				return true;
			case KeyEvent.KEYCODE_VOLUME_UP:
				cameraManager.setTorch(true);
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
