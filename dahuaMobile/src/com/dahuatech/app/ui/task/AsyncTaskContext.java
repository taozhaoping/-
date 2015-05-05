package com.dahuatech.app.ui.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.inter.ITaskContext;

/**
 * @ClassName AsyncTaskContext
 * @Description �첽���������
 * @author 21291
 * @date 2014��10��28�� ����1:54:10
 */
public class AsyncTaskContext {						
	private Context context;				 		//�����Ļ���
	private ITaskContext iTaskContext;				//��ͬ�ӿ�ʵ��
	private ProgressDialog dialog;   				//������
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public ITaskContext getiTaskContext() {
		return iTaskContext;
	}
	public void setiTaskContext(ITaskContext iTaskContext) {
		this.iTaskContext = iTaskContext;
	}

	//�ڲ��൥��ģʽ
	private static class singletonHolder {  
        private static AsyncTaskContext instance = new AsyncTaskContext();  
    }  
	private AsyncTaskContext() {}
	private static AsyncTaskContext asyncTaskContext=null;
	public static AsyncTaskContext getInstance(Context context,ITaskContext iTaskContext) {
		asyncTaskContext=singletonHolder.instance;
		asyncTaskContext.setContext(context);	
		asyncTaskContext.setiTaskContext(iTaskContext);
		return asyncTaskContext;
	}
	
	/** 
	* @Title: initDiglog 
	* @Description: ��ʼ��������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��28�� ����2:27:05
	*/
	private void initDiglog(){
		dialog = new ProgressDialog(context);
		dialog.setMessage(context.getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
	}
	
	/** 
	* @Title: callAsyncTask 
	* @Description: �����첽������
	* @param @param serviceUrl     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��28�� ����2:41:10
	*/
	public void callAsyncTask(String serviceUrl){
		initDiglog();
		new AsyncTaskRequest().execute(serviceUrl);
	}

	/**
	 * @ClassName AsyncTaskRequest
	 * @Description  �첽ִ������,��ȡ����ʵ��
	 * @author 21291
	 * @date 2014��10��28�� ����2:40:30
	 */
	private class AsyncTaskRequest extends AsyncTask<String, Void, Base> {
		// ��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // ��ʾ�ȴ���
		}

		// ��ɸ���UI����
		@Override
		protected void onPostExecute(Base result) {
			super.onPostExecute(result);
			iTaskContext.initBase(result);
			dialog.dismiss(); 	// ���ٵȴ���
		}

		// ��Ҫ����ɺ�ʱ����
		@Override
		protected Base doInBackground(String... params) {
			return iTaskContext.getDataByPost(params[0]);
		}
	}	
}
