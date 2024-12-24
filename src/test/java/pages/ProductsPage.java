package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.driver.PageDriver;

public class ProductsPage extends BasePage {
	WebDriver driver;
	WebDriverWait wait;

	/*
	 * public ProductsPage() { driver = PageDriver.getCurrentDriver();
	 * PageFactory.initElements(driver, this); }
	 * 
	 * @FindBy(css = ".title") public WebElement productsTest;
	 */

	By productsText = By.cssSelector(".title");
	By inventoryItems = By.cssSelector(".inventory_item");
	By addToCartButtons = By.cssSelector(".inventory_item button");
	By cartCount = By.cssSelector(".shopping_cart_badge");
	By sort_selector = By.cssSelector(".product_sort_container");
	By first_item_name = By.cssSelector(".inventory_item:nth-child(1) .inventory_item_name");
	By first_item_price = By.cssSelector(".inventory_item:nth-child(1) .inventory_item_price");
	By target_item_name = By.cssSelector(".inventory_item_name");
	By target_item_price = By.cssSelector(".inventory_item_price");
	By shopping_cart_link = By.cssSelector(".shopping_cart_link");
	By selected_item_name = By.cssSelector(".inventory_item_name");
	By selected_item_price = By.cssSelector(".inventory_item_price");
	By remove_button_selected_product_page = By.cssSelector(".inventory_details_desc_container > button");
	By back_to_product_button = By.cssSelector("#back-to-products");
	By sort_dropdown = By.cssSelector(".product_sort_container");

	private int randomNumber;

	public void setRandomNumber(int no) {
		randomNumber = no;
	}

	public int getRandomNumber() {
		return randomNumber;
	}

	public void waitForProduct() {
		waitForEl(productsText);
	}

	public int getItemsSize() {
		return size(inventoryItems);
	}

	public String getCartCount() {
		return getText(cartCount);
	}

	public ProductsPage select_sortOption(String option) {
		selectByOption(sort_selector, option);
		return this;
	}

	public String getFirstItemName() {
		return getText(first_item_name);
	}

	public String getFirstItemPrice() {
		return getText(first_item_price);
	}

	public void add_All_items_ToCart() {
		for (WebElement el : getEls(addToCartButtons)) {
			el.click();
		}
	}

	public String[] select_a_random_product_and_return_name_price() {
		List<WebElement> eles = getEls(inventoryItems);
		Random r = new Random();
		int rNumber = r.nextInt(0, eles.size());
		setRandomNumber(rNumber);
		String item_name = eles.get(rNumber).findElement(target_item_name).getText();
		String item_price = eles.get(rNumber).findElement(target_item_price).getText().trim().substring(1);
		System.out.println("Name of the item: " + item_name + "  " + item_price);
		WebElement addToCartBtn = eles.get(rNumber).findElement(addToCartButtons);
		addToCartBtn.click();
		String[] data = { item_name, item_price };
		return data;
	}

	public void clickOnShoppingCart() {
		click(shopping_cart_link);
	}

	public String[] check_selected_item_name_price_on_cart() {
		String item_name = getText(selected_item_name);
		String item_price = getText(selected_item_price).trim().substring(1);
		System.out.println("Name of the item after selection: " + item_name + "  " + item_price);
		String[] arr = { item_name, item_price };
		return arr;
	}

	public void clickOnSelectedItem() {
		click(selected_item_name);
	}

	public void click_remove_button_selected_product_page() {
		click(remove_button_selected_product_page);
	}

	public void click_on_back_to_product_button() {
		click(back_to_product_button);
	}

	public String checkAddToCartText(String p_name) {
		List<WebElement> eles = getEls(inventoryItems);

		for (WebElement e : eles) {

			String item_name = e.findElement(target_item_name).getText();
			if (item_name.equalsIgnoreCase(p_name)) {
				return e.findElement(addToCartButtons).getText();
			}
		}
		return "no match found";
	}

	public String[] getAllPricesFromCurrentPage() {
		List<WebElement> eles = getEls(target_item_price);
		String[] sortedPriceArray = new String[eles.size()];
		for (int i = 0; i < eles.size(); i++) {
			sortedPriceArray[i] = eles.get(i).getText().trim().substring(1);
		}
		return sortedPriceArray;
	}

	public void sort_price(String low_or_high) {
		Select s = new Select(PageDriver.getCurrentDriver().findElement(sort_dropdown));

		if (low_or_high.equalsIgnoreCase("low to high")) {
			s.selectByValue("lohi");
		} else {
			s.selectByValue("hilo");
		}
	}
	
	public void sort_product(String AZ) {
		Select s = new Select(PageDriver.getCurrentDriver().findElement(sort_dropdown));

		if (AZ.equalsIgnoreCase("az")) {
			s.selectByValue("az");
		} else {
			s.selectByValue("za");
		}
	}
	
	public String[] getAllProductNameFromCurrentPage() {
		List<WebElement> eles = getEls(target_item_name);
		String[] sortedNameArray = new String[eles.size()];
		for (int i = 0; i < eles.size(); i++) {
			sortedNameArray[i] = eles.get(i).getText().trim();
		}
//		System.out.println(Arrays.toString(sortedNameArray));
		return sortedNameArray;
	}
}
