package com.tgi.safeher.utils;

import java.io.File;
import java.io.IOException;

public class rf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		      
	        File file = new File("http://192.168.0.141:8080/examples/demo.txt");
	        
	        if (file.createNewFile()){
	          System.out.println("File is created!");
	        }else{
	          System.out.println("File already exists.");
	        }
	        
	      } catch (IOException e) {
	        e.printStackTrace();
	  }

	}

}
