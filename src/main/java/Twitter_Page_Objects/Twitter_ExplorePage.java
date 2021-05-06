package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObject;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Twitter_ExplorePage extends Abstract_Class {

    ExtentTest logger;

    public Twitter_ExplorePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = Abstract_Class.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//a[@aria-label='Tweet']")
    WebElement tweet;
    @FindBy(xpath = "//div[@role='textbox']")
    WebElement message;
    @FindBy(xpath = "//div[@data-testid='tweetButton']")
    WebElement tweetButton;
    @FindBy(xpath = "//*[text()='This is a UI automation test']")
    WebElement verifyTweet;
    @FindBy(xpath = "//div[@aria-label='Account menu']")
    WebElement options;
    @FindBy(xpath = "//a[@data-testid='AccountSwitcher_Logout_Button']")
    WebElement signOut;
    @FindBy(xpath = "//div[@data-testid='confirmationSheetConfirm']")
    WebElement confirmSignOut;


    //create a method for click
    public void tweet() {
        Reusable_Actions_PageObject.clickOnElement(driver, tweet, logger, "tweet button");
    }//end of click method


    //create a method for sendkeys
    public void message(String userValue) {
        Reusable_Actions_PageObject.sendKeysMethod(driver, message, userValue, logger, "message");
    }//end of sendkeys method


    //create a method for click
    public void tweetButton() {
        Reusable_Actions_PageObject.clickOnElement(driver, tweetButton, logger, "tweet button");
    }//end of click method



    //create a method for capture text
    public void verifyTweet() {

        if(verifyTweet.equals("This is a UI automation test")){
            System.out.println("Latest tweet posted");
        } else {
            System.out.println("No tweet posted");
        }

    }//end of capture text method


    //create a method for click
    public void option() {
        Reusable_Actions_PageObject.clickOnElement(driver, options, logger, "option");
    }//end of click method


    //create a method for click
    public void signOut() {
        Reusable_Actions_PageObject.clickOnElement(driver, signOut, logger, "sign out");
    }//end of click method


    //Beginning of click on confirm sign out method
    public void confirmSignOut(){
        Reusable_Actions_PageObject.clickOnElement(driver, confirmSignOut, logger, "confirm log out");
    }

}//end of java class