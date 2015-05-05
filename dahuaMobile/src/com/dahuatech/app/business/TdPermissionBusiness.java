package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdPermissionTHeaderInfo;

/**
 * @ClassName TdPermissionBusiness
 * @Description TDȨ�����뵥��ҵ���߼���
 * @author 21291
 * @date 2014��9��23�� ����10:19:30
 */
public class TdPermissionBusiness extends BaseBusiness<TdPermissionTHeaderInfo> {

	/** 
	* @Name: TdPermissionBusiness 
	* @Description:  
	*/
	public TdPermissionBusiness(Context context) {
		super(context,TdPermissionTHeaderInfo.getTdPermissionTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static TdPermissionBusiness tdPermissionBusiness;
	public static TdPermissionBusiness getTdPermissionBusiness(Context context,String serviceUrl) {
		if (tdPermissionBusiness == null) {
			tdPermissionBusiness = new TdPermissionBusiness(context);
		}
		tdPermissionBusiness.setServiceUrl(serviceUrl);
		return tdPermissionBusiness;
	}
	
	/** 
	* @Title: getTdPermissionTHeaderInfo 
	* @Description: TDȨ�����뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return TdPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��9��23�� ����10:20:27
	*/
	public TdPermissionTHeaderInfo getTdPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
