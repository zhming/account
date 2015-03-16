package com.juvenxu.mvnbook.account.account.email;

import static junit.framework.Assert.assertEquals;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

public class AccountEmailServiceTest
{
	private GreenMail greenMail;

	@Before
	public void startMailServer() throws Exception
	{
		greenMail = new GreenMail(ServerSetupTest.SMTP);
		greenMail.setUser("test@juvenxu.com", "123456");
		greenMail.start();
		
	}
	
	@Test
	public void testSendMail() throws Exception
	{	
		ApplicationContext context = new ClassPathXmlApplicationContext("account-email.xml");
		
		AccountEmailService accountEmailService = (AccountEmailService)context.getBean("accountEmailService");
		String to = "8wy260154@mail.extern";
		String subject = "Test Mail";
		String htmlText = "<h1>This is a test mail.</h1>";
		accountEmailService.sendMail(to, subject, htmlText);
		
		//greenMail.wait(5000, 1);
		greenMail.waitForIncomingEmail(5000, 1);
		
		Message[] msgs = greenMail.getReceivedMessages();
		
		assertEquals(1, msgs.length);
		assertEquals(subject, msgs[0].getSubject());
		assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
		
		
	}
	
	@After
	public void stopMailServer() throws Exception
	{
		greenMail.stop();
	}
}
