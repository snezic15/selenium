package com.example.seleniumdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.seleniumdemo.CaptureData;
import com.example.seleniumdemo.FetchData;

public class excelUtility {
	static ArrayList<String> headers = new ArrayList<String>();

	public static ArrayList<ArrayList<String>> getDetailsFromExcel(String inputFilePath, String sheetName)
			throws Exception {
		DataFormatter df = new DataFormatter();
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try {
			FileInputStream fips = new FileInputStream(inputFilePath);

			XSSFWorkbook workbook = new XSSFWorkbook(fips);
			XSSFSheet worksheet;
			if (!sheetName.isEmpty()) {
				worksheet = workbook.getSheet(sheetName);
			} else {
				worksheet = workbook.getSheetAt(0);
			}

			int lastRowNum = worksheet.getLastRowNum();
			int headerSize = 0;

			for (int i = 1; i <= lastRowNum; i++) {
				ArrayList<String> rowData = new ArrayList<String>();
				XSSFRow row = worksheet.getRow(i);

				int lastCellNumPlusOne = row.getLastCellNum();
				headerSize = lastCellNumPlusOne;
				int j;
				for (j = 0; j < lastCellNumPlusOne; j++) {
					XSSFCell cell = row.getCell(j);

					String cellData = df.formatCellValue(cell);
					rowData.add(cellData);
				}
				while (j < headerSize) {
					rowData.add("");
					j++;

				}
				data.add(rowData);
			}
			workbook.close();

		} catch (Exception e) {
			System.out.println(
					"\n could not get data from file at " + inputFilePath + " with sheet name " + sheetName + " \n");
			throw e;
		}

		return data;
	}

	public static void saveToExcel(String OutputFileLocation, String SheetName) throws Exception {
		boolean sheetCreated = createSheet(OutputFileLocation, SheetName);

		if (sheetCreated) {
			headers.add("SNO");
			headers.add("CaseType_CaseNumber_CaseYear");
			headers.add("Petitioner_versus_Respondent");
			headers.add("Case Details_Case Type");
			headers.add("Case Details_Filing Number");
			headers.add("Case Details_Filing Date");
			headers.add("Case Details_Registration Number");
			headers.add("Case Details_Registration Date");
			headers.add("Case Details_CNR Number");
			headers.add("Case Stauts_FirstHearingDate");
			headers.add("Case Stauts_NextHearingDate/DecisionDate");
			headers.add("Case Stauts_CaseStage");
			headers.add("Case Stauts_NatureofDisposal");
			headers.add("Case Stauts_CourtNumberandJudge");
			headers.add("PetitionerandAdvocate");
			headers.add("RespondentandAdvocate");
			headers.add("Acts_UnderAct");
			headers.add("Acts_UnderSection");
			headers.add("Case History");
			addRowToExcel(headers, OutputFileLocation, SheetName);
		}

		for (int i = 0; i < CaptureData.Casetype.size(); i++) {
			ArrayList<String> outputRow = new ArrayList<String>();
			outputRow.add(FetchData.getSNo().get(i));
			outputRow.add(FetchData.getCaseType_CaseNumber_CaseYear().get(i));
			outputRow.add(FetchData.getPetitioner_versus_Respondent().get(i));
			outputRow.add(CaptureData.getCasetype().get(i));
			outputRow.add(CaptureData.getFilingNumber().get(i));
			outputRow.add(CaptureData.getFilingDate().get(i));
			outputRow.add(CaptureData.getRegistrationNumber().get(i));
			outputRow.add(CaptureData.getRegistrationDate().get(i));
			outputRow.add(CaptureData.getCNRNumber().get(i));
			outputRow.add(CaptureData.getFirstHearingDate().get(i));
			outputRow.add(CaptureData.getNextHearingDate().get(i));
			outputRow.add(CaptureData.getCaseStage().get(i));
			outputRow.add(CaptureData.getNatureofDisposal().get(i));
			outputRow.add(CaptureData.getCourtNumberandJudge().get(i));
			outputRow.add(CaptureData.getPetitionerandAdvocate().get(i));
			outputRow.add(CaptureData.getRespondentandAdvocate().get(i));
			outputRow.add(CaptureData.getUnderAct().get(i));
			outputRow.add(CaptureData.getUnderSection().get(i));
			outputRow.add(CaptureData.getCaseHistory().get(i));
			addRowToExcel(outputRow, OutputFileLocation, SheetName);
		}
	}

	public static boolean createSheet(String Path, String Sheetname) throws Exception {
		try {
			FileInputStream fips = new FileInputStream(Path);
			XSSFWorkbook workbook = new XSSFWorkbook(fips);
			XSSFSheet worksheet = workbook.getSheet(Sheetname);

			if (worksheet == null) {
				workbook.createSheet(Sheetname);
				FileOutputStream updatedFile = new FileOutputStream(Path);
				workbook.write(updatedFile);
				updatedFile.close();
				workbook.close();
				return true;
			} else {
				workbook.close();
				return false;
			}
		} catch (Exception e) {
			System.out.println("\n Error While creating new sheet " + Sheetname);
			e.printStackTrace();
			throw e;
		}

	}

	public static void addRowToExcel(ArrayList<String> Result, String Path, String Sheetname) {
		try {
			FileInputStream fips = new FileInputStream(Path);
			XSSFWorkbook wb = new XSSFWorkbook(fips);
			XSSFSheet ws = wb.getSheet(Sheetname);

			int lastRowNum = ws.getLastRowNum();
			XSSFRow rowx;
			int x;
			if (lastRowNum != 0)
				x = lastRowNum + 1;
			else {
				int num = ws.getPhysicalNumberOfRows();
				if (num == 0)
					x = 0;
				else
					x = 1;
			}
			rowx = ws.createRow(x);
			int i = 0;

			for (String s : Result) {
				XSSFCell cellx = rowx.createCell(i);
				cellx.setCellValue(s);
				i++;
			}
			fips.close();
			FileOutputStream output = new FileOutputStream(Path);
			wb.write(output);
			output.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String createExcel(String Path, String filename) throws FileNotFoundException {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String pathToOutput = Path + File.separator + filename + sdf.format(Calendar.getInstance().getTime()) + ".xlsx";
		File file = new File(pathToOutput);
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			// wb.createSheet(filename);
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathToOutput;

	}

}
