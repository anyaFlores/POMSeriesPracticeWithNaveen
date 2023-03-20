package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{
	
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	
	
//	@DataProvider
//	public Object[][] getProductImagesTestData() {
//		return new Object[][] {
//			{"Macbook", "MacBook Pro", 4},
//			{"iMac", "iMac", 3},
//			{"Apple", "Apple Cinema 30\"", 6},
//			{"Samsung", "Samsung SyncMaster 941BW", 1},
//		};
//	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"iMac", "iMac", 3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
		};
	}
	
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> accProductInfoMap = productInfoPage.getProductInfo();
		
		softAssert.assertEquals(accProductInfoMap.get("Brand"), "Apple");// with softAssert -need create an obj in Base class
		//Assert- can access without obj
		softAssert.assertEquals(accProductInfoMap.get("Product Code"), "Product 18");//representing the same feature
		softAssert.assertEquals(accProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(accProductInfoMap.get("productprice"), "$2,000.00");
		softAssert.assertAll();// need to write at the end, to know what has failed!!!!
		//Assert (hard assert)vs verify(softAsset) - it is pasrt pof Unit testing framework , not selenium webdriver
	}
	
	@DataProvider
	public Object[][] getaddToCartTestData() {
		return new Object[][] {
			{"MacBook Pro", 3},
			{"iMac", 4},
//			{"Apple Cinema 30\"", 7},
			{"Samsung SyncMaster 941BW", 2},
		};
	}
	
	@Test(dataProvider = "getaddToCartTestData")
	
	public void addToCartTest(String productName, int quantity ) {
		searchPage = accPage.performSearch(productName);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(quantity);
		StringBuilder actCartMesg = productInfoPage.addProductToCart();
		//Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
		softAssert.assertTrue(actCartMesg.indexOf(productName)>=0);
		softAssert.assertEquals(actCartMesg, "Success: You have added " + productName + " to your shopping cart!");
		
//		searchPage = accPage.performSearch("MacBook Pro");
//		productInfoPage = searchPage.selectProduct("MacBook Pro");
//		productInfoPage.enterQuantity(2);
//		StringBuilder actCartMesg = productInfoPage.addProductToCart();
//		//Success: You have added MacBook Pro to your shopping cart!
//		softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
//		softAssert.assertTrue(actCartMesg.indexOf("MacBook Pro")>=0);
//		softAssert.assertEquals(actCartMesg, "Success: You have added MacBook Pro to your shopping cart!");
		
	}
	
	
	

}