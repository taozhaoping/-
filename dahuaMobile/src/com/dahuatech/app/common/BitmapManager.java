package com.dahuatech.app.common;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.dahuatech.app.AppException;
import com.dahuatech.app.api.ApiClient;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * @ClassName BitmapManager
 * @Description �첽�̼߳���ͼƬ������
 * ʹ��˵����
 * BitmapManager bmpManager;
 * bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.loading));
 * bmpManager.loadBitmap(imageURL, imageView);
 * @author 21291
 * @date 2014��4��16�� ����3:59:04
 */
public class BitmapManager {
	
	private static HashMap<String, SoftReference<Bitmap>> cache;  
    private static ExecutorService pool;  
    private static Map<ImageView, String> imageViews;  
    private Bitmap defaultBmp;  
    private Context context;

    static {  
        cache = new HashMap<String, SoftReference<Bitmap>>();  
        pool = Executors.newFixedThreadPool(5);  //�̶��̳߳�
        imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    }  
       
    //��ȡʵ������
    public static BitmapManager getInstance(Context context){
    	return BitmapManagerHolder.getInstanceNonParam(context);
    }
    
    public static BitmapManager getInstance(Context context,Bitmap def){
    	return BitmapManagerHolder.getInstanceWithParam(context,def);
    }
    
    //����ģʽ �̰߳�ȫд��
    private static class BitmapManagerHolder{
    	
    	private static final BitmapManager getInstanceNonParam(Context context){
    		return new BitmapManager(context);
    	}
    	
    	private static final BitmapManager getInstanceWithParam(Context context,Bitmap def){
    		return new BitmapManager(context,def);
    	}
    	
    }
   
    //���캯��
    private BitmapManager(Context context){
    	this.context=context;
    }
    
    private BitmapManager(Context context,Bitmap def) {
    	this.context=context;
    	this.defaultBmp = def;
    }
    
    /** 
    * @Title: loadBitmap 
    * @Description: ����ͼƬ
    * @param @param url
    * @param @param imageView     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��24�� ����10:29:12
    */
    public void loadBitmap(String url, ImageView imageView) {  
    	loadBitmap(url, imageView, this.defaultBmp, 0, 0);
    }
    
    /** 
    * @Title: loadBitmap 
    * @Description: ����ͼƬ-�����ü���ʧ�ܺ���ʾ��Ĭ��ͼƬ
    * @param @param url
    * @param @param imageView
    * @param @param defaultBmp     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��24�� ����10:28:57
    */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {  
    	loadBitmap(url, imageView, defaultBmp, 0, 0);
    }
 
    /** 
    * @Title: loadBitmap 
    * @Description: ����ͼƬ-��ָ����ʾͼƬ�ĸ߿�
    * @param @param url
    * @param @param imageView
    * @param @param defaultBmp
    * @param @param width
    * @param @param height     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��24�� ����10:28:41
    */
    public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp, int width, int height) {  
        imageViews.put(imageView, url);  
        Bitmap bitmap = getBitmapFromCache(url);  
   
        if (bitmap != null) {  
			//��ʾ����ͼƬ
            imageView.setImageBitmap(bitmap);  
        } else {  
        	//����SD���е�ͼƬ����
        	String filename = FileUtils.getFileName(url);
        	String filepath = imageView.getContext().getFilesDir() + File.separator + filename;
    		File file = new File(filepath);
    		if(file.exists()){
				//��ʾSD���е�ͼƬ����
    			Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(), filename);
        		imageView.setImageBitmap(bmp);
        	}else{
				//�̼߳�������ͼƬ
        		imageView.setImageBitmap(defaultBmp);
        		queueJob(url, imageView, width, height);
        	}
        }  
    }  
  
    /**
     * �ӻ����л�ȡͼƬ
     * @param url
     */
    public Bitmap getBitmapFromCache(String url) {  
    	Bitmap bitmap = null;
        if (cache.containsKey(url)) {  
            bitmap = cache.get(url).get();  
        }  
        return bitmap;  
    }  
    
    /** 
    * @Title: queueJob 
    * @Description: �������м���ͼƬ
    * @param @param url
    * @param @param imageView
    * @param @param width
    * @param @param height     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014��9��24�� ����10:28:15
    */
    public void queueJob(final String url, final ImageView imageView, final int width, final int height) {   
        final Handler handler = new Handler() {  
            @SuppressLint("HandlerLeak")
			public void handleMessage(Message msg) {  
                String tag = imageViews.get(imageView);  
                if (tag != null && tag.equals(url)) {  
                    if (msg.obj != null) {  
                        imageView.setImageBitmap((Bitmap) msg.obj);  
                        try {
                        	//��SD����д��ͼƬ����
							ImageUtils.saveImage(imageView.getContext(), FileUtils.getFileName(url), (Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    } 
                }  
            }  
        };  
  
        pool.execute(new Runnable() {   
            public void run() {  
                Message message = Message.obtain();  
                message.obj = downloadBitmap(url, width, height);  
                handler.sendMessage(message);  
            }  
        });  
    } 
 
    /** 
    * @Title: downloadBitmap 
    * @Description: ����ͼƬ-��ָ����ʾͼƬ�ĸ߿� 
    * @param @param url
    * @param @param width
    * @param @param height
    * @param @return     
    * @return Bitmap    
    * @throws 
    * @author 21291
    * @date 2014��9��24�� ����10:26:28
    */
    private Bitmap downloadBitmap(String url, int width, int height) {   
        Bitmap bitmap = null;
        try {
			//http����ͼƬ
			bitmap = ApiClient.getApiClient(context).getNetBitmap(url);
			if(width > 0 && height > 0) {
				//ָ����ʾͼƬ�ĸ߿�
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			} 
			//���뻺��
			cache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (AppException e) {
			e.printStackTrace();
		}
        return bitmap;  
    }  

}
