package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionAfterTBodyInfo
 * @Description TDȨ�����뵥���������ʵ��
 * @author 21291
 * @date 2014��9��23�� ����11:48:25
 */
public class TdPermissionAfterTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyNumber;  		//�����˹���
	private String FApplyName;  		//����������
	private String FApplyDept;			//��������
	private String FEmail;				//�����ַ
	private String FProductName;		//��Ʒ������
	private String FProductType;		//��Ʒ�ߴ���
	private String FCodeAndName;		//��Ŀ��ź�����
	private String FTestPermission;		//������ԱȨ��
	private String FDevelopPermission;	//�з���ԱȨ��
	private String FManagerPermission;	//��Ŀ����Ȩ��
	private String FReadOnly;			//ֻ��Ȩ��
	
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
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFProductType() {
		return FProductType;
	}
	public void setFProductType(String fProductType) {
		FProductType = fProductType;
	}
	public String getFCodeAndName() {
		return FCodeAndName;
	}
	public void setFCodeAndName(String fCodeAndName) {
		FCodeAndName = fCodeAndName;
	}
	public String getFTestPermission() {
		return FTestPermission;
	}
	public void setFTestPermission(String fTestPermission) {
		FTestPermission = fTestPermission;
	}
	public String getFDevelopPermission() {
		return FDevelopPermission;
	}
	public void setFDevelopPermission(String fDevelopPermission) {
		FDevelopPermission = fDevelopPermission;
	}
	public String getFManagerPermission() {
		return FManagerPermission;
	}
	public void setFManagerPermission(String fManagerPermission) {
		FManagerPermission = fManagerPermission;
	}
	public String getFReadOnly() {
		return FReadOnly;
	}
	public void setFReadOnly(String fReadOnly) {
		FReadOnly = fReadOnly;
	}
	
}
