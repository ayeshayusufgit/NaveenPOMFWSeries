package com.qa.democart.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.democart.base.BaseTest;
import com.qa.democart.pages.RegisterPage;
import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	@BeforeClass
	public void regPageSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	@Test(dataProvider = "getRegisterData")
	public void userRegistrationTest(String fname,String lname,String email,String mobile,String password,String subscribe) {
		registerPage.accountRegistration(fname, lname, email, mobile, password,subscribe);
	}

	@DataProvider
	public Object[][] getRegisterData() {
		Object[][] data = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return data;
	}
}
