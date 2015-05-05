package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FixedAssetsSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FixedAssetsSpecialBusiness
 * @Description �̶��ʲ���������ɹ����󵥾�ҵ���߼���
 * @author 21291
 * @date 2014��8��19�� ����1:53:15
 */
public class FixedAssetsSpecialBusiness extends BaseBusiness<FixedAssetsSpecialTHeaderInfo> {

	/** 
	* @Name: FixedAssetsSpecialBusiness 
	* @Description:  
	*/
	public FixedAssetsSpecialBusiness(Context context) {
		super(context,FixedAssetsSpecialTHeaderInfo.getFixedAssetsSpecialTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static FixedAssetsSpecialBusiness fixedAssetsSpecialBusiness;
	public static FixedAssetsSpecialBusiness getFixedAssetsSpecialBusiness(Context context,String serviceUrl) {
		if (fixedAssetsSpecialBusiness == null) {
			fixedAssetsSpecialBusiness = new FixedAssetsSpecialBusiness(context);
		}
		fixedAssetsSpecialBusiness.setServiceUrl(serviceUrl);
		return fixedAssetsSpecialBusiness;
	}

	/** 
	* @Title: getFixedAssetsSpecialTHeaderInfo 
	* @Description: �̶��ʲ���������ɹ����󵥾�ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return FixedAssetsSpecialTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��19�� ����1:54:16
	*/
	public FixedAssetsSpecialTHeaderInfo getFixedAssetsSpecialTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
