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
package org.encuestame.mvc.controller.social.facebook;

import org.encuestame.business.service.imp.ISecurityService;
import org.encuestame.business.social.IFacebookSocialService;
import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.mvc.controller.social.AbstractSocialController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.FacebookAccessToken;
import org.springframework.social.facebook.FacebookProfile;
import org.springframework.social.facebook.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to Signin with Facebook Account.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 4:46:18 PM
 * @version $Id:$
 */
@Controller
public class FacebookConnectSignIn extends AbstractSocialController{

    @Autowired
    private IFacebookSocialService facebookSocialService;

    /**
     * Sign in the member with their Facebook account.
     * The submitted access token, obtained via a cookie, identifies the connection between a local member account and a Facebook account.
     * For sign-in to succeed, the submitted access token must match a Facebook accessToken on file in our system.
     * Notifies the user if the access token is invalid.
     */
    @RequestMapping(value="/signin/facebook", method=RequestMethod.POST)
    public String signin(@FacebookAccessToken String accessToken) {
        try {
            UserAccount account = this.facebookSocialService.findAccountByConnection(accessToken);
            authenticate(account);
            return "redirect:/";
        } catch (EnMeNoSuchAccountConnectionException e) {
            return handleNoFacebookConnection(new FacebookTemplate(accessToken).getUserProfile());
        } catch (EnMeDomainNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // internal helpers

    private String handleNoFacebookConnection(FacebookProfile userInfo) {
        try {
            //accountRepository.findBySignin(userInfo.getEmail());
            //FlashMap.setWarningMessage("Your Facebook account is not linked with your Greenhouse account. To connect them, sign in and then go to the Settings page.");
            return "redirect:/signin";
        } catch (Exception e) {
           // FlashMap.setInfoMessage("Your Facebook account is not linked with a Greenhouse account. "
            ///        + "If you do not have a Greenhouse account, complete the following form to create one. "
            //        + "If you already have an account, sign in with your username and password, then go to the Settings page to connect with Facebook.");
            // TODO encode form query parameters into url to pre-populate from userinfo
            return "redirect:/signup";
        }
    }

    /**
     * @return the facebookSocialService
     */
    public IFacebookSocialService getFacebookSocialService() {
        return facebookSocialService;
    }

    /**
     * @param facebookSocialService the facebookSocialService to set
     */
    public void setFacebookSocialService(final IFacebookSocialService facebookSocialService) {
        this.facebookSocialService = facebookSocialService;
    }
}
