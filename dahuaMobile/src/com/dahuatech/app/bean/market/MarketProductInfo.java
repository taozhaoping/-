package com.dahuatech.app.bean.market;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketProductInfo
 * @Description ��Ʒ����ʵ��
 * @author 21291
 * @date 2015��1��30�� ����9:09:59
 */
public class MarketProductInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FModel;  		//��Ʒ�ͺ�
	private String FName ;  		//��Ʒ���� 
	private String FFirstLine;		//һ����Ʒ��
	private String FSecLine;  		//������Ʒ��
	private String FStandPrice;  	//�۸�
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getFFirstLine() {
		return FFirstLine;
	}
	public void setFFirstLine(String fFirstLine) {
		FFirstLine = fFirstLine;
	}
	public String getFSecLine() {
		return FSecLine;
	}
	public void setFSecLine(String fSecLine) {
		FSecLine = fSecLine;
	}
	public String getFStandPrice() {
		return FStandPrice;
	}
	public void setFStandPrice(String fStandPrice) {
		FStandPrice = fStandPrice;
	}
	
}
