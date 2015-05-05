package com.dahuatech.app.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * @ClassName FileUtils
 * @Description �ļ��������߰�
 * @author 21291
 * @date 2014��4��16�� ����2:42:27
 */
public class FileUtils {
	
	private static final String TAG="FileUtils";
	
	/** 
	* @Title: write д�ı��ļ�
	* @Description: ��Androidϵͳ�У��ļ������� /data/data/PACKAGE_NAME/files Ŀ¼��
	* @param @param context
	* @param @param fileName
	* @param @param content     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:43:20
	*/
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";
		try {
			FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Title: read 
	* @Description: ��ȡ�ı��ļ�
	* @param @param context
	* @param @param fileName
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:44:10
	*/
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/** 
	* @Title: readInStream 
	* @Description: ��ȡ�ļ�����
	* @param @param inStream ��������������
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:47:04
	*/
	public static String readInStream(InputStream inStream) {
		try {
			//�ֽ����������
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			//˵����������
			while ((length = inStream.read(buffer)) != -1) {
				//�Ѵ�����������д�������
				outStream.write(buffer, 0, length);
			}
			//�ر������
			outStream.close();
			//�ر�������
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			LogUtil.i(TAG, e.getMessage());
		}
		return null;
	}

	/** 
	* @Title: createFile 
	* @Description: �����ļ�
	* @param @param folderPath �ļ�Ŀ¼
	* @param @param fileName �ļ���
	* @param @return     
	* @return File    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:51:19
	*/
	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}
	
	/** 
	* @Title: writeFile 
	* @Description: ���ֻ�д�ļ�
	* @param @param buffer �ֽ�����
	* @param @param folder �ļ�Ŀ¼
	* @param @param fileName �ļ�����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:52:16
	*/
	public static boolean writeFile(byte[] buffer, String folder,String fileName) {
		boolean writeSucc = false;
		String folderPath = "";
		//�жϵ�ǰ��Ҵ洢���Ƿ�ɶ�д
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory()+ File.separator + folder + File.separator;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}
	
	/** 
	* @Title: getFileName 
	* @Description: �����ļ�����·����ȡ�ļ���
	* @param @param filePath
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����2:57:44
	*/
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}
	
	/** 
	* @Title: getFileNameNoFormat 
	* @Description: �����ļ��ľ���·����ȡ�ļ�������������չ��
	* @param @param filePath
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:28:41
	*/
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,point);
	}

	/** 
	* @Title: getFileFormat 
	* @Description: ��ȡ�ļ���չ��
	* @param @param fileName
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:29:49
	*/
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}
	
	/** 
	* @Title: getFileSize 
	* @Description: ��ȡ�ļ���С
	* @param @param filePath
	* @param @return     
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:30:13
	*/
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}
	
	/** 
	* @Title: getFileSize 
	* @Description: ��ȡ�ļ���С
	* @param @param size �ֽ���
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:30:55
	*/
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		DecimalFormat df = new DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}
	
	/** 
	* @Title: formatFileSize 
	* @Description: ת���ļ���С
	* @param @param fileS �ļ�
	* @param @return B/KB/MB/GB    
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:32:20
	*/
	public static String formatFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	/** 
	* @Title: getDirSize 
	* @Description: ��ȡĿ¼�ļ���С
	* @param @param dir Ŀ¼��
	* @param @return     
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:33:44
	*/
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // �ݹ���ü���ͳ��
			}
		}
		return dirSize;
	}
	
	/** 
	* @Title: getFileList 
	* @Description: ��ȡĿ¼�ļ�����
	* @param @param dir Ŀ¼��
	* @param @return     
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:34:37
	*/
	public long getFileList(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				count = count + getFileList(file);// �ݹ�
				count--;
			}
		}
		return count;
	}
	
	/** 
	* @Title: toBytes 
	* @Description: InputStreamת��Ϊ�ֽ�����
	* @param @param in
	* @param @return
	* @param @throws IOException     
	* @return byte[]    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:35:18
	*/
	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}
	
	/** 
	* @Title: checkFileExists 
	* @Description: ����ļ��Ƿ����
	* @param @param name �ļ���
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:35:58
	*/
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;
	}
	
	/** 
	* @Title: checkFilePathExists 
	* @Description: ���·���Ƿ����
	* @param @param path ·������
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:37:29
	*/
	public static boolean checkFilePathExists(String path) {
		return new File(path).exists();
	}
	
	/** 
	* @Title: getFreeDiskSpace 
	* @Description: ����SD����ʣ��ռ�
	* @param @return ����-1��˵��û�а�װsd��    
	* @return long    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:37:59
	*/
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}
	
	/** 
	* @Title: checkSaveLocationExists 
	* @Description: ����Ƿ�װSD��
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:40:48
	*/
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}
	
	/** 
	* @Title: checkExternalSDExists 
	* @Description: ����Ƿ�װ���õ�SD��
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:42:57
	*/
	public static boolean checkExternalSDExists() {
		
		Map<String, String> evn = System.getenv();
		return evn.containsKey("SECONDARY_STORAGE");
	}
	
	/** 
	* @Title: createDirectory 
	* @Description: �½�Ŀ¼
	* @param @param directoryName Ŀ¼����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:41:54
	*/
	public static boolean createDirectory(String directoryName) {
		boolean status;
		if (!directoryName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}
	
	/** 
	* @Title: deleteDirectory 
	* @Description: ɾ��Ŀ¼(������Ŀ¼��������ļ�)
	* @param @param fileName Ŀ¼����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:43:21
	*/
	public static boolean deleteDirectory(String fileName) {
		boolean status = false;
		SecurityManager checker = new SecurityManager();		
		if (!fileName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"+ listfile[i].toString());
						deletedFile.delete();
					}
					LogUtil.i(TAG, "deleteDirectory:"+fileName);
					newPath.delete();		
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
		return status;
	}
	
	/** 
	* @Title: deleteFile 
	* @Description: ɾ��ĳ���ļ�
	* @param @param fileName �ļ���
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:46:51
	*/
	public static boolean deleteFile(String fileName) {
		boolean status = false;
		SecurityManager checker = new SecurityManager();
		if (!fileName.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					LogUtil.i(TAG,"deleteFile:"+fileName);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
				}
			}
		} 
		return status;
	}
	
	/** 
	* @Title: deleteBlankPath 
	* @Description: ɾ����Ŀ¼
	* @param @param path ��Ŀ¼��
	* @param @return ���� 0����ɹ� ,1 ����û��ɾ��Ȩ��, 2�����ǿ�Ŀ¼,3 ����δ֪����    
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:48:50
	*/
	public static int deleteBlankPath(String path) {
		File f = new File(path);
		if (!f.canWrite()) {
			return 1;
		}
		if (f.list() != null && f.list().length > 0) {
			return 2;
		}
		if (f.delete()) {
			return 0;
		}
		return 3;
	}
	
	/** 
	* @Title: reNamePath 
	* @Description: �ļ�������
	* @param @param oldName �ϵ�����
	* @param @param newName �µ�����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:49:45
	*/
	public static boolean reNamePath(String oldName, String newName) {
		File f = new File(oldName);
		return f.renameTo(new File(newName));
	}
	
	/** 
	* @Title: clearFileWithPath 
	* @Description: ���һ���ļ���
	* @param @param filePath �ļ�������     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:51:19
	*/
	public static void clearFileWithPath(String filePath) {
		List<File> files = FileUtils.listPathFiles(filePath);
		if (files.isEmpty()) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				clearFileWithPath(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
	}
	
	/** 
	* @Title: getSDRoot 
	* @Description: ��ȡSD���ĸ�Ŀ¼
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:53:02
	*/
	public static String getSDRoot() {	
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	/** 
	* @Title: getExternalSDRoot 
	* @Description: ��ȡ�ֻ�����SD���ĸ�Ŀ¼
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:53:51
	*/
	public static String getExternalSDRoot() {	
		Map<String, String> evn = System.getenv();		
		return evn.get("SECONDARY_STORAGE");
	}

	
	/** 
	* @Title: listPath 
	* @Description: �г�rootĿ¼��������Ŀ¼
	* @param @param root ��·��
	* @param @return ����·��    
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:52:14
	*/
	public static List<String> listPath(String root) {
		List<String> allDir = new ArrayList<String>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		// ���˵���.��ʼ���ļ���
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				if (f.isDirectory() && !f.getName().startsWith(".")) {
					allDir.add(f.getAbsolutePath());
				}
			}
		}
		return allDir;
	}
	
	/** 
	* @Title: listPathFiles 
	* @Description: ��ȡһ���ļ����µ������ļ�
	* @param @param root
	* @param @return     
	* @return List<File>    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:54:43
	*/
	public static List<File> listPathFiles(String root) {
		List<File> allDir = new ArrayList<File>();
		SecurityManager checker = new SecurityManager();
		File path = new File(root);
		checker.checkRead(root);
		File[] files = path.listFiles();
		for (File f : files) {
			if (f.isFile())
				allDir.add(f);
			else 
				listPath(f.getAbsolutePath());
		}
		return allDir;
	}
	
	/**
	 * @ClassName PathStatus
	 * @Description ·��״̬ö��
	 * @author 21291
	 * @date 2014��4��16�� ����3:55:24
	 */
	public enum PathStatus {
		SUCCESS, EXITS, ERROR
	}
	
	/** 
	* @Title: createPath 
	* @Description: ����Ŀ¼
	* @param @param newPath
	* @param @return     
	* @return PathStatus    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:56:25
	*/
	public static PathStatus createPath(String newPath) {
		File path = new File(newPath);
		if (path.exists()) {
			return PathStatus.EXITS;
		}
		if (path.mkdir()) {
			return PathStatus.SUCCESS;
		} else {
			return PathStatus.ERROR;
		}
	}

	/** 
	* @Title: getPathName 
	* @Description: ��ȡ·����
	* @param @param absolutePath
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:56:58
	*/
	public static String getPathName(String absolutePath) {
		int start = absolutePath.lastIndexOf(File.separator) + 1;
		int end = absolutePath.length();
		return absolutePath.substring(start, end);
	}
	
	/** 
	* @Title: getAppCache 
	* @Description: ��ȡӦ�ó��򻺴��ļ����µ�ָ��Ŀ¼
	* @param @param context
	* @param @param dir
	* @param @return  �����ļ�����ָ��Ŀ¼������   
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014��4��16�� ����3:57:07
	*/
	public static String getAppCache(Context context, String dir) {
		String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
		File savedir = new File(savePath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		savedir = null;
		return savePath;
	}
	
	/** 
	* @Title: packupDatabaseFile 
	* @Description: �����ݿ��ļ����ݵ�sd����
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014��5��19�� ����2:26:16
	*/
	@SuppressWarnings("resource")
	public boolean packupDatabaseFile(){
	    try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	            String currentDBPath = "//data//com.dahuatech.app//databases//"+DBUtils.DATABASE_NAME;
	            String backupDBPath = sd.getAbsolutePath() + "/dahuaMobile/database/"+DBUtils.DATABASE_NAME+"_bak";
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(sd, backupDBPath);

	            if (currentDB.exists()) {
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	            }
	        }
	     return true;//Success
	    } 
	    catch (Exception e) {
	    	 return false;//Failed to backup
	    }
	}
}
