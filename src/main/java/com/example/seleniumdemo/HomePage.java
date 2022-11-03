package com.example.seleniumdemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	public WebDriver Driver;

	@FindBy(xpath = ".//a[@title='Case Type']")
	WebElement CaseType;
	@FindBy(xpath = "//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[1]/a")
	WebElement CaseNo;
	@FindBy(xpath = "//*[@i d='block-block-8']/div[1]/button[1]")
	WebElement CaseStatus;

	public WebElement getCaseType() {
		return CaseType;
	}

	public WebElement getCaseStatus() {
		return CaseStatus;
	}

	public WebElement getCaseNo() {
		return CaseNo;
	}

	public HomePage(WebDriver driver) {
		this.Driver = driver;
		PageFactory.initElements(driver, this);
	}

}
