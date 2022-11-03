package com.example.seleniumdemo.jsoncompilation.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class ExcelUtil {
    public static ArrayList<ArrayList<String>> getDetailsFromExcel(String inputFilePath, String sheetName)
            throws Exception {
        DataFormatter df = new DataFormatter();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try {
            FileInputStream fips = new FileInputStream(inputFilePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fips);
            XSSFSheet worksheet;

            if (!sheetName.isEmpty()) worksheet = workbook.getSheet(sheetName);
            else worksheet = workbook.getSheetAt(0);

            int lastRowNum = worksheet.getLastRowNum();

            for (int i = 1; i <= lastRowNum; i++) {
                ArrayList<String> rowData = new ArrayList<>();
                XSSFRow row = worksheet.getRow(i);

                int lastCellNumPlusOne = row.getLastCellNum();
                int j;

                for (j = 0; j < lastCellNumPlusOne; j++) {
                    XSSFCell cell = row.getCell(j);
                    String cellData = df.formatCellValue(cell);
                    rowData.add(cellData);
                }

                data.add(rowData);
            }

            workbook.close();
        } catch (Exception e) {
            System.out.println("Could not get data from file at [" + inputFilePath + "] with sheet name [" + sheetName + "]");
            throw e;
        }

        return data;
    }

    public static boolean createSheet(String path, String sheetName) throws Exception {
        try {
            FileInputStream fips = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(fips);
            XSSFSheet worksheet = workbook.getSheet(sheetName);

            if (worksheet == null) {
                workbook.createSheet(sheetName);
                FileOutputStream updatedFile = new FileOutputStream(path);
                workbook.write(updatedFile);
                updatedFile.close();
                workbook.close();
                return true;
            } else {
                workbook.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error while creating new sheet [" + sheetName + "]");
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
            XSSFRow row;
            int x;

            if (lastRowNum != 0) x = lastRowNum + 1;
            else {
                int num = ws.getPhysicalNumberOfRows();
                if (num == 0)
                    x = 0;
                else
                    x = 1;
            }

            row = ws.createRow(x);
            int i = 0;

            for (String s : Result) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(s);
                i++;
            }

            fips.close();
            FileOutputStream output = new FileOutputStream(Path);
            wb.write(output);
            output.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createExcel(String path, String filename) throws IOException {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Path p = Paths.get(path);
        Files.createDirectories(p);

        String pathToOutput = path + File.separator + filename + sdf.format(Calendar.getInstance().getTime()) + ".xlsx";
        File file = new File(pathToOutput);

        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathToOutput;
    }

    public static void saveToExcel(String outputPath, String sheetName, LinkedHashMap<String, String> data) throws Exception {
        boolean sheetCreated = createSheet(outputPath, sheetName);

        if (sheetCreated) {
            ArrayList<String> headers = new ArrayList<>(data.keySet());
            addRowToExcel(headers, outputPath, sheetName);
        }

        ArrayList<String> outputRow = new ArrayList<>(data.values());
        addRowToExcel(outputRow, outputPath, sheetName);
    }
}