package com.qa.democart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.democart.utils.ProfileManager;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author ayesh
 */
public class DriverFactory {
	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);
	private ProfileManager profileManager;
	
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
		LOGGER.info("Browser name is:" + browserName);

		highlight = prop.getProperty("highlight");
		System.out.println("Hightlight is:" + highlight);
		
		profileManager=new ProfileManager(prop);
		
		switch (browserName.trim()) {
		case "chrome":
			LOGGER.info("Launching Chrome!");
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver();
			// Create the new ChromeDriver with ThreadLocal Driver
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				init_remoteDriver("chrome");
			} else {
				tlDriver.set(new ChromeDriver(profileManager.getChromeOptions()));
			}
			break;

		case "firefox":
			LOGGER.info("Launching Firefox!");
			WebDriverManager.firefoxdriver().setup();
			// Create the new FirefoxDriver with ThreadLocal Driver
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				init_remoteDriver("firefox");
			} else {
				tlDriver.set(new FirefoxDriver(profileManager.getFirefoxOptions()));
			}
			break;

		case "safari":
			// Create the new SafariDriver with ThreadLocal Driver
			// For safari the docker container cannot be created only available for chrome
			// and safari
			LOGGER.info("Launching Safari!");
			tlDriver.set(new SafariDriver());

			break;

		default:
			LOGGER.info("Please pass the right browser:" + browserName);
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

	public void init_remoteDriver(String browserName) {
		if (browserName.equals("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability("browserName", "chrome");
			cap.setCapability("browserVersion", "85.0");
			cap.setCapability("enableVNC", true);
			cap.setCapability(ChromeOptions.CAPABILITY, profileManager.getChromeOptions());

			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability("browserName", "firefox");
			cap.setCapability("browserVersion", "80.0");
			cap.setCapability("enableVNC", true);
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, profileManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

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
		LOGGER.info("Initializing the prop file");

		try {
			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(fis);// inside the property file all the properties will be stored in the form of
							// Key/Value pairs, prop.load is used to load the properties
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.error("FileNotFoundException is getting generated");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("IOException is getting generated");
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
		LOGGER.info("Taking Screenshot!");
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
