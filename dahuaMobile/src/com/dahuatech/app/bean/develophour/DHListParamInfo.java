package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHListParamInfo
 * @Description ��ȡ�з���ʱ�б��������ʵ����
 * @author 21291
 * @date 2014��10��22�� ����5:08:00
 */
public class DHListParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//Ա����
	private int FWeekIndex; 		//�ܴ�(�ӵ�ǰ�ܵݼ�)
	private int FYear; 				//���
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
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
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DHListParamInfo instance = new DHListParamInfo();  
    }
	private DHListParamInfo() {}
	public static DHListParamInfo getDHListParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHListParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
