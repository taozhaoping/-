package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductReworkTBodyInfo
 * @Description �²�Ʒ�������嵥��ʵ��
 * @author 21291
 * @date 2014��8��27�� ����9:42:52
 */
public class NewProductReworkTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FLocation;   		//��λ
	private String FType;				//���
	private String FMaterialCode;   	//���ϱ���
	private String FLocationCode;   	//����Ϻ�
	private String FMaterialName;		//��������
	private String FModel;				//����ͺ�
	private String FAmount;				//����
	private String FContent;			//��������
	private String FRequireTime;		//Ҫ�����ʱ��
	private String FExpectedTime;		//Ԥ�����ʱ��
	
	public String getFLocation() {
		return FLocation;
	}
	public void setFLocation(String fLocation) {
		FLocation = fLocation;
	}
	public String getFType() {
		return FType;
	}
	public void setFType(String fType) {
		FType = fType;
	}
	public String getFMaterialCode() {
		return FMaterialCode;
	}
	public void setFMaterialCode(String fMaterialCode) {
		FMaterialCode = fMaterialCode;
	}
	public String getFLocationCode() {
		return FLocationCode;
	}
	public void setFLocationCode(String fLocationCode) {
		FLocationCode = fLocationCode;
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
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFContent() {
		return FContent;
	}
	public void setFContent(String fContent) {
		FContent = fContent;
	}
	public String getFRequireTime() {
		return FRequireTime;
	}
	public void setFRequireTime(String fRequireTime) {
		FRequireTime = fRequireTime;
	}
	public String getFExpectedTime() {
		return FExpectedTime;
	}
	public void setFExpectedTime(String fExpectedTime) {
		FExpectedTime = fExpectedTime;
	}
}
