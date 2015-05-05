package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ContributionAwardInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * ���׽�����
* @Title: ContributionAwardBusiness.java 
* @Package com.dahuatech.app.business 
* @Description: TODO
* @date 2015-3-17 ����2:42:11 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class ContributionAwardBusiness extends
		BaseBusiness<ContributionAwardInfo> {

	public ContributionAwardBusiness(Context context) {
		super(context, ContributionAwardInfo.getContributionAwardInfo());
	}

	// ����ģʽ(�̲߳���ȫд��)
	private static ContributionAwardBusiness contributionAwardBusiness;

	public static ContributionAwardBusiness getContributionAwardBusiness(
			Context context, String serviceUrl) {
		if (contributionAwardBusiness == null) {
			contributionAwardBusiness = new ContributionAwardBusiness(context);
		}
		contributionAwardBusiness.setServiceUrl(serviceUrl);
		return contributionAwardBusiness;
	}
	
	/**
	 * 
	* @Title: getContributionAwardInfo 
	* @Description: ��ȡʵ����Ϣ
	* @param  @param taskParam
	* @param  @return   ���� 
	* @return ContributionAwardInfo    �������� 
	* @throws 
	* @author taozhaoping 26078
	* @author mail taozhaoping@gmail.com
	 */
	public ContributionAwardInfo getContributionAwardInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}

}
