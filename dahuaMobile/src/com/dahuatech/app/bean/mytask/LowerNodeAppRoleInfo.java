package com.dahuatech.app.bean.mytask;

import java.util.List;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppRoleInfo
 * @Description �����˽�ɫ��Ϣʵ��
 * @author 21291
 * @date 2014��11��10�� ����4:17:12
 */
public class LowerNodeAppRoleInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FRoleName;									//��ɫ����
	private List<LowerNodeAppUserInfo> FUserList;				//Ա������ 

	public String getFRoleName() {
		return FRoleName;
	}
	public void setFRoleName(String fRoleName) {
		FRoleName = fRoleName;
	}
	public List<LowerNodeAppUserInfo> getFUserList() {
		return FUserList;
	}
	public void setFUserList(List<LowerNodeAppUserInfo> fUserList) {
		FUserList = fUserList;
	}
}
