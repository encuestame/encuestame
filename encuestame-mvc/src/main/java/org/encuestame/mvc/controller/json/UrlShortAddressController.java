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
package org.encuestame.mvc.controller.json;

import org.apache.log4j.Logger;
import org.encuestame.mvc.controller.AbstractJsonController;

/**
 * Url Short Address Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Nov 29, 2010 12:25:00 AM
 * @version $Id:$
 */
//@Controller
@Deprecated
public class UrlShortAddressController extends AbstractJsonController{

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

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
//     @PreAuthorize("hasRole('ENCUESTAME_USER')")
//        @RequestMapping(value = "/api/common/url/{typeUrlShorter}.json", method = RequestMethod.GET)
//        public ModelMap get(
//                @PathVariable String typeUrlShorter, //tinyUrl
//                @RequestParam(value = "url", required = false) String url,
//                HttpServletRequest request,
//                HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
//             final Map<String, Object> jsonResponse = new HashMap<String, Object>();
//            try {
//                if("tinyUrl".equals(typeUrlShorter)){
//                    //tweetpoll service.
//                    final StringBuilder voterUrl = new StringBuilder(url);
//                    final String tinyUrl = getTweetPollService().getTwitterService().getTinyUrl(voterUrl.toString());
//                    jsonResponse.put("url", tinyUrl);
//                } else {
//                    log.warn("No exist more url shorters");
//                }
//                setItemResponse(jsonResponse);
//            } catch (Exception e) {
//                log.error(e);
//                setError(e.getMessage(), response);
//            }
//            return returnData();
//     }
}
