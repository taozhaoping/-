package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeTakeOutTHeaderInfo
 * @Description ӡ�������ͷ����ʵ��
 * @author 21291
 * @date 2014��10��11�� ����9:21:26
 */
public class FeTakeOutTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDept;			//��������
	private String FApplyDate;			//����ʱ��
	private String FTel;				//�����˵绰
	private String FeName;   			//�������ӡ������
	private String FeCode;				//ӡ������
	private String FStartTime;			//��ʼʱ��
	private String FEndTime;			//����ʱ��
	private String FeCarry;				//ӡ��Я����
	private String FeCarryTel;			//Я���˵绰
	private String FDestination;		//Я��Ŀ�ĵ�
	
	private String FProjectName;		//��Ŀ/��������
	private String FSealData;			//����ӡ����
	private String FeUseCarry;			//ӡ��Я����
	private String FeUseDestination;    //Я��Ŀ�ĵ�
	
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
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFeCode() {
		return FeCode;
	}
	public void setFeCode(String feCode) {
		FeCode = feCode;
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
	public String getFeCarry() {
		return FeCarry;
	}
	public void setFeCarry(String feCarry) {
		FeCarry = feCarry;
	}
	public String getFeCarryTel() {
		return FeCarryTel;
	}
	public void setFeCarryTel(String feCarryTel) {
		FeCarryTel = feCarryTel;
	}
	public String getFDestination() {
		return FDestination;
	}
	public void setFDestination(String fDestination) {
		FDestination = fDestination;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFSealData() {
		return FSealData;
	}
	public void setFSealData(String fSealData) {
		FSealData = fSealData;
	}
	public String getFeUseCarry() {
		return FeUseCarry;
	}
	public void setFeUseCarry(String feUseCarry) {
		FeUseCarry = feUseCarry;
	}
	public String getFeUseDestination() {
		return FeUseDestination;
	}
	public void setFeUseDestination(String feUseDestination) {
		FeUseDestination = feUseDestination;
	}
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static FeTakeOutTHeaderInfo instance = new FeTakeOutTHeaderInfo();  
    }
	private FeTakeOutTHeaderInfo() {}
	public static FeTakeOutTHeaderInfo getFeTakeOutTHeaderInfo() {
		return singletonHolder.instance;
	}
}
