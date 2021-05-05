package Twitter_Page_Objects;
import ReusableLibrary.Abstract_Class;

public class Twitter_BaseClass extends Abstract_Class {



    public static Twitter_HomePage twitterHomepage(){
        Twitter_HomePage twitterHomepage = new Twitter_HomePage(driver);
        return  twitterHomepage;
    }//end of method

    public static Twitter_LoginPage twitterLoginpage(){
        Twitter_LoginPage twitterLoginpage = new Twitter_LoginPage(driver);
        return  twitterLoginpage;
    }//end of method

    public static Twitter_ExplorePage twitterExplorepage(){
        Twitter_ExplorePage twitterExplorepage = new Twitter_ExplorePage(driver);
        return  twitterExplorepage;
    }//end of method


}//end of java class
