/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.controller.json.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.geo.ItemGeoLocationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HashTag Json Controller.
 * @author Morales, Diana Paola juanATencuestame.org
 * @since July 20, 2010 12:43:46 PM
 */
@Controller
public class GeoLocationJsonController extends AbstractJsonControllerV1 {

     /**
     * Log.
     */
     private static Log log = LogFactory.getLog(GeoLocationJsonController.class);

             /**
              * @api {get} /api/common/geolocation/search/{typeOfSearch}.json Get Items by Geolocation
              * @apiVersion 1.0.0
              * @apiName GetGeoItemsByType
              * @apiGroup Geolocation
              * @apiPermission user
              * @apiDescription A tag cloud of hashtags with the top hashtags in post mentioning ordered by frecuency.
              * @apiParam {Number} range
              * @apiParam {Number} maxItem
              * @apiParam {String} type
              * @apiParam {Number} longitude
              * @apiParam {Number} latitude
              * @apiParam {Number} period
              * @apiParam {String} tagName
              * @apiParam {String} typeOfSearch
              * @apiSampleRequest http://www.encuestame.org/demo/api/common/geolocation/search/tweetpoll.json
              * @apiSuccessExample
              * @apiSuccess {Object} success
              * @apiSuccess {String} error
              */

    @RequestMapping(value = "/api/common/geolocation/search/{typeOfSearch}.json", method = RequestMethod.GET)
    public @ResponseBody ModelMap getItemsbyGeoLocationRange(
            @RequestParam(value = "range", required = true) Double range,
            @RequestParam(value = "maxItem", required = true) Integer maxItem,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "longitude", required = true) Double longitude,
            @RequestParam(value = "latitude", required = true) Double latitude,
            @RequestParam(value = "period", required = true) String period,
            @RequestParam(value = "tag", required = false) String tagName,
            @PathVariable final String typeOfSearch,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {

        try {
            log.debug("Range " + range);
            log.debug("Maximum " + maxItem);

            SearchPeriods periodValue = (period.isEmpty() ? SearchPeriods.ALLTIME
                    : SearchPeriods.getPeriodString(period));
            TypeSearchResult typeValue = (type.isEmpty() ? TypeSearchResult.ALL
                    : TypeSearchResult.getTypeSearchResult(type));
            TypeSearchResult searchType = TypeSearchResult
                    .getTypeSearchResult(typeOfSearch);
            List<ItemGeoLocationBean> itemsByLocation = new ArrayList<ItemGeoLocationBean>();
            final Map<String, Object> jsonResponse = new HashMap<String, Object>();
            if (searchType.equals(TypeSearchResult.ALL)) {
                itemsByLocation = getLocationService().retrieveItemsByGeo(
                        range, maxItem, typeValue, longitude, latitude,
                        periodValue);
                log.debug("Items by geo location " + itemsByLocation.size());
                jsonResponse.put("itemsByGeo", itemsByLocation);
            } else if (searchType.equals(TypeSearchResult.HASHTAG)) {
                if (tagName == null) {
                    throw new EnMeSearchException("search params required.");
                } else {

                    itemsByLocation = getLocationService()
                            .retreiveHashTagUsebyGeoLo(range, maxItem,
                                    typeValue, longitude, latitude, tagName,
                                    periodValue);
                    log.debug("Hashtag use by geo location "
                            + itemsByLocation.size());
                    jsonResponse.put("hashtagUsebyGeo", itemsByLocation);
                }

            } else if (searchType.equals(TypeSearchResult.SOCIALNETWORK)) {
                itemsByLocation = getLocationService()
                        .retrieveSocialNetworksPublicationsbyGeoLocation(range,
                                maxItem, typeValue, longitude, latitude,
                                periodValue);
                log.debug("Social publications  by geo location "
                        + itemsByLocation.size());
                jsonResponse.put("socialGeo", itemsByLocation);
            }
            setItemResponse(jsonResponse);

        } catch (Exception e) {
            log.error(e);
            setError(e.getMessage(), response);
        }
        return returnData();
    }

}
