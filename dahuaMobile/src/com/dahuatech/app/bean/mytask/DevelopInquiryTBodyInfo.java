package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopInquiryTBodyInfo
 * @Description �з�����ѯ���������ʵ����Ϣ
 * @author 21291
 * @date 2014��7��16�� ����9:26:11
 */
public class DevelopInquiryTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	public String FMasterialName;		//��������
	public String FSupplier;			//��Ӧ��
	public String FManufacturer;		//������
	public String FOffer;				//����
	public String FCurrency;			//�ұ�
	public String FOrderQuantityFrom;	//������(��)
	public String FOrderQuantityTo;		//������(��)
	public String FUnit;				//������λ
	public String FOrderForward;		//������ǰ��
	public String FMini;				//��С��װ��
	public String FMiniOrder;			//��С����
	public String FPayment;				//����
	
	public String getFMasterialName() {
		return FMasterialName;
	}

	public void setFMasterialName(String fMasterialName) {
		FMasterialName = fMasterialName;
	}

	public String getFSupplier() {
		return FSupplier;
	}

	public void setFSupplier(String fSupplier) {
		FSupplier = fSupplier;
	}
	
	public String getFManufacturer() {
		return FManufacturer;
	}

	public void setFManufacturer(String fManufacturer) {
		FManufacturer = fManufacturer;
	}

	public String getFOffer() {
		return FOffer;
	}

	public void setFOffer(String fOffer) {
		FOffer = fOffer;
	}

	public String getFCurrency() {
		return FCurrency;
	}

	public void setFCurrency(String fCurrency) {
		FCurrency = fCurrency;
	}

	public String getFOrderQuantityFrom() {
		return FOrderQuantityFrom;
	}

	public void setFOrderQuantityFrom(String fOrderQuantityFrom) {
		FOrderQuantityFrom = fOrderQuantityFrom;
	}

	public String getFOrderQuantityTo() {
		return FOrderQuantityTo;
	}

	public void setFOrderQuantityTo(String fOrderQuantityTo) {
		FOrderQuantityTo = fOrderQuantityTo;
	}

	public String getFUnit() {
		return FUnit;
	}

	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}

	public String getFOrderForward() {
		return FOrderForward;
	}

	public void setFOrderForward(String fOrderForward) {
		FOrderForward = fOrderForward;
	}

	public String getFMini() {
		return FMini;
	}

	public void setFMini(String fMini) {
		FMini = fMini;
	}

	public String getFMiniOrder() {
		return FMiniOrder;
	}

	public void setFMiniOrder(String fMiniOrder) {
		FMiniOrder = fMiniOrder;
	}

	public String getFPayment() {
		return FPayment;
	}

	public void setFPayment(String fPayment) {
		FPayment = fPayment;
	}
}
