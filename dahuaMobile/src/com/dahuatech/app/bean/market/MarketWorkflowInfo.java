package com.dahuatech.app.bean.market;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketWorkflowInfo
 * @Description ���۹�����ʵ��
 * @author 21291
 * @date 2015��1��26�� ����2:24:42
 */
public class MarketWorkflowInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FTaskId;       //T_WorkflowTaskers��������
	private String FID;			  //T_WorkflowDetails��������
	private String FStepFlagName; //��ת�ڵ�
	private String FItemName;     //������
	private String FShortTel;      //�̺�
	private String FComment;      //�������
	private String FStatusResult; //������
	private String FUpdateTime;   //����ʱ��
	public String getFTaskId() {
		return FTaskId;
	}
	public void setFTaskId(String fTaskId) {
		FTaskId = fTaskId;
	}
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public String getFStepFlagName() {
		return FStepFlagName;
	}
	public void setFStepFlagName(String fStepFlagName) {
		FStepFlagName = fStepFlagName;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	public String getFShortTel() {
		return FShortTel;
	}
	public void setFShortTel(String fShortTel) {
		FShortTel = fShortTel;
	}
	public String getFComment() {
		return FComment;
	}
	public void setFComment(String fComment) {
		FComment = fComment;
	}
	public String getFStatusResult() {
		return FStatusResult;
	}
	public void setFStatusResult(String fStatusResult) {
		FStatusResult = fStatusResult;
	}
	public String getFUpdateTime() {
		if(FUpdateTime==null || FUpdateTime.length()<=0)
			return "";
		else 
		{
			int index=FUpdateTime.indexOf('.');
			if(index > 0)
				return FUpdateTime.substring(0,index).replace("T"," ");	
			else 
				return FUpdateTime.replace("T"," ");	
		}	    
	}
	public void setFUpdateTime(String fUpdateTime) {
		FUpdateTime = fUpdateTime;
	}
}
