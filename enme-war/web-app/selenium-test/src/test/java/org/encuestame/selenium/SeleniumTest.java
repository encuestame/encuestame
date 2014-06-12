package org.encuestame.selenium;


import junit.framework.Assert;
import junit.framework.TestCase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Selenium Test Cases.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 15, 2014
 */
@Category(DefaultTest.class)
public class SeleniumTest extends TestCase {

    @Test
    public void testSetup() throws Exception{
        final InitialHomeTestIT test = new InitialHomeTestIT();
        test.setUp();
        test.testInitialLogin();
        test.tearDown();
        Assert.assertEquals(1,1);
    }
}
