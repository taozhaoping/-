package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppUserInfo
 * @Description �����˻�����Ϣʵ��
 * @author 21291
 * @date 2014��11��10�� ����4:12:46
 */
public class LowerNodeAppUserInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;		//Ա����
	private String FItemName;		//Ա������
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
}
