package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObject;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Twitter_LoginPage extends Abstract_Class {

    ExtentTest logger;

    public Twitter_LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = Abstract_Class.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//input[@name='session[username_or_email]']")
    WebElement userName;
    @FindBy(xpath = "//input[@name='session[password]']")
    WebElement userPassword;
    @FindBy(xpath = "//div[@data-testid='LoginForm_Login_Button']")
    WebElement loginButton;


    //create a method for sendkeys
    public void userName(String userValue) {
        Reusable_Actions_PageObject.sendKeysMethod(driver, userName, userValue, logger, "username");
    }//end of sendkeys method


    //create a method for sendkeys
    public void userPassword(String userValue) {
        Reusable_Actions_PageObject.sendKeysMethod(driver, userPassword, userValue, logger, "password");
    }//end of sendkeys method


    //create a method for click
    public void loginButton() {
        Reusable_Actions_PageObject.clickOnElement(driver, loginButton, logger, "login button");
    }//end of click method

}//end of java class