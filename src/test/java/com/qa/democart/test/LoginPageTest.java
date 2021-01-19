package com.qa.democart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.democart.base.BaseTest;
import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100 : Design Full Login Page for  Democart application...")
@Story("UserStory 101 : Login Page feature story")
public class LoginPageTest extends BaseTest {

	@Description("Verify the Login Page Title Test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void verifyLoginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("Login Page Title is:" + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Verify the Forgot PAssword Link Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2)
	public void verifyForgotPasswordLinkTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkPresent());
	}

	@DataProvider
	public Object[][] getLoginData() {
		Object[][] data = ExcelUtil.getTestData(Constants.LOGIN_SHEET_NAME);
		return data;
	}
	
	@Description("Verify the negative login Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, dataProvider = "getLoginData")
	public void loginNegativeTest(String username, String password) {
		loginPage.doLogin(username, password);
		Assert.assertEquals(loginPage.getLoginErrorMessage(), Constants.LOGIN_ERROR_MESSAGE);
	}

	@Description("Verify the login Test")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
}
