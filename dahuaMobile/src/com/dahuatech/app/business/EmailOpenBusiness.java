package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.EmailOpenTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName EmailOpenBusiness
 * @Description ���俪ͨ����ҵ���߼���
 * @author 21291
 * @date 2014��8��18�� ����10:45:53
 */
public class EmailOpenBusiness extends BaseBusiness<EmailOpenTHeaderInfo> {

	/** 
	* @Name: DocumentApproveBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public EmailOpenBusiness(Context context) {
		super(context,EmailOpenTHeaderInfo.getEmailOpenTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static EmailOpenBusiness emailOpenBusiness;
	public static EmailOpenBusiness getEmailOpenBusiness(Context context,String serviceUrl) {
		if (emailOpenBusiness == null) {
			emailOpenBusiness = new EmailOpenBusiness(context);
		}
		emailOpenBusiness.setServiceUrl(serviceUrl);
		return emailOpenBusiness;
	}
	
	/** 
	* @Title: getEmailOpenTHeaderInfo 
	* @Description: ��ȡ���俪ͨ����ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return EmailOpenTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��18�� ����10:47:00
	*/
	public EmailOpenTHeaderInfo getEmailOpenTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
