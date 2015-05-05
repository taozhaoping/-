package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopTestNetworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopTestNetworkBusiness
 * @Description �з���Ŀ��������Ȩ��ҵ���߼���
 * @author 21291
 * @date 2014��7��15�� ����4:41:37
 */
public class DevelopTestNetworkBusiness extends BaseBusiness<DevelopTestNetworkTHeaderInfo> {
	
	public DevelopTestNetworkBusiness(Context context) {
		super(context,DevelopTestNetworkTHeaderInfo.getDevelopTestNetworkTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DevelopTestNetworkBusiness developTestNetworkBusiness;
	public static DevelopTestNetworkBusiness getDevelopTestNetworkBusiness(Context context,String serviceUrl) {
		if (developTestNetworkBusiness == null) {
			developTestNetworkBusiness = new DevelopTestNetworkBusiness(context);
		}
		developTestNetworkBusiness.setServiceUrl(serviceUrl);
		return developTestNetworkBusiness;
	}
	
	/** 
	* @Title: getDevelopTestNetworkTHeaderInfo 
	* @Description: ��ȡ�з���Ŀ��������Ȩ�ޱ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DevelopTestNetworkTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��15�� ����4:53:51
	*/
	public DevelopTestNetworkTHeaderInfo getDevelopTestNetworkTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
