package com.dahuatech.app.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * @ClassName StringUtils
 * @Description �ַ����������߰�
 * @author 21291
 * @date 2014��4��16�� ����2:58:13
 */

public class StringUtils {
	
	public static final String STRING_EMPTY = "";
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	@SuppressLint("SimpleDateFormat")
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		}
	};

	@SuppressLint("SimpleDateFormat")
	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		}
	};

	/** 
	* @Title: toDate 
	* @Description: ���ַ���תλ������������ 
	* @param @param sdate
	* @param @return     
	* @return Date    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:00:23
	*/
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/** 
	* @Title: toShortDate 
	* @Description: ���ַ���תλ����������
	* @param @param sdate
	* @param @return     
	* @return Date    
	* @throws 
	* @author 21291
	* @date 2014��9��16�� ����12:07:58
	*/
	public static Date toShortDate(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/** 
	* @Title: toDateString 
	* @Description: ����������ת��Ϊ�ַ���
	* @param @param date
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:08:49
	*/
	public static String toDateString(Date date){
		if(date==null)
			return "Unknown";	
		return dateFormater.get().format(date);
	}
	
	/** 
	* @Title: toShortDateString 
	* @Description: ������������ת��Ϊ�ַ���
	* @param @param date
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��9��16�� ����12:10:12
	*/
	public static String toShortDateString(Date date){
		if(date==null)
			return "Unknown";	
		return dateFormater2.get().format(date);
	}
	
    /** 
    * @Title: getSpecifiedDayBefore 
    * @Description: ���ָ�����ڵ�ǰһ��  
    * @param @param date
    * @param @return     
    * @return String    
    * @throws 
    * @author 21291
    * @date 2014��9��16�� ����1:01:29
    */
    public static String getSpecifiedDayBefore(Date date) {
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        int day = c.get(Calendar.DATE);   
        c.set(Calendar.DATE, day - 1);   
        return toShortDateString(c.getTime()); 
    }   
 
    /** 
    * @Title: getSpecifiedDayAfter 
    * @Description: ���ָ�����ڵĺ�һ��  
    * @param @param date
    * @param @return     
    * @return String    
    * @throws 
    * @author 21291
    * @date 2014��9��16�� ����1:01:02
    */
    public static String getSpecifiedDayAfter(Date date) {   
        Calendar c = Calendar.getInstance();   
        c.setTime(date);   
        int day = c.get(Calendar.DATE);   
        c.set(Calendar.DATE, day + 1);       
        return toShortDateString(c.getTime());   
    }   

	
	/** 
	* @Title: friendly_time 
	* @Description: ���Ѻõķ�ʽ��ʾʱ��
	* @param @param sdate
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:07:52
	*/
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// �ж��Ƿ���ͬһ��
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1)+ "����ǰ";
			else
				ftime = hour + "Сʱǰ";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1)+ "����ǰ";
			else
				ftime = hour + "Сʱǰ";
		} else if (days == 1) {
			ftime = "����";
		} else if (days == 2) {
			ftime = "ǰ��";
		} else if (days > 2 && days <= 10) {
			ftime = days + "��ǰ";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	/** 
	* @Title: isToday 
	* @Description: �жϸ����ַ���ʱ���Ƿ�Ϊ����
	* @param @param sdate
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:12:21
	*/
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}
	
	/** 
	* @Title: getToday 
	* @Description: ����long���͵Ľ��������
	* @param @return     
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:15:20
	*/
	public static long getToday() {
		try {
			Calendar cal = Calendar.getInstance();
			String curDate = dateFormater2.get().format(cal.getTime());
			curDate = curDate.replace("-", "");
			return Long.parseLong(curDate);
		} catch (NumberFormatException e) {}
		return 0;
	}
	
	/** 
	* @Title: isEmpty �жϸ����ַ����Ƿ�հ״��� 
	* @Description: �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ��� �������ַ���Ϊnull����ַ���������true
	* @param @param input
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:15:48
	*/
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input.trim()))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	
	/** 
	* @Title: isNotEmpty 
	* @Description: �Ƿ�Ϊ��
	* @param @param s
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����2:21:28
	*/
	public static boolean isNotEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

	/** 
	* @Title: format 
	* @Description: ͨ��{n},��ʽ��
	* @param @param src
	* @param @param objects
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��12��5�� ����2:21:39
	*/
	public static String format(String src, Object... objects) {
		int k = 0;
		for (Object obj : objects) {
			src = src.replace("{" + k + "}", obj.toString());
			k++;
		}
		return src;
	}
	
	/** 
	* @Title: isEmail 
	* @Description: �ж��ǲ���һ���Ϸ��ĵ����ʼ���ַ
	* @param @param email
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:16:29
	*/
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}
	
	/** 
	* @Title: toInt 
	* @Description: 
	* @param @param str ��ת���ַ���
	* @param @param defValue Ĭ��ֵ
	* @param @return ת���쳣���� 0     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:17:11
	*/
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {}
		return defValue;
	}
	
	/** 
	* @Title: toDouble 
	* @Description: �ַ�������ת��Ϊdouble����
	* @param @param str ��ת���ַ���
	* @param @param defValue Ĭ��ֵ
	* @param @return ת���쳣���� 0     
	* @return double    
	* @throws 
	* @author 21291
	* @date 2014��10��8�� ����9:11:16
	*/
	public static double toDouble(String str, int defValue) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {}
		return defValue;
	}
	
	/** 
	* @Title: toDoubleKeepTwo 
	* @Description: double����2λ
	* @param @param d
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��10��8�� ����9:26:09
	*/
	public static String toDoubleKeepTwo(double d) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(d);
	}
	
	/** 
	* @Title: toLong 
	* @Description: �ַ�������ת��Ϊlong����
	* @param @param str
	* @param @return     
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:18:16
	*/
	public static long toLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {}
		return 0;
	}
	
	/** 
	* @Title: toBool 
	* @Description: �ַ�������ת��Ϊ��������
	* @param @param str
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:21:17
	*/
	public static boolean toBool(String str) {
		try {
			return Boolean.parseBoolean(str);
		} catch (Exception e) {}
		return false;
	}
	
	/** 
	* @Title: toConvertString 
	* @Description: ��һ��InputStream��ת�����ַ���
	* @param @param is
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:22:29
	*/
	public static String toConvertString(InputStream is) {
		StringBuffer res = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader read = new BufferedReader(isr);
		try {
			String line;
			line = read.readLine();
			while (line != null) {
				res.append(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != isr) {
					isr.close();
					isr = null;
				}
				if (null != read) {
					read.close();
					read = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
			} catch (IOException e) {}
		}
		return res.toString();
	}
	
	/**
     * @Title: substringBefore
     * @Description: ��ȡ��������־λ֮ǰ���ַ���
     * @param @param str �ַ���
     * @param @param expr �ָ���
     * @param @return
     * @return String
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:46:00
     */
    public static String substringBefore(String str, String expr) {
        if (isEmpty(str) || expr == null) {
            return str;
        }
        if (expr.length() == 0) {
            return STRING_EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * @Title: substringAfter
     * @Description: ��ȡ��������־λ֮����ַ���
     * @param @param str �ַ���
     * @param @param expr �ָ���
     * @param @return
     * @return String
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:46:49
     */
    public static String substringAfter(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (expr == null) {
            return STRING_EMPTY;
        }
        int pos = str.indexOf(expr);
        if (pos == -1) {
            return STRING_EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * @Title: substringBeforeLast
     * @Description: ��ȡ���������һ����־λ֮ǰ���ַ���
     * @param @param str �ַ���
     * @param @param expr �ָ���
     * @param @return
     * @return String
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:47:47
     */
    public static String substringBeforeLast(String str, String expr) {
        if (isEmpty(str) || isEmpty(expr)) {
            return str;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * @Title: substringAfterLast
     * @Description: ��ȡ���������һ����־λ֮����ַ���
     * @param @param str
     * @param @param expr �ָ���
     * @param @return
     * @return String
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:48:36
     */
    public static String substringAfterLast(String str, String expr) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(expr)) {
            return STRING_EMPTY;
        }
        int pos = str.lastIndexOf(expr);
        if (pos == -1 || pos == (str.length() - expr.length())) {
            return STRING_EMPTY;
        }
        return str.substring(pos + expr.length());
    }

    /**
     * @Title: stringToArray
     * @Description: ���ַ������ָ���ת��Ϊ����
     * @param @param string �ַ���
     * @param @param expr �ָ���
     * @param @return
     * @return String[]
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:49:23
     */
    public static String[] stringToArray(String string, String expr) {
        return string.split(expr);
    }

    /**
     * @Title: noSpace
     * @Description: ȥ���ַ����еĿո�
     * @param @param str �ַ���
     * @param @return
     * @return String
     * @throws
     * @author 21291
     * @date 2014��3��12�� ����4:49:37
     */
    public static String noSpace(String str) {
        str = str.trim();
        str = str.replace(" ", "_");
        return str;
    }
    
	/** 
	* @Title: join 
	* @Description: ����ת��Ϊ�ַ��������÷��Ϸָ�
	* @param @param list
	* @param @param delim
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��5��19�� ����12:56:06
	*/
	public static String join(List<String> list, String delim) {
	    StringBuilder buf = new StringBuilder();
	    int num = list.size();
	    for (int i = 0; i < num; i++) {
	        if (i != 0)
	            buf.append(delim);
	        buf.append((String) list.get(i));
	    }
	    return buf.toString();
	}
	
	/** 
	* @Title: convertToList 
	* @Description: �ַ����÷ָ��ת��Ϊ����
	* @param @param source
	* @param @param delim
	* @param @return     
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014��11��13�� ����10:32:59
	*/
	public static List<String> convertToList(String source, String delim) {
		return Arrays.asList(source.split("\\s*"+delim+"\\s*"));
	}
	
    /** 
    * @Title: generateTime 
    * @Description: ����ʱ��
    * @param @param paramLong
    * @param @return     
    * @return String    
    * @throws 
    * @author 21291
    * @date 2014��8��12�� ����12:00:24
    */
    @SuppressLint("DefaultLocale")
	public static String generateTime(long paramLong)
	{
	    int i = (int)(paramLong / 1000L);
	    int j = i % 60;
	    int k = i / 60 % 60;
	    int m = i / 3600;
	    if (m > 0)
	    {
	      Object[] arrayOfObject2 = new Object[3];
	      arrayOfObject2[0] = Integer.valueOf(m);
	      arrayOfObject2[1] = Integer.valueOf(k);
	      arrayOfObject2[2] = Integer.valueOf(j);
	      return String.format("%02d:%02d:%02d", arrayOfObject2);
	    }
	    Object[] arrayOfObject1 = new Object[2];
	    arrayOfObject1[0] = Integer.valueOf(k);
	    arrayOfObject1[1] = Integer.valueOf(j);
	    return String.format("%02d:%02d", arrayOfObject1);
	}
}
