package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class HomePage {
	
	private Page page;
	
	// 1. Maintain Object Repository - String Locators
	
	private String search = "//input[@name='search']";
	private String search_button = "//button[@class='btn btn-default btn-lg']";
	private String macbook_search_header = "//div[@id='content']/h1";
	private String myaccnt_link = "//a[@title='My Account']";
	private String login_button = "//a[text()='Login']";
	
	
	// 2. Page constructor
	public HomePage(Page page)  //Page constructor is called
	{
		this.page = page;
	}	
	
	// 3. Methods or actions
	public String getHomePageTitle() {
		String title = page.title(); 
		System.out.println("Page title is: "+title);
		return title;
	}
	
	public String HomePageURL() {
		String url = page.url(); 
		System.out.println("Page title is: "+url);
		return url;	
	}
	
	public String DoSearch(String productName) {
		page.fill(search, productName);
		page.click(search_button);
		String header = page.textContent(macbook_search_header);
		System.out.println("Header name is: "+header);
		return header;
		
	}
	
	public LoginPage NavigateToLoginPage() {
		page.click(myaccnt_link);
		page.click(login_button);
		return new LoginPage(page);
	}

	
	
}
