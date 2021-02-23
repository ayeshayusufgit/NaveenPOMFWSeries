package com.qa.democart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.democart.factory.DriverFactory;

public class ElementUtil {
	private static final Logger LOGGER = Logger.getLogger(ElementUtil.class);
	private WebDriver driver;// The access of this reference variable shouldnt be provided out of the class
	private JavaScriptUtil jsUtil;

	// If static is used then driver/methods have to be made static
	// No static WebDriver reference/method cannot be used in parallel execution
	// If static is used then we are not using proper oo concept
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		this.jsUtil = new JavaScriptUtil(this.driver);
	}

	public WebElement getElement(By locator) {
		LOGGER.info("Locator is:" + locator);
		WebElement element = driver.findElement(locator);
		LOGGER.info("Element is:" + element.toString());
		if (DriverFactory.highlight.equals("true")) {
			jsUtil.flash(element);
		}
		return element;
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doActionsClick(By locator) {
		Actions action = new Actions(driver);
		// action.click(getElement(locator)).perform();
		action.moveToElement(getElement(locator)).click().build().perform();
	}

	public void doMoveToElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator)).perform();
	}

	// Overloaded methods
	public void clickOnSubMenu(By parentMenu, By firstSubMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doActionsClick(firstSubMenu);
	}

	// Overloaded methods
	public void clickOnSubMenu(By parentMenu, By firstSubMenu, By secondSubMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doMoveToElement(firstSubMenu);
		Thread.sleep(2000);
		doActionsClick(secondSubMenu);
	}

	public void doSendKeys(By locator, String value) {
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		// action.sendKeys(getElement(locator),value)).build().perform()
		action.moveToElement(getElement(locator)).sendKeys(value).build().perform();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean checkElement(By locator) {
		if (getElements(locator).size() > 0) {
			return true;
		}
		return false;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void clickElement(By locator, String value) {
		List<WebElement> elementsList = getElements(locator);
		System.out.println(elementsList.size());

		for (int i = 0; i < elementsList.size(); i++) {

			String elementText = elementsList.get(i).getText();
			System.out.println(elementText);

			if (elementText.equals(value)) {
				elementsList.get(i).click();
				break;
			}

		}
	}

//==================Dropdown Utils======================
	// Select class drodpwn utils
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectByVisbleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByValue(text);
	}

	public List<String> getDropdownOptions(By locator) {
		List<String> optionsValueList = new ArrayList<String>();
		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionslist = select.getOptions();

		for (WebElement e : optionslist) {
			String text = e.getText();
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public void selectDropdownValue(By locator, String value) {
		List<WebElement> indusList = getElements(locator);

		for (WebElement e : indusList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void selectChoiceFromDropdown(By locator, String... value) {
		List<WebElement> choiceList = getElements(locator);
		if (!value[0].equalsIgnoreCase("All")) {

			for (WebElement e : choiceList) {
				String text = e.getText();
				System.out.println(e.getText());

				for (int i = 0; i < value.length; i++) {
					if (text.equals(value[i])) {
						e.click();
						break;
					}
				}
			}
		} else {
			// All Selection

			try {
				for (WebElement e : choiceList) {
					e.click();
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	// ========================wait utils====================

	public List<WebElement> visiblityOfAllElements(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void getPageElementsText(By locator, int timeOut) {
		visiblityOfAllElements(locator, timeOut).stream().forEach(ele -> System.out.println(ele.getText()));
	}

	/*
	 * presenceOfElementLocated(By locator)
	 * 
	 * 
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does notnecessarily mean that the element is visible.
	 */
	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/*
	 * 
	 * 
	 * An expectation for checking that an element, known to be present on the DOM
	 * of a page, isvisible. Visibility means that the element is not only displayed
	 * but also has a height andwidth that is greater than 0.
	 * 
	 * @param locator
	 * 
	 * @param timeOut
	 * 
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
	}

	public boolean waitForUrlToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlContains(urlValue));
	}

	/**
	 * An expectation for checking an element is clickable and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptJSAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissJSAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public String waitForTitleToBe(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}

	/**
	 * This method checking the presence of element using FluentWait
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForElementPresentWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * This method checking the presence of element using FluentWait
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementPresentWithFluentWait(By locator, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> visibilityOfAllElementsWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> visibilityOfAllElementsWithFluentWait(By locator, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public boolean waitForUrlToBeWithFluentWait(String urlValue, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.urlContains(urlValue));
	}

	public boolean waitForUrlToBeWithFluentWait(String urlValue, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.urlContains(urlValue));
	}

	public void clickWhenReadyWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public void clickWhenReadyWithFluentWait(By locator, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public Alert waitForAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public Alert waitForAlertWithFluentWait(int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String waitForTitleToBeWithFluentWait(String title, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}

	public String waitForTitleToBeWithFluentWait(String title, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}

	/**
	 * This method will check the page is fully loaded or not
	 * 
	 * @param timeOut
	 */
	public void jseWaitForPageLoad(int timeOut) {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String jsCommand = "return document.readyState";
		if (jse.executeScript(jsCommand).toString().equals("complete")) {
			System.out.println("Page is fully loaded");
			return;
		}
		for (int i = 0; i < timeOut; i++) {
			try {
				Thread.sleep(500);
				if (jse.executeScript(jsCommand).toString().equals("complete")) {
					System.out.println("Page is fully loaded");
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void imp() {

	}
}
