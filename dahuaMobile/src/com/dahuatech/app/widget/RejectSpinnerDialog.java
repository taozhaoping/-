package com.dahuatech.app.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.inter.ISpinnerListener;

/**
 * @ClassName RejectSpinnerDialog
 * @Description ���ؽڵ� �Զ��嵯��������
 * @author 21291
 * @date 2014��9��12�� ����3:08:28
 */
public class RejectSpinnerDialog extends SpinnerDialog<RejectNodeInfo> {

	public RejectSpinnerDialog(Context context) {
		super(context);
	}

	public RejectSpinnerDialog(Context context, int theme) {
		super(context, theme);
	}

	public RejectSpinnerDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public RejectSpinnerDialog(Context context, List<RejectNodeInfo> list,ISpinnerListener readyListener) {
		super(context, list, readyListener);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reject_spinner_dialog);
	    mSpinner = (Spinner) findViewById (R.id.reject_spinnerDialog_spinner);	    
	    buttonOK = (Button) findViewById(R.id.reject_spinnerDialog_OK);
        buttonOK.setText(spinnerOk);
        buttonCancel = (Button) findViewById(R.id.reject_spinnerDialog_Cancle);
        buttonCancel.setText(spinnerCancle);
        
        //���ؼ�����Դ
        showList=new ArrayList<String>();
        //�������Դ����
        for (RejectNodeInfo item : mList) {
        	showList.add(item.getFNodeKey());
		}  
       
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext,android.R.layout.simple_spinner_item,showList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        mSpinner.setAdapter(adapter);
         
        //ȷ����ť����¼�
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
        	
        	@Override
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                RejectNodeInfo reInfo=mList.get(n);
                mReadyListener.rejectOk(n,reInfo);
                RejectSpinnerDialog.this.dismiss();
            }
        });
        
        //ȡ����ť����¼�
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                RejectSpinnerDialog.this.dismiss();
            }
        });
	}
	
	
}
