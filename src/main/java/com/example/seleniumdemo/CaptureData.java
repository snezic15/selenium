package com.example.seleniumdemo;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.example.seleniumdemo.reusableTest;

public class CaptureData {

	// Table 1 ie Case details
	public static ArrayList<String> Casetype = new ArrayList<String>();
	static ArrayList<String> FilingNumber = new ArrayList<String>();
	static ArrayList<String> FilingDate = new ArrayList<String>();
	static ArrayList<String> RegistrationNumber = new ArrayList<String>();
	static ArrayList<String> RegistrationDate = new ArrayList<String>();
	static ArrayList<String> CNRNumber = new ArrayList<String>();
	// Table 2 ie Case Status
	static ArrayList<String> FirstHearingDate = new ArrayList<String>();
	static ArrayList<String> NextHearingDate = new ArrayList<String>();
	static ArrayList<String> CaseStage = new ArrayList<String>();
	static ArrayList<String> NatureofDisposal = new ArrayList<String>();
	static ArrayList<String> CourtNumberandJudge = new ArrayList<String>();
	// table 3 ie Petitioner and Advocate
	static ArrayList<String> PetitionerandAdvocate = new ArrayList<String>();
	// Table 4 ie Respondent and Advocate
	static ArrayList<String> RespondentandAdvocate = new ArrayList<String>();
	// Table 5 Acts
	static ArrayList<String> UnderAct = new ArrayList<String>();
	static ArrayList<String> UnderSection = new ArrayList<String>();
	// Table 5 Case History
	static ArrayList<String> CaseHistory = new ArrayList<String>();

	public static void setCasetype(ArrayList<String> casetype) {
		Casetype = casetype;
	}

	public static void setFilingNumber(ArrayList<String> filingNumber) {
		FilingNumber = filingNumber;
	}

	public static void setFilingDate(ArrayList<String> filingDate) {
		FilingDate = filingDate;
	}

	public static void setRegistrationNumber(ArrayList<String> registrationNumber) {
		RegistrationNumber = registrationNumber;
	}

	public static void setRegistrationDate(ArrayList<String> registrationDate) {
		RegistrationDate = registrationDate;
	}

	public static void setCNRNumber(ArrayList<String> cNRNumber) {
		CNRNumber = cNRNumber;
	}

	public static void setFirstHearingDate(ArrayList<String> firstHearingDate) {
		FirstHearingDate = firstHearingDate;
	}

	public static void setNextHearingDate(ArrayList<String> nextHearingDate) {
		NextHearingDate = nextHearingDate;
	}

	public static void setCaseStage(ArrayList<String> caseStage) {
		CaseStage = caseStage;
	}

	public static void setCourtNumberandJudge(ArrayList<String> courtNumberandJudge) {
		CourtNumberandJudge = courtNumberandJudge;
	}

	public static void setPetitionerandAdvocate(ArrayList<String> petitionerandAdvocate) {
		PetitionerandAdvocate = petitionerandAdvocate;
	}

	public static void setRespondentandAdvocate(ArrayList<String> respondentandAdvocate) {
		RespondentandAdvocate = respondentandAdvocate;
	}

	public static void setUnderAct(ArrayList<String> underAct) {
		UnderAct = underAct;
	}

	public static void setUnderSection(ArrayList<String> underSection) {
		UnderSection = underSection;
	}

	public static void setCaseHistory(ArrayList<String> caseHistory) {
		CaseHistory = caseHistory;
	}

	public static ArrayList<String> getCaseHistory() {
		return CaseHistory;
	}

	public static ArrayList<String> getPetitionerandAdvocate() {
		return PetitionerandAdvocate;
	}

	public static ArrayList<String> getCasetype() {
		return Casetype;
	}

	public static ArrayList<String> getFilingNumber() {
		return FilingNumber;
	}

	public static ArrayList<String> getFilingDate() {
		return FilingDate;
	}

	public static ArrayList<String> getRegistrationNumber() {
		return RegistrationNumber;
	}

	public static ArrayList<String> getRegistrationDate() {
		return RegistrationDate;
	}

	public static ArrayList<String> getCNRNumber() {
		return CNRNumber;
	}

	public static ArrayList<String> getFirstHearingDate() {
		return FirstHearingDate;
	}

	public static ArrayList<String> getNextHearingDate() {
		return NextHearingDate;
	}

	public static ArrayList<String> getCaseStage() {
		return CaseStage;
	}

	public static ArrayList<String> getCourtNumberandJudge() {
		return CourtNumberandJudge;
	}

	public static ArrayList<String> getRespondentandAdvocate() {
		return RespondentandAdvocate;
	}

	public static ArrayList<String> getUnderAct() {
		return UnderAct;
	}

	public static ArrayList<String> getUnderSection() {
		return UnderSection;
	}

	public static ArrayList<String> getNatureofDisposal() {
		return NatureofDisposal;
	}

	public static void setNatureofDisposal(ArrayList<String> natureofDisposal) {
		NatureofDisposal = natureofDisposal;
	}

	public static void readfromWebpage(WebDriver Driver) {
		reusableTest.captureCaseDetails(Driver);
		reusableTest.captureCaseStatus(Driver);
		reusableTest.capturePetitionerandAdvocate(Driver);
		reusableTest.captureRespondentandAdvocate(Driver);
		reusableTest.captureActs(Driver);
		reusableTest.captureHistory(Driver);

	}

}
