package com.dahuatech.app.inter;

/**
 * @ClassName OnDeleteListioner
 * @Description ����ɾ���¼��ӿ���
 * @author 21291
 * @date 2014��5��15�� ����11:23:41
 */
public interface IOnDeleteListioner {
	public abstract boolean isCandelete(int position);
	
	//ɾ��
	public abstract void onDelete(int ID);
	
	//ȡ��
	public abstract void onBack();
}
