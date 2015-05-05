package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseMarketPayTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseMarketPayTHeaderBusiness
 * @Description �г�Ͷ��֧��ҵ���߼���
 * @author 21291
 * @date 2014��6��25�� ����1:51:09
 */
public class ExpenseMarketPayTHeaderBusiness extends BaseBusiness<ExpenseMarketPayTHeaderInfo> {
	
	/** 
	* @Name: ExpenseMarketPayTHeaderBusiness 
	* @Description:  ����Ĭ�Ϻ���
	*/
	public ExpenseMarketPayTHeaderBusiness(Context context) {
		super(context,ExpenseMarketPayTHeaderInfo.getExpenseMarketPayTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ExpenseMarketPayTHeaderBusiness expenseMarketPayTHeaderBusiness;
	public static ExpenseMarketPayTHeaderBusiness getExpenseMarketPayTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseMarketPayTHeaderBusiness == null) {
			expenseMarketPayTHeaderBusiness = new ExpenseMarketPayTHeaderBusiness(context);
		}
		expenseMarketPayTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseMarketPayTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseMarketPayTHeaderInfo 
	* @Description: ��ȡ�����г�֧������ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExpenseMarketPayTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��6��25�� ����2:09:09
	*/
	public ExpenseMarketPayTHeaderInfo getExpenseMarketPayTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}


}
