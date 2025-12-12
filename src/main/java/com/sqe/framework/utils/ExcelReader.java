package com.sqe.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private Workbook workbook;
    private Sheet sheet;
    private boolean fileExists;

    public ExcelReader(String filePath) {
        fileExists = false;
        workbook = null;

        try {
            // Try to load from classpath first
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(filePath);

            if (inputStream != null) {
                workbook = new XSSFWorkbook(inputStream);
                fileExists = true;
                System.out.println("Loaded Excel file from classpath: " + filePath);
            } else {
                // Try to load from file system
                try {
                    FileInputStream fis = new FileInputStream(filePath);
                    workbook = new XSSFWorkbook(fis);
                    fis.close();
                    fileExists = true;
                    System.out.println("Loaded Excel file from filesystem: " + filePath);
                } catch (IOException e) {
                    System.out.println("Excel file not found: " + filePath + ". Creating empty workbook.");
                    createEmptyWorkbook();
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading Excel file: " + e.getMessage() + ". Creating empty workbook.");
            createEmptyWorkbook();
        }
    }

    private void createEmptyWorkbook() {
        workbook = new XSSFWorkbook();
        fileExists = false;

        // Create default sheets
        createDefaultLoginSheet();
        createDefaultProductsSheet();
        createDefaultUsersSheet();
    }

    private void createDefaultLoginSheet() {
        Sheet loginSheet = workbook.createSheet("Login");

        // Create header row
        Row headerRow = loginSheet.createRow(0);
        String[] headers = {"Username", "Password", "ExpectedResult"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Create data rows
        String[][] loginData = {
                {"standard_user", "secret_sauce", "Success"},
                {"locked_out_user", "secret_sauce", "Fail"},
                {"problem_user", "secret_sauce", "Success"},
                {"performance_glitch_user", "secret_sauce", "Success"}
        };

        for (int i = 0; i < loginData.length; i++) {
            Row row = loginSheet.createRow(i + 1);
            for (int j = 0; j < loginData[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(loginData[i][j]);
            }
        }
    }

    private void createDefaultProductsSheet() {
        Sheet productsSheet = workbook.createSheet("Products");

        // Create header row
        Row headerRow = productsSheet.createRow(0);
        String[] headers = {"Name", "Price", "Category"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Create data rows
        String[][] productsData = {
                {"Sauce Labs Backpack", "29.99", "Backpack"},
                {"Sauce Labs Bike Light", "9.99", "Accessory"},
                {"Sauce Labs Bolt T-Shirt", "15.99", "Clothing"},
                {"Sauce Labs Fleece Jacket", "49.99", "Clothing"},
                {"Sauce Labs Onesie", "7.99", "Clothing"},
                {"Test.allTheThings() T-Shirt (Red)", "15.99", "Clothing"}
        };

        for (int i = 0; i < productsData.length; i++) {
            Row row = productsSheet.createRow(i + 1);
            for (int j = 0; j < productsData[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(productsData[i][j]);
            }
        }
    }

    private void createDefaultUsersSheet() {
        Sheet usersSheet = workbook.createSheet("Users");

        // Create header row
        Row headerRow = usersSheet.createRow(0);
        String[] headers = {"Username", "Password", "FirstName", "LastName"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Create data rows
        String[][] usersData = {
                {"standard_user", "secret_sauce", "Standard", "User"},
                {"locked_out_user", "secret_sauce", "Locked", "Out"},
                {"problem_user", "secret_sauce", "Problem", "User"},
                {"performance_glitch_user", "secret_sauce", "Performance", "Glitch"}
        };

        for (int i = 0; i < usersData.length; i++) {
            Row row = usersSheet.createRow(i + 1);
            for (int j = 0; j < usersData[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(usersData[i][j]);
            }
        }
    }

    public void setSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
    }

    public List<Map<String, String>> getAllData() {
        List<Map<String, String>> data = new ArrayList<>();
        if (sheet == null) {
            return data;
        }

        int rowCount = sheet.getPhysicalNumberOfRows();
        if (rowCount <= 1) {
            return data; // Only header or empty
        }

        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        // Get headers
        Row headerRow = sheet.getRow(0);
        String[] headers = new String[colCount];
        for (int i = 0; i < colCount; i++) {
            headers[i] = getCellValue(headerRow.getCell(i));
        }

        // Get data rows
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                String header = headers[j];
                String value = getCellValue(row.getCell(j));
                rowData.put(header, value);
            }
            data.add(rowData);
        }

        return data;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileExists() {
        return fileExists;
    }

    // Static utility method
    public static List<Map<String, String>> getTestData(String sheetName) {
        String filePath = "testdata/testdata.xlsx";
        ExcelReader reader = new ExcelReader(filePath);
        reader.setSheet(sheetName);
        List<Map<String, String>> data = reader.getAllData();
        reader.close();
        return data;
    }
}