package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTestNetworkTBodyInfo
 * @Description �з���Ŀ��������Ȩ�ޱ��嵥��ʵ��
 * @author 21291
 * @date 2014��7��15�� ����4:35:41
 */
public class DevelopTestNetworkTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyForIp;     //����Ȩ���豸IP��ַ
	private String FPurposeIp;		//Ŀ��IP��ַ/����
	private String FStartTime;		//��ʼ����
	private String FEndTime;		//��������
	
	public String getFApplyForIp() {
		return FApplyForIp;
	}
	
	public void setFApplyForIp(String fApplyForIp) {
		FApplyForIp = fApplyForIp;
	}
	
	public String getFPurposeIp() {
		return FPurposeIp;
	}
	
	public void setFPurposeIp(String fPurposeIp) {
		FPurposeIp = fPurposeIp;
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
