package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHConfirmListChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListRootInfo;

/**
 * @ClassName DHComfirmListPersonAdapter
 * @Description �з���ʱȷ���б������Ա����������
 * @author 21291
 * @date 2014��11��5�� ����3:10:34
 */
public class DHComfirmListPersonAdapter extends BaseExpandableListAdapter {

	private List<DHConfirmListRootInfo> listData;  		//����Դ
	private final LayoutInflater inflater ;  			//��ͼ����
	private int pResource,tResource;         			//�Ӽ���ͼ
	
	public DHComfirmListPersonAdapter(Context context,List<DHConfirmListRootInfo> listData,int pResource,int tResource) {
		this.listData=listData;
		this.inflater = LayoutInflater.from(context);
		this.pResource=pResource;	
		this.tResource=tResource;
	}

	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<DHConfirmListRootInfo> listItems) {
		this.listData = listItems;
	    this.notifyDataSetChanged();
	}

	// �Զ�������ؼ�����
	static class DHListPersonRoot {
		public TextView dr_fTypeName;
		public TextView dr_fHours;
	}	
	private DHListPersonRoot rootHoder = null;
	
	// �Զ����Ӽ��ؼ�����
	static class DHListPersonChild {
		public TextView dc_fWeekDay;
		public TextView dc_fHours;	
	}	
	private DHListPersonChild childHoder = null;
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listData.get(groupPosition).getFChildren().get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {	
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = inflater.inflate(this.tResource, null);
			childHoder = new DHListPersonChild();

			// ��ȡ�ؼ�����
			childHoder.dc_fWeekDay = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_child_FWeekDay);
			childHoder.dc_fHours = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_child_FHours);
			// ���ÿؼ�����convertView
			convertView.setTag(childHoder);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			childHoder = (DHListPersonChild) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHConfirmListChildInfo dChildInfo = (DHConfirmListChildInfo) getChild(groupPosition,childPosition);
		childHoder.dc_fWeekDay.setText(dChildInfo.getFWeekDay());		
		childHoder.dc_fHours.setText(dChildInfo.getFHours()+"Сʱ");
		return convertView;
	}	
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listData.get(groupPosition).getFChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.listData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = inflater.inflate(this.pResource, parent, false);
			rootHoder = new DHListPersonRoot();

			// ��ȡ�ؼ�����
			rootHoder.dr_fTypeName = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_root_FTypeName);
			rootHoder.dr_fHours = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_root_FHours);
			
			// ���ÿؼ�����convertView
			convertView.setTag(rootHoder);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			rootHoder = (DHListPersonRoot) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHConfirmListRootInfo dRootInfo = (DHConfirmListRootInfo)getGroup(groupPosition);
		rootHoder.dr_fTypeName.setText(dRootInfo.getFTypeName());		
		rootHoder.dr_fHours.setText(dRootInfo.getFHours()+"Сʱ");	
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
