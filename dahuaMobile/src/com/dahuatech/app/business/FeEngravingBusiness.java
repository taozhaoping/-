package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeEngravingTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeEngravingBusiness
 * @Description ӡ������ҵ���߼���
 * @author 21291
 * @date 2014��10��11�� ����10:56:50
 */
public class FeEngravingBusiness extends BaseBusiness<FeEngravingTHeaderInfo> {
	
	public FeEngravingBusiness(Context context) {
		super(context,FeEngravingTHeaderInfo.getFeEngravingTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FeEngravingBusiness feEngravingBusiness;
	public static FeEngravingBusiness getFeEngravingBusiness(Context context,String serviceUrl) {
		if (feEngravingBusiness == null) {
			feEngravingBusiness = new FeEngravingBusiness(context);
		}
		feEngravingBusiness.setServiceUrl(serviceUrl);
		return feEngravingBusiness;
	}
	
	/** 
	* @Title: getFeEngravingTHeaderInfoo 
	* @Description: ��ȡӡ�����Ʊ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FeEngravingTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��11�� ����10:57:37
	*/
	public FeEngravingTHeaderInfo getFeEngravingTHeaderInfoo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
