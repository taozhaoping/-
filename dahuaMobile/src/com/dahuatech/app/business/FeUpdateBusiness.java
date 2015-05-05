package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeUpdateTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeUpdateBusiness
 * @Description ӡ������ҵ���߼���
 * @author 21291
 * @date 2014��10��11�� ����11:06:18
 */
public class FeUpdateBusiness extends BaseBusiness<FeUpdateTHeaderInfo> {
	
	public FeUpdateBusiness(Context context) {
		super(context,FeUpdateTHeaderInfo.getFeUpdateTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FeUpdateBusiness feUpdateBusiness;
	public static FeUpdateBusiness getFeUpdateBusiness(Context context,String serviceUrl) {
		if (feUpdateBusiness == null) {
			feUpdateBusiness = new FeUpdateBusiness(context);
		}
		feUpdateBusiness.setServiceUrl(serviceUrl);
		return feUpdateBusiness;
	}
	
	/** 
	* @Title: getFeUpdateTHeaderInfo 
	* @Description: ��ȡӡ��������ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FeUpdateTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��11�� ����11:07:05
	*/
	public FeUpdateTHeaderInfo getFeUpdateTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
