package com.dahuatech.app.bean;

/**
 * @ClassName Entity
 * @Description ʵ����
 * @author 21291
 * @date 2014��4��17�� ����3:46:35
 */
public abstract class Entity extends Base {
	
	private static final long serialVersionUID = 1L;
	
	protected int FID;
	
	public int getFID() {
		return FID;
	}
	
	public Entity(){}
}
