package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHListProjectInfo;
import com.dahuatech.app.bean.develophour.DHListTypeInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName DHListProjectAdapter
 * @Description �з���ʱ�б��������Ŀ��������
 * @author 21291
 * @date 2014��10��23�� ����10:09:42
 */
public class DHListProjectAdapter extends BaseExpandableListAdapter {
	private Context context;		   				//�����Ļ���
	private Integer fBillId;						//�ܵ���ID
	private String fItemNumber;						//Ա����
	private String fWeekDate,fWeekValue;			//��ʱ����,ÿ��ֵ
	private List<DHListProjectInfo> listData;  		//����Դ
	private final LayoutInflater inflater ;  		//��ͼ����
	
	private int pResource,tResource;         		//�Ӽ���ͼ
	private int parentItem = -1;		 			//��ǰѡ�еĸ���
	private int childItem=-1;						//��ǰѡ�е�����
	
	public DHListProjectAdapter(Context context,int fBillId, String fWeekValue,String fItemNumber,String fWeekDate,List<DHListProjectInfo> listData,int pResource,int tResource) {
		this.context=context;
		this.fBillId=fBillId;
		this.fWeekValue=fWeekValue;
		this.fItemNumber=fItemNumber;
		this.fWeekDate=fWeekDate;
		this.listData=listData;
		this.inflater = LayoutInflater.from(context);
		this.pResource=pResource;	
		this.tResource=tResource;
	}

	public void setParentItem(int parentItem) {
		this.parentItem = parentItem;
	}

	public void setChildItem(int childItem) {
		this.childItem = childItem;
	}

	public void refreshView() {
		setParentItem(-1);
		setChildItem(-1);
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<DHListProjectInfo> listItems) {
		this.listData = listItems;
		setParentItem(-1);
		setChildItem(-1);
	    this.notifyDataSetChanged();
	}

	// �Զ���ؼ�����
	static class DHProjectViewHoder {
		public TextView dv_fProjectName;
		public TextView dv_fHours;
	}	
	private DHProjectViewHoder pViewHoder = null;
	
	static class DHTypeViewHoder {
		public TextView dv_fTypeName;
		public TextView dv_fHours;	
		public RelativeLayout dv_fTypeReLayout;
		public Button dv_fTypeAdd;	
	}
		
	private DHTypeViewHoder tViewHoder = null;
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listData.get(groupPosition).getdListTypeInfo().get(childPosition);
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
			tViewHoder = new DHTypeViewHoder();

			// ��ȡ�ؼ�����
			tViewHoder.dv_fTypeName = (TextView) convertView.findViewById(R.id.dh_list_type_item_FTypeName);
			tViewHoder.dv_fHours = (TextView) convertView.findViewById(R.id.dh_list_type_item_FHours);
					
			tViewHoder.dv_fTypeReLayout = (RelativeLayout) convertView.findViewById(R.id.dh_list_type_item_FLayout);
			tViewHoder.dv_fTypeAdd=(Button)convertView.findViewById(R.id.dh_list_type_item_FAdd);
			// ���ÿؼ�����convertView
			convertView.setTag(tViewHoder);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			tViewHoder = (DHTypeViewHoder) convertView.getTag();
		}
		
		if(parentItem==groupPosition){  //ѡ��״̬
			if(childItem==childPosition){
				convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.dh_type_background_color));
			}
			else
			{
				convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			}
		}
		else{
			convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHListTypeInfo dhListTypeInfo = (DHListTypeInfo) getChild(groupPosition,childPosition);
		tViewHoder.dv_fTypeName.setText(dhListTypeInfo.getFTypeName());		
		tViewHoder.dv_fHours.setText(dhListTypeInfo.getFHours()+"Сʱ");
		
		if(isLastChild){  //��������һ������
			tViewHoder.dv_fTypeReLayout.setVisibility(View.VISIBLE);
			tViewHoder.dv_fTypeAdd.setOnClickListener(new ButtonListener(groupPosition));
		}
		else {
			tViewHoder.dv_fTypeReLayout.setVisibility(View.GONE);
		}
		return convertView;
	}	
	
	/**
	 * @ClassName ButtonListener
	 * @Description ��Ӱ�ť���������
	 * @author 21291
	 * @date 2014��11��4�� ����1:30:31
	 */
	private class ButtonListener implements OnClickListener {
		private int bPosition;
		
		public ButtonListener(int position) {
			this.bPosition = position;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==tViewHoder.dv_fTypeAdd.getId()){  //�����ť
				final DHListProjectInfo dhProjectInfo = (DHListProjectInfo)getGroup(bPosition);
				UIHelper.showDHListProjectDetail(context,fBillId,fWeekValue,fItemNumber,"Add","DhListType",fWeekDate,dhProjectInfo.getFProjectCode(),dhProjectInfo.getFProjectName(),"");
			}
		}
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listData.get(groupPosition).getdListTypeInfo().size();
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
			pViewHoder = new DHProjectViewHoder();

			// ��ȡ�ؼ�����
			pViewHoder.dv_fProjectName = (TextView) convertView.findViewById(R.id.dh_list_project_item_FProjectName);
			pViewHoder.dv_fHours = (TextView) convertView.findViewById(R.id.dh_list_project_item_FHours);
			
			// ���ÿؼ�����convertView
			convertView.setTag(pViewHoder);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			pViewHoder = (DHProjectViewHoder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final DHListProjectInfo dhProjectInfo = (DHListProjectInfo)getGroup(groupPosition);
		pViewHoder.dv_fProjectName.setText(dhProjectInfo.getFProjectName());		
		pViewHoder.dv_fHours.setText(dhProjectInfo.getFHours()+"Сʱ");	
		
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
