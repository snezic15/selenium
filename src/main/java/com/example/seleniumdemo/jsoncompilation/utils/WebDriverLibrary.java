package com.example.seleniumdemo.jsoncompilation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class WebDriverLibrary {
	public static WebDriver getDriver(String browser) throws Exception {
		WebDriver driver;

		try {
			switch (browser.toLowerCase()) {
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					break;
				default:
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--no-sandbox");
					options.addArguments("--headless");
					options.addArguments("--disable-dev-shm-usage");
					driver = new ChromeDriver(options);
			}

			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
			driver.manage().window().maximize();

			return driver;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new Exception("Web Driver Setup Error: " + e);
		}
	}
}