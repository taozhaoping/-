package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHProjectInfo;

/**
 * @ClassName DHProjectAdapter
 * @Description �з���ʱģ�� ��Ŀ�����б���������
 * @author 21291
 * @date 2014��10��30�� ����4:37:58
 */
public class DHProjectAdapter extends MyBaseAdapter<DHProjectInfo> {
	
	public DHProjectAdapter(Context context,List<DHProjectInfo> data, int resource) {
		super(context, data, resource);
	}

	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<DHProjectInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class DHProjectViewHolder{
		public RelativeLayout dv_fRelativeLayout;
		public TextView dv_fProjectCode;
		public TextView dv_fProjectName;
	}
	private DHProjectViewHolder dProjectViewHolder=null;  //�Զ�����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			dProjectViewHolder=new DHProjectViewHolder();
			
			//��ȡ�ؼ�����
			dProjectViewHolder.dv_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.dh_project_search_item);
			dProjectViewHolder.dv_fProjectCode=(TextView)convertView.findViewById(R.id.dh_project_search_item_FProjectCode);
			dProjectViewHolder.dv_fProjectName=(TextView)convertView.findViewById(R.id.dh_project_search_item_FProjectName);
			
			//���ÿؼ�����convertView
			convertView.setTag(dProjectViewHolder);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			dProjectViewHolder=(DHProjectViewHolder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHProjectInfo dInfo=listItems.get(position);
		dProjectViewHolder.dv_fRelativeLayout.setTag(dInfo);
		if("-1".equals(dInfo.getFProjectCode())){
			dProjectViewHolder.dv_fProjectCode.setVisibility(View.GONE);
		}
		else {
			dProjectViewHolder.dv_fProjectCode.setVisibility(View.VISIBLE);
		}
		
		dProjectViewHolder.dv_fProjectCode.setText(dInfo.getFProjectCode());
		dProjectViewHolder.dv_fProjectName.setText(dInfo.getFProjectName());
		return convertView;
	}

}
