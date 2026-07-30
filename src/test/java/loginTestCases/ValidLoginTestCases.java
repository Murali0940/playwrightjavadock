package loginTestCases;

import org.testng.annotations.Test;

import base.BaseDriver;
import pages.CompLoginPage;
import pages.UserLoginPage;
import utils.ConfigReader;


public class ValidLoginTestCases extends BaseDriver {

	@Test(priority = 1, description = "Verify login with valid Company and User credentials")
	public void validLoginTest() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin(ConfigReader.get("company.username"), ConfigReader.get("company.password"));

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin(ConfigReader.get("user.username"), ConfigReader.get("user.password"));

		userPage.verifyPageURL();

		browser.close();

	}

}
