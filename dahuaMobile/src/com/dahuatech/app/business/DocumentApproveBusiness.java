package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DocumentApproveTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DocumentApproveBusiness
 * @Description �ļ�����������ҵ���߼���
 * @author 21291
 * @date 2014��8��12�� ����10:52:07
 */
public class DocumentApproveBusiness extends BaseBusiness<DocumentApproveTHeaderInfo> {

	/** 
	* @Name: DocumentApproveBusiness 
	* @Description: Ĭ�Ϲ��캯��  
	*/
	public DocumentApproveBusiness(Context context) {
		super(context,DocumentApproveTHeaderInfo.getDocumentApproveTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DocumentApproveBusiness documentApproveBusiness;
	public static DocumentApproveBusiness getDocumentApproveBusiness(Context context,String serviceUrl) {
		if (documentApproveBusiness == null) {
			documentApproveBusiness = new DocumentApproveBusiness(context);
		}
		documentApproveBusiness.setServiceUrl(serviceUrl);
		return documentApproveBusiness;
	}
	
	/** 
	* @Title: getDocumentApproveTHeaderInfo 
	* @Description: ��ȡ�ļ�����������ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DocumentApproveTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��12�� ����10:53:36
	*/
	public DocumentApproveTHeaderInfo getDocumentApproveTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
