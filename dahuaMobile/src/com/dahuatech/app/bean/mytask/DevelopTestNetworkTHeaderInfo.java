package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTestNetworkInfo
 * @Description �з���Ŀ��������Ȩ�ޱ�ͷ����ʵ��
 * @author 21291
 * @date 2014��7��15�� ����2:44:40
 */
public class DevelopTestNetworkTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyerDeptName;	//���벿��
	private String FDate;				//����ʱ��
	private String FApplyerPermisson;	//����Ȩ��
	private String FTelphone;			//��ϵ�绰
	private String FPermissionRequre;   //��������
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

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFApplyerPermisson() {
		return FApplyerPermisson;
	}

	public void setFApplyerPermisson(String fApplyerPermisson) {
		FApplyerPermisson = fApplyerPermisson;
	}

	public String getFTelphone() {
		return FTelphone;
	}
	
	public void setFTelphone(String fTelphone) {
		FTelphone = fTelphone;
	}

	public String getFPermissionRequre() {
		return FPermissionRequre;
	}

	public void setFPermissionRequre(String fPermissionRequre) {
		FPermissionRequre = fPermissionRequre;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DevelopTestNetworkTHeaderInfo instance = new DevelopTestNetworkTHeaderInfo();  
    }
	private DevelopTestNetworkTHeaderInfo() {}
	public static DevelopTestNetworkTHeaderInfo getDevelopTestNetworkTHeaderInfo() {
		return singletonHolder.instance;
	}	
}
