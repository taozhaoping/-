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
import com.dahuatech.app.bean.expense.ExpandableInfo;
import com.dahuatech.app.bean.expense.FlowParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpandableBusiness
 * @Description �ҵ���ˮҵ���߼���
 * @author 21291
 * @date 2014��8��27�� ����5:24:31
 */
public class ExpandableBusiness extends BaseBusiness<Void> {

	private List<ExpandableInfo> arrayList;
	private Type listType;
	
	private ExpandableBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<ExpandableInfo>>() {}.getType();	
	}
	
	//����ģʽ(�̲߳���ȫд��)
	private static ExpandableBusiness expandableBusiness;	
	public static ExpandableBusiness getExpandableBusiness(Context context,String serviceUrl){
		if(expandableBusiness==null)
		{
			expandableBusiness=new ExpandableBusiness(context);
		}
		expandableBusiness.setServiceUrl(serviceUrl);
		return expandableBusiness;
	}
	
	/** 
	* @Title: getExpandableList 
	* @Description: ��ȡ�ҵ���ˮ��չ��ʵ�弯��
	* @param @param flowParamInfo
	* @param @return     
	* @return List<ExpandableInfo>    
	* @throws 
	* @author 21291
	* @date 2014��8��27�� ����6:10:36
	*/
	public List<ExpandableInfo> getExpandableList(FlowParamInfo flowParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList=new ArrayList<ExpandableInfo>();  //���ؽ��
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // ��ȡAPIʵ��
			String jsonData=FlowParamInfo.ConvertToJson(flowParamInfo);
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
