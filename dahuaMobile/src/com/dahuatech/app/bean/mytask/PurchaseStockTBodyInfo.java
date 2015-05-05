package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PurchaseStockTBodyInfo
 * @Description �ɹ����ϵ��ݱ���ʵ��
 * @author 21291
 * @date 2014��8��15�� ����2:26:35
 */
public class PurchaseStockTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FMaterialCode;   	//���ϴ���
	private String FMaterialName;		//��������
	private String FModel;				//����ͺ�
	private String FNumber;				//����
	private String FUnit;				//��λ
	private String FRdDate;				//Ҫ�󵽻�����
	private String FEstimatedPrice;	    //Ԥ�Ƽ۸�
	private String FNote;	    		//��ע
	
	public String getFMaterialCode() {
		return FMaterialCode;
	}
	public void setFMaterialCode(String fMaterialCode) {
		FMaterialCode = fMaterialCode;
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
	public String getFNumber() {
		return FNumber;
	}
	public void setFNumber(String fNumber) {
		FNumber = fNumber;
	}
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFRdDate() {
		return FRdDate;
	}
	public void setFRdDate(String fRdDate) {
		FRdDate = fRdDate;
	}
	public String getFEstimatedPrice() {
		return FEstimatedPrice;
	}
	public void setFEstimatedPrice(String fEstimatedPrice) {
		FEstimatedPrice = fEstimatedPrice;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
