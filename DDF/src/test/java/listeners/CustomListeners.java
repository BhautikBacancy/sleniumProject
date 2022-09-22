package listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

import base.BaseTest;
import utilities.TestUtil;

public class CustomListeners extends BaseTest implements ITestListener {

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
	
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase()+ "Failed with exception :" +arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.fileName));
		
		
		Reporter.log("<a target=\"_blank\" href="+TestUtil.fileName+">Screenshot link</a>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.fileName+"><img src="+TestUtil.fileName+" height=200 width=200></a>");
		rep.endTest(test);
		rep.flush();
		  
		
	
	
	
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
		
	}

	public void onTestStart(ITestResult arg0) {
	 test =  rep.startTest(arg0.getName().toUpperCase());
		
	}

	public void onTestSuccess(ITestResult arg0) {
      test.log(LogStatus.PASS, arg0.getName().toUpperCase()+ "PASS");
	  rep.endTest(test);
	  rep.flush();
	  
	  
	}

}
