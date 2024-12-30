package base.driver;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import base.App;
import base.ExtentManager;
import base.ExtentReport;
import base.Util;

public class BaseTest {
    WebDriverWait wait;
    //WebDriver driver = null;
    
    
    @BeforeSuite
    public void setUpLogs() {
    	Util.getLogger();
    }
   
    @Parameters({"browser"})
    //@BeforeClass
    @BeforeMethod
    public void setUp(@Optional String browser, ITestResult result) throws MalformedURLException {
    //public void setUp() throws MalformedURLException {

        /*browser will be null if we don't run the test from testNG.xml file
         *so if the browser is null, then get the browser value from App.java file
         *You can change the default value of browser=chrome by passing the browser
         * from maven command line as mvn test -Dbrowser=firefox
         */
    	
    	Util.logger.info(result.getMethod().getMethodName()+ "--Started");
        if(browser == null){
            browser = App.browser;
        }

        DriverFactory.openBrowser(browser);


        System.out.println("");
        System.out.println("------driver initiated------");
        System.out.println("Running on thread - " + Thread.currentThread().getId());
        System.out.println("Tests running on " +
            ((RemoteWebDriver) PageDriver.getCurrentDriver()).getCapabilities().getBrowserName());

        ExtentTest test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
        ExtentReport.setTest(test);
        //cap.getBrowserName());
    }

    //@BeforeMethod
    public void setUpSparktest(ITestResult result){
//        ExtentTest test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
//        ExtentReport.setTest(test);
    }

    @AfterMethod
    public void SparktestResult(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            ExtentReport.getTest().log(Status.FAIL, "test case failed as - " +
                    result.getThrowable());
            String screenshotPath = Util.getScreenshot(result.getMethod().getMethodName()+".jpg");

             /*ExtentReport.getTest()
                    .addScreenCaptureFromPath(screenshotPath)
                    .fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    */
            ExtentReport.getTest().fail(MediaEntityBuilder
                    .createScreenCaptureFromBase64String(Util.convertImg_Base64(screenshotPath)).build());
            
            Util.logger.info(result.getMethod().getMethodName()+ "--Failed");
        }else{
        	if(result.getStatus() == ITestResult.SUCCESS) {
        		Util.logger.info(result.getMethod().getMethodName()+ "--Passed");
        	}else if(result.getStatus() == ITestResult.SKIP) {
        		Util.logger.info(result.getMethod().getMethodName()+ "--Skipped");
        	}
        }
        
        if(PageDriver.getCurrentDriver() != null) {
        	System.out.println();
            PageDriver.getCurrentDriver().quit();
        }
    }

    //@AfterClass
    public void tearDown(){
        //PageDriver.getInstance().getDriver().quit();
        //PageDriver.getDriver().quit();
//        if(PageDriver.getInstance() != null) {
//            PageDriver.getCurrentDriver().quit();
//        }
    }

    @AfterSuite
    public void sparkFlush(){
        ExtentManager.getInstance().flush();
    }
}
