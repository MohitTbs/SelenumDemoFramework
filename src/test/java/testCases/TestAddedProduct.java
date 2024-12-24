package testCases;

import java.util.Arrays;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.driver.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;

public class TestAddedProduct extends BaseTest {

	WebDriverWait wait;

	@Test(description = "Verifying the selected item and its price on cart page")
	public void Test_ProductGenuity() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();

		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		String[] name_price_of_product_on_product_page = productPage.select_a_random_product_and_return_name_price();
		productPage.clickOnShoppingCart();
		String[] name_price_of_product_on_cart_page = productPage.check_selected_item_name_price_on_cart();
		Assert.assertTrue(Arrays.equals(name_price_of_product_on_product_page, name_price_of_product_on_cart_page));
		
	}
	
	@Test(description = "Verifying the selected item get removed")
	public void Test_RemoveProduct() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();

		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		String[] name_price_of_product_on_product_page = productPage.select_a_random_product_and_return_name_price();
		productPage.clickOnShoppingCart();
		productPage.clickOnSelectedItem();
		productPage.click_remove_button_selected_product_page();
		productPage.click_on_back_to_product_button();
		String msg = productPage.checkAddToCartText(name_price_of_product_on_product_page[0]);
		Assert.assertFalse(msg.contains("no match found"));
		

	}
}
