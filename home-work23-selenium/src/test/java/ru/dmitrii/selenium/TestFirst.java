package ru.dmitrii.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestFirst extends BaseTest{


    @Test
    public void test() {
        webDriver.get("https://www.google.ru/");

        WebElement search = webDriver.findElement(By.xpath("//input[@name='q']"));
        search.sendKeys("сбербанк");
        search.sendKeys(Keys.ENTER);

        WebElement target = webDriver.findElement(By.xpath("//h3[text()='Частным клиентам — СберБанк']"));
        Assert.assertTrue(target.isDisplayed());

    }

    @Test
    public  void test2 () throws InterruptedException {
        webDriver.manage().window().maximize();
        webDriver.get("https://www.sberbank.ru/");
        Thread.sleep(500);
    }

    @Test
    public  void test3 () throws InterruptedException {
        webDriver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        webDriver.findElement(By.xpath("//div[@id='start']/button[text()='Start']")).click();
//       WebElement targetText = webDriver.findElement(By.xpath("//h4[text()='Hello World!']"));

        new WebDriverWait(webDriver,10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Hello World!']")));


//        Assert.assertTrue(targetText.isDisplayed());
    }

    @Test
    public  void test4 () throws InterruptedException {
        mainPage.checkOfTheMainPageLocation();
    }

    @Test
    public  void test5 () throws InterruptedException {
        mainPage.checkOfTheMainPageLocation();
    }

}
