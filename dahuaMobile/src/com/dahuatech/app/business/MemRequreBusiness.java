package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.MemRequreInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName MemRequreBusiness
 * @Description MEM�������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��17�� ����3:32:31
 */
public class MemRequreBusiness extends BaseBusiness<MemRequreInfo> {
	
	/** 
	* @Name: MemRequreBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public MemRequreBusiness(Context context) {
		super(context,MemRequreInfo.getMemRequreInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static MemRequreBusiness memRequreBusiness;
	public static MemRequreBusiness getMemRequreBusiness(Context context,String serviceUrl) {
		if (memRequreBusiness == null) {
			memRequreBusiness = new MemRequreBusiness(context);
		}
		memRequreBusiness.setServiceUrl(serviceUrl);
		return memRequreBusiness;
	}
	
	/** 
	* @Title: getMemRequreInfo 
	* @Description: ��ȡMEM�������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return MemRequreInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��17�� ����3:33:59
	*/
	public MemRequreInfo getMemRequreInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}
}
