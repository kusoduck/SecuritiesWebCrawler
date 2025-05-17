package com.kusoduck.stock.app;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MopsCrawlerAdvanced {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://mops.twse.com.tw/mops/#/web/t163sb04");

            // 加入明確等待（等到下拉選單出現）
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("TYPEK")));

            // ===== 選擇「上市」 =====
            WebElement typeDropdown = driver.findElement(By.name("TYPEK"));
            Select typeSelect = new Select(typeDropdown);
            typeSelect.selectByVisibleText("上市");

            // ===== 填寫年度114 =====
            WebElement yearInput = driver.findElement(By.name("year"));
            yearInput.clear();
            yearInput.sendKeys("114");

            // ===== 選擇「第1季」 =====
            WebElement seasonDropdown = driver.findElement(By.name("season"));
            Select seasonSelect = new Select(seasonDropdown);
            seasonSelect.selectByVisibleText("第1季");

            // ===== 點擊查詢 =====
            WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'查詢')]"));
            searchButton.click();

            // 查詢後再等一下表格出現
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.hasBorder tbody tr")));

            // ===== 讀取表格資料 =====
            List<WebElement> rows = driver.findElements(By.cssSelector("table.hasBorder tbody tr"));
            System.out.println("抓到 " + rows.size() + " 筆資料！");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    System.out.print(cell.getText() + " | ");
                }
                System.out.println();
            }

        } finally {
            driver.quit();
        }
    }
}

