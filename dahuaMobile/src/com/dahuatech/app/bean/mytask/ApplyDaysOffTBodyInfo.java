package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyDaysOffTBodyInfo
 * @Description ��ͨ���ŵ������뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:47:57
 */
public class ApplyDaysOffTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDate;			//����ʱ��
	private String FStartTime;		//����ʱ��
	private String FEndTime;		//����ʱ��
	private String FHours;			//����ʱ��
	private String FReason;			//����ԭ��
	
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

	public String getFHours() {
		return FHours;
	}

	public void setFHours(String fHours) {
		FHours = fHours;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
