package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NewProductReworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NewProductReworkBusiness
 * @Description �²�Ʒ��������ҵ���߼���
 * @author 21291
 * @date 2014��8��27�� ����9:44:34
 */
public class NewProductReworkBusiness extends BaseBusiness<NewProductReworkTHeaderInfo> {

	/** 
	* @Name: NewProductReworkBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public NewProductReworkBusiness(Context context) {
		super(context,NewProductReworkTHeaderInfo.getNewProductReworkTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static NewProductReworkBusiness newProductReworkBusiness;
	public static NewProductReworkBusiness getNewProductReworkBusiness(Context context,String serviceUrl) {
		if (newProductReworkBusiness == null) {
			newProductReworkBusiness = new NewProductReworkBusiness(context);
		}
		newProductReworkBusiness.setServiceUrl(serviceUrl);
		return newProductReworkBusiness;
	}
	
	/** 
	* @Title: getNewProductReworkTHeaderInfo 
	* @Description: ��ȡ�²�Ʒ��������ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return NewProductReworkTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��27�� ����9:45:41
	*/
	public NewProductReworkTHeaderInfo getNewProductReworkTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
