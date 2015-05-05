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
import com.dahuatech.app.bean.develophour.DHConfirmJsonInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonJsonInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonParamInfo;
import com.dahuatech.app.bean.develophour.DHConfirmParamInfo;
import com.dahuatech.app.bean.develophour.DHDetailInfo;
import com.dahuatech.app.bean.develophour.DHDetailParamInfo;
import com.dahuatech.app.bean.develophour.DHListInfo;
import com.dahuatech.app.bean.develophour.DHListParamInfo;
import com.dahuatech.app.bean.develophour.DHListProjectJsonInfo;
import com.dahuatech.app.bean.develophour.DHListProjectParamInfo;
import com.dahuatech.app.bean.develophour.DHProjectInfo;
import com.dahuatech.app.bean.develophour.DHProjectParamInfo;
import com.dahuatech.app.bean.develophour.DHTypeInfo;
import com.dahuatech.app.bean.develophour.DHUploadConfirmInfo;
import com.dahuatech.app.bean.develophour.DHUploadConfirmPersonInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DevelopHourBusiness
 * @Description �з���ʱҵ���߼���
 * @author 21291
 * @date 2014��10��21�� ����11:02:37
 */
public class DevelopHourBusiness extends BaseBusiness<Void> {
	private List<DHListInfo> dhList;
	private List<DHListProjectJsonInfo> dhListProjectJson;
	private List<DHProjectInfo> dhProjectList;
	private List<DHTypeInfo> dhTypeList;
	private List<DHConfirmJsonInfo> dhConfirmList;
	private List<DHConfirmListPersonJsonInfo>  dhConfirmListPerson;
	
	private DHDetailInfo dhDetailInfo;
	private Type dhListType,dhListProjectJsonType,dhProjectType,dhType,dhConfirmType,dhConfirmListType;

	public void setDhDetailInfo(DHDetailInfo dhDetailInfo) {
		this.dhDetailInfo = dhDetailInfo;
	}

	private DevelopHourBusiness(Context context) {
		super(context);
		dhListType = new TypeToken<ArrayList<DHListInfo>>() {}.getType();
		dhListProjectJsonType = new TypeToken<ArrayList<DHListProjectJsonInfo>>() {}.getType();
		dhProjectType = new TypeToken<ArrayList<DHProjectInfo>>() {}.getType();
		dhType= new TypeToken<ArrayList<DHTypeInfo>>() {}.getType();
		dhConfirmType = new TypeToken<ArrayList<DHConfirmJsonInfo>>() {}.getType();	
		dhConfirmListType=new TypeToken<ArrayList<DHConfirmListPersonJsonInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static DevelopHourBusiness developHourBusiness;	
	public static DevelopHourBusiness getDevelopHourBusiness(Context context,String serviceUrl){
		if(developHourBusiness==null)
		{
			developHourBusiness=new DevelopHourBusiness(context);
		}
		developHourBusiness.setServiceUrl(serviceUrl);
		return developHourBusiness;
	}
	
	/** 
	* @Title: getDHList 
	* @Description: ��ȡ�з���ʱ�б���ʵ�弯��
	* @param @param dhParamInfo
	* @param @return     
	* @return List<DHListInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��23�� ����9:16:01
	*/
	public List<DHListInfo> getDHList(DHListParamInfo dhParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhList=new ArrayList<DHListInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=DHListParamInfo.ConvertToJson(dhParamInfo);
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
					dhList = gson.fromJson(jsonArray.toString(), dhListType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhList;
	}
	
	/** 
	* @Title: getDHListProjectList 
	* @Description: ��ȡ�з���ʱ�б�����Ŀʵ�弯��
	* @param @param dhParamInfo
	* @param @return     
	* @return List<DHListProjectJsonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��27�� ����10:20:12
	*/
	public List<DHListProjectJsonInfo> getDHListProject(DHListProjectParamInfo dhParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhListProjectJson=new ArrayList<DHListProjectJsonInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=DHListProjectParamInfo.ConvertToJson(dhParamInfo);
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
					dhListProjectJson = gson.fromJson(jsonArray.toString(), dhListProjectJsonType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhListProjectJson;
	}
	
	/** 
	* @Title: getDHDetailInfo 
	* @Description: ��ȡ��ʱ����ʵ����Ϣ
	* @param @param dhDetailParamInfo
	* @param @return     
	* @return DHDetailInfo    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����11:21:16
	*/
	public DHDetailInfo getDHDetailInfo(DHDetailParamInfo dhDetailParamInfo){
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=DHDetailParamInfo.ConvertToJson(dhDetailParamInfo);
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
					dhDetailInfo = (DHDetailInfo) gson.fromJson(jsonObject.toString(),dhDetailInfo.getClass());
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhDetailInfo;
	}
	
	/** 
	* @Title: uploadDHDetailInfo 
	* @Description: �������޸Ĺ�ʱ������Ϣ
	* @param @param dhDetailInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����2:01:03
	*/
	public ResultMessage uploadDHDetailInfo(DHDetailInfo dhDetailInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=DHDetailInfo.ConvertToJson(dhDetailInfo);
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
	
	/** 
	* @Title: getDHProject 
	* @Description: ��ȡ�з���ʱ������Ŀ�б���
	* @param @param dhParamInfo
	* @param @return     
	* @return List<DHProjectInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��30�� ����5:10:05
	*/
	public List<DHProjectInfo> getDHProject(DHProjectParamInfo dhParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhProjectList=new ArrayList<DHProjectInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=DHProjectParamInfo.ConvertToJson(dhParamInfo);
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
					dhProjectList = gson.fromJson(jsonArray.toString(), dhProjectType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhProjectList;
	}

	/** 
	* @Title: getDHType 
	* @Description: ��ȡ�з���ʱ���������б���
	* @param @param fProjectCode ��Ŀ����
	* @param @return     
	* @return List<DHTypeInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��18�� ����2:53:05
	*/
	public List<DHTypeInfo> getDHType(final String fProjectCode){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhTypeList=new ArrayList<DHTypeInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fProjectCode,DESUtils.DEFAULT_KEY));
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
					dhTypeList = gson.fromJson(jsonArray.toString(), dhType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhTypeList;
	}
	
	/** 
	* @Title: getDHConfirmList 
	* @Description: ��ȡ�з���ʱȷ���б��չ��ʵ�弯��
	* @param @param dhParamInfo
	* @param @return     
	* @return List<DHConfirmInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��22�� ����5:09:44
	*/
	public List<DHConfirmJsonInfo> getDHConfirmList(DHConfirmParamInfo dhParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhConfirmList=new ArrayList<DHConfirmJsonInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=DHConfirmParamInfo.ConvertToJson(dhParamInfo);
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
					dhConfirmList = gson.fromJson(jsonArray.toString(), dhConfirmType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhConfirmList;
	}
	
	/** 
	* @Title: getDHConfirmListPerson 
	* @Description: ��ȡ�з���ʱȷ���б���Ա��չ��ʵ�弯��
	* @param @param dhParamInfo
	* @param @return     
	* @return List<DHConfirmListPersonJsonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����2:59:24
	*/
	public List<DHConfirmListPersonJsonInfo> getDHConfirmListPerson(DHConfirmListPersonParamInfo dhParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			dhConfirmListPerson=new ArrayList<DHConfirmListPersonJsonInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=DHConfirmListPersonParamInfo.ConvertToJson(dhParamInfo);
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
					dhConfirmListPerson = gson.fromJson(jsonArray.toString(), dhConfirmListType);
				}
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhConfirmListPerson;
	}
	
	/** 
	* @Title: UploadDhConfirmData 
	* @Description: �ϴ��з���ʱ��Աȷ��
	* @param @param dInfo
	* @param @param dPersonList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����11:32:07
	*/
	public ResultMessage UploadDhConfirmData(DHUploadConfirmInfo dInfo,List<DHUploadConfirmPersonInfo> dPersonList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=DHUploadConfirmInfo.ConvertToJson(dInfo);
			String jsonParams=DHUploadConfirmPersonInfo.ConvertToJson(dPersonList);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			jsonParams= DESUtils.toHexString(DESUtils.encrypt(jsonParams,DESUtils.DEFAULT_KEY));
			
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams", jsonParams);
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
