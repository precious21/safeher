package com.tgi.safeher.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.coobird.thumbnailator.Thumbnails;

public class Common {

	public static InetAddress getServerIpAddress() {

		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static String getValueFromSpecificPropertieFile(String propertieFileName, String key) {

		Properties prop = new Properties();
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		// propertieFileName = "/properties/propertieFileNameHere"
		InputStream is = classloader
				.getResourceAsStream(propertieFileName);
		try {
			prop.load(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return prop.getProperty(key);
	}
	
	public static String saveTempImg(String path, String dirc) {

		String ipAddress = Common.getValueFromSpecificPropertieFile(
				"/properties/ipAddress.properties", "SERVER_IP_ADDRESS");
		String serverPath = FileUtil.getServerPath(dirc);
		String tempPath = "";
		File file = new File(path);
		if(file.exists()){
			byte[] bytes = convertFileIntoBytes(file);
			if(bytes != null){
				String imagePath = serverPath + System.getProperty("file.separator") + file.getName();
				String imagePathLinux =  serverPath + System.getProperty("file.separator")+ "thumb-" + file.getName();
				try {
					Thumbnails.of(file).size(200, 200).toFile(new File(imagePathLinux));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Tomcat Image Path is" + imagePath);
				
				if(!checkIfFileAlreadExists(imagePath)){
					FileOutputStream fileOuputStream = null;
					try {
						try{
							fileOuputStream = new FileOutputStream(imagePath);
						}catch (FileNotFoundException e) {
							tempPath = "Fail";
							e.printStackTrace();
						}
						fileOuputStream.write(bytes);
						fileOuputStream.close();
					} catch (IOException e) {
						tempPath = "Fail";
						e.printStackTrace();
					}
				}
				/*tempPath = "http://"+ipAddress+":8080/"+dirc+"/"+file.getName();*/
				tempPath = "http://"+ipAddress+":8080/"+dirc+"/"+"thumb-"+file.getName();
				System.out.println(tempPath);
			}else{
				tempPath = "Fail";
			}
		}
		
		return tempPath;
	}
	
	public static byte[] convertFileIntoBytes(File file) {
		byte[] bFile = new byte[(int) file.length()];
		FileInputStream fileInputStream=null;
		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			return bFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkIfFileAlreadExists(String path) {
		File f = new File(path);
		if (f.exists()) {
			return true;
		}
		return false;
	}
	
	public static String removeFileFromMainServer(String path, String propertieName, String dirc) {
		String message = "success";
		String serverPath = FileUtil.createDir(
				FileUtil.getPathFromProperties(propertieName),
				dirc);
		try {

			// File(serverPath+"/"+path.substring(path.lastIndexOf("/")+1));

			File file = new File(
					serverPath+"/"+path.substring(path.lastIndexOf("/")+1));

			if (file.delete()) {
				// message = "File deleted successfull";
			} else {
				message = "Delete operation is failed";
			}

		} catch (Exception e) {
			message = "Delete operation is failed";
			e.printStackTrace();

		}

		return message;
	}

	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (float) (earthRadius * c);

		return dist;
	}

	public static Double getSecFromTwoDates(Date startDate, Date endDate) {
		if(startDate == null || endDate == null){
			return 1.0;
		}
		long duration = endDate.getTime() - startDate.getTime();
		System.out.println(duration);
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
		
		return new Double(diffInSeconds);

	}
}
