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
import com.dahuatech.app.bean.mytask.LowerNodeAppBackInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppConfigInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName LowerNodeAppBusiness
 * @Description �¼��ڵ�������ҵ���߼���
 * @author 21291
 * @date 2014��11��10�� ����4:20:48
 */
public class LowerNodeAppBusiness extends BaseBusiness<Void> {
	private List<LowerNodeAppInfo> arrayList;
	private Type listType;
	private ApiClient apiClient;
	
	public LowerNodeAppBusiness(Context context){
		super(context);
		listType = new TypeToken<ArrayList<LowerNodeAppInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static LowerNodeAppBusiness lowerNodeAppBusiness;	
	public static LowerNodeAppBusiness getLowerNodeAppBusiness(Context context,String serviceUrl){
		if(lowerNodeAppBusiness==null)
		{
			lowerNodeAppBusiness=new LowerNodeAppBusiness(context);
		}
		lowerNodeAppBusiness.setServiceUrl(serviceUrl);
		return lowerNodeAppBusiness;
	}
	
	/** 
	* @Title: getLowerNodeAppInfo 
	* @Description: ��ȡ�¼��ڵ������˽ڵ㼯����Ϣ
	* @param @param repository
	* @param @param params
	* @param @return     
	* @return List<LowerNodeAppInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��10�� ����4:23:37
	*/
	public List<LowerNodeAppInfo> getLowerNodeAppInfo(RepositoryInfo repository,String params){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList = new ArrayList<LowerNodeAppInfo>();
			
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
						arrayList = new ArrayList<LowerNodeAppInfo>();
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
	* @Title: passWorkFlowHandle 
	* @Description: �¼��ڵ���������
	* @param @param lConfigInfo
	* @param @param lBackList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��11��12�� ����5:11:15
	*/
	public ResultMessage passWorkFlowHandle(LowerNodeAppConfigInfo lConfigInfo,List<LowerNodeAppBackInfo> lBackList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ʵ��������
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=LowerNodeAppConfigInfo.ConvertToJson(lConfigInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			String jsonParams=LowerNodeAppBackInfo.ConvertToJson(lBackList);
			jsonParams=DESUtils.toHexString(DESUtils.encrypt(jsonParams,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
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
