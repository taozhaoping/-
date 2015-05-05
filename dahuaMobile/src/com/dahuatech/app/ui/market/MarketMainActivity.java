package com.dahuatech.app.ui.market;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName MarketMainActivity
 * @Description �ҵ�������ҳ��
 * @author 21291
 * @date 2015��1��26�� ����1:29:36
 */
public class MarketMainActivity extends MenuActivity {
	private static MarketMainActivity mInstance;
	
	private Button btnMarketBid,btnMarketContract,btnMarketProduct;
	private String fItemNumber; 		//Ա����
	private AppContext appContext; 		//ȫ��Context

	public static MarketMainActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mInstance=this;
		setContentView(R.layout.market_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		//���۲�ѯ
		btnMarketBid=(Button) findViewById(R.id.market_main_bid_search);
		btnMarketBid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketBidSearch(MarketMainActivity.this, fItemNumber);
			}
		});
		
		//��ͬ��ѯ
		btnMarketContract=(Button) findViewById(R.id.market_main_contract_search);
		btnMarketContract.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketContractSearch(MarketMainActivity.this, fItemNumber);
			}
		});
		
		//��Ʒ��ѯ
		btnMarketProduct=(Button) findViewById(R.id.market_main_product_search);
		btnMarketProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketProductSearch(MarketMainActivity.this, fItemNumber);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketMainActivity.this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
