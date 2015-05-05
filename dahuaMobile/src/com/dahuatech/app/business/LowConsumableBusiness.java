package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.LowConsumableTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName LowConsumableBusiness
 * @Description ��ֵ�׺����ϴ��뵥��ҵ���߼���
 * @author 21291
 * @date 2014��8��19�� ����4:37:06
 */
public class LowConsumableBusiness extends BaseBusiness<LowConsumableTHeaderInfo> {

	/** 
	* @Name: LowConsumableBusiness 
	* @Description:  
	*/
	public LowConsumableBusiness(Context context) {
		super(context,LowConsumableTHeaderInfo.getLowConsumableTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static LowConsumableBusiness lowConsumableBusiness;
	public static LowConsumableBusiness getLowConsumableBusiness(Context context,String serviceUrl) {
		if (lowConsumableBusiness == null) {
			lowConsumableBusiness = new LowConsumableBusiness(context);
		}
		lowConsumableBusiness.setServiceUrl(serviceUrl);
		return lowConsumableBusiness;
	}

	/** 
	* @Title: getLowConsumableTHeaderInfo 
	* @Description: ��ֵ�׺����ϴ��뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return LowConsumableTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��19�� ����4:38:29
	*/
	public LowConsumableTHeaderInfo getLowConsumableTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
