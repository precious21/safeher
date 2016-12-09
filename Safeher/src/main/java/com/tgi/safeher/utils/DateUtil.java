/*
 * @Project : 
 * 
 * @Package : com.
 * 
 * @FileName : DateUtil.java
 * 
 * Copyright ©  Trans-Atlantic Business Solutions, All rights reserved.
 */

package com.tgi.safeher.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;

/**
 * @author : 
 * @Date : 
 * @version : Ver. 1.0.0
 * 
 *          <center><b>DateUtil.java</b></center> <center><b>Modification
 *          History</b></center>
 * 
 *          <pre>
 * 
 * ________________________________________________________________________________________________
 * 
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 * 
 * 
 * ________________________________________________________________________________________________
 * </pre>
 * 
 */
/**
 * @author : 
 * @Date : Sep 26, 2013
 * @version : Ver. 1.0.0
 * 
 *          <center><b>DateUtil.java</b></center> <center><b>Modification
 *          History</b></center>
 * 
 *          <pre>
 * 
 * ________________________________________________________________________________________________
 * 
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 * 
 * 
 * ________________________________________________________________________________________________
 * </pre>
 * 
 */
public final class DateUtil implements Serializable{

	public static final long				millisInDay					= 86400000;

	// some static date formats
	private static SimpleDateFormat [ ]		mDateFormats				= loadDateFormats( );

	private static final SimpleDateFormat	mFormat8chars				= new SimpleDateFormat(
																				"yyyyMMdd" );

	private static final SimpleDateFormat	mFormatIso8601Day			= new SimpleDateFormat(
																				"yyyy-MM-dd" );

	private static final SimpleDateFormat	mFormatIso8601				= new SimpleDateFormat(
																				"yyyy-MM-dd'T'HH:mm:ssZ" );

	// http://www.w3.org/Protocols/rfc822/Overview.html#z28
	// Using Locale.US to fix ROL-725 and ROL-628
	private static final SimpleDateFormat	mFormatRfc822				= new SimpleDateFormat(
																				"EEE, d MMM yyyy HH:mm:ss Z", Locale.US );
	// TODO temporarily done for application, later will be done through enum
	private static final String				applicationLevelFormat		= "MM/DD/YYYY LL:NN A";

	public static final String				TOKEN_DATE_FORMAT			= "MMM dd yyyy HH:mma";
	public static final String				TOKEN_DATE_FORMAT_HOUR		= "MMM dd yyyy HH:mm";
	public static final String				ACTION_DATE_FORMAT			= "EEE MMM d HH:mm:ss z yyyy";
	public static final String				ACTION_DATE_INPUT_FORMAT	= "yyyy-MM-dd HH:mm:ss,SSS";
	public static final String				TASK_CREATE_DATE_FORMAT		= "MM/dd/yyyy HH:mm:ss a";
	public static final String				BPM_TOKEN_DATE_FORMAT		= "yyyy-MM-dd";
	public static final String				BPM_TOKEN_DATE_FORMAT_WO_TIME		= "yyyy-MM-dd HH:mm:ss";
	public static final String				CARD_PRODUCTION_DATE_FORMAT	= "yyyy-MM-dd HH:mm:ss";
	public static final String				IMAGE_FILE_DATE_FORMAT		= "__dd-MM-yy_(HH-mm)";
	// Added by bilal Iftikhar
	public static final String				NOTE_FILE_DATE_FORMAT		= "__dd-MM-yy_(HH-mm-ss)";

	// added by Turab
	public static final String				MM_DD_YYYY					= "MM/dd/yyyy";
	// Added By Bilal
	public static final String				MM_DD_YYYY_HH					= "MM/dd/yyyy HH:mm";
	
	private static SimpleDateFormat [ ] loadDateFormats( ) {

		SimpleDateFormat [ ] temp = {
				// new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a"),
				new SimpleDateFormat( "EEE MMM d HH:mm:ss z yyyy" ), // standard
																		// Date.toString()
				new SimpleDateFormat( "MM-dd-yyyy" ),
				new SimpleDateFormat( "MM-dd-yyyy hh:mm:ss" ),// results														// results
				new SimpleDateFormat( "M/d/yy hh:mm:ss" ),
				new SimpleDateFormat( "M/d/yyyy hh:mm:ss" ),
				new SimpleDateFormat( "M/d/yy hh:mm a" ),
				new SimpleDateFormat( "M/d/yyyy hh:mm a" ),
				new SimpleDateFormat( "M/d/yy HH:mm" ),
				new SimpleDateFormat( "M/d/yyyy HH:mm" ),
				new SimpleDateFormat( "dd.MM.yyyy HH:mm:ss" ),
				new SimpleDateFormat( "yy-MM-dd HH:mm:ss.SSS" ),
				new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" ), // standard
																	// Timestamp.toString()
				new SimpleDateFormat( "yyyy-MM-dd" ),
				new SimpleDateFormat( "HH:mm:ss" ), 	// results
				new SimpleDateFormat( "M-d-yy HH:mm" ),
				new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" ),
				new SimpleDateFormat( "M-d-yyyy HH:mm" ),
				new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss.SSS" ),
				new SimpleDateFormat( "M/d/yy" ),
				new SimpleDateFormat( "M/d/yyyy" ),
				new SimpleDateFormat( "M-d-yy" ),
				new SimpleDateFormat( "M-d-yyyy" ),
				new SimpleDateFormat( "MMMM d, yyyyy" ),
				new SimpleDateFormat( "MMM d, yyyyy" ) };

		return temp;
	}

	// -----------------------------------------------------------------------
	/**
	 * Gets the array of SimpleDateFormats that DateUtil knows about.
	 **/
	private static SimpleDateFormat [ ] getFormats( ) {

		return mDateFormats;
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date set to the last possible millisecond of the day, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfDay( Date day ) {

		return getEndOfDay( day, Calendar.getInstance( ) );
	}

	public static Date getEndOfDay( Date day, Calendar cal ) {

		if ( day == null )
			day = new Date( );
		cal.setTime( day );
		cal.set( Calendar.HOUR_OF_DAY, cal.getMaximum( Calendar.HOUR_OF_DAY ) );
		cal.set( Calendar.MINUTE, cal.getMaximum( Calendar.MINUTE ) );
		cal.set( Calendar.SECOND, cal.getMaximum( Calendar.SECOND ) );
		cal.set( Calendar.MILLISECOND, cal.getMaximum( Calendar.MILLISECOND ) );
		return cal.getTime( );
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay( Date day ) {

		return getStartOfDay( day, Calendar.getInstance( ) );
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay( Date day, Calendar cal ) {

		if ( day == null )
			day = new Date( );
		cal.setTime( day );
		cal.set( Calendar.HOUR_OF_DAY, cal.getMinimum( Calendar.HOUR_OF_DAY ) );
		cal.set( Calendar.MINUTE, cal.getMinimum( Calendar.MINUTE ) );
		cal.set( Calendar.SECOND, cal.getMinimum( Calendar.SECOND ) );
		cal.set( Calendar.MILLISECOND, cal.getMinimum( Calendar.MILLISECOND ) );
		return cal.getTime( );
	}

	/**
	 * Returns a Date set just to Noon, to the closest possible millisecond of
	 * the day. If a null day is passed in, a new Date is created. nnoon (00m
	 * 12h 00s)
	 */
	public static Date getNoonOfDay( Date day, Calendar cal ) {

		if ( day == null )
			day = new Date( );
		cal.setTime( day );
		cal.set( Calendar.HOUR_OF_DAY, 12 );
		cal.set( Calendar.MINUTE, cal.getMinimum( Calendar.MINUTE ) );
		cal.set( Calendar.SECOND, cal.getMinimum( Calendar.SECOND ) );
		cal.set( Calendar.MILLISECOND, cal.getMinimum( Calendar.MILLISECOND ) );
		return cal.getTime( );
	}

	// -----------------------------------------------------------------------
	public static Date parseFromFormats( String aValue ) {

		if ( StringUtil.isEmpty( aValue ) )
			return null;

		// get DateUtil's formats
		SimpleDateFormat formats[] = DateUtil.getFormats( );
		if ( formats == null )
			return null;

		// iterate over the array and parse
		Date myDate = null;
		for ( int i = 0 ; i < formats.length ; i++ ) {
			try {
				myDate = DateUtil.parse( aValue, formats[ i ] );
				// if (myDate instanceof Date)
				return myDate;
			} catch ( Exception e ) {
				// do nothing because we want to try the next
				// format if current one fails
			}
		}
		// haven't returned so couldn't parse
		return null;
	}

	// -----------------------------------------------------------------------
	public static java.sql.Timestamp parseTimestampFromFormats( String aValue ) {

		if ( StringUtil.isEmpty( aValue ) )
			return null;

		// call the regular Date formatter
		Date myDate = DateUtil.parseFromFormats( aValue );
		if ( myDate != null )
			return new java.sql.Timestamp( myDate.getTime( ) );
		return null;
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a java.sql.Timestamp equal to the current time
	 **/
	public static java.sql.Timestamp now( ) {

		return new java.sql.Timestamp( new java.util.Date( ).getTime( ) );
	}

	/**
	 * @author : Rafia Taqdees
	 * @Date : Jan 12, 2012
	 * 
	 * @Description : return timestamp of given date
	 * 
	 * @param date
	 * @return Timestamp
	 */
	public static Timestamp getTimestampFromDate( Date date ) {

		if ( date != null ) {
			return new Timestamp( date.getTime( ) );
		}
		return null;
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a string the represents the passed-in date parsed according to
	 * the passed-in format. Returns an empty string if the date or the format
	 * is null.
	 **/
	public static String format( Date aDate, SimpleDateFormat aFormat ) {

		if ( aDate == null || aFormat == null ) {
			return "";
		}
		synchronized ( aFormat ) {
			return aFormat.format( aDate );
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Tries to take the passed-in String and format it as a date string in the
	 * the passed-in format.
	 **/
	public static String formatDateString( String aString,
			SimpleDateFormat aFormat ) {

		if ( StringUtil.isEmpty( aString ) || aFormat == null )
			return "";
		try {
			java.sql.Timestamp aDate = parseTimestampFromFormats( aString );
			if ( aDate != null ) {
				return DateUtil.format( aDate, aFormat );
			}
		} catch ( Exception e ) {
			// Could not parse aString.
		}
		return "";
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date using the passed-in string and format. Returns null if the
	 * string is null or empty or if the format is null. The string must match
	 * the format.
	 **/
	public static Date parse( String aValue, SimpleDateFormat aFormat )
			throws ParseException {

		if ( StringUtil.isEmpty( aValue ) || aFormat == null ) {
			return null;
		}

		return aFormat.parse( aValue );
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns true if endDate is after startDate or if startDate equals endDate
	 * or if they are the same date. Returns false if either value is null.
	 **/
	public static boolean isValidDateRange( Date startDate, Date endDate ) {

		return isValidDateRange( startDate, endDate, true );
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns true if endDate is after startDate or if startDate equals
	 * endDate. Returns false if either value is null. If equalOK, returns true
	 * if the dates are equal.
	 **/
	public static boolean isValidDateRange( Date startDate, Date endDate,
			boolean equalOK ) {

		// false if either value is null
		if ( startDate == null || endDate == null ) {
			return false;
		}

		if ( equalOK ) {
			// true if they are equal
			if ( startDate.equals( endDate ) ) {
				return true;
			}
		}

		// true if endDate after startDate
		if ( endDate.after( startDate ) ) {
			return true;
		}

		return false;
	}
	// -----------------------------------------------------------------------
	/**
	 * Returns true if date1 and date2 are equals or date1 is before date2
	 * False otherwise.
	 **/
	public static boolean isValidDate( Date date1, Date date2 ) {

		
		// false if either value is null
		if ( date1 == null || date2 == null ) {
			return false;
		}

		// true if they are equal
		if ( date1.equals( date2 ) ) {
			return true;
		}

		// true if endDate after startDate
		if ( date1.before( date2 ) ) {
			return true;
		}

		return false;
	}

	// -----------------------------------------------------------------------
	// returns full timestamp format
	public static java.text.SimpleDateFormat defaultTimestampFormat( ) {

		return new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static java.text.SimpleDateFormat get8charDateFormat( ) {

		return DateUtil.mFormat8chars;
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static java.text.SimpleDateFormat defaultDateFormat( ) {

		return DateUtil.friendlyDateFormat( true );
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String defaultTimestamp( Date date ) {

		return DateUtil.format( date, DateUtil.defaultTimestampFormat( ) );
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String defaultDate( Date date ) {

		return DateUtil.format( date, DateUtil.defaultDateFormat( ) );
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly timestamp format
	public static java.text.SimpleDateFormat friendlyTimestampFormat( ) {

		return new java.text.SimpleDateFormat( "dd.MM.yyyy HH:mm:ss" );
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String friendlyTimestamp( Date date ) {

		return DateUtil.format( date, DateUtil.friendlyTimestampFormat( ) );
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String format8chars( Date date ) {

		return DateUtil.format( date, mFormat8chars );
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String formatIso8601Day( Date date ) {

		return DateUtil.format( date, mFormatIso8601Day );
	}

	// -----------------------------------------------------------------------
	public static String formatRfc822( Date date ) {

		return DateUtil.format( date, mFormatRfc822 );
	}

	// -----------------------------------------------------------------------
	// This is a hack, but it seems to work
	public static String formatIso8601( Date date ) {

		if ( date == null )
			return "";

		// Add a colon 2 chars before the end of the string
		// to make it a valid ISO-8601 date.

		String str = DateUtil.format( date, mFormatIso8601 );
		StringBuffer sb = new StringBuffer( );
		sb.append( str.substring( 0, str.length( ) - 2 ) );
		sb.append( ":" );
		sb.append( str.substring( str.length( ) - 2 ) );
		return sb.toString( );
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static java.text.SimpleDateFormat minimalDateFormat( ) {

		return DateUtil.friendlyDateFormat( true );
	}

	// -----------------------------------------------------------------------
	// convenience method using minimal date format
	public static String minimalDate( Date date ) {

		return DateUtil.format( date, DateUtil.minimalDateFormat( ) );
	}

	// -----------------------------------------------------------------------
	// convenience method that returns friendly data format
	// using full month, day, year digits.
	public static java.text.SimpleDateFormat fullDateFormat( ) {

		return DateUtil.friendlyDateFormat( false );
	}

	// -----------------------------------------------------------------------
	public static String fullDate( Date date ) {

		return DateUtil.format( date, DateUtil.fullDateFormat( ) );
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a "friendly" date format.
	 * 
	 * @param mimimalFormat
	 *            Should the date format allow single digits.
	 **/
	public static java.text.SimpleDateFormat friendlyDateFormat(
			boolean minimalFormat ) {

		if ( minimalFormat ) {
			return new java.text.SimpleDateFormat( "d.M.yy" );
		}

		return new java.text.SimpleDateFormat( "dd.MM.yyyy" );
	}

	// -----------------------------------------------------------------------
	/**
	 * Format the date using the "friendly" date format.
	 */
	public static String friendlyDate( Date date, boolean minimalFormat ) {

		return DateUtil
				.format( date, DateUtil.friendlyDateFormat( minimalFormat ) );
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String friendlyDate( Date date ) {

		return DateUtil.format( date, DateUtil.friendlyDateFormat( true ) );
	}

	/**
	 * 
	 * @author : Faisal Basra
	 * @Date : Jan 23, 2012
	 * 
	 * @Description : Method to add days into existing date.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date plusDays( Date date, int days ) {

		DateTime dateTime = new DateTime( date );
		dateTime = dateTime.plusDays( days );
		return dateTime.toDate( );
	}

	/**
	 * 
	 * @author : Faisal Basra
	 * @Date : Jan 23, 2012
	 * 
	 * @Description : Method to subtract days from date provided.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date minusDays( Date date, int days ) {

		DateTime dateTime = new DateTime( date );
		dateTime = dateTime.minusDays( days );
		return dateTime.toDate( );
	}

	/**
	 * @author : Waqas
	 * @Date : Feb 24, 2012
	 * 
	 * @Description : Method to get Current Time Stamp value
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp( ) {

		Date date = new Date( );
		return new Timestamp( date.getTime( ) );
	}
	

	/**
	 * @author : Fawad
	 * @Date : Sep 02, 2016
	 * 
	 * @Description : Method to get 12 Seconds After Time Stamp value
	 * 
	 * @return
	 */
	public static Timestamp getAfterTimeStamp( ) {

		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentTimestamp().getTime());
        cal.add(Calendar.SECOND, -13);
        Timestamp later = new Timestamp(cal.getTime().getTime());
        return later;
	}

	
	public static Timestamp getAfterTimeStamp1( ) {

		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentTimestamp().getTime());
        cal.add(Calendar.SECOND, -13);
        Timestamp later = new Timestamp(cal.getTime().getTime());
        return later;
	}
	/**
	 * @author : Waqas
	 * @Date : Feb 24, 2012
	 * 
	 * @Description :
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date getDateFromTimeStamp( Timestamp timestamp ) {

		if ( timestamp != null ) {
			Date date = new Date( timestamp.getTime( ) );
			return date;
		}
		return null;
	}

	/**
	 * 
	 * @author : Imran Ahmed
	 * @Date : Sep 10, 2012
	 * 
	 * @Description :
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTimeFromTimeStamp( Date date ) {

		if ( date != null ) {
			Format formatter = new SimpleDateFormat( "HH:mm" );

			return formatter.format( date );
		}
		return null;
	}

	/**
	 * 
	 * @author : Imran Ahmed
	 * @Date : Sep 12, 2012
	 * 
	 * @Description :
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	@SuppressWarnings( "deprecation" )
	public static Date dateTime( Date date, Date time ) {

		return new Date( date.getYear( ), date.getMonth( ), date.getDay( ),
				time.getHours( ), time.getMinutes( ), time.getSeconds( ) );
	}

	/**
	 * @author : Waqas
	 * @Date : Jun 13, 2012
	 * 
	 * @Description : To get Time stamp form Date
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getTimeStampFromDate( Date date ) {

		if ( date != null ) {
			return new Timestamp( date.getTime( ) );
		}
		return null;
	}

	/**
	 * @author : Kiran Azeem
	 * @Date : May 1, 2012
	 * 
	 * @Description :
	 * 
	 * @return
	 */
	public static String getApplicationLevelFormat( ) {

		return applicationLevelFormat;
	}

	/**
	 * 
	 * @author : Zeeshan Ahmad
	 * @Date : Nov 1, 2012
	 * 
	 * @Description :
	 * 
	 * @return
	 */
	public static String getImageFileTimeStamp( ) {

		return DateUtil.format( new java.util.Date( ), new SimpleDateFormat(
				DateUtil.IMAGE_FILE_DATE_FORMAT ) );
	}
	/**
	 * 
	 * @author : Bilal Iftikhar
	 * @Date : Jan 24, 2014
	 * 
	 * @Description :
	 * 
	 * @return
	 */
	public static String getNoteFileTimeStamp( ) {

		return DateUtil.format( new java.util.Date( ), new SimpleDateFormat(
				DateUtil.NOTE_FILE_DATE_FORMAT ) );
	}

	/**
	 * @author : Kiran Azeem
	 * @Date : Dec 20, 2012
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Long getDaysDifferenceBetweenTimeStamps( Timestamp fromDate,
			Timestamp toDate ) {

		if ( fromDate != null && toDate != null ) {
			final long MILLIS_PER_DAY = 24 * 3600 * 1000;
			Long msDiff = fromDate.getTime( ) - toDate.getTime( );
			Long daysDiff = Math.round( msDiff / ( ( double ) MILLIS_PER_DAY ) );
			return daysDiff;
		} else {
			return null;
		}
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getYear( String date ) {

		return Integer.parseInt( date.substring( date.length( ) - 4 ) );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getCurrentYear( String date ) {

		return getYear( date );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getMonth( String date ) {

		java.util.Date d = getDate( date, MM_DD_YYYY );
		return getMonth( d );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static java.util.Date getDate( String date, String formatString ) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( );
		try {
			simpleDateFormat.applyPattern( formatString );
			return simpleDateFormat.parse( date );
		} catch ( Exception e ) {
			e.printStackTrace( );
		}

		return null;
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static java.util.Date incrementDate( java.util.Date date, int days ) {

		Calendar calendar = Calendar.getInstance( );
		calendar.setTime( date );
		calendar.add( Calendar.DATE, days );
		return calendar.getTime( );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static String incrementDate( String date, int days ) {

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				MM_DD_YYYY );
		java.util.Date d = getDate( date, MM_DD_YYYY );
		return df.format( incrementDate( d, days ) );

	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getMonth( java.util.Date date ) {

		if ( date == null ) {
			return 0;
		} // end of if
		try {
			Calendar calendar = Calendar.getInstance( );
			calendar.setTime( date );
			return calendar.get( Calendar.MONTH );
		} // end of try
		catch ( Exception e ) {
			e.printStackTrace( );
		} // end of catch
		return 0;
	} // end of getMonth

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getDay( java.util.Date date ) {

		if ( date == null ) {
			return 0;
		} // end of if
		try {
			Calendar calendar = Calendar.getInstance( );
			calendar.setTime( date );
			return calendar.get( Calendar.DAY_OF_MONTH );
		} // end of try
		catch ( Exception e ) {
			e.printStackTrace( );
		} // end of catch
		return 0;
	} // end of getMonth

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static int getDay( String date ) {

		java.util.Date d = getDate( date, MM_DD_YYYY );
		return getDay( d );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static Long getDateDifferenceInMonths( Date mainDate,
			Date comparingDate ) {

		Long diff = null;

		SimpleDateFormat dtMainDateFormat = new SimpleDateFormat(
				mainDate.toString( ) );
		SimpleDateFormat dtComparingDateFormat = new SimpleDateFormat(
				comparingDate.toString( ) );

		if ( mainDate != null && comparingDate != null ) {
			int firstValue = dtMainDateFormat.getCalendar( ).get( Calendar.MONTH )
					+ ( dtMainDateFormat.getCalendar( ).get( Calendar.YEAR ) * 12 );
			int secondValue = dtComparingDateFormat.getCalendar( ).get(
					Calendar.MONTH )
					+ ( dtComparingDateFormat.getCalendar( ).get( Calendar.YEAR ) * 12 );
			int diff1 = firstValue - secondValue;
			diff = new Long( diff1 );
		}
		return diff;
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static final String convertDateToString( Date aDate,
			String formatToBeConverted ) {

		SimpleDateFormat df = null;
		String returnValue = "";

		try {
			if ( aDate != null ) {
				df = new SimpleDateFormat( formatToBeConverted );
				returnValue = df.format( aDate );
			}
		} catch ( Exception e ) {
			e.printStackTrace( );
			returnValue = "";
		}

		return ( returnValue );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static final String convertDateToString( String aDate,
			String aDatePattern, String formatToBeConverted ) {

		SimpleDateFormat df = null;
		String returnValue = "";

		try {
			if ( aDate != null && !aDate.equals( "" ) ) {
				df = new SimpleDateFormat( aDatePattern );
				Date newDate = df.parse( aDate );
				if ( newDate != null ) {
					df = new SimpleDateFormat( formatToBeConverted );
					returnValue = df.format( newDate );
				}
			}
		} catch ( Exception e ) {
			returnValue = "";
		}

		return ( returnValue );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static final Date convertDateToDate( Date aDate,
			String formatToBeConverted ) {

		SimpleDateFormat df = null;
		Date returnValue = null;

		try {

			if ( aDate != null ) {
				df = new SimpleDateFormat( formatToBeConverted );
				returnValue = df.parse( df.format( aDate ) );
			}
		} catch ( Exception e ) {
			System.out.println( "Exception : " + e.toString( ) );
		}

		return ( returnValue );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static final Date convertStringToDate( String aDate,
			String aDatePattern, String formatToBeConverted ) {

		SimpleDateFormat df = null;
		Date returnValue = null;

		try {
			if ( aDate != null && !aDate.equals( "" ) ) {
				df = new SimpleDateFormat( aDatePattern );
				Date newDate = df.parse( aDate );
				if ( newDate != null ) {
					df = new SimpleDateFormat( formatToBeConverted );
					returnValue = df.parse( df.format( newDate ) );
				}
			}
		} catch ( Exception e ) {
			returnValue = null;
		}

		return ( returnValue );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static final Date convertStringToDate( String aDate,
			String formatToBeConverted ) {

		SimpleDateFormat df = null;
		Date returnValue = null;

		try {
			if ( aDate != null && !aDate.equals( "" ) ) {
				df = new SimpleDateFormat( formatToBeConverted );

				returnValue = df.parse( aDate );
			}
		} catch ( Exception e ) {
			returnValue = null;
		}

		return ( returnValue );
	}

	/**
	 * @author Abu Turab
	 * @param date
	 * @return
	 */
	public static Date addDays( Date date, int noOfDays ) {

		Calendar c = new GregorianCalendar( );
		c.setTime( date );
		c.add( Calendar.DAY_OF_MONTH, noOfDays );
		date = c.getTime( );

		return date;
	}
	
	public static Date addYears( Date date, int years ) {

		Calendar c = new GregorianCalendar( );
		c.setTime( date );
		c.add( Calendar.YEAR, years );
		date = c.getTime( );

		return date;
	}
	
	public static Date minusYears( Date date, int years ) {

		DateTime dateTime = new DateTime( date );
		dateTime = dateTime.minusYears( years );
		return dateTime.toDate( );
	}
	
	public static Date minusMonths( Date date, int months ) {

		DateTime dateTime = new DateTime( date );
		dateTime = dateTime.minusYears( months );
		return dateTime.toDate( );
	}
	
	public static Date getMongoDbDate(Date date) {

        try {
    		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		int month = cal.get(Calendar.MONTH)+1;
    		int day = cal.get(Calendar.DAY_OF_MONTH);
    		int year = cal.get(Calendar.YEAR);
    		String dateInString = month+"-"+day+"-"+year;
            
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
            
	}
	
	public static Date getCurrentDateWithoutTime( ) {
		Date date = new Date( );
		try {
			return parse( format( date, mFormat8chars  ), mFormat8chars);
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Abu Turab
	 * @param dt1
	 *            , dt2
	 * @return diff
	 */
	public static Long getDifferenceInYears( Timestamp dt1, Timestamp dt2 ) {

		Long diffInDays = ( Long ) ( ( dt1.getTime( ) - dt2.getTime( ) ) / ( 1000 * 60 * 60 * 24 ) );

		return diffInDays / 365;
	}

	/**
	 * @author Abu Turab
	 * 
	 */
	public static String getCardExpiryDate( ) {

		Calendar c = Calendar.getInstance( );
		c.add( Calendar.YEAR, 5 );

		return format( c.getTime( ), new SimpleDateFormat( "MM/dd/yyyy" )  );
	}

	/**
	 * @author : Abu Turab
	 * @Date : Sep 26, 2013
	 * 
	 * @Description :
	 * 
	 * @param dt
	 * @return
	 */
	public static String Under18Until( Date dt ) {

		Calendar c = Calendar.getInstance( );
		int age = getAge( dt );
		int diff = 18 - age;
		c.add( Calendar.YEAR, diff );

		return format( c.getTime( ), new SimpleDateFormat( "MM/dd/yyyy" )  );
	}

	/**
	 * @author : Abu Turab
	 * @Date : Sep 26, 2013
	 * 
	 * @Description :
	 * 
	 * @param dt
	 * @return
	 */
	public static String Under19Until( Date dt ) {

		Calendar c = Calendar.getInstance( );
		int age = getAge( dt );
		int diff = 19 - age;
		c.add( Calendar.YEAR, diff );

		return format( c.getTime( ), new SimpleDateFormat( "MM/dd/yyyy" )  );
	}

	/**
	 * @author : Abu Turab
	 * @Date : Sep 26, 2013
	 * 
	 * @Description :
	 * 
	 * @param dt
	 * @return
	 */
	public static String Under21Until( Date dt ) {

		Calendar c = Calendar.getInstance( );
		int age = getAge( dt );
		int diff = 21 - age;
		c.add( Calendar.YEAR, diff );

		return format( c.getTime( ), new SimpleDateFormat( "MM/dd/yyyy" )  );
	}

	/**
	 * @author : Abu Turab
	 * @Date : Sep 26, 2013
	 * 
	 * @Description :
	 * 
	 * @param birthdate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getAge( Date birthdate ) {

		long ageInMillis = new Date( ).getTime( ) - birthdate.getTime( );

		Date age = new Date( ageInMillis );

		return age.getYear( );
	}
	
	/**
	 * @author      : Abu Turab
	 * @Date        : Sep 26, 2013
	 *
	 * @Description :
	 *      
	 */
	public static String getCardDateOfIssue ( ) {
		
		return format( new Date(), new SimpleDateFormat( "MM/dd/yyyy" )  );
		
	}
	
	/**
	 * 
	 * @author      : Zeeshan Ahmad
	 * @Date        : Dec 26, 2013
	 *
	 * @Description : Generate Stiker No as per formate i.e, 613JUK
	 *
	 * @return
	 */
	public static String getVehStickerNo(){
		return format( new Date(), new SimpleDateFormat( "MyyMMM" )).toUpperCase( );
	}
	/**
	 * 
	 * @author      : TalibGill
	 * @Date        : Jan 28, 2014
	 *
	 * @Description :
	 *
	 * @param sDate
	 * @param eDate
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static Boolean startEndDateValidation(Date sDate, Date eDate, Date minDate, Date maxDate) {
		if(sDate == null || eDate == null || minDate == null || maxDate == null){
			return Boolean.FALSE;
		}
		if( isValidDateRange(minDate, sDate ) && isValidDateRange( sDate, eDate )  && 
				isValidDateRange( eDate, maxDate ) && isValidDateRange( minDate, maxDate )  ){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	/**
	 * 
	 * @return
	 */
	public static String getTempCardExpiryDate(int year) {
		Calendar c = Calendar.getInstance( );
		c.add( Calendar.YEAR, year );
		return format( c.getTime( ), new SimpleDateFormat( "MM/dd/yyyy" )  );
	}
	
	public static String getMinutesAndSecs(Double totalSecs) {
		int secs = (int)Math.ceil(totalSecs);
		int minutes = secs / 60;
		int seconds = secs % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}
	
	public static String getMonthName(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
	
	public static String getInvoiceDate(Timestamp stamp){
		long timestamp = stamp.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
//		System.out.println(cal.get(Calendar.YEAR));
//		System.out.println(cal.get(Calendar.MONTH)+1);
//		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		
		return getMonthName(cal.get(Calendar.MONTH))+" "+cal.get(
				Calendar.DAY_OF_MONTH)+", "+cal.get(Calendar.YEAR);
	}
	
}
