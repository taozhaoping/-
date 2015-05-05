package com.dahuatech.app.widget;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.dahuatech.app.inter.ISpinnerListener;

/**
 * @ClassName SpinnerDialog
 * @Description �Զ���������
 * @author 21291
 * @date 2014��9��12�� ����3:11:10
 * @param <T>
 */
public class SpinnerDialog<T> extends Dialog {
	protected List<T> mList;  //����Դ
	protected List<String> showList;  //��������ʾ�ļ���
	protected Context mContext;
	
	//�ؼ�
	protected Spinner mSpinner;  
	protected Button buttonOK,buttonCancel;
	
	protected String spinnerOk,spinnerCancle;
	protected ISpinnerListener mReadyListener;
	
	public String getSpinnerOk() {
		return spinnerOk;
	}

	public void setSpinnerOk(String spinnerOk) {
		this.spinnerOk = spinnerOk;
	}

	public String getSpinnerCancle() {
		return spinnerCancle;
	}

	public void setSpinnerCancle(String spinnerCancle) {
		this.spinnerCancle = spinnerCancle;
	}
	
	public SpinnerDialog(Context context) {
		super(context);
	}

	public SpinnerDialog(Context context, int theme) {
		super(context, theme);
	}

	public SpinnerDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public SpinnerDialog(Context context, ISpinnerListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
    }
	
	public SpinnerDialog(Context context, List<T> list, ISpinnerListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = list;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
