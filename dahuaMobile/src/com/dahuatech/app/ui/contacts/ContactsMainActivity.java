package com.dahuatech.app.ui.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ContactsAdapter;
import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.ContactInfo.ContactResultInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.business.ContactsBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.ui.meeting.MeetingPersonListActivity;

/**
 * @ClassName ContactsMainActivity
 * @Description ͨѶ¼��ҳ��Activity
 * @author 21291
 * @date 2014��6��26�� ����1:48:52
 */
public class ContactsMainActivity extends MenuActivity {
	private static ContactsMainActivity mInstance;

	private ProgressDialog dialog; // ���̿�
	private EditText searchText; // �����ı���
	private ImageButton btnSearch; // ��ť
	private ListView mListView; // �б�ؼ�����

	private ContactsAdapter mAdapter; // ������
	private ContactsBusiness contactsBusiness; // ҵ���߼���
	private String fItemNumber; // Ա����
	private String fSourceType; // ��Դ����

	private String serviceUrl; // �����ַ
	private AppContext appContext; // ȫ��Context

	private DbManager mDbHelper; // ���ݿ������

	public static ContactsMainActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.contacts_main);

		mDbHelper = new DbManager(this);
		mDbHelper.openSqlLite();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// ��ʼ��ȫ�ֱ���
		appContext = (AppContext) getApplication();
		// ��������
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}

		// ��ȡ�����ַ
		serviceUrl = AppUrl.URL_API_HOST_ANDROID_CONTACTSMAINACTIVITY;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			fItemNumber = extras.getString(AppContext.FITEMNUMBER_KEY);
			fSourceType = extras.getString(AppContext.CONTACTS_SOURCE_TYPE);
		}

		// ��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness = FactoryBusiness
				.getFactoryBusiness(ContactsMainActivity.this);
		contactsBusiness = (ContactsBusiness) factoryBusiness.getInstance(
				"ContactsBusiness", serviceUrl);

		mListView = (ListView) findViewById(R.id.contacts_listView); // ʵ����ListView
		searchText = (EditText) findViewById(R.id.contacts_searchEditText); // �����ı�
		btnSearch = (ImageButton) findViewById(R.id.contacts_searchImageButton);

		// �������
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����������
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
				showSearch(searchText.getText().toString());
				searchText.setText("");
				searchText.setHint(getResources().getString(
						R.string.contacts_search));
				sendLogs("query"); // ������־��Ϣ����ͳ��
			}
		});
		sendLogs("access"); // ������־��Ϣ����ͳ��

		// ���ر�������
		initContactLocalByPost();

		// ѡ�������¼�
		/*
		 * mListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { if (position == 0) { // ���������Ч return; }
		 * 
		 * mAdapter.notifyDataSetInvalidated(); ContactInfo contactInfo = null;
		 * if (view instanceof RelativeLayout) { // ˵������RelativeLayout������
		 * contactInfo = (ContactInfo) parent .getItemAtPosition(position); }
		 * else { RelativeLayout reLayout = (RelativeLayout) view
		 * .findViewById(R.id.contactslist_tablelayout); contactInfo =
		 * (ContactInfo) reLayout.getTag(); } if (contactInfo == null) { return;
		 * } mDbHelper.deleteContactSearchAll(); } });
		 */

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
	private void initContactLocalByPost() {
		List<ContactInfo> list = mDbHelper.queryContactAllList();
		int length = list.size();
		if (length > 0) {
			list.add(
					length,
					new ContactInfo("0", getResources().getString(
							R.string.history_record_search_clear), "", "", ""));
		}
		// ͨѶ¼
		int Resource = R.layout.contacts_listlayout;
		if ("sms_search".equals(fSourceType)) {
			// ����ͬ��
			Resource = R.layout.contacts_item_invitation;
		}
		mAdapter = new ContactsAdapter(this, list, Resource, mDbHelper);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * @Title: showSearch
	 * @Description: ��ʾ��������
	 * @param @param fSearchText
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��6��26�� ����2:05:44
	 */
	private void showSearch(String fSearchText) {
		// ����һ���ȴ�������ʾ�û��ȴ�
		dialog = new ProgressDialog(this);
		dialog.setMessage("���ڲ�ѯ�У����Ժ�...");
		dialog.setCancelable(false);
		// ��������
		new ContactsAsyncClient().execute(fSearchText);
	}

	/**
	 * @ClassName ContactsAsyncClient
	 * @Description �첽��ȡ����
	 * @author 21291
	 * @date 2014��6��26�� ����6:22:51
	 */
	private class ContactsAsyncClient extends
			AsyncTask<String, Integer, ContactResultInfo> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ContactResultInfo result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // ���ٵȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ContactResultInfo doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}

	/**
	 * @Title: getListByPost
	 * @Description: ͨ��POST��ʽ��ȡ����Դ
	 * @param @param fSearchText �����ı���ֵ
	 * @param @return
	 * @return ContactResultInfo
	 * @throws
	 * @author 21291
	 * @date 2014��6��26�� ����6:22:38
	 */
	private ContactResultInfo getListByPost(String fSearchText) {
		return contactsBusiness.getContactsList(fSearchText);
	}

	/**
	 * 
	 * @Title: dataClear
	 * @Description: TODO(������һ�仰�����������������)
	 * @param @param view ����
	 * @return void ��������
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void dataClear(View view) {
		System.out.println("ContactsMainActivity.dataClear()");
		mDbHelper.deleteContactSearchAll();
		mListView.setAdapter(null);
		// adpterˢ��
		// mAdapter.notifyDataSetChanged();
	}

	/**
	 * @Title: renderListView
	 * @Description: ��ʼ����������
	 * @param @param contactResultInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��6��26�� ����6:22:25
	 */
	private void renderListView(ContactResultInfo contactResultInfo) {
		if (!StringUtils.isEmpty(contactResultInfo.resultStr)) { // �����ȡ��������
			if ("1".equals(contactResultInfo.resultStr)) {
				UIHelper.ToastMessage(ContactsMainActivity.this,
						R.string.contacts_twoword);
			}
			if ("2".equals(contactResultInfo.resultStr)) {
				UIHelper.ToastMessage(ContactsMainActivity.this,
						R.string.contacts_notfind);
			}
		}
		// ����ListView��adapter
		if (contactResultInfo.contactList.size() > 0) {
			// ʹ�����ݼ�����adapter����
			// ͨѶ¼
			int Resource = R.layout.contacts_listlayout;
			if ("sms_search".equals(fSourceType)) {
				// ����ͬ��
				Resource = R.layout.contacts_item_invitation;
			}
			mAdapter = new ContactsAdapter(this, contactResultInfo.contactList,
					Resource, mDbHelper);
			mListView.setAdapter(mAdapter);
		} else {
			mListView.setAdapter(null);
		}
	}

	/**
	 * @Title: sendLogs
	 * @Description: ���ͨѶ¼ʱ��������־��¼��������
	 * @param @param typeName ��������
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��7��31�� ����3:06:44
	 */
	private void sendLogs(final String typeName) {
		LogsRecordInfo logInfo = LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_contacts));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(ContactsMainActivity.this, logInfo);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbHelper.closeSqlLite();
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
