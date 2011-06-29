package org.encuestame.utils.captcha;

import org.encuestame.utils.captcha.http.SimpleHttpLoader;

import junit.framework.TestCase;

public class SimpleHttpLoaderTest extends TestCase {

    SimpleHttpLoader l;

    protected void setUp() throws Exception {
        l = new SimpleHttpLoader();
    }

    public void testHttpGet() {
        // check http plain
        assertNotNull(l.httpGet("http://www.google.com/"));

        // check https
        assertNotNull(l.httpGet("https://mail.google.com/"));

        // check content.
        // assertEquals("no?")
    }

    public void testHttpPost() {
        try {
            l.httpPost("http://www.google.com/search", "hl=da&q=bla&btnG=Google-s%C3%B8gning&meta=");
        }
        catch (Exception e) {
            assertTrue(e.getMessage().indexOf("405 for URL") != -1);
        }
    }

}
