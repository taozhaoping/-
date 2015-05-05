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
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.inter.ISpinnerListener;

/**
 * @ClassName AdAmapSpinnerDialog
 * @Description �򿨵�ַ������
 * @author 21291
 * @date 2014��12��31�� ����10:58:34
 */
public class AdAmapSpinnerDialog extends SpinnerDialog<AdAmapInfo> {
	public AdAmapSpinnerDialog(Context context) {
		super(context);
	}

	public AdAmapSpinnerDialog(Context context, int theme) {
		super(context, theme);
	}

	public AdAmapSpinnerDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public AdAmapSpinnerDialog(Context context,List<AdAmapInfo> list, ISpinnerListener readyListener) {
		super(context,list, readyListener);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_amap_dialog);
	    mSpinner = (Spinner) findViewById (R.id.adAmap_spinnerDialog_spinner);	    
	    buttonOK = (Button) findViewById(R.id.adAmap_spinnerDialog_OK);
        buttonOK.setText(spinnerOk);
        buttonCancel = (Button) findViewById(R.id.adAmap_spinnerDialog_Cancle);
        buttonCancel.setText(spinnerCancle);
        
        //���ؼ�����Դ
        showList=new ArrayList<String>();
        //�������Դ����
        for (AdAmapInfo item : mList) {
        	showList.add(item.getFAddressType()+":"+item.getFAddress());
		}  
       
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext,android.R.layout.simple_spinner_item,showList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        mSpinner.setAdapter(adapter);
         
        //ȷ����ť����¼�
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
        	
        	@Override
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                AdAmapInfo adAmap=mList.get(n);
                mReadyListener.adAmapOk(n, adAmap);
                AdAmapSpinnerDialog.this.dismiss();
            }
        });
        
        //ȡ����ť����¼�
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                AdAmapSpinnerDialog.this.dismiss();
            }
        });
	}
}
