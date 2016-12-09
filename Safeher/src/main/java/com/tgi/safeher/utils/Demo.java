package com.tgi.safeher.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Demo {

	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (float) (earthRadius * c);

		return new Double(new DecimalFormat(
				"##.##").format(dist/1609.34));
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		String abc = "cM-FMjVyKsI:APA91bFEniqRSCCcKucQjfW0w8ZQGXYTQpW_pLE7LJFF3YWY7z-tv21uThZVtpBm_xD9KrKM8AkP8PHz9Z8jRtWrT-mcRPxHvo5I7znmEjMPKRQ3-VyRIzUclFcLcwX3itjvSvd874O0";
		System.out.println(abc.length());
//		System.out.println(new Timestamp(System.currentTimeMillis())+"");
//		String time = new Timestamp(System.currentTimeMillis())+"";
//		String startTime = time.split("\\s")[1].split("\\.")[0];
//		System.out.println(startTime);
		
//		String date = "2016-12-07 12:42:58.4";
//		String[] datearr = date.split(" ");
//		String[] ab = datearr[1].split(":");
//		System.out.println(ab[0]);
//		
//		Timestamp timestamp = new Timestamp(
//				DateUtil.getDate(date, "yyyy-MM-dd hh:mm:ss.SSS").getTime());
//		System.out.println(timestamp.getTime());
//		System.out.println(Common.getSecFromTwoTimeStampMiliSec(timestamp.getTime()));
		
//		System.out.println(DateUtil.getInvoiceDate(stamp));
//		String ipAddress = "192.123.213.23";
//		String abc = "@youRateDriver";
//		
//		abc = abc.replaceAll("@youRateDriver", "<a href='http://"+ipAddress+":8080/Safeher/ratingComingSoon.jsp' traget='_blank' "+
//				"Style='color: #ffffff;'>Rate Driver</a>");
//		
//		System.out.println(abc);
		
//		System.out.println(new Integer("01"));
//		System.out.println(DateUtil.getMinutesAndSecs(0.0));
//		Timestamp stamp = new Timestamp(System.currentTimeMillis());
//		System.out.println(DateUtil.getDateFromTimeStamp(stamp));
		
//		  Date date = new Date(stamp.getTime());
//		  System.out.println(DateUtil.getTimeFromTimeStamp(stamp)
//				  );
//		  System.out.println(date);
//		  Calendar mCalendar = Calendar.getInstance();    
//		  String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
//		  System.out.println(month);
//		Email e = new Email();
//		e.sendEmail("fawadaziz666@gmail.com");
//		System.out.println(e);
//		System.out.println(DateUtil.getMinutesAndSecs(120.0));
//		int count = 1;
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("abc: "+count);
//                if(count == 0){
//                	timer.cancel();
//                }
//            }
//        }, 0, 1000);
		
		
//		Demo d = new Demo();
//		System.out.println(d.distFrom(31.447731, 74.321376, 31.449256, 74.307875));
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//
//		int month = cal.get(Calendar.MONTH) + 1;
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		int year = cal.get(Calendar.YEAR);
//
//		System.out.println(month + "-" + day + "-" + year);
//
//		System.out.println(DateUtil.getMongoDbDate(new Date()));
//		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//		String dateInString = "11-24-2013";
//
//		try {
//
//			Date date = formatter.parse(dateInString);
//			System.out.println(date);
//			System.out.println(formatter.format(date));
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(DateUtil.getCurrentTimestamp());
//		System.out.println(DateUtil.getCurrentDateWithoutTime());
//		Double sec = 34.10;
//		System.out.println(DateUtil.getMinutesAndSecs(sec) + " min");
//
//		String abc = "DFD.jsdf";
//		System.out.println(abc.substring(abc.lastIndexOf(".") + 1));
//
//		Date startDate = new Date("November 22, 2016 10:29 AM");
//		Date endDate = new Date();
//
//		long duration = endDate.getTime() - startDate.getTime();
//		System.out.println(duration);
//		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
//		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
//		long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
//		System.out.println(diffInHours + ":" + diffInMinutes + ":" + diffInSeconds);

		// int totalSecs = 123;
		// int minutes = totalSecs / 60;
		// int seconds = totalSecs % 60;
		//
		// String timeString = String.format("%02d:%02d", minutes, seconds);
		// System.out.println(timeString);
		// String[] splitTime = timeString.split(":");
		// String mainTime = "";
		// for(String time : splitTime){
		// if(!time.equals("00")){
		// mainTime += time+":";
		// }
		// }
		// mainTime = mainTime.substring(0, mainTime.length()-1);
		//
		// System.out.println(mainTime);
	}

}
