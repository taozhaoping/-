package com.dahuatech.app.inter;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ITaskContext
 * @Description ���ݹ�ͬ�����ӿ�
 * @author 21291
 * @date 2014��10��28�� ����2:25:33
 */
public interface ITaskContext {	
	//�첽��ȡʵ����Ϣ
	public abstract Base getDataByPost(String serviceUrl);
	
	//��ʼ����Ϣ
	public abstract void initBase(Base base);
}
