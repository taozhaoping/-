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
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName WorkFlowBusiness
 * @Description ������ҵ���߼���
 * @author 21291
 * @date 2014��4��25�� ����1:40:32
 */
public class WorkFlowBusiness extends BaseBusiness<Void>{
	private List<WorkFlowInfo> arrayList;
	private Type listType;
	private ApiClient apiClient;

	private WorkFlowBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<WorkFlowInfo>();
		listType = new TypeToken<ArrayList<WorkFlowInfo>>() {}.getType();
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static WorkFlowBusiness workFlowBusiness;	
	public static WorkFlowBusiness getWorkFlowBusiness(Context context){
		if(workFlowBusiness==null)
		{
			workFlowBusiness=new WorkFlowBusiness(context);
		}
		return workFlowBusiness;
	}
	
	/** 
	* @Title: getWorkFlowInfo 
	* @Description: ��ȡ���ڵ�������¼
	* @param @param repository
	* @param @param sqlParameters
	* @param @return     
	* @return List<WorkFlowInfo>    
	* @throws 
	* @author 21291
	* @date 2014��4��28�� ����10:00:47
	*/
	public List<WorkFlowInfo> getWorkFlowInfo(RepositoryInfo repository,SqlParametersInfo sqlParameters){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=SqlParametersInfo.ConvertToJson(sqlParameters);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			arrayList = new ArrayList<WorkFlowInfo>();  //���ؽ��
			
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					if(!StringUtils.isEmpty(returnStr)){
						jsonArray = new JSONArray(returnStr);
						arrayList = gson.fromJson(jsonArray.toString(), listType);
					}
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
	
	/** 
	* @Title: approveHandle 
	* @Description: �������� ͨ��/����
	* @param @param repository
	* @param @param params
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��4��28�� ����10:28:07
	*/
	public ResultMessage approveHandle(RepositoryInfo repository,String params) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ʵ��������
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",params);
			// ���󷽷�
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			} else {
				responseErrorMessage = "״̬��Ϊ:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMessage;
	}
}
