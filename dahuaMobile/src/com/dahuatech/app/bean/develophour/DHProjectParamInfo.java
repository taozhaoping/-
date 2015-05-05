package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHProjectParamInfo
 * @Description �з���ʱ��Ŀ��������ʵ����
 * @author 21291
 * @date 2014��10��30�� ����5:07:22
 */
public class DHProjectParamInfo extends Base {
	private static final long serialVersionUID = 1L;

	private String FQueryText; // ��ѯ�ı�
	private int FPageIndex; // ҳ��
	private int FPageSize; // ҳ��С

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

	// �ڲ��൥��ģʽ
	private static class SingletonHolder {
		private static DHProjectParamInfo instance = new DHProjectParamInfo();
	}
	private DHProjectParamInfo() {}
	public static DHProjectParamInfo getDHProjectParamInfo() {
		return SingletonHolder.instance;
	}

	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(DHProjectParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
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
