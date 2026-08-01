package tests;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;

import base.BaseDriver;
import pages.CompLoginPage;
import pages.Homepage;
import pages.UserLoginPage;
import utils.ConfigReader;

public class HomepageTest extends BaseDriver {

	@Test(priority = 1, description = "Verify login with valid Company and User credentials..")
	public void validLoginTest() {

		Locator languageDropdown = page.locator("#mySelect");
		languageDropdown.selectOption("English");

		CompLoginPage companyPage = new CompLoginPage(page);

		companyPage.companyLogin(ConfigReader.get("company.username"), ConfigReader.get("company.password"));

		UserLoginPage userPage = new UserLoginPage(page);

		userPage.userLogin(ConfigReader.get("user.username"), ConfigReader.get("user.password"));

	}

	@Test(priority = 2, description = "Verify_pdf_search")
	public void Verify_pdf_search() {

		new Homepage(page).search_File_in_SearchBar(".pdf");
	}

	@Test(priority = 3, description = "Verify_a3dasm_search")
	public void Verify_a3dasm_search() {

		new Homepage(page).search_File_in_SearchBar(".a3dasm");
	}

	@Test(priority = 4, description = "Verify_a3dprt_search")
	public void Verify_a3dprt_search() {

		new Homepage(page).search_File_in_SearchBar(".a3dprt");
	}

	@Test(priority = 5, description = "Verify_csv_search")
	public void Verify_csv_search() {

		new Homepage(page).search_File_in_SearchBar(".csv");
	}

	@Test(priority = 6, description = "Verify_dxf_search")
	public void Verify_dxf_search() {

		new Homepage(page).search_File_in_SearchBar(".dxf");
	}

	@Test(priority = 7, description = "Verify_dwg_search")
	public void Verify_dwg_search() {

		new Homepage(page).search_File_in_SearchBar(".dwg");
	}

	@Test(priority = 8, description = "Verify_docx_search")
	public void Verify_docx_search() {

		new Homepage(page).search_File_in_SearchBar(".docx");
	}

	@Test(priority = 9, description = "Verify_pptx_search")
	public void Verify_pptx_search() {

		new Homepage(page).search_File_in_SearchBar(".pptx");
	}

	@Test(priority = 10, description = "Verify_jpg_search")
	public void Verify_jpg_search() {

		new Homepage(page).search_File_in_SearchBar(".jpg");
	}

	@Test(priority = 11, description = "Verify_png_search")
	public void Verify_png_search() {

		new Homepage(page).search_File_in_SearchBar(".png");
	}

	@Test(priority = 12, description = "Verify_xlsx_search")
	public void Verify_xlsx_search() {

		new Homepage(page).search_File_in_SearchBar(".xlsx");
	}

	@Test(priority = 13, description = "Verify_tiff_search")
	public void Verify_tiff_search() {

		new Homepage(page).search_File_in_SearchBar(".tiff");
	}

	

	

}