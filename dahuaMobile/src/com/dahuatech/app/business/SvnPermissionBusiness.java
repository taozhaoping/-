package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.SvnPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName SvnPermissionBusiness
 * @Description SVNȨ�޵���ҵ���߼���
 * @author 21291
 * @date 2014��8��12�� ����10:56:23
 */
public class SvnPermissionBusiness extends BaseBusiness<SvnPermissionTHeaderInfo> {

	/** 
	* @Name: SvnPermissionBusiness 
	* @Description:  
	*/
	public SvnPermissionBusiness(Context context) {
		super(context,SvnPermissionTHeaderInfo.getSvnPermissionTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static SvnPermissionBusiness svnPermissionBusiness;
	public static SvnPermissionBusiness getSvnPermissionBusiness(Context context,String serviceUrl) {
		if (svnPermissionBusiness == null) {
			svnPermissionBusiness = new SvnPermissionBusiness(context);
		}
		svnPermissionBusiness.setServiceUrl(serviceUrl);
		return svnPermissionBusiness;
	}
	
	/** 
	* @Title: getSvnPermissionTHeaderInfo 
	* @Description: ��ȡSVNȨ�޵���ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return SvnPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��12�� ����10:57:53
	*/
	public SvnPermissionTHeaderInfo getSvnPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
