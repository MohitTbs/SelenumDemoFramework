package base;

import base.driver.PageDriver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Util {

	// applicable for all browser, but takes screnshot only the visible portion of
	// the browser
	public static String getScreenshot(String imagename) throws IOException, IOException {
		TakesScreenshot ts = (TakesScreenshot) PageDriver.getCurrentDriver();
		File f = ts.getScreenshotAs(OutputType.FILE);
		String filePath = "./screenshot/" + imagename;
		FileUtils.copyFile(f, new File(filePath));
		return filePath;
	}

	public static String convertImg_Base64(String screenshotPath) throws IOException {
		/*
		 * File f = new File(screenshotPath); FileInputStream fis = new
		 * FileInputStream(f); byte[] bytes = new byte[(int)f.length()];
		 * fis.read(bytes); String base64Img = new String(Base64.encodeBase64(bytes),
		 * StandardCharsets.UTF_8);
		 */

		byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
		String base64Img = Base64.encodeBase64String(file);
		return base64Img;
	}

	public static double[] changeStringArraytoDouble(String[] arr) {
		double[] intArray = new double[arr.length];

		for (int i = 0; i < arr.length; i++) {
			intArray[i] = Double.valueOf(arr[i]);
		}

		return intArray;
	}

	public static boolean checkAscending(double[] arr) {

		for (int i = 0, j = 1; i < arr.length && j < arr.length; i++, j++) {
			if (!(arr[i] <= arr[j])) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkDescending(double[] arr) {

		for (int i = 0, j = 1; i < arr.length && j < arr.length; i++, j++) {
			if (!(arr[i] >= arr[j])) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkAlphabeticAZ(String[] arr) {

		int len = arr.length;

		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (arr[i].toLowerCase().compareTo(arr[j].toLowerCase()) > 0) {
					return false;
				}
			}
		}
		return true;

	}
	
	public static boolean checkAlphabeticZA(String[] arr) {

		int len = arr.length;

		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (arr[i].toLowerCase().compareTo(arr[j].toLowerCase()) < 0) {
					return false;
				}
			}
		}
		return true;

	}
}
