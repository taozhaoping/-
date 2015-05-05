package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseMarketPayTHeaderInfo
 * @Description �г�Ͷ��֧��ʵ������Ϣ
 * @author 21291
 * @date 2014��6��25�� ����1:50:28
 */
public class ExpenseMarketPayTHeaderInfo extends Base {

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

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseMarketPayTHeaderInfo instance = new ExpenseMarketPayTHeaderInfo();  
    }
	private ExpenseMarketPayTHeaderInfo() {}
	public static ExpenseMarketPayTHeaderInfo getExpenseMarketPayTHeaderInfo() {
		return singletonHolder.instance;
	}
}
