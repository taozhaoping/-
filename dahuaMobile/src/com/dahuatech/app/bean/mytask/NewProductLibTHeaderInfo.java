package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductLibTHeaderInfo
 * @Description �²�Ʒת���ͷ����ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:32:20
 */
public class NewProductLibTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//����ʱ��
	private String FApplyDept;			//��������
	private String FTel;				//��ϵ�绰
	private String FMaterialType;   	//ת����������
	private String FProductLine;   		//��Ʒ��
	private String FDescribe;   		//����
	private String FReason;   			//ԭ��
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFMaterialType() {
		return FMaterialType;
	}
	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
	}
	public String getFProductLine() {
		return FProductLine;
	}
	public void setFProductLine(String fProductLine) {
		FProductLine = fProductLine;
	}
	public String getFDescribe() {
		return FDescribe;
	}
	public void setFDescribe(String fDescribe) {
		FDescribe = fDescribe;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static NewProductLibTHeaderInfo instance = new NewProductLibTHeaderInfo();  
    }
	private NewProductLibTHeaderInfo() {}
	public static NewProductLibTHeaderInfo getNewProductLibTHeaderInfo() {
		return singletonHolder.instance;
	}
}
