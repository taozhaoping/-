package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdBorrowTHeaderInfo;

/**
 * @ClassName TdBorrowBusiness
 * @Description �����ļ��������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��8��28�� ����4:44:02
 */
public class TdBorrowBusiness extends BaseBusiness<TdBorrowTHeaderInfo> {

	/** 
	* @Name: TdBorrowBusiness 
	* @Description:  
	*/
	public TdBorrowBusiness(Context context) {
		super(context,TdBorrowTHeaderInfo.getTdBorrowTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static TdBorrowBusiness tdBorrowBusiness;
	public static TdBorrowBusiness getTdBorrowBusiness(Context context,String serviceUrl) {
		if (tdBorrowBusiness == null) {
			tdBorrowBusiness = new TdBorrowBusiness(context);
		}
		tdBorrowBusiness.setServiceUrl(serviceUrl);
		return tdBorrowBusiness;
	}
	
	/** 
	* @Title: getTdBorrowTHeaderInfo 
	* @Description: �����ļ��������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return TdBorrowTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��28�� ����4:45:36
	*/
	public TdBorrowTHeaderInfo getTdBorrowTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
