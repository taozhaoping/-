package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionBeforeTBodyInfo
 * @Description TDȨ�����뵥����ǰ����ʵ��
 * @author 21291
 * @date 2014��9��23�� ����11:48:46
 */
public class TdPermissionBeforeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyNumber;  		//�����˹���
	private String FApplyName;  		//����������
	private String FApplyDept;			//��������
	private String FEmail;				//�����ַ
	private String FTdDomain;			//TD��
	private String FTdProject;			//TD��Ŀ
	private String FCodeAndName;		//��Ŀ��ź�����
	private String FManager;			//��Ŀ����
	private String FProjectPermission;	//��ĿȨ��
	
	public String getFApplyNumber() {
		return FApplyNumber;
	}
	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
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
	public String getFEmail() {
		return FEmail;
	}
	public void setFEmail(String fEmail) {
		FEmail = fEmail;
	}
	public String getFTdDomain() {
		return FTdDomain;
	}
	public void setFTdDomain(String fTdDomain) {
		FTdDomain = fTdDomain;
	}
	public String getFTdProject() {
		return FTdProject;
	}
	public void setFTdProject(String fTdProject) {
		FTdProject = fTdProject;
	}
	public String getFCodeAndName() {
		return FCodeAndName;
	}
	public void setFCodeAndName(String fCodeAndName) {
		FCodeAndName = fCodeAndName;
	}
	public String getFManager() {
		return FManager;
	}
	public void setFManager(String fManager) {
		FManager = fManager;
	}
	public String getFProjectPermission() {
		return FProjectPermission;
	}
	public void setFProjectPermission(String fProjectPermission) {
		FProjectPermission = fProjectPermission;
	}
}
