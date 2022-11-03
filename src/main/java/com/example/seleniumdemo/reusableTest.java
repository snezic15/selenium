package com.example.seleniumdemo;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.example.seleniumdemo.CaseStatus;
import com.example.seleniumdemo.CaptureData;

public class reusableTest {
	public static void captureCaseDetails(WebDriver Driver) {
		CaseStatus cs = new CaseStatus(Driver);
		ArrayList<String> Casetype = new ArrayList<String>();
		ArrayList<String> FilingNumber = new ArrayList<String>();
		ArrayList<String> FilingDate = new ArrayList<String>();
		ArrayList<String> RegistrationNumber = new ArrayList<String>();
		ArrayList<String> RegistrationDate = new ArrayList<String>();
		ArrayList<String> CNRNumber = new ArrayList<String>();
		// capture details from table 1 ie Case Details
		Casetype.add(cs.getCasetype().getText().split(": ")[1]);
		FilingNumber.add(cs.getFilingNumber().getText().split(": ")[1].split("F")[0]);
		FilingDate.add(cs.getFilingDate().getText().split(": ")[1]);
		RegistrationNumber.add(cs.getRegistrationNumber().getText().split(": ")[1]);
		RegistrationDate.add(cs.getRegistrationDate().getText().split(": ")[1]);
		CNRNumber.add(cs.getCNRNumber().getText().split(": ")[1]);
		CaptureData.setCasetype(Casetype);
		CaptureData.setFilingNumber(FilingNumber);
		CaptureData.setFilingDate(FilingDate);
		CaptureData.setRegistrationNumber(RegistrationNumber);
		CaptureData.setRegistrationDate(RegistrationDate);
		CaptureData.setCNRNumber(CNRNumber);
	}

	public static void captureCaseStatus(WebDriver Driver) {
		CaseStatus cs = new CaseStatus(Driver);
		ArrayList<String> FirstHearingDate = new ArrayList<String>();
		ArrayList<String> NextHearingDate = new ArrayList<String>();
		ArrayList<String> CaseStage = new ArrayList<String>();
		ArrayList<String> CourtNumberandJudge = new ArrayList<String>();
		ArrayList<String> NatureofDisposal = new ArrayList<String>();

		// capture details from table 2 ie Case Status
		FirstHearingDate.add(cs.getCaseStatusTable().get(0).getText().split(": ")[1]);
		NextHearingDate.add(cs.getCaseStatusTable().get(1).getText().split(": ")[1]);
		CaseStage.add(cs.getCaseStatusTable().get(2).getText().split(": ")[1]);
		if (cs.getCaseStatusTable().get(2).getText().split(": ")[1].equalsIgnoreCase("Case disposed")) {
			NatureofDisposal.add(cs.getCaseStatusTable().get(3).getText().split(": ")[1]);
			CourtNumberandJudge.add(cs.getCaseStatusTable().get(4).getText().split(": ")[1]);
		} else {
			NatureofDisposal.add("N/A");
			CourtNumberandJudge.add(cs.getCaseStatusTable().get(3).getText().split(": ")[1]);
		}

		CaptureData.setFirstHearingDate(FirstHearingDate);
		CaptureData.setNextHearingDate(NextHearingDate);
		CaptureData.setCaseStage(CaseStage);
		CaptureData.setNatureofDisposal(NatureofDisposal);
		CaptureData.setCourtNumberandJudge(CourtNumberandJudge);
	}

	public static void capturePetitionerandAdvocate(WebDriver Driver) {
		CaseStatus cs = new CaseStatus(Driver);
		ArrayList<String> PetitionerandAdvocate = new ArrayList<String>();
		// capture details from table 3 ie Petitioner and Advocate
		PetitionerandAdvocate.add(cs.getPetitionerandAdvocate().getText());
		CaptureData.setPetitionerandAdvocate(PetitionerandAdvocate);

	}

	public static void captureRespondentandAdvocate(WebDriver Driver) {
		CaseStatus cs = new CaseStatus(Driver);
		ArrayList<String> RespondentandAdvocate = new ArrayList<String>();
		// capture details from table 4 ie Respondent and Advocate
		RespondentandAdvocate.add(cs.getRespondentandAdvocate().getText());
		CaptureData.setRespondentandAdvocate(RespondentandAdvocate);
	}

	public static void captureActs(WebDriver Driver) {
		CaseStatus cs = new CaseStatus(Driver);
		String underActs = "";
		String underSection = "";
		ArrayList<String> UnderAct = new ArrayList<String>();
		ArrayList<String> UnderSection = new ArrayList<String>();
		// capture detail from table 5 ie Acts
		for (int i = 0; i < cs.getActs().size(); i++) {
			underActs = underActs + "\n"
					+ Driver.findElement(By.xpath("//*[@id=\"act_table\"]/tbody/tr[" + (i + 2) + "]/td[1]")).getText();
			underSection = underSection + "\n"
					+ Driver.findElement(By.xpath("//*[@id=\"act_table\"]/tbody/tr[" + (i + 2) + "]/td[2]")).getText();
			;
		}
		UnderAct.add(underActs);
		UnderSection.add(underSection);

		CaptureData.setUnderAct(UnderAct);
		CaptureData.setUnderSection(UnderSection);
	}

	public static void captureHistory(WebDriver Driver) {

		ArrayList<String> CaseHistory = new ArrayList<String>();
		int size;
		String caseHistory = "Registration_Number\t\tJudge\t\t\t\t\t\t\tBusiness_on_Date\t\tHearing Date\t\tPurpose of Hearing\t\tBusiness";
		CaseStatus cs = new CaseStatus(Driver);
		size = cs.getCaseHistory().size();

		for (int i = 0; i < size; i++) {
			String Registration_Number = Driver
					.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[1]")).getText();
			String Judge = Driver.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[2]"))
					.getText();
			String BusinessonDate = Driver
					.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[3]")).getText();
			Driver.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[3]/a")).click();

			String Business = Driver
					.findElement(By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7]/table/tbody/tr[2]/td[3]"))
					.getText();

			Driver.findElement(By.xpath("//*[@id=\"back_business\"]/center/a")).click();

			String HearingDate = Driver
					.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[4]")).getText();
			String PurposeofHearing = Driver
					.findElement(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[5]")).getText();

			if (Judge.length() < 50) {
				for (int s = 0; s < 50 - Judge.length(); s++)
					Judge += " ";
			}
			caseHistory = caseHistory + "\n" + Registration_Number + "\t\t\t" + Judge + "\t\t\t" + BusinessonDate
					+ "\t\t\t" + HearingDate + "\t\t" + PurposeofHearing + "\t\t" + Business;
		}
		CaseHistory.add(caseHistory);

		CaptureData.setCaseHistory(CaseHistory);
	}

}
