package com.dahuatech.app.api;

import android.content.Context;

import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;;

/**
 * @ClassName InvokeApi
 * @Description ����ApiClient
 * @author 21291
 * @date 2014��4��21�� ����10:24:57
 */
public class InvokeApi {
	private static ResponseMessage responseMessage;	
	private static ApiClient apiClient;
	static {
		 apiClient=null;
	}
	public InvokeApi() {}

	/** 
	* @Title: getApiClient 
	* @Description: ��ȡApiClient����
	* @param @param context ��ǰ�����Ļ���
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����10:34:30
	*/
	public static ApiClient getApiClient(Context context) {
		// ʵ��������
		apiClient=ApiClient.getApiClient(context);
		return apiClient;
	}
	
	/** 
	* @Title: getApiClient 
	* @Description: 
	* @param @param serviceUrl
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����10:45:15
	*/
	/** 
	* @Title: getApiClient 
	* @Description: ��ȡApiClient����
	* @param @param context ��ǰ�����Ļ���
	* @param @param serviceUrl �����ַ
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����10:34:59
	*/
	public static ApiClient getApiClient(Context context,String serviceUrl) {
		// ʵ��������
		apiClient=ApiClient.getApiClient(context,serviceUrl);
		apiClient.AddHeader("User-Agent", "Mozilla/5.0");
		apiClient.AddHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		apiClient.AddHeader("Accept", "application/json");
		apiClient.AddHeader("Content-type", "application/json;");
		return apiClient;
	}
	
	/** 
	* @Title: getApiClient 
	* @Description: 
	* @param @param serviceUrl
	* @param @param bare
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����10:45:30
	*/
	/** 
	* @Title: getApiClient 
	* @Description: ��ȡApiClient����
	* @param @param context ��ǰ�����Ļ���
	* @param @param serviceUrl �����ַ
	* @param @param bare �Ƿ�յĲ���
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014��9��24�� ����10:38:11
	*/
	public static ApiClient getApiClient(Context context,String serviceUrl,boolean bare) {
		// ʵ��������
		apiClient=ApiClient.getApiClient(context,serviceUrl,bare);
		apiClient.AddHeader("User-Agent", "Mozilla/5.0");
		apiClient.AddHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		apiClient.AddHeader("Accept", "application/json");
		apiClient.AddHeader("Content-type", "application/json;");
		return apiClient;
	}
	
	/** 
	* @Title: closeHttpClient 
	* @Description: �ر�HttpClient����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��29�� ����1:45:36
	*/
	public static void closeHttpClient(){
		if(apiClient!=null){
			apiClient.closeHttpClient();
		}
	}
	
	/** 
	* @Title: getResponse 
	* @Description: ���ݷ����ַ����ȡ��Ӧ��Ϣ
	* @param @param apiClient
	* @param @param requestMethod
	* @param @return     
	* @return ResponseMessage    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����10:52:32
	*/
	public static ResponseMessage getResponse(ApiClient apiClient,RequestMethod requestMethod){
		try {
			responseMessage= new ResponseMessage();
			apiClient.Execute(requestMethod);
			responseMessage.setResponseCode(apiClient.getResponseCode());
			responseMessage.setResponseMessage(jsonTokener(apiClient.getResponseMessage()));
			responseMessage.setResponseErrorMessage(apiClient.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return responseMessage;		
	}
	
	/** 
	* @Title: jsonTokener 
	* @Description: ȥ��UTF-8��BOMͷ
	* @param @param in
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��21�� ����10:40:26
	*/
	private static String jsonTokener(String in) {
		if (in != null && in.startsWith("\ufeff")) {
			in = in.substring(1);
		}
		return in;
	}

}
