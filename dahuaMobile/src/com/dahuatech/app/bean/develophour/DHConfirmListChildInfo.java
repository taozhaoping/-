package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListChildInfo
 * @Description �з���ʱȷ���б�����������Ӽ���
 * @author 21291
 * @date 2014��11��5�� ����2:53:47
 */
public class DHConfirmListChildInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FWeekDay;       		//��������
	private String FHours;				//��ʱ
	
	public String getFWeekDay() {
		return FWeekDay;
	}
	public void setFWeekDay(String fWeekDay) {
		FWeekDay = fWeekDay;
	}
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	} 
}
