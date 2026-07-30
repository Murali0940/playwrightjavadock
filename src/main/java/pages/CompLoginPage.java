package pages;

import com.microsoft.playwright.*;

public class CompLoginPage {

    private Page page;

    private Locator txtUsername;
    private Locator txtPassword;
    private Locator btnLogin;

    public CompLoginPage(Page page) {

        this.page = page;

        txtUsername = page.locator("#username");

        txtPassword = page.locator("#password");

        btnLogin = page.locator("#logmein");

    }

    public UserLoginPage companyLogin(String user, String pass) {

        txtUsername.fill(user);
        System.out.println("Company username is: " + user);

        txtPassword.fill(pass);
        System.out.println("Company password is: " + pass);

        btnLogin.click();
        System.out.println("Company login button clicked.");

        return new UserLoginPage(page);

    }

}