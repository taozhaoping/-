package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.EngineeringInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName EngineeringBusiness
 * @Description �����̵���ʵ��ҵ���߼���
 * @author 21291
 * @date 2014��4��24�� ����4:24:33
 */
public class EngineeringBusiness extends BaseBusiness<EngineeringInfo> {
	private EngineeringInfo engineeringInfo;
	private EngineeringBusiness(Context context) {
		super(context);
		engineeringInfo=EngineeringInfo.getEngineeringInfo();
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static EngineeringBusiness engineeringBusiness;
	public static EngineeringBusiness getEngineeringBusiness(Context context,String serviceUrl) {
		if(engineeringBusiness==null){
			engineeringBusiness=new EngineeringBusiness(context);
		}
		engineeringBusiness.setServiceUrl(serviceUrl);
		return engineeringBusiness;
	}
	
	/** 
	* @Title: getEngineeringInfo 
	* @Description: �����������ʵ�����ȡ������ʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return EngineeringInfo    
	* @throws 
	* @author 21291
	* @date 2014��4��24�� ����4:54:10
	*/
	public EngineeringInfo getEngineeringInfo(TaskParamInfo taskParam) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=TaskParamInfo.ConvertToJson(taskParam);
			apiClient.AddParam("jsonData", jsonData);
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonObject = new JSONObject(resultMessage.getResult());
					engineeringInfo = gson.fromJson(jsonObject.toString(), EngineeringInfo.class);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return engineeringInfo;
	}

}
