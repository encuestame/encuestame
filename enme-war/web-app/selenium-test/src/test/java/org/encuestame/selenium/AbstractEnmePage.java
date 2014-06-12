package org.encuestame.selenium;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
* Abstract class holding functionality common to Selenium Page Objects
* used in Roller.
*/
public abstract class AbstractEnmePage {

    /**
     *
     */
    protected WebDriver driver;

    /**
     *
     */
    protected String pageName;

    /**
     *
     * @param pageTitle
     */
    protected void verifyPageTitle(String pageTitle) {
        if(!driver.getTitle().equals(pageTitle)) {
            throw new IllegalStateException("This is not the " + pageName + ", current page is: "
                    + driver.getTitle());
        }
    }

    /**
     *
     * @param textContent
     * @param textContent2
     */
    protected void verifyTextContent(String textContent, String textContent2) {
        Assert.assertEquals(textContent, textContent2);
    }

    /**
     *
     * @return
     */
    protected String getContentTag(String selector){
        return driver.findElement(By.cssSelector(selector)).getText();
    }

    /*
    * Alternative method of identifying a page, by an HTML ID uniquely on it.
    * Use when multiple views share the same page title.  This method will require
    * adding an id to an element specific to that page if one not already available.
    */
    protected void verifyIdOnPage(String idOnPage) {
        try {
            WebElement div = driver.findElement(By.id(idOnPage));
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("This is not the " + pageName + ", HTML ID: "
                    + idOnPage + " not found.");
        }
    }

    /**
     *
     * @param fieldId
     * @param value
     */
    protected void setFieldValue(String fieldId, String value) {
        WebElement field = driver.findElement(By.id(fieldId));
        field.clear();
        field.sendKeys(value);
    }

    protected void clickByName(String buttonName) {
        driver.findElement(By.name(buttonName)).click();
    }

    protected void clickById(String buttonId) {
        driver.findElement(By.id(buttonId)).click();
    }

    protected void clickByLinkText(String buttonText) {
        driver.findElement(By.linkText(buttonText)).click();
    }

    protected String getTextByCSS(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }

    protected String getTextById(String fieldId) {
        return driver.findElement(By.id(fieldId)).getText();
    }
}