package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHProjectInfo
 * @Description �з���ʱ��Ŀʵ����
 * @author 21291
 * @date 2014��10��28�� ����5:34:53
 */
public class DHProjectInfo extends Base {
	private static final long serialVersionUID = 1L;

	public String FProjectCode;					//��Ŀ���
	public String FProjectName;					//��Ŀ����
	
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public DHProjectInfo(){}
}
