package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.meeting.MeetingListInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName MeetingListAdapter
 * @Description �����б���������
 * @author 21291
 * @date 2014��9��11�� ����11:50:56
 */
public class MeetingListAdapter extends MyBaseAdapter<MeetingListInfo> {

	private int selectItem = -1;	// ��ǰѡ�е�λ��
	private MeetingListView meetingListView = null;

	public MeetingListAdapter(Context context, List<MeetingListInfo> data,int resource) {
		super(context, data, resource);
	}
	
	public void setSelectItem(int selectItem) {  
	    this.selectItem = selectItem;  
	} 
	
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<MeetingListInfo> listItems) {
		this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}

	// �Զ���ؼ�����
	static class MeetingListView {
		public RelativeLayout ml_fRelativeLayout;
		public TextView ml_fMeetingDate;
		public TextView ml_fMeetingStart;
		public TextView ml_fMeetingEnd;
		public TextView ml_fMeetingName;
		public TextView ml_fCreate;
		public ImageButton ml_btnCancle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// ��ȡitem����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			meetingListView = new MeetingListView();

			// ��ȡ�ؼ�����
			meetingListView.ml_fRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.meeting_list_item);
			meetingListView.ml_fMeetingDate = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingDate);
			meetingListView.ml_fMeetingStart = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingStart);
			meetingListView.ml_fMeetingEnd = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingEnd);
			meetingListView.ml_fMeetingName = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingName);
			meetingListView.ml_fCreate = (TextView) convertView.findViewById(R.id.meeting_list_item_fCreate);
			meetingListView.ml_btnCancle = (ImageButton) convertView.findViewById(R.id.meeting_list_item_btnCancle);
			// ���ÿؼ�����convertView
			convertView.setTag(meetingListView);
		} else {
			// ȡ����ǰ������tag�е��Զ�����ͼ����
			meetingListView = (MeetingListView) convertView.getTag();
		}
		
		if(selectItem==position){  //ѡ��״̬
			convertView.findViewById(R.id.meeting_list_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
		}
		else{
			convertView.findViewById(R.id.meeting_list_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}

		// ����position,�Ӽ��ϻ�ȡһ������
		MeetingListInfo mInfo = listItems.get(position);
		meetingListView.ml_fRelativeLayout.setTag(mInfo);
		meetingListView.ml_fMeetingDate.setText(mInfo.getFMeetingDate());
		meetingListView.ml_fMeetingStart.setText(mInfo.getFMeetingStart());
		meetingListView.ml_fMeetingEnd.setText(mInfo.getFMeetingEnd());
		meetingListView.ml_fMeetingName.setText(mInfo.getFMeetingName());
		meetingListView.ml_fCreate.setText(mInfo.getFCreate());

		meetingListView.ml_btnCancle.setTag(mInfo);
		if ("0".equals(mInfo.getFStatus())) { // ˵�������Լ������Ļ��� ֻ��ֻ�� ȡ����ť����ʾ
			meetingListView.ml_btnCancle.setVisibility(View.GONE);
		} else { // ȡ����ť��ʾ
			meetingListView.ml_btnCancle.setVisibility(View.VISIBLE);
			meetingListView.ml_btnCancle.setOnClickListener(new ButtonListener(position));
		}
		return convertView;
	}

	// ��ť���������
	private class ButtonListener implements OnClickListener {
		private int bPos;
		private MeetingBusiness mBusiness; //ҵ���߼���
		
		public ButtonListener(int pos) {
			bPos = pos;
			//��ʼ��ҵ���߼���
			FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(context);
			mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness",AppUrl.URL_API_HOST_ANDROID_CANCLEMEETINGDATA);
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==meetingListView.ml_btnCancle.getId()){  //˵����������¼�
				MeetingListInfo meetingList = (MeetingListInfo) v.getTag();
				new cancleMeetingAsync().execute(meetingList.getFId());		
			}
		}
		
		/**
		 * @ClassName pullUpRefreshAsync
		 * @Description �����첽���ظ���
		 * @author 21291
		 * @date 2014��9��11�� ����4:51:08
		 */
		private class cancleMeetingAsync extends AsyncTask<String, Void, ResultMessage>{

			@Override
			protected ResultMessage doInBackground(String... params) {
				return cancleByPost(params[0]); // ��Ҫ����ɺ�ʱ����
			}

			// ��ɸ���UI����
			@Override
			protected void onPostExecute(ResultMessage result) {
				if(result.isIsSuccess()){  //����ȡ���ɹ�
					listItems.remove(bPos);
					UIHelper.ToastMessage(context, context.getResources().getString(R.string.meeting_cancle_success));
					
					// �ӳ�2��ˢ��ҳ��
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	refreshView();
			            }
			        }, 2000);	
				}
				else{
					UIHelper.ToastMessage(context, context.getResources().getString(R.string.meeting_cancle_failure));
				}
			}
		}
		
		/** 
		* @Title: cancleByPost 
		* @Description: ȡ������
		* @param @param fOrderId ������������
		* @param @return     
		* @return ResultMessage    
		* @throws 
		* @author 21291
		* @date 2014��9��19�� ����5:51:13
		*/
		private ResultMessage cancleByPost(final String fOrderId){
			return mBusiness.removeMeetingListItem(fOrderId);
		}
	}
}
