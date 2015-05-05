package com.dahuatech.app.bean.develophour;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHUploadConfirmInfo
 * @Description �з���ʱȷ����Ա����ʵ��
 * @author 21291
 * @date 2014��11��19�� ����11:08:42
 */
public class DHUploadConfirmPersonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FProjectCode;     //ѡ�е���Ŀ���,
	public String FConfirmNumber;   //ȷ����Ա����,���Ա����ƴ�ӣ���","�ָ�
	
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFConfirmNumber() {
		return FConfirmNumber;
	}
	public void setFConfirmNumber(String fConfirmNumber) {
		FConfirmNumber = fConfirmNumber;
	}
	
	public DHUploadConfirmPersonInfo() {}
	
	//��Listת����json�Ա����������д���
	public static String ConvertToJson(List<DHUploadConfirmPersonInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (DHUploadConfirmPersonInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FProjectCode", item.FProjectCode);
					jsonObject.put("FConfirmNumber", item.FConfirmNumber);
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
