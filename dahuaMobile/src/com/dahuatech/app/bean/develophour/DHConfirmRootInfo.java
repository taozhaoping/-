package com.dahuatech.app.bean.develophour;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName DHConfirmRootInfo
 * @Description �з���ʱȷ���б������ʵ����  (�õ��˹۲���ģʽ-���ǹ۲��������Ǳ��۲�����)
 * @author 21291
 * @date 2014��10��20�� ����11:29:12
 */
public class DHConfirmRootInfo extends Observable implements Observer {  //��ʾ���Ա��۲�
	
	public String FProjectCode;      				//��Ŀ����
	public String FProjectName; 					//��Ŀ����
    public List<DHConfirmChildInfo> FChildren; 		//�Ӽ�����
	private boolean isChecked;						//�Ƿ�ѡ��

	public String getFProjectCode() {
		return FProjectCode;
	}

	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	
	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public List<DHConfirmChildInfo> getFChildren() {
		return FChildren;
	}

	public void setFChildren(List<DHConfirmChildInfo> fChildren) {
		FChildren = fChildren;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public DHConfirmRootInfo(){
		this.isChecked=false;
		FChildren=new ArrayList<DHConfirmChildInfo>();
	}
	
	public void changeChecked(){
		isChecked = !isChecked;
		super.setChanged();  //���ñ仯�� 
		super.notifyObservers(isChecked);  //֪ͨ�б仯��
	}

	@Override
	public void update(Observable observable, Object data) {  //����仯����
		boolean flag = true;
		for (DHConfirmChildInfo dChildInfo : FChildren) {
			if (dChildInfo.isChecked() == false) {
				flag = false;
			}
		}
		this.isChecked = flag;	
	}
	
}
