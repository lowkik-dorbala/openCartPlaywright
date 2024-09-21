package com.qa.opencart.base;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;

public class BaseTest {

	PlaywrightFactory pf;
	Page page; 
	protected Properties prop;
	
	protected HomePage hp;
	protected LoginPage lp;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {	
		pf = new PlaywrightFactory();
		prop = pf.init_props(); // store all the props into prop object
		// we are sending entire props as input into the initBrowser Method
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName); //updates the browser in config.props file
		}
		
		page = pf.initBrowser(prop); //since initBrowser returns page
		hp = new HomePage(page);
	}
	
	
	@AfterTest
	public void tearDown() {
		page.context().browser().close(); //to close the browser	
	}
	
	
	
	
}
