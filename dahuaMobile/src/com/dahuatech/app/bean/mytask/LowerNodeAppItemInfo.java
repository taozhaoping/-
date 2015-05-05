package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppItemInfo
 * @Description �����˻�����Ϣ���ϰ�װ��
 * @author 21291
 * @date 2014��11��10�� ����4:11:23
 */
public class LowerNodeAppItemInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FApproveUser;		//�����˻�����Ϣʵ�弯��
	private String FApproveRole;		//�����˽�ɫ��Ϣʵ�弯��
	
	public String getFApproveUser() {
		return FApproveUser;
	}
	public void setFApproveUser(String fApproveUser) {
		FApproveUser = fApproveUser;
	}
	public String getFApproveRole() {
		return FApproveRole;
	}
	public void setFApproveRole(String fApproveRole) {
		FApproveRole = fApproveRole;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static LowerNodeAppItemInfo instance = new LowerNodeAppItemInfo();  
    }
	private LowerNodeAppItemInfo() {}
	public static LowerNodeAppItemInfo getLowerNodeAppItemInfo() {
		return singletonHolder.instance;
	}
}
