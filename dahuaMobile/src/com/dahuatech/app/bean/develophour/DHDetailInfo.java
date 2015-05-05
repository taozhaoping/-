package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHDetailInfo
 * @Description �з���ʱ����ʵ����
 * @author 21291
 * @date 2014��10��28�� ����5:27:12
 */
public class DHDetailInfo extends Base {
	private static final long serialVersionUID = 1L;

	public int FBillId;				//�ܵ���Id
	public String FItemNumber;		//Ա����
	public String FWeekDate;		//��������
	public String FProjectCode;		//��Ŀ���
	public String FProjectName;		//��Ŀ����
	public String FTypeId;			//�������Id
	public String FTypeName;		//�����������
	public String FHours;			//��ʱ
	public String FNote;			//��ע
	
	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFWeekDate() {
		return FWeekDate;
	}
	public void setFWeekDate(String fWeekDate) {
		FWeekDate = fWeekDate;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}
	public String getFTypeName() {
		return FTypeName;
	}
	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static DHDetailInfo instance = new DHDetailInfo();  
    }
	public DHDetailInfo() {}
	public static DHDetailInfo getDHDetailInfo() {
		return SingletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHDetailInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {			
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FWeekDate", item.FWeekDate);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FProjectName", item.FProjectName);
			jsonObject.put("FTypeId", item.FTypeId);
			jsonObject.put("FTypeName", item.FTypeName);
			jsonObject.put("FHours", item.FHours);
			jsonObject.put("FNote", item.FNote);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
