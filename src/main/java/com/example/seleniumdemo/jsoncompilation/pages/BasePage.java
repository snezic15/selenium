package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;
public class BasePage {
	public static final By caseStatus = By.xpath("//*[@id='block-block-8']/div[1]/button[1]");
	public static final By caseNo = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[1]/a");
	public static final By advocateName = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[4]/a");
	public static final By back = By.xpath("//*[@id=\"back_top\"]/center/a");
}