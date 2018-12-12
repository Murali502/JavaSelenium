package testcaseDefinationLayer;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import BaseFramework.ExtentManager;
import BaseFramework.UIModule;
import pageObjectsLayer.LoginPage;

public class LoginCRM extends UIModule{

	LoginPage login=new LoginPage();
	ExtentManager report=new ExtentManager();
   
	ExtentReports reporter=report.getReporter("C:\\Users\\girish\\Desktop\\Resources\\report.html");
	
	@Test
	public void logincrm() throws Exception
	{
		String testcasename="Login to CRM application";
		ExtentTest logger=reporter.startTest(testcasename);
		String browser="Chrome";
		String Username=getdata(testcasename,"Username",1);
		String Password=getdata(testcasename,"Password",1);
		intializeDriver(browser);
		
		
		login.loginCrm(Username, Password,logger);
		login.createNewDeal(logger);
		
		reporter.endTest(logger);
		driverthread.quit();
	}
	
	@AfterTest
	public void closeereport()
	{
		report.closeReporter();
		
	}
}
