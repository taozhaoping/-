package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName LowerNodeAppInfo
 * @Description �¼��ڵ�����״̬ʵ��
 * @author 21291
 * @date 2014��11��10�� ����4:06:36
 */
public class LowerNodeAppInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FNodeId;			//��ǰ�ڵ�ID
	private String FNodeName;		//��ǰ�ڵ�����
	private int FIsMust;			//��ǰ�ڵ������Ƿ����Ҫ��   0-�������ߣ�1-�������Ҫ��
	
	//��ǰ�ڵ�״̬  	
	// 0-�������������û���¼��ڵ����������ť��Ҳ����û���������
	// 1-���������ˣ�ͨ������������ȡ��Ա��Ϣ  
	// 2-���������ˣ��Ӹ����������̽ڵ����õ���������Ϣ����ȡ
	// 3-���������ˣ��Ȳ�ͨ����������ȡ��Ҳ��ͨ������������Ա����ȡ���Զ�����������
	private int FNodeStatus;		
	private String FSubEntrys;			//�Ӽ�����  ��Ե�ǰ�ڵ�״ֵ̬Ϊ2��������������Ϊ��
	
	public String getFNodeId() {
		return FNodeId;
	}
	public void setFNodeId(String fNodeId) {
		FNodeId = fNodeId;
	}
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public int getFIsMust() {
		return FIsMust;
	}
	public void setFIsMust(int fIsMust) {
		FIsMust = fIsMust;
	}
	public int getFNodeStatus() {
		return FNodeStatus;
	}
	public void setFNodeStatus(int fNodeStatus) {
		FNodeStatus = fNodeStatus;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}

	public LowerNodeAppInfo() {
		this.FSubEntrys="";
	}
}
