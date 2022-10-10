package week5.day2.assignment.servicenowApp;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.sukgu.Shadow;

// Order a new mobile in ServiceNowApp

public class OrderNewMobile_ServiceNowApp extends BaseClassLoginServiceNowApp {

	@Test(dataProvider = "fetchData")
	public void createProposal(String data, String color, String storage) throws InterruptedException {

		// Create instance in Shadow Class to inspect elements in Shadow Root DOM

		Shadow dom = new Shadow(driver);
		dom.setImplicitWait(10);

		// Click-All

		dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(3000);

		// Enter Service catalog in filter navigator and press enter

		WebElement sc = dom.findElementByXPath("//input[@id='filter']");
		sc.sendKeys("Service Catalog");
		sc.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		WebElement sc1 = dom.findElementByXPath("//mark[text()='Service Catalog']");
		driver.executeScript("arguments[0].click();", sc1); // JavaScript Click

		// Click on Mobile, mobile web element is present in the frame
		// Switch to frame (Inside the shadow Root)

		WebElement frame = dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);
		driver.findElement(By.xpath(" //a[text()='Mobiles']")).click();

		// Click on Apple iPhone 6s

		dom.findElementByXPath("//strong[contains(text(),'Apple iPhone 6s')]").click();

		// Unlimited as Monthly data allowance

		WebElement ele = driver.findElement(By.xpath("(//select[@class='form-control cat_item_option ']) [1]"));
		Select dd = new Select(ele);
		dd.selectByValue(data);

		// Select color as rose gold

		WebElement ele1 = driver.findElement(By.xpath("(//select[@class='form-control cat_item_option ']) [2]"));
		Select dd1 = new Select(ele1);
		dd1.selectByVisibleText(color);

		// Select Storage

		WebElement ele2 = driver.findElement(By.xpath("(//select[@class='form-control cat_item_option ']) [3]"));
		Select dd2 = new Select(ele2);
		dd2.selectByValue(storage);

		// Select order now & Javascript Click given to overcome element intercept
		// exception

		WebElement ele3 = driver.findElement(By.xpath("//button[@id='oi_order_now_button']/span[text()='Order Now']"));
		driver.executeScript("arguments[0].click();", ele3);

		// Verify the order and print the request Number

		String verifyText = driver.findElement(By.xpath("//div[@class='notification notification-success']/span"))
				.getText();
		String reqNum = driver.findElement(By.xpath("//a[@id='requesturl']/b")).getText();

		if (verifyText.contains("has been submitted")) {
			System.out.println(verifyText);
			System.out.println("Request number is : " + reqNum);
		} else {
			System.out.println("order not placed");

		}

	}

	@DataProvider(name = "fetchData")
	public String[][] orderExcelData() throws IOException {

		String[][] orderData = BaseClass_DataProvider_ReadExceldata.readExcelData("order");
		return orderData;
	}

}
