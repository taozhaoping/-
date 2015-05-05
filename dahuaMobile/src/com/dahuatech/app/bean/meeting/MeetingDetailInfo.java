package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingDetailInfo
 * @Description ��������ʵ����
 * @author 21291
 * @date 2014��9��11�� ����9:04:41
 */
public class MeetingDetailInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FId;  				//��������
	private String FApplyNumber;  		//������Ա����
	private String FApplyName;  		//����������
	private String FApplyDept;  		//���벿��
	private String FMeetingName;		//��������
	private String FMeetingDate;		//��������
	private String FMeetingStart;		//������ʼʱ��
	private String FMeetingEnd;			//�������ʱ��
	private String FMeetingMasterId;	//����������ID
	private String FMeetingMasterName;	//��������������
	private String FMeetingRoom;		//����ص�
	private String FMeetingRoomId;		//����ص�ID
	private String FMeetingRoomIp;		//����ص�IP
	private String FSubEntrys;			//�Ӽ����� (������Ա����)

	public String getFId() {
		return FId;
	}

	public void setFId(String fId) {
		FId = fId;
	}

	public String getFApplyNumber() {
		return FApplyNumber;
	}

	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDept() {
		return FApplyDept;
	}

	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
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
	
	public String getFMeetingMasterId() {
		return FMeetingMasterId;
	}

	public void setFMeetingMasterId(String fMeetingMasterId) {
		FMeetingMasterId = fMeetingMasterId;
	}

	public String getFMeetingMasterName() {
		return FMeetingMasterName;
	}

	public void setFMeetingMasterName(String fMeetingMasterName) {
		FMeetingMasterName = fMeetingMasterName;
	}
	
	public String getFMeetingRoom() {
		return FMeetingRoom;
	}

	public void setFMeetingRoom(String fMeetingRoom) {
		FMeetingRoom = fMeetingRoom;
	}

	public String getFMeetingRoomId() {
		return FMeetingRoomId;
	}

	public void setFMeetingRoomId(String fMeetingRoomId) {
		FMeetingRoomId = fMeetingRoomId;
	}

	public String getFMeetingRoomIp() {
		return FMeetingRoomIp;
	}

	public void setFMeetingRoomIp(String fMeetingRoomIp) {
		FMeetingRoomIp = fMeetingRoomIp;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MeetingDetailInfo instance = new MeetingDetailInfo();  
    }
	public MeetingDetailInfo() {}
	public static MeetingDetailInfo getMeetingDetailInfo() {
		return singletonHolder.instance;
	}
	
	// ��ʵ�����ת����json�Ա����������д���
	public static String ConvertToJson(MeetingDetailInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FId", item.FId);
			jsonObject.put("FApplyNumber", item.FApplyNumber);
			jsonObject.put("FApplyName", item.FApplyName);
			jsonObject.put("FApplyDept", item.FApplyDept);
			jsonObject.put("FMeetingName", item.FMeetingName);
			jsonObject.put("FMeetingDate", item.FMeetingDate);
			jsonObject.put("FMeetingStart", item.FMeetingStart);
			jsonObject.put("FMeetingEnd", item.FMeetingEnd);	
			jsonObject.put("FMeetingMasterId", item.FMeetingMasterId);
			jsonObject.put("FMeetingMasterName", item.FMeetingMasterName);
			jsonObject.put("FMeetingRoom", item.FMeetingRoom);
			jsonObject.put("FMeetingRoomId", item.FMeetingRoomId);
			jsonObject.put("FMeetingRoomIp", item.FMeetingRoomIp);
			jsonObject.put("FSubEntrys", item.FSubEntrys);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
