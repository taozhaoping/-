package com.dahuatech.app.ui.expense.taxi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseTaxiListAdapter;
import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.GpsRowIdInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DelSlideActionSheet;
import com.dahuatech.app.common.DelSlideActionSheet.OnActionSheetSelected;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.IListViewonSingleTapUpListenner;
import com.dahuatech.app.inter.IOnDeleteListioner;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.DelSlideListView;

/**
 * @ClassName ExpenseTaxiListActivity
 * @Description �ϴ���Ϣ�б�ҳ��
 * @author 21291
 * @date 2014��5��13�� ����2:08:10
 */
public class ExpenseTaxiListActivity extends MenuActivity implements IOnDeleteListioner, IListViewonSingleTapUpListenner,OnActionSheetSelected, OnCancelListener {

	private static final int ACTIVITY_ADD = 0;
	private static final int ACTIVITY_ITEMVIEW = 1;
	
	private Button btnUpload,btnList,btnNew;
	private Button btnCheck,btnCheckCancle,btnBatchUpload;  
	private DelSlideListView mDelSlideListView;
	private List<Map<String, Object>> mData;
	private GPSDbAdapter mDbHelper;
	private ExpenseTaxiListAdapter exAdapter;
	private Cursor mGpsCursor;
	
	private int delId = 0;			 //��ɾ���±�
	private String globalUploadFlag; //�Ƿ��ϴ�����������־   0��û���ϴ� 1���Ѿ��ϴ�
	private String fItemNumber;		 //Ա����
	
	private String serviceUrl;  //�����ַ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//���ؼ���
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		setContentView(R.layout.expense_taxilist);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSETAXILISTACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		globalUploadFlag="0";  //Ĭ���Ǵ��ϴ���¼
		renderListView();
		
		//���ϴ�
		btnUpload=(Button) findViewById(R.id.expense_taxilist_upload);
		btnUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnUpload.setBackgroundResource (R.drawable.tabs_active);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnList.setBackgroundResource (R.drawable.tabs_default);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default);
				
				globalUploadFlag="0";  //���ϴ���¼
				renderListView();
			}
		});
		
		//���ϴ�
		btnList=(Button) findViewById(R.id.expense_taxilist_list);
		btnList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnList.setBackgroundResource (R.drawable.tabs_active);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnUpload.setBackgroundResource (R.drawable.tabs_default);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default_left);
				
				globalUploadFlag="1";  //�Ѿ��ϴ���¼
				renderListView();
			}
		});
		
		//��������
		btnNew=(Button) findViewById(R.id.expense_taxilist_new);
		btnNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��������
				Intent intent = new Intent();
				intent.setClass(ExpenseTaxiListActivity.this, ExpenseTaxiInputActivity.class);
				startActivityForResult(intent, ACTIVITY_ADD);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});	
	}
	
	/** 
	* @Title: renderListView 
	* @Description: ��ʼ�������б���Ϣ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��13�� ����2:15:08
	*/
	private void renderListView(){
		mDelSlideListView=(DelSlideListView)this.findViewById(R.id.tasklist_ListView);
		mGpsCursor = mDbHelper.getGpsdbByUploadFlag(globalUploadFlag);
        mData = getData();
        exAdapter = new ExpenseTaxiListAdapter(this,mData,R.layout.expense_taxilistlayout);
        mDelSlideListView.setAdapter(exAdapter);
        mDelSlideListView.setmOnDeleteListioner(this);
		mDelSlideListView.setThisonSingleTapUpListenner(this);
		exAdapter.setOnDeleteListioner(this);
		
		//ȫѡ
		btnCheck=(Button) findViewById(R.id.expense_taxilist_All);
		btnCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����list�ĳ��ȣ���exAdapter�е�mapֵȫ����Ϊtrue  
                for (int i = 0; i < mData.size(); i++) {  
                	exAdapter.getIsSelected().put(i, true);  	
                }  
            	exAdapter.swapItems(mData);
			}
		});
		
		//ȫѡȡ��
		btnCheckCancle=(Button) findViewById(R.id.expense_taxilist_Cancle);
		btnCheckCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����list�ĳ��ȣ�����ѡ�İ�ť��Ϊδѡ  
                for (int i = 0; i < mData.size(); i++) {  
                    if (exAdapter.getIsSelected().get(i)) {  
                    	exAdapter.getIsSelected().put(i, false);  
                    }  
                }  
                exAdapter.swapItems(mData);
			}
		});
		
		//�����ϴ�
		btnBatchUpload=(Button) findViewById(R.id.expense_taxilist_BatchUpload);
		if("0".equals(globalUploadFlag)){  //�����ϴ�
			btnBatchUpload.setText(R.string.expense_itemview_upload);
		}
		else {
			btnBatchUpload.setText(R.string.expense_taxilist_delete);
		}
		btnBatchUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(globalUploadFlag)){  //�����ϴ�����
					//��ʼ���ϴ������
					List<GpsRowIdInfo> gpsRowIdInfos=new ArrayList<GpsRowIdInfo>();
					//����list�ĳ��ȣ�����ѡ�İ�ť��Ϊδѡ  
	                for (int i = 0; i < mData.size(); i++) {  
	                    if (exAdapter.getIsSelected().get(i)) {  
	                    	GpsRowIdInfo gpsRowIdInfo=new GpsRowIdInfo();
	                    	gpsRowIdInfo.setRowId((String)mData.get(i).get("id"));
	                    	gpsRowIdInfo.setUserId(fItemNumber);
	                    	gpsRowIdInfo.setStartTime((String)mData.get(i).get("startTime"));
	                    	gpsRowIdInfo.setEndTime((String)mData.get(i).get("endTime"));
	                    	gpsRowIdInfo.setStartLocation((String)mData.get(i).get("startLocation"));
	                    	gpsRowIdInfo.setEndLocation((String)mData.get(i).get("endLocation"));   	
	                    	gpsRowIdInfo.setStartPlace((String)mData.get(i).get("startAddress"));
	                    	gpsRowIdInfo.setEndPlace((String)mData.get(i).get("endAddress"));                    	
	                    	gpsRowIdInfo.setCause("");
	                    	gpsRowIdInfo.setAutoFlag((String)mData.get(i).get("autoFlag"));
	                    	gpsRowIdInfo.setAmount((String)mData.get(i).get("amount"));
	                    	
	                    	gpsRowIdInfos.add(gpsRowIdInfo);
	                    } 
	                } 
	                if(gpsRowIdInfos.size() > 0){
	                	batchUpload(gpsRowIdInfos);
	                }
	                else {
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_batch_fail);
					}
            	}
				else {  //����ɾ������
					List<Map<String, Object>> deleteList=new ArrayList<Map<String, Object>>();//��ʼ����ɾ�������
	                for (int i = 0; i < mData.size(); i++) {  
	                    if (exAdapter.getIsSelected().get(i)) {  
	                      	deleteList.add(mData.get(i));
	                    } 
	                } 
	                if(deleteList.size() > 0){	
	                	for (int i = 0; i < deleteList.size(); i++) {
	                		//ɾ������
	                		Map<String, Object> delObj= deleteList.get(i);               		
	            			mDbHelper.deleteGpsdb(Integer.parseInt(delObj.get("id").toString()));
	            			mData.remove(delObj);
						}
	                	exAdapter.initDate();
	                	exAdapter.swapItems(mData);
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_delete_success);
	                }
	                else {
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_delete_fail);
					} 
				}	
			}
		});
		
		OnItemClickListener listener =  new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				exAdapter.setSelectItem(position);
				exAdapter.notifyDataSetInvalidated(); //����UI����
				
				String id = (String) mData.get(position).get("id");
				String startTime = (String) mData.get(position).get("startTime");
				String endTime = (String) mData.get(position).get("endTime");
				String uploadFlag = (String) mData.get(position).get("uploadFlag");
				String startAddress = (String) mData.get(position).get("startAddress");
				String endAddress = (String) mData.get(position).get("endAddress");
				String cause = (String) mData.get(position).get("cause");
				String autoFlag = (String)mData.get(position).get("autoFlag");
				String startLocation = (String)mData.get(position).get("startLocation");
				String endLocation = (String)mData.get(position).get("endLocation");
				String amount=(String)mData.get(position).get("amount");
				globalUploadFlag=uploadFlag;
				
				Intent intent = new Intent();
				intent.setClass(ExpenseTaxiListActivity.this, ExpenseTaxiItemViewActivity.class);
				
				intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
				intent.putExtra(GPSDbAdapter.KEY_ROWID, id);
				intent.putExtra(GPSDbAdapter.KEY_STARTTIME, startTime);
				intent.putExtra(GPSDbAdapter.KEY_ENDTIME, endTime);
				intent.putExtra(GPSDbAdapter.KEY_UPLOADFLAG, uploadFlag);
				intent.putExtra(GPSDbAdapter.KEY_STARTADDRESS, startAddress);
				intent.putExtra(GPSDbAdapter.KEY_ENDADDRESS, endAddress);	
				intent.putExtra(GPSDbAdapter.KEY_CAUSE, cause);
				intent.putExtra(GPSDbAdapter.KEY_AUTOFLAG, autoFlag);
				intent.putExtra(GPSDbAdapter.KEY_STARTLOCATION, startLocation);
				intent.putExtra(GPSDbAdapter.KEY_ENDLOCATION, endLocation);
				intent.putExtra(GPSDbAdapter.KEY_AMOUNT, amount);
				startActivityForResult(intent, ACTIVITY_ITEMVIEW); 
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
        };
        mDelSlideListView.setOnItemClickListener(listener);
	}
	
	/** 
	* @Title: getData 
	* @Description: ��ȡ���ݼ���
	* @param @return     
	* @return List<Map<String,Object>>    
	* @throws 
	* @author 21291
	* @date 2014��5��13�� ����2:15:39
	*/
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(mGpsCursor.moveToFirst();!mGpsCursor.isAfterLast();mGpsCursor.moveToNext()){
			
			int startTimeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTTIME);  
			int endTimeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDTIME);
			int uploadFlagIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_UPLOADFLAG);
			int autoFlagIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_AUTOFLAG);
			int idIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ROWID);
			int startAddressIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTADDRESS);
			int endAddressIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDADDRESS);
			int causeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_CAUSE);
			int startLocationIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTLOCATION); 
			int endLocationIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDLOCATION);
			int amountIndex=mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_AMOUNT);
			
			Map<String, Object> map = new HashMap<String, Object>();
			String startTime = mGpsCursor.getString(startTimeIndex);  
			String endTime = mGpsCursor.getString(endTimeIndex);
			String uploadFlag = mGpsCursor.getString(uploadFlagIndex);
			String autoFlag = mGpsCursor.getString(autoFlagIndex);
			String id = mGpsCursor.getString(idIndex);
			String startAddress = mGpsCursor.getString(startAddressIndex);
			String endAddress = mGpsCursor.getString(endAddressIndex);
			String cause = mGpsCursor.getString(causeIndex);
			String startLocation = mGpsCursor.getString(startLocationIndex);
			String endLocation = mGpsCursor.getString(endLocationIndex);
			String amount=mGpsCursor.getString(amountIndex);
			
			map.put("startTime", startTime);//��ʼʱ��
			map.put("endTime", endTime);    //����ʱ��
			map.put("uploadFlag", uploadFlag);//�Ƿ�ɹ��ϴ���������
			map.put("autoFlag",autoFlag); //�ֶ�����  manual�������Զ��ռ����� automatic
			map.put("id", id); //id
			map.put("startAddress", startAddress);//��ʼ�ص�
			map.put("endAddress", endAddress); //�����ص�
			map.put("cause", cause); //����ԭ��
			map.put("startLocation", startLocation);//��ʼ����
			map.put("endLocation", endLocation);//��������
			map.put("amount", amount);			//���
			
			list.add(map);	
		}	
		mGpsCursor.close();
		return list;
	}
	
	/** 
	* @Title: batchUpload 
	* @Description: �����ϴ��˳���¼����ϵͳ
	* @param @param gpsRowIdInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��5��21�� ����11:32:55
	*/
	@SuppressLint("HandlerLeak")
	private void batchUpload(final List<GpsRowIdInfo> gpsRowIdInfos){
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if(msg.what==1 && msg.obj!=null){
					ResultMessage res=(ResultMessage)msg.obj;
					if(res.isIsSuccess()){
						List<String> hadUpload = new ArrayList<String>(Arrays.asList(res.getResult().split(",")));
						if(hadUpload.size() > 0){
							for (String item : hadUpload) {
								int rowId = Integer.parseInt(item);
								//�����ϴ���־Ϊ�Ѿ��ϴ�
								mDbHelper.updateGpsdb(rowId, "1");
							}
						}
						UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_success);		
						
						// �ӳ�2��ˢ��ҳ��
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	renderListView();
				            }
				        }, 2000);
					}
					else {
						UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_fail_reason+res.getResult());
					}		
				}
				else {
					UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_fail);
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseTaxiListActivity.this);
					ExpenseBusiness exBusiness  = (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
					ResultMessage res=exBusiness.batchUpload(gpsRowIdInfos);
					msg.obj = res;
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case ACTIVITY_ADD:
				globalUploadFlag="0";  //���������Ǵ��ϴ���
				btnUpload.setBackgroundResource (R.drawable.tabs_active);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnList.setBackgroundResource (R.drawable.tabs_default);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default);
				break;
			case ACTIVITY_ITEMVIEW:
				break;
			default:
				break;
		}
		renderListView();
	}

	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(int whichButton) {
		switch (whichButton) {
			case 0:
				if(delId==mData.size()-1){
					exAdapter.getIsSelected().put(delId, false); 
				}
				else{
					if(exAdapter.getIsSelected().get(delId+1)){
						exAdapter.getIsSelected().put(delId+1, false);  
					}
					else {
						exAdapter.getIsSelected().put(delId, false);  
					}
				}				
				//ɾ������
				String dataId=mData.get(delId).get("id").toString();		
				int rowId = Integer.parseInt(dataId);
				mDbHelper.deleteGpsdb(rowId);
				mData.remove(delId);
				mDelSlideListView.deleteItem();
				exAdapter.swapItems(mData);				
				break;
			case 1:
				break;
			default:
				break;
		}	
	}
	@Override
	public boolean isCandelete(int position) {
		return true;
	}

	@Override
	public void onDelete(int ID) {
		delId = ID;
		DelSlideActionSheet.showSheet(this, this, this);		
	}
	
	@Override
	public void onBack() {}

	@Override
	public void onCancel(DialogInterface dialog) {}

	@Override
	public void onSingleTapUp() {}
}
