package ru.dmitrii.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleTest {

    @Test
    public void test() {
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.google.ru/");

        WebElement search = webDriver.findElement(By.xpath("//input[@name='q']"));
        search.sendKeys("сбербанк");
        search.sendKeys(Keys.ENTER);

        WebElement target = webDriver.findElement(By.xpath("//h3[text()='Частным клиентам — СберБанк']"));
        Assertions.assertTrue(target.isDisplayed());

        webDriver.quit();
    }
}
