package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NetworkPermissionTHeaderInfo
 * @Description ����Ȩ�����뵥��ʵ��
 * @author 21291
 * @date 2014��7��9�� ����3:42:51
 */
public class NetworkPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyerDeptName;	//���벿��
	private String FApplyerType;		//��������
	private String FDate;				//����ʱ��
	private String FComboBox4;			//��Ϣ��ȫ
	private String FNote;				//��������
	private String FTelphone;			//��ϵ�绰
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
	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}
	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}
	public String getFApplyerType() {
		return FApplyerType;
	}
	public void setFApplyerType(String fApplyerType) {
		FApplyerType = fApplyerType;
	}
	public String getFDate() {
		return FDate;
	}
	public void setFDate(String fDate) {
		FDate = fDate;
	}
	public String getFComboBox4() {
		return FComboBox4;
	}
	public void setFComboBox4(String fComboBox4) {
		FComboBox4 = fComboBox4;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	public String getFTelphone() {
		return FTelphone;
	}
	public void setFTelphone(String fTelphone) {
		FTelphone = fTelphone;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static NetworkPermissionTHeaderInfo instance = new NetworkPermissionTHeaderInfo();  
    }
	private NetworkPermissionTHeaderInfo() {}
	public static NetworkPermissionTHeaderInfo getNetworkPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
