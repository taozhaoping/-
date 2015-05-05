package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppConfigInfo
 * @Description ѡȡ���¼��ڵ���������ʵ��
 * @author 21291
 * @date 2014��11��12�� ����4:57:47
 */
public class LowerNodeAppConfigInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FAppKey;				//Ӧ�ó���KEY
	private int FSystemId;				//ϵͳ����
	private String FClassTypeId;		//��������ID
	private String FBillId;				//��������
	private String FItemNumber;			//��ǰԱ����
	
	public String getFAppKey() {
		return FAppKey;
	}
	public void setFAppKey(String fAppKey) {
		FAppKey = fAppKey;
	}
	public int getFSystemId() {
		return FSystemId;
	}
	public void setFSystemId(int fSystemId) {
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

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static LowerNodeAppConfigInfo instance = new LowerNodeAppConfigInfo();  
    }
	private LowerNodeAppConfigInfo() {};
	public static LowerNodeAppConfigInfo getLowerNodeAppConfigInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(LowerNodeAppConfigInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FAppKey", item.FAppKey);
			jsonObject.put("FSystemId", item.FSystemId);
			jsonObject.put("FClassTypeId", item.FClassTypeId);
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
