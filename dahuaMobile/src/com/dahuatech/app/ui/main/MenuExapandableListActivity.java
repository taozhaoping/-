package com.dahuatech.app.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

/**
 * @ClassName MenuExapandableListActivity
 * @Description �ҵ���ˮ����˵�����
 * @author 21291
 * @date 2014��10��16�� ����5:17:35
 */
public class MenuExapandableListActivity extends BaseExapandableListActivity {

	private SharedPreferences sp;         //�����ļ�
	private SlidingMenu slidingMenu;	  //�������
	protected CommonMenu commonMenu;      //��ͬ�Ĳ˵���
	
	private Menu absMenu;				  //�˵���
	private String arrows="left";		  //��ͷͼ��
	
	private int rightCount=0;			  //�ұ���ʾ����
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
			
		int width = getPixelsWidth(); 
		final int leftDimen=(int)(width * 2 / 3);		//�����ʾ���
		final int rightDimen=(int)(width * 1 / 3);		//�ұ���ʾ���
		
		//���ò�߲˵���
		slidingMenu=new SlidingMenu(this);	
		slidingMenu.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				if(rightCount != 0){
					setMenuIcon("left");	
				}
			}
		});
		
		slidingMenu.setOnOpenListener(new OnOpenListener(){
			@Override
			public void onOpen() {
				slidingMenu.setBehindWidth(leftDimen);  //��߲�������
			}
		});
		
		slidingMenu.setSecondaryOnOpenListner(new OnOpenListener(){
			@Override
			public void onOpen() {
				slidingMenu.setBehindWidth(rightDimen);  //�ұ߲�������
				if (!slidingMenu.isSecondaryMenuShowing() && rightCount==0) {
					setMenuIcon("right");
				}
				rightCount++;
			}
		});
				
		commonMenu=CommonMenu.getCommonMenu(MenuExapandableListActivity.this,sp,slidingMenu,"other",leftDimen);
		commonMenu.initSlidingMenu();
		commonMenu.initLeftButton();
		commonMenu.initRightButton();
	}
	
	@Override
	public void onBackPressed() {
		if (slidingMenu.isMenuShowing()) {
			slidingMenu.showContent();
			setMenuIcon("left");
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.absMenu=menu;
		getSupportMenuInflater().inflate(R.menu.main, menu);
		if("left".equals(arrows)){
			rightCount=0;
			menu.findItem(R.id.menu_arrows).setIcon(R.drawable.menu_left_arrows);
		}
		else{
			menu.findItem(R.id.menu_arrows).setIcon(R.drawable.menu_right_arrows);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) {
			case R.id.menu_arrows:   //��ͷ
				if (!slidingMenu.isSecondaryMenuShowing()) {
					slidingMenu.showSecondaryMenu();
					setMenuIcon("right");
				}
				return true;
	
			case android.R.id.home:  //������һ��
				commonMenu.toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: ��ȡ��Ļ���ؿ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��12��2�� ����11:21:35
	*/
	private int getPixelsWidth(){
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: setMenuIcon 
	* @Description: ����ͼ�귽��
	* @param @param direction     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��12��19�� ����3:34:19
	*/
	private void setMenuIcon(String direction){
		arrows=direction;
		absMenu.clear();
		onCreateOptionsMenu(absMenu);
	}
}
