/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.selenium;

/**
 *
 * @author anik
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    public static void writeToExcel(String filePath, String keyword, String longestOption, String shortestOption) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;

        try {
            // Open the Excel file
            File excelFile = new File(filePath);
            if (!excelFile.exists()) {
                throw new IOException("Excel file does not exist at: " + filePath);
            }

            fileInputStream = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fileInputStream);

            // Get the current day's sheet
            String currentDay = java.time.LocalDate.now().getDayOfWeek().toString();
            Sheet sheet = workbook.getSheet(currentDay);

            if (sheet == null) {
                throw new IOException("Sheet for the current day (" + currentDay + ") does not exist.");
            }

            // Search for the row with the keyword and update the corresponding columns
            boolean keywordFound = false;

            for (Row row : sheet) {
                Cell keywordCell = row.getCell(1); // Column B (index 1) for the keyword
                if (keywordCell != null && keywordCell.getStringCellValue().equalsIgnoreCase(keyword)) {
                    keywordFound = true;

                    // Update or create cells for longest and shortest options
                    Cell longestCell = row.getCell(2); // Column C (index 2)
                    if (longestCell == null) {
                        longestCell = row.createCell(2);
                    }
                    longestCell.setCellValue(longestOption);

                    Cell shortestCell = row.getCell(3); // Column D (index 3)
                    if (shortestCell == null) {
                        shortestCell = row.createCell(3);
                    }
                    shortestCell.setCellValue(shortestOption);

                    break;
                }
            }

            if (!keywordFound) {
                throw new IOException("Keyword '" + keyword + "' not found in the sheet.");
            }

            // Close the input stream and prepare for writing
            fileInputStream.close();

            fileOutputStream = new FileOutputStream(excelFile);
            workbook.write(fileOutputStream);

            System.out.println("Excel updated successfully for keyword: " + keyword);

        } catch (Exception e) {
            System.err.println("Error while updating Excel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
                if (workbook != null) workbook.close();
            } catch (IOException ex) {
                System.err.println("Error closing resources: " + ex.getMessage());
            }
        }
    }
}
