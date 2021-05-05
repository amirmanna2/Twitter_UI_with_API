package API_Automation;

import ReusableLibrary.Abstract_Class;
import Twitter_Page_Objects.Twitter_BaseClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.awt.*;

import static io.restassured.RestAssured.given;

public class Twitter_Action_Item_1 extends Abstract_Class {


    String consumerKey = "NCLQLFcVta2t8mvijVsrxBlD1";
    String consumerSecret = "xoRdZatFJ6iQHxRoJvv8RuARMf282Z2GEXDBOQ4J0V9gQOcsPT";
    String accessToken = "1388499106063077377-M0E8sRKhZtiKqU3HfsVQoyknfacRku";
    String tokenSecret = "hSGU7RWFp5XhvTmTyhXaHb6x7cVFtkhqAE8s22PlGaSz6";

    String verifyTweet = null;
    String tweetID;


    @BeforeMethod
    public void setup() { RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/"; }//end of set up



    //Use case 1

    @Test()
    public void Twitter_Action_Item_1() throws InterruptedException, AWTException {

        //Post tweet through UI

        driver.navigate().to("https://twitter.com/");
        Thread.sleep(3000);


        Twitter_BaseClass.twitterHomepage().signInButton();
        Thread.sleep(2000);

        Twitter_BaseClass.twitterLoginpage().userName("Amir61155473");
        Twitter_BaseClass.twitterLoginpage().userPassword("Triage2021");
        Twitter_BaseClass.twitterLoginpage().loginButton();
        Thread.sleep(2000);

        Twitter_BaseClass.twitterExplorepage().tweet();
        Thread.sleep(1000);
        Twitter_BaseClass.twitterExplorepage().message("This is a UI automation test.");
        Twitter_BaseClass.twitterExplorepage().tweetButton();
        Thread.sleep(2000);



        //get recent tweet through API

        Response Resp=
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Amir61155473")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        String getTweet=Resp.asString();
        JsonPath js=new JsonPath(getTweet);
        String text1= js.get("text[0]").toString();
        System.out.println("The recent tweet is " + "" + text1 + "");
        verifyTweet=(js.get("id[0]")).toString();
        System.out.println("Id of most recent Tweet is "+verifyTweet);




        // delete recent tweet through API
        given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", verifyTweet)
                .when().post("destroy.json")
                .then();
        System.out.println("Recent twitter message with id '" + verifyTweet + "' has been deleted");



        //refresh page using UI and verify tweet is deleted

        driver.navigate().refresh();
        Thread.sleep(2000);

        if(verifyTweet.equals("This is a UI automation test.")){
            System.out.println("Tweet is present");
        }
        else {
            System.out.println("Tweet is actually deleted");
        }



        //sign out of twitter

        Twitter_BaseClass.twitterExplorepage().option();
        Twitter_BaseClass.twitterExplorepage().signOut();


    }//end of test




    //Use case 2

    @BeforeMethod
    public void setup2() { RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/"; }//end of set up

    @Test()
    public void PostTweet() {

        String postTweet = null;
        for (int i = 0; i < 20; i++) {
            postTweet = "Posting tweet number " + (i + 1);
            Response Resp = given()
                    .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("status", postTweet).
                            when().post("update.json")
                    .then().extract().response();
        }//end of for loop

    }//end of test



    @Test(dependsOnMethods = "PostTweet")
    public void VerifyAndDeleteTweets() throws InterruptedException {

        driver.navigate().to("https://twitter.com/");
        Thread.sleep(3000);


        Twitter_BaseClass.twitterHomepage().signInButton();
        Thread.sleep(2000);

        Twitter_BaseClass.twitterLoginpage().userName("Amir61155473");
        Twitter_BaseClass.twitterLoginpage().userPassword("Triage2021");
        Twitter_BaseClass.twitterLoginpage().loginButton();
        Thread.sleep(2000);




        //get recent tweet through API
        for (int i = 0; i < 2; i++) {
            Response Resp =
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("screen_name", "@Amir61155473")
                            .when().get("user_timeline.json")
                            .then().extract().response();

            String getTweet = Resp.asString();
            JsonPath js = new JsonPath(getTweet);
            String text1 = js.get("text["+i+"]").toString();
            System.out.println("The recent tweet text is " + text1);
            tweetID = (js.get("id[0]")).toString();
            System.out.println("Id of most recent Tweet is " + tweetID);
        }







        for (int i = 0; i < 20; i++) {
            Response Resp =
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("screen_name", "@Amir61155473")
                            .when().get("user_timeline.json")
                            .then().extract().response();

            String getTweet = Resp.asString();
            JsonPath js = new JsonPath(getTweet);
            String text1 = js.get("text[0]").toString();
            System.out.println("The recent tweet text is " + text1);
            tweetID = (js.get("id[0]")).toString();
            System.out.println("Id of most recent Tweet is " + tweetID);

            // delete recent tweet through API
            given().
                    auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                    queryParam("id", tweetID)
                    .when().post("destroy.json")
                    .then();
            System.out.println("Recent twitter message with id '" + tweetID + "' has been deleted");
        }

        driver.navigate().refresh();
        Thread.sleep(2000);

        Twitter_BaseClass.twitterExplorepage().option();
        Twitter_BaseClass.twitterExplorepage().signOut();


    }

}//end of java class
