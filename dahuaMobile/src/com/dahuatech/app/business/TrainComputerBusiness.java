package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TrainComputerTHeaderInfo;

/**
 * @ClassName TrainComputerBusiness
 * @Description  ��ѵ���㻯�������뵥��ҵ���߼���
 * @author 21291
 * @date 2014��8��21�� ����9:30:14
 */
public class TrainComputerBusiness extends BaseBusiness<TrainComputerTHeaderInfo> {

	/** 
	* @Name: TrainComputerBusiness 
	* @Description:  
	*/
	public TrainComputerBusiness(Context context) {
		super(context,TrainComputerTHeaderInfo.getTrainComputerTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static TrainComputerBusiness trainComputerBusiness;
	public static TrainComputerBusiness getTrainComputerBusiness(Context context,String serviceUrl) {
		if (trainComputerBusiness == null) {
			trainComputerBusiness = new TrainComputerBusiness(context);
		}
		trainComputerBusiness.setServiceUrl(serviceUrl);
		return trainComputerBusiness;
	}
	
	/** 
	* @Title: getTrainComputerTHeaderInfo 
	* @Description: ��ѵ���㻯�������뵥��ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return TrainComputerTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��8��21�� ����9:31:02
	*/
	public TrainComputerTHeaderInfo getTrainComputerTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
