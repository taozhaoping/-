package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;

/**
 * @ClassName ExpenseFlowItemAdapter
 * @Description �ͻ�/��Ŀ�б���������
 * @author 21291
 * @date 2014��9��1�� ����7:18:50
 */
public class ExpenseFlowItemAdapter extends MyBaseAdapter<ExpenseFlowItemInfo> {

	private int selectItem = -1;					// ��ǰѡ�е�λ��
	private ExpenseFlowItemView eFlowItemView=null;  //�Զ�����ͼ
	
	public ExpenseFlowItemAdapter(Context context,List<ExpenseFlowItemInfo> data, int resource) {
		super(context, data, resource);
	}
	
	public void setSelectItem(int selectItem) {  
	    this.selectItem = selectItem;  
	} 
	
	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<ExpenseFlowItemInfo> listItems) {
		this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class ExpenseFlowItemView{
		public RelativeLayout ef_fRelativeLayout;
		public TextView ef_fId;
		public TextView ef_fItemName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			eFlowItemView=new ExpenseFlowItemView();
			
			//��ȡ�ؼ�����
			eFlowItemView.ef_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.expense_flowsearch_layout);
			eFlowItemView.ef_fId=(TextView)convertView.findViewById(R.id.expense_flowsearch_layout_FId);
			eFlowItemView.ef_fItemName=(TextView)convertView.findViewById(R.id.expense_flowsearch_layout_FItemName);
			
			//���ÿؼ�����convertView
			convertView.setTag(eFlowItemView);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			eFlowItemView=(ExpenseFlowItemView) convertView.getTag();
		}
		
		if(selectItem==position){  //ѡ��״̬
			convertView.findViewById(R.id.expense_flowsearch_layout).setBackgroundColor(context.getResources().getColor(R.color.background_color));
		}
		else{
			convertView.findViewById(R.id.expense_flowsearch_layout).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		ExpenseFlowItemInfo eInfo=listItems.get(position);
		eFlowItemView.ef_fId.setText(String.valueOf(eInfo.getFId()));
		eFlowItemView.ef_fId.setVisibility(View.GONE);
		eFlowItemView.ef_fItemName.setText(eInfo.getFItemName());
		eFlowItemView.ef_fRelativeLayout.setTag(eInfo);
		
		return convertView;
	}

}
