package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpensePrivateTBodyInfo
 * @Description ��˽�������ݱ���ʵ��
 * @author 21291
 * @date 2014��5��23�� ����9:29:19
 */
public class ExpensePrivateTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int FID; 	  		 //��������
	private String FConSmDate;	 //��������ʱ��
	private String FConSmType;	 //������������
	private String FAmount;  	 //���
	private String FProjectName; //��Ŀ����
	private String FClientName;  //�ͻ�����
	private String FUse;         //����
	
	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}
	
	public String getFConSmDate() {
		return FConSmDate;
	}

	public void setFConSmDate(String fConSmDate) {
		FConSmDate = fConSmDate;
	}

	public String getFConSmType() {
		return FConSmType;
	}

	public void setFConSmType(String fConSmType) {
		FConSmType = fConSmType;
	}

	public String getFAmount() {
		return FAmount;
	}

	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFClientName() {
		return FClientName;
	}

	public void setFClientName(String fClientName) {
		FClientName = fClientName;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}
}
