package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ProjectReadTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ProjectReadBusiness
 * @Description ��Ŀ��Ϣ�Ķ�Ȩ�޵���ҵ���߼���
 * @author 21291
 * @date 2014��9��23�� ����2:49:55
 */
public class ProjectReadBusiness extends BaseBusiness<ProjectReadTHeaderInfo> {

	/** 
	* @Name: ProjectReadBusiness 
	* @Description:  
	*/
	public ProjectReadBusiness(Context context) {
		super(context,ProjectReadTHeaderInfo.getProjectReadTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ProjectReadBusiness projectReadBusiness;
	public static ProjectReadBusiness getProjectReadBusiness(Context context,String serviceUrl) {
		if (projectReadBusiness == null) {
			projectReadBusiness = new ProjectReadBusiness(context);
		}
		projectReadBusiness.setServiceUrl(serviceUrl);
		return projectReadBusiness;
	}
	
	/** 
	* @Title: getProjectReadTHeaderInfo 
	* @Description: ��Ŀ��Ϣ�Ķ�Ȩ�޵���ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ProjectReadTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��9��23�� ����2:50:46
	*/
	public ProjectReadTHeaderInfo getProjectReadTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
