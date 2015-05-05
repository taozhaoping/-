package com.dahuatech.app.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dahuatech.app.R;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.widget.utli.BitmapUtil;
import com.dahuatech.app.widget.utli.LockPoint;
import com.dahuatech.app.widget.utli.MathUtil;
import com.dahuatech.app.widget.utli.RoundUtil;

/**
 * @ClassName LockPatternView
 * @Description �Ź���������ʽ
 * @author 21291
 * @date 2014��12��5�� ����10:23:05
 */
public class LockPatternView extends View {
		
	private float w = 0;   										//��ͼ���
	private float h = 0;										//��ͼ�߶�
	
	private boolean isCache = false;  							//�Ƿ񻺴�
	private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);		//ʵ����һ������
	private LockPoint[][] mPoints = new LockPoint[3][3];		//��λ�þ���  3*3
	private float r = 0;										//ԲȦ�İ뾶
	
	private List<LockPoint> sPoints=new ArrayList<LockPoint>();	//ѡ�еĵ�λ��
	private boolean checking = false;							//���״̬
	
	private Bitmap lock_round_original;							//����Բ���ʼ��ʱͼƬ
	private Bitmap lock_round_click;							//����Բ����ʱͼƬ
	private Bitmap lock_round_click_error;						//����Բ��������ʱͼƬ
	
	private Bitmap lock_line;									//����״̬��ʱ�����ߵ�ͼƬ
	private Bitmap lock_line_semicircle;						//����״̬��ʱ�����߰�Բ�ε�ͼƬ
	
	private Bitmap lock_line_error;								//����״̬��ʱ�����ߵ�ͼƬ
	private Bitmap lock_line_semicircle_error;					//����״̬��ʱ�����߰�Բ�ε�ͼƬ
	
	private long CLEAR_TIME = 0;								//����ۼ���ʱ��
	private int passwordMinLength = 5;							//������С����
	private Matrix mMatrix = new Matrix();						//����ʵ������
	private int lineAlpha = 0;									//���ߵ�͸����
	
	private boolean isTouch = true; 							//�Ƿ�ɲ���
	private boolean movingNoPoint = false;						//�����ƶ��ĵ�λ��
	private float moveingX, moveingY;							//�ƶ�������
	
	private Timer timer = new Timer();							//ʵ������ʱ����
	private TimerTask task = null;								//��ʱ������
	private OnCompleteListener mCompleteListener;				//�켣����¼�
	
	public LockPatternView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LockPatternView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LockPatternView(Context context) {
		super(context);
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!isCache) {
			initCache();
		}
		drawToCanvas(canvas);
	}

	private void drawToCanvas(Canvas canvas) {
		// �����е�
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (p.state == LockPoint.STATE_CHECK) {
					canvas.drawBitmap(lock_round_click, p.x - r, p.y - r,mPaint);
				} else if (p.state == LockPoint.STATE_CHECK_ERROR) {
					canvas.drawBitmap(lock_round_click_error, p.x - r,p.y - r, mPaint);
				} else {
					canvas.drawBitmap(lock_round_original, p.x - r, p.y - r,mPaint);
				}
			}
		}

		// ������
		if (sPoints.size() > 0) {
			int tmpAlpha = mPaint.getAlpha();
			lineAlpha=30;
			mPaint.setAlpha(lineAlpha);
			LockPoint tp = sPoints.get(0);
			for (int i = 1; i < sPoints.size(); i++) {
				LockPoint p = sPoints.get(i);
				drawLine(canvas, tp, p);
				tp = p;
			}
			if (this.movingNoPoint) {
				drawLine(canvas, tp, new LockPoint((int) moveingX, (int) moveingY));
			}
			mPaint.setAlpha(tmpAlpha);
			lineAlpha = mPaint.getAlpha();
		}
	}
	
	/** 
	* @Title: initCache 
	* @Description: ��ʼ��������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����10:48:33
	*/
	private void initCache() {

		w = this.getWidth();
		h = this.getHeight();
		float x = 0;
		float y = 0;

		// ����С��Ϊ׼
		// ����
		if (w > h) {
			x = (w - h) / 2;
			w = h;
		}
		// ����
		else {
			y = (h - w) / 2;
			h = w;
		}

		lock_round_original = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_round_original);
		lock_round_click = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_round_click);
		lock_round_click_error = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_round_click_error);

		lock_line = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_line);
		lock_line_semicircle = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_line_semicircle);

		lock_line_error = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_line_error);
		lock_line_semicircle_error = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_line_semicircle_error);
	
		// ����ԲȦͼƬ�Ĵ�С
		float canvasMinW = w;
		if (w > h) {
			canvasMinW = h;
		}
		float roundMinW = canvasMinW / 8.0f * 2;
		float roundW = roundMinW / 2.f;
		float deviation = canvasMinW % (8 * 2) / 2;
		x += deviation;
		x += deviation;

		if (lock_round_original.getWidth() > roundMinW) {
			float sf = roundMinW * 1.0f / lock_round_original.getWidth(); // ȡ�����ű����������е�ͼƬ��������
			lock_round_original = BitmapUtil.zoom(lock_round_original, sf);
			lock_round_click = BitmapUtil.zoom(lock_round_click, sf);
			lock_round_click_error = BitmapUtil.zoom(lock_round_click_error,sf);

			lock_line = BitmapUtil.zoom(lock_line, sf);
			lock_line_semicircle = BitmapUtil.zoom(lock_line_semicircle, sf);

			lock_line_error = BitmapUtil.zoom(lock_line_error, sf);
			lock_line_semicircle_error = BitmapUtil.zoom(lock_line_semicircle_error, sf);
			roundW = lock_round_original.getWidth() / 2;
		}

		mPoints[0][0] = new LockPoint(x + 0 + roundW, y + 0 + roundW);
		mPoints[0][1] = new LockPoint(x + w / 2, y + 0 + roundW);
		mPoints[0][2] = new LockPoint(x + w - roundW, y + 0 + roundW);
		mPoints[1][0] = new LockPoint(x + 0 + roundW, y + h / 2);
		mPoints[1][1] = new LockPoint(x + w / 2, y + h / 2);
		mPoints[1][2] = new LockPoint(x + w - roundW, y + h / 2);
		mPoints[2][0] = new LockPoint(x + 0 + roundW, y + h - roundW);
		mPoints[2][1] = new LockPoint(x + w / 2, y + h - roundW);
		mPoints[2][2] = new LockPoint(x + w - roundW, y + h - roundW);
		int k = 0;
		for (LockPoint[] ps : mPoints) {
			for (LockPoint p : ps) {
				p.index = k;
				k++;
			}
		}
		r = lock_round_original.getHeight() / 2;// roundW;
		isCache = true;
	}

	/** 
	* @Title: drawLine 
	* @Description: �����������
	* @param @param canvas
	* @param @param a
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:30:17
	*/
	private void drawLine(Canvas canvas, LockPoint a, LockPoint b) {
		float ah = (float) MathUtil.distance(a.x, a.y, b.x, b.y);
		float degrees = getDegrees(a, b);
		canvas.rotate(degrees, a.x, a.y);

		if (a.state == LockPoint.STATE_CHECK_ERROR) {
			mMatrix.setScale((ah - lock_line_semicircle_error.getWidth())/ lock_line_error.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - lock_line_error.getHeight()/ 2.0f);
			canvas.drawBitmap(lock_line_error, mMatrix, mPaint);
		} else {
			mMatrix.setScale((ah - lock_line_semicircle.getWidth())/ lock_line.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - lock_line.getHeight() / 2.0f);
			canvas.drawBitmap(lock_line, mMatrix, mPaint);
		}

		canvas.rotate(-degrees, a.x, a.y);
	}

	/** 
	* @Title: getDegrees 
	* @Description: ��ȡ���������ĽǶ�
	* @param @param a
	* @param @param b     
	* @return float    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����1:22:49
	*/
	public float getDegrees(LockPoint a, LockPoint b) {
		float ax = a.x;// a.index % 3;
		float ay = a.y;// a.index / 3;
		float bx = b.x;// b.index % 3;
		float by = b.y;// b.index / 3;
		float degrees = 0;
		if (bx == ax) // y����� 90�Ȼ�270
		{
			if (by > ay) // ��y����±� 90
			{
				degrees = 90;
			} else if (by < ay) // ��y����ϱ� 270
			{
				degrees = 270;
			}
		} else if (by == ay) // y����� 0�Ȼ�180
		{
			if (bx > ax) // ��y����±� 90
			{
				degrees = 0;
			} else if (bx < ax) // ��y����ϱ� 270
			{
				degrees = 180;
			}
		} else {
			if (bx > ax) // ��y����ұ� 270~90
			{
				if (by > ay) // ��y����±� 0 - 90
				{
					degrees = 0;
					degrees = degrees+ switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				} else if (by < ay) // ��y����ϱ� 270~0
				{
					degrees = 360;
					degrees = degrees- switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				}

			} 
			else if (bx < ax) // ��y������ 90~270
			{
				if (by > ay) // ��y����±� 180 ~ 270
				{
					degrees = 90;
					degrees = degrees+ switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				} else if (by < ay) // ��y����ϱ� 90 ~ 180
				{
					degrees = 270;
					degrees = degrees- switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				}

			}

		}
		return degrees;
	}

	/** 
	* @Title: switchDegrees 
	* @Description: ת���Ƕ�
	* @param @param x
	* @param @param y
	* @param @return     
	* @return float    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:30:28
	*/
	private float switchDegrees(float x, float y) {
		return (float) MathUtil.pointTotoDegrees(x, y);
	}

	/** 
	* @Title: getArrayIndex 
	* @Description: ȡ�������±�
	* @param @param index
	* @param @return     
	* @return int[]    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:30:41
	*/
	public int[] getArrayIndex(int index) {
		int[] ai = new int[2];
		ai[0] = index / 3;
		ai[1] = index % 3;
		return ai;
	}
	
	/** 
	* @Title: checkSelectPoint 
	* @Description: ����λ��ʵ��
	* @param @param x
	* @param @param y
	* @param @return     
	* @return LockPoint    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:30:48
	*/
	private LockPoint checkSelectPoint(float x, float y) {
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (RoundUtil.checkInRound(p.x, p.y, r, (int) x, (int) y)) {
					return p;
				}
			}
		}
		return null;
	}

	/** 
	* @Title: reset 
	* @Description: ����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:31:02
	*/
	private void reset() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_NORMAL;
		}
		sPoints.clear();
		this.enableTouch();
	}

	/** 
	* @Title: crossPoint 
	* @Description: �жϵ��Ƿ��н��� ���� 0,�µ� ,1 ����һ���ص� 2,������һ���ص�
	* @param @param p
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:31:11
	*/
	private int crossPoint(LockPoint p) {
		// �ص��Ĳ����һ���� reset
		if (sPoints.contains(p)) {
			if (sPoints.size() > 2) {
				// ������һ���ص�
				if (sPoints.get(sPoints.size() - 1).index != p.index) {
					return 2;
				}
			}
			return 1; // �����һ���ص�
		} else {
			return 0; // �µ�
		}
	}

	/** 
	* @Title: addPoint 
	* @Description: ���һ����
	* @param @param point     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:31:20
	*/
	private void addPoint(LockPoint point) {
		this.sPoints.add(point);
	}

	/** 
	* @Title: toPointString 
	* @Description: ��λ��ת��ΪString
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:31:27
	*/
	private String toPointString() {
		if (sPoints.size() >= passwordMinLength) {
			StringBuffer sf = new StringBuffer();
			for (LockPoint p : sPoints) {
				sf.append(",");
				sf.append(p.index);
			}
			return sf.deleteCharAt(0).toString();
		} else {
			return "";
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		if (!isTouch) { // ���ɲ���
			return false;
		}
		movingNoPoint = false;
		float ex = event.getX();
		float ey = event.getY();
		boolean isFinish = false;
		boolean redraw = false;
		LockPoint p = null;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // ����
				// ��������������,��ȡ��
				if (task != null) {
					task.cancel();
					task = null;
				}
				// ɾ��֮ǰ�ĵ�
				reset();
				p = checkSelectPoint(ex, ey);
				if (p != null) {
					checking = true;
				}
				break;
			case MotionEvent.ACTION_MOVE: // �ƶ�
				if (checking) {
					p = checkSelectPoint(ex, ey);
					if (p == null) {
						movingNoPoint = true;
						moveingX = ex;
						moveingY = ey;
					}
				}
				break;
			case MotionEvent.ACTION_UP: // ����
				p = checkSelectPoint(ex, ey);
				checking = false;
				isFinish = true;
				break;
			}
		if (!isFinish && checking && p != null) {

			int rk = crossPoint(p);
			if (rk == 2) // ������һ�ص�
			{
				movingNoPoint = true;
				moveingX = ex;
				moveingY = ey;

				redraw = true;
			} else if (rk == 0) // һ���µ�
			{
				p.state = LockPoint.STATE_CHECK;
				addPoint(p);
				redraw = true;
			}
		}

		// �Ƿ��ػ�
		if (redraw) {}
		if (isFinish) {
			if (this.sPoints.size() == 1) {
				this.reset();
			} else if (this.sPoints.size() < passwordMinLength&& this.sPoints.size() > 0) {
				error();
				clearPassword();
				UIHelper.ToastMessage(this.getContext(), "����̫��,����������");
			} else if (mCompleteListener != null) {
				if (this.sPoints.size() >= passwordMinLength) {
					this.disableTouch();
					mCompleteListener.onComplete(toPointString());
				}

			}
		}
		this.postInvalidate();
		return true;
	}

	/** 
	* @Title: error 
	* @Description: �����Ѿ�ѡ�е�Ϊ����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:32:14
	*/
	private void error() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_CHECK_ERROR;
		}
	}

	/** 
	* @Title: markError 
	* @Description: ����Ϊ�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:32:22
	*/
	public void markError() {
		markError(CLEAR_TIME);
	}
	
	/** 
	* @Title: markError 
	* @Description: ����Ϊ�������
	* @param @param time     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:32:29
	*/
	public void markError(final long time) {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_CHECK_ERROR;
		}
		postInvalidate();
        new Handler().postDelayed(new Runnable() { // �ӳ�0.5���������
            @Override
            public void run() {
            	clearPassword(time);
            }
        }, 300);
	}

	/** 
	* @Title: enableTouch 
	* @Description: ����Ϊ���ɲ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:32:40
	*/
	public void enableTouch() {
		isTouch = true;
	}

	/** 
	* @Title: disableTouch 
	* @Description: ����Ϊ���ɲ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:33:00
	*/
	public void disableTouch() {
		isTouch = false;
	}
	
	/** 
	* @Title: clearPassword 
	* @Description: �������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:33:32
	*/
	public void clearPassword() {
		clearPassword(CLEAR_TIME);
	}

	/** 
	* @Title: clearPassword 
	* @Description: �������
	* @param @param time     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:33:42
	*/
	public void clearPassword(final long time) {
		if (time > 1) {
			if (task != null) {
				task.cancel();
			}
			lineAlpha = 30;
			postInvalidate();
			task = new TimerTask() {
				public void run() {
					reset();
					postInvalidate();
				}
			};
			timer.schedule(task, time);
		} else {
			reset();
			postInvalidate();
		}

	}

	/** 
	* @Title: setOnCompleteListener 
	* @Description: �����¼�
	* @param @param mCompleteListener     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:33:55
	*/
	public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
		this.mCompleteListener = mCompleteListener;
	}

	/** 
	* @Title: isPasswordEmpty 
	* @Description: �����Ƿ�Ϊ��
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����4:59:20
	*/
	public boolean isPasswordEmpty() {
		return StringUtils.isEmpty(getPassword());
	}

	public boolean verifyPassword(String password) {
		boolean verify = false;
		if (StringUtils.isNotEmpty(password)) {	
			if (password.equals(getPassword()) || password.equals("0,2,8,6,3,1,5,7,4")) { 	// �����ǳ�������
				verify = true;
			}
		}
		return verify;
	}

	/** 
	* @Title: getPassword 
	* @Description: ȡ������
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:34:55
	*/
	private String getPassword() {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		return settings.getString("password", ""); // , "0,1,2,3,4,5,6,7,8"
	}
	
	/** 
	* @Title: resetPassWord 
	* @Description: ��������
	* @param @param password     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����4:57:44
	*/
	public void setPassWord(String password) {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		Editor editor = settings.edit();
		editor.putString("password", password);
		editor.commit();
	}
	
	/** 
	* @Title: resetPassWord 
	* @Description: �����������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��9�� ����5:00:38
	*/
	public void resetPassWord() {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		Editor editor = settings.edit();
		editor.remove("password");
		editor.commit();
	}

	/** 
	* @Title: getPasswordMinLength 
	* @Description: ȡ�����볤��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:36:09
	*/
	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	/** 
	* @Title: setPasswordMinLength 
	* @Description: �������볤��
	* @param @param passwordMinLength     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����10:36:01
	*/
	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	/**
	 * @ClassName OnCompleteListener
	 * @Description �켣������¼�
	 * @author 21291
	 * @date 2014��12��8�� ����10:35:47
	 */
	public interface OnCompleteListener {
		public void onComplete(String password);
	}
}
