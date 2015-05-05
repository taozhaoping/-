package com.dahuatech.app.common;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.develophour.DHProjectInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchContactInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;

/**
 * @ClassName DbManager
 * @Description ���ݿ������
 * @author 21291
 * @date 2014��9��2�� ����11:22:09
 */
public class DbManager {

	public static final String KEY_ROWID = "_id"; // ��������
	// ����ģ��
	public static final String KEY_CLIENTID = "clientId"; // �ͻ�ID
	public static final String KEY_CLIENTNAME = "clientName"; // �ͻ�����
	public static final String KEY_PROJECTID = "projectId"; // ��ĿID
	public static final String KEY_PROJECTNAME = "projectName"; // ��Ŀ����

	public static final String KEY_DETAIL_FSERVERID = "fServerId"; // ��������������
	public static final String KEY_DETAIL_FPAYTYPE = "fPayType"; // ֧������
	public static final String KEY_DETAIL_FITEMNUMBER = "fItemNumber"; // Ա������
	public static final String KEY_DETAIL_FEXPENDTIME = "fExpendTime"; // ����ʱ��
	public static final String KEY_DETAIL_FEXPENDTYPEPARENT = "fExpendTypeParent"; // �������͸���
	public static final String KEY_DETAIL_FEXPENDTYCHILD = "fExpendTypeChild"; // ������������
	public static final String KEY_DETAIL_FEXPENDADDRESS = "fExpendAddress"; // ���ѵص�
	public static final String KEY_DETAIL_FEXPENDAMOUNT = "fExpendAmount"; // ���ѽ��
	public static final String KEY_DETAIL_FCAUSE = "fCause"; // ����
	public static final String KEY_DETAIL_FCLIENTID = "fClientId"; // �ͻ�
	public static final String KEY_DETAIL_FPROJECTID = "fProjectId"; // ��Ŀ
	public static final String KEY_DETAIL_FCLIENT = "fClient"; // �ͻ�
	public static final String KEY_DETAIL_FPROJECT = "fProject"; // ��Ŀ
	public static final String KEY_DETAIL_FACCOMPANY = "fAccompany"; // ������ͬ
	public static final String KEY_DETAIL_FACCOMPANYREASON = "fAccompanyReason"; // ����ͬԭ�����ͬ��Ա
	public static final String KEY_DETAIL_FSTART = "fStart"; // ������
	public static final String KEY_DETAIL_FDESTINATION = "fDestination"; // Ŀ�ĵ�
	public static final String KEY_DETAIL_FSTARTTIME = "fStartTime"; // ����ʱ��
	public static final String KEY_DETAIL_FENDTIME = "fEndTime"; // ����ʱ��
	public static final String KEY_DETAIL_FBUSINESSLEVEL = "fBusinessLevel"; // �����
	public static final String KEY_DETAIL_FREASON = "fReason"; // δˢ��ԭ��
	public static final String KEY_DETAIL_FDESCRIPTION = "fDescription"; // ˵��
	public static final String KEY_DETAIL_FUPLOADFLAG = "fUploadFlag"; // ���ػ��Ѿ��ϴ���־

	// �ҵĻ���ģ��
	public static final String KEY_MEETING_ROOMID = "roomId"; // ��������������
	public static final String KEY_MEETING_ROOMNAME = "roomName"; // ����������
	public static final String KEY_MEETING_ROOMIP = "roomIp"; // ������IP

	public static final String KEY_MEETING_FITEMNUMBER = "fItemNumber"; // Ա����
	public static final String KEY_MEETING_FITEMNAME = "fItemName"; // Ա������
	public static final String KEY_MEETING_FDEPTNAME = "fDeptName"; // ��������

	// ��ǩ/����ģ��
	public static final String KEY_PLUSCOPY_FITEMNUMBER = "fItemNumber"; // Ա����
	public static final String KEY_PLUSCOPY_FITEMNAME = "fItemName"; // Ա������

	// �з���ʱģ��
	public static final String KEY_DEVELOP_HOURS_PROJECTCODE = "projectCode"; // ��Ŀ���
	public static final String KEY_DEVELOP_HOURS_PROJECTNAME = "projectName"; // ��Ŀ����

	// �ҵ�����ģ��-���۲�ѯ
	public static final String KEY_MARKET_BID_FCUSTOMERNAME = "fcustomername"; // �ͻ�����
	public static final String KEY_MARKET_BID_FBIDCODE = "fbidcode"; // ���۵���

	// �ҵ�����ģ��-��ͬ��ѯ
	public static final String KEY_MARKET_CONTRACT_FCUSTOMERNAME = "fcustomername"; // �ͻ�����
	public static final String KEY_MARKET_CONTRACT_FCONTRACTCODE = "fcontractcode"; // ��ͬ����

	// ͨѶ¼ģ��
	public static final String KEY_MARKET_CONTACT_FCORNET = "fCornet"; // Ա���̺�
	public static final String KEY_MARKET_CONTACT_MAIL = "fEmail"; // Ա���̺�

	private DBUtils mDbHelper;
	private SQLiteDatabase mDb;
	private Context mContext;

	public DbManager(Context context) {
		this.mContext = context;
	}

	/**
	 * @Title: openSqlLite
	 * @Description: �����ݿ�
	 * @param @return
	 * @param @throws SQLException
	 * @return DbManager
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����11:29:10
	 */
	public DbManager openSqlLite() throws SQLException {
		// ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����ExpenseFlowAdapter�Ĳ������Activity��onCreate��
		mDbHelper = new DBUtils(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	/**
	 * @Title: closeSqlLite
	 * @Description: �ر����ݿ�
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����11:29:42
	 */
	public void closeSqlLite() {
		mDbHelper.close();
		mDb.close();
	}

	/**
	 * @Title: insertClientSearch
	 * @Description: ����ͻ��б�������¼
	 * @param @param eInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��17�� ����5:09:01
	 */
	public void insertClientSearch(ExpenseFlowItemInfo eInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_CLIENT_SEARCH + " WHERE 1=1 AND "
					+ KEY_CLIENTNAME + " = " + "\"" + eInfo.getFItemName()
					+ "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_CLIENTID, eInfo.getFId());
				cv.put(KEY_CLIENTNAME, eInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: insertProjectSearch
	 * @Description: ������Ŀ�б�������¼
	 * @param @param eInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��17�� ����5:09:58
	 */
	public void insertProjectSearch(ExpenseFlowItemInfo eInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_PROJECT_SEARCH + " WHERE 1=1 AND "
					+ KEY_PROJECTNAME + " = " + "\"" + eInfo.getFItemName()
					+ "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_PROJECTID, eInfo.getFId());
				cv.put(KEY_PROJECTNAME, eInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deleteClientSearchAll
	 * @Description: ���ȫ���ͻ���ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����2:12:21
	 */
	public void deleteClientSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, null, null);
	}

	/**
	 * @Title: deleteProjectSearchAll
	 * @Description: ���ȫ����Ŀ��ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����2:12:04
	 */
	public void deleteProjectSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, null, null);
	}

	/**
	 * @Title: queryLocalList
	 * @Description: ��ȡ���ؿͻ�/��Ŀ������¼����Դ
	 * @param @param type ��ȡ����
	 * @param @return
	 * @return List<ExpenseFlowItemInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����7:28:18
	 */
	public List<ExpenseFlowItemInfo> queryLocalList(String type) {
		List<ExpenseFlowItemInfo> list = new ArrayList<ExpenseFlowItemInfo>();
		Cursor c = null;
		if ("client".equals(type)) {
			c = queryHistory(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, "",
					KEY_CLIENTNAME, "30");
			while (c.moveToNext()) {
				ExpenseFlowItemInfo eItemInfo = new ExpenseFlowItemInfo();
				eItemInfo.setFId(c.getString(c.getColumnIndex(KEY_CLIENTID)));
				eItemInfo.setFItemName(c.getString(c
						.getColumnIndex(KEY_CLIENTNAME)));
				list.add(eItemInfo);
			}
		} else {
			c = queryHistory(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, "",
					KEY_PROJECTNAME, "30");
			while (c.moveToNext()) {
				ExpenseFlowItemInfo eItemInfo = new ExpenseFlowItemInfo();
				eItemInfo.setFId(c.getString(c.getColumnIndex(KEY_PROJECTID)));
				eItemInfo.setFItemName(c.getString(c
						.getColumnIndex(KEY_PROJECTNAME)));
				list.add(eItemInfo);
			}
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryClientList
	 * @Description: ��ȡ�ͻ�������ʷ��¼����
	 * @param @param queryStr ��ѯ����
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����2:58:05
	 */
	public List<String> queryClientList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, queryStr,
				KEY_CLIENTNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_CLIENTNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryProjectList
	 * @Description: ��ȡ��Ŀ������ʷ��¼����
	 * @param @param queryStr ��ѯ����
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����3:00:45
	 */
	public List<String> queryProjectList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_PROJECT_SEARCH,
				queryStr, KEY_PROJECTNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_PROJECTNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: insertFlowDetail
	 * @Description: �����ҵ���ˮ�����¼
	 * @param @param exDetailInfo
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014��9��5�� ����11:29:51
	 */
	public boolean insertFlowDetail(ExpenseFlowDetailInfo exDetailInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DETAIL_FSERVERID, exDetailInfo.getFServerId());
			cv.put(KEY_DETAIL_FPAYTYPE, exDetailInfo.getFPayType());
			cv.put(KEY_DETAIL_FITEMNUMBER, exDetailInfo.getFItemNumber());
			cv.put(KEY_DETAIL_FEXPENDTIME, exDetailInfo.getFExpendTime());
			cv.put(KEY_DETAIL_FEXPENDTYPEPARENT,
					exDetailInfo.getFExpendTypeParent());
			cv.put(KEY_DETAIL_FEXPENDTYCHILD,
					exDetailInfo.getFExpendTypeChild());
			cv.put(KEY_DETAIL_FEXPENDADDRESS, exDetailInfo.getFExpendAddress());
			cv.put(KEY_DETAIL_FEXPENDAMOUNT, exDetailInfo.getFExpendAmount());
			cv.put(KEY_DETAIL_FCAUSE, exDetailInfo.getFCause());
			cv.put(KEY_DETAIL_FCLIENTID, exDetailInfo.getFClientId());
			cv.put(KEY_DETAIL_FPROJECTID, exDetailInfo.getFProjectId());
			cv.put(KEY_DETAIL_FCLIENT, exDetailInfo.getFClient());
			cv.put(KEY_DETAIL_FPROJECT, exDetailInfo.getFProject());
			cv.put(KEY_DETAIL_FACCOMPANY, exDetailInfo.getFAccompany());
			cv.put(KEY_DETAIL_FACCOMPANYREASON,
					exDetailInfo.getFAccompanyReason());
			cv.put(KEY_DETAIL_FSTART, exDetailInfo.getFStart());
			cv.put(KEY_DETAIL_FDESTINATION, exDetailInfo.getFDestination());
			cv.put(KEY_DETAIL_FSTARTTIME, exDetailInfo.getFStartTime());
			cv.put(KEY_DETAIL_FENDTIME, exDetailInfo.getFEndTime());
			cv.put(KEY_DETAIL_FBUSINESSLEVEL, exDetailInfo.getFBusinessLevel());
			cv.put(KEY_DETAIL_FREASON, exDetailInfo.getFReason());
			cv.put(KEY_DETAIL_FDESCRIPTION, exDetailInfo.getFDescription());
			cv.put(KEY_DETAIL_FUPLOADFLAG, exDetailInfo.getFUploadFlag());
			long ret = mDb.insert(DBUtils.DATABASE_TABLE_FLOW_DETAIL, null, cv);
			mDb.setTransactionSuccessful();
			if (ret > 0) {
				return true;
			}
			return false;
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: updateFlowDetail
	 * @Description: �޸��Ѵ��ڵ���ˮ�����¼
	 * @param @param exDetailInfo
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014��9��5�� ����11:27:41
	 */
	public boolean updateFlowDetail(ExpenseFlowDetailInfo exDetailInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DETAIL_FSERVERID, exDetailInfo.getFServerId());
			cv.put(KEY_DETAIL_FEXPENDTIME, exDetailInfo.getFExpendTime());
			cv.put(KEY_DETAIL_FEXPENDTYPEPARENT,
					exDetailInfo.getFExpendTypeParent());
			cv.put(KEY_DETAIL_FEXPENDTYCHILD,
					exDetailInfo.getFExpendTypeChild());
			cv.put(KEY_DETAIL_FEXPENDADDRESS, exDetailInfo.getFExpendAddress());
			cv.put(KEY_DETAIL_FEXPENDAMOUNT, exDetailInfo.getFExpendAmount());
			cv.put(KEY_DETAIL_FCAUSE, exDetailInfo.getFCause());
			cv.put(KEY_DETAIL_FCLIENTID, exDetailInfo.getFClientId());
			cv.put(KEY_DETAIL_FPROJECTID, exDetailInfo.getFProjectId());
			cv.put(KEY_DETAIL_FCLIENT, exDetailInfo.getFClient());
			cv.put(KEY_DETAIL_FPROJECT, exDetailInfo.getFProject());
			cv.put(KEY_DETAIL_FACCOMPANY, exDetailInfo.getFAccompany());
			cv.put(KEY_DETAIL_FACCOMPANYREASON,
					exDetailInfo.getFAccompanyReason());
			cv.put(KEY_DETAIL_FSTART, exDetailInfo.getFStart());
			cv.put(KEY_DETAIL_FDESTINATION, exDetailInfo.getFDestination());
			cv.put(KEY_DETAIL_FSTARTTIME, exDetailInfo.getFStartTime());
			cv.put(KEY_DETAIL_FENDTIME, exDetailInfo.getFEndTime());
			cv.put(KEY_DETAIL_FBUSINESSLEVEL, exDetailInfo.getFBusinessLevel());
			cv.put(KEY_DETAIL_FREASON, exDetailInfo.getFReason());
			cv.put(KEY_DETAIL_FDESCRIPTION, exDetailInfo.getFDescription());
			cv.put(KEY_DETAIL_FUPLOADFLAG, exDetailInfo.getFUploadFlag());
			long ret = mDb.update(DBUtils.DATABASE_TABLE_FLOW_DETAIL, cv,
					KEY_ROWID + "=" + exDetailInfo.getFLocalId(), null);
			mDb.setTransactionSuccessful();
			if (ret > 0) {
				return true;
			}
			return false;
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: queryFlowDetailList
	 * @Description: ��ȡ�ݴ��ҵ���ˮ�����б�
	 * @param @return
	 * @return List<ExpenseFlowDetailInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��5�� ����11:38:23
	 */
	public List<ExpenseFlowDetailInfo> queryFlowDetailList() {
		ArrayList<ExpenseFlowDetailInfo> list = new ArrayList<ExpenseFlowDetailInfo>();
		Cursor c = queryFlowDetailAll();
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					ExpenseFlowDetailInfo eInfo = new ExpenseFlowDetailInfo();
					eInfo.setFLocalId(c.getInt(c.getColumnIndex(KEY_ROWID)));
					eInfo.setFServerId(c.getInt(c
							.getColumnIndex(KEY_DETAIL_FSERVERID)));
					eInfo.setFPayType(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPAYTYPE)));
					eInfo.setFItemNumber(c.getString(c
							.getColumnIndex(KEY_DETAIL_FITEMNUMBER)));
					eInfo.setFExpendTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTIME)));
					eInfo.setFExpendTypeParent(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTYPEPARENT)));
					eInfo.setFExpendTypeChild(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTYCHILD)));
					eInfo.setFExpendAddress(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDADDRESS)));
					eInfo.setFExpendAmount(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDAMOUNT)));
					eInfo.setFCause(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCAUSE)));
					eInfo.setFClientId(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCLIENTID)));
					eInfo.setFProjectId(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPROJECTID)));
					eInfo.setFClient(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCLIENT)));
					eInfo.setFProject(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPROJECT)));
					eInfo.setFAccompany(c.getString(c
							.getColumnIndex(KEY_DETAIL_FACCOMPANY)));
					eInfo.setFAccompanyReason(c.getString(c
							.getColumnIndex(KEY_DETAIL_FACCOMPANYREASON)));
					eInfo.setFStart(c.getString(c
							.getColumnIndex(KEY_DETAIL_FSTART)));
					eInfo.setFDestination(c.getString(c
							.getColumnIndex(KEY_DETAIL_FDESTINATION)));
					eInfo.setFStartTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FSTARTTIME)));
					eInfo.setFEndTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FENDTIME)));
					eInfo.setFBusinessLevel(c.getString(c
							.getColumnIndex(KEY_DETAIL_FBUSINESSLEVEL)));
					eInfo.setFReason(c.getString(c
							.getColumnIndex(KEY_DETAIL_FREASON)));
					eInfo.setFDescription(c.getString(c
							.getColumnIndex(KEY_DETAIL_FDESCRIPTION)));
					eInfo.setFUploadFlag(c.getString(c
							.getColumnIndex(KEY_DETAIL_FUPLOADFLAG)));
					list.add(eInfo);
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryFlowDetailAll
	 * @Description: ��ѯ�����ҵ���ˮ�������м�¼
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014��9��5�� ����11:40:06
	 */
	public Cursor queryFlowDetailAll() {
		String selectStr = "SELECT * FROM "
				+ DBUtils.DATABASE_TABLE_FLOW_DETAIL + " WHERE 1=1 AND "
				+ KEY_DETAIL_FUPLOADFLAG + " ='0' " + " ORDER BY " + KEY_ROWID
				+ " DESC";
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: deleteFlowDetail
	 * @Description: ɾ���ҵ���ˮ�����¼
	 * @param @param rowId
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014��9��5�� ����11:35:06
	 */
	public boolean deleteFlowDetail(int rowId) {
		return mDb.delete(DBUtils.DATABASE_TABLE_FLOW_DETAIL, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * @Title: batchDeleteFlowDetail
	 * @Description: ����ɾ����ˮ�����¼
	 * @param @param dList
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014��9��10�� ����12:00:32
	 */
	public boolean batchDeleteFlowDetail(List<Integer> dList) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			boolean result = false;
			for (Integer item : dList) {
				result = false;
				if (deleteFlowDetail(item)) {
					result = true;
				}
			}
			mDb.setTransactionSuccessful();
			return result;
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: updateFlag
	 * @Description: �޸���ˮ�����¼�ϴ�����״̬��־
	 * @param @param rowId
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��10�� ����3:44:58
	 */
	public void updateFlag(int rowId) {
		ContentValues args = new ContentValues();
		args.put(KEY_DETAIL_FUPLOADFLAG, "1"); // ��ʾ�Ѿ��ϴ��ɹ��ı�־
		mDb.update(DBUtils.DATABASE_TABLE_FLOW_DETAIL, args, KEY_ROWID + "="
				+ rowId, null);
	}

	/**
	 * @Title: batchUpdateFlag
	 * @Description: �����޸���ˮ�����¼���ϴ�״̬��־
	 * @param @param uList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��10�� ����3:43:18
	 */
	public void batchUpdateFlag(List<Integer> uList) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			for (Integer item : uList) {
				updateFlag(item);
			}
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: insertRoomSearch
	 * @Description: ����������б�������¼
	 * @param @param roomName
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��11�� ����8:16:38
	 */
	public void insertRoomSearch(MeetingRoomInfo meRoomInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH
					+ " WHERE 1=1 AND " + KEY_MEETING_ROOMNAME + " = " + "\""
					+ meRoomInfo.getFRoomName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MEETING_ROOMID, meRoomInfo.getFRoomId());
				cv.put(KEY_MEETING_ROOMNAME, meRoomInfo.getFRoomName());
				cv.put(KEY_MEETING_ROOMIP, meRoomInfo.getFRoomIp());
				mDb.insert(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: insertPersonSearch
	 * @Description: ������Ա�б�������¼
	 * @param @param mPersonInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:02:37
	 */
	public void insertPersonSearch(MeetingPersonInfo mPersonInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH
					+ " WHERE 1=1 AND " + KEY_MEETING_FITEMNUMBER + " = "
					+ "\"" + mPersonInfo.getFItemNumber() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MEETING_FITEMNUMBER, mPersonInfo.getFItemNumber());
				cv.put(KEY_MEETING_FITEMNAME, mPersonInfo.getFItemName());
				cv.put(KEY_MEETING_FDEPTNAME, mPersonInfo.getFDeptName());
				mDb.insert(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deleteRoomSearchAll
	 * @Description: ���ȫ����������ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:07:51
	 */
	public void deleteRoomSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, null, null);
	}

	/**
	 * @Title: deletePersonSearchAll
	 * @Description: ���ȫ����Ա��ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:08:40
	 */
	public void deletePersonSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, null, null);
	}

	/**
	 * @Title: queryRoomAllList
	 * @Description: ��ȡ���ػ�����������¼����Դ
	 * @param @return
	 * @return List<MeetingRoomInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:22:49
	 */
	public List<MeetingRoomInfo> queryRoomAllList() {
		List<MeetingRoomInfo> list = new ArrayList<MeetingRoomInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, "", "",
				"30");
		while (c.moveToNext()) {
			MeetingRoomInfo mRoomInfo = new MeetingRoomInfo();
			mRoomInfo.setFRoomId(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMID)));
			mRoomInfo.setFRoomName(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMNAME)));
			mRoomInfo.setFRoomIp(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMIP)));
			list.add(mRoomInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryRoomList
	 * @Description: ��ȡ������������ʷ��¼����
	 * @param @param queryStr ��ѯ����
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:23:30
	 */
	public List<String> queryRoomList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH,
				queryStr, KEY_MEETING_ROOMNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_MEETING_ROOMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryPersonAllList
	 * @Description: ��ȡ������Ա������¼����Դ
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:24:48
	 */
	public List<MeetingPersonInfo> queryPersonAllList() {
		List<MeetingPersonInfo> list = new ArrayList<MeetingPersonInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, "", "",
				"30");
		while (c.moveToNext()) {
			MeetingPersonInfo mPersonInfo = new MeetingPersonInfo();
			mPersonInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNUMBER)));
			mPersonInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNAME)));
			mPersonInfo.setFDeptName(c.getString(c
					.getColumnIndex(KEY_MEETING_FDEPTNAME)));
			mPersonInfo.setFRecordCount(0);
			list.add(mPersonInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryPersonList
	 * @Description: ��ȡ��Ա����������ʷ��¼����
	 * @param @param queryStr
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:27:35
	 */
	public List<String> queryPersonList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryPersonHistory(
				DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, queryStr, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c
							.getColumnIndex(KEY_MEETING_FITEMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}

		return list;
	}

	/**
	 * @Title: queryHistory
	 * @Description: ������������ѯ��������ʷ��¼
	 * @param @param dataBase ���ݿ�
	 * @param @param queryStr ��ѯ����
	 * @param @param fieldStr �ֶ�����
	 * @param @param topCount ��������
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014��9��2�� ����7:51:18
	 */
	public Cursor queryHistory(String dataBase, String queryStr,
			String fieldStr, String topCount) {
		String whereStr = ""; // ��ѯ����
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + fieldStr + " LIKE '%" + queryStr
					+ "%' ";
		}

		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: queryPersonHistory
	 * @Description: ������������ѯ��Ա��������ʷ��¼
	 * @param @param dataBase ���ݿ�
	 * @param @param queryStr ��ѯ����
	 * @param @param topCount ��������
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014��9��12�� ����10:31:43
	 */
	public Cursor queryPersonHistory(String dataBase, String queryStr,
			String topCount) {
		String whereStr = ""; // ��ѯ����
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + KEY_MEETING_FITEMNUMBER + " LIKE '%"
					+ queryStr + "%' OR " + KEY_MEETING_FITEMNAME + " LIKE '%"
					+ queryStr + "%'";
		}

		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: insertPlusCopyPersonSearch
	 * @Description: ��ǩ/����ģ�������Ա�б�������¼
	 * @param @param mPersonInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��25�� ����10:44:58
	 */
	public void insertPlusCopyPersonSearch(PlusCopyPersonInfo mPersonInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH
					+ " WHERE 1=1 AND " + KEY_PLUSCOPY_FITEMNUMBER + " = "
					+ "\"" + mPersonInfo.getFItemNumber() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_PLUSCOPY_FITEMNUMBER, mPersonInfo.getFItemNumber());
				cv.put(KEY_PLUSCOPY_FITEMNAME, mPersonInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deletePlusCopyPersonSearchAll
	 * @Description: ��ǩ/����ģ�����ȫ����Ա��ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��9��25�� ����10:46:36
	 */
	public void deletePlusCopyPersonSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, null, null);
	}

	/**
	 * @Title: queryPlusCopyPersonAllList
	 * @Description: ��ǩ/����ģ���ȡ������Ա������¼����Դ
	 * @param @return
	 * @return List<PlusCopyPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��9��25�� ����10:47:35
	 */
	public List<PlusCopyPersonInfo> queryPlusCopyPersonAllList() {
		List<PlusCopyPersonInfo> list = new ArrayList<PlusCopyPersonInfo>();
		Cursor c = null;
		c = queryPlusCopyPersonHistory(
				DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, "", "30");
		while (c.moveToNext()) {
			PlusCopyPersonInfo mPersonInfo = new PlusCopyPersonInfo();
			mPersonInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_PLUSCOPY_FITEMNUMBER)));
			mPersonInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_PLUSCOPY_FITEMNAME)));
			list.add(mPersonInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryPlusCopyPersonList
	 * @Description: ��ǩ/����ģ���ȡ��Ա����������ʷ��¼����
	 * @param @param queryStr
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014��9��25�� ����10:49:00
	 */
	public List<String> queryPlusCopyPersonList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryPlusCopyPersonHistory(
				DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, queryStr, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c
							.getColumnIndex(KEY_PLUSCOPY_FITEMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryPlusCopyPersonHistory
	 * @Description: ������������ѯ��Ա��������ʷ��¼
	 * @param @param dataBase ���ݿ�
	 * @param @param queryStr ��ѯ����
	 * @param @param topCount ��������
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014��9��25�� ����10:50:19
	 */
	public Cursor queryPlusCopyPersonHistory(String dataBase, String queryStr,
			String topCount) {
		String whereStr = ""; // ��ѯ����
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + KEY_PLUSCOPY_FITEMNUMBER
					+ " LIKE '%" + queryStr + "%' OR " + KEY_PLUSCOPY_FITEMNAME
					+ " LIKE '%" + queryStr + "%'";
		}
		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: insertDHProjectSearch
	 * @Description: �з���ʱģ��_������Ŀ�б�������¼
	 * @param @param dhProjectInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��10��30�� ����4:03:13
	 */
	public void insertDHProjectSearch(DHProjectInfo dhProjectInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH
					+ " WHERE 1=1 AND " + KEY_DEVELOP_HOURS_PROJECTNAME + " = "
					+ "\"" + dhProjectInfo.getFProjectName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_DEVELOP_HOURS_PROJECTCODE,
						dhProjectInfo.getFProjectCode());
				cv.put(KEY_DEVELOP_HOURS_PROJECTNAME,
						dhProjectInfo.getFProjectName());
				mDb.insert(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH,
						null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deleteDHProjectSearchAll
	 * @Description: �з���ʱģ��_���ȫ����Ŀ��ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014��10��30�� ����4:04:36
	 */
	public void deleteDHProjectSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH, null,
				null);
	}

	/**
	 * @Title: queryDHProjectAllList
	 * @Description: �з���ʱģ��_��ȡ������Ŀ�б�������¼
	 * @param @return
	 * @return List<DHProjectInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��10��30�� ����4:29:28
	 */
	public List<DHProjectInfo> queryDHProjectAllList() {
		List<DHProjectInfo> list = new ArrayList<DHProjectInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH,
				"", "", "5");
		while (c.moveToNext()) {
			DHProjectInfo dhProjectInfo = new DHProjectInfo();
			dhProjectInfo.setFProjectCode(c.getString(c
					.getColumnIndex(KEY_DEVELOP_HOURS_PROJECTCODE)));
			dhProjectInfo.setFProjectName(c.getString(c
					.getColumnIndex(KEY_DEVELOP_HOURS_PROJECTNAME)));
			list.add(dhProjectInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertMarketBidSearch
	 * @Description: �ҵ�����ģ��_���뱨��������¼
	 * @param @param mBidHistoryInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015��1��29�� ����5:10:16
	 */
	public void insertMarketBidSearch(MarketBidHistoryInfo mBidHistoryInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH
					+ " WHERE 1=1 AND " + KEY_MARKET_BID_FCUSTOMERNAME + " = "
					+ "\"" + mBidHistoryInfo.getFCustomerName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MARKET_BID_FCUSTOMERNAME,
						mBidHistoryInfo.getFCustomerName());
				cv.put(KEY_MARKET_BID_FBIDCODE, mBidHistoryInfo.getFBidCode());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deleteMarketBidSearchAll
	 * @Description:�ҵ�����ģ��_���ȫ��������ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015��1��27�� ����6:05:55
	 */
	public void deleteMarketBidSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, null, null);
	}

	/**
	 * @Title: queryMarketBidAllList
	 * @Description: �ҵ�����ģ��_��ȡ���ر����б�������¼
	 * @param @return
	 * @return List<MarketBidHistoryInfo>
	 * @throws
	 * @author 21291
	 * @date 2015��1��29�� ����5:10:48
	 */
	public List<MarketBidHistoryInfo> queryMarketBidAllList() {
		List<MarketBidHistoryInfo> list = new ArrayList<MarketBidHistoryInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, "", "", "5");
		while (c.moveToNext()) {
			MarketBidHistoryInfo mBidInfo = new MarketBidHistoryInfo();
			mBidInfo.setFCustomerName(c.getString(c
					.getColumnIndex(KEY_MARKET_BID_FCUSTOMERNAME)));
			mBidInfo.setFBidCode(c.getString(c
					.getColumnIndex(KEY_MARKET_BID_FBIDCODE)));
			list.add(mBidInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertMarketContractSearch
	 * @Description: �ҵ�����ģ��_�����ͬ������¼
	 * @param @param marketContractHistoryInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015��1��29�� ����5:11:32
	 */
	public void insertMarketContractSearch(
			MarketContractHistoryInfo marketContractHistoryInfo) {
		mDb.beginTransaction(); // ��ʼ����
		try {
			// ���ж��Ƿ��Ѿ�����
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH
					+ " WHERE 1=1 AND " + KEY_MARKET_CONTRACT_FCUSTOMERNAME
					+ " = " + "\""
					+ marketContractHistoryInfo.getFCustomerName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MARKET_CONTRACT_FCUSTOMERNAME,
						marketContractHistoryInfo.getFCustomerName());
				cv.put(KEY_MARKET_CONTRACT_FCONTRACTCODE,
						marketContractHistoryInfo.getFContractCode());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * @Title: deleteMarketContractSearchAll
	 * @Description: �ҵ�����ģ��_���ȫ����ͬ��ʷ��¼
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015��1��27�� ����6:13:54
	 */
	public void deleteMarketContractSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, null, null);
	}

	/**
	 * @Title: queryMarketContractAllList
	 * @Description: �ҵ�����ģ��_��ȡ���غ�ͬ�б�������¼
	 * @param @return
	 * @return List<marketContractHistoryInfo>
	 * @throws
	 * @author 21291
	 * @date 2015��1��29�� ����5:12:08
	 */
	public List<MarketContractHistoryInfo> queryMarketContractAllList() {
		List<MarketContractHistoryInfo> list = new ArrayList<MarketContractHistoryInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, "", "",
				"5");
		while (c.moveToNext()) {
			MarketContractHistoryInfo mContractInfo = new MarketContractHistoryInfo();
			mContractInfo.setFCustomerName(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTRACT_FCUSTOMERNAME)));
			mContractInfo.setFContractCode(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTRACT_FCONTRACTCODE)));
			list.add(mContractInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertContactSearch
	 * @Description: TODO(������һ�仰�����������������)
	 * @param @param mSearchContactInfo ����
	 * @return void ��������
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void insertContactSearch(ContactInfo mContactInfo) {
		mDb.beginTransaction();
		String itemNumber = mContactInfo.getFItemNumber();
		String sql = "select _id from "
				+ DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH
				+ " where FItemNumber=?";
		Cursor c = mDb.rawQuery(sql, new String[] {itemNumber });
		try {
			if (c != null && c.getCount() == 0) {
				ContentValues vs = new ContentValues();
				vs.put(KEY_MEETING_FITEMNUMBER,
						mContactInfo.getFItemNumber());
				vs.put(KEY_MEETING_FITEMNAME, mContactInfo.getFItemName());
				vs.put(KEY_MEETING_FDEPTNAME,
						mContactInfo.getFDepartment());
				vs.put(KEY_MARKET_CONTACT_FCORNET,
						mContactInfo.getFCornet());
				vs.put(KEY_MARKET_CONTACT_MAIL, mContactInfo.getFEmail());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, null,
						vs);
				mDb.setTransactionSuccessful();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mDb.endTransaction(); // ��������
		}
	}

	/**
	 * 
	* @Title: queryPersonAllList 
	* @Description: ��ȡ���ص�ͨѶ¼��Ϣ
	* @param  @return   ���� 
	* @return List<MeetingPersonInfo>    �������� 
	* @throws 
	* @author taozhaoping 26078
	* @author mail taozhaoping@gmail.com
	 */
	public List<ContactInfo> queryContactAllList() {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, "", "",
				"10");
		while (c.moveToNext()) {
			ContactInfo mContactInfo = new ContactInfo();
			mContactInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNUMBER)));
			mContactInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNAME)));
			mContactInfo.setFDepartment(c.getString(c
					.getColumnIndex(KEY_MEETING_FDEPTNAME)));
			mContactInfo.setFCornet(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTACT_FCORNET)));
			mContactInfo.setFEmail(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTACT_MAIL)));
			list.add(mContactInfo);
		}
		c.close();
		return list;
	}
	
	/**
	 * 
	 * @Title: deleteContactSearchAll
	 * @Description: ���ͨѶ¼������¼
	 * @param ����
	 * @return void ��������
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void deleteContactSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, null, null);
	}
}
