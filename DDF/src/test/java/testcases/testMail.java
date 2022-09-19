package testcases;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import utilities.MonitoringMail;
import utilities.TestConfig;

public class testMail {

	public static void main(String[] args) throws AddressException, MessagingException {
		// TODO Auto-generated method stub

	MonitoringMail mail = new MonitoringMail();
	mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
	
	
	
	
	}

}
