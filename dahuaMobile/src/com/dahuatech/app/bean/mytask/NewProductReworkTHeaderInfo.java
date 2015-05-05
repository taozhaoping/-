package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductReworkTHeaderInfo
 * @Description �²�Ʒ������ͷ����ʵ��
 * @author 21291
 * @date 2014��8��27�� ����9:36:02
 */
public class NewProductReworkTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//����ʱ��
	private String FApplyDept;			//��������
	private String FProductLine;   		//��Ʒ��
	private String FProductName;   		//��Ʒ����
	private String FProductModel;   	//��Ʒ�ͺ�
	private String FProjectName;   		//��Ŀ����
	private String FProjectCode;   		//��Ŀ���
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
	public String getFProductLine() {
		return FProductLine;
	}
	public void setFProductLine(String fProductLine) {
		FProductLine = fProductLine;
	}
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFProductModel() {
		return FProductModel;
	}
	public void setFProductModel(String fProductModel) {
		FProductModel = fProductModel;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
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
        private static NewProductReworkTHeaderInfo instance = new NewProductReworkTHeaderInfo();  
    }
	private NewProductReworkTHeaderInfo() {}
	public static NewProductReworkTHeaderInfo getNewProductReworkTHeaderInfo() {
		return singletonHolder.instance;
	}
}
