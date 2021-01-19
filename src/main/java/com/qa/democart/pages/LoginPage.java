package com.qa.democart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil elementUtil;

	// Page Locators: By locators
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.cssSelector("input[value='Login']");
	private By forgotPasswordLinks = By.linkText("Forgotten Password");
	private By emailConfirmationMessage = By.cssSelector("div.alert");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessage = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	// Page Constructors
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		// elementUtil = new ElementUtil(this.driver);
		elementUtil = new ElementUtil(driver);
	}

	@Step("Getting Login Page Title")
	// Page Actions
	public String getLoginPageTitle() {
		// return driver.getTitle();
		return elementUtil.waitForTitleToBe(Constants.LOGIN_PAGE_TITLE, 5);
	}

	/*
	 * public boolean isForgotPasswordLinkPresent() { if
	 * (driver.findElements(forgotPasswordLinks).size() ==
	 * Constants.FORGOTTEN_PWD_LINK_COUNT) return true; return false; }
	 */
	@Step("Checking presence of Forgot Password Link in the Login Page")
	public boolean isForgotPasswordLinkPresent() {
		if (elementUtil.getElements(forgotPasswordLinks).size() == Constants.FORGOT_PWD_LINK_COUNT)
			return true;
		return false;
	}

	/*
	 * public void doLogin(String username, String password) {
	 * System.out.println("Login with username:" + username + " and password:" +
	 * password); driver.findElement(emailId).sendKeys(username);
	 * driver.findElement(passwordId).sendKeys(password);
	 * driver.findElement(loginButton).click(); }
	 */
	@Step("Login with username:{0} and password:{1}")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("Login with username:" + un + " and password:" + pwd);
		elementUtil.doSendKeys(email, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginButton);
		return new AccountsPage(driver);
	}

	@Step("Navigate to Forgot Password Page")
	public ForgotPasswordPage clickForgotPasswordLink() {
		elementUtil.doClick(forgotPasswordLinks);
		return new ForgotPasswordPage(driver);
	}

	@Step("Get the Email Confirmation Message")
	public String getEmailConfirmationMessage() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elementUtil.doGetText(emailConfirmationMessage);
	}

	@Step("Get the Login Error Message")
	public String getLoginErrorMessage() {
		elementUtil.waitForElementVisible(loginErrorMessage, 10);
		if (elementUtil.doIsDisplayed(loginErrorMessage)) {
			return elementUtil.doGetText(loginErrorMessage);
		}
		return null;
	}

	@Step("Navigate to Register Page")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
