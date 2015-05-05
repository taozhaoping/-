package com.dahuatech.app.ui.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.TaskListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.StorageParameterInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.TaskListBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.XListView;
import com.dahuatech.app.widget.XListView.IXListViewListener;

/**
 * @ClassName TaskListActivity
 * @Description ����Activity��
 * @author 21291
 * @date 2014��4��23�� ����10:16:54
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class TaskListActivity extends MenuActivity implements IXListViewListener {
	private static TaskListActivity mInstance;

	private ImageButton searchImageButton;
	private Button btnList,btnHistory;
	private EditText searchEditText;
	private XListView mListView; 	// �б��ؼ�����
	private ProgressDialog dialog;	// ���̿�
	
	private Handler mHandler; // �����߳���
	// ��ʼ��һ���̳߳أ��̶�10���߳�
	private ExecutorService executorService; 
	private static int pendAppStart = 1; //  ��������¼��ʼҳ��
	private static int passAppStart=1;	 // �Ѿ�������ʼҳ��
	private List<TaskInfo> tArrayList; // ����Դ����
	private TaskListAdapter mAdapter; // ������
	
	private String serviceUrl; // �����ַ
	private HashMap<String, String> hashMap; // ������Ϣ
	
	private TaskInfo task;   		//���ݵĲ�����
	private String fSearchText;		// �����ı�
	private String fItemNumber;     // Ա����
	private String fStatus="0";		// Ĭ�ϴ�������¼��0-��������1-������
	private int fTotalCount=0;		// ��ҳ�ܼ�¼�� �������������Ѿ�������¼
	
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ѡ�������ļ�
	
	public static TaskListActivity getInstance(){
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.tasklist);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//���ؼ���
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		//��ʼ�����ݼ���
		tArrayList = new ArrayList<TaskInfo>();
		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			tArrayList.clear();
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		sp=getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);
		executorService = Executors.newFixedThreadPool(10);

		// ��ȡ�ҵ�����������Ϣ
		hashMap = ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "TaskListActivity");
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_TASKLISTACTIVITY;	
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);			
			//����������Ϣ Ϊ�����ؿ���
			sp.edit().putString(AppContext.FSTATUS_KEY, fStatus).commit();
		}
		
		// ����һ���ȴ�������ʾ�û��ȴ�
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
		
		//�������¼�
		btnList = (Button) findViewById(R.id.items_imgbtnList);
		btnList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnList.setBackgroundResource (R.drawable.tabs_active);
				btnList.setTextAppearance(TaskListActivity.this,R.style.tabs_active);
				btnHistory.setBackgroundResource (R.drawable.tabs_default_right);
				btnHistory.setTextAppearance(TaskListActivity.this,R.style.tabs_default_right);
				tArrayList.clear();		
				fStatus="0";
				searchEditText.setText("");
				fSearchText="";
				pendAppStart=1;
				new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
			}
		});
		
		//�������¼�
		btnHistory = (Button) findViewById(R.id.items_imgbtnHistory);
		btnHistory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				btnHistory.setBackgroundResource (R.drawable.tabs_active);
				btnHistory.setTextAppearance(TaskListActivity.this,R.style.tabs_active);
				btnList.setBackgroundResource (R.drawable.tabs_default_left);
				btnList.setTextAppearance(TaskListActivity.this,R.style.tabs_default_left);
				tArrayList.clear();
				fStatus="1";
				searchEditText.setText("");
				fSearchText="";
				passAppStart=1;
				new TaskListAsync().execute(serviceUrl, String.valueOf(passAppStart), AppContext.PAGE_SIZE);	
			}
		});		
		
		searchEditText=(EditText) findViewById(R.id.tasklist_searchEditText);
		searchEditText.setFocusableInTouchMode(true);
		searchEditText.requestFocus();
		
		//�����¼�
		searchImageButton=(ImageButton)findViewById(R.id.tasklist_searchImageButton);
		searchImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				InputMethodManager imm = (InputMethodManager) getSystemService(TaskListActivity.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);				
				fSearchText=searchEditText.getText().toString();
				if(!StringUtils.isEmpty(fSearchText)){  //�����Ϊ��
					imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
					tArrayList.clear();
					if("0".equals(fStatus)){  //˵���Ǵ�������¼����
						pendAppStart=1;
						new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
					}
					else{ //˵������������¼
						passAppStart=1;
						new TaskListAsync().execute(serviceUrl, String.valueOf(passAppStart), AppContext.PAGE_SIZE);	
					}
				}
			}
		});
		
		//��ʱ�Ľ������  ����thread/asynctask
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		sendLogs();	//������־��Ϣ����ͳ��
	}
	
	/**
	 * @ClassName TaskListAsync
	 * @Description �첽ִ������,��ȡ���ݽ����
	 * @author 21291
	 * @date 2014��4��23�� ����3:15:54
	 */
	private class TaskListAsync extends AsyncTask<String, Void, List<TaskInfo>> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ��ʾ�ȴ���
			dialog.show();
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<TaskInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			// ���ٵȴ���
			dialog.dismiss();
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<TaskInfo> doInBackground(String... params) {
			return getListByPost(params[0], params[1], params[2]);
		}
	}
	
	/**
	 * @Title: renderListView
	 * @Description: ���ؽ������ݲ���ʼ��
	 * @param @param arrayList ����
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��4��8�� ����2:59:58
	 */
	private void renderListView(List<TaskInfo> arrayList) {
		// ʵ����XListView
		mListView = (XListView) findViewById(R.id.tasklist_xListView);
		if(arrayList.size()==0){ //�����ȡ��������
			if("0".equals(fStatus)){
				UIHelper.ToastMessage(TaskListActivity.this, getString(R.string.tasklist_list_netparseerror),3000);
			}
			else {
				UIHelper.ToastMessage(TaskListActivity.this, getString(R.string.tasklist_history_netparseerror),3000);
			}
			fTotalCount=0;
			mListView.setAdapter(null);
			mListView.setXListViewListener(TaskListActivity.this);
			if(!mListView.ismPullLoading()){
				mListView.setmPullLoading(true);
			}
			mListView.stopLoadFinish();	
			return;
		}
		fTotalCount=arrayList.get(0).getFTotalCount();
		mListView.setPullLoadEnable(true);
		tArrayList.addAll(arrayList);
		
		// ʹ�����ݼ�����adapter����
		mAdapter = new TaskListAdapter(TaskListActivity.this, tArrayList,R.layout.tasklistlayout,fStatus);
		// ����XListView��adapter
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(TaskListActivity.this);
		// ʵ���� handler
		mHandler = new Handler();
	
		//ÿ����Ŀ����¼�
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				if(position==0)  //���������Ч
					return;				
				task=null;
				//�ж���ͼ�����Ƿ�TextView
				if(view instanceof RelativeLayout){ //��
					task=(TaskInfo) parent.getItemAtPosition(position);
				}
				else { //��
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.tasklistlayout_tablelayout);
					task=(TaskInfo) reLayout.getTag();
				}
				if(task==null)
					return;					
				UIHelper.showTaskDetail(TaskListActivity.this,task,fStatus); // ��ת
			}
		});
	}
	
	/**
	 * @Title: getListByPost
	 * @Description: ͨ��POST��ʽ��ȡ��������Դ
	 * @param @param serviceUrl �����ַ
	 * @param @param pageIndex ҳ��
	 * @param @param pageSize ҳ����ʾ��С
	 * @param @return
	 * @return ArrayList<TaskInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��4��9�� ����2:25:39
	 */
	private List<TaskInfo> getListByPost(String serviceUrl,String pageIndex, String pageSize) {
		// ����ʵ��-�ִ���
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// ����ʵ��-�洢���̲�����
		List<StorageParameterInfo> storageParameters = new ArrayList<StorageParameterInfo>();
		// �ҵ������б���������
		taskListParam(repository, storageParameters, pageIndex, pageSize);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(TaskListActivity.this);
		TaskListBusiness taskListBusiness= (TaskListBusiness)factoryBusiness.getInstance("TaskListBusiness",serviceUrl);
		return taskListBusiness.getTaskList(repository, storageParameters);
	}

	/** 
	* @Title: taskListParam 
	* @Description: �ҵ������б���������
	* @param @param repository �ִ�ʵ��
	* @param @param StorageParameter �洢����ʵ��
	* @param @param pageIndex ҳ��
	* @param @param pageSize ҳ��
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��25�� ����9:18:52
	*/
	private void taskListParam(RepositoryInfo repository,List<StorageParameterInfo> StorageParameter, String pageIndex,String pageSize) {
		// ����ֵ
		String commTblName = "(SELECT a.FMobileOffice,a.FID AS FMenuID,b.* FROM T_Menu AS a LEFT JOIN t_task AS b ON a.FSystemType=b.FSystemType AND a.FClassTypeID=b.FClassTypeID) AS t";
		String commSelectFieldName = "FID,FMenuID,FBillID,FTitle,FSender,FSendTime,FClassTypeID,FClassTypeName,FSystemType,null as FTotalCount";
		String commStrWhere = "t.FMobileOffice=1 AND t.FStatus="+fStatus+" AND t.FReciever='"+fItemNumber+"'"; //��ʽ�ַ���
//		String commStrWhere = "t.FStatus="+fStatus+" AND t.FReciever='"+fItemNumber+"'"; //�����ַ���
		if(!StringUtils.isEmpty(fSearchText)){
			/*2015-04-07  ����ƽ  �������ӵ�����������*/
			commStrWhere = commStrWhere + " and ((FTitle is not null  and t.FTitle like '%"+fSearchText+"%') ";
			commStrWhere = commStrWhere + " or ( FClassTypeName is not null and FClassTypeName like '%"+fSearchText+"%'))";
		}	
		String commOrderFieldName = "FID";

		repository.setClassType(hashMap.get("ClassType"));
		repository.setIsTest(hashMap.get("IsTest"));
		repository.setServiceName(hashMap.get("ServiceName"));
		repository.setServiceType(hashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(hashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(hashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);

		StorageParameter.add(new StorageParameterInfo("tblName", commTblName,"varchar", 1000, false));
		StorageParameter.add(new StorageParameterInfo("SelectFieldName",commSelectFieldName, "varchar", 4000, false));
		StorageParameter.add(new StorageParameterInfo("strWhere", commStrWhere,"varchar", 4000, false));
		StorageParameter.add(new StorageParameterInfo("OrderFieldName",commOrderFieldName, "varchar", 255, false));
		StorageParameter.add(new StorageParameterInfo("PageSize", pageSize,"int", 4, false));
		StorageParameter.add(new StorageParameterInfo("PageIndex", pageIndex,"int", 4, false));
		StorageParameter.add(new StorageParameterInfo("ReturnRowCount", "0","int", 4, true));
		StorageParameter.add(new StorageParameterInfo("OrderType", "true","bit", 2, false));
	}
	
	/**
	* <p>Title: onRefresh</p> 
	* <p>Description: ��������ˢ�·���</p>  
	* @see com.dahuatech.app.widget.XListView.IXListViewListener#onRefresh() 
	*/
	@Override
	public void onRefresh() {
		executorService.submit(new Runnable() {
			public void run() {
				try {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							tArrayList.clear();
							geneItems(1);
							mAdapter = new TaskListAdapter(TaskListActivity.this,tArrayList,R.layout.tasklistlayout,fStatus);
							mListView.setAdapter(mAdapter);
							if(!mListView.ismPullLoading()){
								mListView.setmPullLoading(true);
							}
							onLoad();
						}
					}, 1000);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	/** 
	* <p>Title: onLoadMore</p> 
	* <p>Description: ���¼��ظ��෽��</p>  
	* @see com.dahuatech.app.widget.XListView.IXListViewListener#onLoadMore() 
	*/
	@Override
	public void onLoadMore() {
		executorService.submit(new Runnable() {
			public void run() {
				try {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if("0".equals(fStatus)){
								geneItems(++pendAppStart);
							}
							else {
								geneItems(++passAppStart);
							}						
							mAdapter.notifyDataSetChanged();
							onLoad();
						}
					}, 1000);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private void geneItems(int pageIndex) {
		if(tArrayList.size() < fTotalCount){
			tArrayList.addAll(getListByPost(serviceUrl, String.valueOf(pageIndex), AppContext.PAGE_SIZE));
		}else {
			if("0".equals(fStatus))
				pendAppStart=1;
			else
				passAppStart=1;	
		}			
	}

	/** 
	* @Title: onLoad 
	* @Description: ��ʾ�ַ���
	* @param @param showStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��25�� ����2:45:40
	*/
	private void onLoad() {
		mListView.stopRefresh();
		if(tArrayList.size() < fTotalCount)
			mListView.stopLoadMore();
		else
			mListView.stopLoadFinish();	
		mListView.setRefreshTime("�ո�");
	}
	
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		String fAppStatus=sp.getString(AppContext.TA_APPROVE_FSTATUS, " ");
		if(!StringUtils.isEmpty(fAppStatus)){
			sp.edit().remove(AppContext.TA_APPROVE_FSTATUS).commit();
			tArrayList.clear();
			searchEditText.setText("");
			fSearchText="";
			new TaskListAsync().execute(serviceUrl, "1", AppContext.PAGE_SIZE);	
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: ����ҵ�����ʱ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����1:56:47
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(TaskListActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(TaskListActivity.this);
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