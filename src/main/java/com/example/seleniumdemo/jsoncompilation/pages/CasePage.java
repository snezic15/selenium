package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;

public class CasePage {
	//Table 1
	public static final By courtName = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/span[2]");
	public static final By filingNumber = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/span[3]");
	public static final By filingDate = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/span[3]/span[2]");
	public static final By registrationNumber = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/span[4]/label");
	public static final By registrationDate = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/span[4]/span[2]/label[2]");
	public static final By cnr = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[1]/b/span");

	//Table 2
	public static final By table2 = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span");
	public static final By firstHearing = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[1]/label/strong[2]");
	public static final By decisionDate = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[2]/label/strong[2]");
	public static final By status = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[3]/label/strong[2]");
	public static final By disposalNature = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[4]/label/strong[2]");
	public static final By courtNo = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[4]/label/strong[2]");
	public static final By courtNo2 = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[2]/span[5]/label/strong[2]");

	//Table 3 & 4
	public static final By petitioners = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[3]/span[1]");
	public static final By respondents = By.xpath("//*[@id=\"secondpage\"]/div[2]/div[3]/span[2]");

	//Table 5
	public static final By acts = By.xpath("//*[@id=\"act_table\"]/tbody/tr/td[1]");
	public static final By act = By.xpath("//*[@id=\"act_table\"]/tbody/tr[2]/td[1]");
	public static final By section = By.xpath("//*[@id=\"act_table\"]/tbody/tr[2]/td[2]");

	//Table 6
	public static final By caseHistory = By.xpath("//*[@class=\"history_table\"]/tbody/tr/td[1]");
	public static final By business = By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7]/table/tbody/tr");
	public static final By businessBack = By.xpath("//*[@id=\"back_business\"]/center/a");

	//Table 7
	public static final By ordersName = By.xpath("//*[@id=\"orderheading\"]/tbody/tr/td/h2");
	public static final By orders = By.xpath("//*[@id=\"history_cnr\"]/table[7]/tbody/tr[2]");
}