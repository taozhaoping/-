package com.dahuatech.app.inter;

import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;

/**
 * @ClassName SpinnerListener
 * @Description �Զ������������¼�
 * @author 21291
 * @date 2014��9��12�� ����3:05:55
 */
public interface ISpinnerListener {
	public void rejectOk(int n,RejectNodeInfo reNodeInfo);
	public void dHWeekOk(int n,String itemText,DHWeekInfo dhWeekInfo);
	public void adAmapOk(int n,AdAmapInfo adAmapInfo);
    public void cancelled();
}
