package com.dahuatech.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppGuide
 * @Description ����ҳ��Activity
 * @author 21291
 * @date 2014��9��28�� ����10:40:11
 */
public class AppGuide extends Activity implements OnClickListener {
	private ViewPager viewPager;						//ҳ������
	private ImageView[] dots;							//�ײ�ͼƬ
	private List<View> views;							//Tabҳ���б�
	private int currIndex;								//��ǰҳ�����
	private View view1,view2;							//����ҳ��
    private static final int picsLength=2;   			//ͼƬ����
    private Button fStart;								//�����ť
    
    private int mCount;									//��ʾ����
    private SharedPreferences sp;  						//�����ļ�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_viewpager);
		sp = getSharedPreferences(AppContext.GUIDEANDWELCOME_CONFIG_FILE,MODE_PRIVATE);
		initViewPager();
	}
	
	/** 
	* @Title: initViewPager 
	* @Description: ��ʼ��ҳ������
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��20�� ����4:37:50
	*/
	@SuppressLint("InflateParams")
	private void initViewPager(){
		currIndex=0;
		
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.guide_viewpager_lay1, null);
		view2=inflater.inflate(R.layout.guide_viewpager_lay2, null);
		views.add(view1);
		views.add(view2);
		
		viewPager=(ViewPager)findViewById(R.id.guide_viewpager);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(currIndex);  
		
		initImageView();
	}
	
	/** 
	* @Title: initImageView 
	* @Description: ��ʼ���ײ�ͼƬ
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��11��20�� ����5:06:21
	*/
	private void initImageView() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.guide_viewpager_layout);  
		dots=new ImageView[picsLength];
		for (int i = 0; i < dots.length; i++) {
			dots[i]=(ImageView)layout.getChildAt(i);
			dots[i].setEnabled(false); 	//Ĭ��Ϊ��ɫ 
			dots[i].setOnClickListener(this);  
			dots[i].setTag(i);				//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ  
		}
		dots[currIndex].setEnabled(true);//����Ϊ��ɫ����ѡ��״̬  
	}
	
	/**
	 * @ClassName MyViewPagerAdapter
	 * @Description �Զ�����������
	 * @author 21291
	 * @date 2014��11��20�� ����4:53:06
	 */
	private class MyViewPagerAdapter  extends PagerAdapter{
		private List<View> mListViews;
		
		private MyViewPagerAdapter(List<View> listViews){
			this.mListViews=listViews;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position)); //ɾ��ҳ��
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0); //���ҳ��
			 
			 if(position==1){
				 fStart=(Button)findViewById(R.id.guide_viewpager_start);
				 fStart.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							redirectTo();
						}
					});
			 }
			 return mListViews.get(position);	 
		}
		
		@Override
		public int getCount() {
			return mListViews.size(); //����ҳ��������  
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}	
	}
	
	/**
	 * @ClassName MyOnPageChangeListener
	 * @Description �����������¼�
	 * @author 21291
	 * @date 2014��11��20�� ����4:53:17
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			setCurDot(arg0);  
		}
	}

    /** 
    * @Title: setCurView 
    * @Description: ���õ�ǰ������ҳ  
    * @param @param position     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��11��21�� ����9:38:17
    */
    private void setCurView(int position)  
    {  
        if (position < 0 || position >= dots.length) {  
            return;  
        }  
        viewPager.setCurrentItem(position);  
    } 
    
    /** 
    * @Title: setCurDot 
    * @Description: ��ֻ��ǰ����С���ѡ��  
    * @param @param positon     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��11��21�� ����9:41:34
    */
    private void setCurDot(int positon)  
    {  
        if (positon < 0 || positon > picsLength - 1 || currIndex == positon) {  
            return;  
        }  
        dots[positon].setEnabled(true);  
        dots[currIndex].setEnabled(false);  
        currIndex = positon;  
    }  
	
	@Override
	public void onClick(View v) {
		int position = (Integer)v.getTag();  
        setCurView(position);  
        setCurDot(position);  
	}
	
	/** 
    * @Title: redirectTo 
    * @Description: ��ת����ӭ����
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��28�� ����11:10:25
    */
    private void redirectTo(){  
    	UIHelper.showWelcome(this);
    	closeGuide();
    }
	    
    /** 
    * @Title: closeGuide 
    * @Description: �ر�����ҳ
    * @param      
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��28�� ����11:27:26
    */
    private void closeGuide(){
    	mCount = sp.getInt(AppContext.IS_FIRST_COUNT_KEY,0);
    	mCount++;
    	sp.edit().putInt(AppContext.IS_FIRST_COUNT_KEY, mCount).commit();
    	finish();
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		if (keyCode == KeyEvent.KEYCODE_BACK) { //��ط��ؼ������Ұ�����ͼ���
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
	}
}
