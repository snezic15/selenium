package com.example.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MainController {
	@GetMapping("/")
	public String showForm(Model model) {
		Response res = new Response();
		model.addAttribute("response", res);
		
		return "index";
	}
	
	@PostMapping("/process")
	@ResponseBody
	public Map<String, Object> submitForm(@ModelAttribute("response") Response res) throws IOException {
		JSONArray jsonArray = null;
		if (!res.getUrl().toLowerCase().contains("https://") && !res.getUrl().toLowerCase().contains("http://")) res.setUrl("https://" + res.getUrl());

		URL url = new URL(res.getUrl());
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		int statusCode;

		try {
			statusCode = http.getResponseCode();
		} catch (Exception e) {
			statusCode = 500;
		}

		res.setResponse(statusCode);

		if (statusCode == 200) {

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
		} else res.setSuccess("Failure!");

		System.out.println(res);

		JSONObject j = new JSONObject();
		j.put("Temp", jsonArray);

		return j.toMap();
	}
}
