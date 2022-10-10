package week5.day2.assignment.servicenowApp;

import java.io.IOException;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.core.gherkin.Argument;
import io.github.sukgu.Shadow;

// Proposal Creations in ServiceNow application

public class CreateNewProposal_ServiceNowApp extends BaseClass_DataProvider_ReadExceldata {

	@Test(dataProvider = "proposalData")
	public void createProposalTest(String tempName, String tempDesp, String detailDesp) throws InterruptedException {

		// Create instance in Shadow Class to inspect elements in Shadow Root DOM

		Shadow dom = new Shadow(driver);
		dom.setImplicitWait(10);

		// Click-All

		dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(3000);

		// Enter Proposal in filter navigator and press enter

		WebElement prop1 = dom.findElementByXPath("//input[@id='filter']");
		prop1.sendKeys("proposal");
		WebElement prop2 = dom.findElementByXPath("//mark[text()='Proposal']");
		driver.executeScript("arguments[0].click();", prop2); // JavaScript Click

		// Switch to frame (Inside the shadow Root)

		WebElement frame = dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);

		// Click on New to create a new proposal

		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// Give Template Name

		driver.findElement(By.xpath("//input[@name='std_change_proposal.template_name']")).sendKeys(tempName);

		// Give Template Description

		driver.findElement(By.xpath("//input[@name='std_change_proposal.short_description']")).sendKeys(tempDesp);

		// Give Description in Detail

		driver.findElement(By.xpath("//textarea[@aria-label='Description']")).sendKeys(detailDesp);

		// Click on Submit to sumbit the proposal

		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		// Verify the created proposals
		
		String verifyProposal1 = driver.findElement(By.xpath("//td[text()='Change Storage']")).getText();

		if (verifyProposal1.contains("Change Storage")) {
			System.out.println("Proposal created successfully");
			System.out.println("Proposal name is : " + verifyProposal1);
		} else {
			System.out.println("Proposal1 not created");
		}


		String verifyProposal2 = driver.findElement(By.xpath("//td[text()='Change RAM']")).getText();

		if (verifyProposal2.contains("Change RAM")) 
		{
			System.out.println("Proposal created successfully");
			System.out.println("Proposal name is : " + verifyProposal2);
		} else {
			System.out.println("Proposal2 not created");
		}

	}

	@DataProvider(name = "proposalData")
	public String[][] proposalExcelData() throws IOException {

		String[][] proposalData = BaseClass_DataProvider_ReadExceldata.readExcelData("proposal");

		return proposalData;

	}

}