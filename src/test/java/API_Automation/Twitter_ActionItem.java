package API_Automation;

import ReusableLibrary.Abstract_Class;
import Twitter_Page_Objects.Twitter_BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

import static io.restassured.RestAssured.given;

    public class Twitter_ActionItem extends Abstract_Class {

        String consumerKey = "NCLQLFcVta2t8mvijVsrxBlD1";
        String consumerSecret = "xoRdZatFJ6iQHxRoJvv8RuARMf282Z2GEXDBOQ4J0V9gQOcsPT";
        String accessToken = "1388499106063077377-M0E8sRKhZtiKqU3HfsVQoyknfacRku";
        String tokenSecret = "hSGU7RWFp5XhvTmTyhXaHb6x7cVFtkhqAE8s22PlGaSz6";


        String tweetID;

        @BeforeClass
        public void setup() {
            RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
        }//end of set up


        @Test(priority = 1)
        public void Twitter_UseCase_1() throws InterruptedException{

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
            Twitter_BaseClass.twitterExplorepage().message("This is a UI automation test post.");
            Twitter_BaseClass.twitterExplorepage().tweetButton();
            Thread.sleep(2000);



            //Get recent tweet through API
            Response Resp=
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("screen_name", "@Amir61155473")
                            .when()
                            .get("user_timeline.json")
                            .then()
                            .extract()
                            .response();

            String getTweet=Resp.asString();
            JsonPath js=new JsonPath(getTweet);
            String tweetText= js.get("text[0]").toString();
            tweetID=(js.get("id[0]")).toString();


            if (tweetText.equals("This is a UI automation test post.")) {
                System.out.println("The following tweet has been created: "+ tweetText + " The ID of the tweet is: " + tweetID);
                logger.log(LogStatus.PASS, "The following tweet has been created: " + tweetText + " The ID of the tweet is: "+tweetID);
            } else {
                System.out.println("The following tweet has not been created: " + tweetText + "ID is not available since tweet has not been posted");
                logger.log(LogStatus.INFO, "The following tweet has not been created: " + tweetText + " No ID available.");
            }


            //Delete recent tweet through API
            given().
                    auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                    queryParam("id", tweetID)
                    .when()
                    .post("destroy.json")
                    .then();
            System.out.println("Recent twitter message with id '" + tweetID + "' has been deleted");
            logger.log(LogStatus.PASS,"Recent twitter message with id '" + tweetID + "' has been deleted");




            //Refresh page and verify tweet is deleted
            driver.navigate().refresh();
            Thread.sleep(2000);

            //Verify tweet is not present with API

            Response Resp1=
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                            queryParam("screen_name", "@Amir61155473")
                            .when()
                            .get("user_timeline.json")
                            .then()
                            .extract()
                            .response();

            String getNewTweet=Resp1.asString();
            JsonPath js1=new JsonPath(getNewTweet);
            String NewTweet= js1.get("text[0]");
            Objects.toString("NewTweet", NewTweet);

            if (NewTweet == null){
                System.out.println("The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
                logger.log(LogStatus.PASS, "The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
            }else {
                System.out.println("The recently posted tweet: " + tweetText + " is still present.");
                logger.log(LogStatus.INFO, "The recently posted tweet " + tweetText + " is still present.");

            }


            Twitter_BaseClass.twitterExplorepage().option();
            Twitter_BaseClass.twitterExplorepage().signOut();
            Twitter_BaseClass.twitterExplorepage().confirmSignOut();



        }//End of use case one test

        @Test(priority = 2)
        public void Twitter_UseCase_2() throws InterruptedException{

            String postTweet = null;
            for (int i = 0; i < 5; i++) {
                postTweet = "Auto tweet post #" + (i + 1);
                Response Resp = given()
                        .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                        .queryParam("status", postTweet)
                        .when().post("update.json")
                        .then().extract().response();


                if (Resp.statusCode() == 200){
                    System.out.println("Status code is 200 and successful. Tweet number " +i+ " posted.");
                    logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number " +i+ " posted.");
                } else {
                    System.out.println("Status code is not successful: " + Resp.statusCode());
                    logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
                }
            }//end of for loop


            driver.navigate().to("https://twitter.com/");
            Thread.sleep(3000);


            Twitter_BaseClass.twitterHomepage().signInButton();
            Thread.sleep(2000);

            Twitter_BaseClass.twitterLoginpage().userName("Amir61155473");
            Twitter_BaseClass.twitterLoginpage().userPassword("Triage2021");
            Twitter_BaseClass.twitterLoginpage().loginButton();
            Thread.sleep(2000);

            driver.navigate().refresh();
            Thread.sleep(2000);

            Response Resp =
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                            .queryParam("screen_name", "@Amir61155473")
                            .when()
                            .get("user_timeline.json")
                            .then()
                            .extract()
                            .response();

            String getTweet = Resp.asString();
            JsonPath js = new JsonPath(getTweet);
            String FirstID = (js.get("id[0]")).toString();
            String SecondID = (js.get("id[1]")).toString();
            String FirstTweet = js.get("text[0]").toString();
            String SecondTweet = js.get("text[1]").toString();

            System.out.println("The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
            logger.log(LogStatus.INFO, "The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
            System.out.println("The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");
            logger.log(LogStatus.INFO, "The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");



            //Delete all those 20 tweets using for loop as separate @Test via API
            for (int i = 0; i < 5; i++) {

                Response Resp2 =
                        given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                                .queryParam("screen_name", "@Amir61155473")
                                .when()
                                .get("user_timeline.json")
                                .then()
                                .extract()
                                .response();

                String getTweet2 = Resp2.asString();
                JsonPath js2 = new JsonPath(getTweet2);
                tweetID = (js2.get("id[0]")).toString();
                // delete recent tweet through API

                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                        .queryParam("id", tweetID)
                        .when()
                        .post("destroy.json")
                        .then()
                        .extract()
                        .response();;

                if (Resp.statusCode() == 200){
                    System.out.println("Status code is 200 and successful. Tweet number " +i+ " has been deleted.");
                    logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number " +i+ " has been deleted.");
                } else {
                    System.out.println("Status code is not successful: " + Resp.statusCode());
                    logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
                }


            }

            //Verify Tweet has been deleted and sign off
            Response Resp3 = given()
                    .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                    .queryParam("screen_name", "@Amir61155473")
                    .when()
                    .get("user_timeline.json")
                    .then()
                    .extract()
                    .response();

            String getTweet3 = Resp3.asString();
            JsonPath js3 = new JsonPath(getTweet3);
            String Tweet1 = js3.get("text[3]");
            Objects.toString("NewTweet", Tweet1);
            String Tweet2 = js3.get("text[4]");
            Objects.toString("NewTweet", Tweet2);

            if (Tweet1 == "This is auto-generated tweet number: 1"){
                System.out.println("The first tweet has not been successfully deleted.");
                logger.log(LogStatus.FAIL, "The first tweet has not been successfully deleted.");
            } else {
                System.out.println("The first tweet has been successfully deleted from timeline.");
                logger.log(LogStatus.PASS, "The first tweet has been successfully deleted from timeline.");
            }
            if (Tweet2 == "This is auto-generated tweet number: 2"){
                System.out.println("The second tweet has not been successfully deleted.");
                logger.log(LogStatus.FAIL, "The second tweet has not been successfully deleted.");
            } else {
                System.out.println("The second tweet has been successfully deleted from timeline.");
                logger.log(LogStatus.PASS, "The second tweet has been successfully deleted from timeline.");
            }

            driver.navigate().refresh();
            Thread.sleep(2000);
            Twitter_BaseClass.twitterExplorepage().option();
            Twitter_BaseClass.twitterExplorepage().signOut();
            Twitter_BaseClass.twitterExplorepage().confirmSignOut();


        }//End of twitter use case 2 test

    }
