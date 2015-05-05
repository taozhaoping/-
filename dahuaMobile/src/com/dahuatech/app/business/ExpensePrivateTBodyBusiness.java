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
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyInfo;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.common.DESUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpensePrivateTBodyBusiness
 * @Description ������˽���ݱ���ҵ���߼���
 * @author 21291
 * @date 2014��5��26�� ����2:35:20
 */
public class ExpensePrivateTBodyBusiness extends BaseBusiness<Void> {	
	private List<ExpensePrivateTBodyInfo> arrayList;
	private Type listType;
	
	public ExpensePrivateTBodyBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<ExpensePrivateTBodyInfo>();
		listType = new TypeToken<ArrayList<ExpensePrivateTBodyInfo>>() {}.getType();
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static ExpensePrivateTBodyBusiness expensePrivateTBodyBusiness;	
	public static ExpensePrivateTBodyBusiness getExpensePrivateTBodyBusiness(Context context,String serviceUrl){
		if(expensePrivateTBodyBusiness==null)
		{
			expensePrivateTBodyBusiness=new ExpensePrivateTBodyBusiness(context);
		}
		expensePrivateTBodyBusiness.setServiceUrl(serviceUrl);
		return expensePrivateTBodyBusiness;
	}

	/** 
	* @Title: getExpensePrivateTBodyList 
	* @Description: ��ȡ��˽���ݱ�����ϸ��Ϣ
	* @param @param eBodyParam
	* @param @return     
	* @return List<ExpensePrivateTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014��5��26�� ����5:17:51
	*/
	public List<ExpensePrivateTBodyInfo> getExpensePrivateTBodyList(ExpensePrivateTBodyParam eBodyParam){	
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
