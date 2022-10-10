package week5.day2.assignment.servicenowApp;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass_ServiceNowApp_ReadExcelDataSheetWise {

	public ChromeDriver driver;

	@Parameters({ "url", "username", "password" })
	@BeforeMethod

	public void loginServicenow(String url, String username, String password) throws InterruptedException {

		// Launch ServiceNow application & Login with valid credentials username as
		// admin and password

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[text()='Log in']")).click();
		Thread.sleep(3000);

	}

	// Read Data from Excel Common Method - order creation

	public static String[][] readExcelData(String sheetName) throws IOException {

		XSSFWorkbook book = new XSSFWorkbook("./ExcelData/serviceNowAppData.xlsx");
		
		// index based approach , all excel index starts with 0
		//XSSFSheet sheet = book.getSheetAt(0);
		
		// based on sheet name 
		
		XSSFSheet sheet = book.getSheet("sheetName");

		// It returns row count except header
		int rowCount = sheet.getLastRowNum();
		XSSFRow row = sheet.getRow(1);
		int cellCount = row.getLastCellNum();

		// create a string array to store the fetched data from excel wrt row and cell
		// count
		String[][] data = new String[rowCount][cellCount];

		// read the data from Excel sheet wrt to cell- two dimensional

		for (int i = 1; i <= rowCount; i++) {

			for (int j = 0; j < cellCount; j++) {

				String stringCellValue = sheet.getRow(i).getCell(j).getStringCellValue();
				data[i - 1][j] = stringCellValue;
			}
		}

		return data;

	}


	@AfterMethod

	public void postCondn() {

		driver.close();

	}

}


