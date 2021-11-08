package ru.dmitrii.selenium;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {
    @Test
    public  void selenide () {
        Configuration.browser = "chrome";
        open("https://www.google.com/");
        $(By.name("q")).setValue("sberbank").pressEnter();
        $(By.xpath("//h3[text()='Частным клиентам — СберБанк']")).shouldBe(Condition.visible);
    }

}
