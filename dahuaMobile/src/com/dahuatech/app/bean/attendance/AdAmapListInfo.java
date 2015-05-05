package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdAmapListInfo
 * @Description ���ĵ�ַ����ʵ��
 * @author 21291
 * @date 2015��1��5�� ����10:13:13
 */
public class AdAmapListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FCacheKey;	//����汾��
	private String FAmapList;	//���ĵ�ַ�б�
	public String getFCacheKey() {
		return FCacheKey;
	}
	public void setFCacheKey(String fCacheKey) {
		FCacheKey = fCacheKey;
	}
	public String getFAmapList() {
		return FAmapList;
	}
	public void setFAmapList(String fAmapList) {
		FAmapList = fAmapList;
	}
	
	//�ڲ��൥��ģʽ
	private static class SingletonHolder {  
        private static AdAmapListInfo instance = new AdAmapListInfo();  
    }
	private AdAmapListInfo() {}
	public static AdAmapListInfo getAdAmapListInfo() {
		return SingletonHolder.instance;
	}
}
