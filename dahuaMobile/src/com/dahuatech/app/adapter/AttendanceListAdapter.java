package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.attendance.AdListInfo;

/**
 * @ClassName AttendanceListAdapter
 * @Description �����б�������
 * @author 21291
 * @date 2014��12��18�� ����3:03:30
 */
public class AttendanceListAdapter extends MyBaseAdapter<AdListInfo> {

	private AttendanceListView adListView = null;
	public AttendanceListAdapter(Context context, List<AdListInfo> data,int resource) {
		super(context, data, resource);
	}

	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<AdListInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}

	// �Զ���ؼ�����
	static class AttendanceListView {
		public RelativeLayout ad_fRelativeLayout;
		public TextView ad_fAttendanceDate;
		public TextView ad_fType;
		public TextView ad_fCheckInTime;
		public TextView ad_fCheckOutTime;
		public TextView ad_fAmResult;
		public TextView ad_fPmResult;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			adListView = new AttendanceListView();

			// ��ȡ�ؼ�����
			adListView.ad_fRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.attendance_list_item);
			adListView.ad_fAttendanceDate = (TextView) convertView.findViewById(R.id.attendance_list_item_FAttendanceDate);
			adListView.ad_fType = (TextView) convertView.findViewById(R.id.attendance_list_item_FType);
			adListView.ad_fCheckInTime = (TextView) convertView.findViewById(R.id.attendance_list_item_FCheckInTime);
			adListView.ad_fCheckOutTime = (TextView) convertView.findViewById(R.id.attendance_list_item_FCheckOutTime);
			adListView.ad_fAmResult = (TextView) convertView.findViewById(R.id.attendance_list_item_FAmResult);
			adListView.ad_fPmResult = (TextView) convertView.findViewById(R.id.attendance_list_item_FPmResult);
		
			// ���ÿؼ�����convertView
			convertView.setTag(adListView);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			adListView = (AttendanceListView) convertView.getTag();
		}

		// ����position,�Ӽ��ϻ�ȡһ������
		AdListInfo adListInfo = listItems.get(position);
		adListView.ad_fRelativeLayout.setTag(adListInfo);
		adListView.ad_fAttendanceDate.setText(adListInfo.getFAttendanceDate());
		adListView.ad_fType.setText(adListInfo.getFType());
		adListView.ad_fCheckInTime.setText(adListInfo.getFAmCheckInTime());
		adListView.ad_fCheckOutTime.setText(adListInfo.getFPmCheckOutTime());
		adListView.ad_fAmResult.setText(adListInfo.getFAmResult());
		adListView.ad_fPmResult.setText(adListInfo.getFPmResult());

		return convertView;
	}
}
