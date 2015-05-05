package com.dahuatech.app.adapter;

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
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;

/**
 * @ClassName MeetingPersonAdapter
 * @Description ѡ����Ա��������
 * @author 21291
 * @date 2014��9��25�� ����9:53:39
 */
public class MeetingPersonAdapter extends MyBaseAdapter<MeetingPersonInfo> {
	private int selectItem = -1;						//��ǰѡ�е�λ��
    private SparseArray<Boolean> isSelected;  		//��������CheckBox��ѡ��״��  
	
	private MeetingPersonView meetingPersonView = null;  //�Զ�����ͼ

	@SuppressLint("UseSparseArrays")
	public MeetingPersonAdapter(Context context, List<MeetingPersonInfo> data,int resource,Boolean chkFlag) {
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
    
    public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
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
	public void refreshView(List<MeetingPersonInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	// �Զ���ؼ�����
	static class MeetingPersonView {
		public RelativeLayout mp_fRelativeLayout;
		public TextView mp_fItemNumber;
		public TextView mp_fItemName;
		public TextView mp_fDeptName;
		public CheckBox mp_fChkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			meetingPersonView = new MeetingPersonView();

			// ��ȡ�ؼ�����
			meetingPersonView.mp_fRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.meeting_person_list_item);
			meetingPersonView.mp_fItemNumber = (TextView) convertView.findViewById(R.id.meeting_person_list_item_FItemNumber);
			meetingPersonView.mp_fItemName = (TextView) convertView.findViewById(R.id.meeting_person_list_item_FItemName);
			meetingPersonView.mp_fDeptName = (TextView) convertView.findViewById(R.id.meeting_person_list_item_FDeptName);
			meetingPersonView.mp_fChkBox = (CheckBox) convertView.findViewById(R.id.meeting_person_list_item_chkBox);
			
			// ���ÿؼ�����convertView
			convertView.setTag(meetingPersonView);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			meetingPersonView = (MeetingPersonView) convertView.getTag();
		}
		
		if(selectItem==position){  //ѡ��״̬
			convertView.findViewById(R.id.meeting_person_list_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
		}
		else{
			convertView.findViewById(R.id.meeting_person_list_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}

		// ����position,�Ӽ��ϻ�ȡһ������
		MeetingPersonInfo mInfo = listItems.get(position);
		meetingPersonView.mp_fRelativeLayout.setTag(mInfo);
		if("-1".equals(mInfo.getFItemNumber())){
			meetingPersonView.mp_fItemNumber.setVisibility(View.GONE);
			meetingPersonView.mp_fDeptName.setVisibility(View.GONE);
			meetingPersonView.mp_fChkBox.setVisibility(View.GONE);
		}
		else{
			meetingPersonView.mp_fItemNumber.setVisibility(View.VISIBLE);
			meetingPersonView.mp_fDeptName.setVisibility(View.VISIBLE);
			meetingPersonView.mp_fChkBox.setVisibility(View.VISIBLE);
		}
		meetingPersonView.mp_fItemNumber.setText(mInfo.getFItemNumber());
		meetingPersonView.mp_fItemName.setText(mInfo.getFItemName());
		meetingPersonView.mp_fItemName.setTag(mInfo);
		meetingPersonView.mp_fDeptName.setText(mInfo.getFDeptName());
		
		meetingPersonView.mp_fChkBox.setChecked(getIsSelected().get(position));
		meetingPersonView.mp_fChkBox.setOnClickListener(new View.OnClickListener() {	
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
