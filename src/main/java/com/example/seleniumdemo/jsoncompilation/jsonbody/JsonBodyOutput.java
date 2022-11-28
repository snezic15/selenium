package com.example.seleniumdemo.jsoncompilation.jsonbody;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonBodyOutput {
    private String index = "";
    private String title = "";
    private String listingDate = "";
    private String status = "";
    private String disposalNature = "";
    private String listingStage = "";
    private String courtNo = "";
    private String[] petitioners = new String[0];
    private String[] respondents = new String[0];
    private String[] padvocates = new String[0];
    private String[] radvocates = new String[0];
    private ArrayList<LinkedHashMap<String, String>> orders = new ArrayList<>();
    private ArrayList<LinkedHashMap<String, String>> process = new ArrayList<>();
    private ArrayList<LinkedHashMap<String, String>> listingDates = new ArrayList<>();
    private String act = "";
    private String section = "";
    private String[] officeReports = new String[0];
    private String courtName = "";
    private String cn = "";
    private String cy = "";
    private String state_val = "";
    private String district_val = "";
    private String bench_val = "";
    private String cino = "";
    private String courtID = "";
    private String caseTypeStr = "";
    private String file_no = "";
    private String file_year = "";
    private ArrayList<LinkedHashMap<String, String>> IA = new ArrayList<>();
    private String[] listingJudges = new String[0];
    private String[] judgements = new String[0];
    private String[] history = new String[0];
    private String[] taggedMatters = new String[0];
    private String[] caveats = new String[0];
    private String listingDateSource = "";
    private String listingRoom = "";
    private String caseNoString = "";
    private String[] listingAdvocates = new String[0];
    private LinkedHashMap<String, String> listing = new LinkedHashMap<>();
    private String[] causeLists = new String[0];
    private LinkedHashMap<String, String> statusGroup = new LinkedHashMap<>();
    private String lastUpdated = "";

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisposalNature() {
        return disposalNature;
    }

    public void setDisposalNature(String disposalNature) {
        this.disposalNature = disposalNature;
    }

    public String getListingStage() {
        return listingStage;
    }

    public void setListingStage(String listingStage) {
        this.listingStage = listingStage;
    }

    public String getCourtNo() {
        return courtNo;
    }

    public void setCourtNo(String courtNo) {
        this.courtNo = courtNo;
    }

    public String[] getPetitioners() {
        return petitioners;
    }

    public void setPetitioners(String[] petitioners) {
        this.petitioners = petitioners;
    }

    public String[] getRespondents() {
        return respondents;
    }

    public void setRespondents(String[] respondents) {
        this.respondents = respondents;
    }

    public String[] getPadvocates() {
        return padvocates;
    }

    public void setPadvocates(String[] padvocates) {
        this.padvocates = padvocates;
    }

    public String[] getRadvocates() {
        return radvocates;
    }

    public void setRadvocates(String[] radvocates) {
        this.radvocates = radvocates;
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

    public ArrayList<LinkedHashMap<String, String>> getListingDates() {
        return listingDates;
    }

    public void setListingDates(ArrayList<LinkedHashMap<String, String>> listingDates) {
        this.listingDates = listingDates;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String[] getOfficeReports() {
        return officeReports;
    }

    public void setOfficeReports(String[] officeReports) {
        this.officeReports = officeReports;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getState_val() {
        return state_val;
    }

    public void setState_val(String state_val) {
        this.state_val = state_val;
    }

    public String getDistrict_val() {
        return district_val;
    }

    public void setDistrict_val(String district_val) {
        this.district_val = district_val;
    }

    public String getBench_val() {
        return bench_val;
    }

    public void setBench_val(String bench_val) {
        this.bench_val = bench_val;
    }

    public String getCino() {
        return cino;
    }

    public void setCino(String cino) {
        this.cino = cino;
    }

    public String getCourtID() {
        return courtID;
    }

    public void setCourtID(String courtID) {
        this.courtID = courtID;
    }

    public String getCaseTypeStr() {
        return caseTypeStr;
    }

    public void setCaseTypeStr(String caseTypeStr) {
        this.caseTypeStr = caseTypeStr;
    }

    public String getFile_no() {
        return file_no;
    }

    public void setFile_no(String file_no) {
        this.file_no = file_no;
    }

    public String getFile_year() {
        return file_year;
    }

    public void setFile_year(String file_year) {
        this.file_year = file_year;
    }

    public ArrayList<LinkedHashMap<String, String>> getIA() {
        return IA;
    }

    public void setIA(ArrayList<LinkedHashMap<String, String>> IA) {
        this.IA = IA;
    }

    public String[] getListingJudges() {
        return listingJudges;
    }

    public void setListingJudges(String[] listingJudges) {
        this.listingJudges = listingJudges;
    }

    public String[] getJudgements() {
        return judgements;
    }

    public void setJudgements(String[] judgements) {
        this.judgements = judgements;
    }

    public String[] getHistory() {
        return history;
    }

    public void setHistory(String[] history) {
        this.history = history;
    }

    public String[] getTaggedMatters() {
        return taggedMatters;
    }

    public void setTaggedMatters(String[] taggedMatters) {
        this.taggedMatters = taggedMatters;
    }

    public String[] getCaveats() {
        return caveats;
    }

    public void setCaveats(String[] caveats) {
        this.caveats = caveats;
    }

    public String getListingDateSource() {
        return listingDateSource;
    }

    public void setListingDateSource(String listingDateSource) {
        this.listingDateSource = listingDateSource;
    }

    public String getListingRoom() {
        return listingRoom;
    }

    public void setListingRoom(String listingRoom) {
        this.listingRoom = listingRoom;
    }

    public String getCaseNoString() {
        return caseNoString;
    }

    public void setCaseNoString(String caseNoString) {
        this.caseNoString = caseNoString;
    }

    public String[] getListingAdvocates() {
        return listingAdvocates;
    }

    public void setListingAdvocates(String[] listingAdvocates) {
        this.listingAdvocates = listingAdvocates;
    }

    public LinkedHashMap<String, String> getListing() {
        return listing;
    }

    public void setListing(LinkedHashMap<String, String> listing) {
        this.listing = listing;
    }

    public String[] getCauseLists() {
        return causeLists;
    }

    public void setCauseLists(String[] causeLists) {
        this.causeLists = causeLists;
    }

    public LinkedHashMap<String, String> getStatusGroup() {
        return statusGroup;
    }

    public void setStatusGroup(LinkedHashMap<String, String> statusGroup) {
        this.statusGroup = statusGroup;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = String.valueOf(java.time.Clock.systemUTC().instant());
    }
}