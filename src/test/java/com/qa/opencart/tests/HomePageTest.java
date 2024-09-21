package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
public class HomePageTest extends BaseTest {
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook"}, {"iMac"}, {"Samsung"}
		};		
	}

	@Test(description="HomePageTitle", priority=1)
	public void HomePageTitle() {
		String actualTitle = hp.getHomePageTitle();
		Assert.assertEquals(actualTitle,  AppConstants.LOGIN_STORE_NAME);		
	}
	
	@Test(description="HomePageURL", priority=2)
	public void HomePageURL() {
		String actualUrl = hp.HomePageURL();
		Assert.assertEquals(actualUrl, prop.getProperty("url"));
	}
	
	@Test(dataProvider="getProductData",description="DoSearchProduct", priority=3)
	public void DoSearchProduct(String productName) {
		System.out.println("Searching for: "+productName);
		String actualHeader = hp.DoSearch(productName);
		Assert.assertEquals(actualHeader, "Search - "+productName);
	}
}
