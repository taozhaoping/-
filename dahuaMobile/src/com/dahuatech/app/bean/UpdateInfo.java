package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName Update
 * @Description Ӧ�ó������ʵ����
 * @author 21291
 * @date 2014��4��17�� ����3:47:15
 */
public class UpdateInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int VersionCode; 	//�汾����
	private String VersionName; //�汾����
	private String DownloadUrl; //���ص�ַ
	private String UpdateLog;	//������־

	public int getVersionCode() {
		return VersionCode;
	}

	public void setVersionCode(int versionCode) {
		VersionCode = versionCode;
	}

	public String getVersionName() {
		return VersionName;
	}

	public void setVersionName(String versionName) {
		VersionName = versionName;
	}

	public String getDownloadUrl() {
		return DownloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		DownloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return UpdateLog;
	}

	public void setUpdateLog(String updateLog) {
		UpdateLog = updateLog;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static UpdateInfo instance = new UpdateInfo();  
    }
	private UpdateInfo() {}	
	public static UpdateInfo getUpdate() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(UpdateInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("VersionCode", item.VersionCode);
			jsonObject.put("VersionName", item.VersionName);
			jsonObject.put("DownloadUrl", item.DownloadUrl);
			jsonObject.put("UpdateLog", item.UpdateLog);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
