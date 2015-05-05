package com.dahuatech.app.business;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.ContactInfo.ContactResultInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactsBusiness extends BaseBusiness<ContactResultInfo> {
	private ContactResultInfo contactResult;
	private Type listType;
	private Gson gson;
	
	public ContactsBusiness(Context context) {
		super(context);
		contactResult=new ContactInfo.ContactResultInfo();
		listType = new TypeToken<ArrayList<ContactInfo>>() {}.getType();	
		this.gson=GsonHelper.getInstance();
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static ContactsBusiness contactsBusiness;	
	public static ContactsBusiness getContactsBusiness(Context context,String serviceUrl){
		if(contactsBusiness==null)
		{
			contactsBusiness=new ContactsBusiness(context);
		}
		contactsBusiness.setServiceUrl(serviceUrl);
		return contactsBusiness;
	}
	
	/** 
	* @Title: getContactsList 
	* @Description: ���ݲ�ѯ������ȡͨѶ¼��Ϣ
	* @param @param jsonData
	* @param @return     
	* @return ContactResultInfo resultStrֵ��1-��ѯʧ�ܣ���������������  2 -��ѯʧ�ܣ�δ�鵽��Ϣ
	* @throws 
	* @author 21291
	* @date 2014��6��26�� ����6:07:37
	*/
	public ContactResultInfo getContactsList(String jsonData){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
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
					contactResult.contactList=gson.fromJson(jsonArray.toString(), listType);
					contactResult.resultStr="";
				}
				else {
					contactResult.contactList=new ArrayList<ContactInfo>();
					contactResult.resultStr=resultMessage.getResult();
				}		
			} else {
				responseErrorMessage = "״̬��Ϊ:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "���������ϢΪ:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactResult;
	}

}
