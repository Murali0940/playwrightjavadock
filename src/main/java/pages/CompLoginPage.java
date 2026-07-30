package pages;

import com.microsoft.playwright.*;

public class CompLoginPage {

    private Page page;

    private Locator txtUsername;
    private Locator txtPassword;
    private Locator btnLogin;

    public CompLoginPage(Page page){

        this.page=page;

        txtUsername=page.locator("#username");

        txtPassword=page.locator("#password");

        btnLogin=page.locator("#logmein");

    }

    public UserLoginPage companyLogin(String user,String pass){

        txtUsername.fill(user);

        txtPassword.fill(pass);

        btnLogin.click();

        return new UserLoginPage(page);

    }

}