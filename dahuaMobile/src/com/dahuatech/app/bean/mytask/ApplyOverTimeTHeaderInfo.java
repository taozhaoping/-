package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyOverTimeTHeaderInfo
 * @Description �Ӱ����뵥�ݱ�ͷʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:26:07
 */
public class ApplyOverTimeTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//���ݱ��
	private String FApplyName;			//������
	private String FApplyDate;			//����ʱ��
	private String FOverTimeCount;		//����Ӱ���
	private String FDaysOffCount;		//���������
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

	public String getFOverTimeCount() {
		return FOverTimeCount;
	}

	public void setFOverTimeCount(String fOverTimeCount) {
		FOverTimeCount = fOverTimeCount;
	}

	public String getFDaysOffCount() {
		return FDaysOffCount;
	}

	public void setFDaysOffCount(String fDaysOffCount) {
		FDaysOffCount = fDaysOffCount;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ApplyOverTimeTHeaderInfo instance = new ApplyOverTimeTHeaderInfo();  
    }  
	private ApplyOverTimeTHeaderInfo() {}
	public static ApplyOverTimeTHeaderInfo getApplyOverTimeTHeaderInfo() {
		return singletonHolder.instance;
	}
}
