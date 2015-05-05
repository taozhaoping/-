package com.dahuatech.app.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @ClassName ParseXmlService
 * @Description XML������
 * @author 21291
 * @date 2014��3��14�� ����11:19:29
 */
public class ParseXmlService
{
	public static HashMap<String, String> parseXml(InputStream inStream) throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		// ʵ����һ���ĵ�����������
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// ͨ���ĵ�������������ȡһ���ĵ�������
		DocumentBuilder builder = factory.newDocumentBuilder();
		// ͨ���ĵ�ͨ���ĵ�����������һ���ĵ�ʵ��
		Document document = builder.parse(inStream);
		//��ȡXML�ļ����ڵ�
		Element root = document.getDocumentElement();
		//��������ӽڵ�
		NodeList childNodes = root.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++)
		{
			//�����ӽڵ�
			Node childNode = (Node) childNodes.item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) childNode;
				//�汾��
				if ("version".equals(childElement.getNodeName()))
				{
					hashMap.put("version",childElement.getFirstChild().getNodeValue());
				}
				//�������
				else if (("name".equals(childElement.getNodeName())))
				{
					hashMap.put("name",childElement.getFirstChild().getNodeValue());
				}
				//���ص�ַ
				else if (("url".equals(childElement.getNodeName())))
				{
					hashMap.put("url",childElement.getFirstChild().getNodeValue());
				}
			}
		}
		
		return hashMap;
	}

	/** 
	* @Title: xmlPullParserTest 
	* @Description: xmlPullParser�ļ�����
	* @param @param xpp
	* @param @param tagId
	* @param @return     
	* @return HashMap<String,String>    
	* @throws 
	* @author 21291
	* @date 2014��4��10�� ����3:12:35
	*/
	/* 1. XmlPullParserͨ��next()���������¼�� 
	 * 2. XmlPullParser���¼������������⵽ END_DOCUMENT��Ӧ��ֹͣ������� 
	 * 3. XmlPullParser���¼�����������⵽START_TAG�����Զ���<AAAA bbbb=xxxx c=yyyyy>�����ݣ�����AAAA��ͨ��getName()����ȡ�����Եĸ�������ͨ��getAttributeCount()��ȡ�����Ե����ʺ����ݿɷֱ�ͨ��getAttributeName(index)��getAttributeValue(index)����ȡ�� 
	 * 4. XmlPullParser ���¼������������END_TAG����Ϊ</AAAA>������ͨ��getName()�����AAAA������ 
	 * 5. ����<entry>Hello</entry>���ַ�ʽ�����Ҫ��ȡ�м����ֵ�����¼�ΪXmlPullParser.TEXT, ��ͨ��getText()����ȡ���ݡ� 
	 * 6. ����xml�ļ�������д����ȷ�����������ڽ��͵Ĺ����д�����ȷ��Ӧ��ʹ���쳣����ķ�ʽ������ 
	*/  
	public static HashMap<String, String> xmlPullParser(XmlPullParser xpp,String tagName) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		//����1�������ض�xml�ļ��Ľ�������Ӧ��6�㣬Ӧ�����쳣�����ֹ�������  
        try{  
        	//����2��ͨ��ѭ�����𲽽���XML��ֱ��xml�ļ���������Ӧ��1��͵�2�� 
            while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT){  
                //����3����ȡĿ��tagName�Ľ���  
                if(xpp.getEventType() == XmlPullParser.START_TAG){  
                    if(xpp.getName().equals(tagName)){  
                    	//����4����������
                        getItems(xpp,tagName,hashMap);  
                    }  
                }  
                xpp.next();  
            }  
        } catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
		
	}
	
    /** 
    * @Title: getItems 
    * @Description: ����tagName�����ݣ���ȡÿ��Item��name��ֵ��ע����쳣�Ĵ���
    * @param @param xpp
    * @param @param tagName
    * @param @param hashMap
    * @param @throws Exception     
    * @return void    
    * @throws IOException 
    * @throws XmlPullParserException 
    * @author 21291
    * @date 2014��4��10�� ����3:17:28
    */
    private static void getItems(XmlPullParser xpp,String tagName,HashMap<String, String> hashMap) throws XmlPullParserException, IOException{  
    	String strName="";
    	String strValue="";
        while(true){  
            xpp.next();  
            //<tagName> ...</tagName>�������Ѿ�������ϣ������ļ����������˳�����
            if((xpp.getEventType() == XmlPullParser.END_TAG && xpp.getName().equals(tagName))|| xpp.getEventType() == XmlPullParser.END_DOCUMENT)  
                break;  
  
            if(xpp.getEventType()==XmlPullParser.START_TAG) {  
            	//�������Ե����ֺ���ֵ  
                if(xpp.getName().equals("Item")){  
                    for(int i = 0; i < xpp.getAttributeCount() ; i ++){  
                        if(xpp.getAttributeName(i).equals("name")){  
                        	strName=xpp.getAttributeValue(i);
                        	//����<name>value</name>ֵ
                        	xpp.next();  
                        	if(xpp.getEventType()==XmlPullParser.TEXT)  
                        		strValue=xpp.getText();  
                        	hashMap.put(strName, strValue);  
                        }  
                    }  
                }   
            }  
        }  
    }  
}
