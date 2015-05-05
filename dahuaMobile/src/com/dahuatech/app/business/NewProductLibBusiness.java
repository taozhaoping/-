package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NewProductLibTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NewProductLibBusiness
 * @Description �²�Ʒת�ⵥ��ҵ���߼���
 * @author 21291
 * @date 2014��8��12�� ����10:54:55
 */
public class NewProductLibBusiness extends BaseBusiness<NewProductLibTHeaderInfo> {

	/** 
	* @Name: NewProductLibBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public NewProductLibBusiness(Context context) {
		super(context,NewProductLibTHeaderInfo.getNewProductLibTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static NewProductLibBusiness newProductLibBusiness;
	public static NewProductLibBusiness getNewProductLibBusiness(Context context,String serviceUrl) {
		if (newProductLibBusiness == null) {
			newProductLibBusiness = new NewProductLibBusiness(context);
		}
		newProductLibBusiness.setServiceUrl(serviceUrl);
		return newProductLibBusiness;
	}
	
	/** 
	* @Title: getNewProductLibTHeaderInfo 
	* @Description:  ��ȡ�²�Ʒת�ⵥ��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return NewProductLibTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��12�� ����10:55:36
	*/
	public NewProductLibTHeaderInfo getNewProductLibTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
