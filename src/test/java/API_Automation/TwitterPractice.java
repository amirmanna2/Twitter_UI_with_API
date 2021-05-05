package API_Automation;

import ReusableLibrary.Abstract_Class;
import Twitter_Page_Objects.Twitter_BaseClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;

import static io.restassured.RestAssured.given;

public class TwitterPractice extends Abstract_Class {

    String consumerKey = "NCLQLFcVta2t8mvijVsrxBlD1";
    String consumerSecret = "xoRdZatFJ6iQHxRoJvv8RuARMf282Z2GEXDBOQ4J0V9gQOcsPT";
    String accessToken = "1388499106063077377-M0E8sRKhZtiKqU3HfsVQoyknfacRku";
    String tokenSecret = "hSGU7RWFp5XhvTmTyhXaHb6x7cVFtkhqAE8s22PlGaSz6";

    String tweetID;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up


    @Test
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


    /*
    @Test(dependsOnMethods = "PostTweet")
    public void Twitter_Action_Item_2() throws InterruptedException, AWTException {

        driver.navigate().to("https://twitter.com/");
        Thread.sleep(3000);


        Twitter_BaseClass.twitterHomepage().signInButton();
        Thread.sleep(2000);

        Twitter_BaseClass.twitterLoginpage().userName("Amir61155473");
        Twitter_BaseClass.twitterLoginpage().userPassword("Triage2021");
        Twitter_BaseClass.twitterLoginpage().loginButton();
        Thread.sleep(2000);


        for (int i = 20; i > 19; ++i) {
            String verifyTweet = driver.findElements(By.xpath("//div[@class='css-901oao r-1fmj7o5 r-1qd0xha r-a023e6 r-16dba41 r-rjixqe r-bcqeeo r-bnwqim r-qvutc0']")).get(i).getText();
            if (verifyTweet.equals("Posting number " + i + "tweet.")) {
                System.out.println("Latest tweet posted");
            } else {
                System.out.println("No tweet posted");
            }

            Thread.sleep(2000);
        }//end of loop

    }//end of test


    @Test(dependsOnMethods = "Twitter_Action_Item_2")

    public void deleteRecentTweet() {
        given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", tweetID)
                .when().post("destroy.json")
                .then().statusCode(200).log().all();
        System.out.println("Recent twitter message with id '" + tweetID + "' has been deleted");
    }//end of test


    @Test(dependsOnMethods = "deleteRecentTweet")
    public void refreshDashboard() throws InterruptedException {

        driver.navigate().refresh();
        Thread.sleep(2000);


        for (int i = 0; i < 2; ++i) {
            String verifyTweet = driver.findElements(By.xpath("//div[@class='css-901oao r-1fmj7o5 r-1qd0xha r-a023e6 r-16dba41 r-rjixqe r-bcqeeo r-bnwqim r-qvutc0']")).get(i).getText();
            if (verifyTweet.equals("Posting number " + i + "tweet.")) {
                System.out.println("Latest tweet posted");
            } else {
                System.out.println("No tweet posted");
            }

            Thread.sleep(2000);

            Twitter_BaseClass.twitterExplorepage().option();
            Twitter_BaseClass.twitterExplorepage().signOut();
        }

    }//end of tet

     */
}//end of java class
