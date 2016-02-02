package org.encuestame.mvc.interceptor;

import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckInstallInterceptor extends AbstractEnMeInterceptor {

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2) throws Exception {
         final String uuid = EnMePlaceHolderConfigurer.getConfigurationManager().getProperty("install.uuid");
         if (uuid != null) {
                  final ModelAndView modelAndView = new ModelAndView("redirect:/home");
                  throw new ModelAndViewDefiningException(modelAndView);
         }
        return true;
    }


}
