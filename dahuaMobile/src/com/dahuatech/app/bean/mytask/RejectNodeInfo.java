package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName NodeInfo
 * @Description ���ؽڵ�ʵ����
 * @author 21291
 * @date 2014��4��29�� ����10:04:58
 */
public class RejectNodeInfo extends Base  {
	private static final long serialVersionUID = 1L;
	
	private String FNodeKey;  //���ؽڵ�����
	private String FNodeValue; //���ؽڵ�ֵ
	
	public String getFNodeKey() {
		return FNodeKey;
	}

	public void setFNodeKey(String fNodeKey) {
		FNodeKey = fNodeKey;
	}

	public String getFNodeValue() {
		return FNodeValue;
	}

	public void setFNodeValue(String fNodeValue) {
		FNodeValue = fNodeValue;
	}

	public RejectNodeInfo() {
		super();
	}
	
	public RejectNodeInfo(String fNodeKey,String fNodeValue){
		super();
		this.FNodeKey=fNodeKey;
		this.FNodeValue=fNodeValue;
	}
}
