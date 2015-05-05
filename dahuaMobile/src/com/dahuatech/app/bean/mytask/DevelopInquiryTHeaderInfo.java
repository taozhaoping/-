package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopInquiryTHeaderInfo
 * @Description �з�����ѯ�������ͷʵ����Ϣ
 * @author 21291
 * @date 2014��7��16�� ����9:25:22
 */
public class DevelopInquiryTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FDate;  				//����ʱ��
	private String FApplyerDeptName;	//���벿��
	private String FEmployeeNumber;     //�����˹���
	private String FMaterialType;		//�������
	private String FOfferExplain;		//����˵��
	private String FSubEntrys;			//�Ӽ�����

	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}

	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}

	public String getFEmployeeNumber() {
		return FEmployeeNumber;
	}

	public void setFEmployeeNumber(String fEmployeeNumber) {
		FEmployeeNumber = fEmployeeNumber;
	}

	public String getFMaterialType() {
		return FMaterialType;
	}

	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
	}

	public String getFOfferExplain() {
		return FOfferExplain;
	}

	public void setFOfferExplain(String fOfferExplain) {
		FOfferExplain = fOfferExplain;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��� ����ģʽ
	private static class singletonHolder {  
        private static DevelopInquiryTHeaderInfo instance = new DevelopInquiryTHeaderInfo();  
    } 
	private DevelopInquiryTHeaderInfo() {}
	public static DevelopInquiryTHeaderInfo getDevelopInquiryTHeaderInfo() {
		return singletonHolder.instance;
	}
}
