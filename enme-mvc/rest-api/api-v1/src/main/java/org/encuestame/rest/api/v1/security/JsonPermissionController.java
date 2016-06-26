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
package org.encuestame.rest.api.v1.security;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.EnumerationUtils;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Json Permission.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 11, 2010 10:54:48 PM
 * @version $Id:$
 */
@Controller
public class JsonPermissionController  extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(JsonPermissionController.class);


            /**
             * @api {get} /api/admon/list-permissions.json Get Permissions
             * @apiName GetPermissions
             * @apiGroup Permissions
             * @apiDescription List of permissions available to assign to the user.
             * @apiVersion 1.0.0
             * @apiSampleRequest http://www.encuestame.org/demo/api/admon/list-permissions.json
             * @apiPermission ENCUESTAME_OWNER
             * @apiSuccessExample
             * {
            "error": {

            },
            "success": {
            "permissions": [
            {
            "id": 1,
            "permission": "ENCUESTAME_ADMIN",
            "description": "ENCUESTAME_ADMIN"
            },
            {
            "id": 2,
            "permission": "ENCUESTAME_OWNER",
            "description": "ENCUESTAME_OWNER"
            },
            {
            "id": 3,
            "permission": "ENCUESTAME_ADMIN",
            "description": "ENCUESTAME_ADMIN"
            }
            ]
            }
            }
             */

    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/list-permissions.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getPermissions(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("permissions", getSecurityService().loadAllListPermission());
        setItemResponse(jsonResponse);
        return returnData();
    }

    /**
     * @api {get} /api/admon/list-user-permissions.json Permissions by UserId
     * @apiName GetPermissionsbyUser
     * @apiGroup Permissions
     * @apiDescription Load the list of permissions assigned to a user.
     * @apiParam {Number} id User id
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/admon/list-user-permissions.json?id=1
     * @apiPermission ENCUESTAME_OWNER
     * @apiSuccessExample
     *	{
     * 		error: { },
     * 		success: {
     * 			userPermissions[
     * 				{
	 *     				id: 5,
	 *     				permission: "ENCUESTAME_USER",
	 *     				description: "ENCUESTAME_USER"
     *  			},
     *  			{
	 * 				id: 4,
	 *     				permission: "ENCUESTAME_EDITOR",
	 *    				description: "ENCUESTAME_EDITOR"
     *  			},
     *  			{
     * 					id: 3,
     * 					permission: "ENCUESTAME_PUBLISHER",
     * 					description: "ENCUESTAME_PUBLISHER"
     *  			}
	 *			]
	 *		}
	 *	}

  */

    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/list-user-permissions.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getUserPermissions(
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
            } catch (EnMeNoResultsFoundException e) {
                setError(e.getMessage(), response);
                log.error(e);
            }
        return returnData();
    }


    /**
     * @api {get} /api/admon/{action}-permission.json Permission actions
     * @apiName GetPermissionsActions
     * @apiGroup Permissions
     * @apiDescription Add or remove permissions for a user.
     * @apiParam {Number} id User ID in which you want to perform the action: Add new permission or remove
     * @apiParam {String="ENCUESTAME_USER","ENCUESTAME_EDITOR","ENCUESTAME_PUBLISHER","ENCUESTAME_OWNER","ENCUESTAME_ADMIN"} permission Permission value to assign or remove.
     * @apiParam {String="add","remove"} action available actions to execute.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/admon/{action}-permission.json
     * @apiPermission ENCUESTAME_OWNER
     * @apiSuccessExample
	 *	{
     * 		error: {
     *
     * 		},
     * 		success: {
     * 			p: "ok"
     * 		}
     * 	}
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/{action}-permission.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap addPermission(
             @PathVariable String action,
             @RequestParam(value = "id", required = true) Long userId,
             @RequestParam(value = "permission", required = true) String permission,
             HttpServletRequest request,
             HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
            try {
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                getSecurityService().updatePermission(userId,
                                     getUserPrincipalUsername(),
                        EnumerationUtils.getEnumFromString(EnMePermission.class, permission), action);
                jsonResponse.put("p", "ok");
                setItemResponse(jsonResponse);
            } catch (EnMeException e) {
                setError(e.getMessage(), response);
                log.error(e);
            }
            return returnData();
    }
}
