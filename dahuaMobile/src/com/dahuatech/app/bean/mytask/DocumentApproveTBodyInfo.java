package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTBodyInfo
 * @Description  �ļ����������嵥��ʵ��
 * @author 21291
 * @date 2014��8��12�� ����10:27:12
 */
public class DocumentApproveTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FDocumentCode;   	//�ļ����
	private String FDocumentName;		//�ļ�����
	private String FDocumentVersion;	//�ļ��汾
	private String FDocumentBelong;		//����ҵ��
	
	public String getFDocumentCode() {
		return FDocumentCode;
	}
	public void setFDocumentCode(String fDocumentCode) {
		FDocumentCode = fDocumentCode;
	}
	public String getFDocumentName() {
		return FDocumentName;
	}
	public void setFDocumentName(String fDocumentName) {
		FDocumentName = fDocumentName;
	}
	public String getFDocumentVersion() {
		return FDocumentVersion;
	}
	public void setFDocumentVersion(String fDocumentVersion) {
		FDocumentVersion = fDocumentVersion;
	}
	public String getFDocumentBelong() {
		return FDocumentBelong;
	}
	public void setFDocumentBelong(String fDocumentBelong) {
		FDocumentBelong = fDocumentBelong;
	}
}
