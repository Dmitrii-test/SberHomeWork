package ru.dmitrii.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.AllCreditsPage;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver webDriver;
    public MainPage mainPage;
    public AllCreditsPage allCreditsPage;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        mainPage = new MainPage(webDriver);
        allCreditsPage = new AllCreditsPage(webDriver);

    }


    @AfterTest
    public void tearDown() {
        webDriver.quit();
    }
}
