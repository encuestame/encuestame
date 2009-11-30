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

import org.encuestame.web.beans.commons.MessageSourceFactoryBean;

/**
 * Class Description.
 * @author Picado, Juan juan@encuestame.org
 * @since 29/11/2009 21:47:14
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public interface IService {

    public MessageSourceFactoryBean getMessageSource();
    public void setMessageSource(MessageSourceFactoryBean messageSource);
    public String getMessageProperties(String propertieId);
    public IDataSource getDataEnMeSource();
    public void setDataEnMeSource(final IDataSource dataSource);

}
