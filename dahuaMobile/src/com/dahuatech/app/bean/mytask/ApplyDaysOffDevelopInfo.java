package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyDaysOffDevelopInfo
 * @Description �з����ŵ������뵥��ʵ��
 * @author 21291
 * @date 2014��7��23�� ����2:53:29
 */
public class ApplyDaysOffDevelopInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FBillNo;			//���ݱ��
	private String FApplyName;		//������
	private String FApplyDate;		//����ʱ��
	private String FApplyDept;		//���벿��
	private String FTypeName;		//��������
	private String FSumDays;		//��������
	private String FStartTime;		//����ʱ��
	private String FEndTime;		//����ʱ��
	private String FReason;			//����ԭ��
	
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

	public String getFTypeName() {
		return FTypeName;
	}

	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}

	public String getFSumDays() {
		return FSumDays;
	}

	public void setFSumDays(String fSumDays) {
		FSumDays = fSumDays;
	}

	public String getFStartTime() {
		return FStartTime;
	}

	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}

	//˽�й��캯������ֹ��ʵ���� 
	private ApplyDaysOffDevelopInfo() {}
	//�ڲ���ʵ�ֵ���ģʽ  �ӳټ���,�̰߳�ȫ��java��class����ʱ����ģ�,�����ڴ濪��
	private static class singletonHolder {  
        private static ApplyDaysOffDevelopInfo instance = new ApplyDaysOffDevelopInfo();  
    }  
	public static ApplyDaysOffDevelopInfo getApplyDaysOffDevelopInfo() {
		return singletonHolder.instance;
	}
}
