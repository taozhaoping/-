package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmSubClassInfo
 * @Description �з���ʱ�б�������ʵ����
 * @author 21291
 * @date 2014��11��5�� ����10:46:12
 */
public class DHConfirmSubClassInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;			//ȷ����ԱԱ����
	private String FItemName;			//ȷ����ԱԱ������
	
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
