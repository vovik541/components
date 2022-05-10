package com.library.demo.e2e;

import com.library.demo.config.Constants;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static com.library.demo.config.Constants.WEBSITE_URL;
import static com.library.demo.config.WebDriverConfig.getWebDriver;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


public class LibrarianTest {

    private static WebDriver driver;
    private static Wait waitTenSecond;

    private String bookName;

    @Before
    public void setUp() throws Exception {
        driver = getWebDriver();
        driver.get(WEBSITE_URL);
        waitTenSecond = new WebDriverWait(driver, 10);
        bookName = "test_book_" + (new Random().nextLong());

        driver.get("https://librarydemoipz11.herokuapp.com/");
    }

    @Test
    public void logInAndAddBookTest() {
        waitTenSecond.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/nav/div/a")));
        driver.findElement(By.xpath("//*[@id=\"navmenu\"]/ul/li[1]/a")).click();

        waitTenSecond.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/form/h2")));
        driver.findElement(By.xpath("//*[@id=\"username\"]"))
                .sendKeys("librarian");
        driver.findElement(By.xpath("//*[@id=\"password\"]"))
                .sendKeys("123456");
        driver.findElement(By.xpath("/html/body/div/form/button")).click();

        waitTenSecond.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/section[1]/div/div/div/h1")));
        driver.findElement(By.xpath("/html/body/section[2]/div/div/div[1]/div/div/a")).click();

        waitTenSecond.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"inputName\"]")));
        driver.findElement(By.xpath("//*[@id=\"inputName\"]"))
                .sendKeys(bookName);
        driver.findElement(By.xpath("//*[@id=\"inputAuthor\"]"))
                .sendKeys(bookName);
        driver.findElement(By.xpath("//*[@id=\"inputDescription\"]"))
                .sendKeys(bookName);
        driver.findElement(By.xpath("/html/body/section[2]/form/div[4]/button")).click();

        waitTenSecond.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/section[2]/div/div/div[2]/div/div/a")));
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), \"" + bookName + "\")]"));

        assertEquals("The name was found", bookName, element.getText());
    }

    @After
    public void afterClass() throws Exception {
        driver.close();
    }
}
