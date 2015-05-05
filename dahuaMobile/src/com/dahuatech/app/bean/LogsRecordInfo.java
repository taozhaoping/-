package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName LogsRecordInfo
 * @Description ��־ͳ�Ƽ�¼ʵ��
 * @author 21291
 * @date 2014��7��31�� ����9:48:43
 */
public class LogsRecordInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;		//Ա����
	private String FAccessTime;		//����ʱ��
	private String FModuleName;		//ģ������
	private String FActionName;		//��������
	private String FNote;			//������ϸ����
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFAccessTime() {
		return FAccessTime;
	}

	public void setFAccessTime(String fAccessTime) {
		FAccessTime = fAccessTime;
	}

	public String getFModuleName() {
		return FModuleName;
	}

	public void setFModuleName(String fModuleName) {
		FModuleName = fModuleName;
	}

	public String getFActionName() {
		return FActionName;
	}

	public void setFActionName(String fActionName) {
		FActionName = fActionName;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static LogsRecordInfo instance = new LogsRecordInfo();  
    }
	private LogsRecordInfo() {}
	public static LogsRecordInfo getLogsRecordInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(LogsRecordInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FAccessTime", item.FAccessTime);
			jsonObject.put("FModuleName", item.FModuleName);
			jsonObject.put("FActionName", item.FActionName);
			jsonObject.put("FNote", item.FNote);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
