package org.encuestame.utils.enums;

public enum CommentStatus {
	 /** Approve Comments. **/
    APPROVE,

    /** Moderate Comments. **/
    MODERATE,

    PUBLISHED,

    SPAM,

    ALL,

    CommentStatus(){};

    /**
     * To String.
     */
    public String toString() {
        String comment = "";
        if (this == APPROVE) { comment = "APPROVE"; }
        else if (this == MODERATE) { comment = "MODERATE"; }
        else if (this == PUBLISHED) { comment = "PUBLISHED"; }
        else if (this == SPAM) { comment = "SPAM"; }
        else if (this == ALL) { comment = "ALL"; }
        return comment;
    }

    /**
     * Get comment option enum.
     * @param option
     * @return
     */
    public static CommentStatus getCommentStatus(final String status) {
        if (null == status) { return null; }
        else if (status.equalsIgnoreCase("APPROVE")) { return APPROVE; }
        else if (status.equalsIgnoreCase("MODERATE")) { return MODERATE; }
        else if (status.equalsIgnoreCase("PUBLISHED")) { return PUBLISHED; }
        else if (status.equalsIgnoreCase("SPAM")) { return SPAM; }
        else if (status.equalsIgnoreCase("ALL")) { return ALL; }
        else return null;
    }
}
