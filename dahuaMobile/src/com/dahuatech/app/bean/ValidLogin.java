package com.dahuatech.app.bean;


/**
 * @ClassName ValidLogin
 * @Description ���������½��֤��
 * @author 21291
 * @date 2015��2��5�� ����4:31:40
 */
public class ValidLogin extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//Ա����
	private String FItemName; 		//Ա������
	private int FIsValid;			//�����Ƿ���Ч  0-��Ч��1-��Ч
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

	public int getFIsValid() {
		return FIsValid;
	}

	public void setFIsValid(int fIsValid) {
		FIsValid = fIsValid;
	}
}
