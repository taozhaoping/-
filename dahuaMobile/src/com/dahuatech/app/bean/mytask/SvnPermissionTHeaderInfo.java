package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName SvnPermissionTHeaderInfo
 * @Description SVNȨ�ޱ�ͷ����ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:44:28
 */
public class SvnPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDept;			//��������
	private String FApplyDateStart;		//����ʱ�俪ʼ
	private String FApplyDateEnd;		//����ʱ�����
	private String FSvnShow;			//SVN�⸺���˲鿴
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
	public String getFApplyDateStart() {
		return FApplyDateStart;
	}
	public void setFApplyDateStart(String fApplyDateStart) {
		FApplyDateStart = fApplyDateStart;
	}
	public String getFApplyDateEnd() {
		return FApplyDateEnd;
	}
	public void setFApplyDateEnd(String fApplyDateEnd) {
		FApplyDateEnd = fApplyDateEnd;
	}
	public String getFSvnShow() {
		return FSvnShow;
	}
	public void setFSvnShow(String fSvnShow) {
		FSvnShow = fSvnShow;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static SvnPermissionTHeaderInfo instance = new SvnPermissionTHeaderInfo();  
    }
	private SvnPermissionTHeaderInfo() {}
	public static SvnPermissionTHeaderInfo getSvnPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
