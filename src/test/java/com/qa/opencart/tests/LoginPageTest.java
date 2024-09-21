package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

public class LoginPageTest extends BaseTest {

	
	@Test(priority=1)
	public void loginPageNavigationTest() {
		lp = hp.NavigateToLoginPage(); //the method returns login page
		String login_title = lp.getLoginPageTitle();
		System.out.println(login_title);
		Assert.assertEquals(login_title, AppConstants.HOME_PAGE_TITLE);
	}
	
	@Test(priority=2)
	public void ForgotPwdTestAssert() {
		Assert.assertTrue(lp.verifyForgotPwdVisible());
		
	}
	@Test(priority=3)
	public void appLoginTest() {
		Assert.assertTrue(lp.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim()));
		
	}
	
	
}
