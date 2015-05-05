package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyLeaveTBodyInfo
 * @Description ������뵥�ݱ���ʵ��
 * @author 21291
 * @date 2015��1��12�� ����9:34:22
 */
public class ApplyLeaveTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FHolidayType;		//��������
	private String FStartDate;			//��ʼ����
	private String FStartTime;			//��ʼʱ��
	private String FEndDate;			//��������
	private String FEndTime;			//����ʱ��
	private String FTimeLength;			//ʱ�䳤��
	private String FReason;				//ԭ��
	
	public String getFHolidayType() {
		return FHolidayType;
	}
	public void setFHolidayType(String fHolidayType) {
		FHolidayType = fHolidayType;
	}
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFEndDate() {
		return FEndDate;
	}
	public void setFEndDate(String fEndDate) {
		FEndDate = fEndDate;
	}
	public String getFEndTime() {
		return FEndTime;
	}
	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}
	public String getFTimeLength() {
		return FTimeLength;
	}
	public void setFTimeLength(String fTimeLength) {
		FTimeLength = fTimeLength;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
