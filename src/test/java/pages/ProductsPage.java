package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.charset.StandardCharsets;

public class ProductsPage extends BasePage {
    public static String cartURL = "https://cart.onliner.by/";
    public static String catalogURL = "https://catalog.onliner.by/";
    public static String productLocator = "//*[@class ='schema-product__title']//*[contains (text(),'%s')]";
    public static final By BUY_BUTTON = By.xpath("//*[@id='shop-offers-container']/div[1]/div/div[1]/div/div[1]/a[2]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Add \"{productName}\" to the Card")
    public ProductsPage addProductToCard(String productName) {
        byte[] bytes = productName.getBytes(StandardCharsets.UTF_8);
        String utf8ProductName = new String(bytes, StandardCharsets.UTF_8);

        driver.get(catalogURL + "mobile");
        driver.findElement(By.xpath(String.format(productLocator, utf8ProductName))).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(BUY_BUTTON));
        driver.findElement(BUY_BUTTON).click();
        return new ProductsPage(driver);
    }

    @Step("Go to the Card")
    public CartPage goToTheCard() {
        driver.get(cartURL);
        return new CartPage(driver);
    }
}

