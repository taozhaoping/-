package com.dahuatech.app.bean.develophour;

/**
 * @ClassName DHListProjectJsonInfo
 * @Description �з���ʱ�б��������Ŀʵ��Json��
 * @author 21291
 * @date 2014��10��24�� ����5:24:27
 */
public class DHListProjectJsonInfo extends DHProjectInfo {
	private static final long serialVersionUID = 1L;

	private String FHours;		    //����Сʱ
	private String FSubEntrys;		//�Ӽ�����
	
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
}
