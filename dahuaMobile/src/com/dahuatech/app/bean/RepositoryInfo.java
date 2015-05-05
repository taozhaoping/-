package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName RepositoryInfo
 * @Description �ִ���
 * @author 21291
 * @date 2014��4��23�� ����2:44:11
 */
public class RepositoryInfo extends Base {

	private static final long	serialVersionUID	= 1L;
	
	private String ClassType;   //ϵͳ����ID
	private String IsTest;      //�Ƿ���� 0 ���� 1 ��ʽ
	private String ServiceName; // ��������
		
	//�������� 1 ���ݷ���  2 �ֶ�Ȩ�޷��� 3 �˵�Ȩ�޷��� 4 ����Ȩ�޷��� 5.����������
	private String ServiceType; 
	private boolean SqlType;  	// �Ƿ�洢����
	private boolean IsCahce; 	// �Ƿ���Ҫ����
	private String  FItemNumber;// ͳ����־��Ϣʱ���õ���Ա����

	public String getClassType() {
		return ClassType;
	}

	public void setClassType(String classType) {
		ClassType = classType;
	}

	public String getIsTest() {
		return IsTest;
	}

	public void setIsTest(String isTest) {
		IsTest = isTest;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public boolean isSqlType() {
		return SqlType;
	}

	public void setSqlType(boolean sqlType) {
		SqlType = sqlType;
	}

	public boolean isIsCahce() {
		return IsCahce;
	}

	public void setIsCahce(boolean isCahce) {
		IsCahce = isCahce;
	}

	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	private RepositoryInfo() {
		SqlType = false;
		IsCahce = false;
		FItemNumber="";
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static RepositoryInfo instance = new RepositoryInfo();  
    }
	public static RepositoryInfo getRepositoryInfo() {
		return singletonHolder.instance;
	}
    
    // ��ʵ�����ת����json�Ա����������д���
    public static String ConvertToJson(RepositoryInfo item) {
        String jsonString = "";
        JSONObject jsonObject = new JSONObject();
        try {
	    	  jsonObject.put("ClassType", item.ClassType);  
	          jsonObject.put("IsTest", item.IsTest);  
	          jsonObject.put("ServiceName", item.ServiceName);  
	          jsonObject.put("ServiceType", item.ServiceType);  
	          jsonObject.put("SqlType", item.SqlType);  
	          jsonObject.put("IsCahce", item.IsCahce);  
	          jsonObject.put("FItemNumber", item.FItemNumber);  
	          jsonString = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }	
}
