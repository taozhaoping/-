package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpensePublicTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpensePublicBusiness
 * @Description �Թ�֧����������ҵ���߼���
 * @author 21291
 * @date 2014��6��4�� ����3:31:52
 */
public class ExpensePublicTHeaderBusiness extends BaseBusiness<ExpensePublicTHeaderInfo> {

	public ExpensePublicTHeaderBusiness(Context context) {
		super(context,ExpensePublicTHeaderInfo.getExpensePublicTHeaderInfo());
	}

	// ����ģʽ(�̲߳���ȫд��)
	private static ExpensePublicTHeaderBusiness expensePublicTHeaderBusiness;
	public static ExpensePublicTHeaderBusiness getExpensePublicTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expensePublicTHeaderBusiness == null) {
			expensePublicTHeaderBusiness = new ExpensePublicTHeaderBusiness(context);
		}
		expensePublicTHeaderBusiness.setServiceUrl(serviceUrl);
		return expensePublicTHeaderBusiness;
	}

	/**
	 * @Title: getExpensePublicTHeaderInfo
	 * @Description: ��ȡ�Թ����ݱ���ʵ����Ϣ
	 * @param @param taskParam
	 * @param @return
	 * @return ExpensePublicTHeaderInfo
	 * @throws
	 * @author 21291
	 * @date 2014��6��4�� ����3:57:31
	 */
	public ExpensePublicTHeaderInfo getExpensePublicTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}
}
