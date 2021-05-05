package ReusableLibrary;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class Abstract_Class_For_ActionItem6 {

    public static WebDriver driver = null;
    public static ExtentReports reports = null;
    public static ExtentTest logger = null;
    public static Workbook readableBook = null;
    public static WritableWorkbook writableWorkbook = null;
    public static WritableSheet writableSheet = null;


    //before suite annotation
    @BeforeSuite
    public void setDriver() throws IOException, InterruptedException, BiffException {
        driver = Reusable_Actions.defineTheDriver();
        //set the path to the report that I want to use
        reports = new ExtentReports("src/main/java/HTML_Report/Automation_Report.html");

        //set the driver using reusable method
        WebDriver driver = Reusable_Actions.defineTheDriver();

    }//end of before suite




    //before method will start the log for your report and capture the test Name
    @BeforeMethod
    public void captureTestName(Method methodName) {
        logger = reports.startTest(methodName.getName());
    }//end of before method





    //after method will end the logger(print statement for the report) for individual test
    @AfterMethod
    public void endLogger() {
        reports.endTest(logger);
    }//end of after method





    @AfterSuite
    public void closeDriver() throws IOException, WriteException {
        //flush the logs for the report
        reports.flush();
        //quit the driver
        driver.quit();
    }//end of after suite

}// end of java class