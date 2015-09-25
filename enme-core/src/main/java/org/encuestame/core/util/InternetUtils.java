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
package org.encuestame.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;

/**
 * Internet Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public class InternetUtils {

    protected static Logger log = Logger.getLogger(InternetUtils.class);

    private static final Integer TIMEOUT = 5000;

    /**
     * Ping hostname.
     * @param domain
     * @return
     */
    public static boolean hostReachable(final String domain) {
        InetAddress host;
        boolean reachable = false;
        try {
            // Try to reach the specified address within the timeout
            // periode. If during this periode the address cannot be
            // reach then the method returns false.
            host = InetAddress.getByName(domain);
            reachable = host.isReachable(InternetUtils.TIMEOUT);
        } catch (UnknownHostException e) {
            log.error("hostReachable " + e.getMessage());
        } catch (IOException e) {
            log.error("hostReachable " + e.getMessage());
        }
        return reachable;
    }

    /**
     * Validate URL.
     * @param url URL
     * @return if validate or not.
     */
    public static boolean validateUrl(final String url){
        final String[] schemes = {"http","https"};
        if (!EnMePlaceHolderConfigurer.getBooleanProperty("encuestame.error.debug")) {
            final UrlValidator urlValidator = new UrlValidator(schemes);
            return urlValidator.isValid(url);
        } else {
            return true;
        }
    }

    /**
     * Build Domain.
     * @param request {@link HttpServletRequest}.
     * @return
     */
    private static String getRequestDomain(final HttpServletRequest request){
            final StringBuffer stringBuffer = new StringBuffer(InternetUtils.isSecure(request) ? "https://" : "http://");
            stringBuffer.append(request.getServerName());
            if(request.getRemotePort() != 80){
                stringBuffer.append(":");
                stringBuffer.append(request.getLocalPort());
            }
            stringBuffer.append(request.getContextPath());
            return stringBuffer.toString();
    }

    /**
     * Return the domain of the application either defined in the config file or from the HttpServletRequest.
     * @param request
     * @return
     */
    public static String getDomain(final HttpServletRequest request){
        String domain = EnMePlaceHolderConfigurer.getProperty("application.domain");
        if(domain == null || domain.isEmpty()){
            domain = InternetUtils.getRequestDomain(request);
        }
        return domain;
    }

    /**
     *
     * @param request
     * @return
     */
    public static Boolean isSecure(final HttpServletRequest request){
        Boolean secure = false;
        final String protocol = request.getProtocol();
        if (protocol.indexOf("HTTPS") > -1) {
            secure = true;
        } else {
            secure = false;
            }
        return secure;
    }
    
    /**
     * 
     * @param addr
     * @return
     */
    public static boolean isThisMyIpAddress(InetAddress addr) {
        // Check if the address is a valid special local or loop back
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true;

        // Check if the address is defined on any interface
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }

    public static boolean pingTwitter() {
        return InternetUtils.hostReachable("twitter.com");
    }

    public static boolean pingFacebook() {
        return InternetUtils.hostReachable("facebook.com");
    }

    public static boolean pingLinkedIn() {
        return InternetUtils.hostReachable("linkedin.com");
    }

    public static boolean pingGoogle() {
        return InternetUtils.hostReachable("google.com");
    }

}
