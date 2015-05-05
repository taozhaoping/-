package com.dahuatech.app.widget.utli;

/**
 * @ClassName RoundUtil
 * @Description Բ������
 * @author 21291
 * @date 2014��12��5�� ����1:56:55
 */
public class RoundUtil {

	/** 
	* @Title: checkInRound 
	* @Description: �������ľ����Ƿ���Բ�İ뾶��
	* @param @param sx
	* @param @param sy
	* @param @param r
	* @param @param x
	* @param @param y
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����1:57:20
	*/
	public static boolean checkInRound(float sx, float sy, float r, float x,float y) {
		return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
	}

}
