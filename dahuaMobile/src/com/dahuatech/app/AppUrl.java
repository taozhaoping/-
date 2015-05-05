package com.dahuatech.app;

/**
 * @ClassName AppUrl
 * @Description URL��ַ����
 * @author 21291
 * @date 2014��8��8�� ����10:16:23
 */
public class AppUrl {

	public final static String HOST = "m.dahuatech.com:8010";//10.18.106.83:8010 
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	private final static String URL_SPLITTER = "/";
	private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	private final static String ANDROID_SERVICE="GetAndroidDataService.svc";
	private final static String URL_API_HOST_ANDROID = URL_API_HOST+ANDROID_SERVICE+URL_SPLITTER;
	
	//LYNC2010���ص�ַ
	public final static String URL_API_HOST_ANDROID_LYNC_DOWNLOAD = URL_API_HOST+URL_SPLITTER+"Lync2010.apk";
	
	//�°�-��־ͳ�Ʒ����ַLogsRecord
	public final static String URL_API_HOST_ANDROID_LOGSRECORD = URL_API_HOST_ANDROID+"NewSetLogsRecord";
	
	//LoginActivity �û���½
	public final static String URL_API_HOST_ANDROID_LOGINACTIVITY = URL_API_HOST_ANDROID+"LoginVerify";
	
	//LoginLockActivity ���������û���֤
	public final static String URL_API_HOST_ANDROID_VERIFYVALIDACTIVITY = URL_API_HOST_ANDROID+"VerifyValid";
	
	//NoticeService ֪ͨ����
	public final static String URL_API_HOST_ANDROID_NOTICESERVICE = URL_API_HOST_ANDROID+"GetLoginTaskCount";
	
	//SettingActivity ������Ϣ
	public final static String URL_API_HOST_ANDROID_SETTINGACTIVITY = URL_API_HOST_ANDROID+"SoftCheckUpdate";
	
	//TaskListActivity �ҵ������б�
	public final static String URL_API_HOST_ANDROID_TASKLISTACTIVITY = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//EngineeringActivity �����̵���
	public final static String URL_API_HOST_ANDROID_ENGINEERINGACTIVITY = URL_API_HOST_ANDROID+"GetEngineeringData";
	
	//ExpensePrivateTHeaderActivity ������˽���ݱ�ͷ
	public final static String URL_API_HOST_ANDROID_EXPENSEPRIVATETHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpensePrivateTHeaderData";
	
	//ExpensePrivateTBodyActivity ������˽���ݱ���
	public final static String URL_API_HOST_ANDROID_EXPENSEPRIVATETBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpensePrivateTBodyData";
	
	//ExpensePublicTHeaderActivity �����Թ�����
	public final static String URL_API_HOST_ANDROID_EXPENSEPUBLICTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpensePublicTHeaderData";
	
	//ExpenseCostTHeaderActivity �����������뵥
	public final static String URL_API_HOST_ANDROID_EXPENSECOSTTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseCostTHeaderData";
	
	//ExpenseSpecialTHeaderActivity �����������񵥾ݱ�ͷ
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialTHeaderData";
	
	//ExpenseSpecialTBodyActivity �����������񵥾ݱ���
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialTBodyData";
	
	//ExpenseSpecialThingActivity �����������񵥾ݱ�ͷ
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHINGHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialAuditTHeaderData";
		
	//ExpenseSpecialThingActivity �����������񵥾ݱ���
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHINGBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialAuditTBodyData";
		
	//ExpenseMarketPayTHeaderActivity �����г�Ͷ��֧������
	public final static String URL_API_HOST_ANDROID_EXPENSEMARKETPAYTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseMarketPayTHeaderData";
	
	//ExpenseMarketBidTHeaderActivity �����г�Ͷ�걨������
	public final static String URL_API_HOST_ANDROID_EXPENSEMARKETBIDTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseMarketBidTHeaderData";
	
	//NetworkPermissionActivity ����Ȩ�����뵥��
	public final static String URL_API_HOST_ANDROID_NETWORKPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetNetworkPermissionData";
	
	//DevelopTestNetworkActivity �з���Ŀ����Ȩ�����뵥��
	public final static String URL_API_HOST_ANDROID_DEVELOPTESTNETWORKACTIVITY = URL_API_HOST_ANDROID+"GetDevelopTestNetworkData";
	
	//DaHuaAssumeCostActivity �󻪳е��������뵥��
	public final static String URL_API_HOST_ANDROID_DAHUAASSUMECOSTACTIVITY = URL_API_HOST_ANDROID+"GetDaHuaAssumeCostData";
	
	//DevelopInquiryActivity �з�����ѯ�����뵥��
	public final static String URL_API_HOST_ANDROID_DEVELOPINQUIRYACTIVITY = URL_API_HOST_ANDROID+"GetDevelopInquiryData";
	
	//MemRequreActivity MEM�������뵥��
	public final static String URL_API_HOST_ANDROID_MEMREQUREACTIVITY = URL_API_HOST_ANDROID+"GetMemRequreData";
	
	//ApplyOverTimeActivity �Ӱ����뵥��
	public final static String URL_API_HOST_ANDROID_APPLYOVERTIMEACTIVITY = URL_API_HOST_ANDROID+"GetApplyOverTimeData";
	
	//ApplyLeaveActivity ������뵥��
	public final static String URL_API_HOST_ANDROID_APPLYLEAVEACTIVITY = URL_API_HOST_ANDROID+"GetApplyLeaveData";
	
	//ApplyResumeActivity �������뵥��
	public final static String URL_API_HOST_ANDROID_APPLYRESUMEACTIVITY = URL_API_HOST_ANDROID+"GetApplyResumeData";
	
	//ExAttendanceActivity �쳣���ڵ������뵥��
	public final static String URL_API_HOST_ANDROID_EXATTENDANCEACTIVITY = URL_API_HOST_ANDROID+"GetExAttendanceData";
	
	//ApplyDaysOffActivity ��ͨ���ŵ������뵥��
	public final static String URL_API_HOST_ANDROID_APPLYDAYSOFFACTIVITY = URL_API_HOST_ANDROID+"GetApplyDaysOffData";
	
	//ApplyDaysOffDevelopActivity �з����ŵ������뵥��
	public final static String URL_API_HOST_ANDROID_APPLYDAYSOFFDEVELOPACTIVITY = URL_API_HOST_ANDROID+"GetApplyDaysOffDevelopData";
	
	//DocumentApproveActivity �ļ����������뵥��
	public final static String URL_API_HOST_ANDROID_DOCUMENTAPPROVEACTIVITY = URL_API_HOST_ANDROID+"GetDocumentApproveData";
	
	//NewProductLibActivity �²�Ʒת�����뵥��
	public final static String URL_API_HOST_ANDROID_NEWPRODUCTLIBACTIVITY = URL_API_HOST_ANDROID+"GetNewProductLibData";
	
	//SvnPermissionActivity SVNȨ�����뵥��
	public final static String URL_API_HOST_ANDROID_SVNPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetSvnPermissionData";
	
	//DevelopTravelActivity �з�������ǲ���뵥��
    public final static String URL_API_HOST_ANDROID_DEVELOPTRAVELACTIVITY = URL_API_HOST_ANDROID+"GetDevelopTravelData";
    
    //PurchaseStockActivity �ɹ��������뵥��
    public final static String URL_API_HOST_ANDROID_PURCHASESTOCKACTIVITY = URL_API_HOST_ANDROID+"GetPurchaseStockData";
    
	//EmailOpenActivity ���俪ͨ���뵥��
    public final static String URL_API_HOST_ANDROID_EMAILOPENACTIVITY = URL_API_HOST_ANDROID+"GetEmailOpenData";
    
    //FixedAssetsSpecialActivity �̶��ʲ���������ɹ����󵥾�
    public final static String URL_API_HOST_ANDROID_FIXEDASSETSSPECIALACTIVITY = URL_API_HOST_ANDROID+"GetFixedAssetsSpecialData";
    
    //LowConsumableActivity ��ֵ�׺����ϴ��뵥��
    public final static String URL_API_HOST_ANDROID_LOWCONSUMABLEACTIVITY = URL_API_HOST_ANDROID+"GetLowConsumableData";
    
    //TrainComputerActivity ��ѵ���㻯���ҵ���
    public final static String URL_API_HOST_ANDROID_TRAINCOMPUTERACTIVITY = URL_API_HOST_ANDROID+"GetTrainComputerData";
    
    //TravelApprovalActivity ������������
    public final static String URL_API_HOST_ANDROID_TRAVELAPPROVALACTIVITY = URL_API_HOST_ANDROID+"GetTravelApprovalData";
    
    //DoorPermissionActivity �Ž�Ȩ�޵���
    public final static String URL_API_HOST_ANDROID_DOORPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetDoorPermissionData";
	
    //NewProductReworkActivity �²�Ʒ�������뵥��
  	public final static String URL_API_HOST_ANDROID_NEWPRODUCTREWORKACTIVITY = URL_API_HOST_ANDROID+"GetNewProductReworkData";
  	
  	//TdBorrowActivity �����ļ��������뵥��
  	public final static String URL_API_HOST_ANDROID_TDBORROWACTIVITY = URL_API_HOST_ANDROID+"GetTdBorrowData";
  	
	//TdPermissionActivity TDȨ�����뵥��
  	public final static String URL_API_HOST_ANDROID_TDPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetTdPermissionData";
  	
	//ProjectReadActivity ��Ŀ�Ķ����뵥��
  	public final static String URL_API_HOST_ANDROID_PROJECTREADACTIVITY = URL_API_HOST_ANDROID+"GetProjectReadData";
  	
  	//FeDestroyActivity ӡ���������뵥��
  	public final static String URL_API_HOST_ANDROID_FEDESTROYACTIVITY = URL_API_HOST_ANDROID+"GetFeDestroyData";
  	
 	//FeEngravingActivity ӡ���������뵥��
  	public final static String URL_API_HOST_ANDROID_FEENGRAVINGACTIVITY = URL_API_HOST_ANDROID+"GetFeEngravingData";
    
  	//FeTakeOutActivity ӡ��������뵥��
  	public final static String URL_API_HOST_ANDROID_FETAKEOUTACTIVITY = URL_API_HOST_ANDROID+"GetFeTakeOutData";
  	
  	//FeTransferActivity ӡ���ƽ����뵥��
  	public final static String URL_API_HOST_ANDROID_FETRANSFERACTIVITY = URL_API_HOST_ANDROID+"GetFeTransferData";
  	
  	//FeUpdateActivity ӡ���������뵥��
  	public final static String URL_API_HOST_ANDROID_FEUPDATEACTIVITY = URL_API_HOST_ANDROID+"GetFeUpdateData";
  	
	//WorkFlowActivity ��������ϸ��Ϣ
	public final static String URL_API_HOST_ANDROID_WORKFLOWACTIVITY = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//OldWorkFlowAppServiceUrl �ɰ�-���������������ַ
	public final static String URL_API_HOST_ANDROID_OLDWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//NewWorkFlowAppServiceUrl �°�-���������������ַ
	public final static String URL_API_HOST_ANDROID_NEWWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"WorkFlowApp";
	
	//HrWorkFlowAppServiceUrl HR��-���������������ַ
	public final static String URL_API_HOST_ANDROID_HRWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"HrWorkFlowApp";
	
	//EpWorkFlowAppServiceUrl ������-���������������ַ
	public final static String URL_API_HOST_ANDROID_EPWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"EpWorkFlowApp";
	
	//RejectNodeRepository �°�-���ؽڵ���ǩ/���͸��ӽڵ�������Ϣ
	public final static String URL_API_HOST_ANDROID_REJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetRejectNodeData";
	
	//HrRejectNodeRepository HR��-���ؽڵ���ǩ/���͸��ӽڵ�������Ϣ
	public final static String URL_API_HOST_ANDROID_HRREJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetHrRejectNodeData";
	
	//EpRejectNodeRepository ������-���ؽڵ���ǩ/���͸��ӽڵ�������Ϣ
	public final static String URL_API_HOST_ANDROID_EPREJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetEpRejectNodeData";
	
	//PlusCopyActivity �°�-��ǩ/�������������ַ
    public final static String URL_API_HOST_ANDROID_PLUSCOPYAPPURL = URL_API_HOST_ANDROID+"GetPlusCopyAppData";
    
    //PlusCopyActivity �°�-��ǩ/���Ͳ�ѯ��Ա��ַ
    public final static String URL_API_HOST_ANDROID_PLUSCOPYPERSONURL = URL_API_HOST_ANDROID+"GetPlusCopyPersonData";
    
    //LowerNodeApproveActivity �°�-�¼��ڵ������˷����ַ
    public final static String URL_API_HOST_ANDROID_LOWERNODEAPPROVEURL = URL_API_HOST_ANDROID+"GetLowerNodeAppStatus";
    
    //LowerNodeApproveActivity �°�-�¼��ڵ���������
    public final static String URL_API_HOST_ANDROID_PASSLOWERNODEHANDLEURL = URL_API_HOST_ANDROID+"PassLowerNodeHandle";
	
	//ExpenseMainActivity �˳���¼�ϴ�����
	public final static String URL_API_HOST_ANDROID_EXPENSEMAINACTIVITY = URL_API_HOST_ANDROID+"ExpenseTaxi";
	
	//ExpenseTaxiListActivity �˳���¼�б������ϴ�����
	public final static String URL_API_HOST_ANDROID_EXPENSETAXILISTACTIVITY = URL_API_HOST_ANDROID+"ExpenseTaxiBatch";
	
	//ContactsMainActivity ͨѶ¼�б���Ϣ
	public final static String URL_API_HOST_ANDROID_CONTACTSMAINACTIVITY = URL_API_HOST_ANDROID+"GetContactsData";
	
	//ExpenseFlowListActivity ��ȡ�ҵ���ˮ�б��������ַ
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWLISTACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowListData";
	
	//ExpenseClientSearchListActivity/ExpenseProjectSearchListActivity ��ȡ�ҵ���ˮ�ͻ�/��Ŀ�б��������ַ
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWSEARCHACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowSearchData";
	
	//ExpenseFlowLocalListActivity/ExpenseFlowDetailActivity ������ˮ��¼�б�/��ˮ�����¼ �ϴ���������ַ
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWUPLOADACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowUploadData";

	//MeetingListActivity ��ȡ�ҵĻ����б�
	public final static String URL_API_HOST_ANDROID_MEETINGLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingListData";
	
	//MeetingListActivity ȡ������
    public final static String URL_API_HOST_ANDROID_CANCLEMEETINGDATA = URL_API_HOST_ANDROID+"CancleMeetingData";
	
	//MeetingDetailActivity ��ȡ�ҵĻ�������
	public final static String URL_API_HOST_ANDROID_MEETINGDETAILACTIVITY = URL_API_HOST_ANDROID+"GetMeetingDetailData";
	
	//MeetingDetailActivity ��ȡ�ҵĻ���Ĭ�ϳ�ʼ����Ϣ
    public final static String URL_API_HOST_ANDROID_MEETINGINITACTIVITY = URL_API_HOST_ANDROID+"GetMeetingInitData";
	
	//MeetingDetailActivity �ϴ�����������Ϣ��������
    public final static String URL_API_HOST_ANDROID_UPLOADMEETINGDETAIL = URL_API_HOST_ANDROID+"UploadMeetingDetailData";
	
	//MeetingPersonListActivity ��ȡ��Ա�����б�
	public final static String URL_API_HOST_ANDROID_MEETINGPERSONLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingPersonListData";
	
	//MeetingRoomListActivity ��ȡ�����������б�
	public final static String URL_API_HOST_ANDROID_MEETINGROOMLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingRoomListData";
	
	//MeetingRoomListActivity ��ȡ���˻�������ʷ��¼�б�
	public final static String URL_API_HOST_ANDROID_INITMEETINGROOMLISTACTIVITY = URL_API_HOST_ANDROID+"GetInitMeetingRoomListData";
	
	//DHListActivity ��ȡ�з���ʱ�б�
    public final static String URL_API_HOST_ANDROID_DHLISTACTIVITY = URL_API_HOST_ANDROID+"GetDhListData";
    
    //DHListProjectActivity ��ȡ�з���ʱ������Ŀ�б�
    public final static String URL_API_HOST_ANDROID_DHLISTPROJECTACTIVITY = URL_API_HOST_ANDROID+"GetDhListProjectData";
   
    //DHDetailActivity ��ȡ�з���ʱ������Ϣ
    public final static String URL_API_HOST_ANDROID_DHDETAILACTIVITY = URL_API_HOST_ANDROID+"GetDhDetailData";
    
	//DHDetailActivity �ϴ��з���ʱ������Ϣ
    public final static String URL_API_HOST_ANDROID_UPLOADDHDETAILACTIVITY = URL_API_HOST_ANDROID+"UploadDhDetailData";
    
    //DHProjectSearchActivity ��ȡ�з���ʱ��Ŀ�����б�
    public final static String URL_API_HOST_ANDROID_DHPROJECTSEARCHACTIVITY = URL_API_HOST_ANDROID+"GetDhProjectSearchData";
   
    //DHTypeListActivity ��ȡ�з���ʱ���������б�
    public final static String URL_API_HOST_ANDROID_DHTYPELISTACTIVITY = URL_API_HOST_ANDROID+"GetDhTypeListData";
    
    //DHConfirmListActivity ��ȡ�з���ʱȷ���б�
    public final static String URL_API_HOST_ANDROID_DHCONFIRMLISTACTIVITY = URL_API_HOST_ANDROID+"GetDhConfirmListData";
    
    //DHConfirmListActivity ��ȡ�з���ʱ��Աȷ��
    public final static String URL_API_HOST_ANDROID_UPLOADDHCONFIRMACTIVITY = URL_API_HOST_ANDROID+"UploadDhConfirmData";
    
    //DHConfirmListPersonActivity ��ȡ�з���ʱȷ���б������Ա��Ϣ
    public final static String URL_API_HOST_ANDROID_DHCONFIRMLISTPERSONACTIVITY = URL_API_HOST_ANDROID+"GetDhConfirmListPersonData";
    
    //AdListActivity ��ȡ����ģ�鿼�ڼ�¼
    public final static String URL_API_HOST_ANDROID_ATTENDANCELISTACTIVITY = URL_API_HOST_ANDROID+"GetAttendanceListData";
    
    //AdCheckInActivity ��ȡ�Ƿ��Ѿ�ǩ���ǩ��
    public final static String URL_API_HOST_ANDROID_GETCHECKSTATUSACTIVITY = URL_API_HOST_ANDROID+"GetCheckStatusData";
    
    //AdCheckInActivity ȷ��ǩ��/ǩ��
    public final static String URL_API_HOST_ANDROID_UPLOADCHECKACTIVITY = URL_API_HOST_ANDROID+"UploadCheckData";
    
    //MainActivity �����ĵص�����ַ
    public final static String URL_API_HOST_ANDROID_GETNEWAMAPLISTACTIVITY = URL_API_HOST_ANDROID+"GetNewAmapListData";
    
    //MarketBidSearchActivity ���۷����ַ
    public final static String URL_API_HOST_ANDROID_GETMARKETBIDACTIVITY = URL_API_HOST_ANDROID+"GetMarketBidData";
    
    //MarketContractSearchActivity ��ͬ�����ַ
    public final static String URL_API_HOST_ANDROID_GETMARKETCONTRACTACTIVITY = URL_API_HOST_ANDROID+"GetMarketContractData";
    
    //MarketProductSearchActivity ��Ʒ�����ַ
    public final static String URL_API_HOST_ANDROID_GETMARKETPRODUCTACTIVITY = URL_API_HOST_ANDROID+"GetMarketProductData";
    
    //MarketBidSearchActivity ���۲�ѯ��ʷ��¼�����ַ
    public final static String URL_API_HOST_ANDROID_GETMARKETBIDHISTORYACTIVITY = URL_API_HOST_ANDROID+"GetMarketBidHistroyData";
    
    //MarketContractSearchActivity ��ͬ��ѯ��ʷ��¼�����ַ
    public final static String URL_API_HOST_ANDROID_GETMARKETCONTRACTHISTORYACTIVITY = URL_API_HOST_ANDROID+"GetMarketContractHistroyData";
    
  //ContributionAwardActivity HR���׽����뵥
  	public final static String URL_API_HOST_ANDROID_CONTRIBUTIONAWARDACTIVITY = URL_API_HOST_ANDROID+"GetContributionAwardData";

}
