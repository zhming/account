package com.juvenxu.mvnbook.account.captcha;

public class AccountCaptchaException extends Exception
{
	 public AccountCaptchaException(String err)
	{
		 super(err);
	}
	 public AccountCaptchaException(String err, Exception e)
		{
			 super(err, e);
		}
	 
}
