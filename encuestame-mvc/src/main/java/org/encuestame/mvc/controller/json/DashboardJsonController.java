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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.dashboard.Dashboard;
import org.encuestame.utils.web.DashboardBean;
import org.encuestame.utils.web.GadgetBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "/api/common/gadgets.json", method = RequestMethod.GET)
    public ModelMap getAllWidgets(HttpServletRequest request,
            HttpServletResponse response){
         try {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                 final List<GadgetBean> gadgets = getDashboardService().getAllGadgetsAvailable();
                 jsonResponse.put("gadgets", gadgets);
                 setItemResponse(jsonResponse);
         } catch (Exception e) {
              log.error(e);
              e.printStackTrace();
              setError(e.getMessage(), response);
         }
         return returnData();
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
              e.printStackTrace();
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
    @RequestMapping(value = "/api/common/dashboard/addGadget.json", method = RequestMethod.GET)
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
             e.printStackTrace();
             setError(e.getMessage(), response);
        }
        return returnData();
    }
}
