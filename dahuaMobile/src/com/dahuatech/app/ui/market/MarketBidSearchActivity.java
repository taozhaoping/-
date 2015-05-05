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
import com.dahuatech.app.adapter.MarketBidAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketBidInfo;
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
 * @ClassName MarketBidSearchActivity
 * @Description ���۲�ѯҳ��
 * @author 21291
 * @date 2015��1��26�� ����1:49:35
 */
public class MarketBidSearchActivity extends MenuActivity implements OnRefreshListener<ListView> {
	private EditText searchText;    							//�����ı���
	private ImageButton btnSearch; 			    				//��ť
	private PullToRefreshListView mPullRefreshListView; 		//�б�ؼ�����

	private List<MarketBidInfo> mArrayList;						//����Դ
	private DbManager mDbHelper;								//���ݿ������
	private MarketBidAdapter mAdapter;							//������
	private MarketBusiness marketBusiness;						//ҵ���߼���
	private MarketBidInfo marketBidInfo;						//����ҵ����
	private MarketBidHistoryInfo marketBidHistory;				//��ѯ��ʷ��
	private ProgressDialog dialog;  							//���̿�
	private String fItemNumber; 								//Ա����
	
	private String serviceUrl,localUrl;  						//�����ַ,���ص�ַ
	private AppContext appContext; 								//ȫ��Context
	
	private String fQueryText;									//��ѯ�ַ���
	private int fPageIndex=1;									//ҳ��
	private static final int fPageSize=10;						//ҳ��С
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.market_bid_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
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
		
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_GETMARKETBIDACTIVITY;
		localUrl=AppUrl.URL_API_HOST_ANDROID_GETMARKETBIDHISTORYACTIVITY;
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		initView();
		setOnListener();
		new getLocalAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��27�� ����10:35:33
	*/
	private void initView(){
		searchText=(EditText) findViewById(R.id.market_bid_searchEditText);  //�����ı�		
		searchText.setFocusable(true);
		btnSearch=(ImageButton)findViewById(R.id.market_bid_searchImageButton); 
	    mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.market_bid_pullToRefreshListView); 
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MarketBidSearchActivity.this);
		marketBusiness=(MarketBusiness)factoryBusiness.getInstance("MarketBusiness",""); 
	
		mArrayList=new ArrayList<MarketBidInfo>();
		marketBidHistory=MarketBidHistoryInfo.getMarketBidHistoryInfo();
		fQueryText="";
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: ������ͼ�¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��27�� ����11:31:01
	*/
	private void setOnListener(){
		btnSearch.setOnClickListener(new OnClickListener() {  //ѡ����Ŀ
			
			@Override
			public void onClick(View v) {
				//�����������
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
				
				fQueryText=searchText.getText().toString();
				if(StringUtils.isEmpty(fQueryText)){
					UIHelper.ToastMessage(MarketBidSearchActivity.this, getResources().getString(R.string.market_bid_search_empty));
					return;
				}
				mArrayList.clear();
				searchText.setText("");
				searchText.setHint(getResources().getString(R.string.market_bid_search));
				new getServerAsync().execute(fPageIndex);
				sendLogs();   //������־��Ϣ����ͳ��
			}
		});
	}
	
	/**
	 * @ClassName getLocalAsync
	 * @Description �첽��ȡ���ر�����ʷ��¼����
	 * @author 21291
	 * @date 2015��1��28�� ����9:32:42
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<MarketBidInfo>>{

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MarketBidInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketBidInfo> result) {
			super.onPostExecute(result);
			renderLocalList(result);
		}	
	}
	
	/** 
	* @Title: getLocalByPost 
	* @Description: ��ȡ���ر���������ʷ��¼ʵ�弯��
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��28�� ����9:33:13
	*/
	private List<MarketBidInfo> getLocalByPost(){
		List<MarketBidHistoryInfo> historyList=mDbHelper.queryMarketBidAllList();
		marketBusiness.setServiceUrl(localUrl);
		List<MarketBidInfo> list=marketBusiness.getMarketBidHistoryList(historyList);
		int length=list.size();
		if(list.size() > 0){
			MarketBidInfo marketBidInfo=new MarketBidInfo();
			marketBidInfo.setFBidCode("-1");
			marketBidInfo.setFCustomerName(getResources().getString(R.string.history_record_search_clear));
			marketBidInfo.setFNodeName("");
			marketBidInfo.setFTasker("");
			list.add(length,marketBidInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderLocalList 
	* @Description: ��ʼ�����ر���������ʷ��¼�б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��28�� ����9:38:53
	*/
	private void renderLocalList(final List<MarketBidInfo> listData){
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new MarketBidAdapter(MarketBidSearchActivity.this,mArrayList,R.layout.market_bid_item,mDbHelper,marketBidHistory,fItemNumber);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
			
				marketBidInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					marketBidInfo=(MarketBidInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.market_bid_search_item);
					marketBidInfo=(MarketBidInfo)reLayout.getTag();
				}
				if(marketBidInfo==null){
					return;	
				}
				if("-1".equals(marketBidInfo.getFBidCode())){	//���������¼
					mDbHelper.deleteMarketBidSearchAll();
					mArrayList.clear();
					mAdapter.refreshView();
					UIHelper.ToastMessage(MarketBidSearchActivity.this, getString(R.string.history_record_clear_success));
				}
				else {
					UIHelper.showMarketWorkflow(MarketBidSearchActivity.this, fItemNumber, marketBidInfo.getFSystemType(), marketBidInfo.getFClassTypeID(), marketBidInfo.getFBillID(),"bidflowquery");
				}	
			}
		});
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description �첽��ȡ����������
	 * @author 21291
	 * @date 2015��1��27�� ����12:01:24
	 */
	private class getServerAsync extends AsyncTask<Integer,Void,List<MarketBidInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ��ʾ�ȴ���
			dialog.show();
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MarketBidInfo> doInBackground(Integer... params) {
			return getServerByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketBidInfo> result) {
			super.onPostExecute(result);
			renderServerList(result);
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getServerByPost 
	* @Description: �����������
	* @param @param pageIndex
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��27�� ����3:43:53
	*/
	private  List<MarketBidInfo> getServerByPost(final int pageIndex){
		MarketSearchParamInfo mParamInfo=MarketSearchParamInfo.getMarketSearchParamInfo();
		mParamInfo.setFItemNumber(fItemNumber);
		mParamInfo.setFQueryText(fQueryText);
		mParamInfo.setFPageIndex(pageIndex);
		mParamInfo.setFPageSize(fPageSize);
		marketBusiness.setServiceUrl(serviceUrl);
		return marketBusiness.getMarketBidList(mParamInfo);
	}
	
	/** 
	* @Title: renderServerList 
	* @Description: ��ʼ�����۷������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����5:13:57
	*/
	private void renderServerList(final List<MarketBidInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MarketBidSearchActivity.this, getString(R.string.market_bid_search_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mArrayList.addAll(listData);
		mAdapter=new MarketBidAdapter(MarketBidSearchActivity.this,mArrayList,R.layout.market_bid_item,mDbHelper,marketBidHistory,fItemNumber);
		mPullRefreshListView.setAdapter(mAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
				
				marketBidInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					marketBidInfo=(MarketBidInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.market_bid_search_item);
					marketBidInfo=(MarketBidInfo)reLayout.getTag();
				}
				if(marketBidInfo==null){
					return;	
				}
				marketBidHistory.setFCustomerName(marketBidInfo.getFCustomerName());
				marketBidHistory.setFBidCode(marketBidInfo.getFBidCode());
				mDbHelper.insertMarketBidSearch(marketBidHistory); //���������¼���������ݿ���			
				UIHelper.showMarketWorkflow(MarketBidSearchActivity.this, fItemNumber, marketBidInfo.getFSystemType(), marketBidInfo.getFClassTypeID(), marketBidInfo.getFBillID(),"bidflowquery");
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
	* @Description: �������ظ���
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��27�� ����4:18:06
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
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MarketBidInfo>>{

		@Override
		protected List<MarketBidInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getServerByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketBidInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MarketBidSearchActivity.this, getString(R.string.market_bid_search_netparseerror),3000);
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
	* @Description: ���۲�ѯ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��2��2�� ����11:12:16
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_market_bid));
		logInfo.setFActionName("bidquery");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MarketBidSearchActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketBidSearchActivity.this);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
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
}
