package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopTravelTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopTravelBusiness
 * @Description �з�������ǲ����ҵ���߼���
 * @author 21291
 * @date 2014��8��15�� ����9:51:05
 */
public class DevelopTravelBusiness extends BaseBusiness<DevelopTravelTHeaderInfo> {

	/** 
	* @Name: DevelopTravelBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public DevelopTravelBusiness(Context context) {
		super(context,DevelopTravelTHeaderInfo.getDevelopTravelTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DevelopTravelBusiness developTravelBusiness;
	public static DevelopTravelBusiness getDevelopTravelBusiness(Context context,String serviceUrl) {
		if (developTravelBusiness == null) {
			developTravelBusiness = new DevelopTravelBusiness(context);
		}
		developTravelBusiness.setServiceUrl(serviceUrl);
		return developTravelBusiness;
	}
	
	/** 
	* @Title: getDevelopTravelTHeaderInfo 
	* @Description: ��ȡ�з�������ǲ����ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DevelopTravelTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����9:52:07
	*/
	public DevelopTravelTHeaderInfo getDevelopTravelTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
