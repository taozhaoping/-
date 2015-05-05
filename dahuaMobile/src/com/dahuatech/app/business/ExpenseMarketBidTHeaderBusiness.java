package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseMarketBidTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseMarketBidTHeaderBusiness
 * @Description �г�Ͷ�걨��ҵ���߼���
 * @author 21291
 * @date 2014��6��25�� ����1:51:25
 */
public class ExpenseMarketBidTHeaderBusiness extends BaseBusiness<ExpenseMarketBidTHeaderInfo> {
	
	/** 
	* @Name: ExpenseMarketBidTHeaderBusiness 
	* @Description:����Ĭ�Ϻ���  
	*/
	public ExpenseMarketBidTHeaderBusiness(Context context) {
		super(context,ExpenseMarketBidTHeaderInfo.getExpenseMarketBidTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ExpenseMarketBidTHeaderBusiness expenseMarketBidTHeaderBusiness;
	public static ExpenseMarketBidTHeaderBusiness getExpenseMarketBidTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseMarketBidTHeaderBusiness == null) {
			expenseMarketBidTHeaderBusiness = new ExpenseMarketBidTHeaderBusiness(context);
		}
		expenseMarketBidTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseMarketBidTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseMarketBidTHeaderInfo 
	* @Description: ��ȡ�����г�Ͷ�굥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExpenseMarketBidTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��6��25�� ����2:08:16
	*/
	public ExpenseMarketBidTHeaderInfo getExpenseMarketBidTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}

}
