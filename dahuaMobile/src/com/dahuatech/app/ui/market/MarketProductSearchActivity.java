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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MarketProductAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.market.MarketProductInfo;
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
 * @ClassName MarketProductSearchActivity
 * @Description ��Ʒ��ѯҳ��
 * @author 21291
 * @date 2015��1��30�� ����9:08:44
 */
public class MarketProductSearchActivity extends MenuActivity implements OnRefreshListener<ListView> {
	private EditText searchText;    								//�����ı���
	private ImageButton btnSearch; 			    					//��ť
	private PullToRefreshListView mPullRefreshListView; 			//�б�ؼ�����

	private List<MarketProductInfo> mArrayList;						//����Դ
	private DbManager mDbHelper;									//���ݿ������
	private MarketProductAdapter mAdapter;							//������
	private MarketBusiness marketBusiness;							//ҵ���߼���
	private ProgressDialog dialog;  								//���̿�
	private String fItemNumber; 									//Ա����
	
	private String serviceUrl;  									//�����ַ
	private AppContext appContext; 									//ȫ��Context
	
	private String fQueryText;										//��ѯ�ַ���
	private int fPageIndex=1;										//ҳ��
	private static final int fPageSize=10;							//ҳ��С
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.market_product_search);
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
		
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_GETMARKETPRODUCTACTIVITY;
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		initView();
		setOnListener();
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
		searchText=(EditText) findViewById(R.id.market_product_searchEditText);  //�����ı�		
		searchText.setFocusable(true);
		btnSearch=(ImageButton)findViewById(R.id.market_product_searchImageButton); 
	    mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.market_product_pullToRefreshListView); 

		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MarketProductSearchActivity.this);
		marketBusiness=(MarketBusiness)factoryBusiness.getInstance("MarketBusiness",serviceUrl); 
	
		mArrayList=new ArrayList<MarketProductInfo>();
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
					UIHelper.ToastMessage(MarketProductSearchActivity.this, getResources().getString(R.string.market_product_search_empty));
					return;
				}
				mArrayList.clear();
				searchText.setText("");
				searchText.setHint(getResources().getString(R.string.market_product_search));
				new getServerAsync().execute(fPageIndex);
				sendLogs();  //������־��Ϣ����ͳ��
			}
		});
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description �첽��ȡ����������
	 * @author 21291
	 * @date 2015��1��30�� ����9:26:18
	 */
	private class getServerAsync extends AsyncTask<Integer,Void,List<MarketProductInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ��ʾ�ȴ���
			dialog.show();
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MarketProductInfo> doInBackground(Integer... params) {
			return getServerByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketProductInfo> result) {
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
	* @return List<MarketProductInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��30�� ����9:26:58
	*/
	private  List<MarketProductInfo> getServerByPost(final int pageIndex){
		MarketSearchParamInfo mParamInfo=MarketSearchParamInfo.getMarketSearchParamInfo();
		mParamInfo.setFItemNumber(fItemNumber);
		mParamInfo.setFQueryText(fQueryText);
		mParamInfo.setFPageIndex(pageIndex);
		mParamInfo.setFPageSize(fPageSize);
		return marketBusiness.getMarketProductList(mParamInfo);
	}
	
	/** 
	* @Title: renderServerList 
	* @Description: ��ʼ����ͬ�������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015��1��28�� ����2:47:03
	*/
	private void renderServerList(final List<MarketProductInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MarketProductSearchActivity.this, getString(R.string.market_product_search_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mArrayList.addAll(listData);
		mAdapter=new MarketProductAdapter(MarketProductSearchActivity.this,mArrayList,R.layout.market_product_item);
		mPullRefreshListView.setAdapter(mAdapter);
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
	* @date 2015��1��28�� ����2:49:41
	*/
	public void onPullUpListView(){
		fPageIndex++;   //��ҳ
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2015��1��30�� ����9:28:04
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MarketProductInfo>>{

		@Override
		protected List<MarketProductInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getServerByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MarketProductInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MarketProductSearchActivity.this, getString(R.string.market_product_search_netparseerror),3000);
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
	* @Description: ��ͬ��ѯ��������־��¼��������
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
		logInfo.setFModuleName(getResources().getString(R.string.log_market));
		logInfo.setFActionName("productquery");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MarketProductSearchActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketProductSearchActivity.this);
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
