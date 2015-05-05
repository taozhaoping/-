package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketBidInfo;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName MarketBidAdapter
 * @Description ���������б���������
 * @author 21291
 * @date 2015��1��27�� ����3:51:22
 */
public class MarketBidAdapter extends MyBaseAdapter<MarketBidInfo> {
	private DbManager mDbHelper;
	private MarketBidHistoryInfo marketBidHistory;				//��ѯ��ʷ��
	private String fItemNumber;									//Ա����	
	
	public MarketBidAdapter(Context context,List<MarketBidInfo> data, int resource,DbManager mDbHelper,MarketBidHistoryInfo marketBidHistory,String fItemNumber) {
		super(context, data, resource);
		this.mDbHelper=mDbHelper;
		this.marketBidHistory=marketBidHistory;
		this.fItemNumber=fItemNumber;
	}

	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<MarketBidInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class MarketBidViewHolder{
		public RelativeLayout mb_FRelativeLayout;
		public TextView mb_FCustomerName;
		public TextView mb_FBidCode;
		public TextView mb_FNodeName_Label;
		public TextView mb_FNodeName;
		public TextView mb_FTasker_Label;
		public TextView mb_FTasker;
		public ImageButton mb_FImageButton;
	}
	private MarketBidViewHolder mBidViewHolder=null;  //�Զ�����ͼ
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			mBidViewHolder=new MarketBidViewHolder();
			
			//��ȡ�ؼ�����
			mBidViewHolder.mb_FRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.market_bid_search_item);
			mBidViewHolder.mb_FCustomerName=(TextView)convertView.findViewById(R.id.market_bid_FCustomerName);
			mBidViewHolder.mb_FBidCode=(TextView)convertView.findViewById(R.id.market_bid_FBidCode);
			mBidViewHolder.mb_FNodeName_Label=(TextView)convertView.findViewById(R.id.market_bid_NodeName_Label);
			mBidViewHolder.mb_FNodeName=(TextView)convertView.findViewById(R.id.market_bid_FNodeName);
			mBidViewHolder.mb_FTasker_Label=(TextView)convertView.findViewById(R.id.market_bid_Tasker_Label);
			mBidViewHolder.mb_FTasker=(TextView)convertView.findViewById(R.id.market_bid_FTasker);
			mBidViewHolder.mb_FImageButton=(ImageButton)convertView.findViewById(R.id.market_bid_FImageButton);
			
			//���ÿؼ�����convertView
			convertView.setTag(mBidViewHolder);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			mBidViewHolder=(MarketBidViewHolder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final MarketBidInfo mInfo=listItems.get(position);
		mBidViewHolder.mb_FRelativeLayout.setTag(mInfo);
		if("-1".equals(mInfo.getFBidCode())){
			mBidViewHolder.mb_FBidCode.setVisibility(View.GONE);
			mBidViewHolder.mb_FNodeName_Label.setVisibility(View.GONE);
			mBidViewHolder.mb_FNodeName.setVisibility(View.GONE);
			mBidViewHolder.mb_FTasker_Label.setVisibility(View.GONE);
			mBidViewHolder.mb_FTasker.setVisibility(View.GONE);
			mBidViewHolder.mb_FImageButton.setVisibility(View.GONE);
		}
		else {
			mBidViewHolder.mb_FBidCode.setVisibility(View.VISIBLE);
			mBidViewHolder.mb_FNodeName_Label.setVisibility(View.VISIBLE);
			mBidViewHolder.mb_FNodeName.setVisibility(View.VISIBLE);
			mBidViewHolder.mb_FTasker_Label.setVisibility(View.VISIBLE);
			mBidViewHolder.mb_FTasker.setVisibility(View.VISIBLE);
			mBidViewHolder.mb_FImageButton.setVisibility(View.VISIBLE);
		}
		
		mBidViewHolder.mb_FCustomerName.setText(mInfo.getFCustomerName());
		mBidViewHolder.mb_FBidCode.setText(mInfo.getFBidCode());
		mBidViewHolder.mb_FNodeName.setText(mInfo.getFNodeName());
		mBidViewHolder.mb_FTasker.setText(mInfo.getFTasker());
		mBidViewHolder.mb_FImageButton.setOnClickListener(new ImageBtnListener(mInfo));
		return convertView;
	}
	
	/**
	 * @ClassName ImageBtnListener
	 * @Description ��Ӱ�ť���������
	 * @author 21291
	 * @date 2014��10��23�� ����2:50:49
	 */
	private class ImageBtnListener implements OnClickListener {
		private MarketBidInfo bidInfo;
		public ImageBtnListener(MarketBidInfo bidInfo) {
			this.bidInfo=bidInfo;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==mBidViewHolder.mb_FImageButton.getId()){  //��ť���
				marketBidHistory.setFBidCode(bidInfo.getFBidCode());
				marketBidHistory.setFCustomerName(bidInfo.getFCustomerName());
				mDbHelper.insertMarketBidSearch(marketBidHistory); //���������¼���������ݿ���		
				UIHelper.showMarketWorkflow(context, fItemNumber, bidInfo.getFSystemType(), bidInfo.getFClassTypeID(), bidInfo.getFBillID(),"bidflowquery");
			}
		}
	}

}
