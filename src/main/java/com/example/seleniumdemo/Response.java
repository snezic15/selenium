package com.example.seleniumdemo;

public class Response {
	private String success = "Success!";
	private String url = "https://jsonplaceholder.typicode.com/";
	private int response = 0;
	private String json = "";

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "[Success: " + getSuccess() + ", URL: " + getUrl() + ", Response: " + getResponse() + ", JSON Dump: [" + getJson() + "]]";
	}
}
