package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdBorrowTHeaderInfo
 * @Description �����ļ��������뵥��ͷʵ��
 * @author 21291
 * @date 2014��8��28�� ����4:25:02
 */
public class TdBorrowTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FTel;				//��ϵ�绰
	private String FSecrecyDate;		//��������
	private String FDocumentType;		//�ĵ�����
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
	public String getFSecrecyDate() {
		return FSecrecyDate;
	}
	public void setFSecrecyDate(String fSecrecyDate) {
		FSecrecyDate = fSecrecyDate;
	}
	public String getFDocumentType() {
		return FDocumentType;
	}
	public void setFDocumentType(String fDocumentType) {
		FDocumentType = fDocumentType;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static TdBorrowTHeaderInfo instance = new TdBorrowTHeaderInfo();  
    }
	private TdBorrowTHeaderInfo() {}
	public static TdBorrowTHeaderInfo getTdBorrowTHeaderInfo() {
		return singletonHolder.instance;
	}
}
