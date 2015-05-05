package com.dahuatech.app.widget.utli;

/**
 * @ClassName MathUtil
 * @Description ����������
 * @author 21291
 * @date 2014��12��5�� ����1:15:50
 */
public class MathUtil {
	/** 
	* @Title: distance 
	* @Description: ���������ľ���  ����ֵ�͵�ƽ����
	* @param @param x1
	* @param @param y1
	* @param @param x2
	* @param @param y2
	* @param @return     
	* @return double    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����1:16:39
	*/
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
	}
	
	/** 
	* @Title: pointTotoDegrees 
	* @Description: �����a(x,y)�ĽǶ�
	* @param @param x
	* @param @param y
	* @param @return     
	* @return double    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����1:20:43
	*/
	public static double pointTotoDegrees(double x, double y) {
		return Math.toDegrees(Math.atan2(x, y));
	}

}
