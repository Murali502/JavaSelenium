package BaseFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.csvreader.CsvReader;
import com.relevantcodes.extentreports.ExtentTest;



public class UIModule {

	
	public static WebDriver driverthread;
	static Dictionary dict=new Hashtable();
	static Dictionary dicttoread=new Hashtable();
	public Properties prop;
	public String mainwindow;
	public static ExtentTest logger;
	//public static WebDriver
	
	public WebDriver intializeDriver(String browser)
	{
	try {
	if(browser.toUpperCase().equals("CHROME")||browser.toUpperCase().equals("C"))
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\girish\\Desktop\\Resources\\chromedriver_win32 (1)\\chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		options.addArguments("no-sandbox");
		Map<String,Object> prefs=new HashMap();
		options.setExperimentalOption("prefs",prefs);
		driverthread =new ChromeDriver(options);
	}
	else if(browser.toUpperCase().equals("FIREFOX")||browser.toUpperCase().equals("F"))
	{
		System.setProperty("WebDriver.Chrome.Driver", "‪C:\\Users\\girish\\Desktop\\Resources\\chromedriver.exe");
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_PROFILE,"defaut");
		driverthread =new FirefoxDriver();
		
	}
	else if(browser.toUpperCase().equals("InternetExplorer")||browser.toUpperCase().equals("IE"))
	{
		System.setProperty("WebDriver.Chrome.Driver", "‪C:\\Users\\girish\\Desktop\\Resources\\chromedriver.exe");
		System.setProperty(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, "true");
		System.setProperty(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, "true");
		System.setProperty(InternetExplorerDriver.IGNORE_ZOOM_SETTING, "true");
		driverthread =new InternetExplorerDriver();
	}
	driverthread.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	catch(WebDriverException e)
	{
	e.printStackTrace();	
	}
	return driverthread;
	}
	
	public String getdata(String t_testcase,String value,int Instance) throws Exception
	{
		int flag=0;
		CsvReader csvreader=new CsvReader("C:\\Users\\girish\\Desktop\\Resources\\TestdataCsv.csv");
		
		csvreader.readHeaders();
		while(csvreader.readRecord())
		{
			String Testcasename=csvreader.get("Testcasename").trim();
			String TestcaseInstance=csvreader.get("instance").trim();
			if((t_testcase.equalsIgnoreCase(Testcasename)) && Instance==Integer.parseInt(TestcaseInstance))
			{
				for (int i = 1; i < csvreader.getColumnCount()/2+1; i++) {
					String field=csvreader.get("Field"+i).trim();
					String Value=csvreader.get("Value"+i).trim();
					dicttoread.put(field, Value);
				}
				flag=0;
				break;
			}
			else
			{
			flag=1;	
			}
			
		}
		
		if(flag==1)
		{
			System.out.println("no data present for testname");
		}
		
        return (String) dicttoread.get(value);
	}
	
	public void enter(By by,String value) throws Exception
	{
		WebElement webelement=fluentwait(by,10);
		
		if(webelement==null)
		{
			System.out.println("unable to locate element"+by);
			throw new Exception("unable to locate element"+by);
		}
		else
		{
			webelement.sendKeys(value);
		}
	}
	
	public WebElement fluentwait(By locator,int time)
	{
		
		WebElement r_currentElement=null;
		Wait<WebDriver> p_wait=null;
		try {
			p_wait=new FluentWait<WebDriver>(driverthread).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofMillis(200))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class);
					r_currentElement=p_wait.until(ExpectedConditions.elementToBeClickable(locator));
					return r_currentElement;
		}
		catch(Exception e)
		{
			System.out.println("waiting for the element to display on Dom");
		}
		
		return r_currentElement;
	}
		
	public void compareTextByText(By by,String TextToCompared) throws Exception	
	
	{
		WebElement element=fluentwait(by,10);
		
		if(element==null)
		{
			throw new Exception("unable to locate element");
		}
		else
		{
			element.click();
			String applicationText=element.getText();
			if(applicationText.equalsIgnoreCase(TextToCompared))
			{
				System.out.println(applicationText+""+ "Application data Matched with" + "" +TextToCompared);
			}
			else
			{		
				throw new Exception(applicationText+""+ "Application data not Matched with Expected Data" + "" +TextToCompared);
			}
		}
	}
	
	public void selectByIndex(By by,int index) throws Exception
	{
        WebElement element=fluentwait(by,10);
		
		if(element==null)
		{
			System.out.println("unable to locate drop down text "+by);
			throw new Exception("unable to locate drop down text "+by);
		}	
		else
		{
			Select dropdown=new Select(element);
			dropdown.selectByIndex(index);
		}
	}
	
	public void MouseOver(By by1,By by2)
	{
		WebElement element1=fluentwait(by1,10);
		WebElement element2=fluentwait(by2,10);
	    Actions action=new Actions(driverthread);
	    action.moveToElement(element1).moveToElement(element2).build().perform();
	    
			
	}

    public String GetAttributevalue(By by) throws Exception
    {
    	String attributeValue;
    	WebElement element=fluentwait(by,10);
    	
    	if(element==null)
    	{
    		System.out.println("unable to locate drop down text "+by);
			throw new Exception("unable to locate drop down text "+by);	
    	}
    	else
    	{
    		attributeValue=element.getAttribute("value");
    		System.out.println("Element located by " +by);
    	}
    	
		return attributeValue;
    	
    }
    
    
    public String captuteScreenshots(String Screeshotname)
    {
    	try {
    	File source=((TakesScreenshot)driverthread).getScreenshotAs(OutputType.FILE);
    
    	String dest=System.getProperty("user.dir")+"/Screenshots/"+System.currentTimeMillis(); 
    	File destination=new File(dest);
    	FileUtils.copyFile(source, destination);
    	System.out.println("Screenshot taken");
    	return dest;
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
    	
    }
  
   
    public String genrateRandomString(String t_name,int t_length)
    {
    	char[] p_chars="dfdfakjdfkfjd".toCharArray();
    	Random p_random=new Random();
    	
    	StringBuilder r_sb=new StringBuilder();
    	r_sb.append(t_name);
    	for (int i = 0; i < t_length; i++) {
			
    		char c=p_chars[p_random.nextInt(p_chars.length)];
    		r_sb.append(c);
		}
    	
    	return r_sb.toString();
    }
    
    public void deleteDir(File file)
    {
    
    	File [] contents=file.listFiles();
    	
    	if(contents!=null)
    	{
    		for(File f:contents)
    		{
    			f.delete();
    		}
    	}
    	
    }
    
    public void SystemDateFormat()
    {
    	try {
    		
    		DateFormat date =new SimpleDateFormat();
    		Date date1=new Date();
    		String abc1=date.format(date1);
    	}
    	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		System.out.println(e);
    	}
    }
    
    public String sysDate()
    {
		DateFormat date= new SimpleDateFormat("dd/mm/yyyy");
    	LocalDate localdate=LocalDate.now();
    	return date.format(localdate);
    	
    }
    
    
    public void CreateImageDoc()
    {
    	try
    	{
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		System.out.println(e.getStackTrace());
    	}
    }

    

    public void ElementHasAnyText(By by) throws Exception
    {
    	String attributeValue;
    	WebElement element=fluentwait(by,10);
    	
    	if(element==null)
    	{
    		System.out.println("unable to locate drop down text "+by);
			throw new Exception("unable to locate drop down text "+by);	
    	}
    	else
    	{
    		String ApplicationText=element.getText();
    		if(ApplicationText.length()>0)
    		{
    			
    			System.out.println("Application data is present");
    		}
    		else
    		{
    			throw new Exception("Application data is not present");
    		}
    	}
    	    	
    }
    
    public String ReadNotePad(String PathofFile) throws IOException
    {
    	
    	BufferedReader br=new BufferedReader(new FileReader(PathofFile));
    	
    	try {
    		StringBuilder sb=new StringBuilder();
    		String line=br.readLine();
    		while(line!=null)
    		{
    			sb.append(line);
    			line=br.readLine();
    		}
    		return sb.toString();
    	}
         finally
         {
        	 br.close();
         }
    	
    }
    
    public void click(By by) throws Exception
	{
		WebElement webelement=fluentwait(by,10);
		
		if(webelement==null)
		{
			System.out.println("unable to locate element"+by);
			throw new Exception("unable to locate element"+by);
		}
		else
		{
			webelement.click();;
		}
	}
	
    
}

