package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DoorPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DoorPermissionBusiness
 * @Description �Ž�Ȩ�����뵥��ҵ���߼���
 * @author 21291
 * @date 2014��8��21�� ����2:20:46
 */
public class DoorPermissionBusiness extends BaseBusiness<DoorPermissionTHeaderInfo> {

	/** 
	* @Name: DoorPermissionBusiness 
	* @Description:  
	*/
	public DoorPermissionBusiness(Context context) {
		super(context,DoorPermissionTHeaderInfo.getDoorPermissionTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DoorPermissionBusiness doorPermissionBusiness;
	public static DoorPermissionBusiness getDoorPermissionBusiness(Context context,String serviceUrl) {
		if (doorPermissionBusiness == null) {
			doorPermissionBusiness = new DoorPermissionBusiness(context);
		}
		doorPermissionBusiness.setServiceUrl(serviceUrl);
		return doorPermissionBusiness;
	}
	
	/** 
	* @Title: getDoorPermissionTHeaderInfo 
	* @Description: �Ž�Ȩ�޵���ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DoorPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��21�� ����2:21:39
	*/
	public DoorPermissionTHeaderInfo getDoorPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
