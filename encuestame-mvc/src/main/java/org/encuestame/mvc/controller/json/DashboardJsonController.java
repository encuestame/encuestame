/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.business.gadgets.GadgetsLoader;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * DashBoard Json Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
@Controller
public class DashboardJsonController extends AbstractJsonController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/gadgets/list.json", method = RequestMethod.GET)
    public ModelMap getAllWidgets(HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                 final List<GadgetBean> gadgets = getDashboardService().getAllGadgetsAvailable();
                 jsonResponse.put("gadgets", gadgets);
                 setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/gadgets/directory.json", method = RequestMethod.GET)
    public ModelMap getGadgetsDirectory(HttpServletRequest request,
            HttpServletResponse response){
         try {
             final List<Properties> gadgets = GadgetsLoader.getDirectoy();
             final List<Directory> directory = new ArrayList<DashboardJsonController.Directory>();
             for (Properties properties : gadgets) {
                final Directory directoryItem = new Directory();
                directoryItem.setDescription(properties.getProperty("description"));
                directoryItem.setId(properties.getProperty("name"));
                directoryItem.setImage(properties.getProperty("image"));
                directoryItem.setName(properties.getProperty("name"));
                directory.add(directoryItem);
            }
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             log.debug("/api/common/gadgets/directory.json "+gadgets.size());
             jsonResponse.put("gadgets", directory);
             setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     * Directory.
     * @author Picado, Juan juanATencuestame.org
     * @since 05/08/2011
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Directory implements Serializable {

        @JsonProperty(value = "id")
        private String id;

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "description")
        private String description;

        @JsonProperty(value = "image")
        private String image;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image the image to set
         */
        public void setImage(String image) {
            this.image = image;
        }
    }

    /**
     * Dashboard actions.
     * @param boardName
     * @param boardDesc
     * @param favorite
     * @param layout
     * @param actionType
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/dashboard/create-dashboard.json", method = RequestMethod.POST)
    public ModelMap createtDashboard(
            @RequestParam(value = "name", required = true) String boardName,
            @RequestParam(value = "desc", required = true) String boardDesc,
            @RequestParam(value = "favorite", required = true) Boolean favorite,
            @RequestParam(value = "layout", required = true) String layout,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final DashboardBean bean = new DashboardBean();
             bean.setDashboardName(boardName);
             bean.setDashboardDesc(boardDesc);
             bean.setFavorite(favorite);
             bean.setLayout(layout);
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final Dashboard dashboard = getDashboardService().createDashboard(bean);
             jsonResponse.put("dashboard", ConvertDomainBean.convertDashboardDomaintoBean(dashboard));
             setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     *
     * @param boardId
     * @param gadgetId
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/gadgets/add.json", method = RequestMethod.GET)
    public ModelMap addGadgetonDashboard(
            @RequestParam(value = "boardId", required = true) Long boardId,
            @RequestParam(value = "gadgetId", required = true) Long gadgetId,
            HttpServletRequest request,
            HttpServletResponse response){
        try {
             getDashboardService().addGadgetOnDashboard(boardId, gadgetId);
             setSuccesResponse();
        } catch (Exception e) {
             log.error(e);
             setError(e.getMessage(), response);
        }
        return returnData();
    }
}
