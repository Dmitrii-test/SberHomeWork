package ru.dmitrii.selenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideWikiTest {

    @AfterTest
    public void init() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public  void test1 () {
        open("https://ru.wikipedia.org/");
        SelenideElement selenideElement = $(By.xpath("//div[@id='p-logo']")).shouldBe(Condition.visible);
        Assert.assertTrue(selenideElement.isDisplayed());
    }

    @Test
    public  void test2 () {
        open("https://ru.wikipedia.org/");
        $(By.xpath("//a[@title='арабский']")).click();
        SelenideElement selenideElement = $(By.xpath("//a[@class='mw-wiki-logo']"));
        Assert.assertTrue(selenideElement.isDisplayed());
    }

    @Test
    public  void test3 () {
        open("https://ru.wikipedia.org/");
        $(By.name("search")).setValue("Сбербанк России").pressEnter();
        SelenideElement selenideElement = $(By.xpath("//img[@alt='Изображение логотипа']"));
        Assert.assertTrue(selenideElement.isDisplayed());
    }
}
