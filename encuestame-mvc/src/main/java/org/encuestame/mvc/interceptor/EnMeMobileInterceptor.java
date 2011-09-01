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
package org.encuestame.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Extended Interceptor for mobile device.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 30, 2011
 */
public class EnMeMobileInterceptor extends HandlerInterceptorAdapter {

    /**
     * Device resolver.
     */
    private final DeviceResolver deviceResolver;

    /**
     * Log.
     */
    private static Logger log = Logger.getLogger(EnMeMobileInterceptor.class);

    /**
     * Create a device resolving {@link HandlerInterceptor} that defaults to a {@link LiteDeviceResolver} implementation.
     */
    public EnMeMobileInterceptor() {
        this(new LiteDeviceResolver());
        log.debug("Creating EnMeMobileInterceptor");
    }

    /**
     * Create a device resolving {@link HandlerInterceptor}.
     * @param deviceResolver the device resolver to delegate to in
     * {@link #preHandle(HttpServletRequest, HttpServletResponse, Object)}
     */
    public EnMeMobileInterceptor(DeviceResolver deviceResolver) {
        log.debug("Creating EnMeMobileInterceptor deviceResolver:{"+deviceResolver);
        this.deviceResolver = deviceResolver;
    }

    /**
     * Override device selected operation.
     */
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        Device device = deviceResolver.resolveDevice(request);
        //log.debug("customized mobile resolver");
        final Boolean enabledMobileDevice = EnMePlaceHolderConfigurer
                .getBooleanProperty("application.mobile.enabled");
        //log.debug("deviceResolver application.mobile.enabled:{"+enabledMobileDevice);
        final Boolean forceMobileDevice = EnMePlaceHolderConfigurer
                .getBooleanProperty("application.mobile.only");
        //log.debug("deviceResolver application.mobile.only:{"+forceMobileDevice);
        // is mobile device enabled?
        if (enabledMobileDevice) {
            //force to use mobile view.
            if (forceMobileDevice) {
                request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE,
                        new EnMeDevice(Boolean.TRUE));
            } else {
                request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE,
                        device);
            }
        } else {
            request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, new EnMeDevice(Boolean.FALSE));
        }
        return true;
    }
}

/**
 * Customized device implementation.
 */
class EnMeDevice implements Device {

    private boolean device = false;

    public EnMeDevice(final boolean device) {
        this.device = device;
    }

    @Override
    public boolean isMobile() {
        return device;
    }
}