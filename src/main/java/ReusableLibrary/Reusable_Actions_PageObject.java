package ReusableLibrary;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class Reusable_Actions_PageObject {

    private ExtentTest logger;

    //create a reusable method for navigate to a page
    public static WebDriver defineTheDriver() throws InterruptedException, IOException {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        //define the chromeoptions arguments
        ChromeOptions options = new ChromeOptions();
        //maximize my driver
        //options.addArguments("start-maximized");
        //set the driver to incognito(private)
        //options.addArguments("incognito");
        //set it to headless
        //options.addArguments("headless");
        //define the webdriver
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }//end of define driver method



      //method to click on any webelement by explicit wait
    public static void clickOnElement(WebDriver driver, WebElement xpathLocator, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //use try catch to locate an element and click on it
        try {
            wait.until(ExpectedConditions.visibilityOf(xpathLocator)).click();
            logger.log(LogStatus.PASS,"Successfully clicked " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to click on element " + elementName + " " + e);
            logger.log(LogStatus.FAIL,"Unable to click on a element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of click method




    //method to type on any webelement by explicit wait
    public static void sendKeysMethod(WebDriver driver, WebElement xpathLocator, String userValue, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //use try catch to locate an element and click on it
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpathLocator));
            element.clear();
            element.sendKeys((CharSequence) userValue);
            logger.log(LogStatus.PASS,"Successfully entered a value on " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to enter value on element " + elementName + " " + e);
            logger.log(LogStatus.FAIL,"Unable to enter value on element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of sendkeys method



    //method to submit on any webelement by explicit wait
    public static void submitOnElement(WebDriver driver, WebElement xpathLocator, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //use try catch to locate an element and click on it
        try {
            wait.until(ExpectedConditions.visibilityOf(xpathLocator)).submit();
            logger.log(LogStatus.PASS,"Successfully submit on " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to submit on element " + elementName + " " + e);
            logger.log(LogStatus.FAIL,"Unable to submit on element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of submit method


    //method for Dropdown by explicit wait
    public static void dropdownByText(WebDriver driver,WebElement xpath,String userValue, ExtentTest logger, String elementName){
        WebDriverWait wait = new WebDriverWait(driver,20);
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpath));
            Select dropDown = new Select(element);
            dropDown.selectByVisibleText(userValue);
            logger.log(LogStatus.PASS,"Successfully select on dropdown text " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to select a value from " + elementName + " " + e);
            logger.log(LogStatus.FAIL,"Unable to select on dropdown text " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of dropdown by text


    //method for captureText by explicit wait
    public static String captureText(WebDriver driver,WebElement xpath,int index, ExtentTest logger, String elementName){
        WebDriverWait wait = new WebDriverWait(driver,10);
        String result = "";
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOfAllElements(xpath)).get(index);
            result = element.getText();
            logger.log(LogStatus.PASS,"Successfully captured a text on " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to select a value from " + elementName + " " + e);
            logger.log(LogStatus.FAIL,"Unable to select a value from " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
        return result;
    }//end of captureText


    //method to hover over any webelement by explicit wait
    public static void hoverOverElement(WebDriver driver, WebElement xpathLocator, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //define the mouse actions
        Actions actions = new Actions(driver);

        //use try catch to locate an element and hover over it
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpathLocator));
            actions.moveToElement(element).perform();
            logger.log(LogStatus.PASS, "Successfully hover to " + elementName);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Unable to hover over element " + elementName + " " + e);
            logger.log(LogStatus.FAIL, "Unable hover to " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of hover method



    //method for click on hover element by explicit wait
    public static String clickOnHoverElement(WebDriver driver,WebElement xpath,int index,ExtentTest logger, String elementName){
        WebDriverWait wait = new WebDriverWait(driver,10);
        String result = "";
        try{
            wait.until(ExpectedConditions.visibilityOfAllElements(xpath)).get(index).click();
            logger.log(LogStatus.PASS, "Successfully click on hover element " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to select a value from " + elementName + " " + e);
            logger.log(LogStatus.FAIL, "Unable to click on hover element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
        }
        return result;
    }//end of click on hover click element




    //method to close pop by explicit wait
    public static void closePopupIfExist(WebDriver driver, WebElement xpathLocator, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //use try catch to locate an element and click on it
        try {
            driver.switchTo().alert().dismiss();
            wait.until(ExpectedConditions.visibilityOf(xpathLocator)).submit();
            logger.log(LogStatus.PASS, "Successfully close pop up " );
        } catch (Exception e) {
            System.out.println("Popup is not present. Move on the next step");
            logger.log(LogStatus.FAIL, "Unable to close pop up " + e);
            getScreenShot(driver,elementName,logger);
        }
    }//end of close pop up method


    //method to capture screenshot when logger fails
    public static void getScreenShot(WebDriver driver, String imageName, ExtentTest logger) {
        try {
            String fileName = imageName + ".png";
            String directory = null;
            String snPath = null;
            directory = "src/main/java/HTML_Report/Screenshots/";
            snPath = "Screenshots/";
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = logger.addScreenCapture(snPath + fileName);
            logger.log(LogStatus.FAIL, "", image);
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Error Occured while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }//end of getScreenshot method


} // end of java class



/*

    //method to scroll to webelement by explicit wait
    public static void scrollToElement(WebDriver driver, String xpathLocator, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 20);

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        //store the locator as a element that I want to scroll by for a view

        //use try catch to locate an element and hover over it
        try {
            WebElement addToBag = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator)));
            jse.executeScript("arguments[0].scrollIntoView(true);",addToBag);
        } catch (Exception e) {
            System.out.println("Unable to hover over element " + elementName + " " + e);
        }
    }//end of hover method




 //method to click on any webelement by explicit wait
    public static void clickFromList(WebDriver driver, String xpathLocator, String userValue, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //use try catch to locate an element and click on it
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator))).click();
        } catch (Exception e) {
            System.out.println("Unable to click on element " + elementName + " " + e);
        }
    }//end of click method





    //create a reusable method for navigate to a page
    public static WebDriver defineTheDriver() throws InterruptedException, IOException {
        //kill all chrome instances that are opened
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
        Thread.sleep(1000);
        //set the path to the driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        //define the chromeoptions arguments
        ChromeOptions options = new ChromeOptions();
        //maximize my driver
        options.addArguments("start-maximized");
        //set the driver to incognito(private)
        options.addArguments("incognito");
        //set it to headless
        //options.addArguments("headless");
        //define the webdriver
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }//end of navigate method
 */