package com.dahuatech.app.bean.market;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketBidInfo
 * @Description ��������ʵ��
 * @author 21291
 * @date 2015��1��26�� ����2:19:11
 */
public class MarketBidInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FSystemType;  		//ϵͳID
	private String FClassTypeID;  		//��������ID
	private String FBillID;			    //����ID
	private String FCustomerName;  		//�ͻ�����
	private String FBidCode;  			//���۵���
	private String FNodeName;  			//��ǰ�ڵ�����
	private String FTasker;  			//��ǰ��������
	
	public String getFSystemType() {
		return FSystemType;
	}
	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}
	public String getFClassTypeID() {
		return FClassTypeID;
	}
	public void setFClassTypeID(String fClassTypeID) {
		FClassTypeID = fClassTypeID;
	}
	public String getFBillID() {
		return FBillID;
	}
	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}
	public String getFCustomerName() {
		return FCustomerName;
	}
	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getFBidCode() {
		return FBidCode;
	}
	public void setFBidCode(String fBidCode) {
		FBidCode = fBidCode;
	}
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public String getFTasker() {
		return FTasker;
	}
	public void setFTasker(String fTasker) {
		FTasker = fTasker;
	}
	
}
