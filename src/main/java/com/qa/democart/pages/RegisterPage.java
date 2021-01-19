package com.qa.democart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By fnameTextbox = By.cssSelector("input#input-firstname");
	private By lnameTextbox = By.cssSelector("input#input-lastname");
	private By emailTextbox = By.cssSelector("input#input-email");
	private By telephoneTextbox = By.cssSelector("input#input-telephone");
	private By pwdTextbox = By.cssSelector("input#input-password");
	private By confirmPwdTextbox = By.cssSelector("input#input-confirm");
	private By subscribeYesRadiobutton = By.xpath("//label[@class='radio-inline'][position()=1]/input");
	private By subscribeNoRadiobutton = By.xpath("//label[@class='radio-inline'][position()=2]/input");
	private By agreeCheckbox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By accountSuccessMessage = By.cssSelector("div#content h1");
	private By logOutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public boolean accountRegistration(String fname, String lname, String email, String phone, String pwd,
			String subscribe) {
		elementUtil.doSendKeys(fnameTextbox, fname);
		elementUtil.doSendKeys(lnameTextbox, lname);
		elementUtil.doSendKeys(emailTextbox, email);
		elementUtil.doSendKeys(telephoneTextbox, phone);
		elementUtil.doSendKeys(pwdTextbox, pwd);
		elementUtil.doSendKeys(confirmPwdTextbox, pwd);
		if (subscribe.equalsIgnoreCase("Yes")) {
			elementUtil.doClick(subscribeYesRadiobutton);
		} else {
			elementUtil.doClick(subscribeNoRadiobutton);
		}
		elementUtil.doClick(agreeCheckbox);
		elementUtil.doClick(continueButton);
		String message = elementUtil.doGetText(accountSuccessMessage);
		
		if (message.equals(Constants.ACCOUNTS_CREATION_SUCCESS_MESSAGE)) {
			elementUtil.doClick(logOutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		return false;
	}

	public String getAccountsPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.ACCOUNTS_PAGE_TITLE, 5);
	}
}