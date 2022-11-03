package com.example.seleniumdemo;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

import com.example.seleniumdemo.excelUtility;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import com.example.seleniumdemo.CaseStatus;
import com.example.seleniumdemo.HomePage;
import com.example.seleniumdemo.base;
import com.example.seleniumdemo.reusable;

public class FetchData extends base {

	public WebDriver Driver;
	reusable re = new reusable();
	boolean Flag = true;
	public static boolean disposedFlag = false;
	static ArrayList<String> SNo = new ArrayList<String>();
	static ArrayList<String> CaseType_CaseNumber_CaseYear = new ArrayList<String>();
	static ArrayList<String> Petitioner_versus_Respondent = new ArrayList<String>();
	List<WebElement> Viewtab;

	public static void setSNo(ArrayList<String> sNo) {
		SNo = sNo;
	}

	public static void setCaseType_CaseNumber_CaseYear(ArrayList<String> caseType_CaseNumber_CaseYear) {
		CaseType_CaseNumber_CaseYear = caseType_CaseNumber_CaseYear;
	}

	public static void setPetitioner_versus_Respondent(ArrayList<String> petitioner_versus_Respondent) {
		Petitioner_versus_Respondent = petitioner_versus_Respondent;
	}

	public static ArrayList<String> getSNo() {
		return SNo;
	}

	public static ArrayList<String> getCaseType_CaseNumber_CaseYear() {
		return CaseType_CaseNumber_CaseYear;
	}

	public static ArrayList<String> getPetitioner_versus_Respondent() {
		return Petitioner_versus_Respondent;
	}

	/*
	 * Case No Implementation Block
	 */

	public void testCaseNo(String caseCode, String courtType, String caseType, String yr, int NoOfRecords,
			String CaseNo, ArrayList<ArrayList<String>> excelData) throws Exception {
		String OutputFileLocation = excelUtility.createExcel(System.getProperty("user.dir"), "CaseRecords_");
		Iterator<ArrayList<String>> itr = excelData.iterator();
		int counter = 0;
		do {
			String str = "";
			Driver = driversetup();
			Driver.get("https://districts.ecourts.gov.in/nagpur");

			String parent = Driver.getWindowHandle();// For parent window

			HomePage objHP = new HomePage(Driver);

			objHP.getCaseStatus().click();// clicks on Case Status
			objHP.getCaseNo().click();// Clicks on Case Number

			Set<String> handles = Driver.getWindowHandles();// Window handles

			for (String handle : handles) {
				if (!handle.equalsIgnoreCase(parent)) {
					Driver.switchTo().window(handle);

					CaseStatus objCS = new CaseStatus(Driver);

					if (caseCode.equalsIgnoreCase("Court Complex")) {
						objCS.getCourtComplex().click();
						WebElement courtComplex = objCS.getComplexCaseStatus();
						Select dropDown1 = new Select(courtComplex);
						dropDown1.selectByVisibleText(courtType);
					} else if (caseCode.equalsIgnoreCase("Court Establishment")) {
						objCS.getCourtEstablishment().click();
						WebElement courtComplex = objCS.getEstablishmentCaseStatus();
						Select dropDown1 = new Select(courtComplex);
						dropDown1.selectByVisibleText(courtType);
					}

					WebElement casetype = objCS.getCase_type();
					Select dropDown2 = new Select(casetype);
					dropDown2.selectByVisibleText(caseType);

					if (!CaseNo.isEmpty())
						objCS.getCaseNo().sendKeys(CaseNo);
					else if (!excelData.isEmpty()) {
						objCS.getCaseNo().sendKeys(excelData.get(counter).get(0));
					}

					objCS.getCaseNosearchYear().sendKeys(yr);

					while (Flag) {
						System.out.println("Trying captcha");
						File src = objCS.getCaptcha().getScreenshotAs(OutputType.FILE);
						String path = System.getProperty("user.dir") + "/captcha.png"; // "/CaptchaImages/captcha.png";
						System.out.println(path);
						FileHandler.copy(src, new File(path));
						try {
							ITesseract tessObj = new Tesseract();
							tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
//							tessObj.setDatapath("/app/.apt/usr/share/tesseract-ocr/tessdata");
							str = tessObj.doOCR(new File(path));

//							str = JOptionPane.showInputDialog("Enter Captcha here");

							objCS.getCaptchaText().sendKeys(str);
							objCS.getLogin().click();

							Thread.sleep(2000);

							Viewtab = Driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr/td/a"));
							if (!Viewtab.isEmpty())
								Flag = false;
							else if (objCS.getError().isDisplayed()&&!objCS.getError().getText().equalsIgnoreCase("Invalid Captcha")&&!objCS.getError().getText().equalsIgnoreCase("THERE IS AN ERROR"))
								Flag = false;
							
						} catch (Exception e) {
							objCS.getCaptchaReset().click();
						}
					}

					Viewtab = Driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr/td/a"));
					if (Viewtab.isEmpty()) {
						System.err.println("No Record Found for given details");
						JOptionPane.showConfirmDialog(null, "No Record Found", "Sorry", JOptionPane.DEFAULT_OPTION);
					} else {
						// output sheet location

						for (int i = 0; i < Viewtab.size() && i < NoOfRecords; i++) {
							ArrayList<String> SNo = new ArrayList<String>();
							ArrayList<String> CaseType_CaseNumber_CaseYear = new ArrayList<String>();
							ArrayList<String> Petitioner_versus_Respondent = new ArrayList<String>();

							SNo.add(Driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[1]"))
									.getText());
							CaseType_CaseNumber_CaseYear.add(
									Driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[2]"))
											.getText());
							Petitioner_versus_Respondent.add(
									Driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[3]"))
											.getText());

							setSNo(SNo);
							setCaseType_CaseNumber_CaseYear(CaseType_CaseNumber_CaseYear);
							setPetitioner_versus_Respondent(Petitioner_versus_Respondent);

							Viewtab.get(i).click();
							CaptureData.readfromWebpage(Driver);
							objCS.getBack().click();
							System.out.println("Record no: [" + (counter + 1) + "] Captured Successfully ");
							excelUtility.saveToExcel(OutputFileLocation, "NAGPUR_DISTRICT_COURT");
							System.out.println("Record no: [" + (counter + 1) + "] Exported to Excel Successfully\n");
						}

					}

				}
			}
			Driver.switchTo().window(parent);
			Driver.quit();
			counter++;
			Flag = true;
			if(counter>=excelData.size())
				break;
		} while (itr.hasNext());
//		 JOptionPane.showConfirmDialog(null, "Records Processed Successfully", "Job Complete",
//		 JOptionPane.DEFAULT_OPTION);
	}

	/*
	 * Case Type Implementation Block
	 */
	public void testCaseType(String caseCode, String courtType, String caseType, String yr, String caseStatus,
			int NoOfRecords) throws Exception {
		String OutputFileLocation = excelUtility.createExcel(System.getProperty("user.dir"), "CaseRecords_");
		Driver = driversetup();
		Driver.get("https://districts.ecourts.gov.in/nagpur");

		String parent = Driver.getWindowHandle();// For parent window

		HomePage objHP = new HomePage(Driver);

		objHP.getCaseStatus().click();// clicks on Case Status
		objHP.getCaseType().click(); // Clicks on Case Type

		Set<String> handles = Driver.getWindowHandles();// Window handles

		for (String handle : handles) {
			if (!handle.equalsIgnoreCase(parent)) {
				Driver.switchTo().window(handle);

				CaseStatus objCS = new CaseStatus(Driver);

				if (caseCode.equalsIgnoreCase("Court Complex")) {
					objCS.getCourtComplex().click();
					WebElement courtComplex = objCS.getComplexCaseStatus();
					Select dropDown1 = new Select(courtComplex);
					System.out.println("Selecting Court Type: "+courtType);
					dropDown1.selectByVisibleText(courtType);
				} else if (caseCode.equalsIgnoreCase("Court Establishment")) {
					objCS.getCourtEstablishment().click();
					WebElement courtComplex = objCS.getEstablishmentCaseStatus();
					Select dropDown1 = new Select(courtComplex);
					System.out.println("Selecting Court Type: "+courtType);
					dropDown1.selectByVisibleText(courtType);
				}

				WebElement casetype = objCS.getCase_type();
				Select dropDown2 = new Select(casetype);
				dropDown2.selectByVisibleText(caseType);

				objCS.getSearchYear().sendKeys(yr);

				if (caseStatus.equalsIgnoreCase("Disposed")) {
					objCS.getDisposed().click();
					disposedFlag = true;
				}

				while (Flag) {
					System.out.println("Trying captcha");
					File src = objCS.getCaptcha().getScreenshotAs(OutputType.FILE);
					String path = System.getProperty("user.dir") + "/captcha.png"; // "/CaptchaImages/captcha.png";
					System.out.println(path);
					FileHandler.copy(src, new File(path));
					try {
						ITesseract tessObj = new Tesseract();
						tessObj.setDatapath(System.getProperty("user.dir") + "/src/main/resources/Tess4J/tessdata");
						String str = tessObj.doOCR(new File(path));

//						String str = JOptionPane.showInputDialog("Enter Captcha here");

						objCS.getCaptchaText().sendKeys(str);
						objCS.getLogin().click();

						Thread.sleep(2000);

						Viewtab = Driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr/td/a"));
						if (!Viewtab.isEmpty())
							Flag = false;
						else if (objCS.getError().isDisplayed()&&!objCS.getError().getText().equalsIgnoreCase("Invalid Captcha")&&!objCS.getError().getText().equalsIgnoreCase("THERE IS AN ERROR"))
							Flag = false;
					} catch (Exception e) {
						objCS.getCaptchaReset().click();
					}
				}

				Viewtab = Driver.findElements(By.xpath("//*[@id=\"showList1\"]/tr/td/a"));
				if (Viewtab.isEmpty()) {
					System.err.println("No Record Found for given details");
					JOptionPane.showConfirmDialog(null, "No Record Found", handle, JOptionPane.DEFAULT_OPTION);
				} else {
					// output sheet location

					for (int i = 0; i < Viewtab.size() && i < NoOfRecords; i++) {
						ArrayList<String> SNo = new ArrayList<String>();
						ArrayList<String> CaseType_CaseNumber_CaseYear = new ArrayList<String>();
						ArrayList<String> Petitioner_versus_Respondent = new ArrayList<String>();

						SNo.add(Driver.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[1]"))
								.getText());
						CaseType_CaseNumber_CaseYear.add(Driver
								.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[2]")).getText());
						Petitioner_versus_Respondent.add(Driver
								.findElement(By.xpath("//*[@id=\"showList1\"]/tr[" + (i + 2) + "]/td[3]")).getText());

						setSNo(SNo);
						setCaseType_CaseNumber_CaseYear(CaseType_CaseNumber_CaseYear);
						setPetitioner_versus_Respondent(Petitioner_versus_Respondent);

						Viewtab.get(i).click();
						CaptureData.readfromWebpage(Driver);
						objCS.getBack().click();
						System.out.println("Record no: [" + (i + 1) + "] Captured Successfully ");
						excelUtility.saveToExcel(OutputFileLocation, "NAGPUR_DISTRICT_COURT");
						System.out.println("Record no: [" + (i + 1) + "] Exported to Excel Successfully\n");
					}
					JOptionPane.showConfirmDialog(null, "Records Processed Successfully", "Job Complete",
							JOptionPane.DEFAULT_OPTION);
				}

			}
		}
		Driver.switchTo().window(parent);
		Driver.quit();
	}

}