package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DoorPermissionTBodyInfo
 * @Description �Ž�Ȩ�����뵥�ݱ���ʵ��
 * @author 21291
 * @date 2014��8��21�� ����2:19:22
 */
public class DoorPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FPosition;   	//����λ��
	private String FPermission;		//���뿪ͨȨ��
	private String FNote;	    	//��ע
	
	public String getFPosition() {
		return FPosition;
	}
	public void setFPosition(String fPosition) {
		FPosition = fPosition;
	}
	public String getFPermission() {
		return FPermission;
	}
	public void setFPermission(String fPermission) {
		FPermission = fPermission;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
