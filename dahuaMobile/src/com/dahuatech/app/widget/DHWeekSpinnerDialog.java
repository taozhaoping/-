package com.dahuatech.app.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.inter.ISpinnerListener;

/**
 * @ClassName DHWeekSpinnerDialog
 * @Description �ܴ� �Զ��嵯��������
 * @author 21291
 * @date 2014��10��24�� ����11:41:02
 */
public class DHWeekSpinnerDialog extends SpinnerDialog<DHWeekInfo> {

	private int fCurrentYear;					  //��ǰ���
	private int fCurrentWeekIndex;				  //��ǰ�ܴ�
	private Calendar cal;						  //����ʵ��
	
	private List<DHWeekInfo>  weekInfoList;       //����Ϣ����
	private Spinner mYearSpinner;  				  //��ݿؼ�
	
	public Spinner getmYearSpinner() {
		return mYearSpinner;
	}

	public void setmYearSpinner(Spinner mYearSpinner) {
		this.mYearSpinner = mYearSpinner;
	}

	public DHWeekSpinnerDialog(Context context) {
		super(context);
	}

	public DHWeekSpinnerDialog(Context context, int theme) {
		super(context, theme);
	}

	public DHWeekSpinnerDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public DHWeekSpinnerDialog(Context context,Integer fCurrentWeekIndex, ISpinnerListener readyListener) {
		super(context, readyListener);
		this.fCurrentWeekIndex=fCurrentWeekIndex;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_spinner_dialog);
		mYearSpinner=(Spinner) findViewById (R.id.week_spinner_dialog_year);	 //��ݿؼ�   
	    mSpinner = (Spinner) findViewById (R.id.week_spinner_dialog_week);	     //�ܴοؼ�
	   
	    buttonOK = (Button) findViewById(R.id.week_spinner_dialog_OK);
        buttonOK.setText(spinnerOk);
        buttonCancel = (Button) findViewById(R.id.week_spinner_dialog_Cancle);
        buttonCancel.setText(spinnerCancle);
        
        cal=new GregorianCalendar();    								//��ʼ������ʵ��
        fCurrentYear=cal.get(Calendar.YEAR);  							//��ȡ��ǰ���
        yearFillData(mYearSpinner,fCurrentYear);
        
        //ȷ����ť����¼�
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
        	
        	@Override
            public void onClick(View v) {
                int position = mSpinner.getSelectedItemPosition();
                String itemText=(String)mSpinner.getItemAtPosition(position);
                DHWeekInfo dInfo=weekInfoList.get(position);
                mReadyListener.dHWeekOk(position,itemText, dInfo);
                DHWeekSpinnerDialog.this.dismiss();
            }
        });
        
        //ȡ����ť����¼�
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                DHWeekSpinnerDialog.this.dismiss();
            }
        });
	}
	
	/** 
	* @Title: yearFillData 
	* @Description: ����������Դ
	* @param @param spinner
	* @param @param cal
	* @param @param currentYear     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��20�� ����11:20:43
	*/
	private void yearFillData(final Spinner spinner,int currentYear){
        List<String> showYearList=new ArrayList<String>();
        showYearList.add(String.valueOf(currentYear));  	//���õ�ǰ���
        showYearList.add(String.valueOf(currentYear-1));	//������һ�����
        
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,showYearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(yearAdapter);
        
        //������������
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			// ��̬�ı�ؼ��������İ�ֵ
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				int fYear= Integer.valueOf(spinner.getSelectedItem().toString());
				int fWeekIndex=1;
				if(fYear==fCurrentYear){
					fWeekIndex= fCurrentWeekIndex;
				}
				else{
					fWeekIndex=DateHelper.getNumWeeksForYear(cal, fYear);
				}
				weekInfoList=DateHelper.getDHWeekList(cal,fYear,fWeekIndex);  //��ʼ������Ϣ����
				weekFillData(mSpinner,weekInfoList);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
	}
	
	/** 
	* @Title: weekFillData 
	* @Description: ����ܴ�����Դ
	* @param @param spinner
	* @param @param list      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��20�� ����11:19:00
	*/
	private void weekFillData(final Spinner spinner,final List<DHWeekInfo> list){
        showList=new ArrayList<String>();
        String showWeekItem="";
        //�������Դ����
        for (DHWeekInfo item : list) {
        	showWeekItem=String.valueOf(item.FIndex)+"��"+" "+"["+item.FStartDate+"-"+item.FEndDate+"]";
        	showList.add(showWeekItem);
		}  
       
        ArrayAdapter<String> weekAdapter = new ArrayAdapter<String> (mContext,android.R.layout.simple_spinner_item,showList);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(weekAdapter);
	}
}
