package org.encuestame.utils.enums;

public enum RequestSourceType {
	 /**
     * Represent last 24 hours.
     */
	EMBEDDED("EMBEDDED"),
    /**
     * Represent 7 last days
     */
    NORMAL("NORMAL");

    private String optionAsString;


    /**
     * Constructor.
     */
    RequestSourceType(final String optionAsString) {
        this.optionAsString = optionAsString;
        //Empty constructor.
    }

    /**
     * To String.
     */
    public String toString() {
        String period = "null";
        //If last 24 hours
        if (this == EMBEDDED) { period = "embedded"; }
        //If last 7 days
        else if (this == NORMAL) { period = "normal"; }
        //If last 30 days
        return period;
    }

    /**
 
    /**
     * Get Period by String.
     * @param period period
     * @return
     */
    public static RequestSourceType getSource(final String period) {
        if (null == period) { return EMBEDDED; }
        else if (period.equalsIgnoreCase("embedded")) { return EMBEDDED; }
        else if (period.equalsIgnoreCase("normal")) { return NORMAL; }
        else return NORMAL;
    }
}