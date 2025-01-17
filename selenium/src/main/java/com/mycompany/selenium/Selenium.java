/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.selenium;

/**
 *
 * @author anik
 */

import static com.mycompany.selenium.ExcelReader.readExcelSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Selenium {
    public static void main(String[] args) throws Exception {
        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/anik/Downloads/chromedriver-win64 (2)/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.google.com");
//        driver.manage().window().maximize();
//        driver.findElement(By.name("q")).sendKeys("selenium");
//        Thread.sleep(2000);
//        List<WebElement> list = driver.findElements(By.xpath("//ul[@role='listbox']/li"));
//        for(WebElement element : list){
//            System.out.println(element.getText());
//        }
        
        // Read the Excel data
        List<String> keywords = ExcelReader.readExcelSheet("C:/Users/anik/OneDrive/Documents/NetBeansProjects/Excel.xlsx");

        // Iterate through each keyword
        for (String keyword : keywords) {
           driver.get("https://www.google.com");
            driver.manage().window().maximize();
            driver.findElement(By.name("q")).sendKeys(keyword);
            Thread.sleep(4000);

         // Fetch suggestions
            List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@role='listbox']/li"));
            List<String> suggestionTexts = new ArrayList<>();//.uU7dJb to .erkvQe li span
            for (WebElement suggestion : suggestions) {
                suggestionTexts.add(suggestion.getText());
                System.out.println(suggestion);
            }

            if (suggestionTexts.isEmpty()) {
                System.out.println("No suggestions found for keyword: " + keyword);
                continue;
            }

            // Find the longest and shortest options
            String longestOption = Collections.max(suggestionTexts, (a, b) -> a.length() - b.length());
            String shortestOption = Collections.min(suggestionTexts, (a, b) -> a.length() - b.length());

            System.out.println("Keyword: " + keyword);
            System.out.println("Longest Option: " + longestOption);
            System.out.println("Shortest Option: " + shortestOption);

            // Update Excel file
            ExcelUpdater.updateExcel("C:/Users/anik/OneDrive/Documents/NetBeansProjects/Excel.xlsx", keyword, longestOption, shortestOption);
            ExcelWriter.writeToExcel("C:/Users/anik/OneDrive/Documents/NetBeansProjects/Excel.xlsx", keyword, longestOption, shortestOption);
        }

        driver.quit();
    }
}