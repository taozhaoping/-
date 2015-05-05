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
import android.widget.TableLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.DevelopTestNetworkTBodyInfo;
import com.dahuatech.app.bean.mytask.DevelopTestNetworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.DevelopTestNetworkBusiness;
import com.dahuatech.app.business.FactoryBusiness;
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
 * @ClassName DevelopTestNetworkActivity
 * @Description �з���Ŀ����Ȩ�����뵥��Activity
 * @author 21291
 * @date 2014��7��15�� ����5:02:26
 */
public class DevelopTestNetworkActivity extends MenuActivity implements ITaskContext,ICheckNextNode {
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fDate,fApplyerDeptName,fTelphone,fApplyerPermisson,fPermissionRequre;
	private Button appButton,lowerButton;	//������ť,�¼��ڵ�
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fSubLinearLayout; //���಼��ȫ�ֱ���
	
	private float dip; //����  ���1.5��
	private int fixedHeight;	//�߶ȹ̶�ֵ
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//���ؿ����Ϣ
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private SharedPreferences sp;  //��ȡ��½��Ϣ
	private AsyncTaskContext aTaskContext;		//�첽���������
	private LowerNodeAppCheck lowerNodeAppCheck;		//�첽�����Ƿ����¼��ڵ������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developtestnetwork);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DEVELOPTESTNETWORKACTIVITY;	
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
		fSubLinearLayout=(LinearLayout)findViewById(R.id.developtestnetwork_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(DevelopTestNetworkActivity.this, DevelopTestNetworkActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(DevelopTestNetworkActivity.this, R.string.developtestnetwork_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DevelopTestNetworkActivity.this);
		DevelopTestNetworkBusiness eBusiness= (DevelopTestNetworkBusiness)factoryBusiness.getInstance("DevelopTestNetworkBusiness",serviceUrl);
		return eBusiness.getDevelopTestNetworkTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		DevelopTestNetworkTHeaderInfo deInfo=(DevelopTestNetworkTHeaderInfo)base;
		if(!StringUtils.isEmpty(deInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.developtestnetwork_FBillNo);
			fBillNo.setText(deInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.developtestnetwork_FApplyName);
			fApplyName.setText(deInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFDate())){
			fDate=(TextView)findViewById(R.id.developtestnetwork_FDate);
			fDate.setText(deInfo.getFDate());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyerDeptName())){
			fApplyerDeptName=(TextView)findViewById(R.id.developtestnetwork_FApplyerDeptName);
			fApplyerDeptName.setText(deInfo.getFApplyerDeptName());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFTelphone())){
			fTelphone=(TextView)findViewById(R.id.developtestnetwork_FTelphone);
			fTelphone.setText(deInfo.getFTelphone());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyerPermisson())){
			fApplyerPermisson=(TextView)findViewById(R.id.developtestnetwork_FApplyerPermisson);
			fApplyerPermisson.setText(deInfo.getFApplyerPermisson());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFPermissionRequre())){
			fPermissionRequre=(TextView)findViewById(R.id.developtestnetwork_FPermissionRequre);
			fPermissionRequre.setText(deInfo.getFPermissionRequre());
		}
	
		if(!StringUtils.isEmpty(deInfo.getFSubEntrys())){
			showSubEntrys(deInfo.getFSubEntrys());
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
	* @date 2014��7��15�� ����5:06:33
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<DevelopTestNetworkTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DevelopTestNetworkTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (DevelopTestNetworkTBodyInfo item : tBodyInfos) {
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
	* @date 2014��7��17�� ����5:15:24
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final DevelopTestNetworkTBodyInfo tBodyInfo,final int i){
		paddingLeft = 20; // margin in dips
		paddingRight = 10; 
		paddingTop = 5;
		paddingBottom = 5; 
		fixedHeight=33;	//�߶ȹ̶�ֵ
	    width = getPixelsWidth(); 
		layoutHeight=RelativeLayout.LayoutParams.WRAP_CONTENT;
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
	        layoutHeight=(int)(fixedHeight * dip);
		}
		
		RelativeLayout reLayoutTop=new RelativeLayout(this);  //����һ������ͷ����Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		LayoutParams reLPTop=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
		reLayoutTop.setLayoutParams(reLPTop);
		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		//�к�
		TextView tvTop1 = new TextView(this);
		LayoutParams tvTopParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTop1.setLayoutParams(tvTopParam1);
		tvTop1.setTextAppearance(DevelopTestNetworkActivity.this,R.style.icons);
		tvTop1.setText(R.string.developtestnetwork_line);
		int tvTop1Id=getNewId();
		tvTop1.setId(tvTop1Id);		
		reLayoutTop.addView(tvTop1);
		
		//�к�ֵ
		TextView tvTop2 = new TextView(this);
		LayoutParams tvTopParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTopParam2.addRule(RelativeLayout.RIGHT_OF, tvTop1Id);
		tvTop2.setLayoutParams(tvTopParam2);
		tvTop2.setTextAppearance(DevelopTestNetworkActivity.this,R.style.icons);
		tvTop2.setText(String.valueOf(i));
		reLayoutTop.addView(tvTop2);		
		fSubLinearLayout.addView(reLayoutTop);
		
		RelativeLayout reLayoutBottom=new RelativeLayout(this);  //����һ������ײ���Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		LayoutParams reLPBottom=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom.setLayoutParams(reLPBottom);
		reLayoutBottom.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//����Ȩ���豸IP��ַ����
		TextView tvBottom1 = new TextView(this);
		LayoutParams tvBottomParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottom1.setLayoutParams(tvBottomParam1);
		tvBottom1.setPadding(paddingLeft, 0, 0, 0);
		tvBottom1.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom1.setText(R.string.developtestnetwork_ApplyForIp);
		tvBottom1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.address, 0, 0, 0);
		int tvBottom1Id=getNewId();
		tvBottom1.setId(tvBottom1Id);
		reLayoutBottom.addView(tvBottom1);
		
		//����Ȩ���豸IP��ֵַ
		TextView tvBottom2 = new TextView(this);
		LayoutParams tvBottomParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam2.setMargins(0, 0, 0, 5);
		tvBottomParam2.addRule(RelativeLayout.RIGHT_OF, tvBottom1Id);
		tvBottom2.setLayoutParams(tvBottomParam2);
		tvBottom2.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom2.setText(tBodyInfo.getFApplyForIp());
		reLayoutBottom.addView(tvBottom2);
		
		//Ŀ��IP��ַ/��������
		TextView tvBottom3 = new TextView(this);
		LayoutParams tvBottomParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam3.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottom3.setLayoutParams(tvBottomParam3);
		tvBottom3.setPadding(paddingLeft, 0, 0, 0);
		tvBottom3.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom3.setText(R.string.developtestnetwork_PurposeIp);
		tvBottom3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.address, 0, 0, 0);
		int tvBottom3Id=getNewId();
		tvBottom3.setId(tvBottom3Id);
		reLayoutBottom.addView(tvBottom3);
		
		//Ŀ��IP��ַ/����ֵ
		TextView tvBottom4 = new TextView(this);
		LayoutParams tvBottomParam4 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam4.setMargins(0, 0, 0, 5);
		tvBottomParam4.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottomParam4.addRule(RelativeLayout.RIGHT_OF, tvBottom3Id);
		tvBottom4.setLayoutParams(tvBottomParam4);
		tvBottom4.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom4.setText(tBodyInfo.getFPurposeIp());
		reLayoutBottom.addView(tvBottom4);
		
		//��ʼ��������
		TextView tvBottom5 = new TextView(this);
		LayoutParams tvBottomParam5 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam5.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottom5.setLayoutParams(tvBottomParam5);
		tvBottom5.setPadding(paddingLeft, 0, 0, 0);
		tvBottom5.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		tvBottom5.setText(R.string.developtestnetwork_StartTime);
		int tvBottom5Id=getNewId();
		tvBottom5.setId(tvBottom5Id);
		reLayoutBottom.addView(tvBottom5);
		
		//��ʼ����ֵ
		TextView tvBottom6 = new TextView(this);
		LayoutParams tvBottomParam6 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam6.setMargins(0, 0, 0, 5);
		tvBottomParam6.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottomParam6.addRule(RelativeLayout.RIGHT_OF, tvBottom5Id);
		tvBottom6.setLayoutParams(tvBottomParam6);
		tvBottom6.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom6.setText(tBodyInfo.getFStartTime());
		reLayoutBottom.addView(tvBottom6);
		
		//������������
		TextView tvBottom7 = new TextView(this);
		LayoutParams tvBottomParam7 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam7.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottom7.setLayoutParams(tvBottomParam7);
		tvBottom7.setPadding(paddingLeft, 0, 0, 0);
		tvBottom7.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		tvBottom7.setText(R.string.developtestnetwork_EndTime);
		int tvBottom7Id=getNewId();
		tvBottom7.setId(tvBottom7Id);
		reLayoutBottom.addView(tvBottom7);
		
		//��������ֵ
		TextView tvBottom8 = new TextView(this);
		LayoutParams tvBottomParam8 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam8.setMargins(0, 0, 0, 5);
		tvBottomParam8.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottomParam8.addRule(RelativeLayout.RIGHT_OF, tvBottom7Id);
		tvBottom8.setLayoutParams(tvBottomParam8);
		tvBottom8.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom8.setText(tBodyInfo.getFEndTime());
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
		else { //˵����δ��������
			handleLayout.setVisibility(View.VISIBLE);
			Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
		 
			//��ǩ����
			plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopTestNetworkActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developtestnetwork_title));
				}
			});
		 
			//���Ͳ���
			copyButton.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopTestNetworkActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developtestnetwork_title));
				}
			});
			
			  //�¼��ڵ����
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(DevelopTestNetworkActivity.this,DevelopTestNetworkActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //��������
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 if("0".equals(fStatus)){ //��ʾ������ҳ��
					 Intent intent = new Intent(DevelopTestNetworkActivity.this, WorkFlowActivity.class);
					 intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					 intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					 intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					 intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.developtestnetwork_title));
					 startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					 overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				 } 
				 else 
				 {//��ʾ������ҳ��
					 Intent intent = new Intent(DevelopTestNetworkActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, DevelopTestNetworkActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.developtestnetwork_title));
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
		commonMenu.setContext(DevelopTestNetworkActivity.this);
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
