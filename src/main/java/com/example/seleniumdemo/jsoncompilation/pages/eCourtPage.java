package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;

public class eCourtPage {
	public static final By cnrSearch = By.xpath("//*[@id=\"cino\"]");
	public static final By search = By.xpath("//*[@id=\"searchbtn\"]");
	public static final By validate = By.xpath("//*[@id=\"history_cnr\"]/h3[1]");

	public static final By captcha = By.xpath("//*[@id=\"captcha_image\"]");
	public static final By captchaText = By.xpath("//*[@id=\"fcaptcha_code\"]");

	public static final By error = By.xpath("//*[@id=\"validateError\"]/div/div/div[2]/div/div[1]");
	public static final By closeError = By.xpath("//*[@id=\"validateError\"]/div/div/div[1]/button");
	public static final By back = By.xpath("//*[@id=\"main_back_cnr\"]");

	//Case Page
	//Misc.
	public static final By stateDistrict = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[4]/td[3]/a");

	//QR Code
	public static final By qr = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[4]/td[3]/a");
	public static final By caseTypeNumberYear = By.xpath("//*[@id=\"caseBusinessDiv\"]/table/tbody/tr[4]/td[3]");
	public static final By plaintiff = By.xpath("//*[@id=\"caseBusinessDiv\"]/table/tbody/tr[6]/td[3]");
	public static final By defendant = By.xpath("//*[@id=\"caseBusinessDiv\"]/table/tbody/tr[6]/td[6]");
	public static final By qrClose = By.xpath("//*[@id=\"modal-ack\"]/div/div/div[1]/button");

	//Table 1
	public static final By courtName = By.xpath("//*[@id=\"chHeading\"]");
	public static final By caseType = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[1]/td[2]");
	public static final By filingNumber = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[2]/td[2]");
	public static final By registrationNumber = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[3]/td[2]/label");
	public static final By cnr = By.xpath("//*[@id=\"history_cnr\"]/table[1]/tbody/tr[4]/td[2]/span");

	//Table 2
	public static final By table2 = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr");
	public static final By decisionDate = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr[2]/td[2]/strong");
	public static final By status = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr[3]/td[2]");
	public static final By disposalNature = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr[4]/td[2]/label/strong");
	public static final By courtNo = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr[4]/td[2]");
	public static final By courtNo2 = By.xpath("//*[@id=\"history_cnr\"]/table[2]/tbody/tr[5]/td[2]");

	//Table 3 & 4
	public static final By petitioners = By.xpath("//*[@id=\"history_cnr\"]/table[3]/tbody/tr/td");
	public static final By respondents = By.xpath("//*[@id=\"history_cnr\"]/table[4]/tbody/tr/td");

	//Table 5
	public static final By act = By.xpath("//*[@id=\"act_table\"]/tbody/tr[2]/td[1]");
	public static final By section = By.xpath("//*[@id=\"act_table\"]/tbody/tr[2]/td[2]");

	//Table 6
	public static final By caseHistory = By.xpath("//*[@class=\"history_table table \"]/tbody/tr");
	public static final By business = By.xpath("//*[@id=\"mydiv\"]/center/center[7]/table/tbody/tr");
	public static final By businessBack = By.xpath("//*[@id=\"caseBusinessDiv_back\"]");

	//Table 7
	public static final By orders = By.xpath("//*[@class=\"order_table table \"]/tbody/tr");

	//Table 8
	public static final By ia = By.xpath("//*[@class=\"IAheading\"]/tbody/tr");
}