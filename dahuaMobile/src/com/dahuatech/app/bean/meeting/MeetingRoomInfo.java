package com.dahuatech.app.bean.meeting;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName MeetingRoomInfo
 * @Description ������ʵ����
 * @author 21291
 * @date 2014��9��11�� ����9:20:46
 */
public class MeetingRoomInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FRoomId; 	//��������
	private String FRoomName; 	//����������
	private String FRoomIp; 	//������IP
	private String FType; 		//�����ҳ�ͻ״̬ 0 δ��ͻ  1�ǳ�ͻ
	private int FRecordCount; 	//�ܵĻ����Ҽ�¼

	public String getFRoomId() {
		return FRoomId;
	}
	
	public void setFRoomId(String fRoomId) {
		FRoomId = fRoomId;
	}

	public String getFRoomName() {
		return FRoomName;
	}

	public void setFRoomName(String fRoomName) {
		FRoomName = fRoomName;
	}

	public String getFRoomIp() {
		return FRoomIp;
	}

	public void setFRoomIp(String fRoomIp) {
		FRoomIp = fRoomIp;
	}
	
	public String getFType() {
		return FType;
	}

	public void setFType(String fType) {
		FType = fType;
	}

	public int getFRecordCount() {
		return FRecordCount;
	}

	public void setFRecordCount(int fRecordCount) {
		FRecordCount = fRecordCount;
	}
	
	//Ĭ�Ϲ��캯��
	public MeetingRoomInfo() {}
}
