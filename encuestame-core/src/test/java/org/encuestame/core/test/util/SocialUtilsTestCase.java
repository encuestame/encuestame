/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpException;
import org.encuestame.core.util.SocialUtils;
import org.junit.Test;

/**
 * Date Utils Test Cases.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 14, 2010 11:29:20 PM
 * @version Id:
 */
public class SocialUtilsTestCase extends TestCase{

    /**
     * Test Period Date.
     * @throws IOException
     * @throws HttpException
     */
    @Test
    public void testGetGoGlShortUrl() throws HttpException, IOException{
        System.out.println(SocialUtils.getGoGl("http://blog.jotadeveloper.com",
                                                "AIzaSyCvEMnlGa4q4Suayx1bMYXg-Wkf1jYmmaQ"));
    }

    @Test
    public void testgetTinyUrl() throws HttpException, IOException{
        System.out.println(SocialUtils.getTinyUrl("http://blog.jotadeveloper.com"));
    }

    @Test
    public void testgetGoogleStas() throws HttpException, IOException{
        System.out.println(SocialUtils.getGoGlStats("http://blog.jotadeveloper.com"));
    }

}
