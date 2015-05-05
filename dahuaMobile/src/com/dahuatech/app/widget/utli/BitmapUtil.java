package com.dahuatech.app.widget.utli;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @ClassName BitmapUtil
 * @Description ͼƬ��������
 * @author 21291
 * @date 2014��12��5�� ����11:22:34
 */
public class BitmapUtil {

	/** 
	* @Title: zoom 
	* @Description: ����ͼƬ��������
	* @param @param bitmap
	* @param @param zf
	* @param @return     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����11:23:44
	*/
	public static Bitmap zoom(Bitmap bitmap, float zf) {
		Matrix matrix = new Matrix();		//ʵ��������
		matrix.postScale(zf, zf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
	}
	
	/** 
	* @Title: zoom 
	* @Description: ����ͼƬ��������
	* @param @param bitmap
	* @param @param wf
	* @param @param hf
	* @param @return     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014��12��8�� ����9:53:34
	*/
	public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
	}

}
