package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdAmapInfo
 * @Description �ҵĿ������ĵص�ʵ��
 * @author 21291
 * @date 2014��12��31�� ����10:47:43
 */
public class AdAmapInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAddressType;	//�����ַ����
	private String FAddress;		//�����ַ
	private String FLatitude;  		//��ַ����γ��
	private String FLongitude;  	//��ַ���꾭��
	private String FRadius;			//�뾶
	
	public String getFAddressType() {
		return FAddressType;
	}
	public void setFAddressType(String fAddressType) {
		FAddressType = fAddressType;
	}
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}
	public String getFLatitude() {
		return FLatitude;
	}
	public void setFLatitude(String fLatitude) {
		FLatitude = fLatitude;
	}
	public String getFLongitude() {
		return FLongitude;
	}
	public void setFLongitude(String fLongitude) {
		FLongitude = fLongitude;
	}
	public String getFRadius() {
		return FRadius;
	}
	public void setFRadius(String fRadius) {
		FRadius = fRadius;
	}
}
