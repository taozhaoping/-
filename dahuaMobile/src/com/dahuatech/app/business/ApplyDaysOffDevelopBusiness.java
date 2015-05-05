package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyDaysOffDevelopInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyDaysOffDevelopBusiness
 * @Description �з����ŵ������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��23�� ����3:09:44
 */
public class ApplyDaysOffDevelopBusiness extends BaseBusiness<ApplyDaysOffDevelopInfo> {

	/** 
	* @Name: ApplyDaysOffDevelopBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ApplyDaysOffDevelopBusiness(Context context){
		super(context,ApplyDaysOffDevelopInfo.getApplyDaysOffDevelopInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ApplyDaysOffDevelopBusiness applyDaysOffDevelopBusiness;
	public static ApplyDaysOffDevelopBusiness getApplyDaysOffDevelopBusiness(Context context,String serviceUrl) {
		if (applyDaysOffDevelopBusiness == null) {
			applyDaysOffDevelopBusiness = new ApplyDaysOffDevelopBusiness(context);
		}
		applyDaysOffDevelopBusiness.setServiceUrl(serviceUrl);
		return applyDaysOffDevelopBusiness;
	}
	
	/** 
	* @Title: getApplyDaysOffDevelopInfo 
	* @Description: ��ȡ�з����ŵ������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ApplyDaysOffDevelopInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��23�� ����3:09:20
	*/
	public ApplyDaysOffDevelopInfo getApplyDaysOffDevelopInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
