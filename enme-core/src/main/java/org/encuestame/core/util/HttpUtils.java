package org.encuestame.core.util;

import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by jpicado on 23/01/16.
 */
public class HttpUtils {

    /**
     * Return the locale inside the {@link HttpServletRequest}.
     * @param request
     * @return
     */
    public static Locale getLocale(final HttpServletRequest request) {
        return RequestContextUtils.getLocale(request);
    }
}
