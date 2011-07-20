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
package org.encuestame.core.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Helper to JSON.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public class JSONUtils {


    public static String convertPojoToJSON(final Object object) throws IOException{
        final ObjectMapper m = new ObjectMapper();
        final JsonFactory jf = new JsonFactory();
        final StringWriter sw = new StringWriter();
        final JsonGenerator jg = jf.createJsonGenerator(sw);
        jg.useDefaultPrettyPrinter();
        m.writeValue(jg, object);
        return sw.toString();
    }

}
