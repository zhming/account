package com.juvenxu.mvnbook.account.persist.Service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.juvenxu.mvnbook.account.persist.Account;
import com.juvenxu.mvnbook.account.persist.Service.AccountPersistService;
import com.juvenxu.mvnbook.account.persist.exception.AccountPersistException;

public class AccountPersistServiceImpl implements AccountPersistService
{

	private String file;
	private SAXReader reader = new SAXReader();
	private static final String ELEMENT_ROOT = "account-persist";
	private static final String ELEMENT_ACCOUNTS = "accounts";
	private static final String ELEMENT_ACCOUNT = "account";
	private static final String ELEMENT_ACCOUNT_ID = "id";
	private static final String ELEMENT_ACCOUNT_NAME = "name";
	private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
	private static final String ELEMENT_ACCOUNT_EMAIL = "email";
	private static final String ELEMENT_ACCOUNT_ACTIVATED = "activated";

	public Account createAccount(Account account)
			throws AccountPersistException
	{
		if (readAccount(account.getId()) != null){
			throw new AccountPersistException("the record is existed.", new Exception());
		}
		Document doc = readDocument();
		Element root = doc.getRootElement();
		Element accountsEle = root.element(ELEMENT_ACCOUNTS);
		
		Element accountEle = accountsEle.addElement(ELEMENT_ACCOUNT);
		accountEle.addElement(ELEMENT_ACCOUNT_ID).addText(account.getId());
		accountEle.addElement(ELEMENT_ACCOUNT_NAME).addText(account.getName());
		accountEle.addElement(ELEMENT_ACCOUNT_PASSWORD).addText(account.getPassword());
		accountEle.addElement(ELEMENT_ACCOUNT_EMAIL).addText(account.getEmail());
		accountEle.addElement(ELEMENT_ACCOUNT_ACTIVATED).addText("" + account.isActivated());
		writeDocument(doc);
		return null;
	}

	public void deleteAccount(String id) throws AccountPersistException
	{
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public Account readAccount(String id) throws AccountPersistException
	{
		Document doc = readDocument();
		Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);

		for (Element accountEle : (List<Element>) accountsEle.elements())
		{
			if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id))
			{
				return buildAccount(accountEle);
			}
		}

		
		return null;
	}

	public Account upAccount(Account account) throws AccountPersistException
	{

		return null;
	}

	private Account buildAccount(Element accountEle)
	{
		Account account = new Account();
		account.setId(accountEle.elementText(ELEMENT_ACCOUNT_ID));
		account.setName(accountEle.elementText(ELEMENT_ACCOUNT_NAME));
		account.setPassword(accountEle.elementText(ELEMENT_ACCOUNT_PASSWORD));
		account.setEmail(accountEle.elementText(ELEMENT_ACCOUNT_EMAIL));
		account.setActivated("true".equals(accountEle
				.elementText(ELEMENT_ACCOUNT_ACTIVATED)) ? true : false);
		return account;
	}

	private Document readDocument() throws AccountPersistException
	{
		File dataFile = new File(file);
		if (!dataFile.exists())
		{
			dataFile.getParentFile().mkdirs();
			Document doc = DocumentFactory.getInstance().createDocument();
			Element rootEle = doc.addElement(ELEMENT_ROOT);
			rootEle.addElement(ELEMENT_ACCOUNTS);
			writeDocument(doc);

		}
		try
		{
			return reader.read(new File(file));
		}
		catch (DocumentException e)
		{
			throw new AccountPersistException(
					"Unable to read persist data xml", e);
		}

	}

	private void writeDocument(Document doc) throws AccountPersistException
	{
		Writer out = null;
		try
		{
			out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			XMLWriter writer = new XMLWriter(out,
					OutputFormat.createPrettyPrint());
			writer.write(doc);
		}
		catch (IOException e)
		{
			throw new AccountPersistException(
					"Unable to write persist data xml", e);
		}
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
			}
			catch (IOException e)
			{
				throw new AccountPersistException(
						"Unable to close persist data xml writer.", e);
			}

		}
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

}
