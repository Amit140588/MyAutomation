package com.abitnow.Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtilities {
	static String filePath;

	public ExcelUtilities(String filePath) {
		ExcelUtilities.filePath = filePath;
	}

	public String readData(String sheetName, int row, int cell) {

		String value = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fis);
			Cell cl = wb.getSheet(sheetName).getRow(row).getCell(cell);
			value = cl.getStringCellValue();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return value;
	}
}
