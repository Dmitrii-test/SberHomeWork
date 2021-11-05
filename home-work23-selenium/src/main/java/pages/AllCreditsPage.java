package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AllCreditsPage {
    public WebDriver driver;

    public AllCreditsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//h2[text()='Рефинансирование кредитов']")
    WebElement elReffinanceOfCredits;

    public void checkOfRedirectToCredits() {
        Assert.assertTrue(elReffinanceOfCredits.isDisplayed());
    }

}
