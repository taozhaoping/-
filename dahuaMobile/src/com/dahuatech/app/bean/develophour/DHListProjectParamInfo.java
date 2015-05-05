package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHListProjectParamInfo
 * @Description ��ȡ�з���ʱ�б���Ŀ���� ��������ʵ����
 * @author 21291
 * @date 2014��10��23�� ����9:21:01
 */
public class DHListProjectParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FBillId; //�ܵ���Id
	private String FWeekValue; 	//ÿ��ֵ
	
	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFWeekValue() {
		return FWeekValue;
	}
	public void setFWeekValue(String fWeekValue) {
		FWeekValue = fWeekValue;
	}
	
	//����ģʽ-�̰߳�ȫд��
	private DHListProjectParamInfo() {}
	private static DHListProjectParamInfo dHListProjectParamInfo;
	public static DHListProjectParamInfo getDHListProjectParamInfo() {
		if (dHListProjectParamInfo == null) {
			dHListProjectParamInfo = new DHListProjectParamInfo();
		}
		return dHListProjectParamInfo;
	}
	
	//��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHListProjectParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FWeekValue", item.FWeekValue);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
