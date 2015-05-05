package com.dahuatech.app.adapter;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;

/**
 * @ClassName ExpenseFlowLocalAdapter
 * @Description ���ش��ϴ��б���������
 * @author 21291
 * @date 2014��9��9�� ����9:55:51
 */
@SuppressLint("UseSparseArrays")
public class ExpenseFlowLocalAdapter extends MyBaseAdapter<ExpenseFlowDetailInfo> {

	private String[] fTravelLabelArray;					//���÷ѱ�ǩֵ����
	private String[] fSocializeLabelArray;				//����Ӧ��ѱ�ǩֵ����
	private String[] fTravelValueArray;					//���÷�ʵ��ֵ����
	private String[] fSocializeValueArray;				//����Ӧ���ʵ��ֵ����
	
	private int selectItem = -1;						//��ǰѡ�е�λ��
    private SparseArray<Boolean> isSelected;  			//��������CheckBox��ѡ��״��  
    
    private ExpenseFlowLocalView eFlowLocalView=null;	//�Զ�����ͼ

	public ExpenseFlowLocalAdapter(Context context,List<ExpenseFlowDetailInfo> data, int resource) {
		super(context, data, resource);
		fTravelLabelArray=context.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=context.getResources().getStringArray(R.array.socialize_labels_array);
		fTravelValueArray=context.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=context.getResources().getStringArray(R.array.socialize_value_array);
		isSelected=new SparseArray<Boolean>();
		initDate();
	}

    public void initDate(){  
        for(int i=0; i< getCount();i++) {  
            getIsSelected().put(i,false);
        }  
    } 

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}
	
	public SparseArray<Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(SparseArray<Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	//ˢ����ͼ
	public void refreshView(List<ExpenseFlowDetailInfo> listItems) {
	    this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}

	//�Զ���ؼ�����
	static class ExpenseFlowLocalView{
		public RelativeLayout ef_fRelativeLayout;
		public TextView ef_fExpendTime;
		public TextView ef_fExpendType;
		public TextView ef_fCause;
		public TextView ef_fExpendAddress;
		public TextView ef_fExpendAmount;
		public CheckBox ef_fChkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			eFlowLocalView=new ExpenseFlowLocalView();
			
			//��ȡ�ؼ�����
			eFlowLocalView.ef_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.expense_flowlocal_list_item);
			eFlowLocalView.ef_fExpendTime=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendTime);
			eFlowLocalView.ef_fExpendType=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendType);
			eFlowLocalView.ef_fCause=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FCause);
			eFlowLocalView.ef_fExpendAddress=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendAddress);
			eFlowLocalView.ef_fExpendAmount=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendAmount);
			eFlowLocalView.ef_fChkBox=(CheckBox)convertView.findViewById(R.id.expense_flowlocal_list_item_chkBox);
			
			//���ÿؼ�����convertView
			convertView.setTag(eFlowLocalView);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			eFlowLocalView=(ExpenseFlowLocalView) convertView.getTag();
		}
		
		if(selectItem==position){  //ѡ��״̬
			convertView.findViewById(R.id.expense_flowlocal_list_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
			eFlowLocalView.ef_fExpendTime.setTextColor(context.getResources().getColor(R.color.active_font));
			eFlowLocalView.ef_fExpendType.setTextColor(context.getResources().getColor(R.color.active_font));
			eFlowLocalView.ef_fCause.setTextColor(context.getResources().getColor(R.color.active_font));
		}
		else{ //δѡ��״̬
			convertView.findViewById(R.id.expense_flowlocal_list_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			eFlowLocalView.ef_fExpendTime.setTextColor(context.getResources().getColor(R.color.default_font));
			eFlowLocalView.ef_fExpendType.setTextColor(context.getResources().getColor(R.color.default_font));
			eFlowLocalView.ef_fCause.setTextColor(context.getResources().getColor(R.color.default_font));
			getIsSelected().put(position,false);  
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		ExpenseFlowDetailInfo eInfo=listItems.get(position);
		eFlowLocalView.ef_fRelativeLayout.setTag(eInfo);
		eFlowLocalView.ef_fExpendTime.setText(eInfo.getFExpendTime());
		eFlowLocalView.ef_fExpendType.setText(getExpendType(eInfo.getFExpendTypeParent(),eInfo.getFExpendTypeChild()));
		eFlowLocalView.ef_fCause.setText(eInfo.getFCause());
		eFlowLocalView.ef_fExpendAddress.setText(eInfo.getFExpendAddress());
		eFlowLocalView.ef_fExpendAmount.setText(eInfo.getFExpendAmount());
		
		eFlowLocalView.ef_fChkBox.setChecked(getIsSelected().get(position));
		eFlowLocalView.ef_fChkBox.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v ;  	
				if(getIsSelected().get(position)){
					getIsSelected().put(position,false);  
					cb.setChecked(false); 	
				}
				else {
					getIsSelected().put(position,true);  
					cb.setChecked(true); 
				}
			}
		});
		
		return convertView;
	}
	
	/** 
	* @Title: getExpendType 
	* @Description: ���ݸ��������ID����ȡ��������
	* @param @param fExpendTypeParent
	* @param @param fExpendTypeChild
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����10:14:28
	*/
	private String getExpendType(String fExpendTypeParent,String fExpendTypeChild){
		String expendType="";
		int fChildPosition;
		if("2006".equals(fExpendTypeParent)){  //���÷�
			fChildPosition=Arrays.asList(fTravelValueArray).indexOf(fExpendTypeChild);
			expendType=fTravelLabelArray[fChildPosition];
		}
		else  //����Ӧ���
		{
			fChildPosition=Arrays.asList(fSocializeValueArray).indexOf(fExpendTypeChild);
			expendType=fSocializeLabelArray[fChildPosition];
		}	
		return expendType;
	}
}
