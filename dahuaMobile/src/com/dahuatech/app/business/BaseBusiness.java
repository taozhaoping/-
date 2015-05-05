package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;

/**
 * @ClassName BaseBusiness
 * @Description ҵ���߼�������
 * @author 21291
 * @date 2014��6��4�� ����1:46:25
 */
public class BaseBusiness<T> {
	protected Gson gson;
	protected Context context;
	protected JSONArray jsonArray;
	protected JSONObject jsonObject;
	protected ResultMessage resultMessage;  
	protected ResponseMessage responseMessage;
	protected String responseErrorMessage;
	protected String serviceUrl;  			//�����ַ
	private T t;				 			//��������
	
	public T getT() {
		return t;
	}
	
	public void setT(T t) {
		this.t = t;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public BaseBusiness(Context context) {
		this.context = context;
		this.gson=GsonHelper.getInstance();
		serviceUrl="";
	}
	
	public BaseBusiness(Context context,T t) {
		this(context);
		this.t=t;
	}
	
	/** 
	* @Title: getEntityInfo 
	* @Description: ��ȡʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return T    
	* @throws 
	* @author 21291
	* @date 2014��7��16�� ����9:53:11
	*/
	@SuppressWarnings("unchecked")
	public T getEntityInfo(TaskParamInfo taskParam){
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = TaskParamInfo.ConvertToJson(taskParam);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
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
					t = (T) gson.fromJson(jsonObject.toString(),t.getClass());
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
