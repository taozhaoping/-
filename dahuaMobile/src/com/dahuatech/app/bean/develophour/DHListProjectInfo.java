package com.dahuatech.app.bean.develophour;

import java.util.List;

/**
 * @ClassName DHListProjectInfo
 * @Description �з���ʱ�б��������Ŀʵ����
 * @author 21291
 * @date 2014��10��24�� ����5:29:51
 */
public class DHListProjectInfo extends DHProjectInfo {
	private static final long serialVersionUID = 1L;

	private String FHours;		    				//����Сʱ
	private List<DHListTypeInfo> dListTypeInfo;		//�Ӽ�����
	
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public List<DHListTypeInfo> getdListTypeInfo() {
		return dListTypeInfo;
	}
	public void setdListTypeInfo(List<DHListTypeInfo> dListTypeInfo) {
		this.dListTypeInfo = dListTypeInfo;
	}
}
