package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
	
	//Has no mail class, and this method helps initialize playwright
	
	Playwright playwright; //Playwright Interface reference
	Browser browser;
	BrowserContext browserContext;
	Page page;
	Properties prop;
	
	//ThreadLocal class for Parallel execution - add this for all high level variables.
	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<Playwright>();
	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<Browser>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<BrowserContext>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<Page>();
	
	//now create two methods get and set
	
	//set is when you want to initialize page or browser or context or Playwright
	//Thread Local get method for playwright
	public static Playwright getPlaywright() {
		return tlPlaywright.get();	
		}
	
	//Thread Local get method for Browser
	public static Browser getBrowser() {
		return tlBrowser.get();	
	}
	
	//Thread Local get method for Browser
	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();	
	}
		
	//Thread Local get method for Page
	public static Page getPage() {
		return tlPage.get();	
	}
	
	
	//Create a method that takes browser name as input
	public Page initBrowser(Properties prop) {
		// added complete Props reference in the class argument to capture all properties
		
		String browserName = prop.getProperty("browser").trim();
		System.out.println(browserName+ " has been selected");
		
		//playwright = Playwright.create(); - we use ThreadLocal variable for playwright instead for parallel runs 
		// Below will create a local copy for Playwright from get method above
		tlPlaywright.set(Playwright.create());
		
		
		//cross browser logic - we use switch case
		switch(browserName.toLowerCase()) {
		case "chromium":
			//we use Thread Local set method at BrowserContext level here 
			//browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			
			//set method for chrome
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;			
		case "firefox":
			//browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;			
		case "safari":
			//browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;			
		case "chrome":
			//browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
			break;	
		case "edge":
			//browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false)));
			break;	
		default:
			System.out.println("*** Please pass the correct browser name ***");
		break;		
		}
		
		//commenting before parallel run Thread local concept
//		browserContext = browser.newContext();
//		page = browserContext.newPage();
		String url_prop = prop.getProperty("url").trim();
//		page.navigate(url_prop);
		
		
		// setting Thread local variable for Browser Context using created getBrowser method
		tlBrowserContext.set(getBrowser().newContext());
		
		// setting Thread local variable for Page using Browser Context
		tlPage.set(getBrowserContext().newPage());
		
		//get url using config props get method for Page url
		getPage().navigate(url_prop);
		
		//returns getPage from ThreadLocal for parallel
		return getPage();
		
		//return page;
		
		}
	
	
/** This method is used to initialize the properties from config file **/
	
	public Properties init_props() {
		
		try {
			FileInputStream	ip = new FileInputStream("./src.test.resources/config/config.properties");
			prop = new Properties(); // loads all properties
			prop.load(ip); // and passes on to the ip. we create a prop reference of Properties in BaseTest
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return prop;
	}
	
	
	//code for taking screenshot on Extent Report Listener
	public static String takeScreenshot() {
		//add a specific path for the screenshot to be stored
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";	
		
		//Page options for screenshot. getPage() method has these options
		//page.screenshot(options...)
		getPage().screenshot(new Page.ScreenshotOptions() //this is for parallel execution usecase
				.setPath(Paths.get(path))
				.setFullPage(true));
		
		return path;	
	}
	
}
	
