package com.dahuatech.app.bean.meeting;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingListInfo
 * @Description �ҵĻ����б�ʵ����
 * @author 21291
 * @date 2014��9��11�� ����2:14:57
 */
public class MeetingListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FId;  			//������������
	private String FStatus;  		//����״̬ 0-���������� �����޸ĺ�ȡ�� 1-���˴��� ֻ�ܲ鿴
	private String FCreate;  		//������
	private String FMeetingName;	//��������
	private String FMeetingDate;	//��������
	private String FMeetingStart;	//������ʼʱ��
	private String FMeetingEnd;		//�������ʱ��
	private int FRecordCount;		//�ܵĻ����¼
	
	public String getFId() {
		return FId;
	}
	public void setFId(String fId) {
		FId = fId;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	public String getFCreate() {
		return FCreate;
	}
	public void setFCreate(String fCreate) {
		FCreate = fCreate;
	}
	public String getFMeetingName() {
		return FMeetingName;
	}
	public void setFMeetingName(String fMeetingName) {
		FMeetingName = fMeetingName;
	}
	public String getFMeetingDate() {
		return FMeetingDate;
	}
	public void setFMeetingDate(String fMeetingDate) {
		FMeetingDate = fMeetingDate;
	}
	public String getFMeetingStart() {
		return FMeetingStart;
	}
	public void setFMeetingStart(String fMeetingStart) {
		FMeetingStart = fMeetingStart;
	}
	public String getFMeetingEnd() {
		return FMeetingEnd;
	}
	public void setFMeetingEnd(String fMeetingEnd) {
		FMeetingEnd = fMeetingEnd;
	}
	public int getFRecordCount() {
		return FRecordCount;
	}
	public void setFRecordCount(int fRecordCount) {
		FRecordCount = fRecordCount;
	}
	
}
