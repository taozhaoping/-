package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTHeaderInfo
 * @Description �ļ���������ͷ����ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:21:15
 */
public class DocumentApproveTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;			//����ʱ��
	private String FApplyTel;			//�����˵绰
	private String FPendingApp;   		//�������ļ�������汾
	private String FDocumentType;   	//�ļ�����
	private String FDocumentStatus;   	//�ļ�״̬
	private String FReason;   			//ԭ��
	private String FDocumentPost;   	//�ļ��漰��λ
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
	public String getFApplyTel() {
		return FApplyTel;
	}
	public void setFApplyTel(String fApplyTel) {
		FApplyTel = fApplyTel;
	}
	public String getFPendingApp() {
		return FPendingApp;
	}
	public void setFPendingApp(String fPendingApp) {
		FPendingApp = fPendingApp;
	}
	public String getFDocumentType() {
		return FDocumentType;
	}
	public void setFDocumentType(String fDocumentType) {
		FDocumentType = fDocumentType;
	}
	public String getFDocumentStatus() {
		return FDocumentStatus;
	}
	public void setFDocumentStatus(String fDocumentStatus) {
		FDocumentStatus = fDocumentStatus;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFDocumentPost() {
		return FDocumentPost;
	}
	public void setFDocumentPost(String fDocumentPost) {
		FDocumentPost = fDocumentPost;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DocumentApproveTHeaderInfo instance = new DocumentApproveTHeaderInfo();  
    }
	private DocumentApproveTHeaderInfo() {}
	public static DocumentApproveTHeaderInfo getDocumentApproveTHeaderInfo() {
		return singletonHolder.instance;
	}
}
