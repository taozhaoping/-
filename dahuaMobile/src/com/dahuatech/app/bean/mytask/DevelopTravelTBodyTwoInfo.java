package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTravelTBodyTwoInfo
 * @Description  �з�������ǲ����2����ʵ�� 
 * @author 21291
 * @date 2014��8��15�� ����9:48:25
 */
public class DevelopTravelTBodyTwoInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FTravelName; 	//����������
	private String FTravelDept; 	//�����˲�������
	private String FDeptManager; 	//���ž���
	private String FStartTime; 		//ʵ�ʿ�ʼʱ��
	private String FEndTime; 		//ʵ�ʽ���ʱ��
	
	public String getFTravelName() {
		return FTravelName;
	}
	public void setFTravelName(String fTravelName) {
		FTravelName = fTravelName;
	}
	public String getFTravelDept() {
		return FTravelDept;
	}
	public void setFTravelDept(String fTravelDept) {
		FTravelDept = fTravelDept;
	}
	public String getFDeptManager() {
		return FDeptManager;
	}
	public void setFDeptManager(String fDeptManager) {
		FDeptManager = fDeptManager;
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
}
