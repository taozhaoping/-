package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyOverTimeTBodyInfo
 * @Description �Ӱ����뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:26:26
 */
public class ApplyOverTimeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDate;				//�Ӱ�����
	private String FStartTime;			//����
	private String FEndTime;			//����
	private String FUse;				//��;
	private String FReason;				//�Ӱ�ԭ��
	private String FAttenTime;			//���տ���
	private String FAttendance;			//�������
	
	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFStartTime() {
		return FStartTime;
	}

	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}

	public String getFAttenTime() {
		return FAttenTime;
	}

	public void setFAttenTime(String fAttenTime) {
		FAttenTime = fAttenTime;
	}

	public String getFAttendance() {
		return FAttendance;
	}

	public void setFAttendance(String fAttendance) {
		FAttendance = fAttendance;
	}
}
