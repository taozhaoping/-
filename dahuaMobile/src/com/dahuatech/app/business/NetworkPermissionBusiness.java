package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NetworkPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NetworkPermissionBusiness
 * @Description ����Ȩ�����뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��9�� ����3:55:58
 */
public class NetworkPermissionBusiness extends BaseBusiness<NetworkPermissionTHeaderInfo> {
	
	/** 
	* @Name: NetworkPermissionBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public NetworkPermissionBusiness(Context context) {
		super(context,NetworkPermissionTHeaderInfo.getNetworkPermissionTHeaderInfo());
	}

	// ����ģʽ(�̲߳���ȫд��)
	private static NetworkPermissionBusiness networkPermissionBusiness;
	public static NetworkPermissionBusiness getNetworkPermissionBusiness(Context context,String serviceUrl) {
		if (networkPermissionBusiness == null) {
			networkPermissionBusiness = new NetworkPermissionBusiness(context);
		}
		networkPermissionBusiness.setServiceUrl(serviceUrl);
		return networkPermissionBusiness;
	}
	
	/** 
	* @Title: getNetworkPermissionTHeaderInfo 
	* @Description: ��ȡ����Ȩ�����뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return NetworkPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��9�� ����3:57:09
	*/
	public NetworkPermissionTHeaderInfo getNetworkPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
