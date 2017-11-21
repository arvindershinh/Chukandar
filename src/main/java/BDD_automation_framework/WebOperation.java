package BDD_automation_framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class WebOperation {

    private WebDriverProvider webDriverProvider;
    public WebOperation(String Browser) throws IOException {
        webDriverProvider = new WebDriverProvider(Browser);
    }

    public void get(String url){
        webDriverProvider.getWebDriver().get(url);
    }

    public void sendKey(String elementProperty, String input){
        webDriverProvider.getWebDriver().findElement(By.name(elementProperty)).sendKeys(input);
    }

    public void click(String elementProperty){
        webDriverProvider.getWebDriver().findElement(By.name(elementProperty)).click();
    }


}
