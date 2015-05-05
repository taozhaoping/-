package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowConsumableTHeaderInfo
 * @Description ��ֵ�׺����ϴ��뵥�ݱ�ͷʵ��
 * @author 21291
 * @date 2014��8��19�� ����4:35:25
 */
public class LowConsumableTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//��������
	private String FApplyDept;			//��������
	private String FTel;				//��ϵ�绰
	private String FBillType;			//��������
	private String FApplyCause;			//��������
	private String FSubEntrys;			//�Ӽ�����
	
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
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFBillType() {
		return FBillType;
	}
	public void setFBillType(String fBillType) {
		FBillType = fBillType;
	}
	public String getFApplyCause() {
		return FApplyCause;
	}
	public void setFApplyCause(String fApplyCause) {
		FApplyCause = fApplyCause;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static LowConsumableTHeaderInfo instance = new LowConsumableTHeaderInfo();  
    }
	private LowConsumableTHeaderInfo() {}
	public static LowConsumableTHeaderInfo getLowConsumableTHeaderInfo() {
		return singletonHolder.instance;
	}
}
