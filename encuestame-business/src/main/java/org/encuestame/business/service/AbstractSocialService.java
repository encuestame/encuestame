
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.TwitterAPIOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.IllegalSocialActionException;
import org.encuestame.utils.security.SocialAccountBean;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

/**
 * Social service layer.
 * @author Picado, Juan juanATencuestame.org
 * @deprecated moved to provider packages.
 */
@Deprecated
public class AbstractSocialService extends AbstractConfigurationService {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());




}
