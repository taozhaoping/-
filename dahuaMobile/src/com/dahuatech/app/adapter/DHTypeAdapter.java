package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHTypeInfo;

/**
 * @ClassName DHTypeAdapter
 * @Description �з���ʱģ�� ���������б���������
 * @author 21291
 * @date 2014��11��4�� ����9:55:02
 */
public class DHTypeAdapter extends MyBaseAdapter<DHTypeInfo> {
	
	public DHTypeAdapter(Context context,List<DHTypeInfo> data, int resource) {
		super(context, data, resource);
	}

	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<DHTypeInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class DHTypeViewHolder{
		public RelativeLayout dv_fRelativeLayout;
		public TextView dv_fTypeId;
		public TextView dv_fTypeName;
	}
	private DHTypeViewHolder dTypeViewHolder=null;  //�Զ�����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			dTypeViewHolder=new DHTypeViewHolder();
			
			//��ȡ�ؼ�����
			dTypeViewHolder.dv_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.dh_type_list_item);
			dTypeViewHolder.dv_fTypeId=(TextView)convertView.findViewById(R.id.dh_type_list_item_FTypeId);
			dTypeViewHolder.dv_fTypeName=(TextView)convertView.findViewById(R.id.dh_type_list_item_FTypeName);
			
			//���ÿؼ�����convertView
			convertView.setTag(dTypeViewHolder);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			dTypeViewHolder=(DHTypeViewHolder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHTypeInfo dInfo=listItems.get(position);
		dTypeViewHolder.dv_fRelativeLayout.setTag(dInfo);
		dTypeViewHolder.dv_fTypeId.setText(dInfo.getFTypeId());
		dTypeViewHolder.dv_fTypeName.setText(dInfo.getFTypeName());
		return convertView;
	}

}
