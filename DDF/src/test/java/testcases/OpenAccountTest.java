package testcases;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;

public class OpenAccountTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void openAccount(String customer, String currency)
			throws AddressException, IOException, MessagingException {

		click("openAccount_CSS");
		select("customer_CSS",customer);
		select("currency_CSS",currency);
		//click("process_CSS");
		
		//Assert.fail("Open account test failed");
	
	}

	@DataProvider()
	public Object[][] getData() {

		return TestUtil.getData("OpenAccountTest");

	}

}
