package testcases;

import static utility.Report.report;
import static utility.Report.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import core.Hook;

public class LoginTest extends Hook
{
  @Test(priority = 1,groups="login")
  public void validateuserid() 
  {
	 test = report.createTest("validate userid");
	  signin.click();
	  
	  uid.sendKeys("paragguide@yahoo.co.in");
	  
	  ctnbtn.click();
	    
	     try
	     {
	  err1.getText();
	  test.log(Status.FAIL, MarkupHelper.createLabel("valid userid but getting eror", ExtentColor.RED));
	     Assert.fail("valid userid but getting eror");
	     }
	    catch(Exception e)
	     {
	  test.log(Status.PASS, MarkupHelper.createLabel("valid userid ", ExtentColor.GREEN));
	    	   	 
	     }
  }
  
  @Test(dependsOnMethods= {"validateuserid"},priority = 2,groups="login")
  public void validatepassword()
  {
	  pwd.sendKeys("RMinfotech12#&&");
	  submit.click();
	  
	  try
	  {
		  err2.getText();
		  test.log(Status.FAIL, MarkupHelper.createLabel("valid passwordd but getting eror", ExtentColor.RED));
		     Assert.fail("valid password but getting eror");
		 
	  }
	  catch(Exception e)
	  {
		  test.log(Status.PASS, MarkupHelper.createLabel("valid password ", ExtentColor.GREEN));
		  
	  }
  }
  
  
}
