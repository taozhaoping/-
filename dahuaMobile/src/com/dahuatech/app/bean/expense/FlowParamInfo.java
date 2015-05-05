package com.dahuatech.app.bean.expense;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FlowParamInfo
 * @Description ��ȡ��������ˮ�б��������ʵ����
 * @author 21291
 * @date 2014��8��27�� ����6:04:10
 */
public class FlowParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //Ա����
	private String FQueryText; 	//��ѯ����
	private String FCurrentDate; //��ǰ5���ļ�¼ ��������ʱ��
	private int FPageIndex; 	//ҳ��
	private int FPageSize; 		//ҳ��С
	
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
	public String getFCurrentDate() {
		return FCurrentDate;
	}
	public void setFCurrentDate(String fCurrentDate) {
		FCurrentDate = fCurrentDate;
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
        private static FlowParamInfo instance = new FlowParamInfo();  
    }
	private FlowParamInfo() {}
	public static FlowParamInfo getFlowParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(FlowParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FCurrentDate", item.FCurrentDate);
			jsonObject.put("FPageIndex", item.FPageIndex);
			jsonObject.put("FPageSize", item.FPageSize);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
