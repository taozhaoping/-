package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MemRequreInfo
 * @Description MEM�������뵥��ʵ��
 * @author 21291
 * @date 2014��7��17�� ����2:36:41
 */
public class MemRequreInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;					//��������
	private String FBillNo;				//���ݱ��
	private String FApplyerName;		//����������
	private String FApplyerDeptName;	//���벿��
	private String FApplyDate;			//����ʱ��
	private String FVersion;			//�汾
	private String FValidDate;			//��Ч����
	private String FUpgradeReason;		//����ԭ��
	private String FOtherReason;		//����ԭ��
	private String FUpgradeNote;		//����˵��
	private String FSubject;			//����
	private String FMemReasonNote;		//MEM����ԭ��
	private String FMemProduct;			//MEM�漰��Ʒ�ͺ�/����
	private String FTechnology;			//ԭ�����ļ���
	private String FMemScope;			//MEM���ŷ�Χ
	private String FNote;				//MEM��������
	
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

	public String getFApplyerName() {
		return FApplyerName;
	}

	public void setFApplyerName(String fApplyerName) {
		FApplyerName = fApplyerName;
	}

	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}

	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFVersion() {
		return FVersion;
	}

	public void setFVersion(String fVersion) {
		FVersion = fVersion;
	}

	public String getFValidDate() {
		return FValidDate;
	}

	public void setFValidDate(String fValidDate) {
		FValidDate = fValidDate;
	}

	public String getFUpgradeReason() {
		return FUpgradeReason;
	}

	public void setFUpgradeReason(String fUpgradeReason) {
		FUpgradeReason = fUpgradeReason;
	}

	public String getFOtherReason() {
		return FOtherReason;
	}

	public void setFOtherReason(String fOtherReason) {
		FOtherReason = fOtherReason;
	}

	public String getFUpgradeNote() {
		return FUpgradeNote;
	}

	public void setFUpgradeNote(String fUpgradeNote) {
		FUpgradeNote = fUpgradeNote;
	}

	public String getFSubject() {
		return FSubject;
	}

	public void setFSubject(String fSubject) {
		FSubject = fSubject;
	}

	public String getFMemReasonNote() {
		return FMemReasonNote;
	}

	public void setFMemReasonNote(String fMemReasonNote) {
		FMemReasonNote = fMemReasonNote;
	}

	public String getFMemProduct() {
		return FMemProduct;
	}

	public void setFMemProduct(String fMemProduct) {
		FMemProduct = fMemProduct;
	}

	public String getFTechnology() {
		return FTechnology;
	}

	public void setFTechnology(String fTechnology) {
		FTechnology = fTechnology;
	}

	public String getFMemScope() {
		return FMemScope;
	}

	public void setFMemScope(String fMemScope) {
		FMemScope = fMemScope;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static MemRequreInfo instance = new MemRequreInfo();  
    }
	private MemRequreInfo() {}
	public static MemRequreInfo getMemRequreInfo() {
		return singletonHolder.instance;
	}
}
