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
package org.encuestame.utils;

import org.apache.log4j.Logger;

/**
 * Short url provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 15, 2011
 */
public enum ShortUrlProvider {

    GOOGL,
    TINYURL,
    BITLY,

    ShortTypeUrl(){};

    /**
     * Log.
     */
    private static Logger log = Logger.getLogger(ShortUrlProvider.class);

    /**
     * Get short url provider by string.
     * @param provider provider
     * @return
     */
    public static ShortUrlProvider get(final String provider) {
        log.debug("ShortUrlProvider:{ "+provider);
        if (null == provider) { return BITLY; }
        else if (provider.equalsIgnoreCase("googl")) { return GOOGL; }
        else if (provider.equalsIgnoreCase("tinyurl")) { return TINYURL; }
        else if (provider.equalsIgnoreCase("bitly")) { return BITLY; }
        else return BITLY;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        String shortUrlProvider = "googl";
        if (this == GOOGL) { shortUrlProvider = "googl"; }
        else if (this == TINYURL) { shortUrlProvider = "tinyurl"; }
        else if (this == BITLY) { shortUrlProvider = "bitly"; }
        return shortUrlProvider;
    }
}
