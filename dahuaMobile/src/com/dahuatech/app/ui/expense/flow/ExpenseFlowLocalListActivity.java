package com.dahuatech.app.ui.expense.flow;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseFlowLocalAdapter;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseFlowLocalListActivity
 * @Description �ҵ���ˮ���ϴ��б�ҳ��
 * @author 21291
 * @date 2014��8��27�� ����12:00:56
 */
public class ExpenseFlowLocalListActivity extends MenuActivity {	
	private ListView mListView; 	// �б�ؼ�����
	private List<ExpenseFlowDetailInfo> eArrayList;		//����Դ
	private List<ExpenseFlowDetailInfo> sArrayList;		//��ѡ�е�item��
	private List<Integer> localRowIdList;				//��ѡ�е�item����������뼯��
	
	private ExpenseFlowLocalAdapter mAdapter;   		//����������
	private	ExpenseFlowDetailInfo eDetailInfo;			//����ʵ��
	private DbManager mDbHelper;				//���ݿ������
	private ExpenseBusiness eBusiness;					//ҵ���߼���
	private ProgressDialog dialog;      				//������
	private ProgressDialog uploadDialog;      			//�ϴ����񵯳���
	private Button btnBatchUpload,btnBatchDelete;       //������ť
	
	private String fItemNumber;  	//Ա����
	private String serviceUrl;   	//�ϴ���������ַ
	private AppContext appContext; 	//ȫ��Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		setContentView(R.layout.expense_flowlocal_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWUPLOADACTIVITY;	
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		//���ݼ��ص�����
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//�����ϴ�������
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_uploading));
		uploadDialog.setCancelable(false);
		
		initListData();
		new getLocalAsync().execute();
		setButtonListener();
	}
	
	/** 
	* @Title: initListData 
	* @Description: ��ʼ������Դ������Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����10:48:12
	*/
	private void initListData(){
		//ҵ���߼���ʵ����
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowLocalListActivity.this);
		eBusiness= (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
		
		btnBatchUpload=(Button)findViewById(R.id.expense_flowlocal_list_batchUpload);
		btnBatchDelete=(Button)findViewById(R.id.expense_flowlocal_list_batchDelete);
		mListView=(ListView)findViewById(R.id.expense_flowlocal_list);
		eArrayList=new ArrayList<ExpenseFlowDetailInfo>();
	}
	
	/**
	 * @ClassName getLocalAsync
	 * @Description �첽��ȡ���ش��ϴ��б���
	 * @author 21291
	 * @date 2014��9��9�� ����10:55:08
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<ExpenseFlowDetailInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); 	// ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<ExpenseFlowDetailInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpenseFlowDetailInfo> result) {
			super.onPostExecute(result);
			renderLocalListView(result);	
			dialog.dismiss(); 	// ���ٵȴ���
		}	
	}
		
	/** 
	* @Title: getLocalByPost 
	* @Description: ��ȡ���ش��ϴ��б���
	* @param @return     
	* @return List<ExpenseFlowDetailInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����10:56:38
	*/
	private List<ExpenseFlowDetailInfo> getLocalByPost(){
		return mDbHelper.queryFlowDetailList();
	}
	
	/** 
	* @Title: renderLocalListView 
	* @Description: ���ر��ش��ϴ��б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����11:04:23
	*/
	private void renderLocalListView(final List<ExpenseFlowDetailInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getString(R.string.expense_flow_local_list_netparseerror),3000);
		}
		eArrayList.addAll(listData);
		mAdapter=new ExpenseFlowLocalAdapter(ExpenseFlowLocalListActivity.this,eArrayList,R.layout.expense_flowlocal_layout);
		mListView.setAdapter(mAdapter);
		mListView.setItemsCanFocus(true);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {		
				mAdapter.setSelectItem(position);
				mAdapter.notifyDataSetInvalidated();
				
				eDetailInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					eDetailInfo=(ExpenseFlowDetailInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowlocal_list_item);
					eDetailInfo=(ExpenseFlowDetailInfo)reLayout.getTag();
				}
				if(eDetailInfo==null){
					return;	
				}
				
				UIHelper.showExpenseFlowDetail(ExpenseFlowLocalListActivity.this,fItemNumber,"local",eDetailInfo);
			}
		});
	}
	
	/** 
	* @Title: setButtonListener 
	* @Description: ���ð�ť����¼�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����11:25:26
	*/
	private void setButtonListener(){
		btnBatchUpload.setOnClickListener(new OnClickListener() {  //�����ϴ�
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				uploadSelected();
				sArrayList=getSelectedList();
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_noselect));
				}
				else{
					localRowIdList=new ArrayList<Integer>();
					for (ExpenseFlowDetailInfo item : sArrayList) {
						localRowIdList.add(item.getFLocalId());
					}
					new localUploadAsync().execute(sArrayList);
				}
			}
		});
		
		btnBatchDelete.setOnClickListener(new OnClickListener() {  //����ɾ��
			
			@Override
			public void onClick(View v) {
				deleteSelected();
				sArrayList=getSelectedList();
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_noselect));
				}
				else{
					localRowIdList=new ArrayList<Integer>();
					for (ExpenseFlowDetailInfo item : sArrayList) {
						localRowIdList.add(item.getFLocalId());
					}
					if(mDbHelper.batchDeleteFlowDetail(localRowIdList)){
						UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_success));
					}
					else{
						UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_failure));
					}		
					// �ӳ�2��ˢ��ҳ��
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	uploadSelected();
			            	eArrayList.clear();
							new getLocalAsync().execute();   
			            }
			        }, 2000);	
				}	
			}
		});
	}
	
	/** 
	* @Title: uploadSelected 
	* @Description: �����ϴ��б�ѡ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����1:53:07
	*/
	private void uploadSelected(){
		btnBatchUpload.setBackgroundResource (R.drawable.tabs_default_left_active_white);
		btnBatchUpload.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_left_active_white);
		
		btnBatchDelete.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnBatchDelete.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_right_white);
	}
	
	/** 
	* @Title: deleteSelected 
	* @Description: ����ɾ��ѡ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����1:54:22
	*/
	private void deleteSelected(){
		btnBatchUpload.setBackgroundResource (R.drawable.tabs_default_left_white);
		btnBatchUpload.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_left_white);
		
		btnBatchDelete.setBackgroundResource (R.drawable.tabs_default_right_active_white);
		btnBatchDelete.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_right_active_white);
	}
	
	/** 
	* @Title: getSelectedList 
	* @Description: ���ر�ѡ�е�item��
	* @param @return     
	* @return List<ExpenseFlowDetailInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����11:29:48
	*/
	private List<ExpenseFlowDetailInfo> getSelectedList(){
		List<ExpenseFlowDetailInfo> selectList=new ArrayList<ExpenseFlowDetailInfo>();
		for (int i = 0; i < eArrayList.size(); i++) {
			if(mAdapter.getIsSelected().get(i)){  //˵��ѡ����
				selectList.add(eArrayList.get(i));
			}
		}
		return selectList;
	}
	
	/**
	 * @ClassName localUploadAsync
	 * @Description �ϴ����ݵ�������
	 * @author 21291
	 * @date 2014��9��10�� ����4:07:38
	 */
	private class localUploadAsync extends AsyncTask<List<ExpenseFlowDetailInfo>,Void,ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			uploadDialog.show(); 	// ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(List<ExpenseFlowDetailInfo>... params) {
			return uploadServer(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showUploadResult(result);	
			uploadDialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: uploadServer 
	* @Description: �ϴ���ˮ�����¼��������
	* @param @param uploadList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����4:11:45
	*/
	private ResultMessage uploadServer(List<ExpenseFlowDetailInfo> uploadList){
		return eBusiness.flowBatchUpload(uploadList);
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: �����ϴ���ˮ��¼UI�����
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����4:11:54
	*/
	private void showUploadResult(ResultMessage resultMessage){
		if(resultMessage.isIsSuccess()){  //˵���ϴ��ɹ�	
			mDbHelper.batchUpdateFlag(localRowIdList);
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_success));
		}
		else{
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_failure));
		}	
		// �ӳ�2��ˢ��ҳ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	uploadSelected();
            	eArrayList.clear();
				new getLocalAsync().execute();   
            }
        }, 2000);	
	}
	
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case UIHelper.ACTIVITY_EXPENSEDETAIL_LOCAL:
				if(resultCode==RESULT_OK){
					uploadSelected();
					eArrayList.clear();
					new getLocalAsync().execute();
				}			
				break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(mAdapter!=null && eArrayList.size() > 0){
			mAdapter.refreshView(eArrayList);
		}
	}
}
