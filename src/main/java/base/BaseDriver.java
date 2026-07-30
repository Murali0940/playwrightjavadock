package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.microsoft.playwright.*;

import utils.ConfigReader;

public class BaseDriver {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    public Page page;

    @BeforeClass

    public void setup(){

        playwright=Playwright.create();

        browser=playwright.chromium()

                .launch(new BrowserType.LaunchOptions()

                        .setHeadless(Boolean.parseBoolean(ConfigReader.get("headless")))

                        .setSlowMo(Double.parseDouble(ConfigReader.get("slowmo"))));

        context=browser.newContext();

        page=context.newPage();

        page.navigate(ConfigReader.get("base.url"));
        
        //page.pause(); //playwright inspector will open and you can debug your test 

    }

    @AfterClass

    public void teardown(){

        context.close();

        browser.close();

        playwright.close();

    }

}