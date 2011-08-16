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
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

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
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public static String convertObjectToJsonString(Object object) throws JsonGenerationException, JsonMappingException, IOException{
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
    public static Object convertJsonToObject(final String json, final Class clazz) throws JsonParseException, JsonMappingException, IOException{
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
    public static Map<String, Object> convertJsonToObject(final String json) throws JsonParseException, JsonMappingException, IOException{
        final JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    }
}
