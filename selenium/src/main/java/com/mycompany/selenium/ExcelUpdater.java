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
import java.time.LocalDate;
public class ExcelUpdater {
    public static void updateExcel(String filePath, String keyword, String longestOption, String shortestOption) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Get the current day's sheet
            String currentDay = LocalDate.now().getDayOfWeek().toString();
            Sheet sheet = workbook.getSheet(currentDay);

            if (sheet != null) {
                for (Row row : sheet) {
                    Cell keywordCell = row.getCell(2); // Keywords in column B (index 1)

                    if (keywordCell != null && keywordCell.getStringCellValue().equalsIgnoreCase(keyword)) {
                        Cell longestCell = row.createCell(3); // Column C (index 2) for longest
                        longestCell.setCellValue(longestOption);

                        Cell shortestCell = row.createCell(4); // Column D (index 3) for shortest
                        shortestCell.setCellValue(shortestOption);

                        break;
                    }
                }
            }

            fileInputStream.close();

            // Write back to the Excel file
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();

            System.out.println("Excel updated successfully for keyword: " + keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
