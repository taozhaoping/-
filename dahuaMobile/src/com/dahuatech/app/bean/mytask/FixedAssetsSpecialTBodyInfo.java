package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FixedAssetsSpecialTBodyInfo
 * @Description �̶��ʲ���������ɹ����󵥾ݱ���ʵ��
 * @author 21291
 * @date 2014��8��19�� ����1:52:25
 */
public class FixedAssetsSpecialTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo; 		//�̶��ʲ����
	private String FName; 			//�̶��ʲ�����
	private String FModel; 			//Ʒ���ͺ�
	private String FNumber; 		//����
	private String FDate; 			//Ҫ�󵽻�����
	private String FPerson; 		//����������
	private String FNote; 			//��ע
	
	public String getFBillNo() {
		return FBillNo;
	}
	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFNumber() {
		return FNumber;
	}
	public void setFNumber(String fNumber) {
		FNumber = fNumber;
	}
	public String getFDate() {
		return FDate;
	}
	public void setFDate(String fDate) {
		FDate = fDate;
	}
	public String getFPerson() {
		return FPerson;
	}
	public void setFPerson(String fPerson) {
		FPerson = fPerson;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
