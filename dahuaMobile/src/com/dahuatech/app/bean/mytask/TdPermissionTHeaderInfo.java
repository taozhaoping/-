package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionTHeaderInfo
 * @Description TDȨ�����뵥��ͷʵ��
 * @author 21291
 * @date 2014��9��22�� ����5:27:01
 */
public class TdPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//����������
	private String FApplyNumber;  		//�����˹���
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FTel;				//��ϵ�绰
	private String FPersonType;			//������Ա����
	private String FBeforeAmount;		//TD(����ǰ)�˺�����
	private String FAfterAmount;		//TD(������)�˺�����
	private String FBeforeSubEntrys;	//TD(����ǰ)�Ӽ�����
	private String FAfterSubEntrys;		//TD(������)�Ӽ�����
	
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
	public String getFApplyNumber() {
		return FApplyNumber;
	}
	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
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
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFPersonType() {
		return FPersonType;
	}
	public void setFPersonType(String fPersonType) {
		FPersonType = fPersonType;
	}
	public String getFBeforeAmount() {
		return FBeforeAmount;
	}
	public void setFBeforeAmount(String fBeforeAmount) {
		FBeforeAmount = fBeforeAmount;
	}
	public String getFAfterAmount() {
		return FAfterAmount;
	}
	public void setFAfterAmount(String fAfterAmount) {
		FAfterAmount = fAfterAmount;
	}
	public String getFBeforeSubEntrys() {
		return FBeforeSubEntrys;
	}
	public void setFBeforeSubEntrys(String fBeforeSubEntrys) {
		FBeforeSubEntrys = fBeforeSubEntrys;
	}
	public String getFAfterSubEntrys() {
		return FAfterSubEntrys;
	}
	public void setFAfterSubEntrys(String fAfterSubEntrys) {
		FAfterSubEntrys = fAfterSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static TdPermissionTHeaderInfo instance = new TdPermissionTHeaderInfo();  
    }
	private TdPermissionTHeaderInfo() {}
	public static TdPermissionTHeaderInfo getTdPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
