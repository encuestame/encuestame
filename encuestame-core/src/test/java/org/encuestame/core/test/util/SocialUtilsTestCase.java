/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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
import org.encuestame.persistence.exception.EnmeFailOperation;
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
     * @throws EnmeFailOperation
     */
    @Test(timeout = 5000)
    public void testGetGoGlShortUrl() throws HttpException, IOException, EnmeFailOperation{
        //System.out.println(SocialUtils.getGoGl("http://blog.jotadeveloper.com",
         //                                       "AIzaSyCvEMnlGa4q4Suayx1bMYXg-Wkf1jYmmaQ"));
    }

    @Test(timeout = 5000)
    public void testgetTinyUrl() throws HttpException, IOException{
       // System.out.println(SocialUtils.getTinyUrl("http://blog.jotadeveloper.com"));
    }

    @Test(timeout = 5000)
    public void testgetGoogleStas() throws HttpException, IOException{
       // System.out.println(SocialUtils.getGoGlStats("http://blog.jotadeveloper.com"));
    }

    @Test(timeout = 5000)
    public void testgetBitLy() throws HttpException, IOException, EnmeFailOperation{
        //System.out.println(SocialUtils.getBitLy("http://blog.jotadeveloper.com",
         //       "R_5ea5369d4eee11edbd860ec8ef5dc7a0", "jotadeveloper"));
        //System.out.println(SocialUtils.getBitLy("http://www.encuestame.org",
         //       "R_5ea5369d4eee11edbd860ec8ef5dc7a0", "jotadeveloper"));
    }
}