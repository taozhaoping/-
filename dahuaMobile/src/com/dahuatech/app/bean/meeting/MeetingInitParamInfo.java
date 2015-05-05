package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingInitParamInfo
 * @Description ��������ʷ��¼״̬��ѯ����ʵ����
 * @author 21291
 * @date 2014��9��28�� ����2:46:18
 */
public class MeetingInitParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//Ա����
	private String FRoomIdS; 		//������ID ��������ַ�������','�ֿ�
	private String FSelectedDate;	//�Ѿ�ѡ���������
	private String FSelectedStart;	//�Ѿ�ѡ�������ʼʱ��
	private String FSelectedEnd;	//�Ѿ�ѡ��������ʱ��
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFRoomIdS() {
		return FRoomIdS;
	}

	public void setFRoomIdS(String fRoomIdS) {
		FRoomIdS = fRoomIdS;
	}

	public String getFSelectedDate() {
		return FSelectedDate;
	}

	public void setFSelectedDate(String fSelectedDate) {
		FSelectedDate = fSelectedDate;
	}

	public String getFSelectedStart() {
		return FSelectedStart;
	}

	public void setFSelectedStart(String fSelectedStart) {
		FSelectedStart = fSelectedStart;
	}

	public String getFSelectedEnd() {
		return FSelectedEnd;
	}

	public void setFSelectedEnd(String fSelectedEnd) {
		FSelectedEnd = fSelectedEnd;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MeetingInitParamInfo instance = new MeetingInitParamInfo();  
    }
	private MeetingInitParamInfo() {}
	public static MeetingInitParamInfo getMeetingInitParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(MeetingInitParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FRoomIdS", item.FRoomIdS);
			jsonObject.put("FSelectedDate", item.FSelectedDate);
			jsonObject.put("FSelectedStart", item.FSelectedStart);		
			jsonObject.put("FSelectedEnd", item.FSelectedEnd);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
