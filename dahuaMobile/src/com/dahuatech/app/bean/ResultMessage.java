package com.dahuatech.app.bean;


/**
 * @ClassName ResultMessage
 * @Description ��WCF�������ݣ�����һ��JSON�����Ϣ����
 * @author 21291
 * @date 2014��4��21�� ����10:02:23
 */
public class ResultMessage extends Base {
    
	//���л�Ψһ��
    private static final long serialVersionUID = 1L;
    private boolean IsSuccess;  //���ر�־
    private String Result;      //���
    public boolean isIsSuccess() {
        return IsSuccess;
    }
    public void setIsSuccess(boolean isSuccess) {
        IsSuccess = isSuccess;
    }
    public String getResult() {
        return Result;
    }
    public void setResult(String result) {
        Result = result;
    }
    
    public ResultMessage(){
        IsSuccess=false;
        Result="";
    }
}
