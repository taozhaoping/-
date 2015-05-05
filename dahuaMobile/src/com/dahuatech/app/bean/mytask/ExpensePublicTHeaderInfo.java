package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpensePublicInfo
 * @Description �Թ�֧����������ʵ��
 * @author 21291
 * @date 2014��6��4�� ����10:34:11
 */
public class ExpensePublicTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID; 				//��������(��������)
	private String FGeneralType; 		//����С�ࣨ���ƣ�
	private String FBillNo; 			//���ݱ��
	private String FConSmName; 			//����������
	private String FCommitDate; 		//�ᵥ����
	private String FAmountAll; 			//�������
	private String FRecAccName; 		//�տ�˻���
	private String FContent; 			//����
	
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

	public String getFContent() {
		return FContent;
	}

	public void setFContent(String fContent) {
		FContent = fContent;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpensePublicTHeaderInfo instance = new ExpensePublicTHeaderInfo();  
    }
	private ExpensePublicTHeaderInfo() {}
	public static ExpensePublicTHeaderInfo getExpensePublicTHeaderInfo() {
		return singletonHolder.instance;
	}
}
