package demo.wrappers;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;

public class Wrappers {
    static WebDriver driver;
    
    /*
     * Write your selenium wrappers here
     */
    
     public  Wrappers(WebDriver driver){
        
        Wrappers.driver=driver;
    }
//Method to check Current url containing text youtube or not
    public  boolean urlcheck(String txt){
        try {
            if(driver.getCurrentUrl().equals(txt)){
                System.out.println("Current page url is : "+txt);
                return true;
            }

            return false;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(" Exception while verifying the link :"+e.getMessage());
            return false;
        }
    }
//Method to click on footer links click
    public  void Footerclick(String Footerlinkname) throws InterruptedException{
        //scrolling webpage to see footer elements on screen
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,150)");
        //identifying footer link based on footerlinkname given
        WebElement Footerlink=driver.findElement(By.xpath("//a[text()='"+Footerlinkname+"']"));
        //clicking on footer link
        Footerlink.click();
        urlcheck("https://about.youtube/");
        //finding message on screen
        WebElement Message=driver.findElement(By.xpath("//section[@class='ytabout__content']"));
        //printing message on screen
        System.out.println(Message.getText());
    }


//Method to click on right arrow button based on the section Name given
    public  boolean scrollrght(String Section_Name){
        try {
            //Finding whole section based on section name given
            WebElement section=driver.findElement(By.xpath("//span[contains(text(),'"+Section_Name+"')]/ancestor :: div[@id='dismissible']"));
           //finding right navigation button from whole section parent
            WebElement Right_nav_btn=section.findElement(By.xpath(".//button[@aria-label='Next']"));
            //Using loop to check right button displayed or not,after each iteration
            while (Right_nav_btn.isDisplayed()) {
                //Clicking on right button when displayed
                Right_nav_btn.click();
                //necesssary wait statement
                Thread.sleep(2000);
            }
            //Checking the right button is not displayed(or) visible
            if(!Right_nav_btn.isDisplayed()){
                //returning true when condition true
                return true;
            }
            return false;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("exception while scrolling right :"+e.getMessage());
            return false;
        }
    }
//Method to click on teab containing given Catagory name as display text
    public  void clktab(String Catg_name){
        try {
            //finding and clicking tab containing the given text
            driver.findElement(By.xpath("//yt-formatted-string[text()='"+Catg_name+"']")).click();
           
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception while clicking on tab :"+e.getMessage());
        }
    }
//Method to Check Jonor of the flim in the last of given section name
    public static boolean jonrchk(String Section_Name){
        try {
            //finding all films under given section name in a list
            java.util.List<WebElement> movies=driver.findElements(By.xpath("//a[@title='"+Section_Name+"']/ancestor :: div[@id='dismissible']//div[@id='items']/ytd-grid-movie-renderer"));
            //finding the last movie from the above list
            WebElement des_movie=movies.get(movies.size()-1);
          //getting jonor element of last movie
            WebElement Jonor=des_movie.findElement(By.xpath(".//a/span"));
           //checking for display of element
            if(Jonor.isDisplayed()){
                //printing sucess message
                System.out.println("Jonor of Film/Movie is Present");
               //returning true if condition passes
                    return true;
                
            }
            return false;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception while checking Jonur of last Movie in given section name :"+e.getMessage());
            return false;
        }
    }
//Method to check sensor certificate of last movie in the list is marked "A" for mature or not
    public static boolean sensor_chk(String Section_name,String sensorC) {
     try {
          //finding all films under given section name in a list
        java.util.List<WebElement> movies=driver.findElements(By.xpath("//a/span[text()='"+Section_name+"']/ancestor :: div[@id='dismissible']//div[@id='items']/ytd-grid-movie-renderer"));
        //finding the last movie from the above list
        WebElement des_movie=movies.get(movies.size()-1);
        //Getiing the sensor element of last movie
        WebElement sensor=des_movie.findElement(By.xpath(".//div[2]/p"));
       //checking whether text containing "A" for mature or not
        if(!sensor.getText().contains(sensorC)){
            //printing merssage
            System.out.println("Last movies is Not marked for Mature");
            //returning true if condition passes
            return true;
        }
        
        return false;
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Exception while checking sensor catagory :"+e.getMessage());
        return false;
    }
   }
//Method to print the title,check count of tracks of last playlist in given music Section
     public static boolean titl_print(String MsqSec){
     try {
        //getiing all playlists under given music section in a list
        java.util.List<WebElement> playlists=driver.findElements(By.xpath("//span[contains(text(),'"+MsqSec+"')]/ancestor :: div[@id='dismissible']//div[@id='items']/ytd-compact-station-renderer"));
       //gettting the last playlist
        WebElement des_msq=playlists.get(playlists.size()-1);
        //getting title of the last playlist
        WebElement des_title=des_msq.findElement(By.xpath(".//a/h3"));
        //printing the title
        System.out.println(des_title.getText());
        //getting track count of the last playlist
        WebElement trackcount=des_msq.findElement(By.xpath(".//p[@id='video-count-text']"));
        //splitting text using " " and storing in an array
        String[] temp=trackcount.getText().split(" ");
        //getting the first String from array and converting to integer
        int res=Integer.parseInt(temp[0]);
        //condition check
        if(res<=50){
            //returning true if condition passes
            return true;
        }
       return false;
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Exception while printing the title of last playlist :"+e.getMessage());
      return false;
    }
   }
//Method to check like count of new posts
   public static int likecount(String Section_Name)throws NoSuchElementException,NullPointerException{
    try {
        //intializing desired variables
    int like=0;
    int result=0;
    //getting all news post 
    java.util.List<WebElement> Likes=driver.findElements(By.xpath("//span[text()='"+Section_Name+"']/ancestor::div[@id='dismissible']/div[@id='contents']/ytd-rich-item-renderer/div[@id='content'] /ytd-post-renderer/div[@id='dismissible']/div[@id='toolbar']/ytd-comment-action-buttons-renderer/div[@id='toolbar']/span[@id='vote-count-middle']"));
    //loop to iterate first three post only
    for(int i=0;i<3;i++){
        //getting post using index
        
        //getting the text  in attribute aria-label as String
       
        String temp=Likes.get(i).getText();
        temp.trim();
        System.out.println("Like on Newspost"+(i+1)+" is : "+temp);
        //checking if the string is empty
        if(temp.equals("")){
            //marking like as 0 if empty
            like=0;
        }else{//else block
           
            // converting to int
             like=Integer.parseInt(temp);
            //adding like count with result
            result=result+like;
        }
       //passing the value to result
        like=result;
    }
    //returning value
    return like;
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Exception while counting likes of news post :"+e.getMessage());
        return 0;
    }
    
   }
//Method to check view count of searcg results when given search text is searched
   public  void viewscount(String searchtxt){
    try {
        //intializing desired variables
        long result=0;
        long count=0;
        //finding the search box element
        WebElement searchBox=driver.findElement(By.xpath("//input[@id='search']"));
        //typing search text in search box
        searchBox.clear();
        searchBox.sendKeys(searchtxt);
        
        //finding the search button
        WebElement searchbtn=driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
        //clicking on search button
        searchbtn.click();
        Thread.sleep(5000);
        //intializing webdriver wait
        WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
        //waiting untill results to be present on screen
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ytd-video-renderer//div[@id='metadata-line']")));
        //getting views element of Search results in list
        java.util.List<WebElement> views=driver.findElements(By.xpath("//ytd-video-renderer//div[@id='metadata-line']/span[1]"));
        //iteration using for each loop
        for (WebElement  element: views) {
            //getting text from element
            String viewsText = element.getText();
            //trimming excess spaces
            String viewsText1 = viewsText.trim();
            //spliting using " "
            String arr[] = viewsText1.split(" ");
            //storing String inside variable
            String val = arr[0];
            //printing of value for reference of each video view count
            System.out.println("view for single video : " + val);
            //intialing variables
            long viewsNum = 0;
            String numericString = "";
            //conditions check
            //condition 1: for Millions
                if (val.contains("M")) {
                    //replacing M with ""
                    numericString = val.replace("M", "");
                    //checking for dot in string
                        if (numericString.contains(".")) {
                            //convertion of double numeric value
                            viewsNum = (long) (Double.parseDouble(numericString) * 1000000);
                                } else {
                                    //convertion of String value
                                        viewsNum = Integer.parseInt(numericString) * 1000000;
                                }
                        } else if (val.contains("K")) { //condition 2: for Thousands
                            //replacing K with ""
                                numericString = val.replace("K", "");
                                //checking for dot in string
                                if (numericString.contains(".")) {
                                    //convertion of double numeric value
                                        viewsNum = (long) (Double.parseDouble(numericString) * 1000);
                                } else {
                                      //convertion of String value
                                        viewsNum = Integer.parseInt(numericString) * 1000;
                                }
                        }else if (val.contains("B")) {//Condition 3: check for Billions
                            //Printing the sucess message as Billion already crosses 10 crores
                            System.out.println("Views count of videos reached 10 Crores");
                            break;
                    }
                        count = count + viewsNum;
                       
            //final condition check if count reached ten crore
            if(count>=100000000){
                System.out.println("Views count of videos reached 10 Crores");
                break;
            }
        }
       
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Exception while counting views"+e.getMessage());
    }
   }
}
