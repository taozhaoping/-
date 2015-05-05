package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingSearchParamInfo
 * @Description ��Ա�б�ͻ���ѡ���б� ��ѯ����ʵ����
 * @author 21291
 * @date 2014��9��11�� ����3:52:04
 */
public class MeetingSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//Ա����
	private String FQueryText; 		//��ѯ����
	private int FPageIndex; 		//ҳ��
	private int FPageSize; 			//ҳ��С	
	private String FSelectedDate;	//�Ѿ�ѡ���������
	private String FSelectedStart;	//�Ѿ�ѡ�������ʼʱ��
	private String FSelectedEnd;	//�Ѿ�ѡ��������ʱ��
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFQueryText() {
		return FQueryText;
	}

	public void setFQueryText(String fQueryText) {
		FQueryText = fQueryText;
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
        private static MeetingSearchParamInfo instance = new MeetingSearchParamInfo();  
    }
	private MeetingSearchParamInfo() {}
	public static MeetingSearchParamInfo getMeetingSearchParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(MeetingSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FPageIndex", item.FPageIndex);		
			jsonObject.put("FPageSize", item.FPageSize);
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
