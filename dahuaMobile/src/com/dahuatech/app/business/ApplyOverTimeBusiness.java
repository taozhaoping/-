package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyOverTimeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyOverTimeBusiness
 * @Description �Ӱ����뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��23�� ����3:00:48
 */
public class ApplyOverTimeBusiness extends BaseBusiness<ApplyOverTimeTHeaderInfo> {

	/** 
	* @Name: ApplyOverTimeBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ApplyOverTimeBusiness(Context context){
		super(context,ApplyOverTimeTHeaderInfo.getApplyOverTimeTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ApplyOverTimeBusiness applyOverTimeBusiness;
	public static ApplyOverTimeBusiness getApplyOverTimeBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyOverTimeBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyOverTimeTHeaderInfo 
	* @Description: ��ȡ�Ӱ����뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ApplyOverTimeTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��23�� ����3:02:23
	*/
	public ApplyOverTimeTHeaderInfo getApplyOverTimeTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
