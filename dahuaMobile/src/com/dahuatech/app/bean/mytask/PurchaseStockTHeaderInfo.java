package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PurchaseStockTHeaderInfo
 * @Description �ɹ����ϵ��ݱ�ͷʵ��
 * @author 21291
 * @date 2014��8��15�� ����2:25:42
 */
public class PurchaseStockTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//����ʱ��
	private String FDept;				//��������
	private String FProjectCode;   		//��Ŀ����
	private String FProjectName;   		//��Ŀ����
	private String FProcessName;   		//���ո�����
	private String FTechnologyName;   	//����������
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
	public String getFDept() {
		return FDept;
	}
	public void setFDept(String fDept) {
		FDept = fDept;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFProcessName() {
		return FProcessName;
	}
	public void setFProcessName(String fProcessName) {
		FProcessName = fProcessName;
	}
	public String getFTechnologyName() {
		return FTechnologyName;
	}
	public void setFTechnologyName(String fTechnologyName) {
		FTechnologyName = fTechnologyName;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static PurchaseStockTHeaderInfo instance = new PurchaseStockTHeaderInfo();  
    }
	private PurchaseStockTHeaderInfo() {}
	public static PurchaseStockTHeaderInfo getPurchaseStockTHeaderInfo() {
		return singletonHolder.instance;
	}
}
