package API_Automation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Twitter_API {

    String consumerKey = "KJCHimy26plbR7kd1pdwAzLhj";
    String consumerSecret = "lpyvWkZ2uo3npyil38hgQ1UbMijk7XgalV6nxSF0tH2dA8eBOW";
    String accessToken = "1388499106063077377-H9zUxAE9geklDWdTRTsOxJWX838Fxu";
    String tokenSecret = "71lbRKSUsl4IE1eUoc7UGtJTuQMP6ogn26AQ9OIiD0P8E";

    String tweetID;

    @BeforeClass
    public void setup(){
        RestAssured.baseURI="https://api.twitter.com/1.1/statuses/";
    }//end of set up




    @Test
    public void PostTweet() {
        Response Resp= given()
                .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("status", "This is API Automation Testing for May 4th of 2021.").
                        when().post("update.json")
                .then().extract().response();

        /*if(Resp.statusCode() == 200){
            //logpass
        } else {
            //logfail
        }*/

        String CreateTwe=Resp.asString();
        JsonPath js=new JsonPath(CreateTwe);
        tweetID=(js.get("id")).toString();
        System.out.println("Id of newly Created Tweet is "+tweetID);
    }//end of post tweet

    @Test(dependsOnMethods = "PostTweet")
    public void getRecentTweet() {
        Response Resp=
                given().
                        auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("id", tweetID)
                        .when().get("show.json")
                        .then().extract().response();
        String text= Resp.path("text");
        System.out.println("The recent tweet text is "+ text);
    }//end of get tweet

    @Test(dependsOnMethods = "getRecentTweet")
//@Test
    public void deleteRecentTweet() {
        given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", tweetID)
                .when().post("destroy.json")
                .then().statusCode(200).log().all();
        System.out.println("Recent twitter message with id '"+tweetID+"' has been deleted");
    }//end of delete tweet




    @Test()
    public void getUserTimeLine() {

        Response Resp=
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Amir61155473")
                        .when().get("user_timeline.json")
                        .then().extract().response();

        String getTweet=Resp.asString();
        JsonPath js=new JsonPath(getTweet);
        String text1= js.get("text[0]").toString();
        System.out.println("The recent tweet text is "+ text1);
        tweetID=(js.get("id[0]")).toString();
        System.out.println("Id of most recent Tweet is "+tweetID);
        String text2= js.get("text[1]").toString();
        System.out.println("The previous tweet text is "+ text2);
        String text3= js.get("text[2]").toString();
        System.out.println("The previous tweet text is "+ text3);
    }//end of get tweet



}
