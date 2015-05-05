package com.dahuatech.app.ui.expense.main;

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
 * @ClassName ExpenseMainActivity
 * @Description ����ģ����ҳ
 * @author 21291
 * @date 2014��8��21�� ����3:13:50
 */
public class ExpenseMainActivity extends MenuActivity {
	private static ExpenseMainActivity mInstance;
	
	private Button btnExpenseTaxi,btnExpenseFlow;
	private String fItemNumber;  //Ա����
	private AppContext appContext; //ȫ��Context
	
	public static ExpenseMainActivity getInstance(){
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.expense_main);
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
		
		//�򳵱���	
		btnExpenseTaxi=(Button) findViewById(R.id.expense_main_taxi);
		btnExpenseTaxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseTaxi(ExpenseMainActivity.this,fItemNumber);
			}
		});
		
		//�ҵ���ˮ
		btnExpenseFlow=(Button) findViewById(R.id.expense_main_flow);
		btnExpenseFlow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseFlowList(ExpenseMainActivity.this,fItemNumber);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpenseMainActivity.this);
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
