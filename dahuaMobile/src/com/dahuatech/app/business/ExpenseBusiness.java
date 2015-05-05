package com.dahuatech.app.business;

import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.GpsInfo;
import com.dahuatech.app.bean.expense.GpsRowIdInfo;
import com.dahuatech.app.common.DESUtils;

/**
 * @ClassName ExpenseBusiness
 * @Description ������ҵ���߼���
 * @author 21291
 * @date 2014��6��4�� ����2:40:50
 */
public class ExpenseBusiness extends BaseBusiness<Void> {
	private ExpenseBusiness(Context context) {
		super(context);
	}

	// ����ģʽ(�̲߳���ȫд��)
	private static ExpenseBusiness expenseBusiness;
	public static ExpenseBusiness getExpenseBusiness(Context context,String serviceUrl) {
		if (expenseBusiness == null) {
			expenseBusiness = new ExpenseBusiness(context);
		}
		expenseBusiness.setServiceUrl(serviceUrl);
		return expenseBusiness;
	}
	
	/** 
	* @Title: upload 
	* @Description: �˳���¼�ϴ���������
	* @param @param gpsInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��5��13�� ����6:12:33
	*/
	public ResultMessage upload(GpsInfo gpsInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=GpsInfo.ConvertToJson(gpsInfo);
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
	* @Title: batchUpload 
	* @Description: �����ϴ��˳���¼
	* @param @param gpsIdInfoList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��5��20�� ����5:01:14
	*/
	public ResultMessage batchUpload(List<GpsRowIdInfo> gpsIdInfoList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=GpsRowIdInfo.ConvertToJson(gpsIdInfoList);
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
	* @Title: flowBatchUpload 
	* @Description: �ҵ���ˮ�����ϴ�
	* @param @param efList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��9��10�� ����3:13:52
	*/
	public ResultMessage flowBatchUpload(List<ExpenseFlowDetailInfo> efList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// ��ȡAPIʵ��
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// ���ò���
			String jsonData=ExpenseFlowDetailInfo.ConvertToJson(efList);
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
