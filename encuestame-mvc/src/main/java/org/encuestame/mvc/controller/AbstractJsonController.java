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
package org.encuestame.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.encuestame.core.persistence.dao.INotification;
import org.encuestame.core.persistence.dao.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Abstract Json Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 15, 2010 11:31:20 AM
 * @version $Id:$
 */
public abstract class AbstractJsonController extends BaseController{

    /** Model. **/
    private ModelMap jsonMap = new ModelMap();

    /** {@link NotificationDao}. **/
    @Autowired
    private INotification notificationDao;

    /** Success. **/
    private Map<String, Object> sucess = new HashMap<String, Object>();

    /** Error Json. **/
    private Map<String, Object> error = new HashMap<String, Object>();

    /**
     * Return Data.
     * @return
     */
    protected ModelMap returnData(){
         Map<String, Object> response = new HashMap<String, Object>();
         Assert.notNull(this.sucess);
         Assert.notNull(this.error);
         response.put("success", this.sucess);
         response.put("error", this.error);
         return this.jsonMap.addAllAttributes(response);
    }

    /**
     * Set Error.
     * @param error error.
     */
    protected void setError(final Object error){
         this.error.put("error", error);
    }

    /**
     * Set Item Response.
     * @param label
     * @param item
     */
    protected void setItemResponse(final String label, final Object item){
         this.sucess.put(label, item);
    }

    /**
     * Handler for {@link AccessDeniedException}
     * @param ex exception
     * @return {@link ModelAndView}.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException (AccessDeniedException ex) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("MappingJacksonJsonView");
      mav.addObject("error", ex.getMessage());
      return mav;
    }

    /**
     * @return the notificationDao
     */
    public INotification getNotificationDao() {
        return notificationDao;
    }

    /**
     * @param notificationDao the notificationDao to set
     */
    public void setNotificationDao(INotification notificationDao) {
        this.notificationDao = notificationDao;
    }
}
