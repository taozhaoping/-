package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdBorrowTBodyInfo
 * @Description �����ļ��������뵥����ʵ��
 * @author 21291
 * @date 2014��8��28�� ����4:26:19
 */
public class TdBorrowTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDocumentName;   //�ļ�����
	private String FDocumentUse;	//�ļ���;
	private String FSupportType;	//�������
	private String FVersion;		//�汾
	private String FNote;			//��ע
	
	public String getFDocumentName() {
		return FDocumentName;
	}
	public void setFDocumentName(String fDocumentName) {
		FDocumentName = fDocumentName;
	}
	public String getFDocumentUse() {
		return FDocumentUse;
	}
	public void setFDocumentUse(String fDocumentUse) {
		FDocumentUse = fDocumentUse;
	}
	public String getFSupportType() {
		return FSupportType;
	}
	public void setFSupportType(String fSupportType) {
		FSupportType = fSupportType;
	}
	public String getFVersion() {
		return FVersion;
	}
	public void setFVersion(String fVersion) {
		FVersion = fVersion;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
}
