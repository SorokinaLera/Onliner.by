package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CatalogPage extends BasePage{
    public static final By HEADER_TITLE_BUTTON = By.xpath("//*[@class='schema-header']//*[contains(text(), 'Мобильные телефоны')]");

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open mobile Catalog Page")
    public CatalogPage openPage(){
        driver.get("https://catalog.onliner.by/mobile");
        isPageOpened();
        return this;
    }

    @Step("Validation that the Catalog Page is opened")
    public CatalogPage isPageOpened() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(HEADER_TITLE_BUTTON));
        return this;
    }
}
