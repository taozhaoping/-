package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyDaysOffTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyDaysOffBusiness
 * @Description ��ͨ���ŵ������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��23�� ����3:07:21
 */
public class ApplyDaysOffBusiness extends BaseBusiness<ApplyDaysOffTHeaderInfo> {
	
	/** 
	* @Name: ApplyDaysOffBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ApplyDaysOffBusiness(Context context){
		super(context,ApplyDaysOffTHeaderInfo.getApplyDaysOffTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ApplyDaysOffBusiness applyDaysOffBusiness;
	public static ApplyDaysOffBusiness getApplyDaysOffBusiness(Context context,String serviceUrl) {
		if (applyDaysOffBusiness == null) {
			applyDaysOffBusiness = new ApplyDaysOffBusiness(context);
		}
		applyDaysOffBusiness.setServiceUrl(serviceUrl);
		return applyDaysOffBusiness;
	}
	
	/** 
	* @Title: getApplyDaysOffTHeaderInfo 
	* @Description: ��ȡ��ͨ���ŵ������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ApplyDaysOffTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��23�� ����3:06:54
	*/
	public ApplyDaysOffTHeaderInfo getApplyDaysOffTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
