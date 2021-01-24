package com.qa.democart.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final int FORGOT_PWD_LINK_COUNT = 2;
	public static final String FORGOT_PWD_TITLE = "Forgot Your Password?";
	public static final String FORGOT_PWD_H1_TITLE = "Forgot Your Password?";

	public static final int EMAIL_ADDRESS_TEXT_COUNT = 1;
	public static final String EMAIL_ADDRESS_NOT_FOUND_ERROR_MESSAGE_COUNT = "Warning: The E-Mail Address was not found in our records, please try again!";
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final String ACCOUNTS_PAGE_HEADER = "Your Store";
	//public static final int ACCOUNTS_SECTION_COUNT = 4;
	public static final int ACCOUNTS_SECTION_COUNT = 5;
	public static final String ACCOUNTS_CREATION_SUCCESS_MESSAGE="Your Account Has Been Created!";
	public static final String REGISTER_SHEET_NAME="Register";
	public static final String SEARCH_SHEET_NAME="Search";
	public static final String LOGIN_SHEET_NAME="Login";
	public static final String LOGIN_ERROR_MESSAGE="Warning: No match for E-Mail Address and/or Password.";
	
	public static List<String> accSecList;
	public static final String FORGOT_PWD_EMAIL_CONFIRMATION_MSG="An email with a confirmation link has been sent your email address.";

	public static List<String> getExpectedAccountsSectionList() {
		accSecList = new ArrayList<String>();
		accSecList.add("My Account");
		accSecList.add("My Orders");
		accSecList.add("My Affiliate Account");
		accSecList.add("Newsletter");
		return accSecList;
	}
}
