package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PlusCopyInfo
 * @Description ��ǩ����ʵ����
 * @author 21291
 * @date 2014��9��16�� ����9:45:11
 */
public class PlusCopyInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAppKey;				//Ӧ�ó���KEY
	private String FSystemId;			//ϵͳ����
	private String FClassTypeId;		//��������ID
	private String FBillId;				//��������
	private String FItemNumber;			//��ǰԱ����
	private String FType;			    //���� "0"-�����ǩ  "1"-������
	private String FNodeIds; 			//���ӻ����ַ���
	private String FPersonNumbers;		//��Ա����
	private String FContent; 	 		//�ı�ֵ
	
	public String getFAppKey() {
		return FAppKey;
	}

	public void setFAppKey(String fAppKey) {
		FAppKey = fAppKey;
	}

	public String getFSystemId() {
		return FSystemId;
	}

	public void setFSystemId(String fSystemId) {
		FSystemId = fSystemId;
	}

	public String getFClassTypeId() {
		return FClassTypeId;
	}

	public void setFClassTypeId(String fClassTypeId) {
		FClassTypeId = fClassTypeId;
	}

	public String getFBillId() {
		return FBillId;
	}

	public void setFBillId(String fBillId) {
		FBillId = fBillId;
	}

	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFType() {
		return FType;
	}

	public void setFType(String fType) {
		FType = fType;
	}

	public String getFNodeIds() {
		return FNodeIds;
	}

	public void setFNodeIds(String fNodeIds) {
		FNodeIds = fNodeIds;
	}

	public String getFPersonNumbers() {
		return FPersonNumbers;
	}

	public void setFPersonNumbers(String fPersonNumbers) {
		FPersonNumbers = fPersonNumbers;
	}

	public String getFContent() {
		return FContent;
	}

	public void setFContent(String fContent) {
		FContent = fContent;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static PlusCopyInfo instance = new PlusCopyInfo();  
    }
	private PlusCopyInfo() {};
	public static PlusCopyInfo getPlusCopyInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(PlusCopyInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FAppKey", item.FAppKey);
			jsonObject.put("FSystemId", item.FSystemId);
			jsonObject.put("FClassTypeId", item.FClassTypeId);
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FType", item.FType);
			jsonObject.put("FNodeIds", item.FNodeIds);
			jsonObject.put("FPersonNumbers", item.FPersonNumbers);
			jsonObject.put("FContent", item.FContent);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
