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
package org.encuestame.mvc.controller.settings;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.business.security.ISecurityContext;
import org.encuestame.business.service.SecurityService.Profile;
import org.encuestame.business.service.imp.ISecurityService;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Settings Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 29, 2011 11:37:56 AM
 * @version $Id:$
 */
@Controller
public class SettingsJsonController extends AbstractJsonController{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Upgrade profile settings.
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/settings/profile/{type}/update.json", method = RequestMethod.GET)
    public ModelMap upgradeProfile(HttpServletRequest request,
            @PathVariable String type,
            @RequestParam(value = "data", required = true) String data,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        log.debug("update profile type:"+type);
        log.debug("update profile data:"+data);
        try {
            final ISecurityService security = getSecurityService();
            final ValidateOperations operations = new ValidateOperations(security);
            final HashMap<String, Object> listError = new HashMap<String, Object>();
            if(type.equals(Profile.EMAIL.toString())){
                log.debug("update email");
                if (operations.validateUserEmail(data)) {
                    security.upadteAccountProfile(type, data,
                            getUserPrincipalUsername());
                    setSuccesResponse();
                } else {
                    listError.put(type, "email not valid");
                }
            } else if(type.equals(Profile.USERNAME.toString())){
                log.debug("update username");
                if (operations.validateUsername(data)) {
                    security.upadteAccountProfile(type, data,
                            getUserPrincipalUsername());
                    setSuccesResponse();
                } else {
                    listError.put(type, "username not valid");
                }
            } else {
                setError("type not valid", response);
            }
            if (!listError.isEmpty()) {
                log.debug("list errors " + listError.size());
                setError(listError, response);
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
            throw new JsonGenerationException(e.getMessage());
        }
        return returnData();
    }
}
