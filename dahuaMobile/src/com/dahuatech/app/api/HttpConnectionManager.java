package com.dahuatech.app.api;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * @ClassName HttpConnectionManager
 * @Description ���ӳع�����
 * @author 21291
 * @date 2014��4��15�� ����5:03:36
 */
public class HttpConnectionManager {

	private DefaultHttpClient defaultHttpClient=null;
	private ClientConnectionManager  connectionManager;
	private static HttpParams httpParams;
	private static SchemeRegistry schemeRegistry;

	// ���������
	public static final int MAX_TOTAL_CONNECTIONS = 2000;

	// ���ȴ�ʱ��
	public static final  int WAIT_TIMEOUT = 60000;

	// ÿ��·�����������
	public static final int MAX_ROUTE_CONNECTIONS = 2000;

	// ���ӳ�ʱʱ��
	public static final  int CONNECT_TIMEOUT = 50000;

	// ��ȡ��ʱʱ��
	public static final  int READ_TIMEOUT = 20000;

	static {
		httpParams = new BasicHttpParams();
		// �������������
		ConnManagerParams.setMaxTotalConnections(httpParams,MAX_TOTAL_CONNECTIONS);
		// ���û�ȡ���ӵ����ȴ�ʱ��
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);	
		// ����ÿ��·�����������  
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);  
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);
		// �������ӳ�ʱʱ�� 
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT); 
		// ���ö�ȡ��ʱʱ��  
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
		// �汾Э��
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);  
		// �����ַ���
		HttpProtocolParams.setContentCharset(httpParams, "UTF_8");  
		
		//���Ӳ��� http-Ĭ�� 8010�˿�  https-Ĭ�� 443�˿�
		schemeRegistry = new SchemeRegistry();  
		schemeRegistry.register(new Scheme("http",PlainSocketFactory.getSocketFactory(),8010));  
		schemeRegistry.register(new Scheme("https",SSLSocketFactory.getSocketFactory(),443));
	}
	
	/** 
	* @Name: HttpConnectionManager 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public HttpConnectionManager(){
		connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry); 
	}
	
	//��ȡ�ͻ�������ʵ��
	public HttpClient getHttpClient() {
		if (defaultHttpClient == null){
			defaultHttpClient=new DefaultHttpClient(connectionManager,httpParams);
		}
		return defaultHttpClient;
	}
}
