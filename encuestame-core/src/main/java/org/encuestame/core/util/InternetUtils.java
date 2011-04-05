package org.encuestame.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.validator.UrlValidator;
import org.apache.log4j.Logger;

/**
 * Internet Utils.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public class InternetUtils {

    protected static Logger log = Logger.getLogger(InternetUtils.class);

    private final static Integer TIMEOUT = 5000;

    /**
     * Ping hostname.
     *
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
        final UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
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
