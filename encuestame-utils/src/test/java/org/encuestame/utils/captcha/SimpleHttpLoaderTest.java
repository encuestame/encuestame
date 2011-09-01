/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.captcha;

import org.encuestame.utils.captcha.http.SimpleHttpLoader;

import junit.framework.TestCase;

/**
 * Test for simple httpLoader.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
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
