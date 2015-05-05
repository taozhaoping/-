package com.dahuatech.app.bean.develophour;

import java.util.ArrayList;
import java.util.List;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListRootInfo
 * @Description �з���ʱȷ���б���������������
 * @author 21291
 * @date 2014��11��5�� ����2:49:47
 */
public class DHConfirmListRootInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FTypeId; 							//����ID
	private String FTypeName;  							//��������
	private String FHours;								//��ʱ	
	private List<DHConfirmListChildInfo> FChildren;		//�Ӽ�����
		
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
	public List<DHConfirmListChildInfo> getFChildren() {
		return FChildren;
	}
	public void setFChildren(List<DHConfirmListChildInfo> fChildren) {
		FChildren = fChildren;
	}
	
	public DHConfirmListRootInfo(){
		FChildren=new ArrayList<DHConfirmListChildInfo>();
	}
}
