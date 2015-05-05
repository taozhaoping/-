package com.dahuatech.app.widget;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.LowerNodeAppResultInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppSpinnerInfo;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.ui.task.LowerNodeApproveActivity;

/**
 * @ClassName MultiSelectionSpinner
 * @Description �������ѡ�ؼ�
 * @author 21291
 * @date 2014��11��6�� ����11:37:34
 */
@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
public class MultiSelectionSpinner extends Spinner implements OnMultiChoiceClickListener{
	
	private String[] _items = null;  					//itemԪ������
	private boolean[] mSelection = null;  				//ѡ�в���ֵ����
	private ArrayAdapter<String> simple_adapter;		//��������
	
	private LowerNodeApproveActivity lNodeApprove;		//����ʵ����
	private String spinnerType;							//���������� 
	private int spinnerIndex,roleSpinnerCount;			//����������,��ɫ����������
	
	private String FNodeName;							//�ýڵ�����
	private TextView resultView;						//�ܵĽ���ı��ؼ�
	
	public LowerNodeApproveActivity getlNodeApprove() {
		return lNodeApprove;
	}

	public void setlNodeApprove(LowerNodeApproveActivity lNodeApprove) {
		this.lNodeApprove = lNodeApprove;
	}
	
	public String getSpinnerType() {
		return spinnerType;
	}

	public void setSpinnerType(String spinnerType) {
		this.spinnerType = spinnerType;
	}

	public int getSpinnerIndex() {
		return spinnerIndex;
	}

	public void setSpinnerIndex(int spinnerIndex) {
		this.spinnerIndex = spinnerIndex;
	}

	public int getRoleSpinnerCount() {
		return roleSpinnerCount;
	}

	public void setRoleSpinnerCount(int roleSpinnerCount) {
		this.roleSpinnerCount = roleSpinnerCount;
	}
	
	public String getFNodeName() {
		return FNodeName;
	}

	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}

	public TextView getResultView() {
		return resultView;
	}

	public void setResultView(TextView resultView) {
		this.resultView = resultView;
	}

	public MultiSelectionSpinner(Context context) {
		super(context);
		simple_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);
		super.setAdapter(simple_adapter);  
	}

	public MultiSelectionSpinner(Context context, int mode) {
		super(context, mode);
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		simple_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);  
		super.setAdapter(simple_adapter); 
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
	
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs,int defStyle, int mode) {
		super(context, attrs, defStyle, mode);
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (mSelection != null && which < mSelection.length) {  
			mSelection[which] = isChecked;  
			  
			simple_adapter.clear();  
			simple_adapter.add(buildSelectedItemString());  
			
	    } else {  
			 throw new IllegalArgumentException("������������");  
		}  
	}
	
	@Override  
	public boolean performClick() {  
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());  
		builder.setMultiChoiceItems(_items, mSelection, this);  
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result=getSelectedItemsAsString();
				Map<String,LowerNodeAppSpinnerInfo> spinnerMap=lNodeApprove.getSpinnerMap();
				
				if("User".equals(spinnerType)){  //˵���ڵ���Ա������
					LowerNodeAppSpinnerInfo lUserSpinnerInfo =spinnerMap.get(spinnerType+"_"+"0");
					if(lUserSpinnerInfo!=null){
						lUserSpinnerInfo.setFSpinnerValue(result);
					}
				}
	        	
				if("Role".equals(spinnerType)){  //˵����ɫ��Ա������
					for (int i = 0; i < roleSpinnerCount; i++) {
						if(i==spinnerIndex){
							LowerNodeAppSpinnerInfo lRoleSpinnerInfo =spinnerMap.get(spinnerType+"_"+String.valueOf(spinnerIndex));
							if(lRoleSpinnerInfo!=null){
								lRoleSpinnerInfo.setFSpinnerValue(result);
							}
						}
					}		
				}
				showResultView(getCurrentNodeResult(spinnerMap));
				dialog.dismiss();			
			}
		});               
		builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();  
		return true;  
	}  
	
	/** 
	* @Title: getCurrentNodeResult 
	* @Description: ���򲢻�ȡ��ǰ�ڵ���ѡ���ֵ
	* @param @param map
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����11:50:29
	*/
	@SuppressWarnings("rawtypes")
	private String getCurrentNodeResult(Map<String,LowerNodeAppSpinnerInfo> map){
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;  
		
		//����Iterator����HashMap  Ч�ʸ�
		Iterator it = map.entrySet().iterator();
        Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppSpinnerInfo lSpinnerInfo =map.get(key);
        	if(!StringUtils.isEmpty(lSpinnerInfo.getFSpinnerValue())){
        		if(foundOne){
            		sb.append(",");  
            	}
            	foundOne=true;
            	sb.append(lSpinnerInfo.getFSpinnerValue()); 
        	}	 
        }  
        return sb.toString();
	}
	
	/** 
	* @Title: showResultView 
	* @Description: ��ʾ�����ı��ؼ�ֵ
	* @param @param currentNodeResult     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����11:49:51
	*/
	private void showResultView(String currentNodeResult){
		Map<String,LowerNodeAppResultInfo> nodeValueMap=lNodeApprove.getNodeValueMap();
		LowerNodeAppResultInfo nodeResult=nodeValueMap.get(FNodeName);
		nodeResult.setFSelectResult(currentNodeResult);
		nodeResult.setFShowResult(FNodeName+":"+currentNodeResult);
		resultView.setText(getNodeValueMap(nodeValueMap)); //���ֵ
	}
	
	 @Override  
	 public void setAdapter(SpinnerAdapter adapter) {  
		 throw new RuntimeException("setAdapter����֧��");  
	 }
	 
	/** 
	* @Title: setItems 
	* @Description: ����Ԫ�ؼ���(�������)
	* @param @param items     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:26:36
	*/
	public void setItems(String[] items) {  
		_items = items;  
		mSelection = new boolean[_items.length];  
		simple_adapter.clear();  
		simple_adapter.add(_items[0]);  
		Arrays.fill(mSelection, false);  
	}  
		  
	/** 
	* @Title: setItems 
	* @Description: ����-����Ԫ�ؼ���(���ϲ���)
	* @param @param items     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:27:23
	*/
	public void setItems(List<String> items) { 
		_items = items.toArray(new String[items.size()]);  
		mSelection = new boolean[_items.length];  
        simple_adapter.clear();  
		simple_adapter.add(_items[0]);  
		Arrays.fill(mSelection, false);  
	}  
	
	/** 
	* @Title: setSelection 
	* @Description: ����ѡ��Ԫ��(�������)
	* @param @param selection     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:28:40
	*/
	public void setSelection(String[] selection) {  
	    for (String cell : selection) {  
			for (int j = 0; j < _items.length; ++j) {  
				if (_items[j].equals(cell)) {  
					mSelection[j] = true;  
				}  
			}  
		}  
	} 
	
	/** 
	* @Title: setSelection 
	* @Description: ����-ѡ��Ԫ�ؼ���(���ϲ���)
	* @param @param selection     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:30:30
	*/
	public void setSelection(List<String> selection) {  
	    for (int i = 0; i < mSelection.length; i++) {  
		    mSelection[i] = false;  
		}  
		for (String sel : selection) {  
		    for (int j = 0; j < _items.length; ++j) {  
		    	if (_items[j].equals(sel)) {  
		    		mSelection[j] = true;  
		    	}  
		    }  
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
    }  
	
	/** (�� Javadoc) 
	* <p>Title: setSelection</p> 
	* <p>Description: ����ѡ����</p> 
	* @param index ����ֵ
	* @see android.widget.AbsSpinner#setSelection(int) 
	*/
	public void setSelection(int index) {  
		for (int i = 0; i < mSelection.length; i++) {  
	        mSelection[i] = false;  
		}  
		if (index >= 0 && index < mSelection.length) {  
		    mSelection[index] = true;  
		} else {  
		    throw new IllegalArgumentException("����ֵ " + index + " ��������");  
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
	}  
	
	/** 
	* @Title: setSelection 
	* @Description: ����ѡ����
	* @param @param selectedIndicies  ����Ԫ��    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:34:11
	*/
	public void setSelection(int[] selectedIndicies) {  
		for (int i = 0; i < mSelection.length; i++) {  
			mSelection[i] = false;  
		}  
		for (int index : selectedIndicies) {  
			if (index >= 0 && index < mSelection.length) {  
				mSelection[index] = true;  
			} else {  
				throw new IllegalArgumentException("����ֵ " + index+ " ��������");  
			}     
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
    }  
	
	/** 
	* @Title: getSelectedStrings 
	* @Description: ��ȡѡ�е�Ԫ��ֵ����
	* @param @return     
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:37:10
	*/
	public List<String> getSelectedStrings() {  
		List<String> selection = new LinkedList<String>();  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				selection.add(_items[i]);  
			}  
		}  
		return selection;  
	}  
	
	/** 
	* @Title: getSelectedIndicies 
	* @Description: ��ȡѡ�е�Ԫ����������
	* @param @return     
	* @return List<Integer>    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:39:02
	*/
	public List<Integer> getSelectedIndicies() {  
		List<Integer> selection = new LinkedList<Integer>();  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				selection.add(i);  
			}  
		}  
		return selection;  
	}  
	
	/** 
	* @Title: buildSelectedItemString 
	* @Description: ����ѡ��Ԫ���ַ���
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:39:38
	*/
	private String buildSelectedItemString() {  
		StringBuilder sb = new StringBuilder();  
		boolean foundOne = false;    
		
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				if (foundOne) {  
					sb.append(",");  
				}  
				foundOne = true;  	  
				sb.append(_items[i]);  
			}  
		}  
		return sb.toString();  
	} 
	
	/** 
	* @Title: getSelectedItemsAsString 
	* @Description: ��ȡѡ��Ԫ���ַ���
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��11��6�� ����11:42:06
	*/
	public String getSelectedItemsAsString() {  
		StringBuilder sb = new StringBuilder();  
		boolean foundOne = false;  
		  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				if (foundOne) {  
					sb.append(", ");  
				}  
				foundOne = true;  
				sb.append(_items[i]);  
		    }  
		}  
		return sb.toString();  
	} 
	
	@SuppressWarnings("rawtypes")
	public static String getNodeValueMap(Map<String,LowerNodeAppResultInfo> nodeValueMap){
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;  
		//����Iterator����HashMap  Ч�ʸ�
        Iterator it = nodeValueMap.entrySet().iterator(); 
        Map.Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppResultInfo lResultInfo =nodeValueMap.get(key);
        	if(!StringUtils.isEmpty(lResultInfo.getFShowResult())){
        		if(foundOne){
            		sb.append(";");  
            	}
            	foundOne=true;
            	sb.append(lResultInfo.getFShowResult()); 
        	}	 
        }  
        return sb.toString();
	}
}
