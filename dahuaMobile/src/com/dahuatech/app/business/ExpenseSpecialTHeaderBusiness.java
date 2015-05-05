package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseSpecialTHeaderBusiness
 * @Description �����������񵥾ݱ�ͷҵ���߼���
 * @author 21291
 * @date 2014��6��20�� ����10:02:59
 */
public class ExpenseSpecialTHeaderBusiness extends BaseBusiness<ExpenseSpecialTHeaderInfo> {

	public ExpenseSpecialTHeaderBusiness(Context context) {
		super(context,ExpenseSpecialTHeaderInfo.getExpenseSpecialTHeaderInfo());
	}

	//����ģʽ(�̲߳���ȫд��)
	private static ExpenseSpecialTHeaderBusiness expenseSpecialTHeaderBusiness;
	public static ExpenseSpecialTHeaderBusiness getExpenseSpecialTHeaderBusiness(Context context,String serviceUrl) {
		if(expenseSpecialTHeaderBusiness==null){
			expenseSpecialTHeaderBusiness=new ExpenseSpecialTHeaderBusiness(context);
		}
		expenseSpecialTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseSpecialTHeaderInfo 
	* @Description: ��ȡ�������������ݱ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExpenseSpecialTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��6��20�� ����9:53:23
	*/
	public ExpenseSpecialTHeaderInfo getExpenseSpecialTHeaderInfo(TaskParamInfo taskParam){
		return getEntityInfo(taskParam);
	}
}
