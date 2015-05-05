package com.dahuatech.app.ui.expense.flow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpandableListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.expense.ExpandableInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.FlowParamInfo;
import com.dahuatech.app.business.ExpandableBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuExapandableListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

/**
 * @ClassName ExpenseFlowListActivity
 * @Description �ҵ���ˮ�������б���ҳ
 * @author 21291
 * @date 2014��8��21�� ����3:14:04
 */
public class ExpenseFlowListActivity extends MenuExapandableListActivity implements OnRefreshListener2<ExpandableListView> {
 
	private Button btnSubmit,btnUpload,btnAdd;  //������ť
	private EditText searchEdit;				//�����ı�
	private ImageButton searchImageBtn;			//������ť 
	
	private PullToRefreshExpandableListView mPullRefreshListView;
	private ExpandableListAdapter listAdapter;  //��������
	private List<String> listDataHeader;        //ͷ������
	private HashMap<String, List<ExpenseFlowDetailInfo>> listDataChild;  //�Ӽ����
	private ExpenseFlowDetailInfo efDetailInfo;	//��ˮ����ʵ����
	private ExpandableBusiness eBusiness;		//ҵ���߼���
	private ProgressDialog dialog;      		//������
	
	private String fItemNumber,fQueryText;  	//Ա���š���ѯ�ı�ֵ
	private String serviceUrl;  				//�����ַ
	private AppContext appContext; 				//ȫ��Context
	
	private int fPageIndex=1;					//ҳ��
	private final static int fPageSize=5;		//ҳ��С
	
	private String fCurrentDate;				//��ǰ���ڣ�ͬʱΪ��ҳȡ���5��������һ����������
	private Calendar cal;						//������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_flowlist);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWLISTACTIVITY;	
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
			
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		//����
		cal = Calendar.getInstance();	
		fCurrentDate=StringUtils.toShortDateString(cal.getTime());  //���õ�ǰ����
		
		prepareListData();  //ʵ�������ϱ���	
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
	* @date 2014��8��27�� ����1:54:27
	*/
	private void prepareListData(){
		listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ExpenseFlowDetailInfo>>();
    	//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowListActivity.this);
		eBusiness= (ExpandableBusiness)factoryBusiness.getInstance("ExpandableBusiness",serviceUrl);
		
		//��ʼ���ؼ�
		btnSubmit=(Button)findViewById(R.id.expense_flowlist_btnSubmit);
		btnUpload=(Button)findViewById(R.id.expense_flowlist_btnUpload);
		btnAdd=(Button)findViewById(R.id.expense_flowlist_btnAdd);
		searchEdit=(EditText)findViewById(R.id.expense_flowlist_searchEditText);
		searchImageBtn=(ImageButton)findViewById(R.id.expense_flowlist_searchImageButton);	
        mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.expense_flowlist_expand);	
        fQueryText="";
	}
	
	/** 
	* @Title: setListener 
	* @Description: ���ü����¼�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��9�� ����1:48:27
	*/
	private void setListener(){
		btnSubmit.setOnClickListener(new OnClickListener() {  //���ϴ��¼�
			
			@Override
			public void onClick(View v) {
				serverSelected();
				fPageIndex=1;
				listDataHeader.clear(); 
				listDataChild.clear();
				new getListAsync().execute(fPageIndex);
			}
		});
		
		btnUpload.setOnClickListener(new OnClickListener() {  //���ϴ��¼�
			
			@Override
			public void onClick(View v) {
				localSelected();
				UIHelper.showExpenseFlowLocalList(ExpenseFlowListActivity.this, fItemNumber);
			}
		});

		btnAdd.setOnClickListener(new OnClickListener() {  //�����¼�
	
			@Override
			public void onClick(View v) {
				addSelected();
				UIHelper.showExpenseFlowDetail(ExpenseFlowListActivity.this,fItemNumber,"");
			}
		});
		
		searchEdit.setOnClickListener(new OnClickListener() {  //�ı������¼�
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowListActivity.this ,searchDateListener,
						cal.get(Calendar.YEAR ),   
			            cal.get(Calendar.MONTH ),   
			            cal.get(Calendar.DAY_OF_MONTH )).show();   
			}
		});
		
		searchImageBtn.setOnClickListener(new OnClickListener() {  //������ť����¼�
			
			@Override
			public void onClick(View v) {
				if(!"����ʱ��".equals(searchEdit.getText().toString())){
					fQueryText=searchEdit.getText().toString();
				}
				else {
					fQueryText="";
				}
				fPageIndex=1;
				listDataHeader.clear(); 
				listDataChild.clear();
				new getListAsync().execute(fPageIndex);
			}
		});
	}

	//����ʱ������������
	private DatePickerDialog.OnDateSetListener searchDateListener = new DatePickerDialog.OnDateSetListener(){		
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  searchEdit.setText(StringUtils.toShortDateString(cal.getTime()));
		}
	};
	
	/** 
	* @Title: serverSelected 
	* @Description: ���ϴ����б�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����10:41:43
	*/
	private void serverSelected(){
		btnSubmit.setBackgroundResource (R.drawable.tabs_default_left_active_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_active_white);
		
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_white);
		
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_white);
	}
	
	/** 
	* @Title: localSelected 
	* @Description: �����б�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����10:42:09
	*/
	private void localSelected(){
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_active_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_active_white);
		
		btnSubmit.setBackgroundResource(R.drawable.tabs_default_left_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_white);
	
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_white);
	}
	
	
	/** 
	* @Title: addSelected 
	* @Description: ����ҳ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��22�� ����10:42:45
	*/
	private void addSelected(){
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_active_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_active_white);
		
		btnSubmit.setBackgroundResource (R.drawable.tabs_default_left_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_white);
		
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_white);
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��8��28�� ����10:23:58
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
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
	* @return List<ExpandableInfo>    
	* @throws 
	* @author 21291
	* @date 2014��8��28�� ����10:19:21
	*/
	private List<ExpandableInfo> getListByPost(final Integer pageIndex){
		FlowParamInfo fParamInfo=FlowParamInfo.getFlowParamInfo();
		fParamInfo.setFItemNumber(fItemNumber);
		fParamInfo.setFQueryText(fQueryText);
		fParamInfo.setFCurrentDate(fCurrentDate);
		fParamInfo.setFPageIndex(pageIndex);
		fParamInfo.setFPageSize(fPageSize);
		eBusiness.setServiceUrl(serviceUrl);
		return eBusiness.getExpandableList(fParamInfo);
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
	private void renderListView(final List<ExpandableInfo> listData){
		if(listData.size()==0){ //û������
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_netparseerror),3000);
			listDataHeader.clear();
			listDataChild.clear();
		}
		else {
			mPullRefreshListView.setMode(Mode.BOTH);
			mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
			mPullRefreshListView.setOnRefreshListener(this);  
			initListData(listData); //��ʼ������
		}
		listAdapter = new ExpandableListAdapter(ExpenseFlowListActivity.this, listDataHeader, listDataChild,fItemNumber); //��������������
		setListAdapter(listAdapter);
		
		ExpandableListView exlistView = mPullRefreshListView.getRefreshableView();  
		exlistView.setGroupIndicator(null); //ȥ��ͼ��
		exlistView.setOnChildClickListener(new OnChildClickListener() {

	        @Override
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {  	
	        	listAdapter.setParentItem(groupPosition);
	        	listAdapter.setChildItem(childPosition);
	        	listAdapter.notifyDataSetChanged();
				
	        	efDetailInfo=null;		
	        	efDetailInfo=(ExpenseFlowDetailInfo)listAdapter.getChild(groupPosition,childPosition);
				if(efDetailInfo==null){
					return false;	
				}
	        	
				UIHelper.showExpenseFlowDetail(ExpenseFlowListActivity.this,fItemNumber,"server",efDetailInfo);
	            return true;
	        }
	    });
		
		exlistView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {	
				return false;
			}
	    });
		
		exlistView.setOnGroupExpandListener(new OnGroupExpandListener() {	
            public void onGroupExpand(int groupPosition) {}
        });
 
		exlistView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {}
        });
		searchEdit.setText(getResources().getString(R.string.expense_flow_list_search));
	}
	
	/** 
	* @Title: initListData 
	* @Description: ��ʼ�����ݼ���
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��28�� ����10:41:52
	*/
	private void initListData(final List<ExpandableInfo> listData){
		try {
			Type listType = new TypeToken<ArrayList<ExpenseFlowDetailInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray=null;
			List<ExpenseFlowDetailInfo> eFlowInfos=null;
			for (ExpandableInfo item : listData) {
				// ���������⼯��
				listDataHeader.add(item.getFGroupTitle());
				jsonArray= new JSONArray(item.getFSubEntrys());
				eFlowInfos=gson.fromJson(jsonArray.toString(), listType);
				listDataChild.put(item.getFGroupTitle(), eFlowInfos);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		 //����ˢ��
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
	     onPullDownListView();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
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
	* @date 2014��8��27�� ����4:09:48
	*/
	public void onPullDownListView(){
		fPageIndex=1;
		fQueryText="";
		fCurrentDate=StringUtils.toShortDateString(cal.getTime());  		//���õ�ǰ����
		searchEdit.setText(fQueryText);
		new pullDownRefreshAsync().execute(fPageIndex);  //��ȡ��һҳ��������	
    }
	
	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description �����첽ˢ��������Ϣ
	 * @author 21291
	 * @date 2014��8��28�� ����3:12:44
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{

		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
			mPullRefreshListView.setMode(Mode.BOTH);
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_no_refresh_records),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			listDataHeader.clear();
	        listDataChild.clear();
			initListData(result);
			listAdapter = new ExpandableListAdapter(ExpenseFlowListActivity.this, listDataHeader, listDataChild,fItemNumber); //��������������
			setListAdapter(listAdapter);
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
	* @date 2014��8��27�� ����4:10:43
	*/
	public void onPullUpListView(){
		fPageIndex++;   //��ҳ
		int titleSize= listDataHeader.size();
		if(titleSize > 0){
			Date date = StringUtils.toShortDate(listDataHeader.get(titleSize-1));
			fCurrentDate=StringUtils.getSpecifiedDayBefore(date);  //��ȡ�������һ�����ڵ�ǰһ��			
		}else {
			fCurrentDate=StringUtils.toShortDateString(cal.getTime());
		}
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��8��28�� ����11:31:13
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{

		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_no_load_records),3000);
				mPullRefreshListView.onRefreshComplete();
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				return;
			}
			initListData(result);
			listAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case UIHelper.ACTIVITY_EXPENSEDETAIL_SERVER:
				if(resultCode==RESULT_OK){
					fPageIndex=1;
					listDataHeader.clear(); 
					listDataChild.clear();
					new getListAsync().execute(fPageIndex);
				}			
				break;
			default:
				break;
		}
		serverSelected();
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �����ˮ�б�ʱ��������־��¼��������
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
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_flow));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseFlowListActivity.this,logInfo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpenseFlowListActivity.this);
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
		if(listAdapter!=null && listDataChild.size() > 0){
			listAdapter.refreshView(listDataChild);
		}
	}
}
