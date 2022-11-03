package com.example.seleniumdemo.jsoncompilation.jsonbody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonBodyOutputExcel {
    private String index;
    private String caseTypeNumberYear;
    private String title;
    private String filingNumber;
    private String filingDate;
    private String registrationNumber;
    private String registrationDate;
    private String cnr;
    private String firstHearing;
    private String decisionDate;
    private String status;
    private String disposalNature;
    private String courtNo;
    private String petitioners;
    private String respondents;
    private ArrayList<LinkedHashMap<String, String>> acts;
    private ArrayList<LinkedHashMap<String, String>> caseHistory;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCaseTypeNumberYear() {
        return caseTypeNumberYear;
    }

    public void setCaseTypeNumberYear(String caseTypeNumberYear) {
        this.caseTypeNumberYear = caseTypeNumberYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replace("\n", " ");
    }

    public String getFilingNumber() {
        return filingNumber;
    }

    public void setFilingNumber(String filingNumber) {
        this.filingNumber = filingNumber.split(": ")[1].split("F")[0];
    }

    public String getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate.split(": ")[1];
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber.split(": ")[1];
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate.split(": ")[1];
    }

    public String getCnr() {
        return cnr;
    }

    public void setCnr(String cnr) {
        this.cnr = cnr.split(": ")[1];
    }

    public String getFirstHearing() {
        return firstHearing;
    }

    public void setFirstHearing(String firstHearing) {
        this.firstHearing = firstHearing.split(": ")[1];
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate.split(": ")[1];
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status.split(": ")[1];
    }

    public String getDisposalNature() {
        return disposalNature;
    }

    public void setDisposalNature(String disposalNature) {
        if (disposalNature.split(": ")[1].equalsIgnoreCase("Case Disposed"))
            this.disposalNature = disposalNature.split(": ")[1];
        else this.disposalNature = "N/A";
    }

    public String getCourtNo() {
        return courtNo;
    }

    public void setCourtNo(String courtNo) {
        this.courtNo = courtNo.split(": ")[1];
    }

    public String getPetitioners() {
        return petitioners;
    }

    public void setPetitioners(String petitioners) {
        this.petitioners = petitioners.replace("\n", " ");
    }

    public String getRespondents() {
        return respondents;
    }

    public void setRespondents(String respondents) {
        this.respondents = respondents.replace("\n", " ");
    }

    public ArrayList<LinkedHashMap<String, String>> getActs() {
        return acts;
    }

    public void setActs(ArrayList<LinkedHashMap<String, String>> acts) {
        this.acts = acts;
    }

    public ArrayList<LinkedHashMap<String, String>> getCaseHistory() {
        return caseHistory;
    }

    public void setCaseHistory(ArrayList<LinkedHashMap<String, String>> caseHistory) {
        this.caseHistory = caseHistory;
    }

    public LinkedHashMap<String, String> getMap() {
        LinkedHashMap<String, String> temp = new LinkedHashMap<>();
        ArrayList<String> t;

        temp.put("Index", getIndex());
        temp.put("Case Type/Case Number/Case Year", getCaseTypeNumberYear());
        temp.put("Case Title", getTitle());
        temp.put("Filing Number", getFilingNumber());
        temp.put("Filing Date", getFilingDate());
        temp.put("Registration Number", getRegistrationNumber());
        temp.put("Registration Date", getRegistrationDate());
        temp.put("CNR Number", getCnr());
        temp.put("First Hearing", getFirstHearing());
        temp.put("Decision Date", getDecisionDate());
        temp.put("Status", getStatus());
        temp.put("Disposal Nature", getDisposalNature());
        temp.put("Court No and Judge", getCourtNo());
        temp.put("Petitioners", getPetitioners());
        temp.put("Respondents", getRespondents());

        t = new ArrayList<>(getActs().get(0).values());
        for (int i = 1; i < getActs().size(); i++) {
            t.set(0, (t.get(0) + "\n" + (i + 1) + ") " + getActs().get(i).get("underAct")));
            t.set(1, (t.get(1) + "\n" + (i + 1) + ") " + getActs().get(i).get("underSection")));
        }

        temp.put("Under Act", "1) " + t.get(0));
        temp.put("Under Section", "1) " + t.get(1));

        t = new ArrayList<>(getCaseHistory().get(0).values());
        for (int i = 1; i < getCaseHistory().size(); i++) {
           t.set(0, (t.get(0) + "\n" + (i + 1) + ") " + getCaseHistory().get(i).get("registrationNumber")));
           t.set(1, (t.get(1) + "\n" + (i + 1) + ") " + getCaseHistory().get(i).get("judge")));
           t.set(2, (t.get(2) + "\n" + (i + 1) + ") " + getCaseHistory().get(i).get("businessOnDate")));
           t.set(3, (t.get(3) + "\n" + (i + 1) + ") " + getCaseHistory().get(i).get("hearingDate")));
           t.set(4, (t.get(4) + "\n" + (i + 1) + ") " + getCaseHistory().get(i).get("purposeOfHearing")));
        }

        temp.put("Registration Date", "1) " + t.get(0));
        temp.put("Judge", "1) " + t.get(1));
        temp.put("Business on Date", "1) " + t.get(2));
        temp.put("Hearing Date", "1) " + t.get(3));
        temp.put("Purpose of Hearing", "1) " + t.get(4));

        return temp;
    }
}