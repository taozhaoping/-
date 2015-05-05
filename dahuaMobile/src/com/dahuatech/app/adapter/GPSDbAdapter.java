package com.dahuatech.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dahuatech.app.common.DBUtils;

/**
 * @ClassName GPSDbAdapter
 * @Description GPS��������
 * @author 21291
 * @date 2014��5��12�� ����3:37:19
 */
public class GPSDbAdapter {

	public static final String KEY_ROWID 		 = "_id";
	public static final String KEY_IME 			 = "ime"; 		   //����IME
	public static final String KEY_PATH 		 = "path";		   //·��
	public static final String KEY_STARTTIME	 = "startTime";    //��ʼʱ��
	public static final String KEY_ENDTIME 		 = "endTime";      //����ʱ��
	public static final String KEY_STARTLOCATION = "startLocation";//��ʼ����
	public static final String KEY_ENDLOCATION	 = "endLocation";  //��������
	public static final String KEY_STARTADDRESS	 = "startAddress"; //��ʼ�ص�
	public static final String KEY_ENDADDRESS	 = "endAddress";   //�����ص�
	public static final String KEY_UPLOADFLAG 	 = "uploadFlag";   //�Ƿ��ϴ���������   0��û���ϴ� 1���Ѿ��ϴ�
	public static final String KEY_AUTOFLAG 	 = "autoFlag";	   //�Զ�:automatic  �ֶ���¼��־ ��manual
	public static final String KEY_CAUSE 		 = "cause";	   	   //����ԭ��
	public static final String KEY_AMOUNT 		 = "amount";	   //�������
	
	private DBUtils mDbHelper;
	private SQLiteDatabase mDb;
	private Context mCtx;

	/** 
	* @Name: GPSDbAdapter 
	* @Description: ���췽��
	* @param ctx 
	*/
	public GPSDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
	/** 
	* @Title: openSqlLite 
	* @Description: �����ݿ�
	* @param @return
	* @param @throws SQLException     
	* @return GPSDbAdapter    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:40:56
	*/
	public GPSDbAdapter openSqlLite() throws SQLException {
		mDbHelper = new DBUtils(mCtx);
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
	* @date 2014��5��12�� ����3:41:26
	*/
	public void closeSqlLite() {
		mDbHelper.close();
	}
	
	/** 
	* @Title: manualEntry 
	* @Description: �ֶ�����ʵ����
	* @param startTime
	* @param endTime
	* @param startAddress
	* @param endAddress
	* @param cause
	* @param autoFlag
	* @param ime   
	* @param amount 
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:43:15
	*/
	public int manualEntry(String startTime, String endTime,String startAddress, String endAddress, String cause,String autoFlag, String ime,String amount) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_STARTTIME, startTime);
		initialValues.put(KEY_ENDTIME, endTime);
		initialValues.put(KEY_STARTADDRESS, startAddress);
		initialValues.put(KEY_ENDADDRESS, endAddress);
		initialValues.put(KEY_CAUSE, cause);
		initialValues.put(KEY_AUTOFLAG, autoFlag);
		initialValues.put(KEY_IME, ime);
		initialValues.put(KEY_UPLOADFLAG, "0");// Ĭ��û���ϴ��ɹ�
		initialValues.put(KEY_AMOUNT, amount);

		long ret = mDb.insert(DBUtils.DATABASE_TABLE, null, initialValues);
		Cursor cursor = mDb.rawQuery("select last_insert_rowid() id", null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndexOrThrow("id");
		int id = cursor.getInt(columnIndex);
		
		if (ret > 0) {
			return id;
		} else {
			return 0;
		}
	}
	
	/** 
	* @Title: createGpsdb 
	* @Description: �Զ��ϴ����õĴ�������
	* @param startLocation
	* @param startAddress
	* @param startTime
	* @param autoFlag
	* @param ime
	* @param uploadflag 
	* @param amount   
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:46:03
	*/
	public int createGpsdb(String startLocation, String startAddress,String startTime, String autoFlag, String ime, String uploadflag,String endAddress,String amount) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_STARTLOCATION, startLocation);
		initialValues.put(KEY_STARTADDRESS, startAddress);
		initialValues.put(KEY_STARTTIME, startTime);
		initialValues.put(KEY_AUTOFLAG, autoFlag);
		initialValues.put(KEY_IME, ime);
		initialValues.put(KEY_UPLOADFLAG, uploadflag);
		initialValues.put(KEY_ENDADDRESS, endAddress);
		initialValues.put(KEY_ENDTIME, "");
		initialValues.put(KEY_AMOUNT, amount);

		long ret = mDb.insert(DBUtils.DATABASE_TABLE, null, initialValues); 
		Cursor cursor = mDb.rawQuery("select last_insert_rowid() id", null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndexOrThrow("id");
		int id = cursor.getInt(columnIndex);

		if(ret > 0){
			return id;
		}else{
			return 0;
		}
	}
	
	/** 
	* @Title: deleteGpsdb 
	* @Description: ɾ������
	* @param rowId
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:47:07
	*/
	public boolean deleteGpsdb(int rowId) {
		return mDb.delete(DBUtils.DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	/** 
	* @Title: deleteGpsdbByUpload 
	* @Description: ��������ɾ������
	* @param upload 0��û���ϴ� 1���Ѿ��ϴ�
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:47:19
	*/
	public boolean deleteGpsdbByUpload(String upload) {
		return mDb.delete(DBUtils.DATABASE_TABLE, KEY_UPLOADFLAG + "=" + upload, null) > 0;
	}
	
	/** 
	* @Title: updateGpsdb 
	* @Description: �������м�¼��upload
	* @param uploadflag ��Ҫ���³ɵĽ��  0��û���ϴ� 1���Ѿ��ϴ�
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:50:05
	*/
	public boolean updateGpsdb(String uploadflag) {
		ContentValues args = new ContentValues();
		args.put(KEY_UPLOADFLAG, uploadflag); //�����ϴ��ɹ�
		return mDb.update(DBUtils.DATABASE_TABLE, args,null, null) > 0;
	}
	
	/** 
	* @Title: updateGpsdb 
	* @Description: ����id����
	* @param @param rowId
	* @param @param uploadflag
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:50:26
	*/
	public boolean updateGpsdb(int rowId, String uploadflag) {
		ContentValues args = new ContentValues();
		args.put(KEY_UPLOADFLAG, uploadflag); //�����ϴ��ɹ�
		return mDb.update(DBUtils.DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	/** 
	* @Title: updateGpsdb 
	* @Description: ����id,��ַ��λ�ã�����ʱ�䣬����
	* @param rowId
	* @param endAddress
	* @param endLocation
	* @param endTime
	* @param uploadflag
	* @param amount
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:50:48
	*/
	public boolean updateGpsdb(int rowId, String endAddress,String endLocation, String endTime, String uploadflag,String amount) {
		ContentValues args = new ContentValues();
		args.put(KEY_ENDADDRESS, endAddress);
		args.put(KEY_ENDLOCATION, endLocation);
		args.put(KEY_ENDTIME, endTime);
		args.put(KEY_UPLOADFLAG, uploadflag); //�����ϴ��ɹ�	
		args.put(KEY_AMOUNT, amount);
		return mDb.update(DBUtils.DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	/** 
	* @Title: updateGpsdb 
	* @Description: ����id����
	* @param rowId
	* @param startAddress
	* @param endAddress
	* @param startTime
	* @param endTime
	* @param cause
	* @param amount
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:51:54
	*/
	public boolean updateGpsdb(int rowId, String startAddress, String endAddress,String startTime, String endTime, String cause,String amount) {
		ContentValues args = new ContentValues();
		args.put(KEY_STARTADDRESS, startAddress);
		args.put(KEY_ENDADDRESS, endAddress);
		args.put(KEY_STARTTIME, startTime);
		args.put(KEY_ENDTIME, endTime);
		args.put(KEY_CAUSE, cause); 	
		args.put(KEY_AMOUNT, amount);
		return mDb.update(DBUtils.DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/** 
	* @Title: getAllNotes 
	* @Description: ��ѯ����
	* @param @return     
	* @return Cursor    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:52:26
	*/
	public Cursor getAllNotes() {
		return mDb.query(DBUtils.DATABASE_TABLE, 
			new String[] { KEY_ROWID,KEY_IME,KEY_PATH,KEY_STARTTIME,KEY_ENDTIME,KEY_STARTLOCATION,KEY_ENDLOCATION,
						   KEY_STARTADDRESS,KEY_ENDADDRESS,KEY_UPLOADFLAG,KEY_AUTOFLAG,KEY_CAUSE,KEY_AMOUNT
						 }, 
			null, null, null, null, null);
	}
	
	/** 
	* @Title: getGpsdb 
	* @Description: ����id��ѯ
	* @param @param rowId
	* @param @return
	* @param @throws SQLException     
	* @return Cursor    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:52:49
	*/
	public Cursor getGpsdb(int rowId) throws SQLException {	
		Cursor mCursor =
		mDb.query(true, DBUtils.DATABASE_TABLE, new String[] { KEY_ROWID,KEY_IME,KEY_PATH,KEY_STARTTIME,KEY_ENDTIME,KEY_STARTLOCATION,KEY_ENDLOCATION,
				   KEY_STARTADDRESS,KEY_ENDADDRESS,KEY_UPLOADFLAG,KEY_AUTOFLAG, KEY_CAUSE, KEY_AMOUNT}, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/** 
	* @Title: getGpsdbByUploadFlag 
	* @Description: ����uploadFlag��ѯ
	* @param uploadFlag 0��û���ϴ� 1���Ѿ��ϴ�
	* @param @return  �Ƿ��Ѿ��ϴ��ļ�¼
	* @param @throws SQLException     
	* @return Cursor    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:53:06
	*/
	public Cursor getGpsdbByUploadFlag(String uploadFlag) throws SQLException {
		String orderBy=KEY_ROWID+" DESC";
		Cursor mCursor = mDb.query(true, DBUtils.DATABASE_TABLE, new String[] { KEY_ROWID,KEY_IME,KEY_PATH,KEY_STARTTIME,KEY_ENDTIME,KEY_STARTLOCATION,KEY_ENDLOCATION,
						KEY_STARTADDRESS,KEY_ENDADDRESS,KEY_UPLOADFLAG,KEY_AUTOFLAG, KEY_CAUSE, KEY_AMOUNT}, KEY_UPLOADFLAG + "=" + uploadFlag, null, null,
						null, orderBy, null);
		return mCursor;	
	}
	
	/** 
	* @Title: getGpsdbByUploadFlag 
	* @Description: ����uploadFlag�������ݼ�¼
	* @param uploadFlag 0��û���ϴ� 1���Ѿ��ϴ�
	* @param @return  �Ƿ��Ѿ��ϴ��ļ�¼
	* @param @throws SQLException     
	* @return Cursor    
	* @throws 
	* @author 21291
	* @date 2014��5��12�� ����3:53:06
	*/
	public int getGpsdbByUploadFlagCount(String uploadFlag) throws SQLException {
		Cursor mCursor = mDb.query(true, DBUtils.DATABASE_TABLE, new String[] { KEY_ROWID,KEY_IME,KEY_PATH,KEY_STARTTIME,KEY_ENDTIME,KEY_STARTLOCATION,KEY_ENDLOCATION,
						KEY_STARTADDRESS,KEY_ENDADDRESS,KEY_UPLOADFLAG,KEY_AUTOFLAG, KEY_CAUSE, KEY_AMOUNT}, KEY_UPLOADFLAG + "=" + uploadFlag, null, null,
						null, null, null);
		return mCursor.getCount();	
	}
}
