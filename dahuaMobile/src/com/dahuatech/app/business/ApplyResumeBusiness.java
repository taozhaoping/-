package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyResumeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyResumeBusiness
 * @Description �������뵥��ҵ���߼���
 * @author 21291
 * @date 2015��1��12�� ����10:29:37
 */
public class ApplyResumeBusiness extends BaseBusiness<ApplyResumeTHeaderInfo> {

	/** 
	* @Name: ApplyResumeBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ApplyResumeBusiness(Context context){
		super(context,ApplyResumeTHeaderInfo.getApplyResumeTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ApplyResumeBusiness applyOverTimeBusiness;
	public static ApplyResumeBusiness getApplyResumeBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyResumeBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyResumeTHeaderInfo 
	* @Description: ��ȡ�������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ApplyResumeTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2015��1��12�� ����10:30:07
	*/
	public ApplyResumeTHeaderInfo getApplyResumeTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
