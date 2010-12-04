/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.JCEMac.MD5;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.util.MD5Utils;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Url Short Address Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 29, 2010 12:25:00 AM
 * @version $Id:$
 */
@Controller
public class UrlShortAddressController extends AbstractJsonController{

    /**
     * Domain.
     */
     @Value("${application.domain}") private String appDomainSetted;

     /**
      * Tweet Path Vote.
      */
     @Value("${answers.tweetPathVote}") private String tweetPathVote;

     /**
      * Short Url.
      * @param typeUrlShorter
      * @param url
      * @param request
      * @param response
      * @return
      * @throws JsonGenerationException
      * @throws JsonMappingException
      * @throws IOException
      */
     @PreAuthorize("hasRole('ENCUESTAME_USER')")
        @RequestMapping(value = "/api/common/url/{typeUrlShorter}.json", method = RequestMethod.GET)
        public ModelMap get(
                @PathVariable String typeUrlShorter, //tinyUrl
                @RequestParam(value = "url", required = false) String url,
                HttpServletRequest request,
                HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            try {
                if("tinyUrl".equals(typeUrlShorter)){
                    //tweetpoll service.
                    final StringBuilder voterUrl = new StringBuilder(url);
                    final String tinyUrl = getTweetPollService().getTwitterService().getTinyUrl(voterUrl.toString());
                    jsonResponse.put("url", tinyUrl);
                } else {
                    log.warn("No exist more url shorters");
                }
                setItemResponse(jsonResponse);
            } catch (Exception e) {
                log.error(e);
                setError(e.getMessage(), response);
            }
            return returnData();
     }

     /**
      * Get Url Domain.
      * @return
      */
     public String getUrlDomain(final HttpServletRequest request, final Boolean realDomain){
         final StringBuilder builder = new StringBuilder();
         if(realDomain){
             String header = request.getHeader("X-Forwarded-Host");
             log.debug("Host header  "+header);
             if(header != null) {
                     // we are only interested in the first header entry
                     header = new StringTokenizer(header,",").nextToken().trim();
             }
             if(header == null) {
                     header = request.getHeader("Host");
             }
             builder.append(header);
             log.debug("Host "+builder.toString());
         } else {
             builder.append(getAppDomainSetted());
         }
         return builder.toString();
     }

    /**
     * @return the appDomainSetted
     */
    public String getAppDomainSetted() {
        return appDomainSetted;
    }

    /**
     * @param appDomainSetted the appDomainSetted to set
     */
    public void setAppDomainSetted(final String appDomainSetted) {
        this.appDomainSetted = appDomainSetted;
    }

    /**
     * @return the tweetPathVote
     */
    public String getTweetPathVote() {
        return tweetPathVote;
    }

    /**
     * @param tweetPathVote the tweetPathVote to set
     */
    public void setTweetPathVote(final String tweetPathVote) {
        this.tweetPathVote = tweetPathVote;
    }
}
