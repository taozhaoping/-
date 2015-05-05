package com.dahuatech.app.ui.meeting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ActivityManager;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MeetingPersonAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingPersonListActivity
 * @Description �ҵĻ�����Ա�����б�ҳ��
 * @author 21291
 * @date 2014��9��11�� ����10:42:12
 */
public class MeetingPersonListActivity extends SherlockActivity implements
		SearchView.OnQueryTextListener, SearchView.OnSuggestionListener,
		OnRefreshListener<ListView> {
	private Button btnYetSelect, btnNotSelect, btnCheckAll, btnConfirm;
	private PullToRefreshListView mPullRefreshListView; // �б�
	private ProgressDialog dialog; // ������

	private MeetingPersonAdapter mAdapter; // ��������
	private List<MeetingPersonInfo> mArrayList; // ��������Դ����
	private List<MeetingPersonInfo> personList; // ��ѡ��Ĳ�����Ա�б�
	private List<MeetingPersonInfo> sArrayList; // ��ѡ�е�item��

	private String tempList; // �Ѿ�ѡ����Ա�����ַ���
	private MeetingPersonInfo mPersonInfo; // ����ʵ��
	private List<String> mQueryList; // ��ѯ��Ա������ʷ��¼����
	private DbManager mDbHelper; // ���ݿ������
	private MeetingBusiness mBusiness; // ҵ���߼���

	private static final String[] COLUMNS = { // ���ʹ���и�ʽ
	BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, };
	private MatrixCursor matrixCursor; // ��ʷ��¼����Դ
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView; // �����ؼ�
	private AppContext appContext; // ȫ��Context

	private String fQueryStr; // ��ѯ�ַ���
	private String fFlag = "All"; // ������Աʱ���ͱ�־ ALL-��ʾ���Դ���ȫ����Single��ʾ���ݵ��� Ĭ�ϴ���ȫ��
	private String serviceUrl; // �����ַ
	private int fPageIndex = 1; // ҳ��
	private final static int fPageSize = 10; // ҳ��С
	private int fStatus = 0; // Ĭ�Ͻ���ѡ����Ա�б�0-ѡ����Ա�б� 1-��ѡ��Ա�б�
	private int fRecordCount; // �ܵļ�¼��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbManager(this);
		mDbHelper.openSqlLite(); // �����ݿ�

		setContentView(R.layout.meeting_person_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		appContext = (AppContext) getApplication(); // ��ʼ��ȫ�ֱ���
		// �ж��Ƿ�����������
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}

		// ��ȡ�����ַ
		serviceUrl = AppUrl.URL_API_HOST_ANDROID_MEETINGPERSONLISTACTIVITY;
		// ��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tempList = extras.getString(AppContext.MEETING_DETAIL_PERSON_LIST);
			fFlag = extras.getString(AppContext.MEETING_DETAIL_PERSON_FLAG);
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
	private void initListData() {
		btnYetSelect = (Button) findViewById(R.id.meeting_person_list_yet_select);
		btnNotSelect = (Button) findViewById(R.id.meeting_person_list_not_select);
		btnCheckAll = (Button) findViewById(R.id.meeting_person_list_checkAll);
		btnConfirm = (Button) findViewById(R.id.meeting_person_list_confirm);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.meeting_person_list_pullToRefreshListView);
		mArrayList = new ArrayList<MeetingPersonInfo>();
		mQueryList = new ArrayList<String>();
		fQueryStr = "";

		// ��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness = FactoryBusiness
				.getFactoryBusiness(MeetingPersonListActivity.this);
		mBusiness = (MeetingBusiness) factoryBusiness.getInstance(
				"MeetingBusiness", serviceUrl);

		// �Ѿ�ѡ�еĲ�����Ա�б�
		showPersonList(tempList);
	}

	/**
	 * @Title: showPersonList
	 * @Description: �����Ѿ�ѡ�������Ա����
	 * @param @param fTempList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��18�� ����5:50:43
	 */
	private void showPersonList(final String fTempList) {
		personList = new ArrayList<MeetingPersonInfo>();
		if (!StringUtils.isEmpty(fTempList)) {
			try {
				Type listType = new TypeToken<ArrayList<MeetingPersonInfo>>() {
				}.getType();
				Gson gson = new GsonBuilder().create();
				JSONArray jsonArray = new JSONArray(fTempList);
				personList = gson.fromJson(jsonArray.toString(), listType);
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
	 * @date 2014��9��12�� ����10:34:28
	 */
	private void setupSearchView(MenuItem searchItem) {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			mSearchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
		}
		mSearchView.setIconifiedByDefault(false);
		mSearchView.setIconified(false);
		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setQueryHint(getResources().getString(
				R.string.meeting_search_person));
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
		fQueryStr = c.getString(c
				.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
		fPageIndex = 1;
		mArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute(fPageIndex);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr = query;
		fPageIndex = 1;
		mArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute(fPageIndex);
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
	 * @date 2014��9��12�� ����4:54:39
	 */
	private void showSuggestions(String queryStr) {
		if (!StringUtils.isEmpty(queryStr)) {
			mQueryList = mDbHelper.queryPersonList(queryStr);
			if (mQueryList.size() > 0) { // ˵���пͻ�������ʷ��¼
				matrixCursor = new MatrixCursor(COLUMNS);
				int i = 1;
				for (String item : mQueryList) {
					matrixCursor
							.addRow(new String[] { String.valueOf(i), item });
					i++;
				}
				mSuggestionsAdapter = new SuggestionsAdapter(
						getSupportActionBar().getThemedContext(), matrixCursor);
			} else {
				mSuggestionsAdapter = null;
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
	private void setListener() {

		// ѡ����Ա ��������
		btnNotSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				skipSelected();
				mArrayList.clear();
				new getPersonLocalAsync().execute();
			}
		});

		// �Ѿ�ѡ����Ա
		btnYetSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sureSelected();
				renderPersonSelectedListView(personList);
			}
		});

		// ȫѡ��¼
		btnCheckAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String checkAllText = btnCheckAll.getText().toString();
				List<MeetingPersonInfo> soureList = mArrayList;
				if (fStatus == 1) { // �Ѿ�ѡ����Ա
					soureList = personList;
				}
				if (soureList.size() > 0) {
					if ("ȫѡ".equals(checkAllText)) {
						btnCheckAll.setText(getResources().getString(
								R.string.meeting_person_list_btnResetAll));
						setCheckAll(soureList);
					} else {
						btnCheckAll.setText(getResources().getString(
								R.string.meeting_person_list_btnCheckAll));
						setCancleAll(soureList);
					}
				} else {
					UIHelper.ToastMessage(
							MeetingPersonListActivity.this,
							getResources().getString(
									R.string.meeting_person_list_not_checkAll));
				}
			}
		});

		// ȷ��
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fStatus == 0) { // ѡ����Ա
					sArrayList = getSelectedList(mArrayList);
				} else { // �Ѿ�ѡ����Ա
					sArrayList = getSelectedList(personList);
				}
				if (sArrayList.size() == 0) {
					UIHelper.ToastMessage(
							MeetingPersonListActivity.this,
							getResources().getString(
									R.string.meeting_person_list_notselected));
				} else {
					if (fStatus == 0) { // ��ѡ�����Ա���Ϊ��ѡ��
						if ("Single".equals(fFlag)) { // ˵���ǵ�������
							personList.clear();
						}
						
						personList.addAll(sArrayList);
						personList = ListHelper.rDMeetingPerson(personList); // ȥ��
						for (MeetingPersonInfo item : personList) {
							mDbHelper.insertPersonSearch(item); // ���������¼���������ݿ���
						}

						UIHelper.ToastMessage(
								MeetingPersonListActivity.this,
								getResources().getString(
										R.string.meeting_person_list_selected));
						// �ӳ�2�������Ѿ�ѡ��ҳ��
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								sureSelected();
								renderPersonSelectedListView(personList);
							}
						}, 1000);
					} else { // ������ѡ����Ա�б�
						List<MeetingPersonInfo> selectPersonList = getSelectedList(personList);
						for (MeetingPersonInfo item : personList) {
							mDbHelper.insertPersonSearch(item); // ���������¼���������ݿ���
						}
						Intent intent = new Intent();
						intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST,
								MeetingPersonInfo.ConvertToJson(selectPersonList));
						intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG,
								fFlag);
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
	}

	/**
	 * @Title: skipSelected
	 * @Description: ��ת��ѡ����Աҳ����
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��19�� ����9:09:35
	 */
	private void skipSelected() {
		fStatus = 0;
		btnNotSelect.setBackgroundResource(R.drawable.meeting_tabs_left_active);
		btnNotSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_left_active);

		btnYetSelect.setBackgroundResource(R.drawable.meeting_tabs_right);
		btnYetSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_right);

		btnCheckAll.setText(getResources().getString(
				R.string.meeting_person_list_btnCheckAll));
		btnConfirm.setText(getResources().getString(
				R.string.meeting_person_list_btnSelect));
	}

	/**
	 * @Title: sureSelected
	 * @Description: ��ת����ѡ����Աҳ����
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��19�� ����9:09:50
	 */
	private void sureSelected() {
		fStatus = 1;
		btnYetSelect
				.setBackgroundResource(R.drawable.meeting_tabs_right_active);
		btnYetSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_right_active);

		btnNotSelect.setBackgroundResource(R.drawable.meeting_tabs_left);
		btnNotSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_left);

		btnCheckAll.setText(getResources().getString(
				R.string.meeting_person_list_btnCheckAll));
		btnConfirm.setText(getResources().getString(
				R.string.meeting_person_list_btnSure));
	}

	/**
	 * @Title: getSelectedList
	 * @Description: ��ȡѡ����û�����
	 * @param @param sourceList ����Դ����
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��18�� ����2:21:31
	 */
	private List<MeetingPersonInfo> getSelectedList(
			final List<MeetingPersonInfo> sourceList) {
		List<MeetingPersonInfo> selectList = new ArrayList<MeetingPersonInfo>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (mAdapter.getIsSelected().get(i)) { // ˵��ѡ����
				selectList.add(sourceList.get(i));
			}
		}
		return selectList;
	}

	/**
	 * @Title: setCheckAll
	 * @Description: ȫѡ
	 * @param @param sourceList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��18�� ����2:36:10
	 */
	private void setCheckAll(final List<MeetingPersonInfo> sourceList) {
		// ����list�ĳ��ȣ���mAdapter�е�mapֵȫ����Ϊtrue
		for (int i = 0; i < sourceList.size(); i++) {
			if ("-1".equals(sourceList.get(i).getFItemNumber())) {
				mAdapter.getIsSelected().put(i, false);
			} else {
				mAdapter.getIsSelected().put(i, true);
			}
		}
		mAdapter.refreshView(sourceList);
	}

	/**
	 * @Title: setCancleAll
	 * @Description: ȡ��
	 * @param @param sourceList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��18�� ����2:41:41
	 */
	private void setCancleAll(final List<MeetingPersonInfo> sourceList) {
		// ����list�ĳ��ȣ�����ѡ�İ�ť��Ϊδѡ
		for (int i = 0; i < sourceList.size(); i++) {
			if (mAdapter.getIsSelected().get(i)) {
				mAdapter.getIsSelected().put(i, false);
			}
		}
		mAdapter.refreshView(sourceList);
	}

	/**
	 * @ClassName getPersonLocalAsync
	 * @Description ��ȡ������Ա��ʷ��¼����
	 * @author 21291
	 * @date 2014��9��12�� ����4:39:43
	 */
	private class getPersonLocalAsync extends
			AsyncTask<Void, Void, List<MeetingPersonInfo>> {

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MeetingPersonInfo> doInBackground(Void... params) {
			return getPersonLocalByPost();
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonLocalListView(result);
		}
	}

	/**
	 * @Title: getPersonLocalByPost
	 * @Description: ��ȡ������Ա��ʷ��¼ʵ�弯��
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����4:40:32
	 */
	private List<MeetingPersonInfo> getPersonLocalByPost() {
		List<MeetingPersonInfo> list = mDbHelper.queryPersonAllList();
		int length = list.size();
		if (length > 0) {
			list.add(length, new MeetingPersonInfo("-1", getResources()
					.getString(R.string.history_record_search_clear), "", 0));
		}
		return list;
	}

	/**
	 * @Title: renderPersonLocalListView
	 * @Description: ��ʼ��������Ա��ʷ��¼�б�
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����4:43:23
	 */
	private void renderPersonLocalListView(
			final List<MeetingPersonInfo> listData) {
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				mArrayList, R.layout.meeting_person_list_item, false);
		mPullRefreshListView.setAdapter(mAdapter);

		// �������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) { // ���������Ч
					return;
				}

				mAdapter.setSelectItem(position - 1);
				mAdapter.notifyDataSetInvalidated();

				mPersonInfo = null;
				if (view instanceof RelativeLayout) { // ˵������RelativeLayout������
					mPersonInfo = (MeetingPersonInfo) parent
							.getItemAtPosition(position);
				} else {
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					mPersonInfo = (MeetingPersonInfo) reLayout.getTag();
				}
				if (mPersonInfo == null) {
					return;
				}

				// �ж��Ƿ��������ʷ��¼
				if ("-1".equals(mPersonInfo.getFItemNumber())) { // ���������¼
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					MeetingPersonListActivity.this.onFItemNameClick(reLayout);

					return;
				}

				mDbHelper.closeSqlLite();
				List<MeetingPersonInfo> transferList = new ArrayList<MeetingPersonInfo>();
				transferList.add(mPersonInfo);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(AppContext.MEETING_DETAIL_PERSON_LIST,
						MeetingPersonInfo.ConvertToJson(transferList));
				bundle.putString(AppContext.MEETING_DETAIL_PERSON_FLAG, fFlag);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	public void clearPersonSearchAll(View view) {

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
	public void onFItemNameClick(View view) {
		final MeetingPersonInfo mePersonInfo = (MeetingPersonInfo) view
				.getTag();
		if ("-1".equals(mePersonInfo.getFItemNumber())) { // ���������¼
			mDbHelper.deletePersonSearchAll();
			mArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(
					MeetingPersonListActivity.this,
					getResources().getString(
							R.string.history_record_clear_success));
		}
	}

	/**
	 * @Title: renderPersonSelectedListView
	 * @Description: �Ѿ�ѡ����Ա�б����
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��18�� ����1:52:46
	 */
	private void renderPersonSelectedListView(
			final List<MeetingPersonInfo> listData) {
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				listData, R.layout.meeting_person_list_item, true);
		mPullRefreshListView.setAdapter(mAdapter);
	}

	/**
	 * @ClassName getPersonSearchAsync
	 * @Description �첽��ȡ��Ա������ʵ�弯��
	 * @author 21291
	 * @date 2014��9��12�� ����4:47:34
	 */
	private class getPersonSearchAsync extends
			AsyncTask<Integer, Void, List<MeetingPersonInfo>> {

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<MeetingPersonInfo> doInBackground(Integer... params) {
			return getPersonListByPost(params[0]);
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonListView(result);
			dialog.dismiss(); // ���ٵȴ���
		}
	}

	/**
	 * @Title: getPersonListByPost
	 * @Description: ��ȡ��Ա������ʵ�弯��ҵ���߼�����
	 * @param @param pageIndex
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����4:48:00
	 */
	private List<MeetingPersonInfo> getPersonListByPost(final int pageIndex) {
		MeetingSearchParamInfo mSearchParamInfo = MeetingSearchParamInfo
				.getMeetingSearchParamInfo();
		mSearchParamInfo.setFItemNumber("");
		mSearchParamInfo.setFQueryText(fQueryStr);
		mSearchParamInfo.setFPageIndex(pageIndex);
		mSearchParamInfo.setFPageSize(fPageSize);
		mSearchParamInfo.setFSelectedDate("");
		mSearchParamInfo.setFSelectedStart("");
		mSearchParamInfo.setFSelectedEnd("");

		return mBusiness.getPersonList(mSearchParamInfo);
	}

	/**
	 * @Title: renderPersonListView
	 * @Description: ��ʼ����Ա�������б�
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����4:48:15
	 */
	private void renderPersonListView(final List<MeetingPersonInfo> listData) {
		if (listData.size() == 0) {
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingPersonListActivity.this,
					getString(R.string.meeting_search_person_netparseerror),
					3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount = mArrayList.get(0).getFRecordCount();
		if (mArrayList.size() < fRecordCount) {
			mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		} else {
			mPullRefreshListView.setMode(Mode.DISABLED);
		}
		mPullRefreshListView.getRefreshableView().setSelector(
				android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);

		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				mArrayList, R.layout.meeting_person_list_item, true);
		mPullRefreshListView.setAdapter(mAdapter);

		// �������¼�
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) { // ���������Ч
					return;
				}
				mAdapter.setSelectItem(position - 1);
				mAdapter.notifyDataSetInvalidated();

				mPersonInfo = null;
				if (view instanceof RelativeLayout) { // ˵������RelativeLayout������
					mPersonInfo = (MeetingPersonInfo) parent
							.getItemAtPosition(position);
				} else {
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					mPersonInfo = (MeetingPersonInfo) reLayout.getTag();
				}
				if (mPersonInfo == null) {
					return;
				}
				mDbHelper.insertPersonSearch(mPersonInfo); // ���������¼���������ݿ���
				List<MeetingPersonInfo> transferList = new ArrayList<MeetingPersonInfo>();
				transferList.add(mPersonInfo);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(AppContext.MEETING_DETAIL_PERSON_LIST,
						MeetingPersonInfo.ConvertToJson(transferList));
				bundle.putString(AppContext.MEETING_DETAIL_PERSON_FLAG, fFlag);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// �������ظ���
		mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(
				getResources().getString(
						R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy().setPullLabel(
				getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
				getResources()
						.getString(R.string.pull_to_refresh_release_label));
		onPullUpListView();
	}

	/**
	 * @Title: onPullUpListView
	 * @Description: �������ظ��� �����¼���10������
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����4:52:43
	 */
	public void onPullUpListView() {
		fPageIndex++; // ��ҳ
		new pullUpRefreshAsync().execute(fPageIndex);
	}

	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description �����첽���ظ���
	 * @author 21291
	 * @date 2014��9��12�� ����4:52:33
	 */
	private class pullUpRefreshAsync extends
			AsyncTask<Integer, Void, List<MeetingPersonInfo>> {

		@Override
		protected List<MeetingPersonInfo> doInBackground(Integer... params) {
			return getPersonListByPost(params[0]); // ��Ҫ����ɺ�ʱ����
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			if (result.size() == 0) {
				UIHelper.ToastMessage(
						MeetingPersonListActivity.this,
						getString(R.string.meeting_search_person_netparseerror),
						3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			fRecordCount = mArrayList.get(0).getFRecordCount();
			if (mArrayList.size() < fRecordCount) {
				mPullRefreshListView.setMode(Mode.PULL_FROM_END);
			} else {
				mPullRefreshListView.setMode(Mode.DISABLED);
			}
			mAdapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onDestroy() {
		if (mDbHelper != null) {
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
