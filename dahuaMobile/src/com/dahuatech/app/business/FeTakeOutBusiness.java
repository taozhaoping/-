package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeTakeOutTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeTakeOutBusiness
 * @Description ӡ�����ҵ���߼���
 * @author 21291
 * @date 2014��10��11�� ����10:58:54
 */
public class FeTakeOutBusiness extends BaseBusiness<FeTakeOutTHeaderInfo> {
	
	public FeTakeOutBusiness(Context context) {
		super(context,FeTakeOutTHeaderInfo.getFeTakeOutTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FeTakeOutBusiness feTakeOutBusiness;
	public static FeTakeOutBusiness getFeTakeOutBusiness(Context context,String serviceUrl) {
		if (feTakeOutBusiness == null) {
			feTakeOutBusiness = new FeTakeOutBusiness(context);
		}
		feTakeOutBusiness.setServiceUrl(serviceUrl);
		return feTakeOutBusiness;
	}
	
	/** 
	* @Title: getFeTakeOutTHeaderInfo 
	* @Description: ��ȡӡ�������ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FeTakeOutTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��11�� ����10:59:29
	*/
	public FeTakeOutTHeaderInfo getFeTakeOutTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
