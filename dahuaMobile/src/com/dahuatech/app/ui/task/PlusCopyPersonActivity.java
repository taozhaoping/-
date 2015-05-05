package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.PlusCopyPersonAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.RejectNodeBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName PlusCopyPersonActivity
 * @Description ��ǩ/������Ա�б�Activity
 * @author 21291
 * @date 2014��9��22�� ����9:14:24
 */
public class PlusCopyPersonActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener {
	private Button btnYetSelect,btnNotSelect,btnCheckAll,btnConfirm;
	private ListView pListView;							//�б�
	private ProgressDialog dialog;      				//������
	
	private PlusCopyPersonAdapter mAdapter;				//��������
	private List<PlusCopyPersonInfo> pArrayList;		//��ѯ����Դ
	private List<PlusCopyPersonInfo> personList;		//��ѡ��Ĳ�����Ա�б�
	private List<PlusCopyPersonInfo> sArrayList;		//��ѡ�е�item��
	
	private String tempList ;							//�Ѿ�ѡ����Ա�����ַ���
	private List<String> mQueryList;			  		//��ѯ��Ա������ʷ��¼����
	private DbManager mDbHelper;						//���ݿ������
	private RejectNodeBusiness rBusiness;				//ҵ���߼���
	
	private static final String[] COLUMNS = {  			//���ʹ���и�ʽ
        BaseColumns._ID,
        SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;					//��ʷ��¼����Դ
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  					//�����ؼ�
	private AppContext appContext; 						//ȫ��Context
	
	private int fStatus=0;								//Ĭ�Ͻ���ѡ����Ա�б�0-ѡ����Ա�б� 1-��ѡ��Ա�б�
	private String fQueryStr;							//��ѯ�ַ���
	private String serviceUrl;  						//�����ַ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//�����ݿ�
		
		setContentView(R.layout.pluscopy_person);
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_PLUSCOPYPERSONURL;
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			tempList=extras.getString(AppContext.PLUSCOPY_PERSON_LIST);
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initListData();
		setListener();
		new getPersonLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: ��ʼ��׼����Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��12�� ����4:32:45
	*/
	private void initListData(){
		btnYetSelect=(Button)findViewById(R.id.pluscopy_person_yet_select);
		btnNotSelect=(Button)findViewById(R.id.pluscopy_person_not_select);
		btnCheckAll=(Button)findViewById(R.id.pluscopy_person_checkAll);
		btnConfirm=(Button)findViewById(R.id.pluscopy_person_confirm);
		
		pListView=(ListView)findViewById(R.id.pluscopy_person_list);
		pArrayList = new ArrayList<PlusCopyPersonInfo>();
		mQueryList= new ArrayList<String>();
		fQueryStr="";
		
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(PlusCopyPersonActivity.this);
		rBusiness= (RejectNodeBusiness)factoryBusiness.getInstance("RejectNodeBusiness",serviceUrl);
		
		//�Ѿ�ѡ�еĲ�����Ա�б�
		showPersonList(tempList);
	}
	
	/** 
	* @Title: showPersonList 
	* @Description: �����Ѿ�ѡ����Ա����
	* @param @param fTempList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����10:28:08
	*/
	private void showPersonList(final String fTempList){
		personList= new ArrayList<PlusCopyPersonInfo>();
		if(!StringUtils.isEmpty(fTempList)){
			try {
				Type listType = new TypeToken<ArrayList<PlusCopyPersonInfo>>(){}.getType();
				Gson gson = new GsonBuilder().create();
				JSONArray jsonArray= new JSONArray(fTempList);
				personList=gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	* @date 2014��9��25�� ����2:05:27
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.pluscopy_search_person));
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
		pArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		pArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showSuggestions(newText);
		return false;
	}
	
	/** 
	* @Title: showSuggestions 
	* @Description: ��ʾ��Ա������ʷ��¼
	* @param @param queryStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����10:54:22
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryPlusCopyPersonList(queryStr);
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
	* @Title: setListener 
	* @Description: ���ÿؼ��¼�������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��18�� ����12:16:01
	*/
	private void setListener(){
		
		//ѡ����Ա ��������
		btnNotSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				skipSelected();	
				pArrayList.clear();
				new getPersonLocalAsync().execute();
			}
		});	
		
		//�Ѿ�ѡ����Ա
		btnYetSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sureSelected();
				renderPersonSelectedListView(personList);
			}
		});

		//ȫѡ��¼
		btnCheckAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String checkAllText=btnCheckAll.getText().toString();
				List<PlusCopyPersonInfo> soureList=pArrayList;
				if(fStatus==1){ //�Ѿ�ѡ����Ա
					soureList=personList;
				}
				if(soureList.size() > 0){	
					if("ȫѡ".equals(checkAllText)){
						btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_resetAll));
						setCheckAll(soureList);
					}
					else {
						btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
						setCancleAll(soureList);
					}
				}
				else{
					UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_not_checkAll));
				}
			}
		});
		
		//ȷ��
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(fStatus==0){  //ѡ����Ա
					sArrayList=getSelectedList(pArrayList);
				}
				else { //�Ѿ�ѡ����Ա
					sArrayList=getSelectedList(personList);
				}
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_notselected));
				}
				else{
					if(fStatus==0){  //��ѡ�����Ա���Ϊ��ѡ��
						personList.addAll(sArrayList);
						personList=ListHelper.rDPlusCopyPerson(personList); //ȥ��
						for (PlusCopyPersonInfo item : personList) {
							mDbHelper.insertPlusCopyPersonSearch(item); 	//���������¼���������ݿ���
						}
						UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_selected));
						// �ӳ�2�������Ѿ�ѡ��ҳ��
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	sureSelected();  
				            	renderPersonSelectedListView(personList);
				            }
				        }, 1000);	
					}
					else{  //������ѡ����Ա�б�
						for (PlusCopyPersonInfo item : personList) {
							mDbHelper.insertPlusCopyPersonSearch(item); 	//���������¼���������ݿ���
						}
						Intent intent = new Intent();
						intent.putExtra(AppContext.PLUSCOPY_PERSON_LIST, PlusCopyPersonInfo.ConvertToJson(personList));
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
	}
	
	/** 
	* @Title: skipSelected 
	* @Description:��ת��ѡ����Աҳ���� 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����10:58:35
	*/
	private void skipSelected(){
		fStatus=0;
		btnNotSelect.setBackgroundResource (R.drawable.tabs_active);
		btnNotSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_active);
		
		btnYetSelect.setBackgroundResource (R.drawable.tabs_default_right);
		btnYetSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_default_right);
		
		btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
		btnConfirm.setText(getResources().getString(R.string.pluscopy_person_btnSelect));
	}
	
	/** 
	* @Title: sureSelected 
	* @Description: ��ת����ѡ����Աҳ����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����10:58:49
	*/
	private void sureSelected(){
		fStatus=1;
		btnYetSelect.setBackgroundResource (R.drawable.tabs_active);
		btnYetSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_active);
		
		btnNotSelect.setBackgroundResource (R.drawable.tabs_default_left);
		btnNotSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_default_left);
	
		btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
		btnConfirm.setText(getResources().getString(R.string.pluscopy_person_btnSure));
	}
	
	/** 
	* @Title: getSelectedList 
	* @Description: ��ȡѡ����û�����
	* @param @param sourceList ѡ�е�����Դ����
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:00:45
	*/
	private List<PlusCopyPersonInfo> getSelectedList(final List<PlusCopyPersonInfo> sourceList){
		List<PlusCopyPersonInfo> selectList=new ArrayList<PlusCopyPersonInfo>();
		for (int i = 0; i < sourceList.size(); i++) {
			if(mAdapter.getIsSelected().get(i)){  //˵��ѡ����
				selectList.add(sourceList.get(i));
			}
		}
		return selectList;
	}
	
	/** 
	* @Title: setCheckAll 
	* @Description: ȫѡ��ť�¼�
	* @param @param sourceList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:01:36
	*/
	private void setCheckAll(final List<PlusCopyPersonInfo> sourceList){
		//����list�ĳ��ȣ���mAdapter�е�mapֵȫ����Ϊtrue  
        for (int i = 0; i < sourceList.size(); i++) { 
        	if("-1".equals(sourceList.get(i).getFItemNumber())){
        		mAdapter.getIsSelected().put(i, false);  
        	}
        	else {
        		mAdapter.getIsSelected().put(i, true);  
			}
        }  
        mAdapter.refreshView(sourceList);
	}

	/** 
	* @Title: setCancleAll 
	* @Description: ȡ����ť�¼�
	* @param @param sourceList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:01:42
	*/
	private void setCancleAll(final List<PlusCopyPersonInfo> sourceList){  
		//����list�ĳ��ȣ�����ѡ�İ�ť��Ϊδѡ  
        for (int i = 0; i < sourceList.size(); i++) {  
            if (mAdapter.getIsSelected().get(i)) {  
            	mAdapter.getIsSelected().put(i, false);  
            }  
        }  
        mAdapter.refreshView(sourceList);
	}
	
	/**
	 * @ClassName getPersonLocalAsync
	 * @Description ��ǩ/����ģ���ȡ������Ա��ʷ��¼����
	 * @author 21291
	 * @date 2014��9��25�� ����11:06:54
	 */
	private class getPersonLocalAsync extends AsyncTask<Void,Void,List<PlusCopyPersonInfo>>{

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<PlusCopyPersonInfo> doInBackground(Void... params) {
			return getPersonLocalByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<PlusCopyPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonLocalListView(result);
		}	
	}
	
	/** 
	* @Title: getPersonLocalByPost 
	* @Description: ��ǩ/����ģ���ȡ������Ա��ʷ��¼ʵ�弯��
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:08:34
	*/
	private  List<PlusCopyPersonInfo> getPersonLocalByPost(){
		List<PlusCopyPersonInfo> list=mDbHelper.queryPlusCopyPersonAllList();
		int length=list.size();
		if(length > 0){
			list.add(length, new PlusCopyPersonInfo("-1",getResources().getString(R.string.history_record_search_clear)));
		}
		return list;
	}
	
	/** 
	* @Title: renderPersonLocalListView 
	* @Description: ��ǩ/����ģ���ʼ��������Ա��ʷ��¼�б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:09:43
	*/
	private void renderPersonLocalListView(final List<PlusCopyPersonInfo> listData){
		pArrayList.addAll(listData);
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,pArrayList,R.layout.pluscopy_person_item,false);
		pListView.setAdapter(mAdapter);
	}
	
	/** 
	* @Title: onFItemNameClick 
	* @Description: ��Ա���Ƶ���¼�
	* @param @param view     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��29�� ����12:11:46
	*/
	public void onFItemNameClick(View view){
		final PlusCopyPersonInfo personInfo = (PlusCopyPersonInfo) view.getTag();
		if("-1".equals(personInfo.getFItemNumber())){	//���������¼
			mDbHelper.deletePlusCopyPersonSearchAll();
			pArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.history_record_clear_success));
		}
	}
	
	/** 
	* @Title: renderPersonSelectedListView 
	* @Description: ��ǩ/����ģ���Ѿ�ѡ����Ա�б����
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:15:46
	*/
	private void renderPersonSelectedListView(final List<PlusCopyPersonInfo> listData){
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,listData,R.layout.pluscopy_person_item,true);
		pListView.setAdapter(mAdapter);
	}
	
	/**
	 * @ClassName getPersonSearchAsync
	 * @Description ��ǩ/����ģ���첽��ȡ��Ա������ʵ�弯��
	 * @author 21291
	 * @date 2014��9��25�� ����11:16:35
	 */
	private class getPersonSearchAsync extends AsyncTask<Void, Void, List<PlusCopyPersonInfo>>{

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}
		
		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<PlusCopyPersonInfo> doInBackground(Void... params) {
			return getPersonListByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<PlusCopyPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonListView(result);
			dialog.dismiss(); 	// ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getPersonListByPost 
	* @Description: ��ǩ/����ģ���ȡ��Ա������ʵ�弯��ҵ���߼�����
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:17:19
	*/
	private  List<PlusCopyPersonInfo> getPersonListByPost(){
		return rBusiness.getPlusCopyPersonList(fQueryStr);
	}
	
	/** 
	* @Title: renderPersonListView 
	* @Description: ��ǩ/����ģ���ʼ����Ա�������б�
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����11:18:32
	*/
	private void renderPersonListView(final List<PlusCopyPersonInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(PlusCopyPersonActivity.this, getString(R.string.pluscopy_search_person_netparseerror),3000);
			return;
		}
		pArrayList.addAll(listData);
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,pArrayList,R.layout.pluscopy_person_item,true);
		pListView.setAdapter(mAdapter);
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
