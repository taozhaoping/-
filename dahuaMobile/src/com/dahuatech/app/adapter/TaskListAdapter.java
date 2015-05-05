package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName TaskListAdapter
 * @Description �����б���������
 * @author 21291
 * @date 2014��4��23�� ����11:08:42
 */
public class TaskListAdapter extends MyBaseAdapter<TaskInfo> {

	private TaskListItemView listItemView=null; // �Զ�����ͼ
	 
	private String fStatus;					// Ĭ�ϱ����¼״̬ 0-������ 1-������
	public TaskListAdapter(Context context, List<TaskInfo> data,int resource,String fStatus) {
		super(context, data, resource);
		this.fStatus=fStatus;
	}

	//�Զ���ؼ�����
	static class TaskListItemView{
		public ImageButton tl_fitemsIcon;
		public TextView tl_fClassTypeName;
		public TextView tl_fTitle;
		public TextView tl_fSendTime;
		public TextView tl_fSender;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡtasklistlayout�����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new TaskListItemView();
			
			//��ȡ�ؼ�����
			listItemView.tl_fitemsIcon=(ImageButton) convertView.findViewById(R.id.tasklistlayout_itemsIcon);
			listItemView.tl_fClassTypeName=(TextView) convertView.findViewById(R.id.tasklistlayout_FClassTypeName);
			listItemView.tl_fTitle=(TextView) convertView.findViewById(R.id.tasklistlayout_FTitle);
			listItemView.tl_fSendTime=(TextView) convertView.findViewById(R.id.tasklistlayout_FSendTime);
			listItemView.tl_fSender=(TextView) convertView.findViewById(R.id.tasklistlayout_FSender);
			
			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			listItemView=(TaskListItemView) convertView.getTag();
		}
		// ����position,�Ӽ��ϻ�ȡһ������
		TaskInfo task=listItems.get(position);
		// �������ز���(ʵ����)
		listItemView.tl_fitemsIcon.setTag(task);
		listItemView.tl_fClassTypeName.setText(task.getFClassTypeName());
		listItemView.tl_fTitle.setText(task.getFTitle());
		listItemView.tl_fSendTime.setText(task.getFSendTime());
		listItemView.tl_fSender.setText(task.getFSender());
		
		listItemView.tl_fitemsIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TaskInfo newTask = (TaskInfo) v.getTag(); //Works fine here	
				UIHelper.showTaskDetail(context, newTask,fStatus);
			}
		});
		
		return convertView;
	}

}
