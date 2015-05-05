package com.dahuatech.app.ui.develop.hour;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHProjectAdapter;
import com.dahuatech.app.bean.develophour.DHProjectInfo;
import com.dahuatech.app.bean.develophour.DHProjectParamInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName DHProjectSearchActivity
 * @Description ��Ŀ����ҳ��
 * @author 21291
 * @date 2014��10��27�� ����10:53:42
 */
public class DHProjectSearchActivity extends MenuActivity implements OnRefreshListener<ListView>{

	private EditText searchEditText;
	private ImageButton searchImageButton;
	private PullToRefreshListView mPullRefreshListView; 	//�б�ؼ�
	
	private List<DHProjectInfo> dArrayList;					//����Դ
	private DbManager mDbHelper;							//���ݿ������
	private DevelopHourBusiness dBusiness;					//ҵ���߼���
	private DHProjectInfo dhProjectInfo;					//���ʵ��
	private DHProjectAdapter dAdapter;						//��������	
	private ProgressDialog dialog;      					//������
	
	private String fQueryText;								//��ѯ�ַ���
	private int fPageIndex=1;								//ҳ��
	private static final int fPageSize=10;					//ҳ��С
	
	private String searchUrl;  								//�����ַ
	private AppContext appContext; 							//ȫ��Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.dh_project_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ�����ַ
		searchUrl=AppUrl.URL_API_HOST_ANDROID_DHPROJECTSEARCHACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initView();
		setOnListener();
		new getLocalAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ����ͼ�ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����3:18:54
	*/
	private void initView(){
		searchEditText=(EditText)findViewById(R.id.dh_project_searchEditText);
		searchEditText.setFocusable(true);
		searchImageButton=(ImageButton)findViewById(R.id.dh_project_searchImageButton);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.dh_project_search_pullToRefreshListView);
	
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHProjectSearchActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness",searchUrl);
	
		dArrayList=new ArrayList<DHProjectInfo>();
		fQueryText="";
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: ������ͼ�¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����5:28:53
	*/
	private void setOnListener(){
		
		searchImageButton.setOnClickListener(new OnClickListener() {  //ѡ����Ŀ
			
			@Override
			public void onClick(View v) {
				fQueryText=searchEditText.getText().toString();
				if(StringUtils.isEmpty(fQueryText)){
					UIHelper.ToastMessage(DHProjectSearchActivity.this, getResources().getString(R.string.dh_project_search_empty));
					return;
				}
				dArrayList.clear();
				new getServerAsync().execute(fPageIndex);
				searchEditText.setText("");
				searchEditText.setHint(getResources().getString(R.string.dh_project_search_edit));
			}
		});
	}

	/**
	 * @ClassName getLocalAsync
	 * @Description �첽��ȡ������Ŀ��ʷ��¼����
	 * @author 21291
	 * @date 2014��10��30�� ����3:30:18
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<DHProjectInfo>>{

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHProjectInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			super.onPostExecute(result);
			renderLocalList(result);
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
	private List<DHProjectInfo> getLocalByPost(){
		List<DHProjectInfo> list=mDbHelper.queryDHProjectAllList();
		int length=list.size();
		if(list.size() > 0){
			DHProjectInfo dhProjectInfo=new DHProjectInfo();
			dhProjectInfo.setFProjectCode("-1");
			dhProjectInfo.setFProjectName(getResources().getString(R.string.history_record_search_clear));
			list.add(length,dhProjectInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderLocalList 
	* @Description: ��ʼ��������Ŀ������ʷ��¼�б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����4:35:45
	*/
	private void renderLocalList(final List<DHProjectInfo> listData){
		dArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		dAdapter=new DHProjectAdapter(DHProjectSearchActivity.this,dArrayList,R.layout.dh_project_search_item);
		mPullRefreshListView.setAdapter(dAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
			
				dhProjectInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					dhProjectInfo=(DHProjectInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_project_search_item);
					dhProjectInfo=(DHProjectInfo)reLayout.getTag();
				}
				if(dhProjectInfo==null){
					return;	
				}
				if("-1".equals(dhProjectInfo.getFProjectCode())){	//���������¼
					mDbHelper.deleteDHProjectSearchAll();
					dArrayList.clear();
					dAdapter.refreshView();
					UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.history_record_clear_success));
				}
				else {
					Intent intent = new Intent();
					intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE, dhProjectInfo.getFProjectCode());
					intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME, dhProjectInfo.getFProjectName());	
					setResult(RESULT_OK, intent);
					finish();
				}	
			}
		});
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description �첽��ȡ����������
	 * @author 21291
	 * @date 2014��10��30�� ����4:54:55
	 */
	private class getServerAsync extends AsyncTask<Integer,Void,List<DHProjectInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ��ʾ�ȴ���
			dialog.show();
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHProjectInfo> doInBackground(Integer... params) {
			return getServerByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			super.onPostExecute(result);
			renderServerList(result);
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getServerByPost 
	* @Description: ��ȡ��Ŀ������ʵ�弯��
	* @param @param pageIndex
	* @param @return     
	* @return List<DHProjectInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����4:56:33
	*/
	private  List<DHProjectInfo> getServerByPost(final int pageIndex){
		DHProjectParamInfo dParamInfo=DHProjectParamInfo.getDHProjectParamInfo();
		dParamInfo.setFQueryText(fQueryText);
		dParamInfo.setFPageIndex(pageIndex);
		dParamInfo.setFPageSize(fPageSize);
		return dBusiness.getDHProject(dParamInfo);
	}
	
	/** 
	* @Title: renderServerList 
	* @Description: ��ʼ����Ŀ�������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����5:13:57
	*/
	private void renderServerList(final List<DHProjectInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.dh_project_search_list_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		dArrayList.addAll(listData);
		dAdapter=new DHProjectAdapter(DHProjectSearchActivity.this,dArrayList,R.layout.dh_project_search_item);
		mPullRefreshListView.setAdapter(dAdapter);
		
		//�������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //���������Ч
					return;	
				}
				
				dhProjectInfo=null;		
				if(view instanceof RelativeLayout){  //˵������RelativeLayout������
					dhProjectInfo=(DHProjectInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_project_search_item);
					dhProjectInfo=(DHProjectInfo)reLayout.getTag();
				}
				if(dhProjectInfo==null){
					return;	
				}
				mDbHelper.insertDHProjectSearch(dhProjectInfo); //���������¼���������ݿ���
				mDbHelper.closeSqlLite();
				Intent intent = new Intent();
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE, dhProjectInfo.getFProjectCode());
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME, dhProjectInfo.getFProjectName());	
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
	* @Description: �������ظ��� 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����5:26:01
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
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<DHProjectInfo>>{

		@Override
		protected List<DHProjectInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getServerByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.dh_project_search_list_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			dArrayList.addAll(result);
			dAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
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
	}
}
