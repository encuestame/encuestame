
package org.encuestame.mvc.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.IApplicationServices;
import org.encuestame.business.setup.ApplicationStartup;
import org.encuestame.business.setup.Startup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SetupInterceptor implements HandlerInterceptor {

    protected Logger log = Logger.getLogger(this.getClass());

    @Autowired
    public Startup startup;

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        log.debug("SetupInterceptor afterCompletion");
        log.info(this.startup);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
         log.debug("SetupInterceptor postHandle");
         log.info(this.startup);

    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
        log.debug("SetupInterceptor preHandle");
        log.info(this.startup);
        return true;
    }

    /**
     * @return the startup
     */
    public Startup getStartup() {
        return startup;
    }

    /**
     * @param startup the startup to set
     */
    public void setStartup(Startup startup) {
        this.startup = startup;
    }



}
