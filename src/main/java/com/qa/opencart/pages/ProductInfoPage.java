package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productInfoMap;

	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMessg = By.cssSelector("div.alert.alert-success");
	private By cart = By.id("cart");

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("product header: " + productHeaderVal);
		return productHeaderVal;
		// We do:
		// Login(-->accPage reference)
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count: " + imagesCount);
		return imagesCount;
	}

	public void enterQuantity(int qty) {
		System.out.println("Product Quantity: " + qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	public StringBuilder addProductToCart() {
		eleUtil.doClick(addToCartBtn);//not landing on the next page-"Success msg"
		String successMessg = eleUtil.waitForElementVisible(cartSuccessMessg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText(); 
		
		StringBuilder sb = new StringBuilder(successMessg);
		String msg = sb.substring(0, sb.length()-1).replace("\n", "").toString();//manipulation on StringBuilder
		System.out.println("Cart Success Message:"+ msg);
		return sb;
	}
	
	public Map<String, String> getProductInfo() {
//		Brand: Apple
//		Product Code: Product 16
//		Reward Points: 600
//		Availability: In Stock

//		productInfoMap = new HashMap<String, String>();// NOT order BASED
//		productInfoMap = new LinkedHashMap<String, String>();// maintain the Order
		productInfoMap = new TreeMap<String, String>(); //alphabetical order: first Capital letters-->lowwer case
		// header:
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println(productInfoMap);
		return productInfoMap;

	}

// fetching the meta data
	private void getProductMetaData() {// change to private - encapsulation - we just calling the result in getProductInfo().
		//like launch the browser method- we dont need info about the Ram- we just need to launch the browser
		// meta data:
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String meta = e.getText();// Brand: Apple
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
	private void getProductPriceData() {
		// price data:
//		$602.00
//		Ex Tax: $500.00
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();// order based
		String exTax = priceList.get(1).getText();
		String extaxVal = exTax.split(":")[1].trim();

		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", price);
	}

	
	

}
