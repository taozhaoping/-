package com.dahuatech.app.bean.expense;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName ExpenseFlowDetailInfo
 * @Description ��ˮ��ϸʵ����Ϣ
 * @author 21291
 * @date 2014��9��4�� ����8:14:36
 */
public class ExpenseFlowDetailInfo extends Base {
	private static final long serialVersionUID = 1L;

	private int FLocalId; // ������������
	private int FServerId; // ��������������
	private String FPayType;    // ֧������  ֵΪ�����ÿ�����Я�̡����������޸�����ʱ�䣬�ص㣬���
	private String FItemNumber; // Ա������
	private String FExpendTime; // ����ʱ��
	private String FExpendTypeParent; // �������͸���
	private String FExpendTypeChild; // ������������
	private String FExpendAddress; // ���ѵص�
	private String FExpendAmount; // ���ѽ��
	private String FCause; // ����
	private String FClientId; // �ͻ�ID
	private String FClient; // �ͻ�
	private String FProjectId; // ��ĿID
	private String FProject; // ��Ŀ
	private String FAccompany; // ������ͬ
	private String FAccompanyReason; // ����ͬԭ�����ͬ��Ա  1-��������ͬ��Ա 0��2-��������ͬ��Ա
	private String FStart; // ������
	private String FDestination; // Ŀ�ĵ�
	private String FBusinessLevel; // �����
	private String FStartTime; // ����ʱ��
	private String FEndTime; // ����ʱ��
	private String FReason; // δˢ��ԭ��
	private String FDescription; // ˵��
	private String FUploadFlag; // ���ػ����ϴ���־ 0-������  1-�������ϴ���������Դ���
	
	public int getFLocalId() {
		return FLocalId;
	}

	public void setFLocalId(int fLocalId) {
		FLocalId = fLocalId;
	}

	public int getFServerId() {
		return FServerId;
	}

	public void setFServerId(int fServerId) {
		FServerId = fServerId;
	}
    
	public String getFPayType() {
		return FPayType;
	}

	public void setFPayType(String fPayType) {
		FPayType = fPayType;
	}

	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFExpendTime() {
		return FExpendTime;
	}

	public void setFExpendTime(String fExpendTime) {
		FExpendTime = fExpendTime;
	}

	public String getFExpendTypeParent() {
		return FExpendTypeParent;
	}

	public void setFExpendTypeParent(String fExpendTypeParent) {
		FExpendTypeParent = fExpendTypeParent;
	}

	public String getFExpendTypeChild() {
		return FExpendTypeChild;
	}

	public void setFExpendTypeChild(String fExpendTypeChild) {
		FExpendTypeChild = fExpendTypeChild;
	}

	public String getFExpendAddress() {
		return FExpendAddress;
	}

	public void setFExpendAddress(String fExpendAddress) {
		FExpendAddress = fExpendAddress;
	}

	public String getFExpendAmount() {
		if(StringUtils.isEmpty(FExpendAmount))
			return "";				
		double d1=StringUtils.toDouble(FExpendAmount, 0);
		if(d1!=0){
			FExpendAmount= StringUtils.toDoubleKeepTwo(d1);
		}	
		return FExpendAmount;		
	}

	public void setFExpendAmount(String fExpendAmount) {
		FExpendAmount = fExpendAmount;
	}

	public String getFCause() {
		return FCause;
	}

	public void setFCause(String fCause) {
		FCause = fCause;
	}

	public String getFClientId() {
		return FClientId;
	}

	public void setFClientId(String fClientId) {
		FClientId = fClientId;
	}

	public String getFClient() {
		return FClient;
	}

	public void setFClient(String fClient) {
		FClient = fClient;
	}

	public String getFProjectId() {
		return FProjectId;
	}

	public void setFProjectId(String fProjectId) {
		FProjectId = fProjectId;
	}

	public String getFProject() {
		return FProject;
	}

	public void setFProject(String fProject) {
		FProject = fProject;
	}

	public String getFAccompany() {
		return FAccompany;
	}

	public void setFAccompany(String fAccompany) {
		FAccompany = fAccompany;
	}

	public String getFAccompanyReason() {
		return FAccompanyReason;
	}

	public void setFAccompanyReason(String fAccompanyReason) {
		FAccompanyReason = fAccompanyReason;
	}

	public String getFStart() {
		return FStart;
	}

	public void setFStart(String fStart) {
		FStart = fStart;
	}

	public String getFDestination() {
		return FDestination;
	}

	public void setFDestination(String fDestination) {
		FDestination = fDestination;
	}

	public String getFBusinessLevel() {
		return FBusinessLevel;
	}

	public void setFBusinessLevel(String fBusinessLevel) {
		FBusinessLevel = fBusinessLevel;
	}

	public String getFStartTime() {
		return FStartTime;
	}

	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}

	public String getFDescription() {
		return FDescription;
	}

	public void setFDescription(String fDescription) {
		FDescription = fDescription;
	}

	public String getFUploadFlag() {
		return FUploadFlag;
	}

	public void setFUploadFlag(String fUploadFlag) {
		FUploadFlag = fUploadFlag;
	}
	
	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static ExpenseFlowDetailInfo instance = new ExpenseFlowDetailInfo();  
    }
	public ExpenseFlowDetailInfo() {}
	public static ExpenseFlowDetailInfo getExpenseFlowDetailInfo() {
		return singletonHolder.instance;
	}

	// ��Listת����json�Ա����������д���
	public static String ConvertToJson(List<ExpenseFlowDetailInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (ExpenseFlowDetailInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FLocalId", item.FLocalId);
					jsonObject.put("FServerId", item.FServerId);
					jsonObject.put("FPayType", item.FPayType);
					jsonObject.put("FItemNumber", item.FItemNumber);
					jsonObject.put("FExpendTime", item.FExpendTime);
					jsonObject.put("FExpendTypeParent", item.FExpendTypeParent);
					jsonObject.put("FExpendTypeChild", item.FExpendTypeChild);
					jsonObject.put("FExpendAddress", item.FExpendAddress);
					jsonObject.put("FExpendAmount", item.FExpendAmount);
					jsonObject.put("FCause", item.FCause);
					jsonObject.put("FClientId", item.FClientId);
					jsonObject.put("FClient", item.FClient);
					jsonObject.put("FProjectId", item.FProjectId);
					jsonObject.put("FProject", item.FProject);
					jsonObject.put("FAccompany", item.FAccompany);
					jsonObject.put("FAccompanyReason", item.FAccompanyReason);
					jsonObject.put("FStart", item.FStart);
					jsonObject.put("FDestination", item.FDestination);
					jsonObject.put("FBusinessLevel", item.FBusinessLevel);
					jsonObject.put("FStartTime", item.FStartTime);
					jsonObject.put("FEndTime", item.FEndTime);
					jsonObject.put("FReason", item.FReason);
					jsonObject.put("FDescription", item.FDescription);
					jsonObject.put("FUploadFlag", item.FUploadFlag);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
