package com.juvenxu.mvnbook.account.persist.exception;

public class AccountPersistException extends Exception
{
	public AccountPersistException(String errMessage, Exception e)
	{
		super(errMessage);
		initCause(null);
	}
}
