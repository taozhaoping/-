package com.dahuatech.app.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexpUtils
 * @Description HTML��ص�������ʽ������,��������HTML��ǣ�ת��HTML��ǣ��滻�ض�HTML��� 
 * @author 21291
 * @date 2014��4��17�� ����2:57:38
 */
public class RegexpUtils {
	
	private final static String regxpForHtml = "<([^>]*)>"; // ����������<��ͷ��>��β�ı�ǩ
	public RegexpUtils() {}
	
	/**
	 * �������ܣ��滻�����������ʾ
	 * @param input
	 * @return String
	 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}

	/**
	 * �������ܣ��жϱ���Ƿ����
	 * @param input
	 * @return boolean
	 */
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * �������ܣ�����������"<"��ͷ��">"��β�ı�ǩ
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * �������ܣ�����ָ����ǩ
	 * @param str
	 * @param tag ָ����ǩ
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * �������ܣ��滻ָ���ı�ǩ
	 * @param str
	 * @param beforeTag Ҫ�滻�ı�ǩ
	 * @param tagAttrib Ҫ�滻�ı�ǩ����ֵ
	 * @param startTag �±�ǩ��ʼ���
	 * @param endTag �±�ǩ�������
	 * @return String
	 * @�磺�滻img��ǩ��src����ֵΪ[img]����ֵ[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}
	
	
	/** 
	* @Title: isMobileNo 
	* @Description: �Ƿ�Ϸ����ֻ�����
	* @param @param mobileTel
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��10��16�� ����9:14:07
	*/
	public static boolean isMobileNo(String mobileTel){
		String regxp = "(((13[0-9])|(15[^4,\\D])|(18[0,5-9]|(17[0-9])))\\d{8})|(6\\d{5})";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(mobileTel);
		return matcher.matches();
	}

}
