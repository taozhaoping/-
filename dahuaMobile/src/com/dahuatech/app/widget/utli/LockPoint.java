package com.dahuatech.app.widget.utli;

/**
 * @ClassName LockPoint
 * @Description ��λ��ʵ��
 * @author 21291
 * @date 2014��12��5�� ����10:31:49
 */
public class LockPoint {
	//���״̬
	public static int STATE_NORMAL = 0; 			// δѡ��
	public static int STATE_CHECK = 1; 				// ѡ�� 
	public static int STATE_CHECK_ERROR = 2; 		// �Ѿ�ѡ��,���������
	
	public float x;							//������
	public float y;							//������
	public int state = 0;					//Ĭ��״̬
	public int index = 0;					//�±�
	
	public LockPoint() {
		
	}
	
	public LockPoint(float x,float y){
		this.x=x;
		this.y=y;
	}
}
