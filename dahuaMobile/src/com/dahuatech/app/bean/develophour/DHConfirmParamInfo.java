package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmParamInfo
 * @Description ��ȡ�з���ʱȷ���б��������ʵ����
 * @author 21291
 * @date 2014��10��21�� ����11:04:19
 */
public class DHConfirmParamInfo extends Base {
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
        private static DHConfirmParamInfo instance = new DHConfirmParamInfo();  
    }
	private DHConfirmParamInfo() {}
	public static DHConfirmParamInfo getDHParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHConfirmParamInfo item) {
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
