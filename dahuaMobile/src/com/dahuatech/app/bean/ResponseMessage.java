package com.dahuatech.app.bean;


/**
 * @ClassName ResponseMessage
 * @Description WCF���ú���Ӧ��Ϣʵ��ֵ
 * @author 21291
 * @date 2014��4��21�� ����10:04:30
 */
public class ResponseMessage extends Base {

	//���л�Ψһ��
    private static final long serialVersionUID = 1L;	
    public ResponseMessage() {
        responseCode=0;
        responseMessage="";
        responseErrorMessage="";
    }
    
    private Integer responseCode;  			//��Ӧ״̬�� 
    private String responseMessage;			//��Ӧʵ��ֵ
    private String responseErrorMessage;	//������Ϣ

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    public String getResponseErrorMessage() {
		return responseErrorMessage;
	}

	public void setResponseErrorMessage(String responseErrorMessage) {
		this.responseErrorMessage = responseErrorMessage;
	}
}
