package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName AttendanceListInfo
 * @Description ���ڼ�¼�б�ʵ��
 * @author 21291
 * @date 2014��12��18�� ����1:39:28
 */
public class AdListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAttendanceDate;  	//��������
	private String FType;				//����
	private String FAmCheckInTime;		//����ǩ��ʱ��
	private String FPmCheckOutTime;		//����ǩ��ʱ��
	private String FAmResult;			//����ǩ����
	private String FPmResult;			//����ǩ�����
	public String getFAttendanceDate() {
		return FAttendanceDate;
	}
	public void setFAttendanceDate(String fAttendanceDate) {
		FAttendanceDate = fAttendanceDate;
	}
	public String getFType() {
		return FType;
	}
	public void setFType(String fType) {
		FType = fType;
	}
	public String getFAmCheckInTime() {
		return FAmCheckInTime;
	}
	public void setFAmCheckInTime(String fAmCheckInTime) {
		FAmCheckInTime = fAmCheckInTime;
	}
	public String getFPmCheckOutTime() {
		return FPmCheckOutTime;
	}
	public void setFPmCheckOutTime(String fPmCheckOutTime) {
		FPmCheckOutTime = fPmCheckOutTime;
	}
	public String getFAmResult() {
		return FAmResult;
	}
	public void setFAmResult(String fAmResult) {
		FAmResult = fAmResult;
	}
	public String getFPmResult() {
		return FPmResult;
	}
	public void setFPmResult(String fPmResult) {
		FPmResult = fPmResult;
	}

}
