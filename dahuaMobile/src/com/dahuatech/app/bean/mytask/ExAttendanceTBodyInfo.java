package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExAttendanceTBodyInfo
 * @Description �쳣���ڵ������뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:40:21
 */
public class ExAttendanceTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FDate;				//�Ӱ�����
	private String FStartTime;			//����ʱ��
	private String FOldStartResult;		//������
	private String FChangeStartTime;	//�����ǩ��ʱ��
	private String FStartResult;		//�����ǩ����
	private String FEndTime;			//����ʱ��
	private String FOldEndResult;		//������
	private String FChangeEndTime;		//�����ǩ��ʱ��
	private String FEndResult;			//�����ǩ����
	private String FReason;				//��ǩ����
	
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

	public String getFOldStartResult() {
		return FOldStartResult;
	}

	public void setFOldStartResult(String fOldStartResult) {
		FOldStartResult = fOldStartResult;
	}

	public String getFChangeStartTime() {
		return FChangeStartTime;
	}

	public void setFChangeStartTime(String fChangeStartTime) {
		FChangeStartTime = fChangeStartTime;
	}

	public String getFStartResult() {
		return FStartResult;
	}

	public void setFStartResult(String fStartResult) {
		FStartResult = fStartResult;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFOldEndResult() {
		return FOldEndResult;
	}

	public void setFOldEndResult(String fOldEndResult) {
		FOldEndResult = fOldEndResult;
	}

	public String getFChangeEndTime() {
		return FChangeEndTime;
	}

	public void setFChangeEndTime(String fChangeEndTime) {
		FChangeEndTime = fChangeEndTime;
	}

	public String getFEndResult() {
		return FEndResult;
	}

	public void setFEndResult(String fEndResult) {
		FEndResult = fEndResult;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
