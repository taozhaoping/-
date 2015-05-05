package com.dahuatech.app.ui.meeting;

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
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MeetingRoomAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.meeting.MeetingInitParamInfo;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingRoomListActivity
 * @Description �ҵĻ��� ����������ҳ��
 * @author 21291
 * @date 2014��9��11�� ����10:42:34
 */
public class MeetingRoomListActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener, OnRefreshListener<ListView> {

	private PullToRefreshListView mPullRefreshListView; 	//�б�
	private MeetingRoomAdapter mAdapter;					//��������
	private List<MeetingRoomInfo> mArrayList;				//����Դ����
	private List<String> mQueryList;			  			//��ѯ��������ʷ��¼����
	private DbManager mDbHelper;					//���ݿ������
	private MeetingBusiness mBusiness;						//ҵ���߼���
	private ProgressDialog dialog;      					//������
	
	private static final String[] COLUMNS = {  				//���ʹ���и�ʽ
        BaseColumns._ID,
        SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;						//��ʷ��¼����Դ
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  						//�����ؼ�
	private AppContext appContext; 							//ȫ��Context
		
	private String fQueryStr;								//��ѯ�ַ���
	private String filterUrl,searchUrl;  					//�����ַ
	private int fPageIndex=1;								//ҳ��
	private final static int fPageSize=10;					//ҳ��С
	private int fRecordCount;								//�ܵļ�¼��
	
	private String fItemNumber; 							//Ա����
	private String fSelectedDate="";						//�Ѿ�ѡ���������
	private String FSelectedStart="";						//�Ѿ�ѡ�������ʼʱ��
	private String FSelectedEnd="";							//�Ѿ�ѡ��������ʱ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.meeting_room_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ�����ַ
		filterUrl=AppUrl.URL_API_HOST_ANDROID_INITMEETINGROOMLISTACTIVITY;	
		searchUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGROOMLISTACTIVITY;	
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fSelectedDate=extras.getString(AppContext.MEETING_DETAIL_SELECTEDDATE);
			FSelectedStart=extras.getString(AppContext.MEETING_DETAIL_SELECTEDSTART);
			FSelectedEnd=extras.getString(AppContext.MEETING_DETAIL_SELECTEDEND);
		}
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initListData();
		new getRoomLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: ��ʼ��׼����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����7:55:45
	*/
	private void initListData(){
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.meeting_room_list_pullToRefreshListView);
		mQueryList= new ArrayList<String>();
		mArrayList=new ArrayList<MeetingRoomInfo>();
		fQueryStr="";
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MeetingRoomListActivity.this);
		mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness","");
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
	* @date 2014��9��12�� ����10:34:28
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.meeting_search_room));
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
		mArrayList.clear();
		new getRoomSearchAsync().execute(fPageIndex);
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		fPageIndex=1;
		mArrayList.clear();
		new getRoomSearchAsync().execute(fPageIndex);
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
	* @date 2014��9��12�� ����12:05:11
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryRoomList(queryStr);
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
	 * @ClassName getRoomLocalAsync
	 * @Description ��ȡ���ػ�������ʷ��¼����
	 * @author 21291
	 * @date 2014��9��12�� ����10:36:22
	 */
	private class getRoomLocalAsync extends AsyncTask<Void,Void,List<MeetingRoomInfo>>{

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MeetingRoomInfo> doInBackground(Void... params) {
			return getRoomLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			super.onPostExecute(result);
			renderRoomLocalListView(result);
		}	
	}
	
	/** 
	* @Title: getRoomLocalByPost 
	* @Description: ��ȡ���ػ�������ʷ��¼ʵ�弯��
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����10:40:09
	*/
	private List<MeetingRoomInfo> getRoomLocalByPost(){
		List<MeetingRoomInfo> list=getFilterRoomLocal(mDbHelper.queryRoomAllList());
		int length=list.size();
		if(list.size() > 0){
			MeetingRoomInfo mRoomInfo=new MeetingRoomInfo();
			mRoomInfo.setFRoomId("-1");
			mRoomInfo.setFRoomName(getResources().getString(R.string.history_record_search_clear));
			mRoomInfo.setFType("1");
			list.add(length,mRoomInfo);
		}
		return list;
	}
	
	/** 
	* @Title: getFilterRoomLocal 
	* @Description: ������ʷ��¼����
	* @param @param localList ��������Դ
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����3:28:01
	*/
	private List<MeetingRoomInfo> getFilterRoomLocal(final List<MeetingRoomInfo> localList){
		List<MeetingRoomInfo> returnList;
		int length=localList.size();
		if(length > 0){  //˵������ʷ��¼
			StringBuffer sbBuffer = new StringBuffer();	
			for (int i = 0; i < length; i++) {
				if(i==length-1){
					sbBuffer.append(localList.get(i).getFRoomId());
				}
				else{
					sbBuffer.append(localList.get(i).getFRoomId()).append(",");
				}
			}
			
			MeetingInitParamInfo mInitParamInfo=MeetingInitParamInfo.getMeetingInitParamInfo();
			mInitParamInfo.setFItemNumber(fItemNumber);
			mInitParamInfo.setFRoomIdS(sbBuffer.toString());	
			mInitParamInfo.setFSelectedDate(fSelectedDate);
			mInitParamInfo.setFSelectedStart(FSelectedStart);
			mInitParamInfo.setFSelectedEnd(FSelectedEnd);	
			
			mBusiness.setServiceUrl(filterUrl);
			returnList=mBusiness.getInitRoomList(mInitParamInfo);
		}
		else {
			returnList=new ArrayList<MeetingRoomInfo>();
		}
		return returnList;
	
	}
	
	/** 
	* @Title: renderRoomLocalListView 
	* @Description: ��ʼ�����ػ�������ʷ��¼�б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����10:43:15
	*/
	private void renderRoomLocalListView(final List<MeetingRoomInfo> listData){
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new MeetingRoomAdapter(MeetingRoomListActivity.this,mArrayList,R.layout.meeting_room_list_item);
		mPullRefreshListView.setAdapter(mAdapter);
	}
	
	/**
	 * @ClassName getRoomSearchAsync
	 * @Description �첽��ȡ�����ҷ�����ʵ�弯��
	 * @author 21291
	 * @date 2014��9��12�� ����11:04:24
	 */
	private class getRoomSearchAsync extends AsyncTask<Integer, Void, List<MeetingRoomInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MeetingRoomInfo> doInBackground(Integer... params) {
			return getRoomListByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			super.onPostExecute(result);
			renderRoomListView(result);
			dialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getRoomListByPost 
	* @Description: ��ȡ�����ҷ�����ʵ�弯��ҵ���߼�����
	* @param @param pageIndex
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����11:08:07
	*/
	private  List<MeetingRoomInfo> getRoomListByPost(final int pageIndex){
		MeetingSearchParamInfo mSearchParamInfo=MeetingSearchParamInfo.getMeetingSearchParamInfo();
		mSearchParamInfo.setFItemNumber(fItemNumber);
		mSearchParamInfo.setFQueryText(fQueryStr);
		mSearchParamInfo.setFPageIndex(pageIndex);
		mSearchParamInfo.setFPageSize(fPageSize);
		mSearchParamInfo.setFSelectedDate(fSelectedDate);
		mSearchParamInfo.setFSelectedStart(FSelectedStart);
		mSearchParamInfo.setFSelectedEnd(FSelectedEnd);
		
		mBusiness.setServiceUrl(searchUrl);
		return mBusiness.getRoomList(mSearchParamInfo);
	}
	
	/** 
	* @Title: renderRoomListView 
	* @Description: ��ʼ�������ҷ������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����11:55:23
	*/
	private void renderRoomListView(final List<MeetingRoomInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingRoomListActivity.this, getString(R.string.meeting_search_room_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount=mArrayList.get(0).getFRecordCount();
		if(mArrayList.size() < fRecordCount){
			mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		}
		else {
			mPullRefreshListView.setMode(Mode.DISABLED);
		}
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mAdapter=new MeetingRoomAdapter(MeetingRoomListActivity.this,mArrayList,R.layout.meeting_room_list_item);
		mPullRefreshListView.setAdapter(mAdapter);
	}
	
	/** 
	* @Title: onFTypeClick 
	* @Description: �б���Ŀ�����¼�
	* @param @param view     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����4:20:50
	*/
	public void onFTypeClick(View view){
		final MeetingRoomInfo mrRoomInfo = (MeetingRoomInfo) view.getTag();
		if("-1".equals(mrRoomInfo.getFRoomId())){  //˵��������������ʷ��¼
			mDbHelper.deleteRoomSearchAll();
			mArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(MeetingRoomListActivity.this, getResources().getString(R.string.history_record_clear_success));
		}
		else{
			mDbHelper.insertRoomSearch(mrRoomInfo); 	//���������¼���������ݿ���
			Intent intent = new Intent();
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_ID, mrRoomInfo.getFRoomId());
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_NAME, mrRoomInfo.getFRoomName());	
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_IP, mrRoomInfo.getFRoomIp());	
			setResult(RESULT_OK, intent);
			finish();
		}
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
	* @Description: �������ظ��� �����¼���10������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����12:01:16
	*/
	public void onPullUpListView(){
		fPageIndex++;   //��ҳ
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��9��12�� ����12:01:44
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MeetingRoomInfo>>{

		@Override
		protected List<MeetingRoomInfo> doInBackground(Integer... params) {
			return getRoomListByPost(params[0]); // ��Ҫ����ɺ�ʱ����
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingRoomListActivity.this, getString(R.string.meeting_search_room_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			fRecordCount=mArrayList.get(0).getFRecordCount();
			if(mArrayList.size() < fRecordCount){
				mPullRefreshListView.setMode(Mode.PULL_FROM_END);
			}
			else {
				mPullRefreshListView.setMode(Mode.DISABLED);
			}
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
