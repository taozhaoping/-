package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeTransferTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeTransferBusiness
 * @Description ӡ���ƽ�ҵ���߼���
 * @author 21291
 * @date 2014��10��11�� ����11:00:53
 */
public class FeTransferBusiness extends BaseBusiness<FeTransferTHeaderInfo> {
	
	public FeTransferBusiness(Context context) {
		super(context,FeTransferTHeaderInfo.getFeTransferTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FeTransferBusiness feTransferBusiness;
	public static FeTransferBusiness getFeTransferBusiness(Context context,String serviceUrl) {
		if (feTransferBusiness == null) {
			feTransferBusiness = new FeTransferBusiness(context);
		}
		feTransferBusiness.setServiceUrl(serviceUrl);
		return feTransferBusiness;
	}
	
	/** 
	* @Title: getFeTransferTHeaderInfo 
	* @Description: ��ȡӡ���ƽ���ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FeTransferTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��11�� ����11:01:30
	*/
	public FeTransferTHeaderInfo getFeTransferTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
