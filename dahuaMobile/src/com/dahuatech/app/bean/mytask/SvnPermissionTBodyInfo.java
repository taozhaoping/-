package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName SvnPermissionTBodyInfo
 * @Description SVNȨ�ޱ��嵥��ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:47:49
 */
public class SvnPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FAddress;   			//Ȩ�޷��ʵ�ַ
	private String FReadOrWrite;		//����д
	private String FResponsible;		//������
	private String FReason;				//ԭ��
	
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}
	public String getFReadOrWrite() {
		return FReadOrWrite;
	}
	public void setFReadOrWrite(String fReadOrWrite) {
		FReadOrWrite = fReadOrWrite;
	}
	public String getFResponsible() {
		return FResponsible;
	}
	public void setFResponsible(String fResponsible) {
		FResponsible = fResponsible;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
