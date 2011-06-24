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
package org.encuestame.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.filter.RequestSessionMap;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 6, 2011
 */
public class EnMeMappingExceptionResolver extends SimpleMappingExceptionResolver{

    private Logger logger = Logger.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String errorView = "redirect:/error";
        if (ex.equals(DataAccessResourceFailureException.class)) {
            errorView = "redirect:/error";
        }
        ModelAndView modelAndView = new ModelAndView(errorView);
        if (logger.isDebugEnabled()) {
            ex.printStackTrace();
        }
        //set get parameters;
        //modelAndView.getModel().put(EXCEPTION_TYPE, ex.getClass());
        //modelAndView.getModel().put("error", "fail");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        System.out.println(sw.toString());
        RequestSessionMap.setErrorMessage(ex.getMessage(), sw.toString());
        return modelAndView;
    }
}
