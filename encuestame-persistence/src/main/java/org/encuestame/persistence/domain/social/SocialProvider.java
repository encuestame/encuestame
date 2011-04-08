package org.encuestame.persistence.domain.social;

/**
 * Social Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 27, 2011
 */
public enum SocialProvider {
    /**
     * Twitter provider.
     */
    TWITTER,
    /**
     * Facebook provider.
     */
    FACEBOOK,
    /**
     * Identica provider.
     */
    IDENTICA,
    /**
     * TripIt provider.
     */
    TRIP_IT,
    /**
     * Linked In provider.
     */
    LINKEDIN,
    /**
     * Google Buzz provider.
     */
    BUZZ,
    /**
     * Yahoo provider.
     */
    YAHOO,

    /**
     * Constructor.
     */
    SocialProvider(){
        //Constructor.
    };

    /**
     * To String.
     */
    public String toString() {
        String provider = "";
        if (this == TWITTER) { provider = "TWITTER"; }
        else if (this == FACEBOOK) { provider = "FACEBOOK"; }
        else if (this == IDENTICA) { provider = "IDENTICA"; }
        else if (this == TRIP_IT) { provider = "TRIP_IT"; }
        else if (this == LINKEDIN) { provider = "LINKEDIN"; }
        else if (this == BUZZ) { provider = "BUZZ"; }
        else if (this == YAHOO) { provider = "YAHOO"; }
        return provider;
    }

    /**
     * Get Provider by String.
     * @param permission period
     * @return provider enum
     */
    public static SocialProvider getProvider(final String permission) {
        if (null == permission) { return null; }
        else if (permission.equalsIgnoreCase("TWITTER")) { return TWITTER; }
        else if (permission.equalsIgnoreCase("FACEBOOK")) { return FACEBOOK; }
        else if (permission.equalsIgnoreCase("IDENTICA")) { return IDENTICA; }
        else if (permission.equalsIgnoreCase("TRIPIT")) { return TRIP_IT; }
        else if (permission.equalsIgnoreCase("LINKEDIN")) { return LINKEDIN; }
        else if (permission.equalsIgnoreCase("BUZZ")) { return BUZZ; }
        else if (permission.equalsIgnoreCase("YAHOO")) { return YAHOO; }
        else return null;
    }
}
