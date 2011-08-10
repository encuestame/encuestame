package org.encuestame.persistence.domain.social;

import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;

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
     * Linked In provider.
     */
    LINKEDIN,
    /**
     * Google Buzz provider.
     */
    GOOGLE_BUZZ,

    /** Google +. **/
    GOOGLE_PLUS,
    /**
     * Yahoo provider.
     */
    YAHOO,
    /**
     * My Space.
     */
    MYSPACE,
    //TODO: In the future we can add more API's Tumblr, Plurk, Jaiku.

    YOUTUBE,
    /**
     * Use full
     */
    PICASA,

    /**
     * Google orkut social.
     */
    ORKUT,

    /**
     * To access to google contact support.
     */
    GOOGLE_CONTACTS,

    /**
     * Blog support to publish on blogger accounts.
     */
    BLOGGER,

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
        else if (this == LINKEDIN) { provider = "LINKEDIN"; }
        else if (this == GOOGLE_BUZZ) { provider = "GOOGLEBUZZ"; }
        else if (this == YAHOO) { provider = "YAHOO"; }
        else if (this == MYSPACE) { provider = "MYSPACE"; }
        return provider;
    }

    /**
     * Some cases it´s necessary return the same provider name because the OAuth2 handler is the same.
     * Google manage all API keys with the same callback url on the same console.
     * http://wiki.encuestame.org/display/DOC/Set+Up+or+Customize+Google+OAuth+credentials+on+Encuestame
     * @return social callback string.
     */
    public String getBackUrlProviderName() {
        String provider = "";
        if (this == TWITTER) { provider = "TWITTER"; }
        else if (this == FACEBOOK) { provider = "FACEBOOK"; }
        else if (this == IDENTICA) { provider = "IDENTICA"; }
        else if (this == LINKEDIN) { provider = "LINKEDIN"; }
        else if (this == GOOGLE_BUZZ) { provider = "GOOGLE"; }
        else if (this == YAHOO) { provider = "YAHOO"; }
        else if (this == MYSPACE) { provider = "MYSPACE"; }
        return provider.toLowerCase();
    }

    /**
     * Get Provider by String.
     * @param socialProvider period
     * @return provider enum
     */
    public static SocialProvider getProvider(final String socialProvider) {
        if (null == socialProvider) { return null; }
        else if (socialProvider.equalsIgnoreCase("TWITTER")) { return TWITTER; }
        else if (socialProvider.equalsIgnoreCase("FACEBOOK")) { return FACEBOOK; }
        else if (socialProvider.equalsIgnoreCase("IDENTICA")) { return IDENTICA; }
        else if (socialProvider.equalsIgnoreCase("LINKEDIN")) { return LINKEDIN; }
        else if (socialProvider.equalsIgnoreCase("GOOGLEBUZZ")) { return GOOGLE_BUZZ; }
        else if (socialProvider.equalsIgnoreCase("GOOGLE_BUZZ")) { return GOOGLE_BUZZ; }
        else if (socialProvider.equalsIgnoreCase("YAHOO")) { return YAHOO; }
        else if (socialProvider.equalsIgnoreCase("MYSPACE")) { return MYSPACE; }
        else return null;
    }

    /**
     * Provide OAuth protocol.
     * @param provider {@link SocialProvider}.
     * @return
     */
    public static TypeAuth getTypeAuth(final SocialProvider provider){
        if(provider.equals(TWITTER)
                || provider.equals(IDENTICA)
                || provider.equals(LINKEDIN)
                || provider.equals(MYSPACE)
                || provider.equals(YAHOO)){
            return TypeAuth.OAUTH1;
        } else if (provider.equals(GOOGLE_BUZZ) || provider.equals(FACEBOOK)){
            return TypeAuth.OAUTH2;
        } else {
            return null;
        }
    }
}
