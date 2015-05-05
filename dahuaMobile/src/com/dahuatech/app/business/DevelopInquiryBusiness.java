package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopInquiryTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopInquiryBusiness
 * @Description �з�����ѯ�����뵥��ҵ���߼���
 * @author 21291
 * @date 2014��7��16�� ����9:36:48
 */
public class DevelopInquiryBusiness extends BaseBusiness<DevelopInquiryTHeaderInfo> {
	
	//���ݲ���
	public static final String KEY_FMASTERIALNAME 		= "fMasterialName"; 	//��������
	public static final String KEY_FSUPPLIER 	  		= "fSupplier"; 	  		//��Ӧ��
	public static final String KEY_FMANUFACTURER 	  	= "fManufacturer"; 	  	//������
	public static final String KEY_FOFFER 		  		= "fOffer";		   		//����
	public static final String KEY_FCURRENCY		    = "fCurrency";    		//�ұ�
	public static final String KEY_FORDERQUANTITYFROM   = "fOrderQuantityFrom"; //������(��)
	public static final String KEY_FORDERQUANTITYTO 	= "fOrderQuantityTo";	//������(��)
	public static final String KEY_FUNIT	 			= "fUnit";  			//������λ
	public static final String KEY_FORDERFORWARD	    = "fOrderForward"; 		//������ǰ��
	public static final String KEY_FMINI	 			= "fMini";   			//��С��װ��
	public static final String KEY_FMINIORDER 	 		= "fMiniOrder";   		//��С����
	public static final String KEY_FPAYMENT 	 		= "fPayment";	   		//����
	
	/** 
	* @Name: DevelopInquiryBusiness 
	* @Description:  Ĭ�Ϲ��캯��
	*/
	public DevelopInquiryBusiness(Context context) {
		super(context,DevelopInquiryTHeaderInfo.getDevelopInquiryTHeaderInfo());
	}
	
	// ����ģʽ(�̲߳���ȫд��)
	private static DevelopInquiryBusiness developInquiryBusiness;
	public static DevelopInquiryBusiness getDevelopInquiryBusiness(Context context,String serviceUrl) {
		if (developInquiryBusiness == null) {
			developInquiryBusiness = new DevelopInquiryBusiness(context);
		}
		developInquiryBusiness.setServiceUrl(serviceUrl);
		return developInquiryBusiness;
	}
	
	/** 
	* @Title: getDevelopInquiryTHeaderInfo 
	* @Description: ��ȡ�з�����ѯ�����뵥�ݱ�ͷʵ����Ϣ
	* @param @param taskParam
	* @param @return     
	* @return DevelopInquiryTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014��7��16�� ����9:40:44
	*/
	public DevelopInquiryTHeaderInfo getDevelopInquiryTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}
}
