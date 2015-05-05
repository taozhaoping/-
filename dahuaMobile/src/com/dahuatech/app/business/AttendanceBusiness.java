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
import com.dahuatech.app.bean.attendance.AdAmapListInfo;
import com.dahuatech.app.bean.attendance.AdCheckInfo;
import com.dahuatech.app.bean.attendance.AdCheckStatusInfo;
import com.dahuatech.app.bean.attendance.AdListInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AttendanceBusiness
 * @Description ����ģ��ҵ���߼���
 * @author 21291
 * @date 2014��12��18�� ����1:57:03
 */
public class AttendanceBusiness extends BaseBusiness<Void>{
	
	private List<AdListInfo> arrayList;
	private Type listType;
	private ApiClient apiClient;
	private AdCheckStatusInfo adCheckStatusInfo;
	private AdAmapListInfo adAmapListInfo;

	private AttendanceBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<AdListInfo>();
		listType = new TypeToken<ArrayList<AdListInfo>>() {}.getType();
		adCheckStatusInfo=AdCheckStatusInfo.getAdCheckStatusInfo();
		adAmapListInfo=AdAmapListInfo.getAdAmapListInfo();
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static AttendanceBusiness attendanceBusiness;	
	public static AttendanceBusiness getAttendanceBusiness(Context context,String serviceUrl){
		if(attendanceBusiness==null)
		{
			attendanceBusiness=new AttendanceBusiness(context);
		}
		attendanceBusiness.setServiceUrl(serviceUrl);
		return attendanceBusiness;
	}
	
	/** 
	* @Title: getCheckStausData 
	* @Description: ��ȡǩ��/ǩ��״̬
	* @param @param fItemNumber
	* @param @return     
	* @return AdCheckStatusInfo    
	* @throws 
	* @author 21291
	* @date 2014��12��30�� ����9:32:31
	*/
	public AdCheckStatusInfo getCheckStausData(String fItemNumber) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ʵ��������
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
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
					adCheckStatusInfo = gson.fromJson(jsonObject.toString(), AdCheckStatusInfo.class);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adCheckStatusInfo;
	}
	
	/** 
	* @Title: getAdList 
	* @Description: ��ȡ������ʷ��¼
	* @param @param fItemNumber
	* @param @return     
	* @return List<AdListInfo>    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:07:42
	*/
	public List<AdListInfo> getAdList(String fItemNumber){		
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList = new ArrayList<AdListInfo>();  //���ؽ��
			
			// ��ȡAPIʵ��
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
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
	* @Title: checkHandle 
	* @Description: ǩ���ǩ�������¼�
	* @param @param checkInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��12��18�� ����2:06:13
	*/
	public ResultMessage checkHandle(AdCheckInfo checkInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ʵ��������
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=AdCheckInfo.ConvertToJson(checkInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
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
	
	/** 
	* @Title: getAmapList 
	* @Description: ��ȡ�򿨵ص㼯��
	* @param @param cacheKey ����汾��
	* @param @return     
	* @return AdAmapListInfo    
	* @throws 
	* @author 21291
	* @date 2015��1��5�� ����10:18:28
	*/
	public AdAmapListInfo getAmapList(String cacheKey) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(cacheKey,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			
			// ��ȡ��Ӧ��Ϣ
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// ��ȡ���ֵ
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// ˵�����سɹ�
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonObject = new JSONObject(resultMessage.getResult());
					adAmapListInfo = gson.fromJson(jsonObject.toString(), AdAmapListInfo.class);
				}
				else{
					adAmapListInfo.setFAmapList("");
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return adAmapListInfo;
	}	
}
