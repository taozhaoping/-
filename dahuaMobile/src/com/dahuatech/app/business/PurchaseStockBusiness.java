package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.PurchaseStockTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName PurchaseStockBusiness
 * @Description �ɹ����ϵ���ҵ���߼���
 * @author 21291
 * @date 2014��8��15�� ����2:37:42
 */
public class PurchaseStockBusiness extends BaseBusiness<PurchaseStockTHeaderInfo> {

	/** 
	* @Name: PurchaseStockBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public PurchaseStockBusiness(Context context) {
		super(context,PurchaseStockTHeaderInfo.getPurchaseStockTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static PurchaseStockBusiness purchaseStockBusiness;
	public static PurchaseStockBusiness getPurchaseStockBusiness(Context context,String serviceUrl) {
		if (purchaseStockBusiness == null) {
			purchaseStockBusiness = new PurchaseStockBusiness(context);
		}
		purchaseStockBusiness.setServiceUrl(serviceUrl);
		return purchaseStockBusiness;
	}
	
	/** 
	* @Title: getPurchaseStockTHeaderInfo 
	* @Description: ��ȡ�ɹ����ϵ���ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return PurchaseStockTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��15�� ����2:38:25
	*/
	public PurchaseStockTHeaderInfo getPurchaseStockTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
