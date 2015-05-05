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
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ApplyDaysOffTBodyInfo;
import com.dahuatech.app.bean.mytask.ApplyDaysOffTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyDaysOffBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.ViewIdGenerator;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ApplyDaysOffActivity
 * @Description ��ͨ���ŵ������뵥��Activity
 * @author 21291
 * @date 2014��7��23�� ����3:22:43
 */
public class ApplyDaysOffActivity extends MenuActivity implements ITaskContext {
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept;
	private Button appButton;						//������ť
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  				//������
	private LinearLayout fSubLinearLayout; 			//���಼��ȫ�ֱ���
	
	private float dip; //����  ���1.5��
	private int fixedHeight;	//�߶ȹ̶�ֵ
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//���ؿ����Ϣ
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private AsyncTaskContext aTaskContext;		//�첽���������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applydaysoff);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYDAYSOFFACTIVITY;			
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density;  
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applydaysoff_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ApplyDaysOffActivity.this, ApplyDaysOffActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyDaysOffActivity.this, R.string.applydaysoff_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyDaysOffActivity.this);
		ApplyDaysOffBusiness eBusiness= (ApplyDaysOffBusiness) factoryBusiness.getInstance("ApplyDaysOffBusiness",serviceUrl);
		return eBusiness.getApplyDaysOffTHeaderInfo(taskParam);
	}
	
	@Override
	public void initBase(Base base) {
		ApplyDaysOffTHeaderInfo adInfo=(ApplyDaysOffTHeaderInfo)base;
		if(!StringUtils.isEmpty(adInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applydaysoff_FBillNo);
			fBillNo.setText(adInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applydaysoff_FApplyName);
			fApplyName.setText(adInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applydaysoff_FApplyDate);
			fApplyDate.setText(adInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.applydaysoff_FApplyDept);
			fApplyDept.setText(adInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFSubEntrys())){
			showSubEntrys(adInfo.getFSubEntrys());
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
	* @date 2014��7��25�� ����1:58:49
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			Type listType = new TypeToken<ArrayList<ApplyDaysOffTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ApplyDaysOffTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ApplyDaysOffTBodyInfo item : tBodyInfos) {
					attachRelativeLayout(item);
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
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����1:59:58
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final ApplyDaysOffTBodyInfo tBodyInfo){
		paddingLeft = 20; // margin in dips
		paddingRight = 20; 
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
        
        //��Բ���ͷ��
        RelativeLayout reLayoutTop=new RelativeLayout(this);   //����һ������ײ���Բ���ʵ��
        //������Բ���ʵ���ĸ߶ȺͿ��
        RelativeLayout.LayoutParams reLPTop=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
        reLPTop.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
  		reLayoutTop.setLayoutParams(reLPTop);
  		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  		reLayoutTop.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegraye5_bottom));
        
  		//����ʱ��
		TextView tv1 = new TextView(this);
		RelativeLayout.LayoutParams tvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tv1.setLayoutParams(tvParam1);
		tv1.setPadding(paddingLeft, 0, 0, 0);
		tv1.setTextAppearance(ApplyDaysOffActivity.this,R.style.iconstitle);
		tv1.setText(tBodyInfo.getFDate());
		reLayoutTop.addView(tv1);
		
		//����
		int tvTop3Id=getNewId();
		TextView tv2 = new TextView(this);
		RelativeLayout.LayoutParams tvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvParam2.addRule(RelativeLayout.LEFT_OF, tvTop3Id);
		tvParam2.setMargins(-50, 0, 50, 0);
		tv2.setLayoutParams(tvParam2);
		tv2.setTextAppearance(ApplyDaysOffActivity.this,R.style.icons);
		tv2.setText(R.string.applydaysoff_daysoff);
		reLayoutTop.addView(tv2);
		
		//����ʱ��
		TextView tv3 = new TextView(this);
		RelativeLayout.LayoutParams tvParam3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam3.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tvParam3.setMargins(-50, 0, 50, 0);
		tv3.setLayoutParams(tvParam3);
		tv3.setTextAppearance(ApplyDaysOffActivity.this,R.style.iconstitle);
		tv3.setText(tBodyInfo.getFHours()+"Сʱ");
		tv3.setId(tvTop3Id);
		reLayoutTop.addView(tv3);
		fSubLinearLayout.addView(reLayoutTop);
		
		//��Բ��ֵײ�1
		RelativeLayout reLayoutBottom1=new RelativeLayout(this);  //����һ������ײ���Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		RelativeLayout.LayoutParams reLPBottom1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom1.setLayoutParams(reLPBottom1);
		reLayoutBottom1.setPadding(paddingLeft, 5, 10, 5);
		
		//����
		TextView rb1Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1Tv1.setLayoutParams(rb1TvParam1);
		rb1Tv1.setTextAppearance(ApplyDaysOffActivity.this,R.style.icons);
		rb1Tv1.setText(R.string.applydaysoff_am);
		int rb1Tv1Id=getNewId();
		rb1Tv1.setId(rb1Tv1Id);
		reLayoutBottom1.addView(rb1Tv1);
		
		//����ֵ
		TextView rb1Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1TvParam2.addRule(RelativeLayout.RIGHT_OF, rb1Tv1Id);
		rb1Tv2.setLayoutParams(rb1TvParam2);
		rb1Tv2.setPadding(10, 0, 0, 0);
		rb1Tv2.setTextAppearance(ApplyDaysOffActivity.this,R.style.iconstitle);
		rb1Tv2.setText(tBodyInfo.getFStartTime());
		reLayoutBottom1.addView(rb1Tv2);
		fSubLinearLayout.addView(reLayoutBottom1);
		
		//��Բ��ֵײ�1
		RelativeLayout reLayoutBottom2=new RelativeLayout(this);  //����һ������ײ���Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		RelativeLayout.LayoutParams reLPBottom2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom2.setLayoutParams(reLPBottom2);
		reLayoutBottom2.setPadding(paddingLeft, 5, 10, 5);
		
		//����
		TextView rb2Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2Tv1.setLayoutParams(rb2TvParam1);
		rb2Tv1.setTextAppearance(ApplyDaysOffActivity.this,R.style.icons);
		rb2Tv1.setText(R.string.applydaysoff_pm);
		int rb2Tv1Id=getNewId();
		rb2Tv1.setId(rb2Tv1Id);
		reLayoutBottom2.addView(rb2Tv1);
		
		//����ֵ
		TextView rb2Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2TvParam2.addRule(RelativeLayout.RIGHT_OF, rb2Tv1Id);
		rb2Tv2.setLayoutParams(rb2TvParam2);
		rb2Tv2.setPadding(10, 0, 0, 0);
		rb2Tv2.setTextAppearance(ApplyDaysOffActivity.this,R.style.iconstitle);
		rb2Tv2.setText(tBodyInfo.getFEndTime());
		reLayoutBottom2.addView(rb2Tv2);
		fSubLinearLayout.addView(reLayoutBottom2);
		
		//��Բ��ֵײ�1
		RelativeLayout reLayoutBottom3=new RelativeLayout(this);  //����һ������ײ���Բ���ʵ��
		//������Բ���ʵ���ĸ߶ȺͿ��
		RelativeLayout.LayoutParams reLPBottom3=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom3.setLayoutParams(reLPBottom3);
		reLayoutBottom3.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//ԭ��
		TextView rb3Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3Tv1.setLayoutParams(rb3TvParam1);
		rb3Tv1.setPadding(paddingLeft, 0, 0, 0);
		rb3Tv1.setTextAppearance(ApplyDaysOffActivity.this,R.style.icons);
		rb3Tv1.setText(R.string.applydaysoff_reason);
		int rb3Tv1Id=getNewId();
		rb3Tv1.setId(rb3Tv1Id);
		reLayoutBottom3.addView(rb3Tv1);
		
		//ԭ��ֵ
		TextView rb3Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3TvParam2.addRule(RelativeLayout.RIGHT_OF, rb3Tv1Id);
		rb3Tv2.setLayoutParams(rb3TvParam2);
		rb3Tv2.setPadding(10, 0, 0, 0);
		rb3Tv2.setTextAppearance(ApplyDaysOffActivity.this,R.style.iconstitle);
		rb3Tv2.setText(tBodyInfo.getFReason());
		reLayoutBottom3.addView(rb3Tv2);
		fSubLinearLayout.addView(reLayoutBottom3);
	}
	
	/** 
	* @Title: getNewId 
	* @Description: ��ȡһ���µ�IDֵ
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����2:00:20
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
		
		handleLayout.setVisibility(View.GONE);
		if("1".equals(fStatus)){  //˵��������������
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		appButton.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ApplyDaysOffActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applydaysoff_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{	//��ʾ������ҳ��
					Intent intent = new Intent(ApplyDaysOffActivity.this, WorkFlowBeenActivity.class);
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
		commonMenu.setContext(ApplyDaysOffActivity.this);
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
