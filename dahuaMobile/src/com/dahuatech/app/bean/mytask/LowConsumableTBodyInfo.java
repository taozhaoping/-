package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowConsumableTBodyInfo
 * @Description ��ֵ�׺����ϴ��뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��8��19�� ����4:36:07
 */
public class LowConsumableTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FMaterialType;   	//��������
	private String FMaterialName;		//��������
	private String FModel;				//����ͺ�
	private String FUnit;				//��λ
	private String FAccount;	    	//�·�����
	private String FNote;	    		//��ע
	
	public String getFMaterialType() {
		return FMaterialType;
	}
	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
	}
	public String getFMaterialName() {
		return FMaterialName;
	}
	public void setFMaterialName(String fMaterialName) {
		FMaterialName = fMaterialName;
	}
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFAccount() {
		return FAccount;
	}
	public void setFAccount(String fAccount) {
		FAccount = fAccount;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
