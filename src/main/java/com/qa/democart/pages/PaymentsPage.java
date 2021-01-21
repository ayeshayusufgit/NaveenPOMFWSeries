package com.qa.democart.pages;

import org.openqa.selenium.By;

public class PaymentsPage {

	public By selectPaymentModeButton = By.id("payment");

	public void getPaymentsPageTitle() {
		System.out.println("Get PAyments Page title");
	}

	public void selectPayment(String modeOfPayment) {
		System.out.println("The mode of Payment" + modeOfPayment);
	}
	
	public void placeOrder() {
		System.out.println("Order is placed!!!");
	}
}
