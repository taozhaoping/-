package com.dahuatech.app.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ClassName DBUtils
 * @Description ���ݿ������
 * @author 21291
 * @date 2014��5��19�� ����12:47:22
 */
public class DBUtils extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "expense"; 		// ���ݿ�����
	public static final String SD_DATABASE_NAME = FileUtils.getSDRoot()+ "/dahuaMobile/database/"+DATABASE_NAME; // SD�����ݿ���
	public static final int DATABASE_VERSION = 5; // V1.0.4���ݿ�汾����1,V1.0.5���ݿ�汾����2,V1.0.6���ݿ�汾����3,V2.2.1���ݿ�汾����4 ,V2.4.1���ݿ�汾����5
	
	//����ģ��
	public static final String DATABASE_TABLE = "expense_gps"; 								// ���ϴ�_���ݿ����
	public static final String DATABASE_TABLE_CLIENT_SEARCH = "expense_client_search"; 		// ����ģ��_�ͻ��б�������¼_���ݿ����
	public static final String DATABASE_TABLE_PROJECT_SEARCH = "expense_project_search"; 	// ����ģ��_��Ŀ�б�������¼_���ݿ����
	public static final String DATABASE_TABLE_FLOW_DETAIL = "expense_flow_table_detail"; 	// ����ģ��_��ˮ����_���ݿ����
	
	//����ģ��
	public static final String DATABASE_TABLE_MEETING_ROOM_SEARCH = "meeting_room_search"; 		// ����ģ��_�������б�������¼_���ݿ����
	public static final String DATABASE_TABLE_MEETING_PERSON_SEARCH = "meeting_person_search"; 	// ����ģ��_��Ա�б�������¼_���ݿ����
	
	//��ǩ/����ģ��
	public static final String DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH = "pluscopy_person_search"; 	// ��ǩ/����ģ��_��Ա�б�������¼_���ݿ����
	
	//�з���ʱģ��
	public static final String DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH = "dh_project_search"; 	// �з���ʱģ��_��Ŀ�б�������¼_���ݿ����
	
	//�ҵ�����ģ��
	public static final String DATABASE_TABLE_MARKET_BID_SEARCH = "market_bid_search"; 				// �ҵ�����ģ��_���۲�ѯ��¼_���ݿ����
	public static final String DATABASE_TABLE_MARKET_CONTRACT_SEARCH = "market_contract_search"; 	// �ҵ�����ģ��_��ͬ��ѯ��¼_���ݿ����
	
	//ͨѶ¼ģ��
	public static final String DATABASE_TABLE_MARKET_CONTACT_SEARCH = "Contact_search";             //ͨѶ¼_ͨѶ¼������¼_���ݿ����
	
	//����_����ģ��_���ϴ���
	public static final String DATABASE_CREATE = "create table IF NOT EXISTS "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
			+ " ime text not null, path text, startTime text, endTime text, "
			+ " startLocation text,endLocation text,startAddress text,endAddress text,"
			+ " uploadFlag text, autoFlag text, cause text, amount text );";
	
	//����_����ģ��_�ͻ��б�������¼��
	public static final String DATABASE_CREATE_CLIENT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_CLIENT_SEARCH+" (_id integer primary key autoincrement, clientId varchar,clientName varchar );";
	//����_����ģ��_��Ŀ�б�������¼��
	public static final String DATABASE_CREATE_PROJECT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_PROJECT_SEARCH+" (_id integer primary key autoincrement, projectId varchar,projectName varchar );";	
	//����_����ģ��_�ҵ���ˮ�����
	public static final String DATABASE_CREATE_FLOW_DETAIL = "create table IF NOT EXISTS "+DATABASE_TABLE_FLOW_DETAIL+" (_id integer primary key autoincrement, "
			+ " fServerId integer,fPayType varchar,fItemNumber varchar, fExpendTime varchar, fExpendTypeParent varchar, fExpendTypeChild varchar, fExpendAddress text, "
			+ " fExpendAmount varchar,fCause text,fClientId varchar,fProjectId varchar,fClient varchar,fProject varchar,"
			+ " fAccompany varchar,fAccompanyReason text,"
			+ " fStart text,fDestination text,fStartTime varchar,fEndTime varchar,fBusinessLevel varchar,"
			+ " fReason varchar, fDescription text,fUploadFlag varchar );";
	
	//����_����ģ��_�������б�������¼��
	public static final String DATABASE_CREATE_ROOM_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MEETING_ROOM_SEARCH+" (_id integer primary key autoincrement, roomId varchar,roomName varchar,roomIp varchar );";
	//����_����ģ��_������Ա�б�������¼��
	public static final String DATABASE_CREATE_PERSON_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MEETING_PERSON_SEARCH+" (_id integer primary key autoincrement, fItemNumber varchar,fItemName varchar,fDeptName text );";
	
	//����_�ҵ�����_��ǩ/������Ա�б�������¼��
	public static final String DATABASE_CREATE_PLUSCOPY_PERSON_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH+" (_id integer primary key autoincrement, fItemNumber varchar,fItemName varchar );";
	
	//����_�з���ʱģ��_��Ŀ�б�������¼��
	public static final String DATABASE_CREATE_DEVELOP_HOURS_PROJECT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH+" (_id integer primary key autoincrement, projectCode varchar,projectName varchar );";
	
	//����_�ҵ�����ģ��_���۲�ѯ��¼��
	public static final String DATABASE_CREATE_MARKET_BID_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MARKET_BID_SEARCH+" (_id integer primary key autoincrement, fcustomername varchar,fbidcode varchar );";
	//����_�ҵ�����ģ��_��ͬ��ѯ��¼��
	public static final String DATABASE_CREATE_MARKET_CONTRACT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MARKET_CONTRACT_SEARCH+" (_id integer primary key autoincrement, fcustomername varchar,fcontractcode varchar );";
		
	//ͨѶ¼��¼
	public static final String DATABASE_CREATE_MARKET_CONTACT_SEARCH = "CREATE TABLE " + DATABASE_TABLE_MARKET_CONTACT_SEARCH + " (_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,fItemNumber TEXT,fItemName   TEXT,fEmail      TEXT, fCornet     TEXT,fDeptName TEXT )";

	
	//ɾ��_����ģ��_���ϴ�
	public static final String DROP_TEMP_DATABASE_TABLE = "DROP TABLE IF EXISTS "+DATABASE_TABLE;
	//ɾ��_����ģ��_�ͻ��б�������¼
	public static final String DROP_TEMP_DATABASE_TABLE_CLIENT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_CLIENT_SEARCH;
	//ɾ��_����ģ��_��Ŀ�б�������¼
	public static final String DROP_TEMP_DATABASE_TABLE_PROJECT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_PROJECT_SEARCH;
	//ɾ��_����ģ��_�ҵ���ˮ����
	public static final String DROP_TEMP_DATABASE_TABLE_FLOW_DETAIL = "DROP TABLE IF EXISTS "+DATABASE_TABLE_FLOW_DETAIL;
	
	//ɾ��_����ģ��_�������б�������¼��
	public static final String DROP_TEMP_DATABASE_TABLE_ROOM_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MEETING_ROOM_SEARCH;
	//ɾ��_����ģ��_������Ա�б�������¼��
	public static final String DROP_TEMP_DATABASE_TABLE_PERSON_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MEETING_PERSON_SEARCH;
	
	//ɾ��_�ҵ�����_��ǩ/������Ա�б�������¼��
	public static final String DROP_TEMP_DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH;

	//ɾ��_�з���ʱģ��_��Ŀ�б�������¼
	public static final String DROP_TEMP_DATABASE_DEVELOP_HOURS_TABLE_CLIENT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH;
	
	//ɾ��_�ҵ�����ģ��_���۲�ѯ��¼
	public static final String DROP_TEMP_DATABASE_MARKET_BID_TABLE_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_BID_SEARCH;
	//ɾ��_�ҵ�����ģ��_��ͬ��ѯ��¼
	public static final String DROP_TEMP_DATABASE_MARKET_CONTRACT_TABLE_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_CONTRACT_SEARCH;
	
	//ɾ��_ͨѶ¼ģ��_��ѯ��¼�ϴ�
	public static final String DROP_TEMP_DATABASE_TABLE_CONTACT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_CONTACT_SEARCH;
	
	public DBUtils(Context context) {
		//����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//SQLiteDatabase.openOrCreateDatabase(SD_DATABASE_NAME,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);  								//����_����ģ��_���ϴ���	
		db.execSQL(DATABASE_CREATE_CLIENT_SEARCH);  				//����_����ģ��_�ͻ��б�������¼��
		db.execSQL(DATABASE_CREATE_PROJECT_SEARCH);  				//����_����ģ��_��Ŀ�б�������¼��
		db.execSQL(DATABASE_CREATE_FLOW_DETAIL);  					//����_����ģ��_�ҵ���ˮ�����
		db.execSQL(DATABASE_CREATE_ROOM_SEARCH);  					//����_����ģ��_�������б�������¼��
		db.execSQL(DATABASE_CREATE_PERSON_SEARCH);  				//����_����ģ��_������Ա�б�������¼��
		db.execSQL(DATABASE_CREATE_PLUSCOPY_PERSON_SEARCH); 		//����_�ҵ�����_��ǩ/������Ա�б�������¼��
		db.execSQL(DATABASE_CREATE_DEVELOP_HOURS_PROJECT_SEARCH); 	//����_�з���ʱģ��_��Ŀ�б�������¼��
		db.execSQL(DATABASE_CREATE_MARKET_BID_SEARCH); 				//����_�ҵ�����ģ��_���۲�ѯ��¼��
		db.execSQL(DATABASE_CREATE_MARKET_CONTRACT_SEARCH); 		//����_�ҵ�����ģ��_��ͬ��ѯ��¼��
		db.execSQL(DATABASE_CREATE_MARKET_CONTACT_SEARCH);      //����ͨѶ¼������¼��
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i <= newVersion; i++) {
			switch (i) {
				case 1:
					db.execSQL(DROP_TEMP_DATABASE_TABLE);  						 		//ɾ��_����ģ��_���ϴ���		
					break;	
				case 2:
					db.execSQL(DROP_TEMP_DATABASE_TABLE_CLIENT_SEARCH);  			 	//ɾ��_����ģ��_�ͻ��б�������¼��
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PROJECT_SEARCH);  		 		//ɾ��_����ģ��_��Ŀ�б�������¼��
					db.execSQL(DROP_TEMP_DATABASE_TABLE_FLOW_DETAIL);  			 		//ɾ��_����ģ��_�ҵ���ˮ�����
					db.execSQL(DROP_TEMP_DATABASE_TABLE_ROOM_SEARCH);  			 		//ɾ��_����ģ��_�������б�������¼��
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PERSON_SEARCH);  		 		//ɾ��_����ģ��_������Ա�б�������¼��
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH);	 	//ɾ��_�ҵ�����_��ǩ/������Ա�б�������¼��
					break;
				case 3:
					db.execSQL(DROP_TEMP_DATABASE_DEVELOP_HOURS_TABLE_CLIENT_SEARCH);  	//ɾ��_�з���ʱģ��_��Ŀ�б�������¼��	
					break;	
				case 4:
					db.execSQL(DROP_TEMP_DATABASE_MARKET_BID_TABLE_SEARCH); 			//ɾ��_�ҵ�����ģ��_���۲�ѯ��¼��
					db.execSQL(DROP_TEMP_DATABASE_MARKET_CONTRACT_TABLE_SEARCH); 		//ɾ��_�ҵ�����ģ��_��ͬ��ѯ��¼��
					break;
				case 5:
					db.execSQL(DROP_TEMP_DATABASE_TABLE_CONTACT_SEARCH);
					break;
				default:
					break;
				}
		}
		onCreate(db);
	}
	
	/** 
	* @Title: GetColumns 
	* @Description: ��ȡ�����Ƽ���
	* @param @param db
	* @param @param tableName
	* @param @return     
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014��5��19�� ����12:55:15
	*/
	public static List<String> GetColumns(SQLiteDatabase db, String tableName) {
	    List<String> ar = null;
	    Cursor c = null;
	    try {
	        c = db.rawQuery("select * from " + tableName + " limit 1", null);
	        if (c != null) {
	            ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (c != null)
	            c.close();
	    }
	    return ar;
	}
}
