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
package org.encuestame.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * Json date deserializer.
 * @author Picado, Juan juanATencuestame.org
 * @since May 7, 2011
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(JsonDateDeserializer.class);

    public JsonDateDeserializer() {
    }

    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        try {
            log.debug("json parse:{ "+jp.getText());
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss Z yyyy");
            return sdf.parse(jp.getText());
        } catch (ParseException e) {
            throw new IOException("Error parsing Twitter date format: "
                    + e.toString());
        }
    }

}
