package week5.day2.assignment.servicenowApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.sukgu.Shadow;

public class CreateArticle_ServiceNowApp extends BaseClass_DataProvider_ReadExceldata {

	@Test(dataProvider = "article")

	public void createArticleServiceNowApp(String shortDesp, String detailDesp) throws InterruptedException {

		// Create instance in Shadow Class to inspect elements in Shadow Root DOM

		Shadow dom = new Shadow(driver);
		dom.setImplicitWait(10);

		// Click-All

		dom.findElementByXPath("//div[text()='All']").click();
		Thread.sleep(3000);

		// Enter callers in filter navigator and press enter

		WebElement cc1 = dom.findElementByXPath("//input[@id='filter']");
		cc1.sendKeys("knowledge");
		WebElement cc2 = dom.findElementByXPath("//mark[text()='Knowledge']");
		driver.executeScript("arguments[0].click();", cc2); // JavaScript Click

		// Switch to frame (Inside the shadow Root)

		WebElement frame = dom.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);

		// Click on Create an Article

		driver.findElement(
				By.xpath("//button[@data-original-title='Create an Article']/span[text()='Create an Article']"))
				.click();

		// Enter knowlegde base , short description
		// Select IT as Knowledge Base

		driver.findElement(By.xpath("//button[@id='lookup.kb_knowledge.kb_knowledge_base']")).click();
		Set<String> windowHandles1 = driver.getWindowHandles();
		List<String> windowHandles2 = new ArrayList<String>(windowHandles1);
		driver.switchTo().window(windowHandles2.get(1)); // Switch to resulting window
		driver.findElement(By.xpath("//td[contains(@class,'list_decoration')]/following-sibling::td/a")).click();
		driver.switchTo().window(windowHandles2.get(0)); // Switch back to previous window
		Thread.sleep(3000);
		driver.switchTo().frame(frame); // Switch to frame

		// Select Knowledge Category

		WebElement eleCat = driver.findElement(By.xpath("//button[@id='lookup.kb_knowledge.kb_category']/span"));
		eleCat.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//h1[text()='Category picker']/following::span[text()='IT']")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//span[text()='Java']")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();

		driver.findElement(By.xpath("//input[@id='kb_knowledge.short_description']")).sendKeys(shortDesp);

		// Enter detailed Article description which is inside a frame

		WebElement frameArticle = driver.findElement(By.xpath("//iframe[@id='kb_knowledge.text_ifr']"));
		driver.switchTo().frame(frameArticle);

		driver.findElement(By.xpath("//body[@data-id='kb_knowledge.text']/p")).click();
		driver.findElement(By.xpath("//body[@data-id='kb_knowledge.text']/p")).sendKeys(detailDesp);

		// Switch to parent frame from Nested frame

		driver.switchTo().parentFrame();

		// Click on Submit

		driver.findElement(By.xpath("//div[@class='form_action_button_container']/button[text()='Submit']")).click();

	}

	@DataProvider(name = "article")

	    public String[][] articleExcelData() throws IOException {
		
		String[][] articleExcelData = BaseClass_DataProvider_ReadExceldata.readExcelData("create_article_data");
		return articleExcelData;
	}

}
