package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppRoleJsonInfo
 * @Description �����˽�ɫ��Ϣʵ��(JSON�ַ�����)
 * @author 21291
 * @date 2014��11��11�� ����11:46:42
 */
public class LowerNodeAppRoleJsonInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FRoleName;		//��ɫ����
	private String FRoleApproveUser;		//��ɫ�����˻�����Ϣ (Ա������) 

	public String getFRoleName() {
		return FRoleName;
	}
	public void setFRoleName(String fRoleName) {
		FRoleName = fRoleName;
	}
	public String getFRoleApproveUser() {
		return FRoleApproveUser;
	}
	public void setFRoleApproveUser(String fRoleApproveUser) {
		FRoleApproveUser = fRoleApproveUser;
	}
}
