package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpensePrivateTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpensePrivateTHeaderBusiness
 * @Description ������˽���ݱ�ͷʵ��ҵ���߼���
 * @author 21291
 * @date 2014��5��26�� ����2:35:27
 */
public class ExpensePrivateTHeaderBusiness extends BaseBusiness<ExpensePrivateTHeaderInfo> {
	/** 
	* @Name: ExpensePrivateTHeaderBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	private ExpensePrivateTHeaderBusiness(Context context) {
		super(context,ExpensePrivateTHeaderInfo.getExpensePrivateTHeaderInfo());
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static ExpensePrivateTHeaderBusiness expensePrivateTHeaderBusiness;
	public static ExpensePrivateTHeaderBusiness getExpensePrivateTHeaderBusiness(Context context,String serviceUrl) {
		if(expensePrivateTHeaderBusiness==null){
			expensePrivateTHeaderBusiness=new ExpensePrivateTHeaderBusiness(context);
		}
		expensePrivateTHeaderBusiness.setServiceUrl(serviceUrl);
		return expensePrivateTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpensePrivateTHeaderInfo 
	* @Description: ��ȡ��˽�������ݱ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExpensePrivateTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��5��23�� ����9:44:25
	*/
	public ExpensePrivateTHeaderInfo getExpensePrivateTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}

}
