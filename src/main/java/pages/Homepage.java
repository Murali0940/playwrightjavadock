package pages;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import io.qameta.allure.Allure;
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

    // public void search_File_in_SearchBar(String searchTerm) {

    // Locator alfaDockLogo = page.locator("//img[contains(@src,'logo.png')]");

    // searchInput.click();
    // searchInput.fill(searchTerm);
    // page.getByText("ui-btn").click();
    // searchoptions("Filename");
    // page.waitForTimeout(3000);
    // page.locator("//img[@src='assets/ai-search.png']").click();
    // System.out.println("Search term entered: " + searchTerm);
    // page.waitForLoadState(LoadState.LOAD);
    // System.out.println("Page loaded after search.");
    // Page newPage = page.context().waitForPage(() -> {
    // page.locator("div.imageDiv").first().dblclick();
    // System.out.println("First search result opened.");
    // });
    // // Switches to the new tab automatically
    // newPage.waitForLoadState(LoadState.LOAD);

    // String newTabUrl = newPage.url();

    // System.out.println("New Tab URL : " + newTabUrl);

    // page.waitForTimeout(8000);

    // if (newTabUrl.contains("pdfviewer")) {
    // BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
    // System.out.println("PDF Viewer is opened.");
    // } else if (newTabUrl.contains("a3dviewer")) {
    // BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
    // System.out.println("3D Viewer is opened.");
    // } else if (newTabUrl.contains("csvviewer")) {
    // BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
    // System.out.println("CSV Viewer is opened.");
    // } else if (newTabUrl.contains("drawing")) {
    // BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
    // System.out.println("Drawing Viewer is opened.");
    // } else {
    // BaseDriver.takeScreenshot(newPage, "Search_Result_" + searchTerm);
    // System.out.println("File is not opened in any viewer.");
    // }

    // newPage.close();
    // alfadocklogo();
    // alfaDockLogo.click();
    // System.out.println("Returned to Home page.");

    // }

    // Click Search Button
    private void clickSearchButton() {
        page.locator("//img[@src='assets/ai-search.png']").waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        page.locator("//img[@src='assets/ai-search.png']").click();
    }

    // Search File

    private void searchFile(String searchTerm, String type) {

        searchInput.click();
        searchInput.fill(searchTerm);
        Allure.step("Test entered in search bar");
        Allure.step("Test entered in search bar.");

        page.getByText("ui-btn").click();
        selectType(type);
        Allure.step("Type is selected.");

        clickSearchButton();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        System.out.println("Search completed : " + searchTerm);
        Allure.step("Search completed : " + searchTerm);
    }

    // Open First Search Result
    private Page openFirstSearchResult(String type) {

    return page.context().waitForPage(() -> {

        if (type.equalsIgnoreCase("File")) {

            Locator file = page.locator("div.imageDiv").first();

            file.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            file.dblclick();
            Allure.step("First file double-clicked in search result.");

        } else if (type.equalsIgnoreCase("Folder")) {

            Locator folder = page.locator("(//div[@class='imageDivSmall'])[1]").first();

            folder.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            folder.dblclick();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);
            Allure.step("First folder double-clicked.");

            Locator fileInsideFolder = page.locator("div.imageDiv").first();

            fileInsideFolder.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            fileInsideFolder.dblclick();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);
            Allure.step("First file inside folder double-clicked.");
        }else{
            System.out.println("Invalid type provided: " + type);
            Allure.step("Invalid type provided: " + type);
        }

    });
}

    // Wait Until Viewer Opens
    private void waitForViewer(Page viewerPage) {

        viewerPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        viewerPage.waitForLoadState(LoadState.NETWORKIDLE);
        Allure.step("waiting for viewer");

    }

    // Capture Screenshot
    private void captureViewerScreenshot(Page viewerPage, String searchTerm) {

        BaseDriver.takeScreenshot(viewerPage, "Search_Result_" + searchTerm);

        Allure.step("screenshot captured");

    }

    // Detect Viewer
    private String getViewerName(String url) {

        if (url.contains("pdfviewer"))
            return "PDF Viewer";

        if (url.contains("a3dviewer"))
            return "3D Viewer";

        if (url.contains("csvviewer"))
            return "CSV Viewer";

        if (url.contains("drawing"))
            return "Drawing Viewer";

        return "Unknown Viewer";
    }

    // Return Home
    private void returnToHome(Page viewerPage) {

        viewerPage.close();

        alfadocklogo();

        page.locator("//img[contains(@src,'logo.png')]")
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE));

        page.locator("//img[contains(@src,'logo.png')]").click();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        System.out.println("Returned to Home page.");
        Allure.step("return to homepage");

    }

    public void search_File_in_SearchBar(String searchTerm, String type) {

        searchFile(searchTerm, type);

        Page viewerPage = openFirstSearchResult(type);

        waitForViewer(viewerPage);

        String viewerUrl = viewerPage.url();

        System.out.println("Viewer URL : " + viewerUrl);

        captureViewerScreenshot(viewerPage, searchTerm);

        System.out.println(getViewerName(viewerUrl) + " is opened.");

        returnToHome(viewerPage);
    }

    private void alfadocklogo() {
        Assert.assertTrue(
                logo.isVisible(),
                "Alfadock logo is not visible on the Home page.");
        System.out.println("Alfadock Logo is visible.");
    }

    private void selectType(String type) {

        Locator checkbox = page.locator(
                "//label[text()='" + type + "']/preceding-sibling::div//div[contains(@class,'ui-chkbox-box')]");

        Locator icon = page.locator(
                "//label[text()='" + type + "']/preceding-sibling::div//span[contains(@class,'ui-chkbox-icon')]");

        if (!icon.getAttribute("class").contains("pi-check")) {
            checkbox.click();
            System.out.println(type + " selected.");
        } else {
            System.out.println(type + " is already selected.");
        }
    }

}