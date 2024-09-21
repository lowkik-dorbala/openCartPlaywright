package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	
	private Page page;
	
	
	//String Locators - Login Page
	private String login_username = "//input[@name='email']";
	private String login_pwd = "//input[@name='password']";
	private String login_button = "//input[@value='Login']";
	private String forgot_pwd = "//div[@class='form-group']//a[text()='Forgotten Password']";
	private String myAff_text = "//h2[text()='My Affiliate Account']"; //accounts page locator
	

	// 2. Create constructor of Login Page
	public LoginPage(Page page) {
	this.page = page;	
	}
	
	// 3. Page actions/methods
	public String getLoginPageTitle() {
		return page.title();	
	}
	
	public boolean verifyForgotPwdVisible() {
		return page.isVisible(forgot_pwd);
		
	}
	
	public boolean doLogin(String username, String password) 
	{
		System.out.println("Logging in using the user name: "+username);
		page.fill(login_username, username);
		page.fill(login_pwd, password);
		page.click(login_button);
		if(page.isVisible(myAff_text)) {
			System.out.println("Login is successful");
			return true;
		}
		return false;
	}
	
	
	
}

