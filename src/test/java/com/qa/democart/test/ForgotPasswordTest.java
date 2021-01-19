package com.qa.democart.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.democart.base.BaseTest;
import com.qa.democart.utils.Constants;

public class ForgotPasswordTest extends BaseTest {

	@BeforeClass
	public void forgotPasswordPageSetup() {
		forgotPwdPage = loginPage.clickForgotPasswordLink();
	}

	@Test(priority = 1)
	public void forgotPwdPageTitleTest() {
		String pageTitle = forgotPwdPage.getForgotPwdPageTitle();
		System.out.println("Forgot Password Page Title is:" + pageTitle);
		Assert.assertEquals(pageTitle, Constants.FORGOT_PWD_TITLE);
	}

	@Test(priority = 2)
	public void forgotPwdH1TitleTest() {
		String h1Text = forgotPwdPage.getForgotPwdH1Text();
		System.out.println("Forgot Password H1 text is:" + h1Text);
		Assert.assertEquals(h1Text, Constants.FORGOT_PWD_H1_TITLE);
	}

	@Test(priority = 3)
	public void forgotPwdEmailAddressTextTest() {
		Assert.assertTrue(forgotPwdPage.isEmailAddressTextPresent());
	}

	@Test(priority = 4)
	public void passwordResetWithInvalidUsernameTest() {
		forgotPwdPage.doResetPasswordWithInvalidUsername(prop.getProperty("usernameNotExisting"));
		Assert.assertEquals(forgotPwdPage.getEmailNotFoundErrorMsg(),
				Constants.EMAIL_ADDRESS_NOT_FOUND_ERROR_MESSAGE_COUNT);
	}

	@Test(priority = 5)
	public void passwordResetWithValidUsernameTest() {
		loginPage = forgotPwdPage.doResetPasswordWithValidUsername(prop.getProperty("username"));
		Assert.assertEquals(loginPage.getEmailConfirmationMessage(), Constants.FORGOT_PWD_EMAIL_CONFIRMATION_MSG);
	}
}
