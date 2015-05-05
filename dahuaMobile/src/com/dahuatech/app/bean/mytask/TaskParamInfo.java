package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName TaskParamInfo
 * @Description ĳ�����ݴ��ݵĲ���ʵ����
 * @author 21291
 * @date 2014��4��24�� ����4:43:09
 */
public class TaskParamInfo {
	
	private String FMenuID; //�˵�ID
	private String FSystemType; //ϵͳID
	private String FBillID; //��������
	
	public String getFMenuID() {
		return FMenuID;
	}

	public void setFMenuID(String fMenuID) {
		FMenuID = fMenuID;
	}

	public String getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static TaskParamInfo instance = new TaskParamInfo();  
    }
	private TaskParamInfo() {}
	public static TaskParamInfo getTaskParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(TaskParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FMenuID", item.FMenuID);
			jsonObject.put("FSystemType", item.FSystemType);
			jsonObject.put("FBillID", item.FBillID);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
