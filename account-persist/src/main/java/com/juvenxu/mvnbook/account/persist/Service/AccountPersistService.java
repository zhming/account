package com.juvenxu.mvnbook.account.persist.Service;

import com.juvenxu.mvnbook.account.persist.Account;
import com.juvenxu.mvnbook.account.persist.exception.AccountPersistException;

public interface AccountPersistService
{
	Account createAccount(Account account) throws AccountPersistException;
	Account readAccount(String id) throws AccountPersistException;
	Account upAccount(Account account) throws AccountPersistException;
	void deleteAccount(String id) throws AccountPersistException;
}
