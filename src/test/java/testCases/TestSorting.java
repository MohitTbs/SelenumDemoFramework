package testCases;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Util;
import base.driver.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;

public class TestSorting extends BaseTest{

	
	WebDriverWait wait;

	@Test(description = "Sorting the price in ascending order")
	public void Test_PriceSortingAscending() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();
		Util.logger.info("Hello there");
		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		productPage.sort_price("low to high");
		String[] sortedPriceArray = productPage.getAllPricesFromCurrentPage();
		double[] integerArray = Util.changeStringArraytoDouble(sortedPriceArray);
		Assert.assertTrue(Util.checkAscending(integerArray));		
	}
	
	@Test(description = "Sorting the price in descending order")
	public void Test_PriceSortingDescending() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();

		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		productPage.sort_price("high to low");
		String[] sortedPriceArray = productPage.getAllPricesFromCurrentPage();
		double[] integerArray = Util.changeStringArraytoDouble(sortedPriceArray);
		Assert.assertTrue(Util.checkDescending(integerArray));		
	}
	
	@Test(description = "Sorting the product in A-Z order")
	public void Test_ProductSortingAZ() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();

		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		productPage.sort_product("az");
		String[] sortedNameArray = productPage.getAllProductNameFromCurrentPage();
		Assert.assertTrue(Util.checkAlphabeticAZ(sortedNameArray));		
	}
	
	@Test(description = "Sorting the product in Z-A order")
	public void Test_ProductSortingZA() throws Exception {
		ProductsPage productPage = new ProductsPage();
		LoginPage loginPage = new LoginPage();

		loginPage.login(); // valid login
		Thread.sleep(2000);

		productPage.waitForProduct();
		Thread.sleep(2000);
		productPage.sort_product("za");
		String[] sortedNameArray = productPage.getAllProductNameFromCurrentPage();
		Assert.assertTrue(Util.checkAlphabeticZA(sortedNameArray));		
	}
}
