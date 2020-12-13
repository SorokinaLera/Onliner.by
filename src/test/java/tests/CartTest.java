package tests;

import io.qameta.allure.Description;
import models.Product;
import org.testng.annotations.Test;
import utils.Retry;

public class CartTest extends BaseTest {

    @Test
    @Description("Single product add to Cart")
    public void productShouldBeAddedIntoCart() {
        Product product = new Product("Vivo V20 (красочный закат)");
        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .validateThatProductIsInTheCard(product.getProductName());
    }

    @Test
    @Description("Single product should be removed from Cart")
    public void removeProductFromCart() {
        Product product = new Product("Vivo V20 (красочный закат)");

        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .validateThatProductIsInTheCard(product.getProductName());
        cartPage
                .removeProductFromCart(product.getProductName())
                .validateThatProductWasRemoved(product.getProductName());
    }

    @Test
    @Description("Increase the quantity of the item from the Cart using '+' and verify")
    public void increaseTheQuantityOfTheItemFromTheCartUsingPlus() throws InterruptedException{
        Product product = new Product("Vivo V20 (красочный закат)");
        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .validateThatProductIsInTheCard(product.getProductName());
        cartPage
                .increaseTheQuantityOfTheItemAndValidate(3, product.getProductName());
    }

    @Test
    @Description("Increase the quantity of the item from the Cart using number field and verify")
    public void increaseTheQuantityOfTheItemFromTheCartUsingNumberField() throws InterruptedException{
        Product product = new Product("Vivo V20 (красочный закат)");
        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .validateThatProductIsInTheCard(product.getProductName());
        cartPage.validateIncreasingQuantityUsingNumberFieldAndValidate(5, product.getProductName());
    }

    @Test
    @Description("Multiple products add to Cart")
    public void multipleProductsShouldBeAddedIntoCart() {
        Product product = new Product("Vivo V20 (красочный закат)");
        Product product1 = new Product("Apple iPhone 11 64GB (черный)");
        Product product2 = new Product("Samsung Galaxy M31 SM-M315F/DSN 6GB/128GB (черный)");

        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName());
        productsPage
                .addProductToCard(product1.getProductName());
        productsPage
                .addProductToCard(product2.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .validateThatProductIsInTheCard(product.getProductName());
        cartPage
                .validateThatProductIsInTheCard(product1.getProductName());
        cartPage
                .validateThatProductIsInTheCard(product2.getProductName());
    }

    @Test (retryAnalyzer = Retry.class)
    @Description("Multiple products remove from cart")
    public void multipleProductsShouldBeRemovedFromCart() {
        Product product = new Product("Vivo V20 (красочный закат)");
        Product product1 = new Product("Apple iPhone 11 64GB (черный)");
        Product product2 = new Product("Xiaomi Redmi Note 9 Pro 6GB/128GB международная версия (серый)");

        catalogPage
                .openPage();
        productsPage
                .addProductToCard(product.getProductName());
        productsPage
                .addProductToCard(product1.getProductName());
        productsPage
                .addProductToCard(product2.getProductName())
                .goToTheCard();
        cartPage
                .isPageOpened()
                .removeProductFromCart(product.getProductName())
                .validateThatProductWasRemoved(product.getProductName());
        cartPage.removeProductFromCart(product2.getProductName())
                .validateThatProductWasRemoved(product2.getProductName());
    }
}
