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
import com.dahuatech.app.bean.StorageParameterInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName TaskListBusiness
 * @Description �����б�ҵ���߼���
 * @author 21291
 * @date 2014��4��23�� ����2:32:56
 */
public class TaskListBusiness extends BaseBusiness<Void> {
	private List<TaskInfo> arrayList;
	private Type listType;
	
	private TaskListBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<TaskInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static TaskListBusiness taskListBusiness;	
	public static TaskListBusiness getTaskListBusiness(Context context,String serviceUrl){
		if(taskListBusiness==null)
		{
			taskListBusiness=new TaskListBusiness(context);
		}
		taskListBusiness.setServiceUrl(serviceUrl);
		return taskListBusiness;
	}
	
	/** 
	* @Title: getTaskList 
	* @Description: ��ȡ�����б�����
	* @param @param repository
	* @param @param storageParameter
	* @param @return     
	* @return List<TaskInfo>    
	* @throws 
	* @author 21291
	* @date 2014��4��23�� ����3:04:15
	*/
	public List<TaskInfo> getTaskList(RepositoryInfo repository,List<StorageParameterInfo> storageParameter){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=StorageParameterInfo.ConvertToJson(storageParameter);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			arrayList=new ArrayList<TaskInfo>();  //���ؽ��
			
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
