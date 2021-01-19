package com.qa.democart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author ayesh
 */
public class DriverFactory {
	public WebDriver driver;

	// ThreadLocal concept needs to be applied on WebDriver
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * 
	 * @param browserName
	 * @return WebDriver reference on the bases of the given browser
	 * 
	 */

	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser");

		System.out.println("Browser name is:" + browserName);

		switch (browserName.trim()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver();
			// Create the new ChromeDriver with ThreadLocal Driver
			tlDriver.set(new ChromeDriver());
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			// Create the new FirefoxDriver with ThreadLocal Driver
			tlDriver.set(new FirefoxDriver());
			break;

		case "safari":
			// Create the new SafariDriver with ThreadLocal Driver
			tlDriver.set(new SafariDriver());

			break;

		default:
			System.out.println("Please pass the correct browser name:" + browserName);
			break;
		}
		// driver.manage().deleteAllCookies();
		// driver.manage().window().fullscreen();
		// return driver;
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		// return the local copy of the WebDriver

		return getDriver();
	}

	/**
	 * getDriver using ThreadLocal
	 */
	// Every Thread will get a separate copy of the WebDriver, There will be no
	// deadlock condition
	// If one thread is calling a tccase that doesnt mean the other thread needs to
	// wait for that
	public static synchronized WebDriver getDriver() {
		// whatever u set using ThreadLocal that will be returned using get(), pointing
		// to WebDriver
		return tlDriver.get();
	}

	/**
	 * This method will initialize the properties from config.properties file
	 * 
	 */
	public Properties init_prop() {
		Properties prop = null;
		try {
			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(fis);// inside the property file all the properties will be stored in the form of
							// Key/Value pairs, prop.load is used to load the properties
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * This method is used to take the screenshot and it returns the path of the
	 * screenshot
	 */
	public String getScreenShot() {
		// THis returns Screenshot as a file stored somewhere in the memory

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}
