package BaseFramework;

import com.google.common.io.Resources;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentManager  {
	
	//private static ExtentReports reporter;
	public static ExtentReports reporter;
	
	 
    public synchronized static ExtentReports getReporter(String path){
        if(reporter == null){
            //Set HTML reporting file location
            //String workingDir = System.getProperty("user.dir");
            reporter = new ExtentReports(path, false);
            reporter.addSystemInfo("Test Environment","Test 1");
            reporter.addSystemInfo("Test Cycle","Regression");
            reporter.addSystemInfo("UserName","Murali Golla");
            //reporter.loadConfig(Resources.getResource("extent-config.xml"));
        }
        return reporter;
    }
	
    public static synchronized void closeReporter() {
    	
    	reporter.flush();
    	reporter.close();
    }

    
}
