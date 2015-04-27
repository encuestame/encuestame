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
package org.encuestame.mvc.controller.json.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;
import org.encuestame.business.gadgets.GadgetsLoader;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.persistence.domain.dashboard.Gadget;
import org.encuestame.utils.enums.LayoutEnum;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DashBoard Json Controller.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
@Controller
public class DashboardJsonController extends AbstractJsonControllerV1 {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());


    /**
     * @api {get} /api/common/gadgets/list.json  Get Dashboard gadgets
     * @apiName GetGadgetsList
     * @apiGroup Dashboard
     * @apiDescription Retrieve all gadgets that have been added to a specific dashboard.
     * @apiParam {Number} dashboardId This is the id(unique identifier ) of the dashboard.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/gadgets/list.json
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
	{
		"error":{

		},
		"success":{
    			"gadgets":[],
    			"dashboard":
                	{
                 		"id":1,
                 		"dashboard_name":"DashboardTest",
                 		"favorite":true,
                 		"dashboard_description":"DashboardtestDescription",
                 		"layout":"AAA",
                 		"sequence":null,
                 		"favorite_counter":null,
                 		"selected":false
                	}
    	}
}
     * @apiError Access is denied
     * @apiErrorExample
		{
			"error":{
				"message":"Access is denied",
				"session":true,"status":403,
				"description":"You do not have access to this resource",
				"anonymousUser":true
				}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/gadgets/list.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getAllWidgets(
            @RequestParam(value = "dashboardId", required = true) Long dashboardId,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                 final List<GadgetBean> gadgets = getDashboardService().getAllGadgetsAvailable(dashboardId);
                 jsonResponse.put("gadgets", gadgets);
                 jsonResponse.put("dashboard", ConvertDomainBean
                        .convertDashboardDomaintoBean(getDashboardService()
                                 .getDashboardbyId(dashboardId)));
            setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     * @api {get} /api/common/dashboard Get all Dashboards
     * @apiName GetAllDashboard
     * @apiGroup Dashboard
     * @apiDescription Get a list of all the dashboard that have been created.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/dashboard
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
       {
		error: { },
		success: {
			items: [
				{
					id: 1,
					dashboard_name: "My Panel",
					favorite: true,
					dashboard_description: "My panel description",
					layout: "AB",
					sequence: 1,
					favorite_counter: 1,
					selected: true
				}
			],
			label: "dashboard_name",
			identifier: "id"
		}
		}
		{
     * @apiError Access is denied
     * @apiErrorExample
		{
			error: {
					message: "Access is denied",
					session: true,
					status: 403,
					description: "You do not have access to this resource",
					anonymousUser: true
					}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/dashboard", method = RequestMethod.GET)
    public @ResponseBody ModelMap getMyDashboards(
            HttpServletRequest request,
            HttpServletResponse response){
         try {
            final List<DashboardBean> dashboards = getDashboardService().getAllDashboards(null, 0);
            setItemReadStoreResponse("dashboard_name", "id", dashboards);
         } catch (Exception e) {
              log.error(e);
              //e.printStackTrace();
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     * @api {get} /api/common/gadgets/directory/list.json Get Directory Gadgets
     * @apiName GetGadgets
     * @apiGroup Dashboard
     * @apiDescription List the different types of gadgets available to display any type of information (ex. Activity, Comments, etc).
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/gadgets/directory/list.json
     * @apiPermission ENCUESTAME_USER
     * @apiSuccessExample
		{
			error:
			{

			},
			success: {
				gadgets: [
					{
						id: "stream",
						name: "stream",
						description: "gadgets_activity_description",
						image: "/gadgets/activity.png"
					},
					{
						id: "comments",
						name: "comments",
						description: "gadgets_comments_description",
						image: "/gadgets/comments.png"
					}
				]
			}
		}
     * @apiErrorExample
		{
			error: {
					message: "Access is denied",
					session: true,
					status: 403,
					description: "You do not have access to this resource",
					anonymousUser: true
					}
		}
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/gadgets/directory/list.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getGadgetsDirectory(HttpServletRequest request,
            HttpServletResponse response){
         try {
             final List<Properties> gadgets = GadgetsLoader.getDirectoy();
             final List<Directory> directory = new ArrayList<DashboardJsonController.Directory>();
             for (Properties properties : gadgets) {
                // TODO: move to ConvertDomainBean.
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
              //e.printStackTrace();
              setError(e.getMessage(), response);
         }
         return returnData();
     }


    /**
     * @api {post} /api/common/dashboard Create Dashboard
     * @apiName PostCreateDashboard
     * @apiGroup Dashboard
     * @apiDescription Create Dashboard(user Interface) where you can add gadgtes to show information quickly.
     * @apiParam {String} name This is the name of the new resource. The Dashboard name.
     * @apiParam {String} desc Description about dashboard.
     * @apiParam {Boolean} [favorite] Save and Organize your dashboard, save as favorite, you can acess faster.
     * @apiParam {String} [layout="AAA_COLUMNS", "BB_BLOCK", "B_BLOCK", "AB_COLUMN_BLOCK", "BA_BLOCK_COLUMN"] Specifies the layout type and the distribution scheme elements.
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/dashboard
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/dashboard", method = RequestMethod.POST)
    public @ResponseBody ModelMap createtDashboard(
            @RequestParam(value = "name", required = true) String boardName,
            @RequestParam(value = "desc", required = true) String boardDesc,
            @RequestParam(value = "favorite", required = false) Boolean favorite,
            @RequestParam(value = "layout", required = false) String layout,
            HttpServletRequest request,
            HttpServletResponse response) {
         try {
             final DashboardBean bean = new DashboardBean();
             bean.setDashboardName(boardName);
             bean.setDashboardDesc(boardDesc);
             bean.setFavorite(favorite);
             bean.setLayout(layout == null ? LayoutEnum.BB_BLOCK.toString() : layout);
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
     * @api {put} /api/common/dashboard Update Dasboard
     * @apiName PutEditDashboard
     * @apiGroup Dashboard
     * @apiDescription Edit Dashboard properties
     * @apiParam {Object} bean - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/dashboard
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/dashboard", method = RequestMethod.PUT)
    public @ResponseBody ModelMap updateDashboard(
            @RequestBody DashboardBean bean,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final Dashboard dashboard = getDashboardService().updateDashboard(bean);
             jsonResponse.put("dashboard", ConvertDomainBean.convertDashboardDomaintoBean(dashboard));
             setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              //e.printStackTrace();
              setError(e.getMessage(), response);
         }
         return returnData();
     }

    /**
     * @api {delete} /api/common/dashboard Remove Dashboard
     * @apiName DeleteDashboard
     * @apiGroup Dashboard
     * @apiDescription Remove a Dashboard panel.
     * @apiParam {Object} bean - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/dashboard
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/dashboard", method = RequestMethod.DELETE)
    public @ResponseBody ModelMap deleteDashboard(
            @RequestBody DashboardBean bean,
            HttpServletRequest request,
            HttpServletResponse response){
         try {
             getDashboardService().deleteDasbboard(bean);
             setSuccesResponse();
         } catch (Exception e) {
              log.error(e);
              ////e.printStackTrace();
              setError(e.getMessage(), response);
         }
         return returnData();
     }


    /**
     * @api {post} /api/common/{gadgetId}/gadget.json Add new Gadget
     * @apiName PostAddGadget
     * @apiGroup Dashboard
     * @apiDescription Add new Gadget on Dashboard.
     * @apiParam {Number} boardId - XXXX
     * @apiParam {String} gadgetId - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/{gadgetId}/gadget.json
     * @apiPermission ENCUESTAME_USER
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/{gadgetId}/gadget.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap addGadgetonDashboard(
            @RequestParam(value = "boardId", required = true) Long boardId,
            @PathVariable final String gadgetId,
            HttpServletRequest request,
            HttpServletResponse response){
        try {
             //System.out.println(boardId);
             //System.out.println(gadgetId);
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             final Gadget gadget = getDashboardService().addGadgetOnDashboard(Long.valueOf(boardId), gadgetId);
             jsonResponse.put("gadget", ConvertDomainBean.convertGadgetDomaintoBean(gadget));
             setItemResponse(jsonResponse);
        } catch (Exception e) {
             ////e.printStackTrace();
             log.error(e);
             setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {put} /api/common/{gadgetId}/gadget.json Move gadget
     * @apiName PutMoveGadget
     * @apiGroup Dashboard
     * @apiDescription Update gadget position on Dashboard.
     * @apiParam {Number} gadgetId - XXXX
     * @apiParam {Number} [position] - XXXX
     * @apiParam {Number} [column] - XXXX
     * @apiParam {Number} [dashboardId] - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/{gadgetId}/gadget.json
     * @apiPermission none
     */
    @RequestMapping(value = "/api/common/{gadgetId}/gadget.json", method = RequestMethod.PUT)
    public @ResponseBody ModelMap moveGadget(
            @PathVariable final Long gadgetId,
            @RequestParam(value = "position", required = false) Integer position,
            @RequestParam(value = "column", required = false) Integer column,
            @RequestParam(value = "dashboardId", required = false) Long dashboardId,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            getDashboardService().moveGadget(gadgetId, dashboardId, position, column);
            setSuccesResponse();
        } catch (Exception e) {
            log.error(e);
            ////e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * @api {delete} /api/common/{gadgetId}/gadget.json Remove gadget
     * @apiName DeleteGadget
     * @apiGroup Dashboard
     * @apiDescription Remove gadget from the Dashboard.
     * @apiParam {Number} gadgetId - XXXX
     * @apiParam {Number} dashboardId - XXXX
     * @apiVersion 1.0.0
     * @apiSampleRequest http://www.encuestame.org/demo/api/common/{gadgetId}/gadget.json
     * @apiPermission none
     */
   @RequestMapping(value = "/api/common/{gadgetId}/gadget.json", method = RequestMethod.DELETE)
   public @ResponseBody ModelMap removeGadget(
           //@RequestParam(value = "gadgetId", required = true) Long gadgetId,
           @PathVariable final Long gadgetId,
           @RequestParam(value = "dashboardId", required = true) Long dashboardId,
           HttpServletRequest request,
           HttpServletResponse response){
       try {
            getDashboardService().removeGadget(gadgetId, dashboardId);
            setSuccesResponse();
       } catch (Exception e) {
           log.error(e);
           ////e.printStackTrace();
           setError(e.getMessage(), response);
       }
       return returnData();
   }

    /**
     *
     * @param gadgetId
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/api/common/dashboard/gadget/load.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap loadGadget(
            @RequestParam(value = "gadgetId", required = true) String gadgetId,
            HttpServletRequest request,
            HttpServletResponse response){
        try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
             setItemResponse(jsonResponse);
        } catch (Exception e) {
            log.error(e);
            //e.printStackTrace();
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
}

