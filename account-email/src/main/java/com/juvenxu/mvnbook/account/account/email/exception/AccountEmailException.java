package com.juvenxu.mvnbook.account.account.email.exception;

import javax.mail.MessagingException;

public class AccountEmailException extends MessagingException
{
	//private Exception next;
	public AccountEmailException(String errMessage, MessagingException e){
		super(errMessage);
		//next = e;
		initCause(null);
	}
}
