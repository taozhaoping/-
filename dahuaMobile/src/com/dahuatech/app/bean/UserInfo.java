package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName UserInfo
 * @Description �û�ʵ��
 * @author 21291
 * @date 2014��4��17�� ����11:27:16
 */
public class UserInfo extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//Ա����
	private String FItemName; 		//Ա������
	private String FPassword;		//����
	private boolean IsRememberMe; 	//�Ƿ��ס��½��Ϣ
		
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
		this.FItemName = fItemName;
	}

	public String getFPassword() {
		return FPassword;
	}

	public void setFPassword(String fPassword) {
		FPassword = fPassword;
	}

	public boolean isIsRememberMe() {
		return IsRememberMe;
	}

	public void setIsRememberMe(boolean isRememberMe) {
		IsRememberMe = isRememberMe;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static UserInfo instance = new UserInfo();  
    }
	private UserInfo() {
		IsRememberMe=false;
	}
	private static UserInfo userInfo;
	public static UserInfo getUserInfo() {
		return singletonHolder.instance;
	}

	public static UserInfo getUserInfo(String fItemNumber,String fPassword,boolean isRememberMe) {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		userInfo.setFItemNumber(fItemNumber);
		userInfo.setFPassword(fPassword);
		userInfo.setIsRememberMe(isRememberMe);
		return userInfo;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(UserInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FPassword", item.FPassword);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
