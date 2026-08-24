package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import io.qameta.allure.Allure;
import base.BaseDriver;

public class Homepage {

    private Page page;
    private static final Logger logger = LogManager.getLogger(Homepage.class);

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
            logger.info("All search options unchecked.");
        } else {
            System.out.println("Not all search options are checked. Current states - Filename: " + filename.isChecked()
                    + ", Attributes: " + attribute.isChecked() + ", Content: " + content.isChecked());
            logger.info("Not all search options are checked. Current states - Filename: " + filename.isChecked()
                    + ", Attributes: " + attribute.isChecked() + ", Content: " + content.isChecked());
        }

        switch (option.toLowerCase()) {
            case "filename":
                if (!filename.isChecked()) {
                    attribute.uncheck();
                    content.uncheck();
                    System.out.println("Filename option selected..");
                    logger.info("Filename option selected..");
                } else {
                    System.out.println("Filename option is already selected..");
                    logger.info("Filename option is already selected..");
                }
                break;
            case "attributes":
                if (!attribute.isChecked()) {
                    filename.uncheck();
                    content.uncheck();
                    System.out.println("Attributes option selected.");
                    logger.info("Attributes option selected.");
                } else {
                    System.out.println("Attributes option is already selected.");
                    logger.info("Attributes option is already selected.");
                }
                break;
            case "content":
                if (!content.isChecked()) {
                    filename.uncheck();
                    attribute.uncheck();
                    System.out.println("Content option selected.");
                    logger.info("Content option selected.");
                } else {
                    System.out.println("Content option is already selected.");
                    logger.info("Content option is already selected.");
                }
                break;
            default:
                System.out.println("Invalid search option: " + option);
                logger.warn("Invalid search option: " + option);
        }

    }

    // Click Search Button
    private void clickSearchButton() {
        page.locator("//img[@src='assets/ai-search.png']").waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        page.locator("//img[@src='assets/ai-search.png']").click();
    }

    // Search File

    private void searchFile(String searchTerm, String type) {

        page.waitForTimeout(3000);

        searchInput.click();
        searchInput.fill(searchTerm);
        Allure.step("Test entered in search bar.");
        logger.info("Test entered in search bar.");

        page.getByText("ui-btn").click();
        selectType(type);
        Allure.step("Type is selected.");
        logger.info("Type is selected.");

        clickSearchButton();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        System.out.println("Search completed : " + searchTerm);
        Allure.step("Search completed : " + searchTerm);
        logger.info("Search completed : " + searchTerm);
    }

    // Open First Search Result
    private Page openFirstSearchResult(String type) {

        return page.context().waitForPage(() -> {

            if (type.equalsIgnoreCase("File")) {

                Locator file = page.locator("div.imageDiv").first();

                file.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE));

                file.dblclick();

                handlePasswordDialogIfPresent();

                Allure.step("First file double-clicked in search result.");
                logger.info("First file double-clicked in search result.");

            } else if (type.equalsIgnoreCase("Folder")) {

                Locator folder = page.locator("(//div[@class='imageDivSmall'])[1]").first();

                folder.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE));

                folder.dblclick();
                page.waitForLoadState(LoadState.NETWORKIDLE);
                page.waitForTimeout(3000);
                Allure.step("First folder double-clicked.");
                logger.info("First folder double-clicked.");

                Locator fileInsideFolder = page.locator("div.imageDiv").first();

                fileInsideFolder.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE));

                fileInsideFolder.dblclick();
                handlePasswordDialogIfPresent();
                page.waitForLoadState(LoadState.NETWORKIDLE);
                page.waitForTimeout(3000);
                Allure.step("First file inside folder double-clicked.");
                logger.info("First file inside folder double-clicked.");
            } else {
                System.out.println("Invalid type provided: " + type);
                logger.warn("Invalid type provided: " + type);
                Allure.step("Invalid type provided: " + type);
            }

        });
    }

    private void handlePasswordDialogIfPresent() {

        Locator passwordDialog = page.locator("div.ui-dialog")
                .filter(new Locator.FilterOptions()
                        .setHasText("Enter password"));

        // Dialog is not present
        if (passwordDialog.count() == 0 || !passwordDialog.first().isVisible()) {
            logger.info("No password lock detected. Continuing...");
            return;
        }

        logger.info("Password-protected file/folder detected.");

        Allure.step("Password dialog detected.");

        Locator dialog = passwordDialog.first();

        // Wait for password textbox
        Locator passwordInput = dialog.getByRole(AriaRole.TEXTBOX);

        passwordInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        passwordInput.fill("1234");

        logger.info("Password entered.");

        // Click OK inside THIS dialog
        Locator okButton = dialog.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("OK"));

        okButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        okButton.click();

        Allure.step("Password entered and OK clicked.");

        // Wait until dialog disappears
        dialog.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN));

        logger.info("Password dialog closed successfully.");
    }

    // Wait Until Viewer Opens
    private void waitForViewer(Page viewerPage) {

        viewerPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        viewerPage.waitForLoadState(LoadState.NETWORKIDLE);
        Allure.step("waiting for viewer");
        logger.info("waiting for viewer");

    }

    // Capture Screenshot
    private void captureViewerScreenshot(Page viewerPage, String searchTerm) {

        BaseDriver.takeScreenshot(viewerPage, "Search_Result_" + searchTerm);

        Allure.step("screenshot captured");
        logger.info("screenshot captured");

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
        logger.info("Returned to Home page.");

    }

    private void alfadocklogo() {
        Assert.assertTrue(
                logo.isVisible(),
                "Alfadock logo is not visible on the Home page.");
        System.out.println("Alfadock Logo is visible.");
        Allure.step("Alfadock Logo is visible.");
        logger.info("Alfadock Logo is visible.");
    }

    private void selectType(String type) {

        Locator checkbox = page.locator(
                "//label[text()='" + type + "']/preceding-sibling::div//div[contains(@class,'ui-chkbox-box')]");

        Locator icon = page.locator(
                "//label[text()='" + type + "']/preceding-sibling::div//span[contains(@class,'ui-chkbox-icon')]");

        if (!icon.getAttribute("class").contains("pi-check")) {
            checkbox.click();
            System.out.println(type + " selected.");
            Allure.step(type + " selected.");
            logger.info(type + " selected.");
        } else {
            System.out.println(type + " is already selected.");
            Allure.step(type + " is already selected.");
            logger.info(type + " is already selected.");
        }
    }

    private void click_First_File() {

        page.waitForLoadState();

        Locator file = page.locator("(//div[@class='imageDiv'])[1]");

        file.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        file.click();
        Allure.step("First file clicked in search result.");
        logger.info("First file clicked in search result.");

    }

    private void click_First_Folder() {

        Locator folder = page.locator("(//div[@class='imageDivSmall'])[1]");

        folder.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        folder.click();
        Allure.step("First folder clicked in search result.");
        logger.info("First folder clicked in search result.");
    }

    public void search_File_in_SearchBar(String searchTerm, String type) {

        String methodName = Thread.currentThread()
                .getStackTrace()[2]
                .getMethodName();

        System.out.println(methodName);
        Allure.step("Executing search_File_in_SearchBar method.");
        logger.info("Executing search_File_in_SearchBar method.");

        searchFile(searchTerm, type);

        Page viewerPage = openFirstSearchResult(type);

        waitForViewer(viewerPage);

        String viewerUrl = viewerPage.url();

        System.out.println("Viewer URL : " + viewerUrl);
        Allure.step("Viewer URL : " + viewerUrl);
        logger.info("Viewer URL : " + viewerUrl);

        page.waitForTimeout(5000);

        captureViewerScreenshot(viewerPage, searchTerm);

        System.out.println(getViewerName(viewerUrl) + " is opened.");
        Allure.step(getViewerName(viewerUrl) + " is opened.");
        logger.info(getViewerName(viewerUrl) + " is opened.");

        returnToHome(viewerPage);
    }

    public void search_File_Root_Location(String searchTerm, String filetype) {
        String methodName = Thread.currentThread()
                .getStackTrace()[2]
                .getMethodName();

        System.out.println(methodName);
        Allure.step("Executing search_File_Root_Location method.");
        logger.info("Executing search_File_Root_Location method.");

        searchFile(searchTerm, filetype);
        searchoptions("Filename");
        System.out.println("Search Term : " + searchTerm);
        System.out.println("Type : " + filetype);

        click_First_File();

        Allure.step("First file clicked in search result.");
        logger.info("First file clicked in search result.");
        Assert.assertTrue(page.getByTitle("File Location").isVisible(), "Root icon is not visible.");
        System.out.println("File Location is visible.");
        Allure.step("Root icon is visible.");
        logger.info("Root icon is visible.");

        page.getByTitle("File Location").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        System.out.println("Clicked on Root icon.");
        Allure.step("Clicked on Root icon.");
        logger.info("Clicked on Root icon.");
        page.waitForTimeout(5000);

        captureViewerScreenshot(page, searchTerm);
        alfadocklogo();
        page.waitForLoadState();

    }

    public void search_Folder_Root_Location(String searchTerm, String filetype) {

        // ---------------------------------------------------------
        // Method Name
        // ---------------------------------------------------------
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        System.out.println("Executing method: " + methodName);

        Allure.step("Executing " + methodName + " method.");
        logger.info("Executing {} method.", methodName);

        // ---------------------------------------------------------
        // Step 1: Search File / Folder
        // ---------------------------------------------------------
        searchFile(searchTerm, filetype);

        Allure.step("Searched for: " + searchTerm);
        logger.info("Searched for: {}", searchTerm);

        // ---------------------------------------------------------
        // Step 2: Select Filename Search Option
        // ---------------------------------------------------------
        searchoptions("Filename");

        Allure.step("Selected search option: Filename");
        logger.info("Selected search option: Filename");

        // ---------------------------------------------------------
        // Step 3: Click First Folder
        // ---------------------------------------------------------
        click_First_Folder();

        Allure.step("First folder clicked in search result.");
        logger.info("First folder clicked in search result.");

        // ---------------------------------------------------------
        // Step 4: Locate Root / File Location Icon
        // ---------------------------------------------------------
        Locator rootIcon = page.locator("img[src='assets/go-to-location.png']");

        // ---------------------------------------------------------
        // Step 5: Wait for Root Icon
        // ---------------------------------------------------------
        rootIcon.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(rootIcon.isVisible(), "Root/File Location icon is not visible.");

        System.out.println("Root/File Location icon is visible.");

        Allure.step("Root/File Location icon is visible.");
        logger.info("Root/File Location icon is visible.");

        // ---------------------------------------------------------
        // Step 6: Scroll Root Icon into View
        // ---------------------------------------------------------
        rootIcon.scrollIntoViewIfNeeded();

        Allure.step("Root/File Location icon scrolled into view.");
        logger.info("Root/File Location icon scrolled into view.");

        // ---------------------------------------------------------
        // Step 7: Verify Root Icon is Enabled
        // ---------------------------------------------------------
        Assert.assertTrue(
                rootIcon.isEnabled(),
                "Root/File Location icon is not enabled.");

        logger.info("Root/File Location icon is enabled.");

        // ---------------------------------------------------------
        // Step 8: Click Root / File Location Icon
        // ---------------------------------------------------------
        Allure.step("Clicking Root/File Location icon.");
        logger.info("Clicking Root/File Location icon.");

        rootIcon.click(new Locator.ClickOptions()
                .setTimeout(15000));

        System.out.println("Clicked on Root/File Location icon.");

        Allure.step("Root/File Location icon clicked successfully.");
        logger.info("Root/File Location icon clicked successfully.");

        // ---------------------------------------------------------
        // Step 9: Locate Viewer
        // ---------------------------------------------------------
        Locator viewer = page.locator(
                "div.imageDiv, div.imageDivSmall").first();

        // ---------------------------------------------------------
        // Step 10: Wait for Viewer to Become Visible
        // ---------------------------------------------------------
        viewer.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(viewer.isVisible(), "Viewer is not visible after clicking Root/File Location icon.");

        System.out.println("Viewer is visible.");

        Allure.step("Viewer is visible.");
        logger.info("Viewer is visible.");

        // ---------------------------------------------------------
        // Step 11: Capture Viewer Screenshot
        // ---------------------------------------------------------
        captureViewerScreenshot(page, searchTerm);

        Allure.step("Viewer screenshot captured for: " + searchTerm);
        logger.info("Viewer screenshot captured for: {}", searchTerm);

        // ---------------------------------------------------------
        // Step 12: Continue to AlfaDock
        // ---------------------------------------------------------
        alfadocklogo();

        Allure.step("AlfaDock logo operation completed.");
        logger.info("AlfaDock logo operation completed.");

        // ---------------------------------------------------------
        // Step 13: Final Log
        // ---------------------------------------------------------
        System.out.println("search_Folder_Root_Location completed successfully.");

        Allure.step("search_Folder_Root_Location completed successfully.");

        logger.info("search_Folder_Root_Location completed successfully.");
    }

}