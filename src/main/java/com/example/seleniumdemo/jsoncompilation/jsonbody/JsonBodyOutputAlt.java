package com.example.seleniumdemo.jsoncompilation.jsonbody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonBodyOutputAlt {
    private Map<String, Object> inputs;
    private String index;
    private String caseTypeNumberYear;
    private String title;
    private String court;
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
    private String[] petitioners;
    private String[] padvocate;
    private String[] respondents;
    private String[] radvocate;
    private ArrayList<LinkedHashMap<String, String>> acts;
    private ArrayList<LinkedHashMap<String, String>> caseHistory;
    private ArrayList<LinkedHashMap<String, String>> orders;
    private ArrayList<LinkedHashMap<String, String>> process;
    private String lastUpdated;

    public Map<String, Object> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index.replace(")", "");
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

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
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

    public String[] getPetitioners() {
        return petitioners;
    }

    public void setPetitioners(String[] petitioners) {
        this.petitioners = petitioners;
    }

    public String[] getPadvocate() {
        return padvocate;
    }

    public void setPadvocate(String[] padvocate) {
        this.padvocate = padvocate;
    }

    public String[] getRespondents() {
        return respondents;
    }

    public void setRespondents(String[] respondents) {
        this.respondents = respondents;
    }

    public String[] getRadvocate() {
        return radvocate;
    }

    public void setRadvocate(String[] radvocate) {
        this.radvocate = radvocate;
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

    public ArrayList<LinkedHashMap<String, String>> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<LinkedHashMap<String, String>> orders) {
        this.orders = orders;
    }

    public ArrayList<LinkedHashMap<String, String>> getProcess() {
        return process;
    }

    public void setProcess(ArrayList<LinkedHashMap<String, String>> process) {
        this.process = process;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = String.valueOf(java.time.Clock.systemUTC().instant());
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
        temp.put("Petitioners", Arrays.toString(getPetitioners()));
        temp.put("Respondents", Arrays.toString(getRespondents()));

        if (getActs() != null) {
            t = new ArrayList<>(getActs().get(0).values());
            for (int i = 1; i < getActs().size(); i++) {
                t.set(0, (t.get(0) + "\n" + (i + 1) + ") " + getActs().get(i).get("underAct")));
                t.set(1, (t.get(1) + "\n" + (i + 1) + ") " + getActs().get(i).get("underSection")));
            }

            temp.put("Under Act", "1) " + t.get(0));
            temp.put("Under Section", "1) " + t.get(1));
        } else {
            temp.put("Under Act", "");
            temp.put("Under Section", "");
        }

        if (getCaseHistory() != null) {
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
        } else {
            temp.put("Registration Date", "");
            temp.put("Judge", "");
            temp.put("Business on Date", "");
            temp.put("Hearing Date", "");
            temp.put("Purpose of Hearing", "");
        }

        if (getOrders() != null) {
            if (getOrders().size() == 2) {
                t = new ArrayList<>(getOrders().get(0).values());
                for (int i = 1; i < getOrders().size(); i++) {
                    t.set(0, (t.get(0) + "\n" + (i + 1) + ") " + getOrders().get(i).get("date")));
                    t.set(1, (t.get(1) + "\n" + (i + 1) + ") " + getOrders().get(i).get("link")));
                }

                temp.put("Order Date", "1) " + t.get(0));
                temp.put("Order Link", "1) " + t.get(1));
                temp.put("Process ID", "");
                temp.put("Process Date", "");
                temp.put("Process Title", "");
                temp.put("Party Name", "");
                temp.put("Issued Process", "");
            }
            else if (getOrders().size() == 5) {
                t = new ArrayList<>(getOrders().get(0).values());
                for (int i = 1; i < getOrders().size(); i++) {
                    t.set(0, (t.get(0) + "\n" + (i + 1) + ") " + getOrders().get(i).get("processId")));
                    t.set(1, (t.get(1) + "\n" + (i + 1) + ") " + getOrders().get(i).get("processDate")));
                    t.set(2, (t.get(2) + "\n" + (i + 1) + ") " + getOrders().get(i).get("processTitle")));
                    t.set(3, (t.get(3) + "\n" + (i + 1) + ") " + getOrders().get(i).get("partyName")));
                    t.set(4, (t.get(4) + "\n" + (i + 1) + ") " + getOrders().get(i).get("issuedProcess")));
                }

                temp.put("Order Date", "");
                temp.put("Order Link", "");
                temp.put("Process ID", "1) " + t.get(0));
                temp.put("Process Date", "1) " + t.get(1));
                temp.put("Process Title", "1) " + t.get(2));
                temp.put("Party Name", "1) " + t.get(3));
                temp.put("Issued Process", "1) " + t.get(4));
            }
        } else {
            temp.put("Order Date", "");
            temp.put("Order Link", "");
            temp.put("Process ID", "");
            temp.put("Process Date", "");
            temp.put("Process Title", "");
            temp.put("Party Name", "");
            temp.put("Issued Process", "");
        }

        temp.put("Last Updated", getLastUpdated());

        return temp;
    }
}