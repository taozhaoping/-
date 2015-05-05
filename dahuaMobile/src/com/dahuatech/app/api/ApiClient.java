package com.dahuatech.app.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppException;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.common.LogUtil;
import com.dahuatech.app.common.StringUtils;

public class ApiClient {
	
	private static final String TAG = "APIClient";	
	
	private static String appCookie;
	private static String appUserAgent;
	
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	
	private AppException appException;			//ȫ���쳣��
	private Context context;					//ȫ�������Ļ���
	private String serviceUrl; 					//�����ַ
	private int responseCode;					//��Ӧ״̬��
	private String errorMessage;				//������Ϣ
	private String responseMessage;				//��Ӧ��Ϣ
	private boolean bare; 						//�Ƿ�յĲ���
	private HttpClient httpClient = null;  		//�ͻ�������ʵ��
	private HttpResponse  httpResponse=null; 	//�ͻ�����Ӧʵ��
	
	public AppException getAppException() {
		return appException;
	}

	public void setAppException(AppException appException) {
		this.appException = appException;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	public boolean isBare() {
		return bare;
	}
	
	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public void setParams(ArrayList<NameValuePair> params) {
		this.params = params;
	}

	public ArrayList<NameValuePair> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<NameValuePair> headers) {
		this.headers = headers;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setBare(boolean bare) {
		this.bare = bare;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	//Ĭ�ϳ�ʼ����Ϣ
	private ApiClient(Context context) {
		this.context=context;
		this.appException=null;
		this.serviceUrl="";
		this.responseCode = 0;
		this.responseMessage = "";
		this.errorMessage = "";
		this.bare = false;
	}
	
	//����ģʽ(�̰߳�ȫд��)
	private static ApiClient apiClient; 
	public static ApiClient getApiClient(Context context) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);
		}
		return apiClient;
	}
	
	//��ȡʵ��
	public static ApiClient getApiClient(Context context,String serviceUrl) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);	
		}
		apiClient.setAppException(null);
		apiClient.setResponseCode(0);
		apiClient.setResponseMessage("");
		apiClient.setErrorMessage("");
		apiClient.setBare(false);
		apiClient.setServiceUrl(serviceUrl);
		apiClient.setHeaders(new ArrayList<NameValuePair>());
		apiClient.setParams(new ArrayList<NameValuePair>());
		return apiClient;
	}
	
	//��ȡʵ��
	public static ApiClient getApiClient(Context context,String serviceUrl,boolean bare) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);
		}
		apiClient.setAppException(null);
		apiClient.setResponseCode(0);
		apiClient.setResponseMessage("");
		apiClient.setErrorMessage("");
		apiClient.setServiceUrl(serviceUrl);
		apiClient.setBare(bare);
		apiClient.setHeaders(new ArrayList<NameValuePair>());
		apiClient.setParams(new ArrayList<NameValuePair>());
		return apiClient;
	}
	
	/** 
	* @Title: AddParam 
	* @Description: ��Ӳ���
	* @param @param name
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����5:25:01
	*/
	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	/** 
	* @Title: AddHeader 
	* @Description: ��ӱ���ͷ
	* @param @param name
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����5:25:16
	*/
	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}
	
	/** 
	* @Title: getHttpClient 
	* @Description: ��ȡHttpClientʵ��
	* @param @return     
	* @return HttpClient    
	* @throws 
	* @author 21291
	* @date 2014��4��15�� ����3:59:35
	*/
	private HttpClient getHttpClient() {	
		if(httpClient==null)
		{
			httpClient= new HttpConnectionManager().getHttpClient();
		}
		return httpClient;
	}
	
	/** 
	* @Title: closeHttpClient 
	* @Description: �ر�httpClient����
	* @param @param httpClient     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��15�� ����4:07:11
	*/
	public void closeHttpClient(){
		if(httpClient!=null){
			httpClient.getConnectionManager().shutdown();
			httpClient=null;
		}
	}
	
	/** 
	* @Title: Execute 
	* @Description: ִ��get/put�����װ
	* @param @param method
	* @param @throws Exception     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����5:27:24
	*/
	public void Execute(RequestMethod method) {
		// ��Ӳ�����Ϣ
		String combinedParams = "";
		StringEntity entity = null;
		switch (method) {
			case GET: {
				if (!params.isEmpty()) {
					combinedParams += "/";
					for (NameValuePair p : params) {
						String paramString="";
						try {
							paramString = URLEncoder.encode(p.getValue(),HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
						if (combinedParams.length() > 1)
							combinedParams += "/" + paramString;
						else
							combinedParams += paramString;
					}
				}
				serviceUrl = serviceUrl + combinedParams;	
				HttpGet requestGet = new HttpGet(serviceUrl);
				// ��ӱ���ͷ��Ϣ
				for (NameValuePair h : headers) {
					requestGet.setHeader(h.getName(), h.getValue());
				}
				executeRequest(requestGet);
				break;
			}
			case POST: {
				HttpPost requestPost = new HttpPost(serviceUrl);
				// ��ӱ���ͷ��Ϣ
				for (NameValuePair h : headers) {
					requestPost.setHeader(h.getName(), h.getValue());
				}
				if (!params.isEmpty()) {
					if (bare) {
						try {
							entity = new StringEntity(params.get(0).getValue(),HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
					} else {
						JSONObject jsonObj = new JSONObject();
						combinedParams += "";
						for (NameValuePair p : params) {
							try {
								jsonObj.put(p.getName(), p.getValue());
							} catch (JSONException e) {
								appException= AppException.json(e);
								appException.makeToast(context);
							}
						}
						try {
							entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
					}
					requestPost.setEntity(entity);
				}
				executeRequest(requestPost);
				break;
			}
		}
	}
	
	/** 
	* @Title: executeRequest 
	* @Description: ִ����ض�������
	* @param @param request     
	* @return void    
	 * @throws AppException 
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����5:30:17
	*/
	private void executeRequest(HttpUriRequest request){
		try {
			httpClient = getHttpClient();
			httpResponse = httpClient.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			if (responseCode != HttpStatus.SC_OK) {
				errorMessage = httpResponse.getStatusLine().getReasonPhrase();
				LogUtil.d(TAG, "����״̬��:" + errorMessage + ""+" ���������Ϣ:"+errorMessage);
				appException= AppException.http(responseCode);
				appException.makeToast(context);
			}
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				int length = (int) entity.getContentLength();
				responseMessage = retrieveInputStream(length, new InputStreamReader(entity.getContent(), HTTP.UTF_8));	
				entity.consumeContent();  //�ͷ�����
				LogUtil.d(TAG, "responseMessage��Ӧ��Ϣ:" + responseMessage + "");
			}	
		}catch (IOException e) {
			if(request!=null){
				request.abort();
			}
			closeHttpClient();
			LogUtil.e(TAG, "��������쳣��"+e.getMessage());
			appException= AppException.network(e);
			appException.makeToast(context);
		}
		finally{
			if(request!=null){
				request.abort();
			}
			closeHttpClient();
		}
	}

	/** 
	* @Title: retrieveInputStream 
	* @Description: ���ܴ�WCF�˴��ص����ݣ�ת����String���ͷ���
	* @param @param length ���� 
	* @param @param isr ������
	* @param @return
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����5:30:43
	*/
	private String retrieveInputStream(int length,InputStreamReader isr) {
		if (length < 0)
			length = 10000;
		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			char buffer[] = new char[length];
			int count;
			while ((count = isr.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			LogUtil.e(TAG, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.e(TAG, e.getMessage());
		} finally {
			if(isr!=null)
			{
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/** 
	* @Title: getNetBitmap 
	* @Description: ��ȡ����ͼƬ
	* @param @param url �����ַ
	* @param @return
	* @param @throws AppException     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����5:26:59
	*/
	public Bitmap getNetBitmap(String url) throws AppException {
		HttpUriRequest httpGet = null;
		Bitmap bitmap = null;
		try 
		{
			httpClient = getHttpClient();
			httpGet = new HttpGet(url);
			httpResponse = httpClient.execute(httpGet);
			int statusCode=httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				closeHttpClient();
				throw AppException.http(statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			if(entity!=null)
			{
			   InputStream inStream = entity.getContent();
		       bitmap = BitmapFactory.decodeStream(inStream);
		       inStream.close();
			}     
		} catch (IOException e) { // ���������쳣
			if(httpGet!=null){
				httpGet.abort();
			}		
			closeHttpClient();
			e.printStackTrace();
			LogUtil.e(TAG, "�쳣��Ϣ��"+e.getMessage());
			throw AppException.network(e);
		}
		return bitmap;
	}
	
	/** 
	* @Title: cleanCookie 
	* @Description: ���Cookie��Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��17�� ����4:10:24
	*/
	public static void cleanCookie() {
		appCookie = "";
	}
	
	/** 
	* @Title: getCookie 
	* @Description: ��ȡCookie��Ϣ
	* @param @param appContext
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����5:24:08
	*/
	public static String getCookie(AppContext appContext) {
		if(StringUtils.isEmpty(appCookie)) {
			appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}
	
	/** 
	* @Title: getUserAgent 
	* @Description: ��ȡ����ͻ�����Ϣ
	* @param @param appContext
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����5:24:21
	*/
	public static String getUserAgent(AppContext appContext) {
		if(StringUtils.isEmpty(appUserAgent)) {
			StringBuilder ua = new StringBuilder("OSChina.NET");
			ua.append('/'+appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App�汾
			ua.append("/Android");//�ֻ�ϵͳƽ̨
			ua.append("/"+android.os.Build.VERSION.RELEASE);//�ֻ�ϵͳ�汾
			ua.append("/"+android.os.Build.MODEL); //�ֻ��ͺ�
			ua.append("/"+appContext.getAppId());//�ͻ���Ψһ��ʶ
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}
}
