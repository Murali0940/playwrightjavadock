package loginTestCases;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import base.BaseDriver;
import pages.CompLoginPage;
import pages.UserLoginPage;

public class InvalidLoginTestCases extends BaseDriver {

	@Test(priority = 1, description = "Verify_login_with_invalid_Company_and_User_credentials")
	public void Verify_login_with_invalid_Username_and_Password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("atkgi", "1224");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

	}

	@Test(priority = 2, description = "Verify_login_with_empty_username_and_wrong_password")
	public void Verify_login_with_empty_username_and_wrong_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("", "1234");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

	}

	@Test(priority = 3, description = "Verify_login_with_wrong_username_and_empty_password")
	public void Verify_login_with_wrong_username_and_empty_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("", "1234");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

	}

	@Test(priority = 4, description = "Verify_login_with_wrong_username_and_empty_password")
	public void Verify_login_with_empty_username_and_empty_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("", "");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

	}

	@Test(priority = 5, description = "Verify_userlogin_with_wrong_username_and_wrong_password")
	public void Verify_userlogin_with_invalid_username_and_invalid_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("Atkgi", "1234");

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin("atkgi", "1234");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

		userPage.userLogout();

	}

	@Test(priority = 6, description = "Verify_Userlogin_with_valid_username_and_invalid_password")
	public void Verify_Userlogin_with_valid_username_and_invalid_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("Atkgi", "1234");

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin("Atkgi", "asdfsafa");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

		userPage.userLogout();

	}

	@Test(priority = 7, description = "Verify_Userlogin_with_invalid_username_and_valid_password")
	public void Verify_Userlogin_with_invalid_username_and_valid_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("Atkgi", "1234");

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin("Atki", "1234");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

		userPage.userLogout();

	}

	@Test(priority = 8, description = "Verify_Userlogin_with_empty_username_and_invalid_password")
	public void Verify_Userlogin_with_empty_username_and_invalid_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("Atkgi", "1234");

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin("", "asfgasg");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

		userPage.userLogout();

	}

	@Test(priority = 7, description = "Verify_Userlogin_with_invalid_username_and_empty_password")
	public void Verify_Userlogin_with_invalid_username_and_empty_password() {

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin("Atkgi", "1234");

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin("tkgi", "");

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();

		userPage.userLogout();

	}

}
