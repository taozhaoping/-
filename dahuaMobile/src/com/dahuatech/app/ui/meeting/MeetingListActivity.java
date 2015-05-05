package com.dahuatech.app.ui.meeting;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.dahuatech.app.adapter.MeetingListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.meeting.MeetingListInfo;
import com.dahuatech.app.bean.meeting.MeetingListParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingListActivity
 * @Description �ҵĻ����б�ҳ��
 * @author 21291
 * @date 2014��9��10�� ����5:04:20
 */
public class MeetingListActivity extends MenuActivity implements OnRefreshListener2<ListView> {
	private static MeetingListActivity mInstance;
	public static MeetingListActivity getInstance() {
		return mInstance;
	}
	private Button btnMeetingList,btnMeetingLaunch;
	
	private PullToRefreshListView mPullRefreshListView; 	//�б�
	private MeetingListAdapter mAdapter;					//��������
	private List<MeetingListInfo> mArrayList;				//����Դ����
	private MeetingListInfo mListInfo;						//����ʵ�����
	private MeetingBusiness mBusiness;						//ҵ���߼���
	private ProgressDialog dialog;      					//������
	
	private String fItemNumber; 							//Ա����
	private String serviceUrl;  							//�����ַ
	private AppContext appContext; 							//ȫ��Context
	
	private int fPageIndex=1;								//ҳ��
	private final static int fPageSize=5;					//ҳ��С
	private int fRecordCount;								//�ܵļ�¼��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.meeting_list);	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGLISTACTIVITY;	//��ȡ�����ַ
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		prepareListData();
		setListener();
		new getListAsync().execute(fPageIndex);	
		sendLogs();	//������־��Ϣ����ͳ��
	}
	
	/** 
	* @Title: prepareListData 
	* @Description: ����ʵ����׼��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����3:11:03
	*/
	private void prepareListData(){
		mArrayList=new ArrayList<MeetingListInfo>();
		btnMeetingList=(Button)findViewById(R.id.meeting_list_mine);
		btnMeetingLaunch=(Button)findViewById(R.id.meeting_list_launch);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.meeting_list_PullToRefreshListView);
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MeetingListActivity.this);
		mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness",serviceUrl);
	}
	
	/** 
	* @Title: setListener 
	* @Description: �����¼�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��17�� ����8:48:10
	*/
	private void setListener(){
		//�����б�ˢ��
		btnMeetingList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showListMeeting();
				mArrayList.clear();
				fPageIndex=1;
				new getListAsync().execute(fPageIndex);
			}
		});
		
		//�������
		btnMeetingLaunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showLaunchMeeting();
				UIHelper.showMeetingDetail(MeetingListActivity.this,fItemNumber,"","1");
			}
		});
	}
	
	/** 
	* @Title: showListMeeting 
	* @Description: ��ʾ�����б�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:46:05
	*/
	private void showListMeeting(){
		btnMeetingList.setBackgroundResource (R.drawable.meeting_tabs_left_active);
		btnMeetingList.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_left_active);
		
		btnMeetingLaunch.setBackgroundResource (R.drawable.meeting_tabs_right);
		btnMeetingLaunch.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_right);	
	}
	
	/** 
	* @Title: showLaunchMeeting 
	* @Description: ��ʾ�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��19�� ����11:46:26
	*/
	private void showLaunchMeeting(){
		btnMeetingList.setBackgroundResource (R.drawable.meeting_tabs_left);
		btnMeetingList.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_left);
		
		btnMeetingLaunch.setBackgroundResource (R.drawable.meeting_tabs_right_active);
		btnMeetingLaunch.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_right_active);	
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��9��11�� ����4:30:33
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡʵ�弯����Ϣ
	* @param @param pageIndex
	* @param @return     
	* @return List<MeetingListInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����4:31:02
	*/
	private  List<MeetingListInfo> getListByPost(final Integer pageIndex){
		MeetingListParamInfo mListParamInfo=MeetingListParamInfo.getMeetingListParamInfo();
		mListParamInfo.setFItemNumber(fItemNumber);
		mListParamInfo.setFPageIndex(pageIndex);
		mListParamInfo.setFPageSize(fPageSize);
		mBusiness.setServiceUrl(serviceUrl);
		
		return mBusiness.getMeetingList(mListParamInfo);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ���б���
	* @param @param listData �б���      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��28�� ����10:07:35
	*/
	private void renderListView(final List<MeetingListInfo> listData){
		if(listData.size()==0){ //û������
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount=mArrayList.get(0).getFRecordCount();
		if(mArrayList.size() < fRecordCount){
			mPullRefreshListView.setMode(Mode.BOTH);
		}
		else {
			mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		}
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);  
		
		mAdapter = new MeetingListAdapter(MeetingListActivity.this, mArrayList, R.layout.meeting_list_item); //��������������
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
				
				mListInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					mListInfo=(MeetingListInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.meeting_list_item);
					mListInfo=(MeetingListInfo)reLayout.getTag();
				}
				if(mListInfo==null){
					return;	
				}
				UIHelper.showMeetingDetail(MeetingListActivity.this,fItemNumber,mListInfo.getFId(),mListInfo.getFStatus());
			}
		});
	}
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		 //����ˢ��
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
	     onPullDownListView();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		//�������ظ���
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
        onPullUpListView();	
	}
	
	/** 
	* @Title: onPullDownListView 
	* @Description: ����ˢ�£�ˢ����������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����4:42:34
	*/
	public void onPullDownListView(){
		mArrayList.clear();
		fPageIndex=1;
		new pullDownRefreshAsync().execute(fPageIndex);  //��ȡ��һҳ��������	
    }

	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description �����첽ˢ��������Ϣ
	 * @author 21291
	 * @date 2014��9��11�� ����4:43:10
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{

		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			mPullRefreshListView.setMode(Mode.BOTH);
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_no_refresh_records),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			mAdapter = new MeetingListAdapter(MeetingListActivity.this, mArrayList, R.layout.meeting_list_item); //��������������
			mPullRefreshListView.setAdapter(mAdapter);
			mPullRefreshListView.onRefreshComplete();	
			super.onPostExecute(result);
		}
	}
	
	/** 
	* @Title: onPullUpListView 
	* @Description: �������ظ��� �����¼���5������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��11�� ����4:51:18
	*/
	public void onPullUpListView(){
		fPageIndex++;   //��ҳ
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��9��11�� ����4:51:08
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{

		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_no_load_records),3000);
				mPullRefreshListView.onRefreshComplete();
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				return;
			}
			mArrayList.addAll(result);
			fRecordCount=mArrayList.get(0).getFRecordCount();
			if(mArrayList.size() < fRecordCount){
				mPullRefreshListView.setMode(Mode.BOTH);
			}
			else {
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
			}
			mAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	// �ӻ�������ҳ��,�ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.MEETING_DETAIL_INFO:  //��������
				if(resultCode==RESULT_OK){
					showListMeeting();
					fPageIndex=1;
					mArrayList.clear();
					new getListAsync().execute(fPageIndex);
				}			
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ����ҵĻ���ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����6:46:58
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mymeeting));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MeetingListActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MeetingListActivity.this);
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
		if(mAdapter!=null && mArrayList.size() > 0){
			mAdapter.refreshView(mArrayList);
		}
	}

}
