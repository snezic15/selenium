package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;

public class SearchPageCauseList {
    public static final By courtComplex = By.xpath("//*[@id=\"radCourtComplex\"]");
    public static final By courtEstablishment = By.xpath("//*[@id=\"radCourtEst\"]");
    public static final By complexStatus = By.xpath("//*[@id=\"court_complex_code\"]");
    public static final By establishmentStatus = By.xpath("//*[@id=\"court_code\"]");
    public static final By courtName = By.xpath("//*[@id=\"court_no\"]");
    public static final By causeListDate = By.xpath("//*[@id=\"causelist_date\"]");
    public static final By calendar = By.xpath("//*[@id=\"caseNoDet\"]/div[2]/div/div/div[4]/span[3]/img");

    public static final By captcha = By.xpath("//*[@id=\"captcha_image\"]");
    public static final By captchaText = By.xpath("//*[@id=\"captcha\"]");
    public static final By captchaReset = By.xpath("//*[@id=\"captcha_container_2\"]/div[1]/div/span[3]/a/img");

    public static final By civilSearch = By.xpath("//*[@id=\"butCivil\"]");
    public static final By criminalSearch = By.xpath("//*[@id=\"butCriminal\"]");
    public static final By error = By.xpath("//*[@id=\"txtmsg\"]");

    public static final By viewTab = By.xpath("//*[@id=\"firstpage\"]/table");
    public static final By viewTabs = By.xpath("//*[@id=\"firstpage\"]/table/tbody");
}