package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class BankManagerLoginTest extends BaseTest{
	
	
	@Test
	public void loginAsBankManager() {
		
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent("addCustBtn_CSS"),"Add Customer button not exist");
		//Assert.fail("Bank Manager Login test failed");
		
       
	}

}
