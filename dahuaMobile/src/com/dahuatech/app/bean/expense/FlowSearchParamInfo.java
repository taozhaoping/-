package com.dahuatech.app.bean.expense;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FlowSearchParamInfo
 * @Description �ҵ���ˮ�ͻ�/��Ŀ�б��ѯ��������ʵ����
 * @author 21291
 * @date 2014��9��1�� ����7:43:52
 */
public class FlowSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //Ա����
	private String FQueryItem; 	//��ѯ�ı�
	private String fQueryType; 	//��ѯ���
  	private int FPageIndex; 	//ҳ��
	private int FPageSize; 	//ҳ��С
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFQueryItem() {
		return FQueryItem;
	}
	public void setFQueryItem(String fQueryItem) {
		FQueryItem = fQueryItem;
	}
	public String getfQueryType() {
		return fQueryType;
	}
	public void setfQueryType(String fQueryType) {
		this.fQueryType = fQueryType;
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
        private static FlowSearchParamInfo instance = new FlowSearchParamInfo();  
    }
	private FlowSearchParamInfo() {}
	public static FlowSearchParamInfo getFlowSearchParamInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(FlowSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryItem", item.FQueryItem);
			jsonObject.put("fQueryType", item.fQueryType);
			jsonObject.put("FPageIndex", item.FPageIndex);
			jsonObject.put("FPageSize", item.FPageSize);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
