package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DaHuaAssumeCostInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DaHuaAssumeCostBusiness
 * @Description �󻪳е��������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��15�� ����5:25:36
 */
public class DaHuaAssumeCostBusiness extends BaseBusiness<DaHuaAssumeCostInfo> {
	
	/** 
	* @Name: DaHuaAssumeCostBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public DaHuaAssumeCostBusiness(Context context) {
		super(context,DaHuaAssumeCostInfo.getDaHuaAssumeCostInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DaHuaAssumeCostBusiness daHuaAssumeCostBusiness;
	public static DaHuaAssumeCostBusiness getDaHuaAssumeCostBusiness(Context context,String serviceUrl) {
		if (daHuaAssumeCostBusiness == null) {
			daHuaAssumeCostBusiness = new DaHuaAssumeCostBusiness(context);
		}
		daHuaAssumeCostBusiness.setServiceUrl(serviceUrl);
		return daHuaAssumeCostBusiness;
	}
	
	/** 
	* @Title: getDaHuaAssumeCostInfo 
	* @Description: ��ȡ�󻪳е��������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DaHuaAssumeCostInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��15�� ����5:29:34
	*/
	public DaHuaAssumeCostInfo getDaHuaAssumeCostInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}

}
