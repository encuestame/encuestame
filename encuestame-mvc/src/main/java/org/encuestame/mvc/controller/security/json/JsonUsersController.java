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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.business.service.SecurityService.Profile;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.RelativeTimeEnum;
import org.encuestame.utils.security.ProfileUserAccount;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 30, 2010 11:08:20 PM
 * @version $Id:$
 */
@Controller
public class JsonUsersController extends AbstractJsonController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Get List of Users.
     * @param username username
     * @param request request
     * @param response response
     * @return list of json users.
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/admon/users.json", method = RequestMethod.GET)
    public ModelMap get(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "start", required = false) Integer start,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            //TODO: should be limit and paginate results.
            log.debug("limit "+limit);
            log.debug("start "+start);
            final Map<String, Object> success = new HashMap<String, Object>();
            final List<UserAccountBean> userList = getServiceManager()
                  .getApplicationServices().getSecurityService().loadListUsers(getUserPrincipalUsername(), start, limit);
            log.debug("size users to retrieve "+userList.size());
            //Filter values.
            for (UserAccountBean unitUserBean : userList) {
                log.debug("user to retrieve "+unitUserBean.getUsername());
                getSecurityService().getStatsByUsers(unitUserBean); //filter
                final HashMap<Integer, RelativeTimeEnum> relativeTime = getRelativeTime(unitUserBean.getDateNew());
                final Iterator it = relativeTime.entrySet().iterator();
                while (it.hasNext()) {
                    final Map.Entry<Integer, RelativeTimeEnum> e = (Map.Entry<Integer, RelativeTimeEnum>)it.next();
                    log.debug("--"+e.getKey() + "**" + e.getValue());
                    unitUserBean.setRelateTimeEnjoy(convertRelativeTimeMessage(e.getValue(), e.getKey(), request));
                }
            }
            success.put("users", userList);
            long total = getServiceManager().getApplicationServices()
            .getSecurityService().totalOwnUsers(getUserPrincipalUsername());
            log.debug("user total users "+total);
            success.put("total", total);
            setItemResponse(success);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Get User Info.
     * @param userId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/user-info.json", method = RequestMethod.GET)
    public ModelMap getUserInfo(
            @RequestParam(value = "id", required = true) Long userId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> sucess = new HashMap<String, Object>();
            final UserAccountBean user = getUser(userId);
            log.debug("user info "+userId);
            if (user == null) {
                setError(new EnMeNoResultsFoundException("user not found").getMessage(), response);
                log.error("user not found");
            } else {
                sucess.put("user", user);
                setItemResponse(sucess);
            }
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Get my info profile.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/admon/info-profile.json", method = RequestMethod.GET)
    public ModelMap getMyUserInfo(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            final Map<String, Object> sucess = new HashMap<String, Object>();
            final ProfileUserAccount user = getProfileUserInfo();
            if (user == null) {
                setError("user not found", response);
                log.error("user not found");
            } else {
                sucess.put("profile", user);
                setItemResponse(sucess);
            }
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Create User.
     * @param username
     * @param email
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/create-user.json", method = RequestMethod.POST)
    public ModelMap createUser(
            @RequestParam(value = "newUsername", required = true) String username,
            @RequestParam(value = "newEmailUser", required = true) String email,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        try {
            log.debug("user newUsername "+username);
            log.debug("user newEmailUser "+email);
            final UserAccountBean userBean = new UserAccountBean();
            userBean.setEmail(email);
            userBean.setUsername(username);
            final ValidateOperations cv = new ValidateOperations( getServiceManager().getApplicationServices()
                  .getSecurityService());
            if(cv.validateEmail(email)){ //TODO && cv.validateUsername(username)
                final Map<String, Object> sucess = new HashMap<String, Object>();
                getServiceManager().getApplicationServices().getSecurityService()
                .createUser(userBean, getUserPrincipalUsername());
                sucess.put("userAdded", "ok");
                setItemResponse(sucess);
            } else {
                setError("user or email exist are used.", response);
            }
        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Remove User.
     * @param username
     * @param email
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/remove-user.json", method = RequestMethod.GET)
    public ModelMap removeUser(
            @RequestParam(value = "id", required = true) String id,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         return returnData();
    }


    /**
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/reset-password-user.json", method = RequestMethod.GET)
    public ModelMap resetUserPassword(
            @RequestParam(value = "id", required = true) String id,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         return returnData();
    }

    /**
     *
     * @param groupId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/groups/assingToUser.json", method = RequestMethod.GET)
    public ModelMap assingUserToGroup(
            @RequestParam(value = "id", required = true) Long groupId,
            @RequestParam(value = "userId", required = true) Long userId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
       try {
           getSecurityService().assingGroupFromUser(groupId, userId, getUserPrincipalUsername());
           setSuccesResponse();
       } catch (Exception e) {
           log.error(e);
           e.printStackTrace();
           setError(e.getMessage(), response);
       }
       return returnData();
    }

    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/admon/changeUserStatus.json", method = RequestMethod.GET)
    public ModelMap changeUserStatus(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
       try {
           getSecurityService().changeUserStatus(getUserPrincipalUsername());
           setSuccesResponse();
       } catch (Exception e) {
           log.error(e);
           e.printStackTrace();
           setError(e.getMessage(), response);
       }
       return returnData();
    }

    /**
     * Upgrade user account profile.
     * @param request
     * @param property
     * @param value
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/user/profile/upgrade.json", method = RequestMethod.GET)
    public ModelMap upgradeProfile(HttpServletRequest request,
            @RequestParam(value = "property", required = true) String property,
            @RequestParam(value = "value", required = true) String value,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        try {
            getSecurityService().upadteAccountProfile(Profile.findProfile(property.toUpperCase()), value,
                    getUserPrincipalUsername());
            setSuccesResponse();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }


    /**
     * Check if profile item is valid.
     * @param request
     * @param type
     * @param value
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_OWNER')")
    @RequestMapping(value = "/api/user/account/validate.json", method = RequestMethod.GET)
    public ModelMap validateItem(HttpServletRequest request,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "value", required = true) String value,
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            final ValidateOperations operations = new ValidateOperations(getSecurityService(), getUserAccount());
            final UserAccount account = getUserAccount();
            if (Profile.findProfile(type).equals(Profile.USERNAME)) {
                if(operations.validateUsername(filterValue(value), account)){
                    jsonResponse.put("validate", true);
                } else {
                    jsonResponse.put("validate", false);
                }
            } else if (Profile.findProfile(type).equals(Profile.EMAIL)) {
                if(operations.validateUserEmail(filterValue(value), account)){
                    jsonResponse.put("validate", true);
                } else {
                    jsonResponse.put("validate", false);
                }
            } else {
                setError("invalid params", response);
            }
            log.debug("messages"+ operations.getMessages().toString());
            jsonResponse.put("messages", operations.getMessages());
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }
}
