package com.qa.democart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	public static String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/data.xlsx";
	public static Workbook workBook = null;
	public static Sheet sheet = null;

	public static Object[][] getTestData(String sheetName) {
		FileInputStream fis = null;
		Object excelData[][] = null;

		try {
			fis = new FileInputStream(TEST_DATA_SHEET_PATH);
			workBook = WorkbookFactory.create(fis);

			sheet = workBook.getSheet(sheetName);
			// Create a 2-D Object array with dimensions such that the last row count
			// being picked from the excel and the last column count
			// of the first row as all the rows have the same column

			excelData = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					excelData[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return excelData;
	}
}
