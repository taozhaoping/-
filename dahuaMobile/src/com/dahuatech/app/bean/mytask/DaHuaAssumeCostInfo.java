package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DaHuaAssumeCostInfo
 * @Description �󻪳е��������뵥��ʵ��
 * @author 21291
 * @date 2014��7��15�� ����5:16:27
 */
public class DaHuaAssumeCostInfo extends Base {

	private static final long serialVersionUID = 1L;
	private int FID;    				//��������
	private String FBillNo; 			//���ݱ��
	private String FApplyName;  		//������
	private String FApplyDate;				//����ʱ��
	private String FGoodsType;  		//�������
	private String FPiInfo;				//PI��Ϣ
	private String FBusinessName;  		//������Ա
	private String FFreightEstimate;	//�˷�Ԥ��
	private String FCause;				//���ô󻪳е�ԭ��
	
	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}
	
	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFGoodsType() {
		return FGoodsType;
	}

	public void setFGoodsType(String fGoodsType) {
		FGoodsType = fGoodsType;
	}

	public String getFPiInfo() {
		return FPiInfo;
	}

	public void setFPiInfo(String fPiInfo) {
		FPiInfo = fPiInfo;
	}

	public String getFBusinessName() {
		return FBusinessName;
	}

	public void setFBusinessName(String fBusinessName) {
		FBusinessName = fBusinessName;
	}

	public String getFFreightEstimate() {
		return FFreightEstimate;
	}

	public void setFFreightEstimate(String fFreightEstimate) {
		FFreightEstimate = fFreightEstimate;
	}

	public String getFCause() {
		return FCause;
	}

	public void setFCause(String fCause) {
		FCause = fCause;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static DaHuaAssumeCostInfo instance = new DaHuaAssumeCostInfo();  
    }  
	private DaHuaAssumeCostInfo() {}
	public static DaHuaAssumeCostInfo getDaHuaAssumeCostInfo() {
		return singletonHolder.instance;
	}
}
