package com.example.seleniumdemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.example.seleniumdemo.reusable;

public class base {
	reusable rs = new reusable();
	public WebDriver Driver;

	@SuppressWarnings("deprecation")
	public WebDriver driversetup() throws IOException {
		String browser = "chrome"; // rs.getproperty("browser");
		if (browser.equals("chrome")) {
//			ChromeOptions options=new ChromeOptions();
//			options.addArguments("--headless");
//			options.addArguments("window-size=1400,800");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
//			Driver = new ChromeDriver(options);
			Driver = new ChromeDriver(options);
		}

		else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			Driver = new FirefoxDriver();
		}

		else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			Driver = new EdgeDriver();
		}

		Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		Driver.manage().window().maximize();

		return Driver;
	}
}
