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
package org.encuestame.test.business.social;

import junit.framework.TestCase;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.social.profile.LinkedInProfile;
import org.encuestame.social.api.templates.FacebookAPITemplate;
import org.encuestame.social.api.templates.IdenticaAPITemplate;
import org.encuestame.social.api.templates.LinkedInAPITemplate;
import org.encuestame.utils.categories.test.InternetTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.util.List;

/**
 * Social API test case.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 20, 2011
 */
@Ignore
@Category(InternetTest.class)
public class SocialAPITestCase extends TestCase {

    final String clientId = "287300901667.apps.googleusercontent.com";

    final String clientSecret = "oIJZgke5nPDSEEeNOy0lBrxv";

    private Log log = LogFactory.getLog(this.getClass());

    public void setUp() {
//        ConsoleAppender ca = new ConsoleAppender();
//        ca.setWriter(new OutputStreamWriter(System.out));
//        ca.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
//        // logger.addAppender(ca);
//        BasicConfigurator.configure(ca);
//        Logger.getRootLogger().setLevel(Level.ALL);
    }

    public void tearDown() {
//        LogManager.shutdown();
    }


    public void testFacebook(){
        final String key = "102345790957|2.Zum2qrFYX25vEcK0VPCGXg__.3600.1303513200.1-100002344241319|-K3LZVuOz3trNGOExskllhzHDeo";
        FacebookAPITemplate fb = new FacebookAPITemplate(key);
        fb.updateStatus("@encuestame primer mensajeeeeeeeee");
    }


    public void Identica(){
         //[value=4639a3db610ee773fd48088eba2b42b6, secret=884d825bb5fe5f8c6bcadbe413d1b99d]
         String apiKey = "801f384ec5dc1aecc4801841aae5ac9d";
         String apiSecret = "858629866209502c1023ce2dd86f14a2";
         String token = "4639a3db610ee773fd48088eba2b42b6";
         String tokenSecret = "884d825bb5fe5f8c6bcadbe413d1b99d";
         IdenticaAPITemplate ca = new IdenticaAPITemplate(apiKey, apiSecret, token, tokenSecret);
         ca.updateStatus("@encuestame testing..."+RandomStringUtils.randomAlphabetic(10));
    }

    //@Test
    public void linkedInAPItest(){
        //OAUTH 1 ACCESS TOKEN OAuth1Token [value=e222076b-f187-474a-9ffd-1c7f27760fec, secret=75db960b-2951-48c4-b030-293166a0de8f]

        String apiKey = "5hkdPhtfkRwR0uRhIftai57FA0xbpH7m2fsBFfELvVLf6KMqw1X_FdzsgPkFORuS";
        String apiSecret = "3Tuj7nXvACdCwffnbh-NkUXQ_Re0t1FPakogEPApw_3DBsfowdQuoggCCTd38a9o";
        String token = "e222076b-f187-474a-9ffd-1c7f27760fec";
        String tokenSecret = "75db960b-2951-48c4-b030-293166a0de8f";

        LinkedInAPITemplate tp = new LinkedInAPITemplate(apiKey, apiSecret, token, tokenSecret);
        LinkedInProfile p =  tp.getUserProfile();
        tp.updateStatus("Probando LinkedIn API desde @encuestame, twitter activado", true);
        final List<LinkedInProfile> list = tp.getConnections();
        for (LinkedInProfile linkedInProfile : list) {
            //System.out.println("&&&&&&&&&&&&&&&&&&&&& "+linkedInProfile.getFirstName());
            //System.out.println("&&&&&&&&&&&&&&&&&&&&& "+linkedInProfile.getHeadline());
            //System.out.println("&&&&&&&&&&&&&&&&&&&&& "+linkedInProfile.getPublicProfileUrl());
        }
    }
}
