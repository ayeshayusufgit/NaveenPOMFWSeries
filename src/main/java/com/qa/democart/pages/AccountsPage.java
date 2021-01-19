package com.qa.democart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.democart.utils.Constants;
import com.qa.democart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {

	private ElementUtil elementUtil;

	private By headerLogo = By.cssSelector("div#logo a");
	private By accountSectionHeaders = By.cssSelector("div#content h2");
	private By searchTextbox = By.cssSelector("div#search input");
	private By searchButton = By.cssSelector("div#search button");
	private By searchItemsResult=By.cssSelector("div.product-layout .product-thumb");

	public AccountsPage(WebDriver driver) {
		elementUtil = new ElementUtil(driver);
	}

	@Step("Get the Account Page Title")
	public String getAccountsPageTitle() {
		return elementUtil.waitForTitleToBe(Constants.ACCOUNTS_PAGE_TITLE, 5);
	}

	@Step("Get the Account Page Header Value")
	public String getHeaderValue() {
		if (elementUtil.doIsDisplayed(headerLogo)) {
			return elementUtil.doGetText(headerLogo);
		}
		return null;
	}

	@Step("Get the count of the Account Section")
	public int getAccountsSectionCount() {
		return elementUtil.getElements(accountSectionHeaders).size();
	}

	@Step("Get the Account Section List")
	public List<String> getAccountSectionList() {
		List<String> accountsList = new ArrayList<String>();
		List<WebElement> accountsSectionList = elementUtil.getElements(accountSectionHeaders);

		for (WebElement element : accountsSectionList) {
			String accountsSectionText = element.getText();
			accountsList.add(accountsSectionText);
		}
		return accountsList;
	}

	@Step("Perform Search with:{0}")
	public boolean doSearch(String searchTerm) {
		System.out.println("Searching for:"+searchTerm);
		
		elementUtil.doSendKeys(searchTextbox, searchTerm);
		elementUtil.doClick(searchButton);
		if(elementUtil.getElements(searchItemsResult).size()>0) {
			return true;
		}
		return false;
	}
}
