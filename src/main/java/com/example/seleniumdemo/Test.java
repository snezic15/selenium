package com.example.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Test {



    public Response trigger(Response res) throws MalformedURLException {
        JSONArray jsonArray = null;

        URL url = new URL(res.getUrl());

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //		options.setBinary("/app/.apt/usr/bin/google-chrome");

        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to(url);
        driver.findElement(By.xpath("/html/body/div/main/table[1]/tbody/tr[1]/td[1]/a")).click();

        jsonArray = new JSONArray(driver.findElement(By.cssSelector("pre")).getText());
        res.setJson(jsonArray.toString());
        res.setSuccess("Success!");

        driver.quit();

        return res;

    }


}
