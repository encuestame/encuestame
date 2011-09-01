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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.encuestame.social.api.FacebookAPITemplate;
import org.encuestame.social.api.GoogleBuzzAPITemplate;
import org.encuestame.social.api.IdenticaAPITemplate;
import org.encuestame.social.api.LinkedInAPITemplate;
import org.encuestame.social.api.support.LinkedInProfile;
import org.junit.Ignore;

/**
 * Social API test case.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 20, 2011
 */
@Ignore
public class SocialAPITestCase extends TestCase {

    final String clientId = "287300901667.apps.googleusercontent.com";

    final String clientSecret = "oIJZgke5nPDSEEeNOy0lBrxv";

    Logger logger = Logger.getLogger(SocialAPITestCase.class);

    public void setUp() {
        ConsoleAppender ca = new ConsoleAppender();
        ca.setWriter(new OutputStreamWriter(System.out));
        ca.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
        // logger.addAppender(ca);
        BasicConfigurator.configure(ca);
        Logger.getRootLogger().setLevel(Level.ALL);
    }

    public void tearDown() {
        LogManager.shutdown();
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

    public void buzzAPITest() throws IOException {
        // Assert.notNull(this.linkedInService);
        // Assert.isTrue(true);
        // /System.out.println(this.linkedInService.getAuthorizeLinkedInUrl());
        // final String consumerKey =
        // "5hkdPhtfkRwR0uRhIftai57FA0xbpH7m2fsBFfELvVLf6KMqw1X_FdzsgPkFORuS";
        // final String consumerSecret =
        // "3Tuj7nXvACdCwffnbh-NkUXQ_Re0t1FPakogEPApw_3DBsfowdQuoggCCTd38a9o";
        // final String requestTokenUrl =
        // "https://api.linkedin.com/uas/oauth/requestToken";
        // final String authorizeUrl =
        // "https://www.linkedin.com/uas/oauth/authorize?oauth_token={requestToken}";
        // final String accessTokenUrl =
        // "https://api.linkedin.com/uas/oauth/accessToken";
        final String realToken = "1/8hNJx9pdEkj6ziWJhkdPYCJFjoXZgQACKjDcse93x8Q";
        final String refreshToken = "1/2GI75S23HtwoB1kiPcaRFEqePVh1kYoniubI6obcj_8";
        GoogleBuzzAPITemplate apiTemplate = new GoogleBuzzAPITemplate(realToken,
                "AIzaSyCvEMnlGa4q4Suayx1bMYXg-Wkf1jYmmaQ");
        // try{
        // System.out.println(apiTemplate.getActivities());
        // apiTemplate.likeActivity("tag:google.com,2010:buzz-feed:public:posted:110583664879406693886");
        // } catch (HttpClientErrorException e) {
        // // System.out.println("errorr 401 "+e);
        // OAuth2Operations tm = new OAuth2Template(clientId, clientSecret,
        // "https://accounts.google.com/o/oauth2/auth?client_id={client_id}&redirect_uri={redirect_uri}&scope={scope}&response_type=code",
        // "https://accounts.google.com/o/oauth2/token");
        //
        // System.out.println();
        // apiTemplate = new BuzzAPITemplate(
        // tm.refreshToken(refreshToken).getAccessToken(),
        // "AIzaSyCvEMnlGa4q4Suayx1bMYXg-Wkf1jYmmaQ");
        // apiTemplate.updateStatus("hola2222222222222");
        // //apiTemplate.likeActivity("tag:google.com,2010:buzz-feed:public:posted:110583664879406693886");
        // }

        // OAuth1Template tp = new OAuth1Template(consumerKey, consumerSecret,
        // requestTokenUrl,
        // authorizeUrl,
        // accessTokenUrl);
        // OAuthToken requestToken =
        // tp.fetchNewRequestToken("http://localhost:8080/encuestame/user/linkedIn");
        // System.out.println("********************************  OAuthToken * "+requestToken);
        // String url = tp.buildAuthorizeUrl(requestToken.getValue(),
        // "http://localhost:8080/encuestame/user/linkedIn");
        // System.out.println("********URL "+url);
        // request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken,
        // WebRequest.SCOPE_SESSION);
    }
}
