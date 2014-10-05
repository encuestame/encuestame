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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;


/**
 * JSON utilities..
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 23, 2011
 */
public class JSONUtils {

    /**
     *
     * @param object
     * @return
     * @throws IOException
     * @throws com.fasterxml.jackson.databind.JsonMappingException
     * @throws com.fasterxml.jackson.core.JsonGenerationException
     */
    public static String convertObjectToJsonString(Object object) throws IOException{
         final JsonFactory factory = new JsonFactory();
         final ObjectMapper mapper = new ObjectMapper(factory);
         return mapper.writeValueAsString(object);
    }

    /**
     *
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Object convertJsonToObject(final String json, final Class clazz) throws IOException{
        final JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        return mapper.readValue(json, clazz);
    }

    /**
     *
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Map<String, Object> convertJsonToObject(final String json) throws IOException{
        final JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    }
}
