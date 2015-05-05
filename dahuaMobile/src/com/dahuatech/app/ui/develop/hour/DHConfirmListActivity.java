package com.dahuatech.app.ui.develop.hour;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHConfirmListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHConfirmChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmJsonInfo;
import com.dahuatech.app.bean.develophour.DHConfirmParamInfo;
import com.dahuatech.app.bean.develophour.DHConfirmRootInfo;
import com.dahuatech.app.bean.develophour.DHConfirmSubClassInfo;
import com.dahuatech.app.bean.develophour.DHUploadConfirmInfo;
import com.dahuatech.app.bean.develophour.DHUploadConfirmPersonInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.DHWeekSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DHConfirmListActivity
 * @Description �з���ʱȷ���б�
 * @author 21291
 * @date 2014��10��17�� ����3:30:19
 */
public class DHConfirmListActivity extends MenuActivity {
	
	private EditText searchEdit;								//�����ı�
	private ImageButton searchImage;							//������ť
	private TextView fWeekIndexTView;							//�ܴ�
	private ExpandableListView exlistView;						//�б�ؼ�
	private Button btnSelect,btnOk;								//ȫѡ,ȷ��
	private ProgressDialog dialog;      						//������

	private List<DHConfirmRootInfo> dArrayList;					//����Դ
	private List<DHUploadConfirmPersonInfo> selectList;			//ѡ�е������
	private DHConfirmChildInfo dConfirmChildInfo;				//����ʵ��
	private DHConfirmListAdapter dAdapter;						//������
	private DevelopHourBusiness dBusiness;						//ҵ���߼���
	
	private String fItemNumber,fShowResult;  					//Ա����,���½��
	private String serviceUrl,confirmUrl;  						//�����ַ,��Ա��ʱȷ�ϵ�ַ
	private AppContext appContext; 								//ȫ��Context
	
	private Calendar cal;										//������
	private int fCurrentYear;									//��ǰ���
	private int fWeekIndex;										//�ܴ�
	private int fCurrentWeekIndex;								//��ǰ�ܴ�

	private Type subClassType;									//��������
	private Gson gson;											//gsonʵ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dh_confirm_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		appContext=(AppContext)getApplication(); //��ʼ��ȫ�ֱ���
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}	
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHCONFIRMLISTACTIVITY;
		confirmUrl=AppUrl.URL_API_HOST_ANDROID_UPLOADDHCONFIRMACTIVITY;
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initView();	
		setOnListener();
		new getListAsync().execute();	
	}
	
	/** 
	* @Title: initView 
	* @Description: ��ʼ���ؼ���ͼԪ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��20�� ����4:47:07
	*/
	private void initView(){
		searchEdit=(EditText)findViewById(R.id.dh_confirm_list_search_EditText);
		searchEdit.setInputType(InputType.TYPE_NULL);
		searchImage=(ImageButton)findViewById(R.id.dh_confirm_list_search_ImageButton);
		fWeekIndexTView=(TextView)findViewById(R.id.dh_comfirm_list_FWeekIndex);
		exlistView=(ExpandableListView)findViewById(R.id.dh_confirm_list);	
		
		btnSelect=(Button)findViewById(R.id.dh_comfirm_list_btnSelect);
		btnOk=(Button)findViewById(R.id.dh_comfirm_list_btnOk);
	
		//�����������
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
				
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHConfirmListActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
	
		dArrayList=new ArrayList<DHConfirmRootInfo>();
		
		cal = new GregorianCalendar(); 
	    fCurrentYear=cal.get(Calendar.YEAR);  		 		 	        						//��ȡ��ǰ���  							
	    fCurrentWeekIndex=fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime())-1;		    //��ȡ��ǰ�����ǵڼ��ܵ���һ��
	    fWeekIndexTView.setText("��"+String.valueOf(fWeekIndex)+"��");

	    subClassType = new TypeToken<ArrayList<DHConfirmSubClassInfo>>(){}.getType();
	    gson=GsonHelper.getInstance();
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: �����¼�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��4�� ����6:21:22
	*/
	private void setOnListener(){	
		searchImage.setOnClickListener(new OnClickListener() { //������ť
			@Override
			public void onClick(View v) {
				showWeekSpinner();
			}
		});
		
		btnSelect.setOnClickListener(new OnClickListener() { //ȫѡ��ť
			@Override
			public void onClick(View v) {
				selectAll();
			}
		});
		
		btnOk.setOnClickListener(new OnClickListener() { //ȷ����ť
			@Override
			public void onClick(View v) {
				selectList=getSelectList();
				if(selectList.size() > 0){
					showAlertDialog();
				}
				else{
					UIHelper.ToastMessage(DHConfirmListActivity.this, getString(R.string.dh_confrim_list_no_select));
				}
			}
		});
	}
	
	/** 
	* @Title: showWeekSpinner 
	* @Description: ��ʾ�ܴε�����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��24�� ����10:59:41
	*/
	private void showWeekSpinner(){
		final DHWeekSpinnerDialog dSpinnerDialog=new DHWeekSpinnerDialog(DHConfirmListActivity.this,fCurrentWeekIndex,new ISpinnerListener(){

			@Override
			public void rejectOk(int n, RejectNodeInfo reInfo) {}

			@Override
			public void adAmapOk(int n, AdAmapInfo adAmapInfo) {}	
			
			@Override
			public void cancelled() {
				searchEdit.setText("");
			}

			@Override
			public void dHWeekOk(int n,String itemText, DHWeekInfo dhWeekInfo) { //ѡ����һ���ܶ���
				searchEdit.setText(itemText);
				fCurrentYear=dhWeekInfo.getFYear();
				fWeekIndex=dhWeekInfo.getFIndex();
				
				dArrayList.clear();
				fWeekIndexTView.setText("��"+String.valueOf(fWeekIndex)+"��");
				new getListAsync().execute();	
			}
		});
		dSpinnerDialog.setTitle(getResources().getString(R.string.spinner_week_prompt));
		dSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
		dSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
		dSpinnerDialog.show();	
	}
	
	/** 
	* @Title: showAlertDialog 
	* @Description: �����Ƿ�ȷ������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����5:08:59
	*/
	@SuppressLint("InlinedApi")
	private void showAlertDialog(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(DHConfirmListActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setTitle(R.string.dh_confrim_list_sure_warn);
		builder.setMessage(R.string.dh_confrim_list_sure_make);
		builder.setPositiveButton(R.string.dh_confrim_list_sure_yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();					
						personDhConfirm();
					}
				});
		builder.setNegativeButton(R.string.dh_confrim_list_sure_no,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/** 
	* @Title: personDhConfirm 
	* @Description: ��Ա��ʱȷ��
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����5:11:14
	*/
	private void personDhConfirm(){
		new uploadConfirmAsync().execute();
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽�ϴ��з���ʱ��Աȷ��
	 * @author 21291
	 * @date 2014��10��21�� ����11:37:34
	 */
	private class uploadConfirmAsync extends AsyncTask<Void, Void, ResultMessage>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected ResultMessage doInBackground(Void... params) {
			return uploadByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showUploadResult(result);	
		}	
	}
	
	/** 
	* @Title: uploadByPost 
	* @Description: ��ȡ�������
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����11:37:11
	*/
	private ResultMessage uploadByPost(){
		SharedPreferences sp=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		DHUploadConfirmInfo dUploadConfirm=DHUploadConfirmInfo.getDHUploadConfirmInfo();
		dUploadConfirm.setFItemNumber(fItemNumber);
		dUploadConfirm.setFItemName(sp.getString(AppContext.FITEMNAME_KEY, ""));
		dUploadConfirm.setFYear(fCurrentYear);
		dUploadConfirm.setFWeekIndex(fWeekIndex);
		
		dBusiness.setServiceUrl(confirmUrl);
		return dBusiness.UploadDhConfirmData(dUploadConfirm, selectList);
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: �����ϴ���UI���
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��19�� ����11:39:33
	*/
	private void showUploadResult(ResultMessage resultMessage){
		fShowResult=getResources().getString(R.string.dh_confrim_list_failure);
		if(resultMessage.isIsSuccess()){
			fShowResult=getResources().getString(R.string.dh_confrim_list_success);
			sendLogs(); //������־��Ϣ����ͳ��
		}
		UIHelper.ToastMessage(DHConfirmListActivity.this, fShowResult);
		// �ӳ�2��ˢ��ҳ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	dArrayList=new ArrayList<DHConfirmRootInfo>();
            	new getListAsync().execute();	
            }
        }, 2000);	
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��10��21�� ����11:37:34
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<DHConfirmJsonInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHConfirmJsonInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHConfirmJsonInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: ��ȡʵ�弯����Ϣ
	* @param @param pageIndex
	* @param @return     
	* @return List<DHRootInfo>    
	* @throws 
	* @author 21291
	* @date 2014��10��21�� ����11:42:41
	*/
	private List<DHConfirmJsonInfo> getListByPost(){
		DHConfirmParamInfo dParamInfo=DHConfirmParamInfo.getDHParamInfo();
		dParamInfo.setFItemNumber(fItemNumber);
		dParamInfo.setFWeekIndex(fWeekIndex);
		dParamInfo.setFYear(fCurrentYear);
		
		dBusiness.setServiceUrl(serviceUrl);
		return dBusiness.getDHConfirmList(dParamInfo);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ���б���
	* @param @param dhRootList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��21�� ����11:43:14
	*/
	private void renderListView(final List<DHConfirmJsonInfo> dhRootList){
		if(dhRootList.size()==0){ //û������
			UIHelper.ToastMessage(DHConfirmListActivity.this, getString(R.string.dh_confrim_list_netparseerror),3000);
			dArrayList.clear();
		}
		else {
			initData(dhRootList);
		}
		dAdapter = new DHConfirmListAdapter(DHConfirmListActivity.this, dArrayList,R.layout.dh_confirm_list_root,R.layout.dh_confirm_list_child);
		exlistView.setAdapter(dAdapter);
		exlistView.setGroupIndicator(null); //ȥ��ͼ��
		
		exlistView.setOnChildClickListener(new OnChildClickListener() { //������

	        @Override
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {  	
	        	dConfirmChildInfo=null;
	        	dConfirmChildInfo=(DHConfirmChildInfo)dAdapter.getChild(groupPosition,childPosition);
				if(dConfirmChildInfo!=null){
					final DHConfirmRootInfo dRootInfo = (DHConfirmRootInfo) dAdapter.getGroup(groupPosition);	
					UIHelper.showDHConfirmListPerson(DHConfirmListActivity.this, fItemNumber,fWeekIndex,fCurrentYear,dRootInfo.getFProjectCode(),dConfirmChildInfo.getFItemNumber());	
				}
	        	return true;
	        }
	    });
		
		exlistView.setOnGroupClickListener(new OnGroupClickListener() { //������

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {	
				return false;
			}
	    });
		
		exlistView.setOnGroupExpandListener(new OnGroupExpandListener() {	
            public void onGroupExpand(int groupPosition) {}
        });
 
		exlistView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {}
        });
	}
	
	/** 
	* @Title: initData 
	* @Description: ��ʼ����������
	* @param @param dhRootList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����11:01:57
	*/
	private void initData(final List<DHConfirmJsonInfo> dhRootList){
		try {
			for (DHConfirmJsonInfo item : dhRootList) {	
				//ʵ��������
				DHConfirmRootInfo dConfirmRootInfo=new DHConfirmRootInfo();
				dConfirmRootInfo.setFProjectCode(item.getFProjectCode());
				dConfirmRootInfo.setFProjectName(item.getFProjectName());
				
				List<DHConfirmChildInfo> dConfirmChildInfos=new ArrayList<DHConfirmChildInfo>();
				JSONArray jsonArray= new JSONArray(item.getFSubChilds());
				List<DHConfirmSubClassInfo> dSubClassInfos=gson.fromJson(jsonArray.toString(), subClassType);
				for (DHConfirmSubClassInfo dSubClassInfo : dSubClassInfos) {  
					DHConfirmChildInfo dhConfirmChildInfo=new DHConfirmChildInfo(); //ʵ��������
					dhConfirmChildInfo.setFItemNumber(dSubClassInfo.getFItemNumber());
					dhConfirmChildInfo.setFItemName(dSubClassInfo.getFItemName());
					dConfirmChildInfos.add(dhConfirmChildInfo);
					
					dhConfirmChildInfo.addObserver(dConfirmRootInfo);  //�۲츸��
					dConfirmRootInfo.addObserver(dhConfirmChildInfo);  //�۲�����
				}	
				dConfirmRootInfo.setFChildren(dConfirmChildInfos);
				dArrayList.add(dConfirmRootInfo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: selectAll 
	* @Description: ȫѡ����
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����2:19:51
	*/
	private void selectAll(){
		for (DHConfirmRootInfo dRootInfo : dArrayList) {
			dRootInfo.changeChecked();
			dAdapter.refreshView();
		}
	}
	
	/** 
	* @Title: getSelectList 
	* @Description: ��ȡ��ѡ�����
	* @param @return     
	* @return List<DHConfirmChildInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����11:30:08
	*/
	private List<DHUploadConfirmPersonInfo> getSelectList(){
		List<DHUploadConfirmPersonInfo> reList=new ArrayList<DHUploadConfirmPersonInfo>();
		for (DHConfirmRootInfo dRootInfo : dArrayList) {
			DHUploadConfirmPersonInfo dPersonInfo=new DHUploadConfirmPersonInfo();
			List<String> fItemNumbers=new ArrayList<String>();
			for (DHConfirmChildInfo dConfirmChildInfo : dRootInfo.getFChildren()) {
				if(dConfirmChildInfo.isChecked()){
					fItemNumbers.add(dConfirmChildInfo.getFItemNumber());
				}
			}
			dPersonInfo.setFProjectCode(dRootInfo.getFProjectCode());
			dPersonInfo.setFConfirmNumber(StringUtils.join(fItemNumbers, ","));
			reList.add(dPersonInfo);
		}
		return reList;
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: �ϴ���ʱ��Ϣ��������־��¼��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��9��28�� ����6:46:58
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_develop_hour));
		logInfo.setFActionName("confirm");
		logInfo.setFNote("dhconfirm");
		UIHelper.sendLogs(DHConfirmListActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHConfirmListActivity.this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dArrayList!=null && dArrayList.size() > 0){  //��չ۲����б�
			for (DHConfirmRootInfo dRootInfo : dArrayList) {
				dRootInfo.deleteObservers();
				if(dRootInfo.getFChildren()!=null && dRootInfo.getFChildren().size() > 0){
					for (DHConfirmChildInfo dConfirmChildInfo : dRootInfo.getFChildren()) {
						dConfirmChildInfo.deleteObservers();
					}
				}
			}
		}
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
