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
import com.dahuatech.app.bean.mytask.PlusCopyInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName RejectNodeBusiness
 * @Description ���ؽڵ�ҵ���߼���
 * @author 21291
 * @date 2014��7��28�� ����6:03:16
 */
public class RejectNodeBusiness extends BaseBusiness<Void> {
	private List<RejectNodeInfo> arrayList;
	private List<PlusCopyPersonInfo> plusCopyPersonList;
	private Type listType,plusCopyListType;
	private ApiClient apiClient;
	
	public RejectNodeBusiness(Context context){
		super(context);
		listType = new TypeToken<ArrayList<RejectNodeInfo>>() {}.getType();	
		plusCopyListType=new TypeToken<ArrayList<PlusCopyPersonInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static RejectNodeBusiness rejectNodeBusiness;	
	public static RejectNodeBusiness getRejectNodeBusiness(Context context,String serviceUrl){
		if(rejectNodeBusiness==null)
		{
			rejectNodeBusiness=new RejectNodeBusiness(context);
		}
		rejectNodeBusiness.setServiceUrl(serviceUrl);
		return rejectNodeBusiness;
	}
	
	/** 
	* @Title: getRejectNodeInfo 
	* @Description: ��ȡ���ؽڵ���Ϣ
	* @param @param repository
	* @param @param params
	* @param @return     
	* @return List<RejectNodeInfo>    
	* @throws 
	* @author 21291
	* @date 2014��7��29�� ����11:02:36
	*/
	public List<RejectNodeInfo> getRejectNodeInfo(RepositoryInfo repository,String params){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList = new ArrayList<RejectNodeInfo>();
			
			// ��ȡAPIʵ��
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
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					if(!StringUtils.isEmpty(returnStr)){
						jsonArray = new JSONArray(returnStr);
						arrayList = gson.fromJson(jsonArray.toString(), listType);
					}
					else {
						arrayList = new ArrayList<RejectNodeInfo>();
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
	* @Title: getPlusCopyPersonList 
	* @Description: ��ȡ��ǩ/���Ͳ�ѯ��Ա��Ϣ
	* @param @param jsonData ��ѯ����
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��9��25�� ����10:11:29
	*/
	public List<PlusCopyPersonInfo> getPlusCopyPersonList(String jsonData){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			plusCopyPersonList=new ArrayList<PlusCopyPersonInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
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
					jsonArray = new JSONArray(resultMessage.getResult());
					plusCopyPersonList = gson.fromJson(jsonArray.toString(), plusCopyListType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plusCopyPersonList;
	}
	
	/** 
	* @Title: plusCopyApp 
	* @Description: ��ǩ/������������
	* @param @param plusCopyInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��26�� ����10:11:38
	*/
	public ResultMessage plusCopyApp(PlusCopyInfo plusCopyInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=PlusCopyInfo.ConvertToJson(plusCopyInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
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
