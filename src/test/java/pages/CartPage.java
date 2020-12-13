package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class CartPage extends BasePage {
    public static final By CART_TITLE_LABEL = By.xpath("//*[@class='cart-form']//*[contains(text(),'Корзина')]");
    public static final By PRODUCT = By.xpath("//*[@class ='cart-form__offers-part cart-form__offers-part_data']//*[@class='cart-form__link cart-form__link_primary cart-form__link_base-alter']");
    public static String TRASH_CAN_BUTTON = "//*[contains(text(),'%s')]//ancestor::*[@class='cart-form__offers-flex']//*[@class='cart-form__control']//*[@class='button-style button-style_auxiliary button-style_small cart-form__button cart-form__button_remove']";
    public static String PlUS_BUTTON = "//*[contains(text(),'%s')]//ancestor::*[@class='cart-form__offers-flex']//*[@class='button-style button-style_auxiliary button-style_small cart-form__button cart-form__button_increment helpers_hide_tablet']";
    public static String PRICE_LOCATOR = "//*[contains(text(),'%s')]//ancestor::*[@class='cart-form__offers-flex']//*[@class='cart-form__offers-part cart-form__offers-part_price helpers_hide_tablet']//span";
    public static String FIELD_OF_NUMBERS = "//*[contains(text(),'%s')]//ancestor::*[@class='cart-form__offers-flex']//*[@type='text']";
    public static String ONE_ITEM_PRICE_LOCATOR = "//*[contains(text(),'%s')]//ancestor::*[@class='cart-form__offers-flex']//*[@class='cart-form__offers-part cart-form__offers-part_price helpers_hide_tablet']//*[contains(text(),'%s')]";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Validation that page was opened")
    public CartPage isPageOpened() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CART_TITLE_LABEL));
        return this;
    }

    @Step("Validation that an item was added to the cart successfully")
    public boolean validateThatProductIsInTheCard(String productName) {
        boolean condition = false;
        for (int i = 0; i <= driver.findElements(PRODUCT).size(); i++) {
            String item = driver.findElement(PRODUCT).getText();
            if (productName.equals(item)) {
                condition = true;
                log.info(productName + " was added in the card successfully");
                break;
            }
        }
        return condition;
    }

    @Step("Validation that an item was removed from the cart successfully")
    public CartPage removeProductFromCart(String productName) {
        WebElement element = driver.findElement(By.xpath(String.format(TRASH_CAN_BUTTON, productName)));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        return this;
    }

    @Step("Validation that an item was removed from the cart")
    public CartPage validateThatProductWasRemoved(String productName) {
        List<WebElement> items = driver.findElements(PRODUCT);
        int count = 0;
        for (WebElement element : items) {
            String item = element.getText();
            if (item.equals(productName)) {
                log.error(String.format("Product '%s' wasn't removed", productName));
                count++;
            }
        }
        Assert.assertEquals(count, 0);
        log.info(String.format("Product '%s' was removed successfully", productName));
        return this;
    }


    @Step("Validate quantity increase function using a plus button")
    public CartPage increaseTheQuantityOfTheItemAndValidate(int amount, String productName) throws InterruptedException {
        String priceFromCart = driver.findElement(By.xpath(String.format(PRICE_LOCATOR, productName))).getText();
        int oneItemPrice = Integer.parseInt(priceFromCart.replace(",00 р.", ""));
        int i = 0;
        while (i < amount - 1) {
            WebElement element = driver.findElement(By.xpath(String.format(PlUS_BUTTON, productName)));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            i++;
        }
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String.format(ONE_ITEM_PRICE_LOCATOR, productName, oneItemPrice))));
        TimeUnit.SECONDS.sleep(2);
        String newIncreasedPrice = driver.findElement(By.xpath(String.format(PRICE_LOCATOR, productName))).getText();
        int increasedPrice = Integer.parseInt(newIncreasedPrice.replace(",00 р.", ""));

        Assert.assertEquals(increasedPrice, amount * oneItemPrice);
        return new CartPage(driver);
    }

    @Step("Validate quantity increase function using a numeric field")
    public CartPage validateIncreasingQuantityUsingNumberFieldAndValidate(int amount, String productName) throws InterruptedException {
        String priceFromCart = driver.findElement(By.xpath(String.format(PRICE_LOCATOR, productName))).getText();
        int oneItemPrice = Integer.parseInt(priceFromCart.replace(",00 р.", ""));
        WebElement toClear = driver.findElement(By.xpath(String.format(FIELD_OF_NUMBERS, productName)));
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        driver.findElement(By.xpath(String.format(FIELD_OF_NUMBERS, productName))).sendKeys(String.valueOf(amount));
        TimeUnit.SECONDS.sleep(2);
        String newIncreasedPrice = driver.findElement(By.xpath(String.format(PRICE_LOCATOR, productName))).getText();
        int increasedPrice = Integer.parseInt(newIncreasedPrice.replace(",00 р.", "").replace(" ", ""));

        Assert.assertEquals(increasedPrice, amount * oneItemPrice);
        return new CartPage(driver);
    }
}
