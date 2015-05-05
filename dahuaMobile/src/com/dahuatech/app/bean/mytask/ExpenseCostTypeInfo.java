package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTypeInfo
 * @Description ������������ʵ����
 * @author 21291
 * @date 2014��5��23�� ����9:22:03
 */
public class ExpenseCostTypeInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FCostId;  		//����������ϸ����ID
	private int FCostCode;		//�������ͱ���
	private String FCostValue;  //���ý��
		
	public int getFCostId() {
		return FCostId;
	}

	public void setFCostId(int fCostId) {
		FCostId = fCostId;
	}

	public int getFCostCode() {
		return FCostCode;
	}

	public void setFCostCode(int fCostCode) {
		FCostCode = fCostCode;
	}

	public String getFCostValue() {
		return FCostValue;
	}

	public void setFCostValue(String fCostValue) {
		FCostValue = fCostValue;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseCostTypeInfo instance = new ExpenseCostTypeInfo();  
    }
	private ExpenseCostTypeInfo() {}
	public static ExpenseCostTypeInfo getExpenseCostTypeInfo() {
		return singletonHolder.instance;
	}
}
