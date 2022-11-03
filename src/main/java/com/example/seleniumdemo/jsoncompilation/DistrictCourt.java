package com.example.seleniumdemo.jsoncompilation;

import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyInputAdvocateName;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyInputCaseNumber;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyOutputExcel;
import com.example.seleniumdemo.jsoncompilation.pages.BasePage;
import com.example.seleniumdemo.jsoncompilation.pages.CasePage;
import com.example.seleniumdemo.jsoncompilation.pages.SearchPageAdvocateName;
import com.example.seleniumdemo.jsoncompilation.pages.SearchPageCaseNumber;
import com.example.seleniumdemo.jsoncompilation.utils.ExcelUtil;
import com.example.seleniumdemo.jsoncompilation.utils.WebDriverLibrary;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.*;

public class DistrictCourt {
    ArrayList<JsonBodyOutputExcel> json;
    public WebDriver driver;
    WebDriverWait w;
    String outputFileLocation;
    String parentPage;

    public DistrictCourt(String browser, String folderName) throws Exception {
        try {
            driver = WebDriverLibrary.getDriver(browser);
            driver.get("https://districts.ecourts.gov.in/nagpur");
            parentPage = driver.getWindowHandle();

            w = new WebDriverWait(driver, Duration.ofSeconds(10));

            outputFileLocation = ExcelUtil.createExcel(System.getProperty("user.dir") + "/src/main/resources" +
                    "/excelOutput/" + folderName + "/", "CaseRecords_");
            json = new ArrayList<>();
        } catch (Exception e) {
            assert driver != null;
            driver.quit();

            throw new Exception(e);
        }
    }

    public void caseByNumber(JsonBodyInputCaseNumber input) throws Exception {
        WebElement we;
        Select dd;

        try {
            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.caseStatus));
            we.click();

            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.caseNo));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            switch (input.getCaseCode().toLowerCase().trim()) {
                case "court complex":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.courtComplex));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.complexStatus));
                    dd = new Select(we);
                    dd.selectByVisibleText(input.getCourtType());
                    break;
                case "court establishment":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.courtEstablishment));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.establishmentStatus));
                    dd = new Select(we);
                    dd.selectByVisibleText(input.getCourtType());
                    break;
                default:
                    throw new Exception("Not valid option for Case Code. Provided [" + input.getCaseCode() + "]");
            }

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseType));
            dd = new Select(we);
            dd.selectByVisibleText(input.getCaseType());

            if (!input.getCaseNo().isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseNo));
                we.sendKeys(input.getCaseNo());
            }

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseNoYear));
            we.sendKeys(input.getCaseYear());

            String captchaPath;
            while (true) {
                System.out.println("Trying Captcha");

                String path = System.getProperty("user.dir") + "/captcha.png";
                System.out.println(path);

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.captcha));
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    captchaPath = tessObj.doOCR(new File(path));

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.captchaText));
                    we.sendKeys(captchaPath.replaceAll("[^A-Za-z0-9]",""));

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.search));
                    we.click();

                    Thread.sleep(2000);

                    we = driver.findElement(SearchPageCaseNumber.error);
                    if (!driver.findElements(SearchPageCaseNumber.viewTab).isEmpty() || (we.isDisplayed() && !we.getText().trim().equalsIgnoreCase("Invalid Captcha") && !we.getText().trim().equalsIgnoreCase("There is an error")))
                        break;
                } catch (Exception e) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.captchaReset));
                    we.click();
                }
            }
        } catch (Exception e) {
            driver.quit();
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public void caseByAdvocate(JsonBodyInputAdvocateName input) throws Exception {
        WebElement we;
        Select dd;

        try {
            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.caseStatus));
            we.click();

            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.advocateName));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            switch (input.getCaseCode().toLowerCase().trim()) {
                case "court complex":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.courtComplex));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.complexStatus));
                    dd = new Select(we);
                    dd.selectByVisibleText(input.getCourtType());
                    break;
                case "court establishment":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.courtEstablishment));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.establishmentStatus));
                    dd = new Select(we);
                    dd.selectByVisibleText(input.getCourtType());
                    break;
                default:
                    throw new Exception("Not valid option for Case Code. Provided [" + input.getCaseCode() + "]");
            }

            switch (input.getSearchBy().toLowerCase().trim()) {
                case "advocate":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.searchAdvocate));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.advocate));
                    we.sendKeys(input.getAdvocate());
                    break;
                case "bar code":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.searchBarCode));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barState));
                    we.sendKeys(input.getState());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barCode));
                    we.sendKeys(input.getBarcode());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barYear));
                    we.sendKeys(input.getYear());
                    break;
                case "date case list":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.searchDateCaseList));
                    we.click();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barState));
                    we.sendKeys(input.getState());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barCode));
                    we.sendKeys(input.getBarcode());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.barYear));
                    we.sendKeys(input.getYear());
                    break;
                default:
                    throw new Exception("Not valid option for Search By. Provided [" + input.getSearchBy() + "]");
            }

            if (input.getSearchBy().trim().equalsIgnoreCase("date case list")) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.dateCaseListCal));
                we.sendKeys(input.getCalendar());
            } else {
                switch (input.getStatus().toLowerCase().trim()) {
                    case "pending":
                        we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.codePending));
                        we.click();
                        break;
                    case "disposed":
                        we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.codeDisposed));
                        we.click();
                        break;
                    case "both":
                        we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.codeBoth));
                        we.click();
                        break;
                    default:
                        throw new Exception("Not valid option for Status. Provided [" + input.getStatus() + "]");
                }
            }

            String captchaPath;
            while (true) {
                System.out.println("Trying Captcha");

                String path = System.getProperty("user.dir") + "/captcha.png";
                System.out.println(path);

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.captcha));
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    captchaPath = tessObj.doOCR(new File(path));

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.captchaText));
                    we.sendKeys(captchaPath.replaceAll("[^A-Za-z0-9]",""));

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.search));
                    we.click();

                    Thread.sleep(5000);

                    we = driver.findElement(SearchPageAdvocateName.error);
                    if (!driver.findElements(SearchPageAdvocateName.viewTab).isEmpty() || (we.isDisplayed() && !we.getText().trim().equalsIgnoreCase("Invalid Captcha") && !we.getText().trim().equalsIgnoreCase("There is an error")))
                        break;
                } catch (Exception e) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.captchaReset));
                    we.click();
                }
            }
        } catch (Exception e) {
            driver.quit();
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public ArrayList<JsonBodyOutputExcel> casePage(int recordsToReturn) throws Exception {
        WebElement we;

        try {
            JsonBodyOutputExcel jInit = new JsonBodyOutputExcel();
            List<WebElement> viewTabs = driver.findElements(SearchPageCaseNumber.viewTab);
            if (viewTabs.isEmpty()) {
                throw new Exception("No records found for given details");
            } else {
                for (int i = 0; i < viewTabs.size() && i < recordsToReturn; i++) {
                    jInit.setIndex(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[1]")).getText());
                    jInit.setCaseTypeNumberYear(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) +
                            "]/td[2]")).getText());
                    jInit.setTitle(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[3]")).getText());
                    viewTabs.get(i).click();

                    JsonBodyOutputExcel jReturn = saveRecordCaseNumber(jInit);

                    System.out.println("Record index [" + (i + 1) + "] captured successfully");
                    System.out.println("Temp: " + jReturn.getMap());

                    ExcelUtil.saveToExcel(outputFileLocation, "Nagpur District Court", jReturn.getMap());
                    System.out.println("Record Exported to Excel Successfully");

                    we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                    we.click();

                    json.add(jReturn);
                }
            }
        } catch (Exception e) {
            driver.quit();
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        driver.quit();
        return json;
    }

    private JsonBodyOutputExcel saveRecordCaseNumber(JsonBodyOutputExcel j) {
        ArrayList<LinkedHashMap<String, String>> map;
        List<WebElement> weList;
        WebElement we;

        //Table 1: Case Details
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.filingNumber));
        j.setFilingNumber(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.filingDate));
        j.setFilingDate(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.registrationNumber));
        j.setRegistrationNumber(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.registrationDate));
        j.setRegistrationDate(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.cnr));
        j.setCnr(we.getText());

        //Table 2: Case Status
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.firstHearing));
        j.setFirstHearing(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.decisionDate));
        j.setDecisionDate(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.status));
        j.setStatus(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.disposalNature));
        j.setDisposalNature(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtNo));
        j.setCourtNo(we.getText());

        //Table 3 & 4: Petitioner and Respondent
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.petitioners));
        j.setPetitioners(we.getText());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.respondents));
        j.setRespondents(we.getText());

        //Table 5: Acts
        map = new ArrayList<>();
        weList = driver.findElements(CasePage.acts);

        for (int i = 0; i < weList.size(); i++) {
            LinkedHashMap<String, String> act = new LinkedHashMap<>();

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"act_table\"]/tbody/tr[" + (i + 2) + "]/td[1]")));
            act.put("underAct", we.getText());

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"act_table\"]/tbody/tr[" + (i + 2) + "]/td[2]")));
            act.put("underSection", we.getText());

            map.add(act);
        }

        j.setActs(map);

        //Table 6: Case History
        map = new ArrayList<>();
        weList = driver.findElements(CasePage.caseHistory);

        for (int i = 0; i < weList.size(); i++) {
            LinkedHashMap<String, String> history = new LinkedHashMap<>();

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[1]")));
            history.put("registrationNumber", we.getText());

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[2]")));
            history.put("judge", we.getText());

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[3]")));
            history.put("businessOnDate", we.getText());

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[4]")));
            history.put("hearingDate", we.getText());

            we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[5]")));
            history.put("purposeOfHearing", we.getText());

            map.add(history);
        }

        j.setCaseHistory(map);

        return j;
    }
}