package com.dahuatech.app.bean.expense;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpandableInfo
 * @Description �ҵ���ˮ��չ��ʵ����Ϣ
 * @author 21291
 * @date 2014��8��25�� ����5:30:09
 */
public class ExpandableInfo extends Base {

	private static final long serialVersionUID = 1L;
	private String FGroupTitle;		    //Ⱥ�����
	private String FSubEntrys;			//�Ӽ�����
	
	public String getFGroupTitle() {
		return FGroupTitle;
	}
	public void setFGroupTitle(String fGroupTitle) {
		FGroupTitle = fGroupTitle;
	}
	
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
}
