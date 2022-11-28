package com.example.seleniumdemo.jsoncompilation;

import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyInputCNRNumber;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyOutput;
import com.example.seleniumdemo.jsoncompilation.jsonbody.JsonBodyOutputToCaseNumber;
import com.example.seleniumdemo.jsoncompilation.pages.eCourtPage;
import com.example.seleniumdemo.jsoncompilation.utils.WebDriverLibrary;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class eCourt {
    WebDriver driver;
    JavascriptExecutor executor;
    WebDriverWait w;

    public eCourt(String browser) throws Exception {
        try {
            driver = WebDriverLibrary.getDriver(browser);
            executor = (JavascriptExecutor) driver;
            driver.get("https://services.ecourts.gov.in/ecourtindia_v6/");

            w = new WebDriverWait(driver, Duration.ofSeconds(2));
        } catch (Exception e) {
            assert driver != null;
            driver.quit();

            throw new Exception(e);
        }
    }

    public void search(JsonBodyInputCNRNumber input) throws Exception {
        WebElement we;

        try {
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.cnrSearch));
            we.sendKeys(input.getCino());

            System.out.println("Trying Captcha");
            while (true) {
                String path = System.getProperty("user.dir") + "/captcha.png";

                we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.captcha));
                File src = we.getScreenshotAs(OutputType.FILE);

                FileHandler.copy(src, new File(path));
                try {
                    ITesseract tessObj = new Tesseract();
                    tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
                    String captchaPath = tessObj.doOCR(new File(path)).replaceAll("[^A-Za-z0-9]", "").toLowerCase();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.captchaText));
                    System.out.println("Trying [" + captchaPath + "]");
                    we.clear();
                    we.sendKeys(captchaPath);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.search));
                    we.click();

                    Thread.sleep(1000);

                    if (driver.getPageSource().contains("This Case Code does not exists")) break;

                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.validate));
                    if (we.isDisplayed()) break;
                } catch (Exception e) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.closeError));
                    executor.executeScript("arguments[0].click();", we);

                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.back));
                    executor.executeScript("arguments[0].click();", we);
                }
            }

            if (driver.getPageSource().contains("This Case Code does not exists"))
                throw new Exception("No records found");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Search Page: " + e);
        }
    }

    public JsonBodyOutput casePage(boolean b) throws Exception {
        JsonBodyOutput jInit = new JsonBodyOutput();

        try {
            saveRecordCaseNumber(jInit, b);
            System.out.println("Record captured successfully");
        } catch (Exception e) {
            driver.quit();
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        driver.quit();
        System.out.println("Web Driver has successfully terminated");

        return jInit;
    }

    private void saveRecordCaseNumber(JsonBodyOutput j, boolean b) throws InterruptedException, ParseException {
        ArrayList<LinkedHashMap<String, String>> map;
        List<WebElement> weList;
        WebElement we;

        //Misc. Hardcoded
        j.setCourtID("DistrictCourt");
        j.setListingDateSource("From Case Status");
        j.setLastUpdated();

        //Initial
//        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.stateDistrict));
//        String[] xx = we.getAttribute("onclick").trim().split("&");
//        for (String value : xx) {
//            if (value.contains("state_code")) j.setState_val(value.replace("state_code=", ""));
//            if (value.contains("dist_code")) j.setDistrict_val(value.replace("dist_code=", ""));
//            if (value.contains("court_code") && !value.contains("national"))
//                j.setBench_val(value.replace("court_code=", ""));
//        }

        //QR Code Popup
        we = w.until(ExpectedConditions.elementToBeClickable(eCourtPage.qr));
        we.click();

        Thread.sleep(1000);

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.caseTypeNumberYear));
        j.setCaseNoString(we.getText().trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.plaintiff));
        j.setTitle(we.getText().trim() + " versus ");

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.defendant));
        j.setTitle(j.getTitle() + we.getText().trim());

        we = w.until(ExpectedConditions.elementToBeClickable(eCourtPage.qrClose));
        we.click();

        //Table 1: Case Details
        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.courtName));
        j.setCourtName(we.getText().trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.caseType));
        j.setCaseTypeStr(we.getText().split("-")[0].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.filingNumber));
        j.setFile_no(we.getText().split("/")[0].trim());
        j.setFile_year(we.getText().split("/")[1].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.registrationNumber));
        j.setCn(we.getText().split("/")[0].trim());
        j.setCy(we.getText().split("/")[1].trim());

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.cnr));
        j.setCino(we.getText().trim());

        //Table 2: Case Status
        if (!driver.findElements(eCourtPage.decisionDate).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.decisionDate));

            LinkedHashMap<String, String> list = new LinkedHashMap<>();
            String[] tt = we.getText().trim().split(" ");

            Date date = new SimpleDateFormat("MMM").parse(tt[1]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            list.put("date", tt[0].substring(0, tt[0].length() - 2));
            list.put("month", tt[1]);

            String d = list.get("date") + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + tt[2];
            j.setListingDate(d);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date enteredDate = sdf.parse(d);
            Date currentDate = new Date();

            list.put("inFuture", (enteredDate.after(currentDate) ? "true" : "false"));

            j.setListing(list);
        }

        if (!driver.findElements(eCourtPage.status).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.status));
            j.setListingStage(we.getText().trim());

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

        if (driver.findElements(eCourtPage.table2).size() == 4) {
            if (!driver.findElements(eCourtPage.courtNo).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.courtNo));
                j.setCourtNo(we.getText().split("-")[0].trim());
                j.setListingRoom(we.getText().split("-")[0].trim());
                j.setListingJudges(new String[]{we.getText().split("-")[1].trim()});
            }
        } else {
            if (!driver.findElements(eCourtPage.disposalNature).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.disposalNature));
                j.setDisposalNature(we.getText().trim());
            }

            if (!driver.findElements(eCourtPage.courtNo2).isEmpty()) {
                we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.courtNo2));
                j.setCourtNo(we.getText().split("-")[0].trim());
                j.setListingRoom(we.getText().split("-")[0].trim());
                j.setListingJudges(new String[]{we.getText().split("-")[1].trim()});
            }
        }

        //Table 3 & 4
        //Petitioner/Padvocate
        String[] sa;
        ArrayList<String> pr1 = new ArrayList<>();
        ArrayList<String> pr2 = new ArrayList<>();

        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.petitioners));
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
        we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.respondents));
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
        if (!driver.findElements(eCourtPage.act).isEmpty()) {
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.act));
            j.setAct(we.getText().trim());

            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.section));
            j.setSection(we.getText().trim());
        }

        //Table 6: Case History
        if (!driver.findElements(eCourtPage.caseHistory).isEmpty()) {
            map = new ArrayList<>();
            weList = driver.findElements(eCourtPage.caseHistory);

            for (int i = 0; i < weList.size(); i++) {
                LinkedHashMap<String, String> history = new LinkedHashMap<>();

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table table \"]/tbody/tr[" + (i + 1) + "]/td[1]")));
                history.put("judge", we.getText().trim());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table table \"]/tbody/tr[" + (i + 1) + "]/td[2]")));
                history.put("businessOnDate", we.getText().trim());

                //Business Link
                if (b) {
                    we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table table \"]/tbody/tr[" + (i + 1) + "]/td[2]/a")));
                    executor.executeScript("arguments[0].click();", we);

                    Thread.sleep(1500);

                    w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.business));
                    List<WebElement> businessList = driver.findElements(eCourtPage.business);
                    StringBuilder t = new StringBuilder();

                    for (int x = 1; x < businessList.size() - 1; x++) {
                        t.append("[").append(driver.findElement(By.xpath("//*[@id=\"mydiv\"]/center/center[7]/table/tbody/tr[" + (x + 1) + "]/td[1]")).getText().trim());
                        t.append(": ").append(driver.findElement(By.xpath("//*[@id=\"mydiv\"]/center/center[7]/table/tbody/tr[" + (x + 1) + "]/td[3]")).getText().trim()).append("] ");
                    }

                    history.put("business", t.toString().trim());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.businessBack));
                    executor.executeScript("arguments[0].click();", we);

                    Thread.sleep(1500);
                }

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table table \"]/tbody/tr[" + (i + 1) + "]/td[3]")));
                history.put("hearingDate", we.getText().trim());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"history_table table \"]/tbody/tr[" + (i + 1) + "]/td[4]")));
                history.put("purposeOfHearing", we.getText().trim());

                map.add(history);
            }

            j.setListingDates(map);
        }

        //Table 7: Orders
        if (!driver.findElements(eCourtPage.orders).isEmpty()) {
            for (int x = 0; x < driver.findElements(eCourtPage.orders).size(); x++) {
                map = new ArrayList<>();
                weList = driver.findElements(eCourtPage.orders);

                for (int i = 1; i < weList.size(); i++) {
                    LinkedHashMap<String, String> order = new LinkedHashMap<>();

                    we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table table \"]/tbody/tr[" + (i + 1) + "]/td[2]")));
                    order.put("date", we.getText().trim());

                    we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"order_table table \"]/tbody/tr[" + (i + 1) + "]/td[3]/a")));
                    order.put("link", we.getAttribute("href").trim());

                    map.add(order);
                }

                j.setOrders(map);
            }
        }

        //Table 8: IA Status
        if (!driver.findElements(eCourtPage.ia).isEmpty()) {
            map = new ArrayList<>();
            weList = driver.findElements(eCourtPage.ia);

            for (int i = 1; i < weList.size(); i++) {
                LinkedHashMap<String, String> ia = new LinkedHashMap<>();

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"IAheading\"]/tbody/tr[" + (i + 1) + "]/td[1]")));
                ia.put("IA", we.getText().trim());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"IAheading\"]/tbody/tr[" + (i + 1) + "]/td[2]")));
                ia.put("filedBy", we.getText().trim().replace("\n", ", "));

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"IAheading\"]/tbody/tr[" + (i + 1) + "]/td[3]")));
                ia.put("filingDate", we.getText().trim());

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"IAheading\"]/tbody/tr[" + (i + 1) + "]/td[4]")));
                ia.put("particular", we.getText().trim().replace("\n", " "));

                we = w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class=\"IAheading\"]/tbody/tr[" + (i + 1) + "]/td[5]")));
                ia.put("status", we.getText().trim());

                map.add(ia);
            }

            j.setIA(map);
        }
    }

    public JsonBodyOutputToCaseNumber casePageBasic() throws Exception {
        JsonBodyOutputToCaseNumber ret = new JsonBodyOutputToCaseNumber();
        LinkedHashMap<String, String> cn = new LinkedHashMap<>();
        WebElement we;

        try {
            //Initial
//            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.stateDistrict));
//            String[] xx = we.getAttribute("onclick").trim().split("&");
//            for (String value : xx) {
//                if (value.contains("state_code")) cn.put("state_val", value.replace("state_code=", ""));
//                if (value.contains("dist_code")) cn.put("district_val", value.replace("dist_code=", ""));
//                if (value.contains("court_code") && !value.contains("national"))
//                    cn.put("bench_val", value.replace("court_code=", ""));
//            }
//
//            //Court Complex
//            if (cn.get("bench_val").length() >= 5) cn.put("courtComplexEstb", "0");
//            else cn.put("courtComplexEstb", "1");

            //Misc
            cn.put("state_val", "");
            cn.put("district_val", "");
            cn.put("bench_val", "");
            cn.put("courtComplexEstb", "");

            //CT
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.caseType));
            cn.put("ct", we.getText().split("-")[0].trim());

            //CN/CY
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.registrationNumber));
            cn.put("cn", we.getText().split("/")[0].trim());
            cn.put("cy", we.getText().split("/")[1].trim());

            //Misc
            cn.put("recordsReturned", "1");
            ret.setLastUpdated();
            ret.setCaseNumber(cn);

            //Title
            //QR Code Popup
            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.qr));
            we.click();

            Thread.sleep(1000);

            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.plaintiff));
            ret.setTitle(we.getText().trim() + " versus ");

            we = w.until(ExpectedConditions.presenceOfElementLocated(eCourtPage.defendant));
            ret.setTitle(ret.getTitle() + we.getText().trim());

            System.out.println("Record captured successfully");
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception("Error has occurred while operating Case Page: " + e);
        }

        driver.quit();
        return ret;
    }
}