package org.encuestame.core.util;

/**
 * User Account profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 5, 2011 11:18:56 AM
 */
public enum Profile{
    EMAIL,
    USERNAME,
    LANGUAGE,
    PRIVATE,
    REAL_NAME;

    Profile(){
    }

    /**
     * Find Profile.
     * @param value
     * @return
     */
    public static Profile findProfile(final String value) {
        Profile result = null;
        if (null != value) {
           if ("EMAIL".equalsIgnoreCase(value)) { result = EMAIL; }
           if ("USERNAME".equalsIgnoreCase(value)) { result = USERNAME; }
           if ("LANGUAGE".equalsIgnoreCase(value)) { result = LANGUAGE; }
           if ("PRIVATE".equalsIgnoreCase(value)) { result = PRIVATE; }
           if ("REAL_NAME".equalsIgnoreCase(value)) { result = REAL_NAME; }
        }
        return result;
    }



    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == EMAIL) { type = "email"; }
        else if (this == USERNAME) { type = "username"; }
        else if (this == REAL_NAME) { type = "completeName"; }
        else if (this == LANGUAGE) { type = "language"; }
        else if (this == PRIVATE) { type = "private"; }
        return type;
    }
}
