package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeEngravingTBodyInfo
 * @Description ӡ�����Ʊ��嵥��ʵ��
 * @author 21291
 * @date 2014��10��11�� ����2:59:26
 */
public class FeEngravingTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FCompany;			//������˾
	private String FeType; 				//ӡ������
	private String FKeeper;				//������
	private String FKeeperTel;			//�����˵绰
	private String FKeeperDept;			//���ܲ���
	private String FKeeperArea;   		//��������
	private String FeName;  			//ӡ������
	private String FReason;				//����ԭ��
	private String FNote;				//��ע
	
	public String getFCompany() {
		return FCompany;
	}
	public void setFCompany(String fCompany) {
		FCompany = fCompany;
	}
	public String getFeType() {
		return FeType;
	}
	public void setFeType(String feType) {
		FeType = feType;
	}
	public String getFKeeper() {
		return FKeeper;
	}
	public void setFKeeper(String fKeeper) {
		FKeeper = fKeeper;
	}
	public String getFKeeperTel() {
		return FKeeperTel;
	}
	public void setFKeeperTel(String fKeeperTel) {
		FKeeperTel = fKeeperTel;
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
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
