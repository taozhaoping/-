package com.dahuatech.app.ui.task;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpensePrivateTBodyAdapter;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyInfo;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.business.ExpensePrivateTBodyBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class ExpensePrivateTBodyActivity extends MenuActivity {

	private String fSystemType,fBillID,fCostCode;
	private ListView mListView; 	// �б�ؼ�����
	private ProgressDialog dialog;	// ���̿�
	
	private List<ExpensePrivateTBodyInfo> eArrayList;  // ����Դ����
	private ExpensePrivateTBodyAdapter epAdapter;      // ��������
	
	private String serviceUrl;  //�����ַ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenseprivatetbody);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEPRIVATETBODYACTIVITY;	
		//��ȡ���ݲ�����Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fCostCode=extras.getString(AppContext.FEXPENSEPRIVATE_COSTTYPE_KEY);
		}
		
		//����һ���ȴ�������ʾ�û��ȴ�
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//��ʼ�����ݼ�����Ϣ
		eArrayList=new ArrayList<ExpensePrivateTBodyInfo>();
		new ExpensePrivateTBodyListAsync().execute(serviceUrl);		
	}

	/**
	 * @ClassName ExpensePrivateTBodyListAsync
	 * @Description �첽ִ������,��ȡ���ݽ����
	 * @author 21291
	 * @date 2014��5��27�� ����9:30:28
	 */
	private class ExpensePrivateTBodyListAsync extends AsyncTask<String, integer, List<ExpensePrivateTBodyInfo>> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpensePrivateTBodyInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // ���ٵȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<ExpensePrivateTBodyInfo> doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ���ؽ������ݲ���ʼ��
	* @param @param arrayList ����     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��27�� ����9:31:48
	*/
	private void renderListView(List<ExpensePrivateTBodyInfo> arrayList) {
		// ʵ����XListView
		mListView = (ListView) findViewById(R.id.expenseprivatetbody_xListView);
		if(arrayList.size()==0){ //�����ȡ��������
			UIHelper.ToastMessageLong(ExpensePrivateTBodyActivity.this, R.string.expensePrivateTBody_netparseerror);
			mListView.setAdapter(null);
			return;
		}
		eArrayList.addAll(arrayList);	
		// ʹ�����ݼ�����adapter����
		epAdapter = new ExpensePrivateTBodyAdapter(ExpensePrivateTBodyActivity.this, eArrayList,R.layout.expenseprivatetbody_layout);
		// ����XListView��adapter
		mListView.setAdapter(epAdapter);
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ͨ��POST��ʽ��ȡ��������Դ
	* @param @param serviceUrl �����ַ
	* @param @return     
	* @return List<ExpensePrivateTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014��6��20�� ����4:19:25
	*/
	private List<ExpensePrivateTBodyInfo> getListByPost(String serviceUrl) {
		// ����ʵ��
		ExpensePrivateTBodyParam eParam = ExpensePrivateTBodyParam.getExpensePrivateTBodyParam();
		eParam.setFBillID(fBillID);
		eParam.setFSystemType(fSystemType);
		eParam.setFCostCode(fCostCode);
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpensePrivateTBodyActivity.this);
		ExpensePrivateTBodyBusiness eBodyBusiness= (ExpensePrivateTBodyBusiness)factoryBusiness.getInstance("ExpensePrivateTBodyBusiness",serviceUrl);
		return eBodyBusiness.getExpensePrivateTBodyList(eParam);
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
