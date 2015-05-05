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
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.expense.FlowSearchParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpenseFlowItemBusiness
 * @Description �ҵ���ˮ�ͻ�/��Ŀҵ���߼���
 * @author 21291
 * @date 2014��9��1�� ����6:48:14
 */
public class ExpenseFlowItemBusiness extends BaseBusiness<Void> {

	private List<ExpenseFlowItemInfo> arrayList;
	private Type listType;
	
	private ExpenseFlowItemBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<ExpenseFlowItemInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static ExpenseFlowItemBusiness expenseFlowItemBusiness;	
	public static ExpenseFlowItemBusiness getExpenseFlowItemBusiness(Context context,String serviceUrl){
		if(expenseFlowItemBusiness==null)
		{
			expenseFlowItemBusiness=new ExpenseFlowItemBusiness(context);
		}
		expenseFlowItemBusiness.setServiceUrl(serviceUrl);
		return expenseFlowItemBusiness;
	}
	
	/** 
	* @Title: getExpenseFlowItemList 
	* @Description: ��ȡ�ͻ�/��Ŀ�б���
	* @param @param flowSearchParamInfo
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��1�� ����7:38:23
	*/
	public List<ExpenseFlowItemInfo> getExpenseFlowItemList(FlowSearchParamInfo flowSearchParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList=new ArrayList<ExpenseFlowItemInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=FlowSearchParamInfo.ConvertToJson(flowSearchParamInfo);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonArray = new JSONArray(resultMessage.getResult());
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
