package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppResultInfo
 * @Description �ڵ�ѡȡ�Ľ��ʵ����
 * @author 21291
 * @date 2014��11��12�� ����1:53:01
 */
public class LowerNodeAppResultInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int FIsMust;					//��ǰ�ڵ������Ƿ����Ҫ��   0-�������ߣ�1-�������Ҫ��
	private String FNodeId;					//��ǰ�ڵ�ID
	private String FNodeName;				//��ǰ�ڵ�����
	private String FSelectResult;			//ÿ���ڵ�ѡȡ�Ľ��ֵ
	private String FShowResult;				//ÿ���ڵ���ʾ���ֵ
	
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
	public String getFSelectResult() {
		return FSelectResult;
	}
	public void setFSelectResult(String fSelectResult) {
		FSelectResult = fSelectResult;
	}
	public LowerNodeAppResultInfo() {
		this.FSelectResult="";
	}
	public String getFShowResult() {
		return FShowResult;
	}
	public void setFShowResult(String fShowResult) {
		FShowResult = fShowResult;
	}
	
}
