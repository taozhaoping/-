package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName EmailOpenTBodyInfo
 * @Description ���俪ͨ���嵥��ʵ��
 * @author 21291
 * @date 2014��8��18�� ����10:44:29
 */
public class EmailOpenTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FEmailAddress; 		//�����ַ
	private String FStartTime; 			//��ʼ����
	private String FApplyDeadLine; 	    //����Ȩ������
	
	public String getFEmailAddress() {
		return FEmailAddress;
	}
	public void setFEmailAddress(String fEmailAddress) {
		FEmailAddress = fEmailAddress;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFApplyDeadLine() {
		return FApplyDeadLine;
	}
	public void setFApplyDeadLine(String fApplyDeadLine) {
		FApplyDeadLine = fApplyDeadLine;
	}
}
