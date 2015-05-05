package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpensePrivateTBodyParam
 * @Description ��ȡ��˽���ݱ�����ϸ������
 * @author 21291
 * @date 2014��5��26�� ����4:36:08
 */
public class ExpensePrivateTBodyParam extends Base {

	private static final long serialVersionUID = 1L;
	
	public String FSystemType; //ϵͳ����ID
	public String FBillID; //��������
	public String FCostCode; //����������ϸ����
	
	public String getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	public String getFCostCode() {
		return FCostCode;
	}

	public void setFCostCode(String fCostCode) {
		FCostCode = fCostCode;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpensePrivateTBodyParam instance = new ExpensePrivateTBodyParam();  
    }
	public ExpensePrivateTBodyParam() {}	
	public static ExpensePrivateTBodyParam getExpensePrivateTBodyParam() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(ExpensePrivateTBodyParam item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FSystemType", item.FSystemType);
			jsonObject.put("FBillID", item.FBillID);
			jsonObject.put("FCostCode", item.FCostCode);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
