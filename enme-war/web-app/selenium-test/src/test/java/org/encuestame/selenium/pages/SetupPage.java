package org.encuestame.selenium.pages;

import junit.framework.Assert;
import org.encuestame.selenium.AbstractEnmePage;
import org.openqa.selenium.WebDriver;

public class SetupPage extends AbstractEnmePage{
	
	
	public SetupPage(final WebDriver driver) {
		this.driver = driver;
	}
	
	public void initStep1(){
        final String stepContent = getContentTag(".setup-description > h3").trim();
        verifyTextContent("Step 1 : Welcome", stepContent);
        clickByName("_eventId_install-submit");
    }

	public void initStep2() {
        final String stepContent = getContentTag(".setup-description > h3").trim();
        verifyTextContent("Step 2 : Create Database", stepContent);
        clickByName("_eventId_create");
    }
	public void initStep3(){
        final String stepContent = getContentTag(".setup-description > h3").trim();
        verifyTextContent("Step 3 : Install demo data", stepContent);
        clickByName("_eventId_no");
    }

	public void initStep4(
            final String username,
            final String email,
            final String emailRepeat,
            final String password,
            final String passwordRepeat){
        final String stepContent = getContentTag(".setup-description > h3").trim();
        verifyTextContent("Step 4 : Create administration user", stepContent);
        setFieldValue("username", username);
        setFieldValue("email", email);
        setFieldValue("confirmEmail", emailRepeat);
        setFieldValue("password", password);
        setFieldValue("confirmPassword", passwordRepeat);
        clickByName("_eventId_create-user");
    }

	public void initStep5(){
        final String stepContent = getContentTag(".setup-description > h3").trim();
        verifyTextContent("Step 5 : Review your User Administration", stepContent);
        //TODO: check if the user data is correct
        clickByName("_eventId_welcome");
    }

    public void initStep6(){
        final String stepContent = getContentTag(".setup-welcome > h3").trim();
        verifyTextContent("Install Succeeded", stepContent);
        clickByName("_eventId_finish");
    }
}
