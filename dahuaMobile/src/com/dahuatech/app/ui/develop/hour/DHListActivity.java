package com.dahuatech.app.ui.develop.hour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
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
import com.dahuatech.app.adapter.DHListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHListInfo;
import com.dahuatech.app.bean.develophour.DHListParamInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.DHWeekSpinnerDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName DHListActivity
 * @Description �з���ʱ�б�
 * @author 21291
 * @date 2014��10��21�� ����5:34:18
 */
public class DHListActivity extends MenuActivity implements OnRefreshListener2<ListView> {
	private static DHListActivity mInstance;				
	
	private EditText searchEditText;						//�����ı���
	private ImageButton searchImage;						//������ť
	private PullToRefreshListView mPullRefreshListView;		//�б�ؼ�
	private ProgressDialog dialog;      					//������
	
	private DevelopHourBusiness dBusiness;					//ҵ���߼���
	private List<DHListInfo> dArrayList;					//����Դ
	private DHListAdapter dAdapter;							//��������
	
	private String fItemNumber;  							//Ա����
	private String serviceUrl;  							//�����ַ
	private AppContext appContext; 							//ȫ��Context

	private Calendar cal;									//������
	private int fCurrentYear;								//��ǰ���
	private int fWeekIndex;									//�ܴ�
	private int fCurrentWeekIndex;							//��ǰ�ܴ�
	
	public static DHListActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.dh_list);
		
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
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHLISTACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initListView();
		setListener();
		new getListAsync().execute(fWeekIndex);	
		sendLogs();	//������־��Ϣ����ͳ��
	}
	
	/** 
	* @Title: initListView 
	* @Description: ��ʼ���б�ҳ��ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��23�� ����3:20:57
	*/
	private void initListView(){
		searchEditText=(EditText)findViewById(R.id.dh_list_searchEditText);
		searchEditText.setInputType(InputType.TYPE_NULL);
		searchImage=(ImageButton)findViewById(R.id.dh_list_searchImageButton);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.dh_list_PullToRefreshListView);
	
		//�����������
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHListActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
	    dArrayList=new ArrayList<DHListInfo>();
	
	    cal = new GregorianCalendar(); 
	    fCurrentYear=cal.get(Calendar.YEAR);  		 		 	        						//��ȡ��ǰ���  							
	    fCurrentWeekIndex=fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());				//��ȡ��ǰ�����ǵڼ���
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
		searchImage.setOnClickListener(new OnClickListener() { //������ť
			@Override
			public void onClick(View v) {
				showWeekSpinner();
			}
		});
	}
	
	/** 
	* @Title: showWeekSpinner 
	* @Description: ��ʾ�ܴε�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����10:54:50
	*/
	private void showWeekSpinner(){
		final DHWeekSpinnerDialog dSpinnerDialog=new DHWeekSpinnerDialog(DHListActivity.this,fCurrentWeekIndex,new ISpinnerListener(){

			@Override
			public void rejectOk(int n, RejectNodeInfo reInfo) {}

			@Override
			public void adAmapOk(int n, AdAmapInfo adAmapInfo) {}	
			
			@Override
			public void cancelled() {
				searchEditText.setText("");
			}

			@Override
			public void dHWeekOk(int n,String itemText, DHWeekInfo dhWeekInfo) { //ѡ����һ���ܶ���
				searchEditText.setText(itemText);
				fCurrentYear=dhWeekInfo.getFYear();
				fWeekIndex=dhWeekInfo.getFIndex();
				dArrayList.clear();
				new getListAsync().execute(fWeekIndex);	
			}

		
		});
		dSpinnerDialog.setTitle(getResources().getString(R.string.spinner_week_prompt));
		dSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
		dSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
		dSpinnerDialog.show();	
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��10��23�� ����4:00:28
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡʵ�弯����Ϣ
	* @param @param fWeekIndex
	* @param @return     
	* @return List<DHListInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��24�� ����2:18:52
	*/
	private List<DHListInfo> getListByPost(final Integer fWeekIndex){
		DHListParamInfo dhListParamInfo=DHListParamInfo.getDHListParamInfo();
		dhListParamInfo.setFItemNumber(fItemNumber);
		dhListParamInfo.setFWeekIndex(fWeekIndex);
		dhListParamInfo.setFYear(fCurrentYear);
		
		dBusiness.setServiceUrl(serviceUrl);
		return dBusiness.getDHList(dhListParamInfo);
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
	private void renderListView(final List<DHListInfo> listData){
		dArrayList.addAll(listData);
		dAdapter = new DHListAdapter(fItemNumber,DHListActivity.this, dArrayList, R.layout.dh_list_item); //��������������
		mPullRefreshListView.setAdapter(dAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(this);
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
	* @date 2014��10��23�� ����4:19:26
	*/
	public void onPullDownListView(){
		dArrayList.clear();
		fCurrentYear=cal.get(Calendar.YEAR);  									//��ǰ���
		fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());				//��ȡ��ǰ�����ǵڼ���
		new pullDownRefreshAsync().execute(fWeekIndex);  //��ȡ��һҳ��������	
    }
	
	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description �����첽ˢ��������Ϣ
	 * @author 21291
	 * @date 2014��10��23�� ����4:21:32
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{

		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			dArrayList.addAll(result);
			dAdapter = new DHListAdapter(fItemNumber,DHListActivity.this, dArrayList, R.layout.dh_list_item); //��������������
			mPullRefreshListView.setAdapter(dAdapter);
			mPullRefreshListView.onRefreshComplete();	
			super.onPostExecute(result);
		}
	}

	/** 
	* @Title: onPullUpListView 
	* @Description: �������ظ���,�ټ�����һ������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��23�� ����4:21:59
	*/
	public void onPullUpListView(){
		fWeekIndex--;   //��ҳ
		if(fWeekIndex < 1){  //˵����������һ������һ�ܿ�ʼ
			fCurrentYear=fCurrentYear-1;  //��һ��
			fWeekIndex=DateHelper.getNumWeeksForYear(cal, fCurrentYear);//��ȡ��һ���ܵ����� 
		}
		new pullUpRefreshAsync().execute(fWeekIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��10��23�� ����4:22:31
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{

		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			// ��Ҫ����ɺ�ʱ����
			return getListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			dArrayList.addAll(result);
			dAdapter.notifyDataSetChanged();	
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case AppContext.DEVELOP_HOURS_DETAIL:
				if(resultCode==RESULT_OK){
					dArrayList=new ArrayList<DHListInfo>();
					fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());	//��ȡ��ǰ�����ǵڼ���
					new getListAsync().execute(fWeekIndex);	
				}		
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �����ʱ�б�ʱ��������־��¼��������
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
		logInfo.setFModuleName(getResources().getString(R.string.log_develop_hour));
		logInfo.setFActionName("access");
		logInfo.setFNote("dhlist");
		UIHelper.sendLogs(DHListActivity.this,logInfo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHListActivity.this);
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
