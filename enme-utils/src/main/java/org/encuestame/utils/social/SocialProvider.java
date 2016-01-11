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
package org.encuestame.utils.social;

/**
 * Social Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 27, 2011
 */
public enum SocialProvider {
    /**
     * Twitter provider.
     */
    TWITTER("TWITTER"),
    /**
     * Facebook provider.
     */
    FACEBOOK("FACEBOOK"),
    
    /**
     * Identica provider.
     */
    @Deprecated
    IDENTICA("IDENTICA"),
    
    /**
     * The new name of identi.ca
     */
    PUMPIO("PUMPIO"),

    /**
     * Linked In provider.
     */
    LINKEDIN("LINKEDIN"),
    /**
     * Google Buzz provider.
     */
    @Deprecated
    GOOGLE_BUZZ("GOOGLE_BUZZ"),

    /** Google +. **/
    GOOGLE_PLUS("GOOGLE_PLUS"),
    /**
     * Yahoo provider.
     */
    YAHOO("YAHOO"),
    /**
     * My Space.
     */
    @Deprecated
    MYSPACE("MYSPACE"),
    //TODO: In the future we can add more API's Tumblr, Plurk, Jaiku.

    YOUTUBE("YOUTUBE"),
    /**
     * Use full
     */
    PICASA("PICASA"),

    /**
     * Google orkut social.
     */
    ORKUT("ORKUT"),

    /**
     * To access to google contact support.
     */
    GOOGLE_CONTACTS("GOOGLE_CONTACTS"),

    /**
     * Blog support to publish on blogger accounts.
     */
    BLOGGER("BLOGGER"),
    
    /**
     * 
     */
    PLURK("PLURK"),
    
    /**
     * 
     */
    TUMBLR("TUMBLR"),
    
    /**
     * All social providers.
     */
    ALL("ALL");

    private String socialProviderAsString;

    /**
     * Constructor.
     */
    SocialProvider(String optionAsString){
        this.socialProviderAsString = optionAsString;
    }


    /**
     * Some cases its necessary return the same provider name because the OAuth2 handler is the same.
     * Google manage all API keys with the same callback url on the same console.
     * http://wiki.encuestame.org/display/DOC/Set+Up+or+Customize+Google+OAuth+credentials+on+Encuestame
     * @return social callback string.
     */
    public String getBackUrlProviderName() {
        String provider = "";
        if (this == TWITTER) { provider = "TWITTER"; }
        else if (this == FACEBOOK) { provider = "FACEBOOK"; }
        else if (this == PLURK) { provider = "PLURK"; }
        else if (this == TUMBLR) { provider = "TUMBLR"; }
        else if (this == IDENTICA) { provider = "IDENTICA"; }
        else if (this == PUMPIO) { provider = "PUMPIO"; }
        else if (this == LINKEDIN) { provider = "LINKEDIN"; }
        else if (this == GOOGLE_PLUS) { provider = "GOOGLEPLUS"; }
        else if (this == GOOGLE_BUZZ) { provider = "GOOGLEBUZZ"; }
        else if (this == YAHOO) { provider = "YAHOO"; }
        else if (this == MYSPACE) { provider = "MYSPACE"; }
        return provider.toLowerCase();
    }


    /**
     * Provide OAuth protocol.
     * @param provider {@link SocialProvider}.
     * @return
     */
    public static TypeAuth getTypeAuth(final SocialProvider provider) {
        if(provider.equals(TWITTER)
	        || provider.equals(IDENTICA)
	        || provider.equals(ALL)
	        || provider.equals(TUMBLR)
	        || provider.equals(PLURK)
	        || provider.equals(LINKEDIN)
	        || provider.equals(PUMPIO)
	        || provider.equals(MYSPACE)
	        || provider.equals(YAHOO)){
            return TypeAuth.OAUTH1;
        } else if (
        		provider.equals(GOOGLE_PLUS) ||
        		provider.equals(GOOGLE_BUZZ) ||
        		provider.equals(FACEBOOK)){
            return TypeAuth.OAUTH2;
        } else {
            return null;
        }
    }
}
