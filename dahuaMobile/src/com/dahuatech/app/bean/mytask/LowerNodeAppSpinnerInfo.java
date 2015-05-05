package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppSpinnerInfo
 * @Description ÿ���¼��ڵ�������������ʵ��
 * @author 21291
 * @date 2014��11��12�� ����9:23:49
 */
public class LowerNodeAppSpinnerInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FSpinnerType;			//����������
	private int FSpinnerIndex;				//����������
	private int FRoleSpinnerCount;			//��ɫ����������
	private String FSpinnerValue;			//������ѡ�е��ı�ֵ
	
	public String getFSpinnerType() {
		return FSpinnerType;
	}
	public void setFSpinnerType(String fSpinnerType) {
		FSpinnerType = fSpinnerType;
	}
	public int getFSpinnerIndex() {
		return FSpinnerIndex;
	}
	public void setFSpinnerIndex(int fSpinnerIndex) {
		FSpinnerIndex = fSpinnerIndex;
	}
	public int getFRoleSpinnerCount() {
		return FRoleSpinnerCount;
	}
	public void setFRoleSpinnerCount(int fRoleSpinnerCount) {
		FRoleSpinnerCount = fRoleSpinnerCount;
	}
	public String getFSpinnerValue() {
		return FSpinnerValue;
	}
	public void setFSpinnerValue(String fSpinnerValue) {
		FSpinnerValue = fSpinnerValue;
	}
	
	public LowerNodeAppSpinnerInfo() {
		this.FSpinnerValue="";
	}
}
