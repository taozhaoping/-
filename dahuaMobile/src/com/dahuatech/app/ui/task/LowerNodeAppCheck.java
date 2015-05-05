package com.dahuatech.app.ui.task;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.WorkFlowBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;

/**
 * @ClassName LowerNodeAppCheck
 * @Description �Ƿ���Ҫ�¼��ڵ�����������
 * @author 21291
 * @date 2014��11��10�� ����5:04:04
 */
public class LowerNodeAppCheck {
	
	private Context context;				   		//���������Ļ���
	private String serviceUrl;					  	//�����ַ
	private HashMap<String, String> checkHashMap; 	//������Ϣ
	
	private ICheckNextNode iNodeAppCheck;		//���ӿ�
	public ICheckNextNode getiNodeAppCheck() {
		return iNodeAppCheck;
	}

	public void setiNodeAppCheck(ICheckNextNode iNodeAppCheck) {
		this.iNodeAppCheck = iNodeAppCheck;
	}

	private LowerNodeAppCheck() {}
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static LowerNodeAppCheck instance = new LowerNodeAppCheck();  
    }
	private static LowerNodeAppCheck lowerNodeAppCheck=null;
	public static LowerNodeAppCheck getLowerNodeAppCheck(Context context,ICheckNextNode iNodeAppCheck) {
		lowerNodeAppCheck=singletonHolder.instance;
		lowerNodeAppCheck.context=context;
		lowerNodeAppCheck.serviceUrl=AppUrl.URL_API_HOST_ANDROID_NEWWORKFLOWAPPSERVICEURL;
		lowerNodeAppCheck.setiNodeAppCheck(iNodeAppCheck);
		return lowerNodeAppCheck;
	}
	
	/** 
	* @Title: checkStatusAsync 
	* @Description: ��鵥���¼��ڵ��Ƿ���Ҫ����
	* @param @param fSystemId ϵͳID
	* @param @param fClassTypeId ����ID
	* @param @param fBillId ��������ID
	* @param @param fItemNumber Ա����        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��14�� ����11:29:42
	*/
	public void checkStatusAsync(String fSystemId,String fClassTypeId,String fBillId,String fItemNumber){
		String jsonParam=fSystemId+","+fClassTypeId+","+fBillId+","+fItemNumber;
		new WorkFlowHandleAsync().execute(fItemNumber,jsonParam,serviceUrl);
	}
	
	/**
	 * @ClassName WorkFlowHandleAsync
	 * @Description �첽������
	 * @author 21291
	 * @date 2014��11��10�� ����5:31:23
	 */
	public class WorkFlowHandleAsync extends AsyncTask<String, Void, ResultMessage> {

		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			iNodeAppCheck.setCheckResult(result);
		}
		
		@Override
		protected ResultMessage doInBackground(String... params) {		
			return workFlowHandle(params[0],params[1],params[2]);
		}
	}	
	
	/** 
	* @Title: workFlowHandle 
	* @Description: �첽������
	* @param @param fItemNumber Ա����
	* @param @param jsonParam 	����ֵ
	* @param @param serviceUrl  �����ַ
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��11��14�� ����2:06:42
	*/
	private ResultMessage workFlowHandle(String fItemNumber, String jsonParam, String serviceUrl) {
		checkHashMap=ParseXmlService.xmlPullParser(context.getResources().getXml(R.xml.configfile), "LowerNodeAppCheck");
		
		// ����ֵ	
		RepositoryInfo repository=RepositoryInfo.getRepositoryInfo();
		repository.setClassType(checkHashMap.get("ClassType"));
		repository.setIsTest(checkHashMap.get("IsTest"));
		repository.setServiceName(checkHashMap.get("ServiceName"));
		repository.setServiceType(checkHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(checkHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(checkHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(context);
		WorkFlowBusiness workFlowBusiness=(WorkFlowBusiness)factoryBusiness.getInstance("WorkFlowBusiness",""); 	
		workFlowBusiness.setServiceUrl(serviceUrl);
		return workFlowBusiness.approveHandle(repository, jsonParam);
	}
	
	/** 
	* @Title: showNextNode 
	* @Description: ��ʾ�¼��ڵ�
	* @param @param resultMessage ���ֵ
	* @param @param nextNode �¼��ڵ㰴ť
	* @param @param context ��ǰ�����Ļ���
	* @param @param fSystemId ϵͳID
	* @param @param fClassTypeId ����ID
	* @param @param fBillId ����ID 
	* @param @param fItemNumber Ա����
	* @param @param fBillName ��������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��14�� ����2:33:10
	*/
	public void showNextNode(ResultMessage resultMessage,Button nextNode,final Context context, final String fSystemId,final String fClassTypeId,final String fBillId,final String fItemNumber,final String fBillName){
		if(resultMessage.isIsSuccess()){  //˵��������
			nextNode.setBackgroundResource(R.drawable.imgbtn_blue);
			nextNode.setOnClickListener(new OnClickListener() { //˵��������
				
				@Override
				public void onClick(View v) {
					UIHelper.showLowerNodeApp(context,fSystemId,fClassTypeId,fBillId,fItemNumber,fBillName);
				}
			});
		}
		else{ //˵��û���� 
			nextNode.setEnabled(false);
		}
	}
}
