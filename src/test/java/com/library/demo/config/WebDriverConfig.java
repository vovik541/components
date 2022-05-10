package com.library.demo.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverConfig {
    public static WebDriver getWebDriver(){

        System.setProperty("webdriver.chrome.driver","src/test/java/com/library/demo/driver/chromedriver.exe");

        return new ChromeDriver();
    }
}
