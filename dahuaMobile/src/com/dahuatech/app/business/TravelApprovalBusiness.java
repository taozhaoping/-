package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TravelApprovalInfo;

/**
 * @ClassName TravelApprovalBusiness
 * @Description ������������ҵ���߼���
 * @author 21291
 * @date 2014��8��21�� ����9:54:10
 */
public class TravelApprovalBusiness extends BaseBusiness<TravelApprovalInfo> {

	/** 
	* @Name: TravelApprovalBusiness 
	* @Description:  
	*/
	public TravelApprovalBusiness(Context context) {
		super(context,TravelApprovalInfo.getTravelApprovalInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static TravelApprovalBusiness travelApprovalBusiness;
	public static TravelApprovalBusiness getTravelApprovalBusiness(Context context,String serviceUrl) {
		if (travelApprovalBusiness == null) {
			travelApprovalBusiness = new TravelApprovalBusiness(context);
		}
		travelApprovalBusiness.setServiceUrl(serviceUrl);
		return travelApprovalBusiness;
	}
	
	/** 
	* @Title: getTravelApprovalInfo 
	* @Description: ������������ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return TravelApprovalInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��21�� ����9:54:54
	*/
	public TravelApprovalInfo getTravelApprovalInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
