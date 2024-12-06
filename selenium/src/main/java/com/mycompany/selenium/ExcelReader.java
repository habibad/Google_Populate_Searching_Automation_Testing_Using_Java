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
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class ExcelReader {
    public static List<String> readExcelSheet(String filePath) {
        List<String> keywords = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);

            // Get the current day of the week
            DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
            Sheet sheet = workbook.getSheet(dayOfWeek.toString());

            if (sheet != null) {
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(2) != null) {
                        keywords.add(row.getCell(2).getStringCellValue());
                    }
                }
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keywords;
    }

}
