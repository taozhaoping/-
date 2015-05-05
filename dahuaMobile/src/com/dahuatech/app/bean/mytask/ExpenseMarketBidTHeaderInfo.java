package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseMarketBidTHeaderInfo
 * @Description �г�Ͷ�걨��ʵ������Ϣ
 * @author 21291
 * @date 2014��6��25�� ����1:50:51
 */
public class ExpenseMarketBidTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;   			//��������
	private String FBillNo;      	//���ݱ��
	private String FConSmName;   	//����������
	private String FCommitDate;  	//�ᵥʱ��
	private String FAmountAll;   	//֧���ܽ��
	private String FRecAccName;  	//�տ�˻���
	private String FPubPayNo;    	//Ͷ�����뵥��
	private String FProjectName; 	//��Ŀ����
	private String FConSmTypeName;  //��������
	private String FCaseType;		//�������
	private String FSetOffType;		//��������
	
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

	public String getFRecAccName() {
		return FRecAccName;
	}

	public void setFRecAccName(String fRecAccName) {
		FRecAccName = fRecAccName;
	}

	public String getFPubPayNo() {
		return FPubPayNo;
	}

	public void setFPubPayNo(String fPubPayNo) {
		FPubPayNo = fPubPayNo;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFConSmTypeName() {
		return FConSmTypeName;
	}

	public void setFConSmTypeName(String fConSmTypeName) {
		FConSmTypeName = fConSmTypeName;
	}

	public String getFCaseType() {
		return FCaseType;
	}

	public void setFCaseType(String fCaseType) {
		FCaseType = fCaseType;
	}

	public String getFSetOffType() {
		return FSetOffType;
	}

	public void setFSetOffType(String fSetOffType) {
		FSetOffType = fSetOffType;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseMarketBidTHeaderInfo instance = new ExpenseMarketBidTHeaderInfo();  
    }
	private ExpenseMarketBidTHeaderInfo() {}
	public static ExpenseMarketBidTHeaderInfo getExpenseMarketBidTHeaderInfo() {
		return singletonHolder.instance;
	}
}
