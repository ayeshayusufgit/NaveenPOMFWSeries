package com.qa.democart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.qa.democart.factory.DriverFactory;
import com.qa.democart.pages.AccountsPage;
import com.qa.democart.pages.ForgotPasswordPage;
import com.qa.democart.pages.LoginPage;
import com.qa.democart.pages.RegisterPage;

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	public Properties prop;
	public LoginPage loginPage;
	public AccountsPage accountsPage;
	public ForgotPasswordPage forgotPwdPage;
	public RegisterPage registerPage;

	// setUp() to launch the browser
	@BeforeTest
	public void setUp() {
		df=new DriverFactory();
		
		prop = df.init_prop();
		driver = df.init_driver(prop);
		driver.get(prop.getProperty("url"));
		loginPage=new LoginPage(driver);
		accountsPage=new AccountsPage(driver);
		forgotPwdPage=new ForgotPasswordPage(driver);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
