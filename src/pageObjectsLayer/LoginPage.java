package pageObjectsLayer;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import BaseFramework.UIModule;
import net.bytebuddy.asm.Advice.Enter;

public class LoginPage extends UIModule {

	private By obj_name_username=By.name("username");
	private By obj_name_password=By.name("password");
	private By obj_xpath_login_button=By.xpath("//input[@type='submit']");
	private By obj_xpath_Deal=By.xpath("//a[text()='Deals'");
	private By obj_xpath_NewDeal=By.xpath("//a[text()='New Deal']");
	private By obj_xpath_KeyWord=By.xpath("//input[@name='cs_keyword']");
	
	
	public void loginCrm(String Username,String Password,ExtentTest logger) throws Exception
	{
		driverthread.get("https://www.freecrm.com//login.cfm");
		logger.log(LogStatus.PASS, "Succesfully login to CRM application");
		enter(obj_name_username,Username);
		logger.log(LogStatus.PASS, "User Name enter as :"+Username);
		enter(obj_name_password,Password);
		logger.log(LogStatus.PASS, "User Name enter as :"+Password);
		click(obj_xpath_login_button);
		logger.log(LogStatus.PASS, "User Name enter as :"+Username);
		String dest=captuteScreenshots("CRM");
		logger.log(LogStatus.PASS,logger.addScreenCapture(dest));
	}

	public void createNewDeal(ExtentTest logger) throws Exception
	{
          click(obj_xpath_Deal);
          click(obj_xpath_NewDeal);
          enter(obj_xpath_KeyWord,"Murali");
		
	}
}
