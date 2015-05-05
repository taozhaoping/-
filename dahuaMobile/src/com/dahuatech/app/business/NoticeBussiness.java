package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;

/**
 * @ClassName MainBusiness
 * @Description ֪ͨ����ҵ���߼���
 * @author 21291
 * @date 2014��5��20�� ����11:20:19
 */
public class NoticeBussiness extends BaseBusiness<Void> {
	private NoticeBussiness(Context context) {
		super(context);
	}

	//����ģʽ(�̲߳���ȫд��)
	private static NoticeBussiness noticeBussiness=null;
	public static NoticeBussiness getNoticeBussiness(Context context,String serviceUrl){
		if(noticeBussiness==null){
			noticeBussiness = new NoticeBussiness(context);	
		}
		noticeBussiness.setServiceUrl(serviceUrl);
		return noticeBussiness;
	}
	
	/** 
	* @Title: getTaskCount 
	* @Description: ��ȡ��½�˴�������������
	* @param @param fItemNumber
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��5��20�� ����11:22:33
	*/
	public String getTaskCount(String fItemNumber) {
		String returnString="0";
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			apiClient.AddParam("jsonData", fItemNumber);
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

}
