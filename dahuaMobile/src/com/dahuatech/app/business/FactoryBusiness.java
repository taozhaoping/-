package com.dahuatech.app.business;

import android.content.Context;

/**
 * @ClassName FactoryBusiness
 * @Description ҵ���߼��ļ򵥹�����
 * @author 21291
 * @date 2014��6��4�� ����2:27:05
 */
public class FactoryBusiness<T> {
	
	private T t;  //˽�б���
	private Context context;
	private static FactoryBusiness<?> mInstance;
	static{
		mInstance=null;
	}
	
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	private FactoryBusiness(Context context){
		this.context=context;
	}

	//��ȡʵ��
	@SuppressWarnings("rawtypes")
	public static FactoryBusiness<?> getFactoryBusiness(Context context) {
		if(mInstance==null){
			mInstance=new FactoryBusiness(context);
		}
		return mInstance;
	}
	
	/** 
	* @Title: getInstance 
	* @Description: ��ȡҵ���߼�ʵ������
	* @param @param type ����
	* @param @param url  �����ַ
	* @param @return     
	* @return T    
	* @throws 
	* @author 21291
	* @date 2014��7��16�� ����10:50:08
	*/
	@SuppressWarnings("unchecked")
	public T getInstance(String type,String url) {	
		if("NoticeBussiness".equals(type)){   //֪ͨ����ҵ���߼���
			t=(T) NoticeBussiness.getNoticeBussiness(context,url);
		}	
		if("SettingBusiness".equals(type)){   //ϵͳ����ҵ���߼���
			t=(T) SettingBusiness.getSettingBusiness(context,url);
		}
		if("UserLoginBussiness".equals(type)){  //��½��֤ҵ���߼���
			t=(T) UserLoginBussiness.getUserLoginBussiness(context,url);
		}
		if("WorkFlowBusiness".equals(type)){  //������ҵ���߼���
			t=(T) WorkFlowBusiness.getWorkFlowBusiness(context);
		}
		if("RejectNodeBusiness".equals(type)){  //���ؽڵ�ҵ���߼���
			t=(T) RejectNodeBusiness.getRejectNodeBusiness(context,url);
		}
		if("LowerNodeAppBusiness".equals(type)){  //�¼��ڵ�������ҵ���߼���
			t=(T) LowerNodeAppBusiness.getLowerNodeAppBusiness(context,url);
		}
		if("ContactsBusiness".equals(type)){  //ͨѶ¼ҵ���߼���
			t=(T) ContactsBusiness.getContactsBusiness(context,url);
		}
		if("ExpenseBusiness".equals(type)){  //������ҵ���߼��� 
			t=(T) ExpenseBusiness.getExpenseBusiness(context,url);
		}
		if("ExpandableBusiness".equals(type)){ //�ҵ���ˮ���б�ҳҵ���߼���  
			t=(T) ExpandableBusiness.getExpandableBusiness(context,url);
		}	
		if("ExpenseFlowItemBusiness".equals(type)){ //�ҵ���ˮ�ͻ�/��Ŀ�����б�ҳҵ���߼���
			t=(T) ExpenseFlowItemBusiness.getExpenseFlowItemBusiness(context,url);
		}	
		if("MeetingBusiness".equals(type)){ //�ҵĻ���ҵ���߼���
			t=(T) MeetingBusiness.getMeetingBusiness(context,url);
		}	
		if("DevelopHourBusiness".equals(type)){  //�з���ʱҵ���߼��� 
			t=(T) DevelopHourBusiness.getDevelopHourBusiness(context,url);
		}
		if("AttendanceBusiness".equals(type)){  //����ģ��ҵ���߼���
			t=(T) AttendanceBusiness.getAttendanceBusiness(context,url);
		}
		if("MarketBusiness".equals(type)){  //�ҵ�����ģ��ҵ���߼���
			t=(T) MarketBusiness.getMarketBusiness(context,url);
		}
		if("TaskListBusiness".equals(type)){  //�����б�ҵ���߼���
			t=(T) TaskListBusiness.getTaskListBusiness(context,url);
		}
		//���嵥������ҵ��
		if("EngineeringBusiness".equals(type)){  //�����̵���ʵ��ҵ���߼���
			t=(T) EngineeringBusiness.getEngineeringBusiness(context,url);
		}	
		if("ExpensePrivateTBodyBusiness".equals(type)){  //������˽���ݱ���ҵ���߼���
			t=(T) ExpensePrivateTBodyBusiness.getExpensePrivateTBodyBusiness(context,url);
		}
		if("ExpensePrivateTHeaderBusiness".equals(type)){  //������˽���ݱ�ͷҵ���߼���
			t=(T) ExpensePrivateTHeaderBusiness.getExpensePrivateTHeaderBusiness(context,url);
		}
		if("ExpensePublicTHeaderBusiness".equals(type)){  //�Թ�֧����������ҵ���߼���
			t=(T) ExpensePublicTHeaderBusiness.getExpensePublicTHeaderBusiness(context,url);
		}
		if("ExpenseCostTHeaderBusiness".equals(type)){  //�������뱨������ҵ���߼���
			t=(T) ExpenseCostTHeaderBusiness.getExpenseCostTHeaderBusiness(context,url);
		}
		if("ExpenseSpecialTBodyBusiness".equals(type)){  //�����������񵥾ݱ���ҵ���߼���
			t=(T) ExpenseSpecialTBodyBusiness.getExpenseSpecialTBodyBusiness(context,url);
		}
		if("ExpenseSpecialTHeaderBusiness".equals(type)){  //�����������񵥾ݱ�ͷҵ���߼���
			t=(T) ExpenseSpecialTHeaderBusiness.getExpenseSpecialTHeaderBusiness(context,url);
		}
		if("ExpenseMarketPayTHeaderBusiness".equals(type)){  //�����г�֧�����ݱ�ͷҵ���߼���
			t=(T) ExpenseMarketPayTHeaderBusiness.getExpenseMarketPayTHeaderBusiness(context,url);
		}
		if("ExpenseMarketBidTHeaderBusiness".equals(type)){  //�����г�Ͷ�굥�ݱ�ͷҵ���߼���
			t=(T) ExpenseMarketBidTHeaderBusiness.getExpenseMarketBidTHeaderBusiness(context,url);
		}
		if("NetworkPermissionBusiness".equals(type)){  //����Ȩ�޵���ҵ���߼���
			t=(T) NetworkPermissionBusiness.getNetworkPermissionBusiness(context,url);
		}
		if("DevelopTestNetworkBusiness".equals(type)){  //�з���Ŀ��������Ȩ�޵���ҵ���߼���
			t=(T) DevelopTestNetworkBusiness.getDevelopTestNetworkBusiness(context,url);
		}
		if("DaHuaAssumeCostBusiness".equals(type)){  //�󻪳е����õ���ҵ���߼���
			t=(T) DaHuaAssumeCostBusiness.getDaHuaAssumeCostBusiness(context,url);
		}
		if("DevelopInquiryBusiness".equals(type)){  //�з�����ѯ�۵���ҵ���߼���
			t=(T) DevelopInquiryBusiness.getDevelopInquiryBusiness(context,url);
		}
		if("MemRequreBusiness".equals(type)){  //MEM���̵���ҵ���߼���
			t=(T) MemRequreBusiness.getMemRequreBusiness(context,url);
		}
		if("ApplyOverTimeBusiness".equals(type)){  //�Ӱ൥��ҵ���߼���
			t=(T) ApplyOverTimeBusiness.getApplyOverTimeBusiness(context,url);
		}
		if("ExAttendanceBusiness".equals(type)){  //�쳣���ڵ�������ҵ���߼���
			t=(T) ExAttendanceBusiness.getExAttendanceBusiness(context,url);
		}
		if("ApplyDaysOffBusiness".equals(type)){  //��ͨ���ŵ��ݵ���ҵ���߼���
			t=(T) ApplyDaysOffBusiness.getApplyDaysOffBusiness(context,url);
		}
		if("ApplyDaysOffDevelopBusiness".equals(type)){  //�з����ŵ��ݵ���ҵ���߼���
			t=(T) ApplyDaysOffDevelopBusiness.getApplyDaysOffDevelopBusiness(context,url);
		}
		if("DocumentApproveBusiness".equals(type)){  //�ļ�����������ҵ���߼���
			t=(T) DocumentApproveBusiness.getDocumentApproveBusiness(context,url);
		}
		if("NewProductLibBusiness".equals(type)){  //�²�Ʒת�ⵥ��ҵ���߼���
			t=(T) NewProductLibBusiness.getNewProductLibBusiness(context,url);
		}
		if("SvnPermissionBusiness".equals(type)){  //SVNȨ�޵���ҵ���߼���
			t=(T) SvnPermissionBusiness.getSvnPermissionBusiness(context,url);
		}
		if("DevelopTravelBusiness".equals(type)){  //�з�������ǲ����ҵ���߼���
			t=(T) DevelopTravelBusiness.getDevelopTravelBusiness(context,url);
		}
		if("PurchaseStockBusiness".equals(type)){  //�ɹ����ϵ���ҵ���߼���
			t=(T) PurchaseStockBusiness.getPurchaseStockBusiness(context,url);
		}
		if("EmailOpenBusiness".equals(type)){  //���俪ͨ����ҵ���߼���
			t=(T) EmailOpenBusiness.getEmailOpenBusiness(context,url);
		}
		if("FixedAssetsSpecialBusiness".equals(type)){  //�̶��ʲ���������ɹ����󵥾�ҵ���߼���
			t=(T) FixedAssetsSpecialBusiness.getFixedAssetsSpecialBusiness(context,url);
		}
		if("LowConsumableBusiness".equals(type)){  //��ֵ�׺����ϴ��뵥��ҵ���߼���
			t=(T) LowConsumableBusiness.getLowConsumableBusiness(context,url);
		}
		if("TrainComputerBusiness".equals(type)){  //��ѵ���㻯���ҵ���ҵ���߼���
			t=(T) TrainComputerBusiness.getTrainComputerBusiness(context,url);
		}
		if("TravelApprovalBusiness".equals(type)){  //������������ҵ���߼���
			t=(T) TravelApprovalBusiness.getTravelApprovalBusiness(context,url);
		}
		if("DoorPermissionBusiness".equals(type)){  //�Ž�Ȩ�޵���ҵ���߼���
			t=(T) DoorPermissionBusiness.getDoorPermissionBusiness(context,url);
		}
		if("NewProductReworkBusiness".equals(type)){  //�²�Ʒ��������ҵ���߼���
			t=(T) NewProductReworkBusiness.getNewProductReworkBusiness(context,url);
		}
		if("TdBorrowBusiness".equals(type)){  //�����ļ��������뵥��ҵ���߼���
			t=(T) TdBorrowBusiness.getTdBorrowBusiness(context,url);
		}
		if("TdPermissionBusiness".equals(type)){  //TDȨ�����뵥��ҵ���߼���
			t=(T) TdPermissionBusiness.getTdPermissionBusiness(context,url);
		}
		if("ProjectReadBusiness".equals(type)){  //��Ŀ��Ϣ�Ķ�Ȩ�����뵥��ҵ���߼���
			t=(T) ProjectReadBusiness.getProjectReadBusiness(context,url);
		}
		if("FeDestroyBusiness".equals(type)){  //ӡ������ҵ���߼���
			t=(T) FeDestroyBusiness.getFeDestroyBusiness(context,url);
		}
		if("FeEngravingBusiness".equals(type)){  //ӡ������ҵ���߼���
			t=(T) FeEngravingBusiness.getFeEngravingBusiness(context,url);
		}
		if("FeTakeOutBusiness".equals(type)){  //ӡ�����ҵ���߼���
			t=(T) FeTakeOutBusiness.getFeTakeOutBusiness(context,url);
		}
		if("FeTransferBusiness".equals(type)){  //ӡ���ƽ�ҵ���߼���
			t=(T) FeTransferBusiness.getFeTransferBusiness(context,url);
		}
		if("FeUpdateBusiness".equals(type)){  //ӡ������ҵ���߼���
			t=(T) FeUpdateBusiness.getFeUpdateBusiness(context,url);
		}	
		if("ApplyLeaveBusiness".equals(type)){  //�������ҵ���߼���
			t=(T) ApplyLeaveBusiness.getApplyLeaveBusiness(context,url);
		}	
		if("ApplyResumeBusiness".equals(type)){  //��������ҵ���߼���
			t=(T) ApplyResumeBusiness.getApplyResumeBusiness(context,url);
		}	
		if("ContributionAwardBusiness".equals(type)) //���׽������뵥
		{
			t=(T) ContributionAwardBusiness.getContributionAwardBusiness(context, url);
		}
		if("ExpenseSpecialThingHeaderBusiness".equals(type)) //�������ﴦ���ͷ
		{
			t=(T) ExpenseSpecialThingHeaderBusiness.getExpenseSpecialTHeaderBusiness(context, url);
		}
		if("ExpenseSpecialThingBodyBusiness".equals(type)) //�������ﴦ�����
		{
			t=(T) ExpenseSpecialThingBodyBusiness.getExpenseSpecialTBodyBusiness(context, url);
		}
		
		return t;
	}
}
