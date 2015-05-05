package com.dahuatech.app.ui.develop.hour;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHComfirmListPersonAdapter;
import com.dahuatech.app.bean.develophour.DHConfirmListChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonJsonInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonParamInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListRootInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DHConfirmListPersonActivity
 * @Description �з���ʱȷ���б���Ա��Ϣ
 * @author 21291
 * @date 2014��11��5�� ����3:03:38
 */
public class DHConfirmListPersonActivity extends MenuActivity {

	private ExpandableListView exlistView;  						//�б�ؼ�
	private ProgressDialog dialog;      							//������
	
	private DevelopHourBusiness dBusiness;							//ҵ���߼���
	private DHComfirmListPersonAdapter dAdapter;					//������������
	private List<DHConfirmListRootInfo> dArrayList;					//����Դ
	
	private String serviceUrl;  									//�����ַ
	private AppContext appContext; 									//ȫ��Context
	
	private Type listType;											//�����Ӽ�����
	private Gson gson;												//gsonʵ��
	
	private String fProjectNumber;									//��Ŀ����Ա����
	private int fWeekIndex;											//�ڼ���
	private int fCurrentYear;										//��ǰ���
	private String fProjectCode;  									//��Ŀ���
	private String fConfrimNumber;  								//ȷ����ԱԱ����     
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dh_confirm_list_person);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		//��ʼ��ȫ�ֱ���
		appContext=(AppContext)getApplication();
		//�ж��Ƿ�����������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//��ȡ������Ϣ
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fProjectNumber=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER);
			fWeekIndex=extras.getInt(AppContext.DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX,0);
			fCurrentYear=extras.getInt(AppContext.DEVELOP_HOURS_CONFIRM_PASS_YEAR,0);
			fProjectCode=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE);
			fConfrimNumber=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER);
		}
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHCONFIRMLISTPERSONACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		initListView();
		new getListAsync().execute();
	}
	
	/** 
	* @Title: initListView 
	* @Description: ��ʼ���б�ҳ��ؼ�
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����3:27:04
	*/
	private void initListView(){
		exlistView=(ExpandableListView)findViewById(R.id.dh_confirm_list_person_ExpandableListView);
	
		//��ʼ��ҵ���߼���
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHConfirmListPersonActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
		dArrayList=new ArrayList<DHConfirmListRootInfo>();
		
		listType= new TypeToken<ArrayList<DHConfirmListChildInfo>>(){}.getType();
		gson=GsonHelper.getInstance();
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description �첽��ȡʵ�弯����Ϣ
	 * @author 21291
	 * @date 2014��11��5�� ����3:31:11
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<DHConfirmListPersonJsonInfo>>{
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected List<DHConfirmListPersonJsonInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// ��ɸ���UI����
		@Override
		protected void onPostExecute(List<DHConfirmListPersonJsonInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // ���ٵȴ���
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description:  ��ȡʵ�弯����Ϣ
	* @param @return     
	* @return List<DHConfirmListPersonJsonInfo>    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����3:54:23
	*/
	private List<DHConfirmListPersonJsonInfo> getListByPost(){
		DHConfirmListPersonParamInfo dPersonParamInfo=DHConfirmListPersonParamInfo.getDHConfirmListPersonParamInfo();
		dPersonParamInfo.setFProjectNumber(fProjectNumber);
		dPersonParamInfo.setFWeekIndex(fWeekIndex);
		dPersonParamInfo.setFYear(fCurrentYear);
		dPersonParamInfo.setFProjectCode(fProjectCode);
		dPersonParamInfo.setFConfrimNumber(fConfrimNumber);
		
		dBusiness.setServiceUrl(serviceUrl);
		return dBusiness.getDHConfirmListPerson(dPersonParamInfo);
	}

	/** 
	* @Title: renderListView 
	* @Description: ��ʼ���б���
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����3:55:47
	*/
	private void renderListView(final List<DHConfirmListPersonJsonInfo> listData){
		if(listData.size()==0){ //û������
			UIHelper.ToastMessage(DHConfirmListPersonActivity.this, getString(R.string.dh_confrim_list_person_netparseerror),3000);
			return;
		}
		getPersonList(listData);
		dAdapter = new DHComfirmListPersonAdapter(DHConfirmListPersonActivity.this,dArrayList,R.layout.dh_confirm_list_person_root,R.layout.dh_confirm_list_person_child); //��������������
		exlistView.setAdapter(dAdapter);
		
		for(int i=0; i < dAdapter.getGroupCount(); i++){
			exlistView.expandGroup(i);
		}
		
		exlistView.setOnGroupClickListener(new OnGroupClickListener() {  //������

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {	
				return false;
			}
	    });
		
		exlistView.setOnChildClickListener(new OnChildClickListener() {  //������

	        @Override
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {  	
	        	return true;
	        }
	    });
		
		exlistView.setOnGroupExpandListener(new OnGroupExpandListener() {	
			int previousItem = -1;
			public void onGroupExpand(int groupPosition) {
				if(groupPosition != previousItem )
           		 exlistView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });
 
		exlistView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {
            }
        });
	}
	
	/** 
	* @Title: getPersonList 
	* @Description: ��ȡÿ�˾��幤ʱ��Ϣ
	* @param @param list     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����3:59:57
	*/
	private void getPersonList(List<DHConfirmListPersonJsonInfo> list){	
		for (DHConfirmListPersonJsonInfo dPersonJsonInfo : list) {
			DHConfirmListRootInfo dConfirmListRootInfo=new DHConfirmListRootInfo();
			dConfirmListRootInfo.setFTypeId(dPersonJsonInfo.getFTypeId());
			dConfirmListRootInfo.setFTypeName(dPersonJsonInfo.getFTypeName());
			dConfirmListRootInfo.setFHours(dPersonJsonInfo.getFHours());
			if(!StringUtils.isEmpty(dPersonJsonInfo.getFSubChilds())){
				getFSubChilds(dPersonJsonInfo.getFSubChilds(),dConfirmListRootInfo);
			}
			dArrayList.add(dConfirmListRootInfo);
		}
	}
	
	/** 
	* @Title: getFSubChilds 
	* @Description: ��ȡ���༯��
	* @param @param fSubEntrys ���༯���ַ���
	* @param @param dRootInfo  ����ʵ��       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��5�� ����4:03:35
	*/
	private void getFSubChilds(String fSubEntrys,DHConfirmListRootInfo dRootInfo){
		try {
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DHConfirmListChildInfo> dList=gson.fromJson(jsonArray.toString(), listType);
			dRootInfo.setFChildren(dList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHConfirmListPersonActivity.this);
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
