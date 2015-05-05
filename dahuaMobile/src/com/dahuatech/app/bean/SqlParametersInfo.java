package com.dahuatech.app.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName SqlParametersInfo
 * @Description sql������
 * @author 21291
 * @date 2014��4��23�� ����2:50:33
 */
public class SqlParametersInfo extends Base {
	
	private static final long	serialVersionUID	= 1L;
	
	private int ParameterCount;  //��������
	private String Parameter1; //����һ
	private String Parameter2; //������
	private String Parameter3; //������
	private String Parameter4; //������
	private String Parameter5; //������
	
	public int getParameterCount() {
		return ParameterCount;
	}
	
	public void setParameterCount(int parameterCount) {
		ParameterCount = parameterCount;
	}
	
	public String getParameter1() {
		return Parameter1;
	}
	
	public void setParameter1(String parameter1) {
		Parameter1 = parameter1;
	}
	
	public String getParameter2() {
		return Parameter2;
	}
	
	public void setParameter2(String parameter2) {
		Parameter2 = parameter2;
	}
	
	public String getParameter3() {
		return Parameter3;
	}
	
	public void setParameter3(String parameter3) {
		Parameter3 = parameter3;
	}
	
	public String getParameter4() {
		return Parameter4;
	}
	
	public void setParameter4(String parameter4) {
		Parameter4 = parameter4;
	}
	
	public String getParameter5() {
		return Parameter5;
	}
	
	public void setParameter5(String parameter5) {
		Parameter5 = parameter5;
	}
	
	private SqlParametersInfo() {}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static SqlParametersInfo instance = new SqlParametersInfo();  
    }
	public static SqlParametersInfo getSqlParametersInfo() {
		return singletonHolder.instance;
	}
	
	// ��Listת����json�Ա����������д���
	public static String ConvertToJson(List<SqlParametersInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (SqlParametersInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("ParameterCount", item.ParameterCount);
					switch (item.ParameterCount) {
						case 1:
							jsonObject.put("Parameter1", item.Parameter1);
							break;
						case 2:
							jsonObject.put("Parameter1", item.Parameter1);
							jsonObject.put("Parameter2", item.Parameter2);
							break;
						case 3:
							jsonObject.put("Parameter1", item.Parameter1);
							jsonObject.put("Parameter2", item.Parameter2);
							jsonObject.put("Parameter3", item.Parameter3);
							break;
						case 4:
							jsonObject.put("Parameter1", item.Parameter1);
							jsonObject.put("Parameter2", item.Parameter2);
							jsonObject.put("Parameter3", item.Parameter3);
							jsonObject.put("Parameter4", item.Parameter4);
							break;
						case 5:
							jsonObject.put("Parameter1", item.Parameter1);
							jsonObject.put("Parameter2", item.Parameter2);
							jsonObject.put("Parameter3", item.Parameter3);
							jsonObject.put("Parameter4", item.Parameter4);
							jsonObject.put("Parameter5", item.Parameter5);
							break;
						
						default:
							break;
					}	
					
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
	public static String ConvertToJson(SqlParametersInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {			
			jsonObject.put("ParameterCount", item.ParameterCount);
			switch (item.ParameterCount) {
				case 1:
					jsonObject.put("Parameter1", item.Parameter1);
					break;
				case 2:
					jsonObject.put("Parameter1", item.Parameter1);
					jsonObject.put("Parameter2", item.Parameter2);
					break;
				case 3:
					jsonObject.put("Parameter1", item.Parameter1);
					jsonObject.put("Parameter2", item.Parameter2);
					jsonObject.put("Parameter3", item.Parameter3);
					break;
				case 4:
					jsonObject.put("Parameter1", item.Parameter1);
					jsonObject.put("Parameter2", item.Parameter2);
					jsonObject.put("Parameter3", item.Parameter3);
					jsonObject.put("Parameter4", item.Parameter4);
					break;
				case 5:
					jsonObject.put("Parameter1", item.Parameter1);
					jsonObject.put("Parameter2", item.Parameter2);
					jsonObject.put("Parameter3", item.Parameter3);
					jsonObject.put("Parameter4", item.Parameter4);
					jsonObject.put("Parameter5", item.Parameter5);
					break;
				
				default:
					break;
			}	
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
