package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTHeaderInfo
 * @Description �������뵥��ʵ��
 * @author 21291
 * @date 2014��6��16�� ����1:53:04
 */
public class ExpenseCostTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;   //��������
	private String FGeneralType; //����С�ࣨ���룩
	private String FGeneralCode; //����С�ࣨ���룩
	private String FGeneralName; //����С�ࣨ���룩
	private String FBillNo;      //���ݱ��
	private String FConSmName;   //����������
	private String FCommitDate;  //�ᵥʱ��
	private String FAppAmt;  	 //Ԥ������
	private String FLDAmt;		 //�����
	private String FCostType;    //��������
	
	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFGeneralType() {
		return FGeneralType;
	}

	public void setFGeneralType(String fGeneralType) {
		FGeneralType = fGeneralType;
	}
	
	public String getFGeneralCode() {
		return FGeneralCode;
	}

	public void setFGeneralCode(String fGeneralCode) {
		FGeneralCode = fGeneralCode;
	}

	public String getFGeneralName() {
		return FGeneralName;
	}

	public void setFGeneralName(String fGeneralName) {
		FGeneralName = fGeneralName;
	}

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFConSmName() {
		return FConSmName;
	}

	public void setFConSmName(String fConSmName) {
		FConSmName = fConSmName;
	}

	public String getFCommitDate() {
		return FCommitDate;
	}

	public void setFCommitDate(String fCommitDate) {
		FCommitDate = fCommitDate;
	}

	public String getFAppAmt() {
		return FAppAmt;
	}

	public void setFAppAmt(String fAppAmt) {
		FAppAmt = fAppAmt;
	}

	public String getFLDAmt() {
		return FLDAmt;
	}

	public void setFLDAmt(String fLDAmt) {
		FLDAmt = fLDAmt;
	}

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseCostTHeaderInfo instance = new ExpenseCostTHeaderInfo();  
    }
	private ExpenseCostTHeaderInfo() {}
	public static ExpenseCostTHeaderInfo getExpenseCostTHeaderInfo() {
		return singletonHolder.instance;
	}
}
