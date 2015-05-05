package com.dahuatech.app.bean.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketBidHistoryInfo
 * @Description ������ʷ��ѯʵ��
 * @author 21291
 * @date 2015��1��29�� ����4:38:55
 */
public class MarketBidHistoryInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FCustomerName;  		//�ͻ�����
	private String FBidCode;  			//���۵���
	public String getFCustomerName() {
		return FCustomerName;
	}
	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getFBidCode() {
		return FBidCode;
	}
	public void setFBidCode(String fBidCode) {
		FBidCode = fBidCode;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MarketBidHistoryInfo instance = new MarketBidHistoryInfo();  
    }
	public MarketBidHistoryInfo() {}
	public static MarketBidHistoryInfo getMarketBidHistoryInfo() {
		return singletonHolder.instance;
	}
	
	//��Listת����json�Ա����������д���
	public static String ConvertToJson(List<MarketBidHistoryInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (MarketBidHistoryInfo item : items) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("FCustomerName", item.FCustomerName);
						jsonObject.put("FBidCode", item.FBidCode);
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
