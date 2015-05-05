package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyLeaveTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyLeaveBusiness
 * @Description ������뵥��ҵ���߼���
 * @author 21291
 * @date 2015��1��12�� ����9:35:32
 */
public class ApplyLeaveBusiness extends BaseBusiness<ApplyLeaveTHeaderInfo> {

	/** 
	* @Name: ApplyLeaveBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ApplyLeaveBusiness(Context context){
		super(context,ApplyLeaveTHeaderInfo.getApplyLeaveTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ApplyLeaveBusiness applyOverTimeBusiness;
	public static ApplyLeaveBusiness getApplyLeaveBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyLeaveBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyLeaveTHeaderInfo 
	* @Description: ��ȡ������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ApplyLeaveTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2015��1��12�� ����9:36:37
	*/
	public ApplyLeaveTHeaderInfo getApplyLeaveTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
