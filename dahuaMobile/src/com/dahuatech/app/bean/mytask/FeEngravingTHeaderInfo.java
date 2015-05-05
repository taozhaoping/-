package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeEngravingTHeaderInfo
 * @Description ӡ�����Ʊ�ͷ����ʵ��
 * @author 21291
 * @date 2014��10��10�� ����5:26:44
 */
public class FeEngravingTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDept;			//��������
	private String FApplyDate;			//����ʱ��
	private String FTel;				//��ϵ�绰
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
        private static FeEngravingTHeaderInfo instance = new FeEngravingTHeaderInfo();  
    }
	private FeEngravingTHeaderInfo() {}
	public static FeEngravingTHeaderInfo getFeEngravingTHeaderInfo() {
		return singletonHolder.instance;
	}
}
