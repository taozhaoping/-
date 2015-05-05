package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseCostTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseCostTHeaderBusiness
 * @Description �������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��6��16�� ����2:21:54
 */
public class ExpenseCostTHeaderBusiness extends BaseBusiness<ExpenseCostTHeaderInfo> {
	
	/** 
	* @Name: ExpenseCostTHeaderBusiness 
	* @Description: Ĭ�Ϲ��캯�� 
	*/
	public ExpenseCostTHeaderBusiness(Context context) {
		super(context,ExpenseCostTHeaderInfo.getExpenseCostTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ExpenseCostTHeaderBusiness expenseCostTHeaderBusiness;
	public static ExpenseCostTHeaderBusiness getExpenseCostTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseCostTHeaderBusiness == null) {
			expenseCostTHeaderBusiness = new ExpenseCostTHeaderBusiness(context);
		}
		expenseCostTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseCostTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseCostTHeaderInfo 
	* @Description: ��ȡ�������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExpenseCostTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��6��16�� ����2:27:35
	*/
	public ExpenseCostTHeaderInfo getExpenseCostTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
