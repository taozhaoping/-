package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Entity;

/**
 * @ClassName TaskInfo
 * @Description ����ʵ����
 * @author 21291
 * @date 2014��4��22�� ����5:26:45
 */
public class TaskInfo extends Entity {

	private static final long serialVersionUID = 1L;
	
	private int FMenuID;	  //�˵�����
	private String FBillID;   //��������
	private String FTitle;	  //���ݱ���
	private String FSender;	  //������
	private String FSendTime; //����ʱ��
	
	private int FClassTypeID;   	//��������ID
	private String FClassTypeName;  //������������
	
	private int FSystemType;		//ϵͳID
	private int FTotalCount;		//�ܵļ�¼��

	public int getFMenuID() {
		return FMenuID;
	}

	public void setFMenuID(int fMenuID) {
		FMenuID = fMenuID;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	public String getFTitle() {
		return FTitle;
	}

	public void setFTitle(String fTitle) {
		FTitle = fTitle;
	}

	public String getFSender() {
		return FSender;
	}

	public void setFSender(String fSender) {
		FSender = fSender;
	}

	public String getFSendTime() {
		if(FSendTime==null || FSendTime.length()<=0)
			return "";
		else 
		{
			int index=FSendTime.indexOf('.');
			if(index > 0)
				return FSendTime.substring(0,index).replace("T"," ");	
			else 
				return FSendTime.replace("T"," ");	
		}	 
	}

	public void setFSendTime(String fSendTime) {
		FSendTime = fSendTime;
	}

	public int getFClassTypeID() {
		return FClassTypeID;
	}

	public void setFClassTypeID(int fClassTypeID) {
		FClassTypeID = fClassTypeID;
	}

	public String getFClassTypeName() {
		return FClassTypeName;
	}

	public void setFClassTypeName(String fClassTypeName) {
		FClassTypeName = fClassTypeName;
	}
	
	public int getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(int fSystemType) {
		FSystemType = fSystemType;
	}
	
	public int getFTotalCount() {
		return FTotalCount;
	}

	public void setFTotalCount(int fTotalCount) {
		FTotalCount = fTotalCount;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static TaskInfo instance = new TaskInfo();  
    }
	public TaskInfo() {}
	public static TaskInfo getTaskInfo() {
		return singletonHolder.instance;
	}
}
