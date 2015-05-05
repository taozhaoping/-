package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeUpdateTHeaderInfo
 * @Description ӡ��������ͷ����ʵ��
 * @author 21291
 * @date 2014��10��11�� ����10:47:13
 */
public class FeUpdateTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDept;			//��������
	private String FApplyDate;			//����ʱ��
	private String FTel;				//��ϵ�绰
	private String FDestroy;   			//������
	private String FDestroyWitness;   	//���ټ�֤��
	private String FAmount;   			//��������
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
	public String getFDestroy() {
		return FDestroy;
	}
	public void setFDestroy(String fDestroy) {
		FDestroy = fDestroy;
	}
	public String getFDestroyWitness() {
		return FDestroyWitness;
	}
	public void setFDestroyWitness(String fDestroyWitness) {
		FDestroyWitness = fDestroyWitness;
	}
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static FeUpdateTHeaderInfo instance = new FeUpdateTHeaderInfo();  
    }
	private FeUpdateTHeaderInfo() {}
	public static FeUpdateTHeaderInfo getFeUpdateTHeaderInfo() {
		return singletonHolder.instance;
	}
}
