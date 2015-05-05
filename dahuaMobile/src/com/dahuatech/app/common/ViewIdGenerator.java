package com.dahuatech.app.common;

import java.util.concurrent.atomic.AtomicInteger;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * {@link View#generateViewId()}Ҫ��API Level >= 17,����������ɼ�������API Level
 * <p>
 * �Զ��жϵ�ǰAPI Level,�����ȵ���{@link View#generateViewId()},��ʹ����������
 * {@link View#generateViewId()} ����,Ҳ�ܱ�֤���ɵ�IdΨһ
 * <p>
 * =============
 * <p>
 * while {@link View#generateViewId()} require API Level >= 17, this tool is compatibe with all API.
 * <p>
 * according to current API Level, it decide weather using system API or not.<br>
 * so you can use {@link ViewIdGenerator#generateViewId()} and
 * {@link View#generateViewId()} in the same time and don't worry about getting
 * same id
 * 
 * @ClassName ViewIdGenerator
 * @Description �Զ�����ֵ
 * @author 21291
 * @date 2014��6��17�� ����2:28:03
 */

public class ViewIdGenerator {

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	
	/** 
	* @Title: generateViewId 
	* @Description: �Զ�����VIEW��ͼID
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��10��9�� ����9:37:53
	*/
	public static int generateViewId() {
		 for (;;) {
		        final int result = sNextGeneratedId.get();
		        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
		        int newValue = result + 1;
		        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
		        if (sNextGeneratedId.compareAndSet(result, newValue)) {
		            return result;
		        }
		    }
	}
	
	
	/** 
	* @Title: hideSearchIcon 
	* @Description: ����������ťͼ��
	* @param @param view     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��10��9�� ����9:37:50
	*/
	@SuppressWarnings("unused")
	private static void hideSearchIcon(View view){
	   if (view instanceof ViewGroup)
	   {
	      ViewGroup group = (ViewGroup) view;
	      for (int i = 0; i < group.getChildCount(); ++i)
	      {
	         hideSearchIcon(group.getChildAt(i));
	      }
	   }
	   else if (view instanceof ImageView)
	   {
	      view.setVisibility(View.GONE);
	   }
	}
}
