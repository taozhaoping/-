package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyResumeTBodyInfo
 * @Description �������뵥�ݱ���ʵ��
 * @author 21291
 * @date 2015��1��12�� ����10:23:24
 */
public class ApplyResumeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FStartDate;			//��ʼ����
	private String FAmCheckTime;		//����ǩ��ʱ��
	private String FAmCheckResult;		//����ǩ����
	private String FAmResume;			//��������
	private String FPmCheckTime;		//����ǩ��ʱ��
	private String FPmCheckResult;		//����ǩ�����
	private String FPmResume;			//��������
	private String FReason;				//����ԭ��
	
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFAmCheckTime() {
		return FAmCheckTime;
	}
	public void setFAmCheckTime(String fAmCheckTime) {
		FAmCheckTime = fAmCheckTime;
	}
	public String getFAmCheckResult() {
		return FAmCheckResult;
	}
	public void setFAmCheckResult(String fAmCheckResult) {
		FAmCheckResult = fAmCheckResult;
	}
	public String getFAmResume() {
		return FAmResume;
	}
	public void setFAmResume(String fAmResume) {
		FAmResume = fAmResume;
	}
	public String getFPmCheckTime() {
		return FPmCheckTime;
	}
	public void setFPmCheckTime(String fPmCheckTime) {
		FPmCheckTime = fPmCheckTime;
	}
	public String getFPmCheckResult() {
		return FPmCheckResult;
	}
	public void setFPmCheckResult(String fPmCheckResult) {
		FPmCheckResult = fPmCheckResult;
	}
	public String getFPmResume() {
		return FPmResume;
	}
	public void setFPmResume(String fPmResume) {
		FPmResume = fPmResume;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
