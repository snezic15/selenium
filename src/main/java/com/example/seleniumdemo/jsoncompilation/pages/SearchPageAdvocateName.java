package com.example.seleniumdemo.jsoncompilation.pages;

import org.openqa.selenium.By;

public class SearchPageAdvocateName {
    public static final By courtComplex = By.xpath("//*[@id=\"radCourtComplex\"]");
    public static final By courtEstablishment = By.xpath("//*[@id=\"radCourtEst\"]");

    public static final By complexStatus = By.xpath(".//select[@name='court_complex_code']");
    public static final By establishmentStatus = By.xpath("//*[@id=\"court_code\"]");

    public static final By searchAdvocate = By.xpath("//*[@id=\"radAdvtName\"]");
    public static final By advocate = By.xpath("//*[@id=\"advocate_name\"]");

    public static final By searchBarCode = By.xpath("//*[@id=\"radAdvtBarCode\"]");
    public static final By barState = By.xpath("//*[@id=\"adv_bar_state\"]");
    public static final By barCode = By.xpath("//*[@id=\"adv_bar_code\"]");
    public static final By barYear = By.xpath("//*[@id=\"adv_bar_year\"]");

    public static final By codePending = By.xpath("//*[@id=\"radP\"]");
    public static final By codeDisposed = By.xpath("//*[@id=\"radD\"]");
    public static final By codeBoth = By.xpath("//*[@id=\"radB\"]");

    public static final By searchDateCaseList = By.xpath("//*[@id=\"radCourtEst\"]");
    public static final By dateCaseListCal = By.xpath("//*[@id=\"caselist_date\"]");

    public static final By captcha = By.xpath("//*[@id=\"captcha_image\"]");
    public static final By captchaText = By.xpath("//*[@id=\"captcha\"]");
    public static final By captchaReset = By.xpath("//*[@id=\"captcha_container_2\"]/div[1]/div/span[3]/a/img");

    public static final By search = By.xpath("//*[@id=\"caseNoDet\"]/div[2]/div[9]/span[3]/input[1]");
    public static final By viewTab = By.xpath("//*[@id=\"showList1\"]/tr/td/a");
    public static final By error = By.xpath("//*[@id=\"errSpan\"]/p");
}