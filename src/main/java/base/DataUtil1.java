package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataUtil1 {

	public static String TESTDATA_SHEET_PATH = "./src/test/resources/testdata/test_data1.xlsx";
	public static XSSFWorkbook book;
	public static XSSFSheet sheet;

	@DataProvider(name = "dp2") // Using Method Name for the sheets
	public Object[][] getData1(Method m) {
		FileInputStream file = null;

		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			book = new XSSFWorkbook(file);

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sheetName = m.getName();
		sheet = book.getSheet(sheetName);

		int rows = sheet.getLastRowNum(); // This will give total number indexes
		int cols = sheet.getRow(0).getLastCellNum(); // This will give total number of cols

		Object[][] data = new Object[rows][1];
		Hashtable<String, String> table = null;

		for (int rowNum = 1; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < cols; colNum++) {

				table.put(sheet.getRow(0).getCell(colNum).toString(), sheet.getRow(rowNum).getCell(colNum).toString());

				data[rowNum - 1][0] = table;
			}
		}

		return data;
	}

	@DataProvider(name = "dp3") // Using the same sheet for all the test cases
	public static Object[][] getData2(Method m) {
		FileInputStream file = null;

		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			book = new XSSFWorkbook(file);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//sheet = book.getSheet("data");// To retrieve the sheet name
		sheet = book.getSheet("Sheet1");
		int rows = sheet.getLastRowNum();
		System.out.println("Total rows are : " + (rows + 1));

		String testName = m.getName();
		System.out.println("Test name is : " + testName);

		// Find the test case start row

		int testCaseRowNum = 0;

		OuterLoop: for (testCaseRowNum = 0; testCaseRowNum <= rows; testCaseRowNum++) {

			String testCaseName = "";
			// excel.getCellData(config.getProperty("testDataSheetName"), 0,
			// testCaseRowNum);

			XSSFCell cell = null;
			cell = (XSSFCell) sheet.getRow(testCaseRowNum).getCell(0);

			if (cell != null) {
				testCaseName = sheet.getRow(testCaseRowNum).getCell(0).toString();
				if (testCaseName.equalsIgnoreCase(testName))
					break OuterLoop;
			}

			// testCaseName=sheet.getRow(testCaseRowNum).getCell(testCaseRowNum).getStringCellValue();

		}

		System.out.println("Test case starts from row num: " + (testCaseRowNum + 1));

		// Checking total rows in test case

		int dataStartRowNum = testCaseRowNum + 2;

		int testRows = 0;
		CellType cellType = null;
		XSSFCell cell1 = null;
		// boolean b = true;
		// row1.getCell(0).getCellType().BLANK

		while (true) {

			try {
				cell1 = sheet.getRow(dataStartRowNum + testRows).getCell(0);
				if(cell1==null) {
					break;
				}
				// cellType = sheet.getRow(dataStartRowNum + testRows).getCell(0).getCellType();
			} catch (Exception e) {
				break;
			}

			testRows++;
			//System.out.println(testRows);
		}

		System.out.println("Total rows of data are : " + testRows);

		// Checking total cols in test case

		int colStartColNum = testCaseRowNum + 1;
		int testCols = 0;
		XSSFCell cell2 = null;
		while (true) {
			try {
				cell2 = sheet.getRow(colStartColNum).getCell(testCols);
				if (cell2 == null) {
					break;
				}
			} catch (Exception t) {
				break;
			}
			testCols++;
			//System.out.println(testCols);

		}

		System.out.println("Total cols are : " + testCols);

		// Printing data

		Object[][] data = new Object[testRows][1];

		int i = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {

			Hashtable<String, String> table = new Hashtable<String, String>();

			for (int cNum = 0; cNum < testCols; cNum++) {

				// System.out.println(excel.getCellData(config.getProperty("testDataSheetName"),
				// cNum, rNum));
				String testData = sheet.getRow(rNum).getCell(cNum).toString();
				//System.out.println(testData);
				// String testData = excel.getCellData(config.getProperty("testDataSheetName"),
				// cNum, rNum);
				String colName = sheet.getRow(colStartColNum).getCell(cNum).toString();
				//System.out.println(colName);
				// String colName = excel.getCellData(config.getProperty("testDataSheetName"),
				// cNum, colStartColNum);

				table.put(colName, testData);

			}

			data[i][0] = table;
			i++;

		}

		return data;
	}
}
