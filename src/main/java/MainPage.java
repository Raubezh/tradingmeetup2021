import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By headingText1 = By.xpath("//h1");
    private By headingText2 = By.xpath("//h2");
    private By signUpText = By.xpath("//p[contains(@class,'subscribe')]");
    private By radioButton = By.xpath("//label[contains(text(),'%s')]");
    private By errorText = By.xpath("//input[@id='email-user']/ancestor::p/preceding-sibling::div[@class='sg-response error']");
    private By successText = By.xpath("//input[@id='email-user']/ancestor::p/preceding-sibling::div[@class='sg-response success']");
    private By emailField = By.xpath("//input[@id='email-user']");
    private By subscribeButton = By.xpath("//input[@value='Subscribe']");

    public MainPage typeEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public MainPage clickSubscribeButton() {
        driver.findElement(subscribeButton).click();
        return this;
    }


    public String getHeadingText1() {
        return driver.findElement(headingText1).getText();
    }

    public String getHeadingText2() {
        return driver.findElement(headingText2).getText();
    }

    public String getSignUpText() {
        return driver.findElement(signUpText).getText();
    }

    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }

    public String getSuccessText() {
        return driver.findElement(successText).getText();
    }

    public MainPage fillingSubscribeForm(String email) {
        this.typeEmail(email);
        this.clickSubscribeButton();
        return new MainPage(driver);
    }

    public MainPage clearInput() {
        driver.findElement(emailField).clear();
        return this;
    }

    public MainPage selectRadioButton(String option) {
        String optionXpath = String.format("//label[contains(text(),'%s')]", option);
        driver.findElement(By.xpath(optionXpath)).click();
        return this;
    }
}