package com.dahuatech.app.bean.market;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketSearchParamInfo
 * @Description ��������ʵ��
 * @author 21291
 * @date 2015��1��26�� ����2:38:37
 */
public class MarketSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;  		//Ա����
	private String FQueryText;  		//��ѯ�ı�
	private int FPageIndex;  			//ҳ��
	private int FPageSize;  			//ҳ��С
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
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MarketSearchParamInfo instance = new MarketSearchParamInfo();  
    }
	private MarketSearchParamInfo() {}
	public static MarketSearchParamInfo getMarketSearchParamInfo() {
		return singletonHolder.instance;
	}

	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(MarketSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FPageIndex", item.FPageIndex);		
			jsonObject.put("FPageSize", item.FPageSize);	
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
