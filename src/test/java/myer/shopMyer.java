package myer;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import helper.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class shopMyer {

    public ExtentReports report = ExtentManager.extentReportGenerator("Myer", "");
    public ExtentTest logger;
    public String imgloc = ExtentManager.imgloc;
    public String propfileloc = appconfig.proploc;
    public PropertyHub propertyhub = new PropertyHub(propfileloc);
    public String browser = System.getProperty("browser", propertyhub.getProperty("browser"));
    public CommonUtil util = new CommonUtil();
    public String url = System.getProperty("url", propertyhub.getProperty("url"));
    public WebKeywords wk = new WebKeywords();
    // BaseTest bt = new BaseTest();


    @Test(dataProvider = "myer", dataProviderClass = DataFactory.class)
    public void shopM(String Search,String ResultCount, String Item_link,String Item1) throws Exception {
    // Ideally I would have used Page Object Model to store the object and methods of the respective pages.
     //As it's a single test POM was not followed here.

        logger = report.createTest("Myer Shopping Cart");
        wk.setLogger(logger);
        util.setLogger(logger);
        WebDriver driver = BrowserFactory.startBrowser(browser);
        driver.get(url);
        logger.log(Status.INFO, "Navigated to :" + url);
        //Verify browser is on the Home Page
        wk.VerifyPageExists(driver, "MYER | Shop Fashion, Homewares, Beauty, Toys & More");
        //Search for Lego Star Wars
        wk.EnterNoVerification_Se(driver.findElement(By.id("search-input")),Search);
       // WebElement resultContainer= driver.findElement(By.xpath("//div[@data-automation='products-container']"));
        WebElement resultCount= driver.findElement(By.xpath("//span[@data-automation='product-total']"));

        //Verify 6 results are found. Please note this step might fail if the number of products change from 6
        util.CaptureElementClip_Se(driver,resultCount,Status.PASS,"Search Results",imgloc);
        wk.VerifyElementExists(resultCount ,ResultCount);
        String item1Link = Item_link;
       String item1 = Item1;
        WebElement e_item1 = driver.findElement(By.xpath(
                "//span[@class='screen-reader-text'][normalize-space()='"+item1Link+"']/parent::a"));
        wk.Click_Se(e_item1,item1);
        util.CaptureScreenClipNoScroll_Se(driver,Status.PASS,"Item Selected",imgloc);

        WebElement e_itemTitle = driver.findElement(By.xpath("//span[@data-automation='product-title']"));
        wk.VerifyElementExists(e_itemTitle ,item1);
        wk.Click_Se(driver.findElement(By.id("add-to-bag-btn")),"ADD TO BAG");

        WebElement e_bagTitle = driver.findElement(By.xpath("//h2[@class='addBagTitle']"));
        WebElement e_bagItm1 = driver.findElement(By.xpath("//span[@class='rp_name']"));

        util.CaptureScreenClipNoScroll_Se(driver,Status.PASS,"Item Added To Bag",imgloc);
        wk.VerifyElementExists(e_bagTitle ,"Item added to bag");
        wk.VerifyElementExists(e_bagItm1 ,item1);
        wk.Click_Se(driver.findElement(By.xpath("//button[@data-automation='view-bag-checkout-btn']"))
                ,"VIEW BAG AND CHECKOUT");

        util.CaptureScreenClip_Se(driver,Status.PASS,"My Bag",imgloc);
        wk.VerifyPageExists(driver,"Bag | Myer");


        wk.ClickJS_Se(driver.findElement(By.xpath("//button[@data-automation='continue-to-checkout']"))
                ,"CONTINUE");
        util.CaptureScreenClip_Se(driver,Status.PASS,"Checkout",imgloc);
        wk.VerifyElementExists(driver.findElement(By.xpath("//h1[@data-automation='main-heading']")),
                "Checkout");
        driver.close();
    }
    @AfterMethod
    public void flushReport() {
        try {
           // driver.close();
            report.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
