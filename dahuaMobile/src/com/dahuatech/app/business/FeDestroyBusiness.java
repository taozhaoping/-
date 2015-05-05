package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeDestroyTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeDestroyBusiness
 * @Description ӡ������ҵ���߼���
 * @author 21291
 * @date 2014��10��11�� ����10:54:30
 */
public class FeDestroyBusiness extends BaseBusiness<FeDestroyTHeaderInfo> {
	
	public FeDestroyBusiness(Context context) {
		super(context,FeDestroyTHeaderInfo.getFeDestroyTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FeDestroyBusiness feDestroyBusiness;
	public static FeDestroyBusiness getFeDestroyBusiness(Context context,String serviceUrl) {
		if (feDestroyBusiness == null) {
			feDestroyBusiness = new FeDestroyBusiness(context);
		}
		feDestroyBusiness.setServiceUrl(serviceUrl);
		return feDestroyBusiness;
	}
	
	/** 
	* @Title: getFeDestroyTHeaderInfo 
	* @Description: ��ȡӡ�����ٱ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FeDestroyTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��11�� ����10:55:26
	*/
	public FeDestroyTHeaderInfo getFeDestroyTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
