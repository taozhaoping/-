package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdCheckStatusInfo
 * @Description ����״̬ʵ��
 * @author 21291
 * @date 2014��12��29�� ����4:44:23
 */
public class AdCheckStatusInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FStatus;  			//����״̬  0 ����δ�� 1��������δǩ�� ������ǩ�� 2�����δǩ�� 3�����Ѵ���ǩ��
	private int FAttendId;			//����ID
	private String FCheckInTime;	//ǩ��ʱ��
	private String FCheckOutTime;	//ǩ��ʱ��
	
	public int getFStatus() {
		return FStatus;
	}
	public void setFStatus(int fStatus) {
		FStatus = fStatus;
	}
	public int getFAttendId() {
		return FAttendId;
	}
	public void setFAttendId(int fAttendId) {
		FAttendId = fAttendId;
	}
	public String getFCheckInTime() {
		return FCheckInTime;
	}
	public void setFCheckInTime(String fCheckInTime) {
		FCheckInTime = fCheckInTime;
	}
	public String getFCheckOutTime() {
		return FCheckOutTime;
	}
	public void setFCheckOutTime(String fCheckOutTime) {
		FCheckOutTime = fCheckOutTime;
	}
	
	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static AdCheckStatusInfo instance = new AdCheckStatusInfo();  
    }
	private AdCheckStatusInfo() {}
	public static AdCheckStatusInfo getAdCheckStatusInfo() {
		return SingletonHolder.instance;
	}
}
