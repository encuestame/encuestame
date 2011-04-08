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
package org.encuestame.mvc.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.springframework.web.servlet.HandlerInterceptor;


public abstract class AbstractEnMeInterceptor extends AbstractBaseOperations implements HandlerInterceptor{


    /**
     * Create cookie.
     * @param cookieName
     * @param response
     */
    protected void createAddCookie(
            final String cookieName,
            final HttpServletResponse response,
            final String value){
        //Cookie cookie = new Cookie(cookieName, value);
        //cookie.setMaxAge(expiry)
        //response.addCookie(cookie);
    }
}
