package com.dahuatech.app.bean.expense;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseFlowItemInfo
 * @Description ��ˮ�ͻ�/��Ŀ�б���ʵ��
 * @author 21291
 * @date 2014��9��1�� ����6:40:39
 */
public class ExpenseFlowItemInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FId; 		//��������
	private String FItemName; 	//Ա������Ŀ����
	public String getFId() {
		return FId;
	}
	public void setFId(String fId) {
		FId = fId;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	//Ĭ�Ϲ��캯��
	public ExpenseFlowItemInfo(){}
}
