package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmJsonInfo
 * @Description �з���ʱȷ���б���JSONʵ����
 * @author 21291
 * @date 2014��11��5�� ����2:56:28
 */
public class DHConfirmJsonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FProjectCode;      //��Ŀ����
	public String FProjectName;      //��Ŀ����
    public String FSubChilds; 		 //�Ӽ�����
    
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

	public String getFSubChilds() {
		return FSubChilds;
	}

	public void setFSubChilds(String fSubChilds) {
		FSubChilds = fSubChilds;
	}	
}
