package com.qa.democart.pages;

import org.openqa.selenium.By;

public class WishlistPage {

	By addToWishlist = By.id("wishListIcon");
	By wishlistTitle = By.id("wishlistTitle");

	public void addToWishlist() {
		System.out.println("Adding to the wishlist!!!");
	}
	
	public void checkWishlistTitle() {
		System.out.println("Check wishlist title!!!");
	}
}
