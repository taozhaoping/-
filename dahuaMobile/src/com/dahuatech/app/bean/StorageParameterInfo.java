package com.dahuatech.app.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName StorageParameterInfo
 * @Description �洢���̲�����
 * @author 21291
 * @date 2014��4��23�� ����2:51:43
 */
public class StorageParameterInfo extends Base {
	
	private static final long	serialVersionUID	= 1L;
	public String Key; //�洢������
	public String Value; //�洢����ֵ
	public String SqlDbType; //�洢��������
	public int ValueLength; //�洢����ֵ����
	public boolean IsOutput; //�Ƿ����

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getSqlDbType() {
		return SqlDbType;
	}

	public void setSqlDbType(String sqlDbType) {
		SqlDbType = sqlDbType;
	}

	public int getValueLength() {
		return ValueLength;
	}

	public void setValueLength(int valueLength) {
		ValueLength = valueLength;
	}

	public boolean isIsOutput() {
		return IsOutput;
	}

	public void setIsOutput(boolean isOutput) {
		IsOutput = isOutput;
	}
	
	public StorageParameterInfo() {
		IsOutput = false;
	}
	
	public StorageParameterInfo(String key,String value,String sqlDbType,int valueLength,boolean isOutput) {
		this.Key=key;
		this.Value=value;
		this.SqlDbType=sqlDbType;
		this.ValueLength=valueLength;
		this.IsOutput=isOutput;
	}

	// ��Listת����json�Ա����������д���
	public static String ConvertToJson(List<StorageParameterInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (StorageParameterInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Key", item.Key);
					jsonObject.put("Value", item.Value);
					jsonObject.put("SqlDbType", item.SqlDbType);
					jsonObject.put("ValueLength", item.ValueLength);
					jsonObject.put("IsOutput", item.IsOutput);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(StorageParameterInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Key", item.Key);
			jsonObject.put("Value", item.Value);
			jsonObject.put("SqlDbType", item.SqlDbType);
			jsonObject.put("ValueLength", item.ValueLength);
			jsonObject.put("IsOutput", item.IsOutput);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
