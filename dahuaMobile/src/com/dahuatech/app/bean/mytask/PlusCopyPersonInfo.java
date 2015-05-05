package com.dahuatech.app.bean.mytask;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PlusCopyPersonInfo
 * @Description  ��ǩ������Աʵ����
 * @author 21291
 * @date 2014��9��16�� ����9:45:01
 */
public class PlusCopyPersonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //��ǩ������ԱԱ����
	private String FItemName; 	//��ǩ������Ա����
	
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
	
	//Ĭ�Ϲ��캯��
	public PlusCopyPersonInfo() {}
	
	public PlusCopyPersonInfo(String fItemNumber,String fItemName) {
		this.FItemNumber=fItemNumber;
		this.FItemName=fItemName;
	}
	
	// ��Listת����json�Ա����������д���
	public static String ConvertToJson(List<PlusCopyPersonInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (PlusCopyPersonInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FItemNumber", item.FItemNumber);
					jsonObject.put("FItemName", item.FItemName);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
