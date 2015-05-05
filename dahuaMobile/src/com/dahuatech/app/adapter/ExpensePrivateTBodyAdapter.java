package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyInfo;

/**
 * @ClassName ExpensePrivateTBodyAdapter
 * @Description ��˽���ݷ���������ϸ��������
 * @author 21291
 * @date 2014��5��26�� ����2:43:42
 */
public class ExpensePrivateTBodyAdapter extends MyBaseAdapter<ExpensePrivateTBodyInfo> {

	private ExpensePrivateTBodyView listItemView=null; // �Զ�����ͼ
	
	public ExpensePrivateTBodyAdapter(Context context, List<ExpensePrivateTBodyInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//�Զ���ؼ�����
	static class ExpensePrivateTBodyView{
		public TextView ep_fConSmDate;
		public TextView ep_fConSmType;
		public TextView ep_fAmount;
		public TextView ep_fProjectName;
		public TextView ep_fClientName;
		public TextView ep_fUse;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡexpenseprivatetbody�����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new ExpensePrivateTBodyView();
			
			//��ȡ�ؼ�����
			listItemView.ep_fConSmDate=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FConSmDate);
			listItemView.ep_fConSmType=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FConSmType);		
			listItemView.ep_fAmount=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FAmount);
			listItemView.ep_fProjectName=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FProjectName);
			listItemView.ep_fClientName=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FClientName);
			listItemView.ep_fUse=(TextView) convertView.findViewById(R.id.expensePrivateTBodyInfo_FUse);
			
			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			listItemView=(ExpensePrivateTBodyView) convertView.getTag();
		}
		// ����position,�Ӽ��ϻ�ȡһ������
		ExpensePrivateTBodyInfo eTBodyInfo=listItems.get(position);
		// �����ݰ󶨵�item������
		listItemView.ep_fConSmDate.setText(eTBodyInfo.getFConSmDate());
		listItemView.ep_fConSmType.setText(eTBodyInfo.getFConSmType());
		listItemView.ep_fAmount.setText(eTBodyInfo.getFAmount());
		listItemView.ep_fProjectName.setText(eTBodyInfo.getFProjectName());
		listItemView.ep_fClientName.setText(eTBodyInfo.getFClientName());
		listItemView.ep_fUse.setText(eTBodyInfo.getFUse());
		
		return convertView;
	}
	
	//���õ���¼�
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
