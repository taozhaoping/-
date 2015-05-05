package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHUploadConfirmInfo
 * @Description �з���ʱȷ�ϲ�������ʵ��
 * @author 21291
 * @date 2014��11��19�� ����11:08:42
 */
public class DHUploadConfirmInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FItemNumber;      //��Ŀ����Ա����
	public String FItemName;      	//��Ŀ����Ա������
    public int FYear; 		 		//���
    public int FWeekIndex; 		 	//�ܴ�
    
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}
	public int getFWeekIndex() {
		return FWeekIndex;
	}
	public void setFWeekIndex(int fWeekIndex) {
		FWeekIndex = fWeekIndex;
	}
	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static DHUploadConfirmInfo instance = new DHUploadConfirmInfo();  
    }
	public DHUploadConfirmInfo() {}
	public static DHUploadConfirmInfo getDHUploadConfirmInfo() {
		return SingletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHUploadConfirmInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FItemName", item.FItemName);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
