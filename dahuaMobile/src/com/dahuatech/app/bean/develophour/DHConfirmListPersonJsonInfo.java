package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListPersonJsonInfo
 * @Description �з���ʱȷ���б������ʵ��JSON��
 * @author 21291
 * @date 2014��11��5�� ����2:45:39
 */
public class DHConfirmListPersonJsonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FTypeId; 			//����ID
	private String FTypeName; 			//��������
	private String FHours;				//��ʱ
	private String FSubChilds; 			//�Ӽ�����

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

	public String getFHours() {
		return FHours;
	}

	public void setFHours(String fHours) {
		FHours = fHours;
	}

	public String getFSubChilds() {
		return FSubChilds;
	}

	public void setFSubChilds(String fSubChilds) {
		FSubChilds = fSubChilds;
	}	
}
