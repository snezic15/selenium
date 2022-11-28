package com.example.seleniumdemo.jsoncompilation.jsonbody;

import java.util.LinkedHashMap;

public class JsonBodyOutputToCaseNumber {
    private String index;
    private String title;
    private String court;
    private LinkedHashMap<String, String> caseNumber;
    private String lastUpdated;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index.replace(")", "");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replace("\n", " ");
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public LinkedHashMap<String, String> getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(LinkedHashMap<String, String> caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = String.valueOf(java.time.Clock.systemUTC().instant());
    }
}