package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Entity;


/**
 * @ClassName EngineeringInfo
 * @Description �����̵���ʵ����
 * @author 21291
 * @date 2014��4��22�� ����6:14:14
 */
public class EngineeringInfo extends Entity {

	private static final long serialVersionUID = 1L;
	
	private String FBillNo;			//���ݱ���
	private String FApplyName;		//������
	private String FApplyDate;		//����ʱ��
	private String FEngineereName;  //����������
	private String FAddress; 		//��ַ
	private String FAmount;			//Ԥ�ƴ󻪽��
	private String FContact; 		//��ϵ��
	private String FTel;			//��ϵ�绰
	private String FComboBox; 		//Ȧ������
	private String FText; 			//ԭȦ������
	private String FComboBox1; 		//Ŀ��Ȧ������
	private String FBase1; 			//Ȧ����˭
	private String FNote;	 		//ԭ��˵��

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFEngineereName() {
		return FEngineereName;
	}

	public void setFEngineereName(String fEngineereName) {
		FEngineereName = fEngineereName;
	}

	public String getFAddress() {
		return FAddress;
	}

	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}

	public String getFAmount() {
		return FAmount;
	}

	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}

	public String getFContact() {
		return FContact;
	}
	
	public String getFTel() {
		return FTel;
	}

	public void setFTel(String fTel) {
		FTel = fTel;
	}

	public void setFContact(String fContact) {
		FContact = fContact;
	}

	public String getFComboBox() {
		return FComboBox;
	}

	public void setFComboBox(String fComboBox) {
		FComboBox = fComboBox;
	}

	public String getFText() {
		return FText;
	}

	public void setFText(String fText) {
		FText = fText;
	}

	public String getFComboBox1() {
		return FComboBox1;
	}

	public void setFComboBox1(String fComboBox1) {
		FComboBox1 = fComboBox1;
	}

	public String getFBase1() {
		return FBase1;
	}

	public void setFBase1(String fBase1) {
		FBase1 = fBase1;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static EngineeringInfo instance = new EngineeringInfo();  
    }
	private EngineeringInfo() {}
	public static EngineeringInfo getEngineeringInfo() {
		return singletonHolder.instance;
	}
}
