package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductLibTBodyInfo
 * @Description �²�Ʒת����嵥��ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:37:56
 */
public class NewProductLibTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FModel;   			//����ͺ�
	private String FProductName;		//��Ʒ����
	private String FUnit;				//������λ
	private String FAmount;				//����
	private String FOutLocation;		//��ת����λ
	private String FInLocation;			//��ת���λ
	private String FNote;				//��ע
	
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFOutLocation() {
		return FOutLocation;
	}
	public void setFOutLocation(String fOutLocation) {
		FOutLocation = fOutLocation;
	}
	public String getFInLocation() {
		return FInLocation;
	}
	public void setFInLocation(String fInLocation) {
		FInLocation = fInLocation;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
