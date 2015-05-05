package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.market.MarketProductInfo;

/**
 * @ClassName MarketProductAdapter
 * @Description ��Ʒ�����б���������
 * @author 21291
 * @date 2015��1��30�� ����1:13:28
 */
public class MarketProductAdapter extends MyBaseAdapter<MarketProductInfo> {

	public MarketProductAdapter(Context context,List<MarketProductInfo> data, int resource) {
		super(context, data, resource);
	}

	// ˢ����ͼ
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// ˢ����ͼ
	public void refreshView(List<MarketProductInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//�Զ���ؼ�����
	static class MarketProductViewHolder{
		public RelativeLayout mp_FRelativeLayout;
		public TextView mp_FModel;
		public TextView mp_FName;
		public TextView mp_FFirstLine;
		public TextView mp_FSecLine;
		public TextView mp_FStandPrice;
	}
	private MarketProductViewHolder mProductViewHolder=null;  //�Զ�����ͼ
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// ��ȡitem����ļ�����ͼ
			convertView=listContainer.inflate(this.itemViewResource, null);
			mProductViewHolder=new MarketProductViewHolder();
			
			//��ȡ�ؼ�����
			mProductViewHolder.mp_FRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.market_product_search_layout);
			mProductViewHolder.mp_FModel=(TextView)convertView.findViewById(R.id.market_product_search_FModel);
			mProductViewHolder.mp_FName=(TextView)convertView.findViewById(R.id.market_product_search_FName);
			mProductViewHolder.mp_FFirstLine=(TextView)convertView.findViewById(R.id.market_product_search_FFirstLine);
			mProductViewHolder.mp_FSecLine=(TextView)convertView.findViewById(R.id.market_product_search_FSecLine);
			mProductViewHolder.mp_FStandPrice=(TextView)convertView.findViewById(R.id.market_product_search_FStandPrice);

			//���ÿؼ�����convertView
			convertView.setTag(mProductViewHolder);	
		}
		else {
			//ȡ����ǰ������tag�е��Զ�����ͼ����
			mProductViewHolder=(MarketProductViewHolder) convertView.getTag();
		}
		
		// ����position,�Ӽ��ϻ�ȡһ������
		final MarketProductInfo mInfo=listItems.get(position);
		mProductViewHolder.mp_FRelativeLayout.setTag(mInfo);
		if("-1".equals(mInfo.getFModel())){
			mProductViewHolder.mp_FName.setVisibility(View.GONE);
			mProductViewHolder.mp_FFirstLine.setVisibility(View.GONE);
			mProductViewHolder.mp_FSecLine.setVisibility(View.GONE);
			mProductViewHolder.mp_FStandPrice.setVisibility(View.GONE);
		}
		else {
			mProductViewHolder.mp_FName.setVisibility(View.VISIBLE);
			mProductViewHolder.mp_FFirstLine.setVisibility(View.VISIBLE);
			mProductViewHolder.mp_FSecLine.setVisibility(View.VISIBLE);
			mProductViewHolder.mp_FStandPrice.setVisibility(View.VISIBLE);
		}
		
		mProductViewHolder.mp_FModel.setText(mInfo.getFModel());
		mProductViewHolder.mp_FName.setText(mInfo.getFName());
		mProductViewHolder.mp_FFirstLine.setText(mInfo.getFFirstLine());
		mProductViewHolder.mp_FSecLine.setText(mInfo.getFSecLine());
		mProductViewHolder.mp_FStandPrice.setText(mInfo.getFStandPrice());
		return convertView;
	}
}
