package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ProjectReadTHeaderInfo
 * @Description ��Ŀ��Ϣ�Ķ�Ȩ�����뵥��ͷʵ��
 * @author 21291
 * @date 2014��9��23�� ����2:20:49
 */
public class ProjectReadTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyNumber;  		//�����˹���
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FTel;				//��ϵ�绰
	private String FPermissionType;		//Ȩ���޸�����
	private String FProjectManage;		//��Ŀ����
	private String FProgramPublic;		//���򷢲�
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
	public String getFApplyNumber() {
		return FApplyNumber;
	}
	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
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
	public String getFPermissionType() {
		return FPermissionType;
	}
	public void setFPermissionType(String fPermissionType) {
		FPermissionType = fPermissionType;
	}
	public String getFProjectManage() {
		return FProjectManage;
	}
	public void setFProjectManage(String fProjectManage) {
		FProjectManage = fProjectManage;
	}
	public String getFProgramPublic() {
		return FProgramPublic;
	}
	public void setFProgramPublic(String fProgramPublic) {
		FProgramPublic = fProgramPublic;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ProjectReadTHeaderInfo instance = new ProjectReadTHeaderInfo();  
    }
	private ProjectReadTHeaderInfo() {}
	public static ProjectReadTHeaderInfo getProjectReadTHeaderInfo() {
		return singletonHolder.instance;
	}
}
