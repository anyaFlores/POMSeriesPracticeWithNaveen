package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {

		System.out.println("reading data from sheet: " + sheetName);

		Object data[][] = null;

		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);

			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

}



//package com.qa.opencart.utils;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//
//public class ExcelUtil {
//
//	// to read data from Excel file
//	private static final String TEST_DATA_SHEET_PATH = ".src\\test\\resources\\testdata\\OpenCartTestData.xlsx";
//	private static Workbook book;
//	private static Sheet sheet;
//
//	public static Object[][] getTestData(String sheetName) {// give me the sheetName
//		System.out.println("reading data from sheet: " + sheetName);
//		// data in 2 dimentional data array
//		Object data[][] = null;// just define it
//
//		try {
//			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
//			book = WorkbookFactory.create(ip); //get the book
//			sheet = book.getSheet(sheetName);// get the sheet
//
//			// data got initialized:
//			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
//
//			for (int i = 0; i < sheet.getLastRowNum(); i++) {
//				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
//					
//					//write the data in 2 dimential object array
//					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();// go to i+1 row and j=0; it is a excel
//																			// String - convert into java String
//					// 1st time= 0 0
//					// 2nd time = 0 1....and then value of i will be increased
//				}
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return data;
//	}
//
//}
