package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHWeekInfo
 * @Description �з���ʱ��ʵ����
 * @author 21291
 * @date 2014��10��24�� ����9:27:29
 */
public class DHWeekInfo extends Base {
	private static final long serialVersionUID = 1L;

	public int FYear;			//���
	public int FIndex;			//�ܴ�
	public String FStartDate;	//��ʼʱ��
	public String FEndDate;		//����ʱ��
	
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}
	public int getFIndex() {
		return FIndex;
	}
	public void setFIndex(int fIndex) {
		FIndex = fIndex;
	}
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFEndDate() {
		return FEndDate;
	}
	public void setFEndDate(String fEndDate) {
		FEndDate = fEndDate;
	}
	
}
