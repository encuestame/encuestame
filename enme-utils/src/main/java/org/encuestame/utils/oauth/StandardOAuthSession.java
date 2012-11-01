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
package org.encuestame.utils.oauth;

/**
 * Describe Standard OAuth Session.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 24, 2010 3:53:13 PM
 * @version $Id:$
 */
public class StandardOAuthSession implements OAuthSession {

        private String apiKey;

        private String callbackUrl;

        private String requestToken;

        private String secret;

        private Long authorizingAccountId;

        private String verifier;

        public StandardOAuthSession(String apiKey, String callbackUrl, String requestToken, String secret) {
            this.apiKey = apiKey;
            this.callbackUrl = callbackUrl;
            this.requestToken = requestToken;
            this.secret = secret;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public String getRequestToken() {
            return requestToken;
        }

        public String getSecret() {
            return secret;
        }

        public void authorize(Long authorizingAccountId, String verifier) {
            this.authorizingAccountId = authorizingAccountId;
            this.verifier = verifier;
        }

        public boolean authorized() {
            return authorizingAccountId != null;
        }

        public Long getAuthorizingAccountId() {
            return authorizingAccountId;
        }

        public String getVerifier() {
            return verifier;
        }
   }
