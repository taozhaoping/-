package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName EmailOpenTHeaderInfo
 * @Description ���俪ͨ��ͷ����ʵ��
 * @author 21291
 * @date 2014��8��18�� ����10:43:16
 */
public class EmailOpenTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyDate;			//����ʱ��
	private String FEmailType;  		//�������
	private String FApplyName;			//������
	private String FApplyDept;			//��������
	private String FReason;				//����
	private String FApplyPermission;	//����Ȩ��
	private String FInfoSafe;			//��Ϣ��ȫ
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFEmailType() {
		return FEmailType;
	}
	public void setFEmailType(String fEmailType) {
		FEmailType = fEmailType;
	}
	public String getFApplyName() {
		return FApplyName;
	}
	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFApplyPermission() {
		return FApplyPermission;
	}
	public void setFApplyPermission(String fApplyPermission) {
		FApplyPermission = fApplyPermission;
	}
	public String getFInfoSafe() {
		return FInfoSafe;
	}
	public void setFInfoSafe(String fInfoSafe) {
		FInfoSafe = fInfoSafe;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static EmailOpenTHeaderInfo instance = new EmailOpenTHeaderInfo();  
    }
	private EmailOpenTHeaderInfo() {}
	public static EmailOpenTHeaderInfo getEmailOpenTHeaderInfo() {
		return singletonHolder.instance;
	}
}
