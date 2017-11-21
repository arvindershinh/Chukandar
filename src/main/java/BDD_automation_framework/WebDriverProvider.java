package BDD_automation_framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class WebDriverProvider {
    private WebDriver webDriver;
    public WebDriverProvider(String Browser) throws IOException {

        switch (Browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Workspace\\ClojureProject\\Chukandar\\src\\main\\browserDrivers\\chromedriver.exe");
                webDriver = new ChromeDriver();
                break;
            case "IE":
                System.out.println("Arv wow");
                break;
            case "Firefox":
                break;
        }
    }
    public WebDriver getWebDriver() {
        return webDriver;
    }


    
}
