package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExAttendanceTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExAttendanceBusiness
 * @Description �쳣���ڵ������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��23�� ����3:04:23
 */
public class ExAttendanceBusiness extends BaseBusiness<ExAttendanceTHeaderInfo> {

	/** 
	* @Name: ExAttendanceBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public ExAttendanceBusiness(Context context){
		super(context,ExAttendanceTHeaderInfo.getExAttendanceTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static ExAttendanceBusiness exAttendanceBusiness;
	public static ExAttendanceBusiness getExAttendanceBusiness(Context context,String serviceUrl) {
		if (exAttendanceBusiness == null) {
			exAttendanceBusiness = new ExAttendanceBusiness(context);
		}
		exAttendanceBusiness.setServiceUrl(serviceUrl);
		return exAttendanceBusiness;
	}
	
	/** 
	* @Title: getExAttendanceTHeaderInfo 
	* @Description: ��ȡ�쳣���ڵ������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return ExAttendanceTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��23�� ����3:04:20
	*/
	public ExAttendanceTHeaderInfo getExAttendanceTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
