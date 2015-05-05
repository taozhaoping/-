package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyDaysOffTHeaderInfo
 * @Description ��ͨ���ŵ������뵥�ݱ�ͷʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:47:34
 */
public class ApplyDaysOffTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//���ݱ��
	private String FApplyName;			//������
	private String FApplyDate;			//����ʱ��
	private String FApplyDept;			//���벿��
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
        private static ApplyDaysOffTHeaderInfo instance = new ApplyDaysOffTHeaderInfo();  
    }  
	private ApplyDaysOffTHeaderInfo() {}
	public static ApplyDaysOffTHeaderInfo getApplyDaysOffTHeaderInfo() {
		return singletonHolder.instance;
	}
}
