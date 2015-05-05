package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.NetworkPermissionTBodyInfo;
import com.dahuatech.app.bean.mytask.NetworkPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NetworkPermissionBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.ViewIdGenerator;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName NetworkPermissionActivity
 * @Description ����Ȩ�����뵥��Activity
 * @author 21291
 * @date 2014��7��9�� ����4:00:15
 */
public class NetworkPermissionActivity extends MenuActivity implements ITaskContext,ICheckNextNode{
	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fDate,fApplyerDeptName,fApplyerType,fTelphone,fComboBox4,fNote;
	private Button appButton,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private float dip; //����  ���1.5��
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width; // margin in dips
	private	DisplayMetrics displaymetrics; 	//���ؿ����Ϣ

	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networkpermission);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_NETWORKPERMISSIONACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, ""); 	//��ȡԱ����
		
		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density;  
		fSubLinearLayout=(LinearLayout)findViewById(R.id.networkpermission_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(NetworkPermissionActivity.this, NetworkPermissionActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(NetworkPermissionActivity.this, R.string.networkpermission_netparseerror);
			return;
		}
	}
	
	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(NetworkPermissionActivity.this);
		NetworkPermissionBusiness eBusiness= (NetworkPermissionBusiness)factoryBusiness.getInstance("NetworkPermissionBusiness",serviceUrl);
		return eBusiness.getNetworkPermissionTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		NetworkPermissionTHeaderInfo npInfo=(NetworkPermissionTHeaderInfo)base;
		if(!StringUtils.isEmpty(npInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.networkpermission_FBillNo);
			fBillNo.setText(npInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.networkpermission_FApplyName);
			fApplyName.setText(npInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFDate())){
			fDate=(TextView)findViewById(R.id.networkpermission_FDate);
			fDate.setText(npInfo.getFDate());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFApplyerDeptName())){
			fApplyerDeptName=(TextView)findViewById(R.id.networkpermission_FApplyerDeptName);
			fApplyerDeptName.setText(npInfo.getFApplyerDeptName());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFApplyerType())){
			fApplyerType=(TextView)findViewById(R.id.networkpermission_FApplyerType);
			fApplyerType.setText(npInfo.getFApplyerType());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFTelphone())){
			fTelphone=(TextView)findViewById(R.id.networkpermission_FTelphone);
			fTelphone.setText(npInfo.getFTelphone());
		}
		
		if(!StringUtils.isEmpty(npInfo.getFComboBox4())){
			fComboBox4=(TextView)findViewById(R.id.networkpermission_FComboBox4);
			fComboBox4.setText(npInfo.getFComboBox4());
		}

		if(!StringUtils.isEmpty(npInfo.getFNote())){
			fNote=(TextView)findViewById(R.id.networkpermission_FNote);
			fNote.setText(npInfo.getFNote());
		}
	
		if(!StringUtils.isEmpty(npInfo.getFSubEntrys())){
			showSubEntrys(npInfo.getFSubEntrys());
		}
		initApprove();
	}

	/** 
	* @Title: showSubEntrys 
	* @Description: �������༯��
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��9�� ����4:22:40
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<NetworkPermissionTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<NetworkPermissionTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (NetworkPermissionTBodyInfo item : tBodyInfos) {
					attachRelativeLayout(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: ��̬ƴ�� ��ʾ������ͼ�������ж��
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��17�� ����5:20:51
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final NetworkPermissionTBodyInfo tBodyInfo,final int i){
        paddingLeft = 20; // margin in dips
		paddingRight = 10; 
		paddingTop = 5;
		paddingBottom = 5; 
	    width = getPixelsWidth(); 
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
        }
        
		RelativeLayout reLayoutTop=new RelativeLayout(this);  //����һ������ͷ����Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		LayoutParams reLPTop=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutTop.setLayoutParams(reLPTop);
		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		
		//�к�
		TextView tvTop1 = new TextView(this);
		LayoutParams tvTopParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTop1.setLayoutParams(tvTopParam1);
		tvTop1.setTextAppearance(NetworkPermissionActivity.this,R.style.icons);
		tvTop1.setText(R.string.networkpermission_line);
		int tvTop1Id=getNewId();
		tvTop1.setId(tvTop1Id);		
		reLayoutTop.addView(tvTop1);
		
		//�к�ֵ
		TextView tvTop2 = new TextView(this);
		LayoutParams tvTopParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTopParam2.addRule(RelativeLayout.RIGHT_OF, tvTop1Id);
		tvTop2.setLayoutParams(tvTopParam2);
		tvTop2.setTextAppearance(NetworkPermissionActivity.this,R.style.icons);
		tvTop2.setText(String.valueOf(i));
		reLayoutTop.addView(tvTop2);		
		fSubLinearLayout.addView(reLayoutTop);
		
		RelativeLayout reLayoutBottom=new RelativeLayout(this);  //����һ������ײ���Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		LayoutParams reLPBottom=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom.setLayoutParams(reLPBottom);
		reLayoutBottom.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//����¥��
		TextView tvBottom1 = new TextView(this);
		LayoutParams tvBottomParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottom1.setLayoutParams(tvBottomParam1);
		tvBottom1.setPadding(paddingLeft, 0, 0, 0);
		tvBottom1.setTextAppearance(NetworkPermissionActivity.this,R.style.iconstext);
		tvBottom1.setText(R.string.networkpermission_IpAddress);
		tvBottom1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.address, 0, 0, 0);
		int tvBottom1Id=getNewId();
		tvBottom1.setId(tvBottom1Id);
		reLayoutBottom.addView(tvBottom1);
		
		//����¥��ֵ
		TextView tvBottom2 = new TextView(this);
		LayoutParams tvBottomParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam2.setMargins(0, 0, 0, 5);
		tvBottomParam2.addRule(RelativeLayout.RIGHT_OF, tvBottom1Id);
		tvBottom2.setLayoutParams(tvBottomParam2);
		tvBottom2.setTextAppearance(NetworkPermissionActivity.this,R.style.iconscontent);
		tvBottom2.setText(tBodyInfo.getFIpAddress());
		reLayoutBottom.addView(tvBottom2);
		
		//��ʼʱ������
		TextView tvBottom3 = new TextView(this);
		LayoutParams tvBottomParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam3.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottom3.setLayoutParams(tvBottomParam3);
		tvBottom3.setPadding(paddingLeft, 0, 0, 0);
		tvBottom3.setTextAppearance(NetworkPermissionActivity.this,R.style.iconstext);
		tvBottom3.setText(R.string.networkpermission_StartDate);
		tvBottom3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		int tvBottom3Id=getNewId();
		tvBottom3.setId(tvBottom3Id);
		reLayoutBottom.addView(tvBottom3);
		
		//��ʼʱ��ֵ
		TextView tvBottom4 = new TextView(this);
		LayoutParams tvBottomParam4 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam4.setMargins(0, 0, 0, 5);
		tvBottomParam4.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottomParam4.addRule(RelativeLayout.RIGHT_OF, tvBottom3Id);
		tvBottom4.setLayoutParams(tvBottomParam4);
		tvBottom4.setTextAppearance(NetworkPermissionActivity.this,R.style.iconscontent);
		tvBottom4.setText(tBodyInfo.getFDate1());
		reLayoutBottom.addView(tvBottom4);
		
		//��Ч��������
		TextView tvBottom5 = new TextView(this);
		LayoutParams tvBottomParam5 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam5.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottom5.setLayoutParams(tvBottomParam5);
		tvBottom5.setPadding(paddingLeft, 0, 0, 0);
		tvBottom5.setTextAppearance(NetworkPermissionActivity.this,R.style.iconstext);
		tvBottom5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		tvBottom5.setText(R.string.networkpermission_ComboBox1);
		int tvBottom5Id=getNewId();
		tvBottom5.setId(tvBottom5Id);
		reLayoutBottom.addView(tvBottom5);
		
		//��Ч����ֵ
		TextView tvBottom6 = new TextView(this);
		LayoutParams tvBottomParam6 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam6.setMargins(0, 0, 0, 5);
		tvBottomParam6.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottomParam6.addRule(RelativeLayout.RIGHT_OF, tvBottom5Id);
		tvBottom6.setLayoutParams(tvBottomParam6);
		tvBottom6.setTextAppearance(NetworkPermissionActivity.this,R.style.iconscontent);
		tvBottom6.setText(tBodyInfo.getFComboBox1());
		reLayoutBottom.addView(tvBottom6);	
		
		//Ȩ���������
		TextView tvBottom7 = new TextView(this);
		LayoutParams tvBottomParam7 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam7.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottom7.setLayoutParams(tvBottomParam7);
		tvBottom7.setPadding(paddingLeft, 0, 0, 0);
		tvBottom7.setTextAppearance(NetworkPermissionActivity.this,R.style.iconstext);
		tvBottom7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount_3002, 0, 0, 0);
		tvBottom7.setText(R.string.networkpermission_ComboBox3);
		int tvBottom7Id=getNewId();
		tvBottom7.setId(tvBottom7Id);
		reLayoutBottom.addView(tvBottom7);
		
		//Ȩ�����ֵ
		TextView tvBottom8 = new TextView(this);
		LayoutParams tvBottomParam8 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam8.setMargins(0, 0, 0, 5);
		tvBottomParam8.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottomParam8.addRule(RelativeLayout.RIGHT_OF, tvBottom7Id);
		tvBottom8.setLayoutParams(tvBottomParam8);
		tvBottom8.setTextAppearance(NetworkPermissionActivity.this,R.style.iconscontent);
		tvBottom8.setText(tBodyInfo.getFComboBox3());
		reLayoutBottom.addView(tvBottom8);
		
		fSubLinearLayout.addView(reLayoutBottom);
	}
	
	/** 
	* @Title: getNewId 
	* @Description:  ��ȡһ���µ�IDֵ
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��7��9�� ����4:27:38
	*/
	private int getNewId(){
		int fId;
		do {
			fId = ViewIdGenerator.generateViewId();
		} while (findViewById(fId) != null);
		return fId;
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: ��ȡ��Ļ���ؿ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����2:00:31
	*/
	private int getPixelsWidth(){
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: initApprove 
	* @Description: ����������ť��ͼ
	* @param    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��13�� ����5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout);
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		if("1".equals(fStatus)){  //˵��������������
			appButton.setText(R.string.approve_imgbtnApprove_record);
			handleLayout.setVisibility(View.GONE);
		}
		else {  //˵����δ��������
			handleLayout.setVisibility(View.VISIBLE);
			Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			
			//��ǩ����
			plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(NetworkPermissionActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.networkpermission_title));
				}
			});
			 
			//���Ͳ���
			copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(NetworkPermissionActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.networkpermission_title));
				}
			});
			
			//�¼��ڵ����
			lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(NetworkPermissionActivity.this,NetworkPermissionActivity.this);
			lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		}
		 
		//��������
		appButton.setOnClickListener(new OnClickListener() {
			@Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(NetworkPermissionActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.networkpermission_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(NetworkPermissionActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
		 	}
		});
		
		fSubLinearLayout.addView(buttonLayout);
	}
	
	@Override
	public void setCheckResult(ResultMessage resultMessage) {
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, NetworkPermissionActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.networkpermission_title));
	}

	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.ACTIVITY_WORKFLOW:
				if(resultCode==RESULT_OK){
					fStatus="1";  //�Ӵ�����ҳ����� ˵���������򲵻�ͨ��
					SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);					
					sp.edit().putString(AppContext.TA_APPROVE_FSTATUS, fStatus).commit();
					appButton.setText(R.string.approve_imgbtnApprove_record);
					handleLayout.setVisibility(View.GONE);
				}			
				break;
			case AppContext.ACTIVITY_WORKFLOWBEEN:
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(NetworkPermissionActivity.this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
