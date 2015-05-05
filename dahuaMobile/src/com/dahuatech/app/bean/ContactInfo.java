package com.dahuatech.app.bean;

import java.util.List;

/**
 * @ClassName ContactInfo
 * @Description ͨѶ¼ʵ��
 * @author 21291
 * @date 2014��6��26�� ����9:20:36
 */
public class ContactInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;  //����
	private String FItemName;	 //����
	private String FEmail;		 //����
	private String FCornet;		 //�̺�
	private String FDepartment;  //����
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFItemName() {
		return FItemName;
	}
	
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}

	public String getFEmail() {
		return FEmail;
	}

	public void setFEmail(String fEmail) {
		FEmail = fEmail;
	}

	public String getFCornet() {
		return FCornet;
	}

	public void setFCornet(String fCornet) {
		FCornet = fCornet;
	}

	public String getFDepartment() {
		return FDepartment;
	}

	public void setFDepartment(String fDepartment) {
		FDepartment = fDepartment;
	}
		
	public ContactInfo(){};	
	public ContactInfo(String fItemNumber,String fItemName,String fEmail,String fCornet,String fDepartment){
		this.FItemNumber=fItemNumber;
		this.FItemName=fItemName;
		this.FEmail=fEmail;
		this.FCornet=fCornet;
		this.FDepartment=fDepartment;
	}
	
	//����˷��ؽ������
	public static class ContactResultInfo{
		 public List<ContactInfo> contactList;
		 public String resultStr;
	}
}

