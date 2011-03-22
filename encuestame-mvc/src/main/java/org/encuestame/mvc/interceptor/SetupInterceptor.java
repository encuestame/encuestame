
package org.encuestame.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.business.setup.StartupProcess;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SetupInterceptor implements HandlerInterceptor {

    protected Logger log = Logger.getLogger(this.getClass());

    //@Autowired
    public StartupProcess startup;

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
    public StartupProcess getStartup() {
        return startup;
    }

    /**
     * @param startup the startup to set
     */
    public void setStartup(StartupProcess startup) {
        this.startup = startup;
    }



}
