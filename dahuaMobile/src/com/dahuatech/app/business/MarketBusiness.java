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
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketBidInfo;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractInfo;
import com.dahuatech.app.bean.market.MarketProductInfo;
import com.dahuatech.app.bean.market.MarketSearchParamInfo;
import com.dahuatech.app.bean.market.MarketWorkflowInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MarketBusiness extends BaseBusiness<Void> {

	private List<MarketBidInfo> bidList;
	private List<MarketContractInfo> contractList;
	private List<MarketProductInfo> productList;
	private List<MarketWorkflowInfo> workflowList;
	private Type bidListType,contractListType,productListType,workflowListType;
	private Gson gson;
	private ApiClient apiClient;
	
	public MarketBusiness(Context context) {
		super(context);
		bidListType = new TypeToken<ArrayList<MarketBidInfo>>() {}.getType();	
		contractListType = new TypeToken<ArrayList<MarketContractInfo>>() {}.getType();	
		productListType= new TypeToken<ArrayList<MarketProductInfo>>() {}.getType();
		workflowListType=new TypeToken<ArrayList<MarketWorkflowInfo>>() {}.getType();	
		this.gson=GsonHelper.getInstance();
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static MarketBusiness marketBusiness;	
	public static MarketBusiness getMarketBusiness(Context context,String serviceUrl){
		if(marketBusiness==null)
		{
			marketBusiness=new MarketBusiness(context);
		}
		marketBusiness.setServiceUrl(serviceUrl);
		return marketBusiness;
	}
	
	/** 
	* @Title: getMarketBidList 
	* @Description: ��ȡ���۲�ѯ���
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��26�� ����2:52:07
	*/
	public List<MarketBidInfo> getMarketBidList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			bidList=new ArrayList<MarketBidInfo>();  //���ؽ��
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					bidList = gson.fromJson(jsonArray.toString(), bidListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bidList;
	}
	
	/** 
	* @Title: getMarketContractList 
	* @Description: ��ȡ��ͬ��ѯ���
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��26�� ����2:52:38
	*/
	public List<MarketContractInfo> getMarketContractList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			contractList=new ArrayList<MarketContractInfo>();  //���ؽ��
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					contractList = gson.fromJson(jsonArray.toString(), contractListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractList;
	}
	
	/** 
	* @Title: getMarketProductList 
	* @Description: ��ȡ��Ʒ��ѯ���
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketProductInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��30�� ����9:20:42
	*/
	public List<MarketProductInfo> getMarketProductList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			productList=new ArrayList<MarketProductInfo>();  //���ؽ��
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					productList = gson.fromJson(jsonArray.toString(), productListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
	
	/** 
	* @Title: getMarketWorkflowInfo 
	* @Description: ��ȡ����ģ����ڵ�������¼
	* @param @param repository
	* @param @param sqlParameters
	* @param @return     
	* @return List<MarketWorkflowInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����2:27:12
	*/
	public List<MarketWorkflowInfo> getMarketWorkflowInfo(RepositoryInfo repository,SqlParametersInfo sqlParameters){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			workflowList = new ArrayList<MarketWorkflowInfo>();  //���ؽ��
			
			// ��ȡAPIʵ��
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=SqlParametersInfo.ConvertToJson(sqlParameters);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			
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
						workflowList = gson.fromJson(jsonArray.toString(), workflowListType);
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
		return workflowList;
	}

	/** 
	* @Title: getMarketBidHistoryList 
	* @Description: ��ȡ��ʷ���۲�ѯ��¼
	* @param @param mHistory
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����4:54:08
	*/
	public List<MarketBidInfo> getMarketBidHistoryList(List<MarketBidHistoryInfo> mHistory){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			bidList=new ArrayList<MarketBidInfo>();  //���ؽ��
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=MarketBidHistoryInfo.ConvertToJson(mHistory);
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
					bidList = gson.fromJson(jsonArray.toString(), bidListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bidList;
	}
	
	/** 
	* @Title: getMarketContractHistoryList 
	* @Description: ��ȡ��ʷ��ͬ��ѯ��¼
	* @param @param mHistory
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015��1��29�� ����4:56:32
	*/
	public List<MarketContractInfo> getMarketContractHistoryList(List<MarketContractHistoryInfo> mHistory){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			contractList=new ArrayList<MarketContractInfo>();  //���ؽ��
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=MarketContractHistoryInfo.ConvertToJson(mHistory);
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
					contractList = gson.fromJson(jsonArray.toString(), contractListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractList;
	}
}
