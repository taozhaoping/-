package com.dahuatech.app.business;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.bean.mytask.ExpenseSpecialTBodyInfo;
import com.dahuatech.app.common.DESUtils;
import com.google.gson.reflect.TypeToken;

/**
 * 
* @Title: ExpenseSpecialThingBodyBusiness.java 
* @Package com.dahuatech.app.business 
* @Description: �����������񵥾ݱ���ҵ���߼���
* @date 2015��4��7�� ����5:23:03 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class ExpenseSpecialThingBodyBusiness extends BaseBusiness<Void> {
	private List<ExpenseSpecialTBodyInfo> arrayList;
	private Type listType;
	public ExpenseSpecialThingBodyBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<ExpenseSpecialTBodyInfo>();
		listType = new TypeToken<ArrayList<ExpenseSpecialTBodyInfo>>() {}.getType();
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static ExpenseSpecialThingBodyBusiness expenseSpecialThingBodyBusiness;
	public static ExpenseSpecialThingBodyBusiness getExpenseSpecialTBodyBusiness(Context context,String serviceUrl) {
		if(expenseSpecialThingBodyBusiness==null){
			expenseSpecialThingBodyBusiness=new ExpenseSpecialThingBodyBusiness(context);
		}
		expenseSpecialThingBodyBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialThingBodyBusiness;
	}
	
	/** 
	* @Title: getExpenseSpecialTBodyList 
	* @Description: ��ȡ�������������ݱ���ʵ�弯��
	* @param @param eBodyParam
	* @param @return     
	* @return List<ExpensePrivateTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014��6��20�� ����9:56:36
	*/
	public List<ExpenseSpecialTBodyInfo> getExpenseSpecialTBodyList(ExpensePrivateTBodyParam eBodyParam){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=ExpensePrivateTBodyParam.ConvertToJson(eBodyParam);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					jsonArray = new JSONArray(returnStr);
					arrayList = gson.fromJson(jsonArray.toString(), listType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}

}
