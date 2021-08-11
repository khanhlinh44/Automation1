import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutFlow {

    ChromeDriver driver;
    HashMap<String, List<String>> expectedProductList = new HashMap<>();
    HashMap<String, List<String>> actualProductList = new HashMap<>();

    @Before
    public void Before() {
        String shopDomain = "https://au-webhook-adc1.onshopbase.com/";
        String os = System.getProperty("os.name");

        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chrome/chromedriver.exe"); // tự download webdriver vào thay đường dẫn đúng

        driver = new ChromeDriver();
        driver.get(shopDomain);
        driver.manage().window().maximize();

        waitElementVisible("");
    }

    @Test
    public void searchThenSelectProduct(String prodName) {
        // xpath product
        String iconSearch = "//input[@placeholder='Search']|//*[@data-testid='openSearchPanel']";
        String textboxSearch = "//div[@class='search-input-group']//input[@placeholder='Search' or @id ='search']";
        String selectedProd = "(//span[normalize-space(.)='" + prodName + "']//ancestor::a//img)[1])";


        // search
        clickElement(iconSearch);
        inputToElement(textboxSearch, prodName);
        clickElement(selectedProd);
        expectedProductList.put("Shirt", asList("3", "10.00"));
    }

    @Test
    public void addProductToCart() {
        clickElement("//button[child::span[normalize-space()='Add to cart']]");
    }

    @Test
    public void getActualProductInfo() {
        String actualProductName = getElementText("");
        String actualQuantity = getElementText("");
        String actualPrice = getElementText("");

        actualProductList.put(actualProductName, asList(actualQuantity, actualPrice));
    }

    @Test
    public void Test() throws InterruptedException {

        // customer information
        String email = "linhnguyen@beeeting.com";
        String firstName = "Linh";
        String lastName = "Nguyen";
        String city = "HN";
        String expectedDiscountCode = "TESTING";
        String actualDiscountCode = "";
        String prodName = "Shirt";

        // card info
        String cardNumber = "4242 4242 4242 4242";
        String expireAt = "08/22";
        String cvv = "111";

        String iframeCard = "//div[@id='stripe-card-number']//iframe";
        String iframeExpireAt = "";
        String iframeCVV = "";
        String xpathDiscountCodeSF = "(//span[@class='reduction-code__text'])[1]";

        // search
        searchThenSelectProduct(prodName);

        // set quantity
        clickElement("");

        // add product to cart
        addProductToCart();

        // verify cart: verify expectedProductList với actualProductList

        // Checkout
        clickOnButtonName("Checkout");

        // Enter customer information
        enterTextToTextbox("Email", email);
        enterTextToTextbox("First name", firstName);
        enterTextToTextbox("Last name", lastName);
        enterTextToTextbox("City", city);
        clickOnCheckbox("Keep me up to date on news and exclusive offers");

        //Shipping method
        clickOnButtonName("Continue to shipping method");

        //Enter Discount then verify
        enterTextToTextbox("Enter your discount code here'", expectedDiscountCode);
        clickOnButtonName("Apply");
        actualDiscountCode = getElementText(xpathDiscountCodeSF);
        assertThat(actualDiscountCode).isEqualToIgnoringCase(expectedDiscountCode);

        //Payment method
        clickOnButtonName("Continue to payment method'");
        enterTextToTextbox("Cardholder name", firstName + lastName);

        enterFieldInCardForm(iframeCard, "Card number", cardNumber);
        enterFieldInCardForm(iframeExpireAt, "MM/YY'", cardNumber);
        enterFieldInCardForm(iframeCVV, "MM/YY'", cardNumber);
        driver.switchTo().parentFrame();

        clickOnButtonName("Complete order");
    }

    public void enterFieldInCardForm(String iframeCard, String fieldName, String value) {
        driver.switchTo().parentFrame();
        driver.switchTo().frame(iframeCard);
        enterTextToTextbox(fieldName, value);
    }

    /*@After
    public void After(){
        driver.quit();
        System.out.println("Finish");
    }*/
    public WebElement getElement(String xpath) {
        return driver.findElement(By.xpath(xpath));

    }

    public void clickOnButtonName(String btnName) {
        clickElement("//button[normalize-space()='" + btnName + "']");
    }

    public void clickElement(String xpath) {
        waitElementVisible(xpath);
        getElement(xpath).click();
    }

    public String getElementText(String xpath) {
        waitElementVisible(xpath);
        return getElement(xpath).getText();
    }

    public void inputToElement(String xpath, String value) {
        waitElementVisible(xpath);
        WebElement e = getElement(xpath);
        e.clear();
        e.sendKeys(value);
    }

    public String getText(String xpath) {
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public void waitElementVisible(String xpath) {
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    public void clickOnCheckbox(String checkboxName) {
        clickElement("//span[normalize-space()='" + checkboxName + "']/preceding-sibling::input");
    }

    public void enterTextToTextbox(String name, String value) {
        inputToElement("//input[@placeholder='" + name + "']", value);
    }

    public void switchToIframe(String xpath) {
        driver.switchTo().frame(xpath);
    }

}
