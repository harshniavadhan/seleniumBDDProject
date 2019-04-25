
package com.identity.commonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

public class CommonUtils {
	private static int shortTime = 8;
	private static int LongTime = 45;
	private static int mediumTime = 25;
	private static int veryShortTime = 4;
	private static int ExtraLongTime = 240;
	private static int VeryExtraLongTime = 1800;

	public static int generateRandomNumber(int Min, int Max) {

		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static int getExtraLongTime() {
		return ExtraLongTime;
	}

	public static void setExtraLongTime(int extraLongTime) {
		ExtraLongTime = extraLongTime;
	}

	public static int getVeryExtraLongTime() {
		return VeryExtraLongTime;
	}

	public static void setVeryExtraLongTime(int VeryextraLongTime) {
		VeryExtraLongTime = VeryextraLongTime;
	}

	public static int getLongTime() {
		return LongTime;
	}

	public static int getShortTime() {
		return shortTime;
	}

	public static int getVeryShortTime() {
		return veryShortTime;
	}

	public static int getMediumTime() {
		return mediumTime;
	}

	public static String generateRandomPhoneNumber() {

		Random rand = new Random();
		int i1 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		int i2 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		int i3 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		DecimalFormat dc = new DecimalFormat("000");
		return dc.format(i1) + "-" + dc.format(i2) + "-" + dc.format(i3);
	}

	public static Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		};
	}
	
	public static boolean checkIfTwoMapsAreSame(HashMap<String, String> A, HashMap<String, String> B) {
		System.out.println("the UI value Map is -" + A);
		System.out.println("the actual value Map is -" + B);
		boolean isEqual = true;
		if(A.keySet() != null && B.keySet()!=null && A.keySet().equals(B.keySet())) {
			for(String key:A.keySet()) {
				if(!A.get(key).trim().equals(B.get(key).trim())) {
					isEqual = false;
					break;
				}
			}
		}
		return isEqual;
	}
	
	public static ArrayList<String> unZipIt(String zipFileFolder, String outputFolder){

	     byte[] buffer = new byte[1024];
	     ArrayList<String> filePathListInZip = new ArrayList<String> ();

	     try{

	    	//create output directory is not exists
	    	File folder = new File(outputFolder);
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}

	    	//get the zip file content
	    	String zipFilePath = "";
	    	File zipFolder = new File(zipFileFolder);
	    	File[] fileArray = zipFolder.listFiles();
	    	for(File f: fileArray) {
	    		if(f.getName().contains("Keyword_Ranking_Daily_History_249211-93209")) {
	    			zipFilePath = f.getAbsolutePath();
	    		}
	    	}
	    	ZipInputStream zis =
	    		new ZipInputStream(new FileInputStream(zipFilePath));
	    	//get the zipped file list entry
	    	ZipEntry ze = zis.getNextEntry();
	    	

	    	while(ze!=null){

	    	   String fileName = ze.getName();
	    	   String fullyQualifiedFilePath = outputFolder + File.separator + fileName;
	    	   filePathListInZip.add(fullyQualifiedFilePath);
	           File newFile = new File(outputFolder + File.separator + fileName);

	           System.out.println("file unzip : "+ newFile.getAbsoluteFile());

	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	            new File(newFile.getParent()).mkdirs();

	            FileOutputStream fos = new FileOutputStream(newFile);

	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	       		fos.write(buffer, 0, len);
	            }

	            fos.close();
	            ze = zis.getNextEntry();
	    	}

	        zis.closeEntry();
	    	zis.close();

	    	System.out.println("Done");

	    }catch(IOException ex){
	       ex.printStackTrace();
	    }
	     
	     return filePathListInZip;
	}
	
	public void deleteFile() {
		File dir = new File(System.getProperty("user.dir") + "/src/resources/downloads/");

		File[] dirContents = dir.listFiles();

		/*
		 * for (int i = 0; i < dirContents.length; ) { // File has been found,
		 * it can now be deleted: dirContents[i].delete(); return true; }
		 */

		for (File files : dirContents) {
			System.out.println("THESE ARE FILES PRESENT" + files);
			files.delete();

		}

	}

	public static String getFutureDate(int days, String format) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format);
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, days); // Adds 15 days
		return df.format(c.getTime());
	}

	public static String getCurrentDate(int days, String format) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format);
		c.setTime(new Date());// setting today's date
		return df.format(c.getTime());
	}
	
	
	public static String getFutureMinutes(int minutesToAdd){
		String newTime = "";
		try {
		//String myTime = "14:10";
	 Calendar cal = Calendar.getInstance();
	 SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	 String myTime = dateFormat.format(cal.getTime());
	
	 Date d;

		d = dateFormat.parse(myTime);
	
	
	 cal.setTime(d);
	 cal.add(Calendar.MINUTE, minutesToAdd);
	  newTime = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return newTime;
	}
	
	public static String getMonthBasedonFirstWeekLogic() {
		int month = 0;
		String monthName = "";
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		int date = c.get(Calendar.DAY_OF_MONTH);
		// System.out.println("the date value of today is - " + date);// this is
		// the logic we need to use ...
		month = c.get(Calendar.MONTH);
		// System.out.println("the month value of today is - " + month);
		if (date > 6) {
			monthName = months[month];
		} else {
			if (month > 0) {
				monthName = months[month - 1];// cutting down the Month if
												// greater then 0.
			} else {
				monthName = months[11];
			}
		}
		return monthName;
	}

	public static String getYearBasedonFirstWeekLogic() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		int date = c.get(Calendar.DAY_OF_MONTH);
		// System.out.println("the date value of today is - " + date);
		int year = c.get(Calendar.YEAR);
		// System.out.println("the year value of today is - " + year);
		int month = c.get(Calendar.MONTH);
		// System.out.println("the month value of today is - " + month);
		if (date < 7 && month == 0) {
			year = year - 1;
		}

		return Integer.valueOf(year).toString();
	}

	public static String getPastTime(int hours, String format) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format);
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.SECOND, 0);
		return df.format(c.getTime());
	}

	/*
	 * public static void main (String[] args) { boolean result = parse(
	 * "27 May 2009", "dd MMMM YYYY"); if(result) { System.out.println(
	 * "my test passed"); }else { System.out.println("failed"); } }
	 */

	public static boolean parseDate(String d, String format) {
		boolean isPatternMatching = false;
		if (d != null) {

			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				sdf.parse(d);
				System.out.println("pattern is matching");
				isPatternMatching = true;
			} catch (Exception e) {
				System.out.println("exception");
				e.printStackTrace();
			}
		}
		return isPatternMatching;
	}
}