package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHDetailParamInfo
 * @Description �з���ʱ�����������ʵ����
 * @author 21291
 * @date 2014��10��30�� ����11:19:34
 */
public class DHDetailParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FBillId;		 	//�ܵ���Id
	private String FWeekDate; 	 	//��������
	private String FProjectCode; 	//��Ŀ���
	private String FTypeId; 	    //����Id

	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFWeekDate() {
		return FWeekDate;
	}
	public void setFWeekDate(String fWeekDate) {
		FWeekDate = fWeekDate;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}

	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static DHDetailParamInfo instance = new DHDetailParamInfo();  
    }
	private DHDetailParamInfo() {}
	public static DHDetailParamInfo getDHDetailParamInfo() {
		return SingletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHDetailParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FWeekDate", item.FWeekDate);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FTypeId", item.FTypeId);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
