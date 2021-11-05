package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class MainPage {
    public WebDriver driver;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//img[@alt='Официальный сайт Сбербанка России']")
    private WebElement elMainIcon;

    @FindBy(xpath = "//a[@aria-label='Кредиты']")
    private WebElement elCreditsButton;

    @FindBy(xpath = "//a[text()='Все кредиты']")
    private WebElement elAllCreditsButton;

    public void checkOfTheMainPageLocation() {
        Assert.assertTrue(elMainIcon.isDisplayed());
    }

    public void clickCreditsButton() {
        elCreditsButton.click();
    }

    public void clickAllCreditsButton() {
        elAllCreditsButton.click();
    }

}
