package org.encuestame.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.commons.MessageSourceFactoryBean;

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
 *
 * Id: MasterService.java Date: 22/05/2009 1:02:45
 *
 * @author juanpicado package: org.encuestame.core.service
 * @version 1.0
 */
public class Service {

    private MessageSourceFactoryBean messageSource;
    protected Log log = LogFactory.getLog(this.getClass());

    public Service() {
        // TODO Auto-generated constructor stub
    }

    protected MessageSourceFactoryBean getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        this.messageSource = messageSource;
    }

    /**
     *
     * @param i_propertyId
     * @return
     */
    public String getMessageProperties(String i_propertyId) {
        return getMessageSource() == null ? i_propertyId : getMessageSource()
                .getMessage(i_propertyId, null, null);
    }

}
