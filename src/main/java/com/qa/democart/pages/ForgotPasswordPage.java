package com.qa.democart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

public class ForgotPasswordPage {
	private WebDriver driver;
	private ElementUtil elementUtil;

	private By h1Title = By.xpath("//div[@id='content']/h1");
	private By emailAddressText = By.xpath("//div[@id='content']//legend");
	private By emailTextbox = By.id("input-email");
	private By continueButton = By.xpath("//input[@value='Continue']");
	private By emailNotFoundErrorMessage = By.cssSelector("div.alert");

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public String getForgotPwdPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.FORGOT_PWD_TITLE, 5);
	}

	public String getForgotPwdH1Text() {
		return elementUtil.doGetText(h1Title);
	}

	public boolean isEmailAddressTextPresent() {
		if (elementUtil.getElements(emailAddressText).size() == Constants.EMAIL_ADDRESS_TEXT_COUNT)
			return true;
		return false;
	}

	public LoginPage doResetPasswordWithValidUsername(String email) {
		System.out.println("Reset Password with email:" + email);
		elementUtil.doSendKeys(emailTextbox, email);
		elementUtil.doClick(continueButton);
		return new LoginPage(driver);
	}

	public void doResetPasswordWithInvalidUsername(String email) {
		System.out.println("Reset Password with email:" + email);
		elementUtil.doSendKeys(emailTextbox, email);
		elementUtil.doClick(continueButton);
	}

	public String getEmailNotFoundErrorMsg() {
		if (elementUtil.doIsDisplayed(emailNotFoundErrorMessage))
			return elementUtil.doGetText(emailNotFoundErrorMessage);

		return null;
	}

}
