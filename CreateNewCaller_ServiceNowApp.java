package week5.day2.assignment.servicenowApp;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.sukgu.Shadow;

public class CreateNewCaller_ServiceNowApp extends BaseClass_DataProvider_ReadExceldata {

	@Test(dataProvider = "newCaller")

	public void createCaller(String firstName, String lastName, String title, String email, String busiPhoneNum,
			String mblPhoneNum) throws InterruptedException {

		// Create instance in Shadow Class to inspect elements in Shadow Root DOM

		Shadow dom = new Shadow(driver);
		dom.setImplicitWait(10);

		// Click-All

		dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(3000);

		// Enter callers in filter navigator and press enter

		WebElement cc1 = dom.findElementByXPath("//input[@id='filter']");
		cc1.sendKeys("callers");
		WebElement cc2 = dom.findElementByXPath("//mark[text()='Callers']");
		driver.executeScript("arguments[0].click();", cc2); // JavaScript Click

		// Switch to frame (Inside the shadow Root)

		WebElement frame = dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);

		// Click on New to create a New Caller

		driver.findElement(By.xpath("//button[text()='New']")).click();

		// Enter caller details - firstname,lastname,title,email,business phone ,mobile
		// numbers

		driver.findElement(By.xpath("//input[@id='sys_user.first_name']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='sys_user.last_name']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@id='sys_user.title']")).sendKeys(title);
		driver.findElement(By.xpath("//input[@id='sys_user.email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='sys_user.phone']")).sendKeys(busiPhoneNum);
		driver.findElement(By.xpath("//input[@id='sys_user.mobile_phone']")).sendKeys(mblPhoneNum);

		// Click on Submit

		driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();

		// Verify New caller Creation
		
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(firstName,Keys.ENTER);

		WebElement ele = driver.findElement(By.xpath("//table[@id='sys_user_table']/tbody/tr/td[text()=" + "'" + firstName + "'" + "]"));
		String callerName = ele.getText();

		if (callerName.contains(firstName)) {

			System.out.println("Caller " + firstName + " is created successfully");

		} else {

			System.out.println("Caller is not created ");

		}

	}

	@DataProvider(name = "newCaller")
	public String[][] newCallerData() throws IOException {

		String[][] newCallerData = BaseClass_DataProvider_ReadExceldata.readExcelData("newCaller");
		return newCallerData;
	}

}