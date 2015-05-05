package com.dahuatech.app.ui.market;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MarketContractAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractInfo;
import com.dahuatech.app.bean.market.MarketSearchParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MarketBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MarketContractSearchActivity
 * @Description 合同查询页面
 * @author 21291
 * @date 2015年1月26日 下午1:50:45
 */
public class MarketContractSearchActivity extends MenuActivity implements OnRefreshListener<ListView> {
	private EditText searchText;    								//搜索文本框
	private ImageButton btnSearch; 			    					//按钮
	private PullToRefreshListView mPullRefreshListView; 			//列表控件对象

	private List<MarketContractInfo> mArrayList;					//数据源
	private DbManager mDbHelper;									//数据库管理类
	private MarketContractAdapter mAdapter;							//适配器
	private MarketBusiness marketBusiness;							//业务逻辑类
	private MarketContractInfo marketContractInfo;					//具体业务类
	private MarketContractHistoryInfo marketContractHistory;    	//查询历史类
	private ProgressDialog dialog;  								//进程框
	private String fItemNumber; 									//员工号
	
	private String serviceUrl,localUrl;  							//服务地址,本地地址
	private AppContext appContext; 									//全局Context
	
	private String fQueryText;										//查询字符串
	private int fPageIndex=1;										//页数
	private static final int fPageSize=10;							//页大小
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		
		setContentView(R.layout.market_contract_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_GETMARKETCONTRACTACTIVITY;
		localUrl=AppUrl.URL_API_HOST_ANDROID_GETMARKETCONTRACTHISTORYACTIVITY;
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		initView();
		setOnListener();
		new getLocalAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月27日 上午10:35:33
	*/
	private void initView(){
		searchText=(EditText) findViewById(R.id.market_contract_searchEditText);  //搜索文本		
		searchText.setFocusable(true);
		btnSearch=(ImageButton)findViewById(R.id.market_contract_searchImageButton); 
	    mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.market_contract_pullToRefreshListView); 
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MarketContractSearchActivity.this);
		marketBusiness=(MarketBusiness)factoryBusiness.getInstance("MarketBusiness",""); 
	
		mArrayList=new ArrayList<MarketContractInfo>();
		fQueryText="";
		marketContractHistory=MarketContractHistoryInfo.getMarketContractHistoryInfo();
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: 监听视图事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月27日 上午11:31:01
	*/
	private void setOnListener(){
		btnSearch.setOnClickListener(new OnClickListener() {  //选择项目
			
			@Override
			public void onClick(View v) {
				//隐藏软件键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
				
				fQueryText=searchText.getText().toString();
				if(StringUtils.isEmpty(fQueryText)){
					UIHelper.ToastMessage(MarketContractSearchActivity.this, getResources().getString(R.string.market_contract_search_empty));
					return;
				}
				mArrayList.clear();
				searchText.setText("");
				searchText.setHint(getResources().getString(R.string.market_contract_search));
				new getServerAsync().execute(fPageIndex);
				sendLogs();  //发送日志信息进行统计
			}
		});
	}
	
	/**
	 * @ClassName getLocalAsync
	 * @Description 异步获取本地合同历史记录集合
	 * @author 21291
	 * @date 2015年1月28日 下午2:37:46
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<MarketContractInfo>>{

		// 主要是完成耗时操作
		@Override
		protected List<MarketContractInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MarketContractInfo> result) {
			super.onPostExecute(result);
			renderLocalList(result);
		}	
	}
	
	/** 
	* @Title: getLocalByPost 
	* @Description: 获取本地合同搜索历史记录实体集合
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月28日 下午2:38:07
	*/
	private List<MarketContractInfo> getLocalByPost(){
		List<MarketContractHistoryInfo> historyList=mDbHelper.queryMarketContractAllList();
		marketBusiness.setServiceUrl(localUrl);
		List<MarketContractInfo> list= marketBusiness.getMarketContractHistoryList(historyList);
		int length=list.size();		
		if(list.size() > 0){
			MarketContractInfo marketContractInfo=new MarketContractInfo();
			marketContractInfo.setFContractCode("-1");
			marketContractInfo.setFCustomerName(getResources().getString(R.string.history_record_search_clear));
			marketContractInfo.setFNodeName("");
			marketContractInfo.setFTasker("");
			list.add(length,marketContractInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderLocalList 
	* @Description: 初始化本地合同搜索历史记录列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月28日 下午2:34:08
	*/
	private void renderLocalList(final List<MarketContractInfo> listData){
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new MarketContractAdapter(MarketContractSearchActivity.this,mArrayList,R.layout.market_contract_item,mDbHelper,marketContractHistory,fItemNumber);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //点击其他无效
					return;	
				}
			
				marketContractInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					marketContractInfo=(MarketContractInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.market_contract_search_item);
					marketContractInfo=(MarketContractInfo)reLayout.getTag();
				}
				if(marketContractInfo==null){
					return;	
				}
				if("-1".equals(marketContractInfo.getFContractCode())){	//清除搜索记录
					mDbHelper.deleteMarketContractSearchAll();
					mArrayList.clear();
					mAdapter.refreshView();
					UIHelper.ToastMessage(MarketContractSearchActivity.this, getString(R.string.history_record_clear_success));
				}
				else {
					UIHelper.showMarketWorkflow(MarketContractSearchActivity.this, fItemNumber, marketContractInfo.getFSystemType(), marketContractInfo.getFClassTypeID(), marketContractInfo.getFBillID(),"contractflowquery");
				}	
			}
		});
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description 异步获取服务器集合
	 * @author 21291
	 * @date 2015年1月27日 下午12:01:24
	 */
	private class getServerAsync extends AsyncTask<Integer,Void,List<MarketContractInfo>>{

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 显示等待框
			dialog.show();
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<MarketContractInfo> doInBackground(Integer... params) {
			return getServerByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MarketContractInfo> result) {
			super.onPostExecute(result);
			renderServerList(result);
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getServerByPost 
	* @Description: 搜索结果处理
	* @param @param pageIndex
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月28日 下午2:46:38
	*/
	private  List<MarketContractInfo> getServerByPost(final int pageIndex){
		MarketSearchParamInfo mParamInfo=MarketSearchParamInfo.getMarketSearchParamInfo();
		mParamInfo.setFItemNumber(fItemNumber);
		mParamInfo.setFQueryText(fQueryText);
		mParamInfo.setFPageIndex(pageIndex);
		mParamInfo.setFPageSize(fPageSize);
		marketBusiness.setServiceUrl(serviceUrl);
		return marketBusiness.getMarketContractList(mParamInfo);
	}
	
	/** 
	* @Title: renderServerList 
	* @Description: 初始化合同服务器列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月28日 下午2:47:03
	*/
	private void renderServerList(final List<MarketContractInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MarketContractSearchActivity.this, getString(R.string.market_contract_search_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mArrayList.addAll(listData);
		mAdapter=new MarketContractAdapter(MarketContractSearchActivity.this,mArrayList,R.layout.market_contract_item,mDbHelper,marketContractHistory,fItemNumber);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //点击其他无效
					return;	
				}
				
				marketContractInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					marketContractInfo=(MarketContractInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.market_contract_search_item);
					marketContractInfo=(MarketContractInfo)reLayout.getTag();
				}
				if(marketContractInfo==null){
					return;	
				}
				marketContractHistory.setFContractCode(marketContractInfo.getFContractCode());
				marketContractHistory.setFCustomerName(marketContractInfo.getFCustomerName());
				mDbHelper.insertMarketContractSearch(marketContractHistory); //添加搜索记录到本地数据库中		
				UIHelper.showMarketWorkflow(MarketContractSearchActivity.this, fItemNumber, marketContractInfo.getFSystemType(), marketContractInfo.getFClassTypeID(), marketContractInfo.getFBillID(),"contractflowquery");
			}
		});
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		//上拉加载更多
		mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy().setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label)); 
        onPullUpListView();
	}
	
	/** 
	* @Title: onPullUpListView 
	* @Description: 上拉加载更多
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月28日 下午2:49:41
	*/
	public void onPullUpListView(){
		fPageIndex++;   //分页
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2015年1月28日 下午2:50:17
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MarketContractInfo>>{

		@Override
		protected List<MarketContractInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getServerByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MarketContractInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MarketContractSearchActivity.this, getString(R.string.market_contract_search_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			mAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 合同查询，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月2日 上午11:12:16
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_market_contract));
		logInfo.setFActionName("contractquery");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MarketContractSearchActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketContractSearchActivity.this);
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
	}
}
