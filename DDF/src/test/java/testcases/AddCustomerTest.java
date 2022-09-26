package testcases;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;

public class AddCustomerTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void addCustomer(String firstname, String lastname, String postcode,String alerttxt)
			throws AddressException, IOException, MessagingException, InterruptedException {

		click("addCustBtn_CSS");
		Thread.sleep(3000);
		type("firstName_CSS", firstname);
		Thread.sleep(3000);
		type("lastName_CSS", lastname);
		Thread.sleep(3000);
		type("postCode_CSS", postcode);
		Thread.sleep(3000);
		
		click("addCustomer_CSS");
		
	//	Alert alert = driver.switchTo().alert();
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(alerttxt),"Customer not added successfully");
		
		alert.accept();
		
		//Assert.fail("Add customer test failed");
	}

	@DataProvider()
	public Object[][] getData() {

		return TestUtil.getData("AddCustomerTest");

	}

}
