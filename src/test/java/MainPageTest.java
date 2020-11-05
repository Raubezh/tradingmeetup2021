import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @Rule
    public TestName testName = new TestName();
    private static String watchedLog;

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            watchedLog += description + " failed!\n";
        }

        @Override
        protected void succeeded(Description description) {
            watchedLog += description + " passed!\n";
        }
    };

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = (new WebDriverWait(driver, 4));
        driver.manage().window().maximize();
        driver.get("https://tradingmeetup2021.com");
        mainPage = new MainPage(driver);
    }

    @Before
    public void printTestMethod() {
        System.out.println("Start '" + testName.getMethodName() + "'");
    }


    @Test
    public void test1_CheckingPageFunctionality() {
        MainPage MainPage = mainPage
                .selectRadioButton("I have no btc")
                .selectRadioButton("I have btc")
                .typeEmail("a")
                .clickSubscribeButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        String error = MainPage.getErrorText();
        Assert.assertEquals("Please enter a valid email address", error);
    }

    @Test
    public void test2_CheckingInteractionOfRadiobuttonsWithEmptyInputField() {
        MainPage MainPage = mainPage
                .selectRadioButton("I have no btc")
                .fillingSubscribeForm("");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        String error = MainPage.getErrorText();
        Assert.assertEquals("Please enter a valid email address", error);

        mainPage.selectRadioButton("I have btc")
                .clickSubscribeButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        Assert.assertEquals("Please enter a valid email address", error);
    }

    @Test
    public void test3_CheckingInteractionOfRadiobuttonsWithCorrectEmailInInputField() {
        MainPage MainPage = mainPage
                .typeEmail("mowiyik926@gridmauk.com")
                .selectRadioButton("I have no btc")
                .clickSubscribeButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response success']")));

        String success = MainPage.getSuccessText();
        Assert.assertEquals("Thank you for registration. We’ll send you link soon.", success);

        driver.navigate().refresh();

        mainPage.selectRadioButton("I have btc")
                .fillingSubscribeForm("mowiyik926@gridmauk.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response success']")));
        Assert.assertEquals("Thank you for registration. We’ll send you link soon.", success);
    }

    @Test
    public void test4_CheckingInteractionOfRadiobuttonsWithIncorrectDataInInputField() {
        MainPage MainPage = mainPage
                .selectRadioButton("I have no btc")
                .typeEmail("a")
                .clickSubscribeButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        String error = MainPage.getErrorText();
        Assert.assertEquals("Please enter a valid email address", error);

        mainPage.selectRadioButton("I have btc")
                .clearInput()
                .typeEmail("a")
                .clickSubscribeButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));
        Assert.assertEquals("Please enter a valid email address", error);

        mainPage.selectRadioButton("I have no btc")
                .clearInput()
                .typeEmail("ф")
                .clickSubscribeButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        Assert.assertEquals("Please enter a valid email address", error);

        mainPage.selectRadioButton("I have btc")
                .clearInput()
                .typeEmail("ф")
                .clickSubscribeButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));
        Assert.assertEquals("Please enter a valid email address", error);

    }

    @Test
    public void test5_CheckingInteractionOfRadiobuttonsWithIncorrectEmailWithCyrillicKInInputField() {
        MainPage MainPage = mainPage
                .selectRadioButton("I have no btc")
                .typeEmail("почта@gmail.com")
                .clickSubscribeButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));

        String error = MainPage.getErrorText();
        Assert.assertEquals("Please enter a valid email address", error);

        driver.navigate().refresh();

        mainPage.fillingSubscribeForm("почта@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sg-response error']")));
        Assert.assertEquals("Please enter a valid email address", error);
    }


    @After
    public void tearDown() {
        System.out.println("'" + testName.getMethodName() + "' finished ");
        driver.quit();
    }

    @AfterClass
    public static void Logger() {
        System.out.println(watchedLog);
    }
}
