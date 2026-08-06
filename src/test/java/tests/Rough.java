package tests;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;

import base.BaseDriver;
import pages.CompLoginPage;
import pages.Homepage;
import pages.UserLoginPage;
import utils.ConfigReader;

public class Rough extends BaseDriver {

	@Test(priority = 1, description = "Verify login with valid Company and User credentials..")
	public void validLoginTest() {

		Locator languageDropdown = page.locator("#mySelect");
		languageDropdown.selectOption("English");

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin(ConfigReader.get("company.username"), ConfigReader.get("company.password"));

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin(ConfigReader.get("user.username"), ConfigReader.get("user.password"));

	}

	@Test(priority = 2, description = "search_File_Root_Location")
	public void search_Root_Location_File() {

		new Homepage(page).search_File_Root_Location(".pdf", "File");
	}

}
