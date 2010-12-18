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
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.security.SecGroup;
import org.encuestame.utils.web.UnitGroupBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Group Json Service Controller.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since December 16, 2010
 * @version $Id:$
 */

@Controller
public class JsonGroupServiceController extends AbstractJsonController {

    /**
     *
     * @param groupName
     * @param groupDesc
     * @param stateId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "api/groups/{type}Group.json", method = RequestMethod.GET)
    public ModelMap createGroup(
            @RequestParam(value = "groupName", required = true) String groupName,
            @RequestParam(value = "groupDescription", required = false) String groupDesc,
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "groupId", required = false) Long groupId,
            @PathVariable String type,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
              // log.debug(" "+folderName);
               final Map<String, Object> sucess = new HashMap<String, Object>();
               if("create".equals(type)){
                   final UnitGroupBean unitGroupBean = new UnitGroupBean();
                   unitGroupBean.setGroupDescription(groupDesc == null ? "" : groupDesc);
                   unitGroupBean.setGroupName(groupName);
                   unitGroupBean.setStateId(stateId); //TODO: remove
                   sucess.put("groupBean", getSecurityService().createGroup(unitGroupBean, getUserPrincipalUsername()));
                   setItemResponse(sucess);
               } else if ("update".equals(type)) {
                   final SecGroup groupDomain = getSecurityService().getGroupbyIdandUser(groupId, getUserPrincipalUsername());//find by group Id and principal User.
                   if (groupDomain != null){
                       final UnitGroupBean groupBean = ConvertDomainBean.convertGroupDomainToBean(groupDomain);
                       groupBean.setGroupDescription(groupDesc);
                       //TODO: Set state
                        if (!groupName.isEmpty()){
                           groupBean.setGroupName(groupName);
                       }
                       getSecurityService().updateGroup(groupBean);
                       sucess.put("g", groupBean);
                       setItemResponse(sucess);
                   }
               }
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
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
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "api/groups/removeGroup.json", method = RequestMethod.GET)
    public ModelMap deleteGroup(
            @RequestParam(value = "groupId", required = true) Long groupId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
               log.debug("Group Id"+ groupId);
               getSecurityService().deleteGroup(groupId);
               setSuccesResponse();
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }

    /**
     *
     * @param groupName
     * @param groupDesc
     * @param stateId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "api/groups/createGroup.json", method = RequestMethod.GET)
    public ModelMap updateGroup(
            @RequestParam(value = "groupName", required = true) String groupName,
            @RequestParam(value = "groupDescription", required = false) String groupDesc,
            @RequestParam(value = "stateId", required = false) Long stateId,

            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
           try {
              // log.debug(" "+folderName);
               final UnitGroupBean unitGroupBean = new UnitGroupBean();
               unitGroupBean.setGroupDescription(groupDesc);
               unitGroupBean.setGroupName(groupName);
               unitGroupBean.setStateId(stateId);

              final Map<String, Object> sucess = new HashMap<String, Object>();
                   final UnitGroupBean gb = getSecurityService().updateGroup(unitGroupBean);
                   sucess.put("groupBean", gb);
                   setItemResponse(sucess);
          } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
          }
          return returnData();
      }
}
