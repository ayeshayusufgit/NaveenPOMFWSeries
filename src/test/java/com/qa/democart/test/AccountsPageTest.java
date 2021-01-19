package com.qa.democart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@Epic("Epic 200 : Design Full Account Page for  Democart application...")
@Story("UserStory 201 : Accounts Page feature story")
public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountsPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Description("Verify Accounts Page Title Test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void accountsPageTitleTest() {
		String title = accountsPage.getAccountsPageTitle();
		System.out.println("Accounts Page Title is :" + title);
		Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
	}

	@Description("Verify Accounts Header Test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void accountsPageHeaderTest() {
		String accountsHeader = accountsPage.getHeaderValue();
		System.out.println("Accounts Page Header:" + accountsHeader);
		Assert.assertEquals(accountsHeader, Constants.ACCOUNTS_PAGE_HEADER);
	}

	@Description("Verify Accounts Page Section Count Test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 3)
	public void accountPageSectionCountTest() {
		Assert.assertTrue(accountsPage.getAccountsSectionCount() == Constants.ACCOUNTS_SECTION_COUNT);
	}

	@Description("Verify Accounts Page Section Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void accountPageSectionTextTest() {
		List<String> accSectionList = accountsPage.getAccountSectionList();
		Assert.assertEquals(accSectionList, Constants.getExpectedAccountsSectionList());
	}

	/*@DataProvider
	public Object[][] productTestData() {
		Object data[][] = { { "iMac" }, { "Macbook" }, { "iPhone" } };
		return data;
	}*/

	@DataProvider
	public Object[][] getProductSearchData(){
		return ExcelUtil.getTestData(Constants.SEARCH_SHEET_NAME);
	}
	
	@Description("Verify Product Search Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5,dataProvider = "getProductSearchData")
	public void searchTest(String searchTerms) {
		Assert.assertTrue(accountsPage.doSearch(searchTerms));
	}

}
