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
package org.encuestame.business.service.social.api;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.social.BuzzAPIOperations;
import org.encuestame.core.social.Data;
import org.encuestame.core.social.oauth2.ProtectedResourceClientFactory;
import org.jfree.util.Log;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Google Buzz
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 19, 2011
 */
public class BuzzAPITemplate extends AbstractSocialAPISupport implements BuzzAPIOperations{

    /**
     * Google Key.
     */
    private String GOOGLE_KEY;

    /**
     *
     * @param accessToken
     */
    public BuzzAPITemplate(final String accessToken, final String googleKey) {
         setRestTemplate(ProtectedResourceClientFactory.draft10(accessToken));
          this.GOOGLE_KEY = googleKey;
    }

    public String getProfile(){
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$RESTFUL PROFILE$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        String profile = "https://www.googleapis.com/buzz/v1/people/@me/@self?alt=json";
        Object profileMap = getRestTemplate().getForObject(profile, Object.class);
        System.out.println(profileMap);
        return "";
    }

    public String updateStatus(final String status) throws IOException{
         final Data jsonData = new Data();
         jsonData.getData().getObject().setType("note");
         jsonData.getData().getObject().setComment(status);
         ObjectMapper m = new ObjectMapper();
         JsonFactory jf = new JsonFactory();
         StringWriter sw = new StringWriter();
         org.codehaus.jackson.JsonGenerator jg = jf.createJsonGenerator(sw);
         jg.useDefaultPrettyPrinter();
         m.writeValue(jg, jsonData);
        String PROFILE_URL = "https://www.googleapis.com/buzz/v1/activities/@me/@self?key={key}&alt=json";
        //getRestTemplate().post
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<String, String>();
        //System.out.println(getRestTemplate().postForEntity(PROFILE_URL, requestData, String.class));
        //HttpEntity<?> requestEntity = new HttpEntity<String>(buildBaseHeaders());
        String h = "<entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:activity=\"http://activitystrea.ms/spec/1.0\"><activity:object><activity:object-type>http://activitystrea.ms/schema/1.0/note</activity:object-type> <content type=\"html\">hji hi hi hi hihih!</content></activity:object></entry>";

        Map response = getRestTemplate().postForObject(PROFILE_URL, jsonData, Map.class, GOOGLE_KEY);
        //URI d = getRestTemplate().postForLocation(PROFILE_URL, h);
        System.out.println(response);
        //System.out.println(response.getHeaders());
        //System.out.println(response.getStatusCode());
        //Log.debug("mappp-------"+response);
        return "google";
    }

    public void likeActivity(String id){
        final String URL = "https://www.googleapis.com/buzz/v1/activities/@me/@liked/{activityId}?key="+GOOGLE_KEY;
         getRestTemplate().put(URL, Map.class, id);
    }

    public String getActivities(){
        final String x = "https://www.googleapis.com/buzz/v1/activities/@me/@public?alt=json";
        Object f = getRestTemplate().getForObject(x, Object.class);
        System.out.println(f);
        return "google";
    }

    private MultiValueMap<String, String> buildBaseHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        return headers;
    }
}
