package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTBodyInfo
 * @Description �����������뵥��ʵ��
 * @author 21291
 * @date 2014��6��17�� ����11:12:04
 */
public class ExpenseCostTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;    			//��ϸ����
	private String FConSmType; 		//��������
	private String FConSmDate;  	//��������
	private String FAppAmt;			//Ԥ������
	private String FLendType;		//�������
	private String FLDAmt;			//�����
	private String FProjectName;	//��Ŀ
	private String FUse;			//����
	
	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFConSmType() {
		return FConSmType;
	}

	public void setFConSmType(String fConSmType) {
		FConSmType = fConSmType;
	}

	public String getFConSmDate() {
		return FConSmDate;
	}

	public void setFConSmDate(String fConSmDate) {
		FConSmDate = fConSmDate;
	}

	public String getFAppAmt() {
		return FAppAmt;
	}

	public void setFAppAmt(String fAppAmt) {
		FAppAmt = fAppAmt;
	}

	public String getFLendType() {
		return FLendType;
	}

	public void setFLendType(String fLendType) {
		FLendType = fLendType;
	}

	public String getFLDAmt() {
		return FLDAmt;
	}

	public void setFLDAmt(String fLDAmt) {
		FLDAmt = fLDAmt;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}

}
