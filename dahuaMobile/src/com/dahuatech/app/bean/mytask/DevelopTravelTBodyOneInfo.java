package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTBodyInfo
 * @Description  �з�������ǲ����1����ʵ�� 
 * @author 21291
 * @date 2014��8��12�� ����10:27:12
 */

public class DevelopTravelTBodyOneInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FSchedule; 		//�ճ̰���
	private String FStartTime; 		//��ʼʱ��
	private String FEndTime; 		//����ʱ��
	private String FNote; 			//��ע
	
	public String getFSchedule() {
		return FSchedule;
	}
	public void setFSchedule(String fSchedule) {
		FSchedule = fSchedule;
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
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
