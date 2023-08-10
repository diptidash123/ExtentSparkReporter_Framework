package TestPackageSparkReport;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SparkReportExtent {
	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("target/SparkReport5.html");
	
	WebDriver driver;
	
	@BeforeTest
    public void openbrowser()
    {
		//spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("DiptiExtentReport");
		spark.config().setTheme(Theme.STANDARD);
		extent.attachReporter(spark);
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Browser launched successfully");
		driver.get("https://www.google.com");
    }
	
	@AfterTest
	public void closebrowser()
	{
		extent.flush();
		driver.close();
	}

	@Test(priority = 1)
	public void TestingMethod1()
	{
		System.out.println("verifying the page title of the webpage");
		ExtentTest test=extent.createTest("verify the pagetitle").assignAuthor("Dipti Ranjan Dash").assignCategory("functionality").assignDevice("windows 11");
		System.out.println("Testing Method execution started for validation");
		test.info("capturing the titile");
		String ATitle=driver.getTitle();
		String ETitle="Google";
		if (ATitle.equals(ETitle))
		{
			System.out.println("The title is correct");
			test.pass("The title is correct");
			test.addScreenCaptureFromPath(screencapture(driver));
		}
		else {
			System.out.println("The title is incorrect");
			test.fail("The title is incorrect");
		}
	}
	
	@Test(priority = 2)
	public void TestingMethod2() throws InterruptedException
	{
		System.out.println("verifying the page url of the webpage");
		ExtentTest test=extent.createTest("verifying the page url").assignAuthor("Dipti Ranjan Dash").assignCategory("smoke").assignDevice("windows 11 OS");
		String Aurl=driver.getCurrentUrl();
		String Eurl="https://www.google.com";
	    if (Aurl.equals(Eurl)) {
			test.pass("url is correct"+" "+ Aurl);
			test.addScreenCaptureFromPath(screencapture(driver));
		}
	    else {
			test.fail("url is incorrect"+" "+ Aurl);
			test.addScreenCaptureFromPath(screencapture(driver));
		}	
	    test.info("The validation for url is started");
	    
	    //click to about element 
	    WebElement clickoperation=driver.findElement(By.xpath("(.//a[@class=\"pHiOh\"])[1]"));
	    JavascriptExecutor js=(JavascriptExecutor) driver;
	    js.executeScript("arguments[0].click();",clickoperation);
	    Thread.sleep(3000);
	    System.out.println("user navigated to about page");
	    
	    driver.navigate().back();
	    Thread.sleep(3000);
	    test.warning("Test locator is wrong");
	    test.addScreenCaptureFromPath(screencapture(driver));
	    driver.findElement(By.xpath("(//a[@class=\"pHi\"])[2]")).click();
	    test.skip("This step is skipped");
	    driver.findElement(By.xpath("(//a[@class=\"i\"])[2]")).click();
	    
		
	}
	
	public static String screencapture(WebDriver driver)
	{
		File sourcefile =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File targetfile = new File("D:\\Eclipse Folder\\org.ExtentSparkReporter\\screenshotsfolder\\Image"+System.currentTimeMillis()+".png");
		String Mainpath=targetfile.getAbsolutePath();
		try {
			FileUtils.copyFile(sourcefile, targetfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Mainpath;
		
	}
}