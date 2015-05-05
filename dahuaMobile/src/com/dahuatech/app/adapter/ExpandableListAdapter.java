package com.dahuatech.app.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName ExpandableListAdapter
 * @Description �ҵ���ˮ�б�������
 * @author 21291
 * @date 2014��8��27�� ����1:45:21
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private String[] fTravelLabelArray;			//���÷ѱ�ǩֵ����
	private String[] fSocializeLabelArray;		//����Ӧ��ѱ�ǩֵ����
	private String[] fTravelValueArray;			//���÷�ʵ��ֵ����
	private String[] fSocializeValueArray;		//����Ӧ���ʵ��ֵ����
	
	private Context context;
	private List<String> listDataHeader; //ͷ������
	private HashMap<String, List<ExpenseFlowDetailInfo>> listDataChild; //�Ӽ�����
	private String fItemNumber;			 //Ա����
	private int parentItem = -1;		 		//��ǰѡ�еĸ���
	private int childItem=-1;					//��ǰѡ�е�����

	private ChildViewHolder cViewHolder=null;   //�Զ���������ͼ
	private GroupViewHolder gviewHolder=null;	//�Զ��������ͼ

	public ExpandableListAdapter(Context context, List<String> listDataHeader,HashMap<String, List<ExpenseFlowDetailInfo>> listChildData,String fItemNumber) {
		fTravelLabelArray=context.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=context.getResources().getStringArray(R.array.socialize_labels_array);
		fTravelValueArray=context.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=context.getResources().getStringArray(R.array.socialize_value_array);
		
		this.context = context;
		this.listDataHeader = listDataHeader;
		this.listDataChild = listChildData;
		this.fItemNumber=fItemNumber;
	}
	
	public void setParentItem(int parentItem) {
		this.parentItem = parentItem;
	}

	public void setChildItem(int childItem) {
		this.childItem = childItem;
	}
	
	//ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
		
	//ˢ����ͼ
	public void refreshView(HashMap<String, List<ExpenseFlowDetailInfo>> listItems) {
		this.listDataChild = listItems;
		setParentItem(-1);
		setChildItem(-1);
	    this.notifyDataSetChanged();
	}
	
	//�����Զ���ؼ�
	static class GroupViewHolder{
		TextView gv_title;
		Button gv_btnAdd;
	}
	
	//�����Զ���ؼ�����
	static class ChildViewHolder{
		TextView cv_fExpendType;
		TextView cv_fExpendAddress;
		TextView cv_fExpendAmount;
		TextView cv_fCause;
	}

	public Object getChild(int groupPosition, int childPosititon) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
	}

	//��ȡ���ڸ�������躢����ص�����
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	//������ѡ����ʽ  
	@SuppressLint("InflateParams")
	public View getChildView(int groupPosition, int childPosititon,boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expense_flowlist_item, null);
			cViewHolder=new ChildViewHolder();
			
			//��ȡ�ؼ�����
			cViewHolder.cv_fExpendType=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendType);
			cViewHolder.cv_fExpendAddress=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendAddress);
			cViewHolder.cv_fExpendAmount=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendAmount);
			cViewHolder.cv_fCause=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FCause);
			
			//���ÿؼ�����convertView
			convertView.setTag(cViewHolder);
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			cViewHolder=(ChildViewHolder) convertView.getTag();
		}
		
		if(parentItem==groupPosition){  //ѡ��״̬
			if(childItem==childPosititon){  //ѡ��״̬
				convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
			}
			else{
				convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			}
		}
		else{
			convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final ExpenseFlowDetailInfo childInfo = (ExpenseFlowDetailInfo) getChild(groupPosition, childPosititon);
		//��ʼ������
		cViewHolder.cv_fExpendType.setText(getExpendType(childInfo.getFExpendTypeParent(),childInfo.getFExpendTypeChild()));
		cViewHolder.cv_fExpendAddress.setText(childInfo.getFExpendAddress());
		cViewHolder.cv_fExpendAmount.setText(childInfo.getFExpendAmount());
		cViewHolder.cv_fCause.setText(childInfo.getFCause());
		
		return convertView;
	}
	
	/** 
	* @Title: getExpendType 
	* @Description: ���ݸ��������ID����ȡ��������
	* @param @param fExpendTypeParent
	* @param @param fExpendTypeChild
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����10:14:28
	*/
	private String getExpendType(String fExpendTypeParent,String fExpendTypeChild){
		String expendType="";
		int fChildPosition;
		if("2006".equals(fExpendTypeParent)){  //���÷�
			fChildPosition=Arrays.asList(fTravelValueArray).indexOf(fExpendTypeChild);
			expendType=fTravelLabelArray[fChildPosition];
		}
		else  //����Ӧ���
		{
			fChildPosition=Arrays.asList(fSocializeValueArray).indexOf(fExpendTypeChild);
			expendType=fSocializeLabelArray[fChildPosition];
		}	
		return expendType;
	}

	//������ָ��Group��Child��Ŀ
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
	}

	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}

	public int getGroupCount() {
		return this.listDataHeader.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expense_flowlist_group, null);
            gviewHolder=new GroupViewHolder();
        	
            //��ȡ�ؼ�����
            gviewHolder.gv_title=(TextView) convertView.findViewById(R.id.expense_flowlist_group_title);
            gviewHolder.gv_btnAdd=(Button) convertView.findViewById(R.id.expense_flowlist_group_btnAdd);
            convertView.setTag(gviewHolder);  //���ÿؼ�����convertView
        }
        else {
        	//ȡ����ǰ������tag�е��Զ�����ͼ����
        	gviewHolder=(GroupViewHolder)convertView.getTag();
		}
        //��ʼ������
        String headerTitle = (String) getGroup(groupPosition);
        gviewHolder.gv_title.setTypeface(null, Typeface.BOLD);
        gviewHolder.gv_title.setText(headerTitle);
        gviewHolder.gv_btnAdd.setTag(headerTitle);
        gviewHolder.gv_btnAdd.setOnClickListener(new GroupBtnListener());
        return convertView;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	//���ⰴť���������
	private class GroupBtnListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			if(v.getId()==gviewHolder.gv_btnAdd.getId()){  //˵����������¼�
				String title = (String) v.getTag();
				UIHelper.showExpenseFlowDetail(context,fItemNumber,title);
			}
		}
	}

}
