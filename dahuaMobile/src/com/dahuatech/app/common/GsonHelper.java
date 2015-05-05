package com.dahuatech.app.common;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @ClassName GsonHelper
 * @Description Gson������
 * @author 21291
 * @date 2014��10��30�� ����2:05:41
 */
public class GsonHelper {

	//�ڲ��൥��ģʽ,�ӳټ���,�̰߳�ȫ(java��class����ʱ�����),Ҳ�������ڴ�����
	private static class SingletonHolder {  
        private static Gson instance = new  GsonBuilder()
		.registerTypeAdapter(Date.class, new DateHelper())
		.serializeNulls().setDateFormat(DateFormat.LONG)
		.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		.setPrettyPrinting().create();
    }
	public static Gson getInstance() {
		return SingletonHolder.instance;
	}
}
