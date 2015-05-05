package com.dahuatech.app.ui.expense.flow;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseFlowItemAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.expense.FlowSearchParamInfo;
import com.dahuatech.app.business.ExpenseFlowItemBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName ExpenseClientSearchActivity
 * @Description ��ˮ�ͻ��б�ҳ��
 * @author 21291
 * @date 2014��9��1�� ����5:13:49
 */
public class ExpenseClientSearchListActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener, OnRefreshListener<ListView>{
	
	private PullToRefreshListView mPullRefreshListView; 		//�б�
	private ExpenseFlowItemAdapter mAdapter;   					//��������
	private List<ExpenseFlowItemInfo> eArrayList;				//����Դ����
	private ExpenseFlowItemInfo eInfo;							//����ʵ��		 
	private List<String> mQueryList;			  				//��ѯ�ͻ���ʷ��¼����
	private DbManager mDbHelper;						        //���ݿ������
	private ExpenseFlowItemBusiness eBusiness;					//ҵ���߼���
	private ProgressDialog dialog;      						//������
	
	private static final String[] COLUMNS = {  					//���ʹ���и�ʽ
          BaseColumns._ID,
          SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;							//��ʷ��¼����Դ
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  							//�����ؼ�
	private AppContext appContext; 								//ȫ��Context
	
	private String fItemNumber,fQueryStr;						//Ա���š���ѯ�ַ���
	private String serviceUrl;  								//�����ַ
	private int fPageIndex=1;									//ҳ��
	private final static int fPageSize=15;						//ҳ��С

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.expense_flowclient_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWSEARCHACTIVITY;
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initListData(); //��ʼ����Ϣ
		new getClientLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: ��ʼ��׼����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��2�� ����10:08:31
	*/
	private void initListData(){
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.expense_flowclient_search_pullToRefreshListView);
		mQueryList= new ArrayList<String>();
		eArrayList=new ArrayList<ExpenseFlowItemInfo>();
		fQueryStr="";
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseClientSearchListActivity.this);
		eBusiness= (ExpenseFlowItemBusiness)factoryBusiness.getInstance("ExpenseFlowItemBusiness",serviceUrl);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_search, menu);	
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
	    setupSearchView(searchItem);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				setResult(RESULT_OK, new Intent());
				finish();
				return true;		
		}
		return super.onOptionsItemSelected(item);
	}

	/** 
	* @Title: setupSearchView 
	* @Description: ���������ؼ�
	* @param @param searchItem     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��1�� ����2:38:30
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.expense_flow_client_list_search));
	    mSearchView.setOnQueryTextListener(this);
	    mSearchView.setOnSuggestionListener(this);
	}
	
	@Override
	public boolean onSuggestionSelect(int position) {	
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
		fQueryStr = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
		fPageIndex=1;
		eArrayList.clear();
		new getClientSearchAsync().execute(fPageIndex);
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		fPageIndex=1;
		eArrayList.clear();
		new getClientSearchAsync().execute(fPageIndex);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showSuggestions(newText);
		return false;
	}
	
	/** 
	* @Title: showSuggestions 
	* @Description: ��ʾ������ʷ��¼
	* @param @param queryStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��2�� ����3:02:41
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryClientList(queryStr);
			if(mQueryList.size()>0){  //˵���пͻ�������ʷ��¼
				matrixCursor = new MatrixCursor(COLUMNS);
		        int i=1;
		        for (String item : mQueryList) {
		        	matrixCursor.addRow(new String[]{String.valueOf(i), item});
		            i++;
		        }
		        mSuggestionsAdapter = new SuggestionsAdapter(getSupportActionBar().getThemedContext(), matrixCursor);
			}
			else {
				mSuggestionsAdapter=null;
			}
			mSearchView.setSuggestionsAdapter(mSuggestionsAdapter);
		}	
	}
	
	/**
	 * @ClassName getClientLocalAsync
	 * @Description ��ȡ���ؿͻ���ʷ��¼����
	 * @author 21291
	 * @date 2014��9��2�� ����5:18:58
	 */
	private class getClientLocalAsync extends AsyncTask<Void,Void,List<ExpenseFlowItemInfo>>{

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Void... params) {
			return getClientLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			super.onPostExecute(result);
			renderClientLocalListView(result);
		}	
	}
	
	/** 
	* @Title: getLocalByPost 
	* @Description: ��ȡ���ؿͻ���ʷ��¼ʵ�弯��
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��2�� ����5:21:21
	*/
	private  List<ExpenseFlowItemInfo> getClientLocalByPost(){
		List<ExpenseFlowItemInfo> list=mDbHelper.queryLocalList("client");
		int length=list.size();
		if(length > 0){
			ExpenseFlowItemInfo eInfo=new ExpenseFlowItemInfo();
			eInfo.setFId("");
			eInfo.setFItemName(getResources().getString(R.string.history_record_search_clear));
			list.add(length,eInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderClientLocalListView 
	* @Description: ��ʼ�����ؿͻ���ʷ��¼�б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��2�� ����5:22:27
	*/
	private void renderClientLocalListView(final List<ExpenseFlowItemInfo> listData){
		eArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new ExpenseFlowItemAdapter(ExpenseClientSearchListActivity.this,eArrayList,R.layout.expense_flowsearch_layout);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
				mAdapter.setSelectItem(position-1);
				mAdapter.notifyDataSetInvalidated(); //����UI����
				
				eInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					eInfo=(ExpenseFlowItemInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowsearch_layout);
					eInfo=(ExpenseFlowItemInfo)reLayout.getTag();
				}
				if(eInfo==null){
					return;	
				}
				if(StringUtils.isEmpty(eInfo.getFId())){	//���������¼
					mDbHelper.deleteClientSearchAll();
					eArrayList.clear();
					mAdapter.refreshView();
					UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getResources().getString(R.string.history_record_clear_success));
				}
				else {
					mDbHelper.closeSqlLite();
					Intent intent = new Intent();
					intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID, eInfo.getFId());
					intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME, eInfo.getFItemName());	
					setResult(RESULT_OK, intent);
					finish();
				}	
			}
		});
	}
	
	/**
	 * @ClassName getClientSearchAsync
	 * @Description �첽��ȡ�ͻ�������ʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��9��1�� ����7:53:46
	 */
	private class getClientSearchAsync extends AsyncTask<Integer, Void, List<ExpenseFlowItemInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Integer... params) {
			return getClientListByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			super.onPostExecute(result);
			renderClientListView(result);
			dialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡ�ͻ�������ʵ�弯��ҵ���߼�����
	* @param @param pageIndex
	* @param @param fQueryStr
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��1�� ����7:58:50
	*/
	private  List<ExpenseFlowItemInfo> getClientListByPost(final int pageIndex){
		FlowSearchParamInfo fSearchParamInfo=FlowSearchParamInfo.getFlowSearchParamInfo();
		fSearchParamInfo.setFItemNumber(fItemNumber);
		fSearchParamInfo.setFQueryItem(fQueryStr);
		fSearchParamInfo.setfQueryType("client");
		fSearchParamInfo.setFPageIndex(pageIndex);
		fSearchParamInfo.setFPageSize(fPageSize);
		eBusiness.setServiceUrl(serviceUrl);
		return eBusiness.getExpenseFlowItemList(fSearchParamInfo);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ���ͻ��������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��1�� ����8:03:50
	*/
	private void renderClientListView(final List<ExpenseFlowItemInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getString(R.string.expense_flow_client_list_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		eArrayList.addAll(listData);
		mAdapter=new ExpenseFlowItemAdapter(ExpenseClientSearchListActivity.this,eArrayList,R.layout.expense_flowsearch_layout);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
				mAdapter.setSelectItem(position-1);
				mAdapter.notifyDataSetInvalidated(); //����UI����
				
				eInfo=null;		
				if(view instanceof RelativeLayout){  //˵������LinearLayout������
					eInfo=(ExpenseFlowItemInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowsearch_layout);
					eInfo=(ExpenseFlowItemInfo)reLayout.getTag();
				}
				if(eInfo==null){
					return;	
				}
				mDbHelper.insertClientSearch(eInfo);  //���������¼���������ݿ���
				Intent intent = new Intent();
				intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID, eInfo.getFId());
				intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME, eInfo.getFItemName());	
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		//�������ظ���
		mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy().setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label)); 
        onPullUpListView();
	}
	
	/** 
	* @Title: onPullUpListView 
	* @Description:  �������ظ��� �����¼���10������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��2�� ����9:55:33
	*/
	public void onPullUpListView(){
		fPageIndex++;   //��ҳ
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��9��2�� ����10:05:04
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<ExpenseFlowItemInfo>>{

		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getClientListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getString(R.string.expense_flow_client_list_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			eArrayList.addAll(result);
			mAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}
		setResult(RESULT_OK, new Intent());
		finish();
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
