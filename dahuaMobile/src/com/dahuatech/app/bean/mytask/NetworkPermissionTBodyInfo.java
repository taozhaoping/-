package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NetworkPermissionTBodyInfo
 * @Description ����Ȩ�����뵥���������ʵ��
 * @author 21291
 * @date 2014��7��9�� ����3:49:47
 */
public class NetworkPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FIpAddress; 		//����¥��
	private String FComboBox1;  	//��ͨ��Ч����
	private String FComboBox3;		//����Ȩ�����
	private String FDate1;			//��ʼʱ��
	
	public String getFIpAddress() {
		return FIpAddress;
	}
	public void setFIpAddress(String fIpAddress) {
		FIpAddress = fIpAddress;
	}
	public String getFComboBox1() {
		return FComboBox1;
	}
	public void setFComboBox1(String fComboBox1) {
		FComboBox1 = fComboBox1;
	}
	public String getFComboBox3() {
		return FComboBox3;
	}
	public void setFComboBox3(String fComboBox3) {
		FComboBox3 = fComboBox3;
	}
	public String getFDate1() {
		return FDate1;
	}
	public void setFDate1(String fDate1) {
		FDate1 = fDate1;
	}
}
