package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyResumeTHeaderInfo
 * @Description �������뵥�ݱ�ͷʵ��
 * @author 21291
 * @date 2015��1��12�� ����10:23:05
 */
public class ApplyResumeTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//���ݱ��
	private String FApplyName;			//������
	private String FApplyDate;			//����ʱ��
	private String FApplyDept;			//��������
	private String FSubEntrys;			//�Ӽ�����
	
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

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ApplyResumeTHeaderInfo instance = new ApplyResumeTHeaderInfo();  
    }  
	private ApplyResumeTHeaderInfo() {}
	public static ApplyResumeTHeaderInfo getApplyResumeTHeaderInfo() {
		return singletonHolder.instance;
	}
}
