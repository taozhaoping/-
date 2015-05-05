package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.common.DESUtils;

/**
 * @ClassName UserLoginBussiness
 * @Description ��½��֤ҵ���߼���
 * @author 21291
 * @date 2014��3��31�� ����9:41:21
 */
public class UserLoginBussiness extends BaseBusiness<Void> {
	private UserLoginBussiness(Context context) {
		super(context);
	}

	//����ģʽ(�̲߳���ȫд��)
	private static UserLoginBussiness userLoginBussiness=null;	
	public static UserLoginBussiness getUserLoginBussiness(Context context,String serviceUrl){
		if(userLoginBussiness==null){
			userLoginBussiness = new UserLoginBussiness(context);	
		}
		userLoginBussiness.setServiceUrl(serviceUrl);
		return userLoginBussiness;
	}
	
	/** 
	* @Title: loginVerify 
	* @Description: ��½��֤�߼�
	* @param @param userInfo
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����12:02:31
	*/
	public String loginVerify(UserInfo userInfo) {
		String returnString="";
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=UserInfo.ConvertToJson(userInfo);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// ��ȡ��Ӧ��Ϣ
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				if(resultMessage.isIsSuccess()){
					returnString=resultMessage.getResult();
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "���������ϢΪ:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return returnString;
	}
	
	/** 
	* @Title: verifyValid 
	* @Description: ��֤Ա�������Ƿ���Ч
	* @param @param fItemNumber
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2015��2��5�� ����4:41:00
	*/
	public ResultMessage verifyValid(String fItemNumber) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// ��ȡ��Ӧ��Ϣ
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			} else {
				responseErrorMessage = "״̬��Ϊ:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "���������ϢΪ:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return resultMessage;
	}	
}
