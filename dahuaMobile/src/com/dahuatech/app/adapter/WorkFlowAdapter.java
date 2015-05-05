package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;

/**
 * @ClassName WorkFlowAdapter
 * @Description ��������������¼��������
 * @author 21291
 * @date 2014��4��25�� ����1:41:39
 */
public class WorkFlowAdapter extends MyBaseAdapter<WorkFlowInfo> {

	private WorkFlowItemView listItemView=null; // �Զ�����ͼ
	
	public WorkFlowAdapter(Context context, List<WorkFlowInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//�Զ���ؼ�����
	static class WorkFlowItemView{
		public TextView wf_fStepFlagName;
		public TextView wf_fUpdateTime;
		public TextView wf_fItemName;
		public TextView wf_fStatusResult;
		public TextView wf_fComment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡworkflowlayout�����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new WorkFlowItemView();
			
			//��ȡ�ؼ�����
			listItemView.wf_fStepFlagName=(TextView) convertView.findViewById(R.id.workflowlayout_FStepFlagName);
			listItemView.wf_fUpdateTime=(TextView) convertView.findViewById(R.id.workflowlayout_FUpdateTime);
			listItemView.wf_fItemName=(TextView) convertView.findViewById(R.id.workflowlayout_FItemName);
			listItemView.wf_fStatusResult=(TextView) convertView.findViewById(R.id.workflowlayout_FStatusResult);
			listItemView.wf_fComment=(TextView)convertView.findViewById(R.id.workflowlayout_FComment);
			
			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			listItemView=(WorkFlowItemView) convertView.getTag();
		}
		// ����position,�Ӽ��ϻ�ȡһ������
		WorkFlowInfo work=listItems.get(position);
		// �����ݰ󶨵�item������
		listItemView.wf_fStepFlagName.setText(work.getFStepFlagName());
		listItemView.wf_fUpdateTime.setText(work.getFUpdateTime());
		listItemView.wf_fItemName.setText(work.getFItemName());
		listItemView.wf_fStatusResult.setText(work.getFStatusResult());
		listItemView.wf_fComment.setText(work.getFComment());
		
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
