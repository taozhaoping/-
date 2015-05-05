package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.ExpenseSpecialTBodyInfo;

/**
 * @ClassName ExpenseSpecialTBodyAdapter
 * @Description �������񵥾ݷ���������ϸ��������
 * @author 21291
 * @date 2014��6��20�� ����3:12:02
 */
public class ExpenseSpecialTBodyAdapter extends MyBaseAdapter<ExpenseSpecialTBodyInfo> {

	private ExpenseSpecialTBodyView listItemView=null; // �Զ�����ͼ
	
	public ExpenseSpecialTBodyAdapter(Context context, List<ExpenseSpecialTBodyInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//�Զ���ؼ�����
	static class ExpenseSpecialTBodyView{
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
			// ��ȡexpensespecialtbody�����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new ExpenseSpecialTBodyView();
			
			//��ȡ�ؼ�����
			listItemView.ep_fConSmDate=(TextView) convertView.findViewById(R.id.expensespecialtbody_FConSmDate);
			listItemView.ep_fConSmType=(TextView) convertView.findViewById(R.id.expensespecialtbody_FConSmType);		
			listItemView.ep_fAmount=(TextView) convertView.findViewById(R.id.expensespecialtbody_FAmount);
			listItemView.ep_fProjectName=(TextView) convertView.findViewById(R.id.expensespecialtbody_FProjectName);
			listItemView.ep_fClientName=(TextView) convertView.findViewById(R.id.expensespecialtbody_FClientName);
			listItemView.ep_fUse=(TextView) convertView.findViewById(R.id.expensespecialtbody_FUse);
			
			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			listItemView=(ExpenseSpecialTBodyView) convertView.getTag();
		}
		// ����position,�Ӽ��ϻ�ȡһ������
		ExpenseSpecialTBodyInfo eTBodyInfo=listItems.get(position);
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
