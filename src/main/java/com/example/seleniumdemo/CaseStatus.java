package com.example.seleniumdemo;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CaseStatus {
	public WebDriver Driver;

	@FindBy(xpath = "//*[@id=\"radCourtComplex\"]")
	WebElement CourtComplex;
	@FindBy(xpath = "//*[@id=\"radCourtEst\"]")
	WebElement CourtEstablishment;
	@FindBy(xpath = ".//select[@name='court_complex_code']")
	WebElement complexCaseStatus;
	@FindBy(xpath = "//*[@id=\"court_code\"]")
	WebElement establishmentCaseStatus;
	@FindBy(xpath = ".//select[@name='case_type']")
	WebElement case_type;
	@FindBy(xpath = "//*[@id=\'search_year\']")
	WebElement searchYear;
	@FindBy(xpath = "//*[@id='rgyear']")
	WebElement CaseNosearchYear;
	@FindBy(xpath = "//*[@id=\"search_case_no\"]")
	WebElement CaseNo;
	@FindBy(xpath = "//*[@id=\"radD\"]")
	WebElement disposed;
	@FindBy(xpath = "//*[@id=\"captcha_image\"]")
	WebElement captcha;
	@FindBy(xpath = "//*[@id=\"captcha\"]")
	WebElement captchaText;
	@FindBy(xpath = "//*[@id=\"captcha_container_2\"]/div[1]/div/span[3]/a/img")
	WebElement captchaReset;
	@FindBy(xpath = "//*[@id=\"caseNoDet\"]/div[8]/span[3]/input[1]")
	WebElement Login;
	@FindBy(xpath = "//*[@id=\"back_top\"]/center/a")
	WebElement Back;
	@FindBy(xpath = "//*[@id=\"errSpan\"]/p")
	WebElement Error;

	// Table 1
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/span[2]")
	WebElement Casetype;
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/span[3]")
	WebElement FilingNumber;
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/span[3]/span[2]")
	WebElement FilingDate;
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/span[4]/label")
	WebElement RegistrationNumber;
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/span[4]/span[2]/label[2]")
	WebElement RegistrationDate;
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[1]/b/span")
	WebElement CNRNumber;
	// table 2
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[2]/span/label/strong[2]")
	List<WebElement> caseStatusTable;
	// table3
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[3]/span[1]")
	WebElement PetitionerandAdvocate;
	// table 4
	@FindBy(xpath = "//*[@id=\"secondpage\"]/div[2]/div[3]/span[2]")
	WebElement RespondentandAdvocate;
	// table 5
	@FindBy(xpath = "//*[@id=\"act_table\"]/tbody/tr/td[1]")
	List<WebElement> Acts;
	// table 6
	@FindBy(xpath = "//*[@class=\"history_table\"]/tbody/tr/td[1]")
	List<WebElement> CaseHistory;

	public List<WebElement> getCaseHistory() {
		return CaseHistory;
	}

	public WebElement getBack() {
		return Back;
	}

	public List<WebElement> getActs() {
		return Acts;
	}

	public WebElement getLogin() {
		return Login;
	}

	public WebElement getPetitionerandAdvocate() {
		return PetitionerandAdvocate;
	}

	public WebElement getRespondentandAdvocate() {
		return RespondentandAdvocate;
	}

	public WebElement getCaptcha() {
		return captcha;
	}

	public WebElement getCaptchaText() {
		return captchaText;
	}

	public WebElement getCase_type() {
		return case_type;
	}

	public WebElement getSearchYear() {
		return searchYear;
	}

	public WebElement getCasetype() {
		return Casetype;
	}

	public WebElement getFilingNumber() {
		return FilingNumber;
	}

	public WebElement getFilingDate() {
		return FilingDate;
	}

	public WebElement getRegistrationNumber() {
		return RegistrationNumber;
	}

	public WebElement getRegistrationDate() {
		return RegistrationDate;
	}

	public WebElement getCNRNumber() {
		return CNRNumber;
	}

	public List<WebElement> getCaseStatusTable() {
		return caseStatusTable;
	}

	public CaseStatus(WebDriver driver) {
		this.Driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getCaptchaReset() {
		return captchaReset;
	}

	public WebElement getError() {
		return Error;
	}

	public WebElement getCourtComplex() {
		return CourtComplex;
	}

	public WebElement getCourtEstablishment() {
		return CourtEstablishment;
	}

	public WebElement getComplexCaseStatus() {
		return complexCaseStatus;
	}

	public WebElement getEstablishmentCaseStatus() {
		return establishmentCaseStatus;
	}

	public WebElement getDisposed() {
		return disposed;
	}

	public WebElement getCaseNosearchYear() {
		return CaseNosearchYear;
	}

	public WebElement getCaseNo() {
		return CaseNo;
	}

}
