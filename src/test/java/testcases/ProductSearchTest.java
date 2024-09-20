package testcases;

import static utility.Report.report;
import static utility.Report.test;
import static utility.TakeScreenShot.takeScreenShot;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import core.Hook;

public class ProductSearchTest extends Hook
{
  @Test(dataProvider="getSeachData",dependsOnGroups= {"login"})
  public void search(String productname) throws Exception 
  {
	  test = report.createTest("search test");
	  
	  productsearchbox.clear();
	  productsearchbox.sendKeys(productname);
	  
	  test.log(Status.PASS, MarkupHelper.createLabel("searching -- "+productname, ExtentColor.GREEN));
	  
	  Actions a = new Actions(driver);
	  a.sendKeys(Keys.ENTER).perform();
	  
	  // screen shot
	  takeScreenShot("ProductResult-"+productname);
	  
	  int prodlist = searchresults.size();
	  if(prodlist < 0)
	  {
		  Assert.fail("No Products found..");
		  test.log(Status.FAIL, MarkupHelper.createLabel("searching -- "+productname+"Not Found", ExtentColor.RED));
		  
	  }
	  else
	  {
		  test.log(Status.PASS, MarkupHelper.createLabel("searching -- "+productname+" Found", ExtentColor.GREEN));
		  addtokart();
	  }
	       
	  }
  
  @DataProvider
  public String[][] getSeachData() throws Exception
  {
	  return utility.MakeConnectionPOI.getData("AmazonLogin", "Sheet2");
  }
  
  @Test(dependsOnMethods = {"search"})
  public void addtokart() throws Exception
  {
 Iterator <WebElement> it = searchresults.iterator();
	  
	 // while(it.hasNext())
	 // {
		  //System.out.println(it.next().getText());
		 // test.log(Status.PASS, MarkupHelper.createLabel(it.next().getText(), ExtentColor.GREEN));
		for(int i=1;i <=3;i++)
		{
		  test.log(Status.PASS, MarkupHelper.createLabel(it.next().getText(), ExtentColor.GREEN));
		  it.next().click(); // open first 3 link on new tab..
		  
		  Thread.sleep(4000);
		  
		 Set <String> tabs = driver.getWindowHandles();
		 Iterator <String> ii = tabs.iterator();
		 
		 String maintab = ii.next(); // 1st tab
		 String producttab = ii.next();  // 2nd tab
		 
		 driver.switchTo().window(producttab);
		 
		 driver.findElement(By.xpath("//*[@id = 'add-to-cart-button']")).click();
		 test.log(Status.PASS, MarkupHelper.createLabel("Add to kart done", ExtentColor.GREEN));
		 driver.close(); // close current tab only..
		 
		 driver.switchTo().window(maintab);
		 
		}
	
	//  }
 
  }
}
