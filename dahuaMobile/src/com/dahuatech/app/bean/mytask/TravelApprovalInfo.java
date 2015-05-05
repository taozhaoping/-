package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TravelApprovalInfo
 * @Description ������������ʵ��
 * @author 21291
 * @date 2014��8��21�� ����9:52:51
 */
public class TravelApprovalInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FClientName;			//�ͻ�����
	private String FStartTime;			//����ʱ��
	private String FBackTime;			//����ʱ��
	private String FTravelAddress;		//����ص�
	private String FTravelDays;			//��������
	private String FTravelCause;		//��������
	private String FArrangement;		//ʱ�䰲��
	private String FTravelReport;		//�����
	
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
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFClientName() {
		return FClientName;
	}
	public void setFClientName(String fClientName) {
		FClientName = fClientName;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFBackTime() {
		return FBackTime;
	}
	public void setFBackTime(String fBackTime) {
		FBackTime = fBackTime;
	}
	public String getFTravelAddress() {
		return FTravelAddress;
	}
	public void setFTravelAddress(String fTravelAddress) {
		FTravelAddress = fTravelAddress;
	}
	public String getFTravelDays() {
		return FTravelDays;
	}
	public void setFTravelDays(String fTravelDays) {
		FTravelDays = fTravelDays;
	}
	public String getFTravelCause() {
		return FTravelCause;
	}
	public void setFTravelCause(String fTravelCause) {
		FTravelCause = fTravelCause;
	}
	public String getFArrangement() {
		return FArrangement;
	}
	public void setFArrangement(String fArrangement) {
		FArrangement = fArrangement;
	}
	public String getFTravelReport() {
		return FTravelReport;
	}
	public void setFTravelReport(String fTravelReport) {
		FTravelReport = fTravelReport;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static TravelApprovalInfo instance = new TravelApprovalInfo();  
    }
	private TravelApprovalInfo() {}
	public static TravelApprovalInfo getTravelApprovalInfo() {
		return singletonHolder.instance;
	}
}
