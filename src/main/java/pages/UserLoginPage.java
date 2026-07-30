package pages;

import org.testng.Assert;

import com.microsoft.playwright.*;

public class UserLoginPage {

    private Page page;

    private Locator txtUsername1;

    private Locator txtPassword1;

    private Locator btnLogin1;

    private Locator btnLogout1;

    public UserLoginPage(Page page) {

        this.page = page;

        txtUsername1 = page.locator("#username");

        txtPassword1 = page.locator("#password");

        btnLogin1 = page.locator("#login");

        btnLogout1 = page.locator("#logout");

    }

    public Homepage userLogin(String user, String pass) {

        txtUsername1.fill(user);

        txtPassword1.fill(pass);

        btnLogin1.click();

        return new Homepage(page);

    }

    public void userLogout() {
        btnLogout1.click();
    }

    public void verifyPageURL() {

        page.waitForLoadState();

        String actualURL = page.url();
        String expectedURL = "https://www.alfadock-pack.com/userlogin.html";

        System.out.println("Actual URL is: " + actualURL);

        Assert.assertTrue(
                actualURL.contains(expectedURL),
                "Expected title to contain '" + expectedURL +
                        "' but found '" + actualURL + "'");
    }

}