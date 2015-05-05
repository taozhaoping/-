package com.dahuatech.app.bean.attendance;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdCheckInfo
 * @Description ǩ���ǩ��ʵ��
 * @author 21291
 * @date 2014��12��18�� ����1:48:53
 */
public class AdCheckInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FAttendId;			//����ID
	private int FAttendStatus;		//����״̬
	private String FItemNumber;  	//Ա����
	private String FItemName;  		//Ա������
	private String FLatitude;  		//����γ��
	private String FLongitude;  	//���꾭��
	private String FAddress;		//�����ַ
	
	public int getFAttendId() {
		return FAttendId;
	}
	public void setFAttendId(int fAttendId) {
		FAttendId = fAttendId;
	}
	public int getFAttendStatus() {
		return FAttendStatus;
	}
	public void setFAttendStatus(int fAttendStatus) {
		FAttendStatus = fAttendStatus;
	}
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
	public String getFLatitude() {
		return FLatitude;
	}
	public void setFLatitude(String fLatitude) {
		FLatitude = fLatitude;
	}
	public String getFLongitude() {
		return FLongitude;
	}
	public void setFLongitude(String fLongitude) {
		FLongitude = fLongitude;
	}
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}

	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static AdCheckInfo instance = new AdCheckInfo();  
    }
	public AdCheckInfo() {}
	public static AdCheckInfo getAdCheckInfo() {
		return SingletonHolder.instance;
	}
	
	//��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(AdCheckInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {			
			jsonObject.put("FAttendId", item.FAttendId);
			jsonObject.put("FAttendStatus", item.FAttendStatus);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FLatitude", item.FLatitude);
			jsonObject.put("FLongitude", item.FLongitude);
			jsonObject.put("FAddress", item.FAddress);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
