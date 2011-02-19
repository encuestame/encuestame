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
package org.encuestame.mvc.controller.security.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Json Permission.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 11, 2010 10:54:48 PM
 * @version $Id:$
 */
@Controller
public class JsonPermissionController  extends AbstractJsonController{


    /**
     * Load All List of Permissions.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_ADMIN')")
    @RequestMapping(value = "/api/admon/list-permissions.json", method = RequestMethod.GET)
    public ModelMap getPermissions(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("permissions", getSecurityService().loadAllListPermission());
        setItemResponse(jsonResponse);
        return returnData();
    }

    /**
     * Load List of Permissions by UserId.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_ADMIN')")
    @RequestMapping(value = "/api/admon/list-user-permissions.json", method = RequestMethod.GET)
    public ModelMap getUserPermissions(
            @RequestParam(value = "id", required = true) Long userId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                final UserAccountBean user = getUser(userId);
                log.debug("user.getListPermission() "+user.getUsername());
                log.debug("user.getListPermission() "+user.getListPermission().size());
                jsonResponse.put("userPermissions", user.getListPermission());
                setItemResponse(jsonResponse);
            } catch (EnMeDomainNotFoundException e) {
                setError(e.getMessage(), response);
                log.error(e);
            }
        return returnData();
    }

    /**
     *
     * @param userId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_ADMIN')")
    @RequestMapping(value = "/api/admon/{action}-permission.json", method = RequestMethod.GET)
    public ModelMap addPermission(
             @PathVariable String action,
             @RequestParam(value = "id", required = true) Long userId,
             @RequestParam(value = "permission", required = true) String permission,
             HttpServletRequest request,
             HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                getSecurityService().updatePermission(userId,
                                     getUserPrincipalUsername(),
                                     EnMePermission.getPermissionString(permission), action);
                jsonResponse.put("p", "ok");
                setItemResponse(jsonResponse);
            } catch (EnMeExpcetion e) {
                setError(e.getMessage(), response);
                log.error(e);
            }
            return returnData();
    }
}
