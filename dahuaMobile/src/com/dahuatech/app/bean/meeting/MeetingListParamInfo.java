package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingListParamInfo
 * @Description �ҵĻ����б��������ʵ����
 * @author 21291
 * @date 2014��9��16�� ����3:31:47
 */
public class MeetingListParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;  	//Ա����
	private int FPageIndex;  		//ҳ��
	private int FPageSize;  		//ҳ��С
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public int getFPageIndex() {
		return FPageIndex;
	}
	public void setFPageIndex(int fPageIndex) {
		FPageIndex = fPageIndex;
	}
	public int getFPageSize() {
		return FPageSize;
	}
	public void setFPageSize(int fPageSize) {
		FPageSize = fPageSize;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MeetingListParamInfo instance = new MeetingListParamInfo();  
    }
	private MeetingListParamInfo() {}
	public static MeetingListParamInfo getMeetingListParamInfo() {
		return singletonHolder.instance;
	}

	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(MeetingListParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FPageIndex", item.FPageIndex);		
			jsonObject.put("FPageSize", item.FPageSize);	
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
