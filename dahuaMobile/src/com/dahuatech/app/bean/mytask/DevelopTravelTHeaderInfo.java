package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DevelopTravelTHeaderInfo
 * @Description �з�������ǲ��ͷ����ʵ��
 * @author 21291
 * @date 2014��8��15�� ����9:45:38
 */
public class DevelopTravelTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//����ʱ��
	private String FAssumeCost;			//���óе�������
	private String FAssumeDept;			//���óе��˲���
	private String FTravelAim;			//����Ŀ��
	private String FTravelAddress;		//����ص�
	private String FTravelStartTime;	//���ʼʱ��
	private String FTravelEndTime;		//�������ʱ��
	private String FProjectName;		//��Ŀ����
	private String FProjectCode;		//��Ŀ���
	private String FTravelReason;		//��������
	private String FTravelWay;			//���ʽ
	private String FPublicNoteBook;	    //�����ʼǱ�����
	private String FSubEntrysOne;		//�Ӽ�����
	private String FSubEntrysTwo;		//�Ӽ�����
	
	public int getFID() {
		return FID;
	}
	public void setFID(int fID) {
		FID = fID;
	}
	public String getFBillNo() {
		return FBillNo;
	}
	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}
	public String getFApplyName() {
		return FApplyName;
	}
	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFAssumeCost() {
		return FAssumeCost;
	}
	public void setFAssumeCost(String fAssumeCost) {
		FAssumeCost = fAssumeCost;
	}
	public String getFAssumeDept() {
		return FAssumeDept;
	}
	public void setFAssumeDept(String fAssumeDept) {
		FAssumeDept = fAssumeDept;
	}
	public String getFTravelAim() {
		return FTravelAim;
	}
	public void setFTravelAim(String fTravelAim) {
		FTravelAim = fTravelAim;
	}
	public String getFTravelAddress() {
		return FTravelAddress;
	}
	public void setFTravelAddress(String fTravelAddress) {
		FTravelAddress = fTravelAddress;
	}
	public String getFTravelStartTime() {
		return FTravelStartTime;
	}
	public void setFTravelStartTime(String fTravelStartTime) {
		FTravelStartTime = fTravelStartTime;
	}
	public String getFTravelEndTime() {
		return FTravelEndTime;
	}
	public void setFTravelEndTime(String fTravelEndTime) {
		FTravelEndTime = fTravelEndTime;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFTravelReason() {
		return FTravelReason;
	}
	public void setFTravelReason(String fTravelReason) {
		FTravelReason = fTravelReason;
	}
	public String getFTravelWay() {
		return FTravelWay;
	}
	public void setFTravelWay(String fTravelWay) {
		FTravelWay = fTravelWay;
	}
	public String getFPublicNoteBook() {
		return FPublicNoteBook;
	}
	public void setFPublicNoteBook(String fPublicNoteBook) {
		FPublicNoteBook = fPublicNoteBook;
	}
	public String getFSubEntrysOne() {
		return FSubEntrysOne;
	}
	public void setFSubEntrysOne(String fSubEntrysOne) {
		FSubEntrysOne = fSubEntrysOne;
	}
	public String getFSubEntrysTwo() {
		return FSubEntrysTwo;
	}
	public void setFSubEntrysTwo(String fSubEntrysTwo) {
		FSubEntrysTwo = fSubEntrysTwo;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DevelopTravelTHeaderInfo instance = new DevelopTravelTHeaderInfo();  
    }
	private DevelopTravelTHeaderInfo() {}
	public static DevelopTravelTHeaderInfo getDevelopTravelTHeaderInfo() {
		return singletonHolder.instance;
	}
}
