package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHTypeInfo
 * @Description �з���ʱ��������ʵ��
 * @author 21291
 * @date 2014��10��28�� ����5:33:10
 */
public class DHTypeInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FTypeId;			//����Id
	public String FTypeName;		//��������
	
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}
	public String getFTypeName() {
		return FTypeName;
	}
	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}
}
