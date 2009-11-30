/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
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
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.commons.MessageSourceFactoryBean;

/**
 * Service Manager.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class ServiceManager extends Service implements IServiceManager {

    public Log log = LogFactory.getLog(this.getClass());

    public IApplicationServices applicationServices;

    /**
     * Setter of {@link ApplicationServices}.
     * @param applicationServices the applicationServices to set
     */
    public void setApplicationServices(IApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    /**
     * Getter of {@link ApplicationServices}
     * @return the applicationServices
     */
    public IApplicationServices getApplicationServices() {
        return applicationServices;
    }
}
