package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.AppException;
import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.UpdateInfo;
import com.google.gson.JsonParseException;

/**
 * @ClassName SettingBusiness
 * @Description ϵͳ����ҵ���߼���
 * @author 21291
 * @date 2014��4��22�� ����10:00:00
 */
public class SettingBusiness extends BaseBusiness<Void> {
	private SettingBusiness(Context context) {
		super(context);
	}
	// ����ģʽ(�̲߳���ȫд��)
	private static SettingBusiness settingBusiness;
	public static SettingBusiness getSettingBusiness(Context context,String serviceUrl) {
		if (settingBusiness == null) {
			settingBusiness = new SettingBusiness(context);
		}
		settingBusiness.setServiceUrl(serviceUrl);
		return settingBusiness;
	}

	/**
	 * @Title: checkVersion
	 * @Description: ���汾����
	 * @param @return
	 * @return Update
	 * @throws
	 * @author 21291
	 * @date 2014��4��22�� ����11:10:27
	 */
	public UpdateInfo checkVersion() throws AppException {
		UpdateInfo updateInfo=null;
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ��ȡ��Ӧ��Ϣ
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.GET);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				if (resultMessage.isIsSuccess()) {
					try {
						updateInfo = gson.fromJson(resultMessage.getResult(), UpdateInfo.class);
					}
					catch (JsonParseException e) {
						e.printStackTrace();			
					}
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "���������ϢΪ:"
						+ responseMessage.getResponseErrorMessage();
			}

		} catch (Exception e) {
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}

		return updateInfo;
	}
	
	/** 
	* @Title: SendLogRecord 
	* @Description: ������־ͳ��ʵ����Ϣ
	* @param @param logInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��31�� ����10:30:19
	*/
	public void SendLogRecord(LogsRecordInfo logInfo){
		try {
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = LogsRecordInfo.ConvertToJson(logInfo);
			apiClient.AddParam("jsonData", jsonData);
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() != HttpStatus.SC_OK) {
				responseErrorMessage = "״̬��Ϊ:"+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
