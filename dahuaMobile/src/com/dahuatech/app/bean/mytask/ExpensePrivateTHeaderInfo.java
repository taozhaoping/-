package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpensePrivateTHeaderInfo
 * @Description ��˽�������ݱ�ͷʵ��
 * @author 21291
 * @date 2014��5��22�� ����5:22:45
 */
public class ExpensePrivateTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FID; 				//��������(��������)
	private String FBillNo; 			//���ݱ��
	private String FAmountAll; 			//�������
	private String FConSmAmountAll; 	//���ý��ϼ�
	private String FConSmName; 			//����������
	private String FCommitDate; 		//�ᵥ����
	private String FCostType; 			//��������

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFAmountAll() {
		return FAmountAll;
	}

	public void setFAmountAll(String fAmountAll) {
		FAmountAll = fAmountAll;
	}

	public String getFConSmAmountAll() {
		return FConSmAmountAll;
	}

	public void setFConSmAmountAll(String fConSmAmountAll) {
		FConSmAmountAll = fConSmAmountAll;
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

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpensePrivateTHeaderInfo instance = new ExpensePrivateTHeaderInfo();  
    }
	private ExpensePrivateTHeaderInfo() {}
	public static ExpensePrivateTHeaderInfo getExpensePrivateTHeaderInfo() {
		return singletonHolder.instance;
	}
}
