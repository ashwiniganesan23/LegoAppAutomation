package legoApp.driverfactory;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
	public WebDriver driver;
	ThreadLocal<WebDriver> tlDriver= new ThreadLocal<WebDriver>();


	
	public ThreadLocal<WebDriver> launchBrowser(String browserName) throws IOException {


		if (browserName.contains("chrome")) {

			ChromeOptions options = new ChromeOptions();
			//options.addArguments ("--headless");
			//options.addArguments ("--start-maximized");
			HashMap<String, Object> chromePref = new HashMap<String, Object>();
			Map<String, Object> langs = new HashMap<String, Object>();
			langs.put("es", "en");
			chromePref.put("profile.default_content_settings.popups", 0);
			chromePref.put ( "translate_whitelists", langs);
			chromePref.put("translate", "{'enabled' : true}");
			options.setExperimentalOption("prefs", chromePref);
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(options));
		}
		else if (browserName.contains("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
		}
		else {
			System.out.println("Please check browser has been mentioned in config");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

		return tlDriver;


	}
	
	public WebDriver getDriver() {
		return  tlDriver.get();
	}
	

}
