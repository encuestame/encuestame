/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, writes to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.exception;
 /**
 * Encuestame Exception.
 * @author Picado, Juan juan@encuestame.org
 * @since May 07, 2009
 */
public class EnMeExpcetion extends Exception{

    /** serial. */
    private static final long serialVersionUID = 7631058192250904935L;

    /**
     * Constructor.
     */
    public EnMeExpcetion() {
        super();

    }
    /**
     * Exception.
     * @param message message
     * @param cause cause
     */
    public EnMeExpcetion(final String message, final Throwable cause) {
        super(message, cause);

    }
    /**
     * Exception.
     * @param message message
     */
    public EnMeExpcetion(final String message) {
        super(message);

    }

    /**
     * Exception.
     * @param cause cause
     */
    public EnMeExpcetion(final Throwable cause) {
        super(cause);

    }
}
