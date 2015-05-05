package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseSpecialTHeaderInfo
 * @Description �������������ݱ�ͷʵ��
 * @author 21291
 * @date 2014��6��20�� ����10:01:00
 */
public class ExpenseSpecialTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FID;   		 //��������
	private String FGeneralType; //����С�ࣨ���룩
	private String FGeneralCode; //����С�ࣨ���룩
	private String FGeneralName; //����С�ࣨ���룩
	private String FBillNo;      //���ݱ��
	private String FConSmName;   //����������
	private String FCommitDate;  //�ᵥʱ��
	private String FAmountAll;   //���
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

	public String getFAmountAll() {
		return FAmountAll;
	}

	public void setFAmountAll(String fAmountAll) {
		FAmountAll = fAmountAll;
	}

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseSpecialTHeaderInfo instance = new ExpenseSpecialTHeaderInfo();  
    }
	private ExpenseSpecialTHeaderInfo() {}
	public static ExpenseSpecialTHeaderInfo getExpenseSpecialTHeaderInfo() {
		return singletonHolder.instance;
	}
}
