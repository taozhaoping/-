package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TrainComputerTBodyInfo
 * @Description ��ѵ���㻯�������뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��8��21�� ����9:29:11
 */
public class TrainComputerTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FApplyDesc;   	//����˵��
	private String FStartTime;		//��ʼʱ��
	private String FEndTime;	    //����ʱ��
	private String FNote;	    	//��ע
	
	public String getFApplyDesc() {
		return FApplyDesc;
	}
	public void setFApplyDesc(String fApplyDesc) {
		FApplyDesc = fApplyDesc;
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
