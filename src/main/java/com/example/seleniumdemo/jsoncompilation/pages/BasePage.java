package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;
public class BasePage {
	public static final By state = By.xpath("//*[@id=\"block-views-india-block\"]/div/div/div/ul");
	public static final By district = By.xpath("//*[@id=\"map-position-list\"]/div/div/div[2]/div/div/div/ul");

	public static final By caseStatus = By.xpath("//*[@id='block-block-8']/div[1]/button[1]");
	public static final By caseNo = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[1]/a");
	public static final By partyName = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[3]/a");
	public static final By advocateName = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[1]/ul/li[4]/a");

	public static final By causeListButton = By.xpath("//*[@id=\"block-block-8\"]/div[1]/button[3]");
	public static final By causeList = By.xpath("//*[@id=\"block-block-8\"]/div[1]/div[3]/ul/li/a");

	public static final By loading = By.xpath("//*[@id=\"divWait\"]");
	public static final By back = By.xpath("//*[@id=\"back_top\"]/center/a");
}