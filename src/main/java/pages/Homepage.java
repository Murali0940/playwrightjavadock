package pages;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import base.BaseDriver;

public class Homepage {

    private Page page;

    // Locators

    private Locator logo;
    private Locator searchInput;

    public Homepage(Page page) {
        this.page = page;
        this.logo = page.locator("//img[@src='assets/icons/logo.png']");
        this.searchInput = page.getByPlaceholder("Search");

    }

    public String getTitle() {
        return page.title();
    }

    public void searchoptions(String option) {

        Locator filename = page.getByText("Filename");
        Locator attribute = page.getByText("Attributes");
        Locator content = page.getByText("Content");

        if (filename.isChecked() && attribute.isChecked() && content.isChecked()) {
            filename.uncheck();
            attribute.uncheck();
            content.uncheck();
            System.out.println("All search options unchecked.");
        } else {
            System.out.println("Not all search options are checked. Current states - Filename: " + filename.isChecked()
                    + ", Attributes: " + attribute.isChecked() + ", Content: " + content.isChecked());
        }

        switch (option.toLowerCase()) {
            case "filename":
                if (!filename.isChecked()) {
                    attribute.uncheck();
                    content.uncheck();
                    System.out.println("Filename option selected..");
                } else {
                    System.out.println("Filename option is already selected..");
                }
                break;
            case "attributes":
                if (!attribute.isChecked()) {
                    filename.uncheck();
                    content.uncheck();
                    System.out.println("Attributes option selected.");
                } else {
                    System.out.println("Attributes option is already selected.");
                }
                break;
            case "content":
                if (!content.isChecked()) {
                    filename.uncheck();
                    attribute.uncheck();
                    System.out.println("Content option selected.");
                } else {
                    System.out.println("Content option is already selected.");
                }
                break;
            default:
                System.out.println("Invalid search option: " + option);
        }

    }

    // PDF_Search_in_SearchBar

    public void search_File_in_SearchBar(String searchTerm) {

        Locator alfaDockLogo = page.locator("//img[contains(@src,'logo.png')]");

        searchInput.click();
        searchInput.fill(searchTerm);
        page.getByText("ui-btn").click();
        searchoptions("Filename");
        page.waitForTimeout(3000);
        page.locator("//img[@src='assets/ai-search.png']").click();
        System.out.println("Search term entered: " + searchTerm);
        page.waitForLoadState(LoadState.LOAD);
        System.out.println("Page loaded after search.");
        Page newPage = page.context().waitForPage(() -> {
            page.locator("div.imageDiv").first().dblclick();
            System.out.println("First search result opened.");
        });
        // Switches to the new tab automatically
        newPage.waitForLoadState(LoadState.LOAD);

        String newTabUrl = newPage.url();

        System.out.println("New Tab URL : " + newTabUrl);

        page.waitForTimeout(8000);

        if (newTabUrl.contains("pdfviewer")) {
            BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
            System.out.println("PDF Viewer is opened.");
        } else if (newTabUrl.contains("a3dviewer")) {
            BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
            System.out.println("3D Viewer is opened.");
        } else if (newTabUrl.contains("csvviewer")) {
            BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
            System.out.println("CSV Viewer is opened.");
        } else if (newTabUrl.contains("drawing")) {
            BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
            System.out.println("Drawing Viewer is opened.");
        } else {
            BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
            System.out.println("File is not opened in any viewer.");
        }

        newPage.close();
        alfaDockLogo.click();
        System.out.println("Returned to Home page.");

    }

    public void alfadocklogo() {
        Assert.assertTrue(
                logo.isVisible(),
                "Alfadock logo is not visible on the Home page.");
        System.out.println("Alfadock Logo is visible.");
    }

}