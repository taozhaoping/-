package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.ExpenseSpecialThingHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseSpecialTHeaderBusiness
 * @Description �����������񵥾ݱ�ͷҵ���߼���
 * @author 21291
 * @date 2014��6��20�� ����10:02:59
 */
public class ExpenseSpecialThingHeaderBusiness extends BaseBusiness<ExpenseSpecialThingHeaderInfo> {

	public ExpenseSpecialThingHeaderBusiness(Context context) {
		super(context,ExpenseSpecialThingHeaderInfo.getExpenseSpecialThingHeaderInfo());
	}

	//����ģʽ(�̲߳���ȫд��)
	private static ExpenseSpecialThingHeaderBusiness expenseSpecialThingHeaderBusiness;
	public static ExpenseSpecialThingHeaderBusiness getExpenseSpecialTHeaderBusiness(Context context,String serviceUrl) {
		if(expenseSpecialThingHeaderBusiness==null){
			expenseSpecialThingHeaderBusiness=new ExpenseSpecialThingHeaderBusiness(context);
		}
		expenseSpecialThingHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialThingHeaderBusiness;
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
	public ExpenseSpecialThingHeaderInfo getExpenseSpecialThingHeaderInfo(TaskParamInfo taskParam){
		return getEntityInfo(taskParam);
	}
}
