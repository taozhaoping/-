package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeUpdateTBodyInfo
 * @Description ӡ���������嵥��ʵ��
 * @author 21291
 * @date 2014��10��11�� ����10:48:31
 */
public class FeUpdateTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FeCode;    			//ӡ������
	private String FeType; 				//ӡ������
	private String FeName;  			//ӡ������
	private String FCompany;			//������˾
	private String FStatus;				//״̬
	private String FKeeper;				//������
	private String FKeeperDept;			//���ܲ���
	private String FKeeperArea;   		//��������
	private String FReason;				//����ԭ��
	
	public String getFeCode() {
		return FeCode;
	}
	public void setFeCode(String feCode) {
		FeCode = feCode;
	}
	public String getFeType() {
		return FeType;
	}
	public void setFeType(String feType) {
		FeType = feType;
	}
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFCompany() {
		return FCompany;
	}
	public void setFCompany(String fCompany) {
		FCompany = fCompany;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	public String getFKeeper() {
		return FKeeper;
	}
	public void setFKeeper(String fKeeper) {
		FKeeper = fKeeper;
	}
	public String getFKeeperDept() {
		return FKeeperDept;
	}
	public void setFKeeperDept(String fKeeperDept) {
		FKeeperDept = fKeeperDept;
	}
	public String getFKeeperArea() {
		return FKeeperArea;
	}
	public void setFKeeperArea(String fKeeperArea) {
		FKeeperArea = fKeeperArea;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}

