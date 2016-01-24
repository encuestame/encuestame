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
package org.encuestame.test.business.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.encuestame.core.social.profile.BuzzProfile;
import org.encuestame.utils.categories.test.InternetTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 26, 2011
 */
@Category(InternetTest.class)
public class JSONUtilTestCase extends TestCase {

    @Test
    public void testJson() throws JsonParseException, JsonMappingException, IOException{
        String json = "{\"data\":{\"id\":null,\"displayName\":null,\"kind\":\"kined\",\"aboutMe\":\"abbout me\",\"profileUrl\":null,\"emails\":[],\"url\":[],\"photos\":null,\"organizations\":[]}}";
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        BuzzProfile bz = new BuzzProfile();
//        bz.getData().setAboutMe("abbout me");
//        bz.getData().setEmails(new ArrayList<BuzzProfile.Email>());
//        bz.getData().setKind("kined");
//        bz.getData().setUrl(new ArrayList<BuzzProfile.Urls>());
//        bz.getData().setOrganizations(new ArrayList<BuzzProfile.Organizations>());

        //System.out.println(mapper.writeValueAsString(bz));
       final Map d = mapper.readValue(json, Map.class);
       //System.out.println(d);


       TypeReference<HashMap<String,Object>> typeRef
             = new TypeReference<
                    HashMap<String,Object>
                  >() {};
       HashMap<String,Object> o
            = mapper.readValue(json, typeRef);
       //System.out.println("Got " + o);
    }
}
