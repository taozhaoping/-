package com.dahuatech.app.bean.develophour;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName DHConfirmChildInfo
 * @Description �з���ʱȷ���б��Ӽ���ʵ����  (�õ��˹۲���ģʽ-���ǹ۲��������Ǳ��۲�����)
 * @author 21291
 * @date 2014��10��20�� ����11:43:03
 */
public class DHConfirmChildInfo extends Observable implements Observer {

	private String FItemNumber;		//ȷ����ԱԱ����
	private String FItemName;		//ȷ����ԱԱ������
	private boolean isChecked;		//�Ƿ�ѡ��
   
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}

	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public DHConfirmChildInfo(){
		this.isChecked=false;
	}
	
	public void changeChecked(){  //�ı�״̬
		isChecked = !isChecked;
		super.setChanged();
		super.notifyObservers();
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof Boolean) {
			this.isChecked = (Boolean) data;
		}
	}
}
