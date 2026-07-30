package pages;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

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

    // PDF_Search_in_SearchBar

    public void searchPDF(String searchTerm) {
        searchInput.click();
        searchInput.fill(searchTerm);
        page.getByText("ui-btn").click();
        searchoptions("Filename");
        page.waitForTimeout(3000);
        page.locator("//img[@src='assets/ai-search.png']").click();
        System.out.println("Search term entered: " + searchTerm);
        page.waitForLoadState(LoadState.LOAD);
        System.out.println("Page loaded after search.");

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
                    System.out.println("Filename option selected.");
                } else {
                    System.out.println("Filename option is already selected.");
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

    public void alfadocklogo() {
        Assert.assertTrue(
                logo.isVisible(),
                "Alfadock logo is not visible on the Home page.");
        System.out.println("Alfadock Logo is visible.");
    }
}