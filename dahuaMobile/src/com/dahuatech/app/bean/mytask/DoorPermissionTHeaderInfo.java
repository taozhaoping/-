package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DoorPermissionTHeaderInfo
 * @Description �Ž�Ȩ�����뵥�ݱ�ͷʵ��
 * @author 21291
 * @date 2014��8��21�� ����2:16:53
 */
public class DoorPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FApplyType;			//��������
	private String FTel;				//��ϵ�绰
	private String FIsOther;		    //�����������Ż򹫹��Ž�Ȩ��
	private String FRegion;				//��Ӧ����
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
	public String getFApplyType() {
		return FApplyType;
	}
	public void setFApplyType(String fApplyType) {
		FApplyType = fApplyType;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFIsOther() {
		return FIsOther;
	}
	public void setFIsOther(String fIsOther) {
		FIsOther = fIsOther;
	}
	public String getFRegion() {
		return FRegion;
	}
	public void setFRegion(String fRegion) {
		FRegion = fRegion;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DoorPermissionTHeaderInfo instance = new DoorPermissionTHeaderInfo();  
    }
	private DoorPermissionTHeaderInfo() {}
	public static DoorPermissionTHeaderInfo getDoorPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
