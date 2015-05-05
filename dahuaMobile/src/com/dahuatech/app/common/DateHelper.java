package com.dahuatech.app.common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @ClassName DateHelper
 * @Description ʱ�������
 * @author 21291
 * @date 2014��4��21�� ����10:55:54
 */
@SuppressLint("UseValueOf")
public class DateHelper implements JsonDeserializer<Date> {

	private static final String COMMON_DATE = "yyyy-MM-dd";
	private static final String COMPARE_DATE = "yyyy-MM-dd HH:mm";

	/**
	 * (�� Javadoc)
	 * <p>
	 * Title: Date�����л�
	 * </p>
	 * <p>
	 * Description: ��Date�������ͷ����л�������չ
	 * </p>
	 * 
	 * @param json
	 * @param typeOfT
	 * @param context
	 * @return
	 * @throws JsonParseException
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public Date deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
		String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
		Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
		Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
		String result = matcher.replaceAll("$2");
		return new Date(new Long(result));
	}

	/**
	 * @Title: getWeekOfYear
	 * @Description: ȡ��ָ�������ǵڼ���
	 * @param @param c ����ʵ��
	 * @param @param date
	 * @param @return
	 * @return int
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����3:55:21
	 */
	public static int getWeekOfYear(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @Title: getWeekOfDate
	 * @Description: ����ʱ�䣬��ȡ�������ܼ�
	 * @param @param c ����ʵ��
	 * @param @param date ʱ��ʵ��
	 * @param @return
	 * @return String
	 * @throws
	 * @author 21291
	 * @date 2014��11��18�� ����4:21:45
	 */
	public static String getWeekOfDate(Calendar c, Date date) {
		String[] weekDaysName = { "FSunday", "FMonday", "FTuesday",
				"FWednesday", "FThursday", "FFriday", "FSaturday" };
		c.setTime(date);
		int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	/**
	 * @Title: getNumWeeksForYear
	 * @Description: �õ�ĳһ���ܵ�����
	 * @param @param c ����ʵ��
	 * @param @param year ���
	 * @param @return
	 * @return int
	 * @throws
	 * @author 21291
	 * @date 2014��11��20�� ����1:15:08
	 */
	public static int getNumWeeksForYear(Calendar c, int year) {
		c.set(year, 0, 1);
		return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: �õ�ĳ��ĳ�ܵĵ�һ��
	 * @param @param c ����ʵ��
	 * @param @param year ���
	 * @param @param week ����
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����3:56:52
	 */
	public static Date getFirstDayOfWeek(Calendar c, int year, int week) {
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(c, cal.getTime());
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: ȡ��ָ�����������ܵĵ�һ��
	 * @param @param c ����ʵ��
	 * @param @param date ��ǰ����
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����3:59:49
	 */
	public static Date getFirstDayOfWeek(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY); // �����Ǵ���һ��ʼΪ��һ��
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @Title: getLastDayOfWeek
	 * @Description: ȡ��ָ�����������ܵ����һ��
	 * @param @param c ����ʵ��
	 * @param @param date ��ǰ����
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����4:01:05
	 */
	public static Date getLastDayOfWeek(Calendar c, Date date) {
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // �����Ǵ�����Ϊ���һ��
		return c.getTime();
	}

	/**
	 * @Title: getFirstDayOfWeek
	 * @Description: ȡ�õ�ǰ���������ܵĵ�һ��
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����10:43:43
	 */
	public static Date getFirstDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY); // �����Ǵ���һ��ʼΪ��һ��
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @Title: getLastDayOfWeek
	 * @Description: ȡ�õ�ǰ���������ܵ����һ��
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����10:44:15
	 */
	public static Date getLastDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // �����Ǵ�����Ϊ���һ��
		return c.getTime();
	}

	/**
	 * @Title: getCurrYearFirst
	 * @Description: ��ȡĳ���һ������
	 * @param @param year
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����10:56:50
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(Calendar.YEAR, year);
		Date currYearFirst = c.getTime();
		return currYearFirst;
	}

	/**
	 * @Title: getCurrYearLast
	 * @Description: ��ȡĳ�����һ������
	 * @param @param year
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����10:56:41
	 */
	public static Date getCurrYearLast(int year) {
		Calendar c = new GregorianCalendar();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = c.getTime();
		return currYearLast;
	}

	/**
	 * @Title: getDHWeekList
	 * @Description: ��ȡָ�������������Ϣ
	 * @param @param c ����ʵ��
	 * @param @param year ָ�����
	 * @param @param weekCounts ����
	 * @param @return
	 * @return List<DHWeekInfo>
	 * @throws
	 * @author 21291
	 * @date 2014��10��24�� ����4:02:10
	 */
	public static List<DHWeekInfo> getDHWeekList(Calendar c, int year,int weekCounts) {
		List<DHWeekInfo> list = new ArrayList<DHWeekInfo>();
		final DateFormat sdf = new SimpleDateFormat(COMMON_DATE, Locale.CHINA);
		for (int i = weekCounts; i > 0; i--) {
			DHWeekInfo dhWeekInfo = new DHWeekInfo();
			dhWeekInfo.setFYear(year);
			dhWeekInfo.setFIndex(i);
			dhWeekInfo.setFStartDate(sdf.format(getFirstDayOfWeek(c, year,i - 1)));
			dhWeekInfo.setFEndDate(sdf.format(getLastDayOfWeek(c,getFirstDayOfWeek(c, year, i - 1))));
			list.add(dhWeekInfo);
		}
		return list;
	}

	/**
	 * @Title: dateCompare
	 * @Description: ����ʱ��Ƚ�
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return Date
	 * @throws
	 * @author 21291
	 * @date 2015��1��16�� ����10:40:06
	 */
	public static int dateCompare(Calendar c, String date1, String date2) {
		final DateFormat df = new SimpleDateFormat(COMPARE_DATE, Locale.CHINA);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
