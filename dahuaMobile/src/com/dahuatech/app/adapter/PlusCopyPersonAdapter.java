package com.dahuatech.app.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;

/**
 * @ClassName PlusCopyPersonAdapter
 * @Description ��ǩ/������Ա��ѯ��������
 * @author 21291
 * @date 2014��9��25�� ����9:53:30
 */
@SuppressLint("UseSparseArrays")
public class PlusCopyPersonAdapter extends MyBaseAdapter<PlusCopyPersonInfo> {
    private SparseArray<Boolean> isSelected;  		//��������CheckBox��ѡ��״��  	
	private PlusCopyPersonView plusCopyPersonView = null;  //�Զ�����ͼ
	
	public PlusCopyPersonAdapter(Context context, List<PlusCopyPersonInfo> data,int resource,Boolean chkFlag) {
		super(context, data, resource);
		isSelected=new SparseArray<Boolean>();
		initDate(chkFlag);
	}
	
	// ��ʼ��isSelected������  
    public void initDate(Boolean chkFlag){  
        for(int i=0; i< getCount();i++) {  
            getIsSelected().put(i,chkFlag);  
        }  
    } 
    
	public SparseArray<Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(SparseArray<Boolean> isSelected) {
		this.isSelected = isSelected;
	}
	
	// ˢ����ͼ
	public void refreshView() {
	    this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<PlusCopyPersonInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	// �Զ���ؼ�����
	static class PlusCopyPersonView {
		public TextView pc_fItemNumber;
		public TextView pc_fItemName;
		public CheckBox pc_fChkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			plusCopyPersonView = new PlusCopyPersonView();

			// ��ȡ�ؼ�����
			plusCopyPersonView.pc_fItemNumber = (TextView) convertView.findViewById(R.id.pluscopy_person_item_FItemNumber);
			plusCopyPersonView.pc_fItemName = (TextView) convertView.findViewById(R.id.pluscopy_person_item_FItemName);
			plusCopyPersonView.pc_fChkBox = (CheckBox) convertView.findViewById(R.id.pluscopy_person_item_chkBox);
			
			// ���ÿؼ�����convertView
			convertView.setTag(plusCopyPersonView);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			plusCopyPersonView = (PlusCopyPersonView) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		PlusCopyPersonInfo pInfo = listItems.get(position);
		if("-1".equals(pInfo.getFItemNumber())){  //��ʾ����ʾ��ѡ��
			plusCopyPersonView.pc_fItemNumber.setVisibility(View.GONE);
			plusCopyPersonView.pc_fChkBox.setVisibility(View.GONE);
		}
		else{
			plusCopyPersonView.pc_fItemNumber.setVisibility(View.VISIBLE);
			plusCopyPersonView.pc_fChkBox.setVisibility(View.VISIBLE);
		}
		plusCopyPersonView.pc_fItemNumber.setText(pInfo.getFItemNumber());
		plusCopyPersonView.pc_fItemName.setText(pInfo.getFItemName());
		plusCopyPersonView.pc_fItemName.setTag(pInfo);
		
		plusCopyPersonView.pc_fChkBox.setChecked(getIsSelected().get(position));
		plusCopyPersonView.pc_fChkBox.setOnClickListener(new View.OnClickListener() {	
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
}
