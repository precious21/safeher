package com.tgi.safeher.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

import com.tgi.safeher.beans.UserImageBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;

public class FileUtil {
	
	public static String getPathFromProperties(String key) {

		Properties prop = new Properties();
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		InputStream is = classloader
				.getResourceAsStream("/properties/electronicMedia.properties");
		try {
			prop.load(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return prop.getProperty(key);
	}

	public static boolean validateVideoExtension(String ext) {
		boolean result = false;
		if (ext.equalsIgnoreCase("flv") || ext.equalsIgnoreCase("ogg")
				|| ext.equalsIgnoreCase("mov") || ext.equalsIgnoreCase("mpeg")
				|| ext.equalsIgnoreCase("mpg") || ext.equalsIgnoreCase("mp4")
				|| ext.equalsIgnoreCase("wmv") || ext.equalsIgnoreCase("webm")
				|| ext.equalsIgnoreCase("3gpp") || ext.equalsIgnoreCase("3gp")) {
			result = true;
		}
		return result;
	}

	public static String copyFileForElectronicResource(
			SafeHerDecorator decorator) {
		String message = "success";
		try {
			UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
			MultipartFile[] images = imageBean.getFileArray();

			if (images != null && images.length > 0) {
				for (int i = 0; i < images.length; i++) {
					MultipartFile userImagae = images[i];
					saveImageElectronicResource(userImagae, decorator);
				}
				decorator.getResponseMap().put("userImageUrl", imageBean.getUrlList());
			}
		} catch (Exception e) {
			message = "Failed to upload image";
		}
		return message;
	}

	public static void saveImageElectronicResource(MultipartFile userImgae,
			SafeHerDecorator decorator) {

		try {
			String time = System.currentTimeMillis() + "";
			UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
			String imageName = time + "-" + userImgae.getOriginalFilename();
			if((imageName.contains(".") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("png") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("tif") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpg") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpeg")) || !imageName.contains(".")){
				if (userImgae.getContentType() != null) {
					imageName += imageName + "." + userImgae.getContentType();
				}
			}
//			String serverPath = getServerPath("ElectronicesResource");
			String serverPath = createDir(
					getPathFromProperties("ELECTRONICS_RESOURCE_SERVER"), "electronicResources");
			byte[] bytes = userImgae.getBytes();

			String imagePath = serverPath + System.getProperty("file.separator") + imageName;
			FileOutputStream fileOuputStream = new FileOutputStream(imagePath);
			fileOuputStream.write(bytes);
			fileOuputStream.close();
//			imageBean.getUrlList().add(
//					Paths.ELECTRONICS_RESOURCE + imageName);
//			imageBean.getUrlList().add(
//					imagePath);
			String result = Common.saveTempImg(imagePath, "ElectronicesResourceTemp");
			if(result.equalsIgnoreCase("Fail")){
//				message = "Failed to upload image";
			}else{
				imageBean.getUrlList().add(
						result);
			}

			System.out.println(imagePath);
			System.out.println("Done");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static String copyFileForDriver(SafeHerDecorator decorator) {
		String message = "success";
		try {
			String time = System.currentTimeMillis() + "";
			UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
			MultipartFile userImgae = imageBean.getFile();
			String imageName = time + "" + userImgae.getOriginalFilename();
			if((imageName.contains(".") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("png") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("tif") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpg") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpeg")) || !imageName.contains(".")){
				if (userImgae.getContentType() != null) {
					imageName += imageName + "." + userImgae.getContentType();
				}
			}
//			String serverPath = getServerPath("userImages");
			String serverPath = createDir(
					getPathFromProperties("USER_IMAGE_SERVER"), "userImages");
			byte[] bytes = userImgae.getBytes();
			String imagePath = serverPath + System.getProperty("file.separator") + imageName;
			FileOutputStream fileOuputStream = new FileOutputStream(imagePath);
			fileOuputStream.write(bytes);
			fileOuputStream.close();
//			decorator.getInfo().put("userImageUrl",
//					serverPath + imageName);
//			decorator.getInfo().put("userImageUrl",
//					imagePath);
			String result = Common.saveTempImg(imagePath, "userImagesTemp");
			if(result.equalsIgnoreCase("Fail")){
				message = "Failed to upload image";
			}else{
				decorator.getInfo().put("userImageUrl",
						result);
				imageBean.setMediaUrl(result);
			}

			System.out.println(imagePath);
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failed to upload image";
		}
		return message;
	}

	public static String copyFile(SafeHerDecorator decorator) {
		String message = "success";
		try {
			String time = System.currentTimeMillis() + "";
			UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
			MultipartFile userImgae = imageBean.getFile();
			String imageName = time + "" + userImgae.getOriginalFilename();
			if((imageName.contains(".") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("png") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("tif") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpg") && 
					!imageName.substring(imageName.lastIndexOf(".")+1).equalsIgnoreCase("jpeg")) || !imageName.contains(".")){
				if (userImgae.getContentType() != null) {
					imageName += imageName + "." + userImgae.getContentType();
				}
			}
//			String serverPath = getServerPath("userImages");
			String serverPath = createDir(
					getPathFromProperties("USER_IMAGE_SERVER"), "userImages");
			byte[] bytes = userImgae.getBytes();
			String imagePath = serverPath + System.getProperty("file.separator") + imageName;
			FileOutputStream fileOuputStream = new FileOutputStream(imagePath);
			fileOuputStream.write(bytes);
			fileOuputStream.close();
//			decorator.getInfo().put("userImageUrl",
//					serverPath + imageName);
//			decorator.getInfo().put("userImageUrl",
//					imagePath);
			String result = Common.saveTempImg(imagePath, "userImagesTemp");
			if(result.equalsIgnoreCase("Fail")){
				message = "Failed to upload image";
			}else{
				decorator.getInfo().put("userImageUrl",
						result);
			}

			System.out.println(imagePath);
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failed to upload image";
		}
		return message;
	}

	public static String copyRegistrationFile(SafeHerDecorator decorator) {
		String message = "success";
		try {
			String time = System.currentTimeMillis() + "";
			UserImageBean imageBean = (UserImageBean) decorator.getDataBean();
			MultipartFile userImgae = imageBean.getFile();
			String imageName = time + "" + userImgae.getOriginalFilename();
			imageName = imageName.replaceAll("_", "");
//			if (userImgae.getContentType() != null) {
//				imageName += imageName + "." + userImgae.getContentType();
//			}
			String dirc = "";
			if(imageBean.getImageTypeFlag().equals("IPRO")){
				dirc = "userImages";
			}else if(imageBean.getImageTypeFlag().equals("ILIC")){
				dirc = "licenseImages";
			}else if(imageBean.getImageTypeFlag().equals("IINS")){
				dirc = "InsuranseImages";
			}else if(imageBean.getImageTypeFlag().equals("IREG")){
				dirc = "registrationImages";
			}
			String serverPath = createDir(
					getPathFromProperties("USER_IMAGE_SERVER"), dirc);
			byte[] bytes = userImgae.getBytes();
			String imagePath = serverPath + System.getProperty("file.separator") + imageName;
			FileOutputStream fileOuputStream = new FileOutputStream(imagePath);
			fileOuputStream.write(bytes);
			fileOuputStream.close();
			String result = Common.saveTempImg(imagePath, dirc+"Temp");
			if(result.equalsIgnoreCase("Fail")){
				message = "Failed to upload image";
			}else{
				imageBean.setMediaUrl(result);
			}
			System.out.println(imagePath);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failed to upload image";
		}
		return message;
	}

	public static String getServerPath(String drctry) {
		String path = System.getProperty("catalina.base");

		/*File dir = new File(path + "/webapps/"+drctry);*/

		File dir = new File(path +System.getProperty("file.separator") +"webapps"+System.getProperty("file.separator")+drctry);
		System.out.println(dir);

		// if the directory does not exist, create it
		if (!dir.exists()) {
			dir.mkdir();
		}

		return dir.getPath();
	}

	public static String createDir(String path, String drctry) {

		File dir = new File(path +drctry);
		System.out.println(dir);

		// if the directory does not exist, create it
		if (!dir.exists()) {
			dir.mkdir();
		}

		return dir.getPath();
	}

	public static String removeElcRes(String path) {
		String message = "success";
		try {
			
			String serverPath = getServerPath("ElectronicesResourceTemp");
//			File file = new File(serverPath+"/"+path.substring(path.lastIndexOf("/")+1));
			
			File file = new File(serverPath+"/"+path.substring(path.lastIndexOf("/")+1));

			if (file.delete()) {
//				message = "File deleted successfull";
			} else {
				message = "Delete operation is failed";
			}

		} catch (Exception e) {
			message = "Delete operation is failed";
			e.printStackTrace();

		}
		
		return message;

	}
	
	public static String removeTempData(String path, String dirc) {
		String message = "success";
		try {
			
			String serverPath = getServerPath(dirc);
			File file = new File(serverPath+"/"+path.substring(path.lastIndexOf("/")+1));
			
			if (file.delete()) {
//				message = "File deleted successfull";
			} else {
				message = "Delete operation is failed";
			}

		} catch (Exception e) {
			message = "Delete operation is failed";
			e.printStackTrace();

		}
		
		return message;

	}
	
	public static String getBytesFromFilePath(String imagePath) {

		FileInputStream fileInputStream = null;
		
		String serverPath = getServerPath("ElectronicesResourceTemp");

		File file = new File(serverPath+"/"+imagePath);
//		File file = new File(imagePath);

		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

//			for (int i = 0; i < bFile.length; i++) {
//				System.out.print((char) bFile[i]);
//			}

			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile.toString();
	}

}
