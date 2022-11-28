package com.example.seleniumdemo.jsoncompilation;

import com.example.seleniumdemo.jsoncompilation.jsonbody.*;
import com.example.seleniumdemo.jsoncompilation.pages.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class DistrictCourt {
    public WebDriver driver;
    WebDriverWait w;
    String parentPage;

    public DistrictCourt(String browser) throws Exception {
        try {
            driver = WebDriverLibrary.getDriver(browser);
            driver.get("https://districts.ecourts.gov.in/");
            parentPage = driver.getWindowHandle();

            w = new WebDriverWait(driver, Duration.ofSeconds(6));
        } catch (Exception e) {
            assert driver != null;
            driver.quit();

            throw new Exception(e);
        }
    }

    private void navigateToDistrict(String state, String district) throws Exception {
        try {
            WebElement we;

            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.state));
            we.findElement(By.xpath("//*[@id=\"block-views-india-block\"]/div/div/div/ul/li[" + state + "]/a")).click();

            we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.district));
            we.findElement(By.xpath("//*[@id=\"map-position-list\"]/div/div/div[2]/div/div/div/ul/li[" + district + "]/a")).click();
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public int caseNumber(JsonBodyInputCaseNumber input) throws Exception {
        List<WebElement> le;
        WebElement we;
        Select dd;

        try {
            navigateToDistrict(input.getState_val(), input.getDistrict_val());

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.caseStatus));
            we.click();

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.caseNo));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            if (input.getCourtComplexEstb().trim().equalsIgnoreCase("0")) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.courtComplex));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.complexStatus));
            } else {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.courtEstablishment));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.establishmentStatus));
            }

            dd = new Select(we);
            dd.selectByValue(input.getBench_val());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseType));
            dd = new Select(we);
            le = dd.getOptions();

            boolean ret = true;
            for (WebElement temp : le) {
                if (temp.getAttribute("value").contains(input.getCt())) {
                    dd.selectByValue(input.getCt());
                    ret = false;
                    break;
                }
            }

            if (ret) {
                for (WebElement temp : le) {
                    if (temp.getText().contains(input.getCt())) {
                        dd.selectByValue(temp.getAttribute("value"));
                        break;
                    }
                }
            }

            if (!input.getCn().isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseNo));
                we.sendKeys(input.getCn());
            }

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.caseNoYear));
            we.sendKeys(input.getCy());

            System.out.println("Trying Captcha");
            String captchaSrc;
            while (true) {
                String path = System.getProperty("user.dir") + "/captcha.png";

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.captcha));
                captchaSrc = we.getAttribute("src");
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    String captchaPath = tessObj.doOCR(new File(path)).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                    captchaPath = (captchaPath.length() < 6 ? "FAILED" : captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.captchaText));
                    System.out.println("Trying [" + captchaPath + "]");
                    we.sendKeys(captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCaseNumber.search));
                    we.click();

                    w.until(ExpectedConditions.invisibilityOfElementLocated(BasePage.loading));

                    we = driver.findElement(SearchPageCaseNumber.error);
                    if (we.isDisplayed()) {
                        if (we.getText().equalsIgnoreCase("Record not found")) break;
                    } else break;

                    while (true)
                        if (!driver.findElement(SearchPageCaseNumber.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                } catch (Exception e) {
                    while (true)
                        if (!driver.findElement(SearchPageCaseNumber.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                }
            }

            if (!driver.findElements(SearchPageCaseNumber.viewTab).isEmpty())
                return driver.findElements(SearchPageCaseNumber.viewTab).size();
            else throw new Exception("No records found for given details");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public int partyName(JsonBodyInputPartyName input) throws Exception {
        WebElement we;
        Select dd;

        try {
            navigateToDistrict(input.getState_val(), input.getDistrict_val());

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.caseStatus));
            we.click();

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.partyName));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            if (input.getCourtComplexEstb().trim().equalsIgnoreCase("0")) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.courtComplex));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.complexStatus));
            } else {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.courtEstablishment));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.establishmentStatus));
            }

            dd = new Select(we);
            dd.selectByValue(input.getBench_val());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.name));
            we.sendKeys(input.getName());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.caseYear));
            we.sendKeys(input.getCy());

            switch (input.getF().toLowerCase().trim()) {
                case "0":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.pending));
                    we.click();
                    break;
                case "1":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.disposed));
                    we.click();
                    break;
                default:
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.both));
                    we.click();
                    break;
            }

            System.out.println("Trying Captcha");
            String captchaSrc;
            while (true) {
                String path = System.getProperty("user.dir") + "/captcha.png";

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.captcha));
                captchaSrc = we.getAttribute("src");
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    String captchaPath = tessObj.doOCR(new File(path)).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                    captchaPath = (captchaPath.length() < 6 ? "FAILED" : captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.captchaText));
                    System.out.println("Trying [" + captchaPath + "]");
                    we.sendKeys(captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.search));
                    we.click();

                    w.until(ExpectedConditions.invisibilityOfElementLocated(BasePage.loading));

                    we = driver.findElement(SearchPagePartyName.error);
                    if (we.isDisplayed()) {
                        if (we.getText().equalsIgnoreCase("Record not found")) break;
                    } else break;

                    while (true)
                        if (!driver.findElement(SearchPagePartyName.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                } catch (Exception e) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPagePartyName.captchaReset));
                    we.click();

                    while (true)
                        if (!driver.findElement(SearchPagePartyName.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                }
            }

            if (!driver.findElements(SearchPageCaseNumber.viewTab).isEmpty())
                return driver.findElements(SearchPageCaseNumber.viewTab).size();
            else throw new Exception("No records found for given details");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public int advocateName(JsonBodyInputAdvocateName input) throws Exception {
        WebElement we;
        Select dd;

        try {
            navigateToDistrict(input.getState_val(), input.getDistrict_val());

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.caseStatus));
            we.click();

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.advocateName));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            if (input.getCourtComplexEstb().equalsIgnoreCase("0")) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.courtComplex));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.complexStatus));
            } else {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.courtEstablishment));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.establishmentStatus));
            }

            dd = new Select(we);
            dd.selectByValue(input.getBench_val());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.advocate));
            we.click();

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.name));
            we.sendKeys(input.getName());

            switch (input.getF().toLowerCase().trim()) {
                case "0":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.pending));
                    we.click();
                    break;
                case "1":
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.disposed));
                    we.click();
                    break;
                default:
                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.both));
                    we.click();
                    break;
            }

            System.out.println("Trying Captcha");
            String captchaSrc;
            while (true) {
                String path = System.getProperty("user.dir") + "/captcha.png";

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.captcha));
                captchaSrc = we.getAttribute("src");
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    String captchaPath = tessObj.doOCR(new File(path)).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                    captchaPath = (captchaPath.length() < 6 ? "FAILED" : captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.captchaText));
                    System.out.println("Trying [" + captchaPath + "]");
                    we.sendKeys(captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageAdvocateName.search));
                    we.click();

                    w.until(ExpectedConditions.invisibilityOfElementLocated(BasePage.loading));

                    we = driver.findElement(SearchPageAdvocateName.error);
                    if (we.isDisplayed()) {
                        if (we.getText().equalsIgnoreCase("Record not found")) break;
                    } else break;

                    while (true)
                        if (!driver.findElement(SearchPageCaseNumber.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                } catch (Exception e) {
                    while (true)
                        if (!driver.findElement(SearchPageAdvocateName.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                }
            }

            if (!driver.findElements(SearchPageCaseNumber.viewTab).isEmpty())
                return driver.findElements(SearchPageCaseNumber.viewTab).size();
            else throw new Exception("No records found for given details");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public int causeList(JsonBodyInputCauseList input) throws Exception {
        WebElement we;
        Select dd;

        try {
            navigateToDistrict(input.getState_val(), input.getDistrict_val());

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.causeListButton));
            we.click();

            we = w.until(ExpectedConditions.elementToBeClickable(BasePage.causeList));
            we.click();

            //Window handles
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equalsIgnoreCase(parentPage)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            if (input.getCourtComplexEstb().trim().equalsIgnoreCase("0")) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.courtComplex));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.complexStatus));
            } else {
                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.courtEstablishment));
                we.click();

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.establishmentStatus));
            }

            dd = new Select(we);
            dd.selectByValue(input.getBench_val());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.courtName));
            dd = new Select(we);
            dd.selectByValue(input.getCourt());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.causeListDate));
            we.clear();
            we.sendKeys(input.getCauseDate());

            we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.calendar));
            we.click();

            System.out.println("Trying Captcha");
            String captchaSrc;
            while (true) {
                String path = System.getProperty("user.dir") + "/captcha.png";

                we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.captcha));
                captchaSrc = we.getAttribute("src");
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    String captchaPath = tessObj.doOCR(new File(path)).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
                    captchaPath = (captchaPath.length() < 6 ? "FAILED" : captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(SearchPageCauseList.captchaText));
                    System.out.println("Trying [" + captchaPath + "]");
                    we.sendKeys(captchaPath);

                    if (input.getCaseType().trim().equalsIgnoreCase("0")) {
                        we = w.until(ExpectedConditions.elementToBeClickable(SearchPageCauseList.civilSearch));
                        we.click();
                    } else {
                        we = w.until(ExpectedConditions.elementToBeClickable(SearchPageCauseList.criminalSearch));
                        we.click();
                    }

                    Thread.sleep(3000);

                    we = driver.findElement(SearchPageCauseList.viewTab);
                    if (we.isDisplayed()) break;

                    we = driver.findElement(SearchPageCauseList.error);
                    if (we.isDisplayed()) {
                        if (we.getText().equalsIgnoreCase("Record not found")) break;
                    } else break;

                    while (true)
                        if (!driver.findElement(SearchPageCauseList.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                } catch (Exception e) {
                    while (true)
                        if (!driver.findElement(SearchPageCaseNumber.captcha).getAttribute("src").equalsIgnoreCase(captchaSrc))
                            break;
                }
            }

            if (!driver.findElements(SearchPageCauseList.viewTabs).isEmpty())
                return driver.findElements(SearchPageCauseList.viewTabs).size();
            else throw new Exception("No records found for given details");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public JsonBodyOutput casePage(int i, boolean b) throws Exception {
        WebElement we;
        JsonBodyOutput ret = new JsonBodyOutput();

        try {
            if (driver.findElements(SearchPageCaseNumber.viewTab).isEmpty() && !driver.findElements(BasePage.back).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            }

            List<WebElement> t = driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td"));
            if (!t.get(0).getAttribute("id").toLowerCase().contains("td_court_name")) {
                ret.setIndex(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[1]")).getText().replace(")", ""));
                ret.setCaseNoString(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td" +
                        "[2]")).getText());
                ret.setTitle(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[3]")).getText().replace("\n", " "));
                ret.setLastUpdated();

                if (t.size() == 4)
                    driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[4]/a")).click();
                else driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[5]/a")).click();

                saveRecord(ret, b);
                System.out.println("Record index [" + ret.getIndex() + "] captured successfully");

                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            } else throw new Exception("Not a valid index");
        } catch (Exception e) {
            if (!e.getMessage().toLowerCase().contains("not a valid index")) e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        return ret;
    }

    public JsonBodyOutputAlt casePageAlt(int i, boolean b) throws Exception {
        WebElement we;
        JsonBodyOutputAlt ret = new JsonBodyOutputAlt();

        try {
            if (driver.findElements(SearchPageCaseNumber.viewTab).isEmpty() && !driver.findElements(BasePage.back).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            }

            List<WebElement> t = driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td"));
            if (!t.get(0).getAttribute("id").toLowerCase().contains("td_court_name")) {
                ret.setIndex(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[1]")).getText());
                ret.setCaseTypeNumberYear(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td" +
                        "[2]")).getText());
                ret.setTitle(driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[3]")).getText());
                ret.setLastUpdated();

                if (t.size() == 4)
                    driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[4]/a")).click();
                else driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[5]/a")).click();

                saveRecordAlt(ret, b);
                System.out.println("Record index [" + ret.getIndex() + "] captured successfully");

                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            } else throw new Exception("Not a valid index");
        } catch (Exception e) {
            if (!e.getMessage().toLowerCase().contains("not a valid index")) e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        return ret;
    }

    public LinkedHashMap<String, String> casePageAdvocateBasic(int i) throws Exception {
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();

        try {
            WebElement t = driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td"));
            if (!t.getAttribute("id").toLowerCase().contains("td_court_name")) {
                ret.put("Index", driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[1]")).getText());
                ret.put("Title", driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[3]")).getText());
                ret.put("Court", driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[4]")).getText().replace("\n", ", "));

                String[] caseTypeNumberYear = driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 1) + "]/td[2]")).getText().split("/");
                ret.put("CT", caseTypeNumberYear[0]);
                ret.put("CN", caseTypeNumberYear[1]);
                ret.put("CY", caseTypeNumberYear[2]);

                System.out.println("Record index [" + ret.get("Index") + "] captured successfully");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        return ret;
    }

    public JsonBodyOutput casePageCauseList(int i, boolean b) throws Exception {
        WebElement we;
        JsonBodyOutput ret = new JsonBodyOutput();

        try {
            if (driver.findElements(SearchPageCauseList.viewTabs).isEmpty() && !driver.findElements(BasePage.back).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            }

            List<WebElement> viewTabs = driver.findElements(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr"));
            if (viewTabs.size() == 1) {
                viewTabs = driver.findElements(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr/td"));
                if (viewTabs.size() == 4) {
                    ret.setIndex(viewTabs.get(0).getText());
                    ret.setCaseNoString(viewTabs.get(1).getText());
                    ret.setTitle(viewTabs.get(2).getText());
                    ret.setLastUpdated();
                    driver.findElement(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr/td[2]/a")).click();

                    saveRecord(ret, b);
                    System.out.println("Record index [" + ret.getIndex() + "] captured successfully");

                    we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                    we.click();
                } else throw new Exception("Not a valid index");
            } else throw new Exception("Not a valid index");
        } catch (Exception e) {
            if (!e.getMessage().toLowerCase().contains("not a valid index")) e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        return ret;
    }

    public JsonBodyOutputAlt casePageCauseListAlt(int i, boolean b) throws Exception {
        WebElement we;
        JsonBodyOutputAlt ret = new JsonBodyOutputAlt();

        try {
            if (driver.findElements(SearchPageCauseList.viewTabs).isEmpty() && !driver.findElements(BasePage.back).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                we.click();
            }

            List<WebElement> viewTabs = driver.findElements(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr"));
            if (viewTabs.size() == 1) {
                viewTabs = driver.findElements(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr/td"));
                if (viewTabs.size() == 4) {
                    ret.setIndex(viewTabs.get(0).getText());
                    ret.setCaseTypeNumberYear(viewTabs.get(1).getText());
                    ret.setTitle(viewTabs.get(2).getText());
                    ret.setLastUpdated();
                    driver.findElement(By.xpath("//*[@id=\"firstpage\"]/table/tbody[" + (i + 1) + "]/tr/td[2]/a")).click();

                    saveRecordAlt(ret, b);
                    System.out.println("Record index [" + ret.getIndex() + "] captured successfully");

                    we = w.until(ExpectedConditions.presenceOfElementLocated(BasePage.back));
                    we.click();
                } else throw new Exception("Not a valid index");
            } else throw new Exception("Not a valid index");
        } catch (Exception e) {
            if (!e.getMessage().toLowerCase().contains("not a valid index")) e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        return ret;
    }

    private void saveRecord(JsonBodyOutput j, boolean b) throws ParseException {
        ArrayList<LinkedHashMap<String, String>> map;
        List<WebElement> weList;
        WebElement we;

        //Misc. Hardcoded
        j.setCourtID("DistrictCourt");
        j.setListingDateSource("From Case Status");
        j.setLastUpdated();

        //Table 1: Case Details
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtName));
        j.setCourtName(we.getText().split(": ")[1].trim());
        j.setCaseTypeStr(we.getText().split(": ")[1].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.filingNumber));
        j.setFile_no(we.getText().split(": ")[1].split("F")[0].split("/")[0].trim());
        j.setFile_year(we.getText().split(": ")[1].split("F")[0].split("/")[1].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.registrationNumber));
        j.setCn(we.getText().split("/")[0].trim());
        j.setCy(we.getText().split("/")[1].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.cnr));
        j.setCino(we.getText().split(": ")[1].trim());

        //Table 2: Case Status
        if (!driver.findElements(CasePage.decisionDate).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.decisionDate));

            LinkedHashMap<String, String> list = new LinkedHashMap<>();
            String[] tt = we.getText().trim().split(" ");

            Date date = new SimpleDateFormat("MMM").parse(tt[2]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            list.put("date", tt[1].substring(0, tt[1].length() - 2));
            list.put("month", tt[2]);

            String d = list.get("date") + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + tt[3];
            j.setListingDate(d);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date enteredDate = sdf.parse(d);
            Date currentDate = new Date();

            list.put("inFuture", (enteredDate.after(currentDate) ? "true" : "false"));

            j.setListing(list);
        }

        if (!driver.findElements(CasePage.status).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.status));
            j.setListingStage(we.getText().split(": ")[1].trim());

            LinkedHashMap<String, String> ss = new LinkedHashMap<>();
            if (we.getText().toLowerCase().contains("dismissal") || we.getText().toLowerCase().contains("disposed")) {
                j.setStatus("DISPOSED");
                ss.put("label", "DISPOSED");
                ss.put("id", "disposed");
            } else {
                j.setStatus("");
                ss.put("label", "LISTED");
                ss.put("id", "progress");
            }

            j.setStatusGroup(ss);
        }

        if (driver.findElements(CasePage.table2).size() == 4) {
            if (!driver.findElements(CasePage.courtNo).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtNo));
                j.setCourtNo(we.getText().split("-")[0].split(": ")[1].trim());
                j.setListingRoom(we.getText().split("-")[0].split(": ")[1].trim());
                j.setListingJudges(new String[]{we.getText().split("-")[1].trim()});
            }
        } else {
            if (!driver.findElements(CasePage.disposalNature).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.disposalNature));
                j.setDisposalNature(we.getText().trim());
            }

            if (!driver.findElements(CasePage.courtNo2).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtNo2));
                j.setCourtNo(we.getText().split("-")[0].split(": ")[1].trim());
                j.setListingRoom(we.getText().split("-")[0].split(": ")[1].trim());
                j.setListingJudges(new String[]{we.getText().split("-")[1].trim()});
            }
        }

        //Table 3 & 4
        //Petitioner/Padvocate
        String[] sa;
        ArrayList<String> pr1 = new ArrayList<>();
        ArrayList<String> pr2 = new ArrayList<>();

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.petitioners));
        sa = we.getText().split("\n");

        for (String value : sa) {
            if (!value.trim().isEmpty()) {
                if (value.contains(")")) pr1.add(value.split("\\)")[1].trim());
                else pr2.add(value.replace("Advocate-", "").replace("Advocate -", "").trim());
            }
        }

        j.setPetitioners(pr1.toArray(new String[0]));
        j.setPadvocates(pr2.toArray(new String[0]));

        //Respondent/Radvocate
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.respondents));
        sa = we.getText().split("\n");
        pr1 = new ArrayList<>();
        pr2 = new ArrayList<>();

        for (String value : sa) {
            if (!value.trim().isEmpty()) {
                if (value.contains(")")) pr1.add(value.split("\\)")[1].trim());
                else pr2.add(value.replace("Advocate-", "").replace("Advocate -", "").trim());
            }
        }

        j.setRespondents(pr1.toArray(new String[0]));
        j.setRadvocates(pr2.toArray(new String[0]));

        //Table 5: Acts
        if (!driver.findElements(CasePage.acts).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.act));
            j.setAct(we.getText().trim());

            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.section));
            j.setSection(we.getText().trim());
        }

        //Table 6: Case History
        if (!driver.findElements(CasePage.caseHistory).isEmpty()) {
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

                //Business Link
                if (b) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[3]/a")));
                    we.click();

                    w.until(ExpectedConditions.presenceOfElementLocated(CasePage.business));
                    List<WebElement> businessList = driver.findElements(CasePage.business);
                    StringBuilder t = new StringBuilder();
                    for (int x = 1; x < businessList.size() - 2; x++) {
                        t.append("[").append(driver.findElement(By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7" +
                                "]/table/tbody/tr[" + (x + 1) + "]/td[1]")).getText().trim());
                        t.append(": ").append(driver.findElement(By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7" +
                                "]/table/tbody/tr[" + (x + 1) + "]/td[3]")).getText().trim()).append("] ");
                    }
                    history.put("business", t.toString().trim());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.businessBack));
                    we.click();
                }

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[4]")));
                history.put("hearingDate", we.getText());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[5]")));
                history.put("purposeOfHearing", we.getText());

                map.add(history);
            }

            j.setListingDates(map);
        }

        //Table 7: Orders
        if (!driver.findElements(CasePage.orders).isEmpty()) {
            for (int x = 0; x < driver.findElements(CasePage.orders).size(); x++) {
                map = new ArrayList<>();
                weList = driver.findElements(CasePage.orders);

                if (driver.findElement(CasePage.ordersName).getText().equalsIgnoreCase("Process Details")) {
                    for (int i = 1; i < weList.size(); i++) {
                        LinkedHashMap<String, String> order = new LinkedHashMap<>();

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[1]")));
                        order.put("processId", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[2]")));
                        order.put("processDate", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[3]")));
                        order.put("processTitle", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[4]")));
                        order.put("partyName", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[5]")));
                        order.put("issuedProcess", we.getText());

                        map.add(order);
                    }

                    j.setProcess(map);
                }

                if (driver.findElement(CasePage.ordersName).getText().equalsIgnoreCase("Orders")) {
                    for (int i = 1; i < weList.size(); i++) {
                        LinkedHashMap<String, String> order = new LinkedHashMap<>();

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"][" + (i + 1) + "]/tbody/tr/td[2]")));
                        order.put("date", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"][" + (i + 1) + "]/tbody/tr/td[3]/a")));
                        order.put("link", we.getAttribute("href"));

                        map.add(order);
                    }

                    j.setOrders(map);
                }
            }
        }
    }

    private void saveRecordAlt(JsonBodyOutputAlt j, boolean b) {
        ArrayList<LinkedHashMap<String, String>> map;
        List<WebElement> weList;
        WebElement we;

        //Table 1: Case Details
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtName));
        j.setCourt(we.getText());

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
        if (!driver.findElements(CasePage.firstHearing).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.firstHearing));
            j.setFirstHearing(we.getText());
        }

        if (!driver.findElements(CasePage.decisionDate).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.decisionDate));
            j.setDecisionDate(we.getText());
        }

        if (!driver.findElements(CasePage.status).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.status));
            j.setStatus(we.getText());
        }

        if (driver.findElements(CasePage.table2).size() == 4) {
            if (!driver.findElements(CasePage.courtNo).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtNo));
                j.setCourtNo(we.getText());
            }
        } else {
            if (!driver.findElements(CasePage.disposalNature).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.disposalNature));
                j.setDisposalNature(we.getText().trim());
            }

            if (!driver.findElements(CasePage.courtNo2).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.courtNo2));
                j.setCourtNo(we.getText());
            }
        }

        //Table 3 & 4
        //Petitioner/Padvocate
        String[] sa;
        ArrayList<String> pr1 = new ArrayList<>();
        ArrayList<String> pr2 = new ArrayList<>();

        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.petitioners));
        sa = we.getText().split("\n");

        for (String value : sa) {
            if (!value.trim().isEmpty()) {
                if (value.contains(")")) pr1.add(value.split("\\)")[1].trim());
                else pr2.add(value.replace("Advocate-", "").replace("Advocate -", "").trim());
            }
        }

        j.setPetitioners(pr1.toArray(new String[0]));
        j.setPadvocate(pr2.toArray(new String[0]));

        //Respondent/Radvocate
        we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.respondents));
        sa = we.getText().split("\n");
        pr1 = new ArrayList<>();
        pr2 = new ArrayList<>();

        for (String value : sa) {
            if (!value.trim().isEmpty()) {
                if (value.contains(")")) pr1.add(value.split("\\)")[1].trim());
                else pr2.add(value.replace("Advocate-", "").replace("Advocate -", "").trim());
            }
        }

        j.setRespondents(pr1.toArray(new String[0]));
        j.setRadvocate(pr2.toArray(new String[0]));

        //Table 5: Acts
        if (!driver.findElements(CasePage.acts).isEmpty()) {
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
        }

        //Table 6: Case History
        if (!driver.findElements(CasePage.caseHistory).isEmpty()) {
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

                //Business Link
                if (b) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[3]/a")));
                    we.click();

                    w.until(ExpectedConditions.presenceOfElementLocated(CasePage.business));
                    List<WebElement> businessList = driver.findElements(CasePage.business);
                    StringBuilder t = new StringBuilder();
                    for (int x = 1; x < businessList.size() - 2; x++) {
                        t.append("[").append(driver.findElement(By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7" +
                                "]/table/tbody/tr[" + (x + 1) + "]/td[1]")).getText().trim());
                        t.append(": ").append(driver.findElement(By.xpath("//*[@id=\"thirdpage\"]/div/center/center[7" +
                                "]/table/tbody/tr[" + (x + 1) + "]/td[3]")).getText().trim()).append("] ");
                    }
                    history.put("business", t.toString().trim());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(CasePage.businessBack));
                    we.click();
                }

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[4]")));
                history.put("hearingDate", we.getText());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table\"]/tbody/tr[" + (i + 2) + "]/td[5]")));
                history.put("purposeOfHearing", we.getText());

                map.add(history);
            }

            j.setCaseHistory(map);
        }

        //Table 7: Orders
        if (!driver.findElements(CasePage.orders).isEmpty()) {
            for (int x = 0; x < driver.findElements(CasePage.orders).size(); x++) {
                map = new ArrayList<>();
                weList = driver.findElements(CasePage.orders);

                if (driver.findElement(CasePage.ordersName).getText().equalsIgnoreCase("Process Details")) {
                    for (int i = 1; i < weList.size(); i++) {
                        LinkedHashMap<String, String> order = new LinkedHashMap<>();

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[1]")));
                        order.put("processId", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[2]")));
                        order.put("processDate", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[3]")));
                        order.put("processTitle", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[4]")));
                        order.put("partyName", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"]/tbody/tr[" + (i + 1) + "]/td[5]")));
                        order.put("issuedProcess", we.getText());

                        map.add(order);
                    }

                    j.setProcess(map);
                }

                if (driver.findElement(CasePage.ordersName).getText().equalsIgnoreCase("Orders")) {
                    for (int i = 1; i < weList.size(); i++) {
                        LinkedHashMap<String, String> order = new LinkedHashMap<>();

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"][" + (i + 1) + "]/tbody/tr/td[2]")));
                        order.put("date", we.getText());

                        we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table\"][" + (i + 1) + "]/tbody/tr/td[3]/a")));
                        order.put("link", we.getAttribute("href"));

                        map.add(order);
                    }

                    j.setOrders(map);
                }
            }
        }
    }

    public void driverQuit() {
        driver.quit();
        System.out.println("Web Driver has successfully terminated");
    }
}