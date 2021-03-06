package com.juvenxu.mvnbook.account.captcha;

import java.util.List;

public interface AccountCaptchaService
{
	String generateCaptchaKey() throws AccountCaptchaException;
	byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
	boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;
	List<String> getPerDefinedTexts();
	void setPerDefinedTexts(List<String> preDefinedTexts);
}
