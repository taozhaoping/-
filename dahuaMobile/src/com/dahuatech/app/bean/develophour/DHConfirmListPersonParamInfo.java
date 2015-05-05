package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListPersonParamInfo
 * @Description �з���ʱȷ���б������ʵ�崫�ݲ�����
 * @author 21291
 * @date 2014��11��5�� ����2:47:08
 */
public class DHConfirmListPersonParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FProjectNumber;   	//��Ŀ����Ա����
	private int FWeekIndex;				//�ܴ�
	private int FYear;					//���
	private String FProjectCode;	 	//��Ŀ����
	private String FConfrimNumber;	 	//ȷ����ԱԱ����
	
	public String getFProjectNumber() {
		return FProjectNumber;
	}
	public void setFProjectNumber(String fProjectNumber) {
		FProjectNumber = fProjectNumber;
	}
	public int getFWeekIndex() {
		return FWeekIndex;
	}
	public void setFWeekIndex(int fWeekIndex) {
		FWeekIndex = fWeekIndex;
	}
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFConfrimNumber() {
		return FConfrimNumber;
	}
	public void setFConfrimNumber(String fConfrimNumber) {
		FConfrimNumber = fConfrimNumber;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DHConfirmListPersonParamInfo instance = new DHConfirmListPersonParamInfo();  
    }
	private DHConfirmListPersonParamInfo() {}
	public static DHConfirmListPersonParamInfo getDHConfirmListPersonParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHConfirmListPersonParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FProjectNumber", item.FProjectNumber);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FConfrimNumber", item.FConfrimNumber);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
