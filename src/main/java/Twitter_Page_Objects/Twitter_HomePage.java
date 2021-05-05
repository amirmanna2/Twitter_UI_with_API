package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObject;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Twitter_HomePage extends Abstract_Class {

    ExtentTest logger;

    public Twitter_HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = Abstract_Class.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//a[@data-testid='loginButton']")
    WebElement signInButton;



    //create a method for click
    public void signInButton() {
        Reusable_Actions_PageObject.clickOnElement(driver, signInButton, logger, "sign in button");
    }//end of click method


}//end of java class