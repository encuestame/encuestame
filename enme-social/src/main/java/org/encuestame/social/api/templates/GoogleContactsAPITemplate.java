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
package org.encuestame.social.api.templates;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.oauth2.support.ProtectedResourceClientFactory;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.social.api.AbstractSocialAPISupport;
import org.encuestame.core.social.operation.GoogleContactsAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;

/**
 * Google Contacts API template.
 * @author Picado, Juan juanATencuestame.org
 * @since 29/07/2011
 */
public class GoogleContactsAPITemplate extends AbstractSocialAPISupport
        implements GoogleContactsAPIOperations {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     *
     */
    private String GOOGLE_KEY = EnMePlaceHolderConfigurer.getProperty("google.api.key");

    /**
     *
     */
    private String GOOGLE_CONTACTS_FULL_REST = "https://www.google.com/m8/feeds/contacts/default/full/1?key={key}&alt=json";

    //private String GOOGLE_SINGLE_PROFILE_REST= "https://www.google.com/m8/feeds/contacts/{email}/full/{id}?key={key}&alt=json";


    /**
     *
     * @param socialAccount
     */
    public GoogleContactsAPITemplate(final SocialAccount socialAccount) {
        this(socialAccount.getAccessToken(), socialAccount.getApplicationKey()
                .toString());
    }

    /**
     * Constructor.
     *
     * @param accessToken
     */
    public GoogleContactsAPITemplate(final String accessToken,
            final String googleKey) {
        setRestTemplate(ProtectedResourceClientFactory.draft10(accessToken));
    }

    /**
     *
     */
    public SocialUserProfile getProfile() {
        Map profileMap = getRestTemplate().getForObject(
                this.GOOGLE_CONTACTS_FULL_REST, Map.class, this.GOOGLE_KEY);
        final SocialUserProfile profile = new SocialUserProfile();
        log.debug(profileMap);
        Map feed = (Map) profileMap.get("feed");
        LinkedHashMap mail = (LinkedHashMap) feed.get("id");
        return profile;
    }

    @Override
    public TweetPublishedMetadata updateStatus(String status) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
