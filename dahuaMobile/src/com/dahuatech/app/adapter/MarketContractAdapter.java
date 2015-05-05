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
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractInfo;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName MarketContractAdapter
 * @Description ��ͬ�����б���������
 * @author 21291
 * @date 2015��1��28�� ����1:54:48
 */
public class MarketContractAdapter extends MyBaseAdapter<MarketContractInfo> {
	private DbManager mDbHelper;
	private MarketContractHistoryInfo marketContractHistory;    	//��ѯ��ʷ��
	private String fItemNumber;										//Ա����	
	
	public MarketContractAdapter(Context context,List<MarketContractInfo> data, int resource,DbManager mDbHelper,MarketContractHistoryInfo marketContractHistory,String fItemNumber) {
		super(context, data, resource);
		this.mDbHelper=mDbHelper;
		this.marketContractHistory=marketContractHistory;
		this.fItemNumber=fItemNumber;
	}

	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<MarketContractInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class MarketContractViewHolder{
		public RelativeLayout mc_FRelativeLayout;
		public TextView mc_FCustomerName;
		public TextView mc_FContractCode;
		public TextView mc_FNodeName_Label;
		public TextView mc_FNodeName;
		public TextView mc_FTasker_Label;
		public TextView mc_FTasker;
		public ImageButton mc_FImageButton;
	}
	private MarketContractViewHolder mContractViewHolder=null;  //�Զ�����ͼ
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			mContractViewHolder=new MarketContractViewHolder();
			
			//��ȡ�ؼ�����
			mContractViewHolder.mc_FRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.market_contract_search_item);
			mContractViewHolder.mc_FCustomerName=(TextView)convertView.findViewById(R.id.market_contract_FCustomerName);
			mContractViewHolder.mc_FContractCode=(TextView)convertView.findViewById(R.id.market_contract_FContractCode);
			mContractViewHolder.mc_FNodeName_Label=(TextView)convertView.findViewById(R.id.market_contract_NodeName_Label);
			mContractViewHolder.mc_FNodeName=(TextView)convertView.findViewById(R.id.market_contract_FNodeName);
			mContractViewHolder.mc_FTasker_Label=(TextView)convertView.findViewById(R.id.market_contract_Tasker_Label);
			mContractViewHolder.mc_FTasker=(TextView)convertView.findViewById(R.id.market_contract_FTasker);
			mContractViewHolder.mc_FImageButton=(ImageButton)convertView.findViewById(R.id.market_contract_FImageButton);
			
			//���ÿؼ�����convertView
			convertView.setTag(mContractViewHolder);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			mContractViewHolder=(MarketContractViewHolder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final MarketContractInfo mInfo=listItems.get(position);
		mContractViewHolder.mc_FRelativeLayout.setTag(mInfo);
		if("-1".equals(mInfo.getFContractCode())){
			mContractViewHolder.mc_FContractCode.setVisibility(View.GONE);
			mContractViewHolder.mc_FNodeName_Label.setVisibility(View.GONE);
			mContractViewHolder.mc_FNodeName.setVisibility(View.GONE);
			mContractViewHolder.mc_FTasker_Label.setVisibility(View.GONE);
			mContractViewHolder.mc_FTasker.setVisibility(View.GONE);
			mContractViewHolder.mc_FImageButton.setVisibility(View.GONE);
		}
		else {
			mContractViewHolder.mc_FContractCode.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FNodeName_Label.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FNodeName.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FTasker_Label.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FTasker.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FImageButton.setVisibility(View.VISIBLE);
		}
		
		mContractViewHolder.mc_FCustomerName.setText(mInfo.getFCustomerName());
		mContractViewHolder.mc_FContractCode.setText(mInfo.getFContractCode());
		mContractViewHolder.mc_FNodeName.setText(mInfo.getFNodeName());
		mContractViewHolder.mc_FTasker.setText(mInfo.getFTasker());
		mContractViewHolder.mc_FImageButton.setOnClickListener(new ImageBtnListener(mInfo));
		return convertView;
	}
	
	/**
	 * @ClassName ImageBtnListener
	 * @Description ��Ӱ�ť���������
	 * @author 21291
	 * @date 2014��10��23�� ����2:50:49
	 */
	private class ImageBtnListener implements OnClickListener {
		private MarketContractInfo contractInfo;
		public ImageBtnListener(MarketContractInfo contractInfo) {
			this.contractInfo=contractInfo;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==mContractViewHolder.mc_FImageButton.getId()){  //��ť���
				marketContractHistory.setFContractCode(contractInfo.getFContractCode());
				marketContractHistory.setFCustomerName(contractInfo.getFCustomerName());
				mDbHelper.insertMarketContractSearch(marketContractHistory); //���������¼���������ݿ���		
				UIHelper.showMarketWorkflow(context, fItemNumber, contractInfo.getFSystemType(), contractInfo.getFClassTypeID(), contractInfo.getFBillID(),"contractflowquery");
			}
		}
	}

}
