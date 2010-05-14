/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;
import org.encuestame.utils.web.UnitGroupBean;

/**
 * {@link ListShuttleGroupConverter} Converter.
 * @author Picado, Juan juanATencuestame.org
 * @since
 * @version $Id:$
 */
public class ListShuttleGroupConverter implements Converter {

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Object.
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        log.info("converter value "+value);
        int index = value.indexOf(':');
        return new UnitGroupBean(Long.valueOf(value.substring(index + 1)), value.substring(0, index), value.substring(index + 1));
    }

    /**
     * As String.
     */
    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        log.info("getAsString value "+ value.toString());
        return value.toString();
    }

}
