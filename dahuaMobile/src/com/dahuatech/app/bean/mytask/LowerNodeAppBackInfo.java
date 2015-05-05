package com.dahuatech.app.bean.mytask;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppBackInfo
 * @Description ѡȡ���¼��ڵ���Աʵ��
 * @author 21291
 * @date 2014��11��12�� ����4:11:11
 */
public class LowerNodeAppBackInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FNodeId;			//��ǰ�ڵ�ID
	private String FNodeName;		//��ǰ�ڵ�����
	private String FSelectItem;		//ѡ�е���
	private int FIsMust;			//��ǰ�ڵ������Ƿ����Ҫ��   0-�������ߣ�1-�������Ҫ��
	
	public String getFNodeId() {
		return FNodeId;
	}
	public void setFNodeId(String fNodeId) {
		FNodeId = fNodeId;
	}
	
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public String getFSelectItem() {
		return FSelectItem;
	}
	public void setFSelectItem(String fSelectItem) {
		FSelectItem = fSelectItem;
	}
	public int getFIsMust() {
		return FIsMust;
	}
	public void setFIsMust(int fIsMust) {
		FIsMust = fIsMust;
	}
	// ��Listת����json�Ա����������д���
	public static String ConvertToJson(List<LowerNodeAppBackInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (LowerNodeAppBackInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FNodeId", item.FNodeId);
					jsonObject.put("FSelectItem", item.FSelectItem);
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
