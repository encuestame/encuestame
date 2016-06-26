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
package org.encuestame.rest.api.v1.social;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.util.exception.IllegalSocialActionException;
import org.encuestame.utils.json.SocialAccountBean;
import org.encuestame.utils.social.SocialProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Social Account Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */
@Controller
public class SocialAccountsJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(SocialAccountsJsonController.class);

            /**
             * @api {get} /api/social/actions/account/{type}.json Social Accounts actions
             * @apiName GetSocialAccounts
             * @apiGroup SocialAccounts
             * @apiDescription Change state / remove / disable / enable of social account.
             * @apiParam {String} type - XXXX
             * @apiParam {Number} socialAccountId - XXXX
             * @apiVersion 1.0.0
             * @apiSampleRequest http://www.encuestame.org/demo/api/social/actions/account/{type}.json
             * @apiPermission ENCUESTAME_USER
             */

    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/social/actions/account/{type}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap actionTwitterAccount(
            @PathVariable String type,
            @RequestParam(value = "socialAccountId", required = true) Long socialAccountId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
           getSecurityService().changeStateSocialAccount(socialAccountId, type);
           setSuccesResponse();
        } catch (IllegalSocialActionException e) {
            setError(e.getMessage(), response);
        } catch (EnMeNoResultsFoundException e) {
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {get} /api/common/social/accounts.json Get Social Accounts
     * @apiName GetTypesSocialAccounts
     * @apiGroup SocialAccounts
     * @apiDescription  Return Social Valid Accounts.
     * @apiParam {String} [provider - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/social/accounts.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/accounts.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getSocialAccountByType(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "provider", required = false) String provider)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
            provider = provider == null ? "all" : provider;
            final List<SocialAccountBean> accounts = getSecurityService()
                    .getValidSocialAccounts(org.encuestame.utils.EnumerationUtils.getEnumFromString(SocialProvider.class, provider), true);
            setItemReadStoreResponse("socialAccounts", "id", accounts);
        } catch (Exception e) {
            log.error(e);
            //e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * @api {get} /api/common/social/providers.json Providers
     * @apiName GetProviders
     * @apiGroup SocialAccounts
     * @apiDescription Return list of enabled providers.
     * @apiParam {Number} provider - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/social/providers.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/providers.json", method = RequestMethod.GET)
    public ModelMap getProvidersEnabled(
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
             final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
             final List<SocialProvider> providers = new ArrayList<SocialProvider>();
                 providers.add(SocialProvider.TWITTER);
                 providers.add(SocialProvider.GOOGLE_PLUS);
                 providers.add(SocialProvider.LINKEDIN);
                 providers.add(SocialProvider.IDENTICA);
                 providers.add(SocialProvider.FACEBOOK);
             jsonResponse.put("provider", providers);
             setItemResponse(jsonResponse);
             log.debug("Social providers enabled "+providers.size());
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
