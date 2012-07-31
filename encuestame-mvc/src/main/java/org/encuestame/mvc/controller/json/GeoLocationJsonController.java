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
package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.utils.ValidationUtils;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.geo.ItemGeoLocationBean;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HashTag Json Controller.
 * @author Morales, Diana Paola juanATencuestame.org
 * @since July 20, 2010 12:43:46 PM
 */
@Controller
public class GeoLocationJsonController extends AbstractJsonController {

	 /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 
     * @param range
     * @param maxItem
     * @param type
     * @param longitude
     * @param latitude
     * @param period
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/api/common/geolocation/items.json", method = RequestMethod.GET)
    public ModelMap getItemsbyGeoLocationRange(
            @RequestParam(value = "range", required = true) Double range,
            @RequestParam(value = "maxItem", required = true) Integer maxItem,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "longitude", required = true) Double longitude,
            @RequestParam(value = "latitude", required = true) Double latitude,
            @RequestParam(value = "period", required = true) String period,
            
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
    	
            try {
                log.debug("Range "+range);
                log.debug("Maximum "+maxItem);
                
                SearchPeriods periodValue = (period.isEmpty() ? SearchPeriods.ALLTIME : SearchPeriods.getPeriodString(period));
                TypeSearchResult typeValue = (type.isEmpty() ? TypeSearchResult.ALL : TypeSearchResult.getTypeSearchResult(type));
                
                final Map<String, Object> jsonResponse = new HashMap<String, Object>();
                final List<ItemGeoLocationBean> itemsByLocation = getLocationService().retrieveItemsByGeo(range, maxItem, typeValue, longitude, latitude, periodValue);
                log.debug("Items by geo location "+itemsByLocation.size());
                jsonResponse.put("itemsByGeo", itemsByLocation);
                setItemResponse(jsonResponse);  
             
            } catch (Exception e) {
                 log.error(e);
                 setError(e.getMessage(), response);
            }
            return returnData();
        }

    
	/**
	 *  
	 * @param range
	 * @param maxItem
	 * @param type
	 * @param longitude
	 * @param latitude
	 * @param period
	 * @param tagName
	 * @param request
	 * @param response
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/common/geolocation/hashtag.json", method = RequestMethod.GET)
	public ModelMap retrieveHashTagUsebyGeoLocation(
			@RequestParam(value = "range", required = true) Double range,
			@RequestParam(value = "maxItem", required = true) Integer maxItem,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "longitude", required = true) Double longitude,
			@RequestParam(value = "latitude", required = true) Double latitude,
			@RequestParam(value = "period", required = true) String period,
			@RequestParam(value = "tag", required = true) String tagName,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {

		try {
			log.debug("Range " + range);
			log.debug("Maximum " + maxItem);

			SearchPeriods periodValue = (period.isEmpty() ? SearchPeriods.ALLTIME
					: SearchPeriods.getPeriodString(period));
			TypeSearchResult typeValue = (type.isEmpty() ? TypeSearchResult.ALL
					: TypeSearchResult.getTypeSearchResult(type));

			final Map<String, Object> jsonResponse = new HashMap<String, Object>();
			final List<ItemGeoLocationBean> hashtagUsebyGeoLocation = getLocationService()
					.retreiveHashTagUsebyGeoLo(range, maxItem, typeValue,
							longitude, latitude, tagName, periodValue);
			log.debug("Hashtag use by geo location " + hashtagUsebyGeoLocation.size());
			jsonResponse.put("hashtagUsebyGeo", hashtagUsebyGeoLocation);
			setItemResponse(jsonResponse);

		} catch (Exception e) {
			log.error(e);
			setError(e.getMessage(), response);
		}
		return returnData();
	}
 
	/**
	 * 
	 * @param range
	 * @param maxItem
	 * @param type
	 * @param longitude
	 * @param latitude
	 * @param period
	 * @param request
	 * @param response
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/common/geolocation/social.json", method = RequestMethod.GET)
	public ModelMap retrieveSocialNetworksPublicationsbyGeoLocation(
			@RequestParam(value = "range", required = true) Double range,
			@RequestParam(value = "maxItem", required = true) Integer maxItem,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "longitude", required = true) Double longitude,
			@RequestParam(value = "latitude", required = true) Double latitude,
			@RequestParam(value = "period", required = true) String period, 
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {

		try {
			log.debug("Range " + range);
			log.debug("Maximum " + maxItem);

			SearchPeriods periodValue = (period.isEmpty() ? SearchPeriods.ALLTIME
					: SearchPeriods.getPeriodString(period));
			TypeSearchResult typeValue = (type.isEmpty() ? TypeSearchResult.ALL
					: TypeSearchResult.getTypeSearchResult(type));

			final Map<String, Object> jsonResponse = new HashMap<String, Object>();
			final List<ItemGeoLocationBean> socialPublicationsbyGeoLocation = getLocationService()
					.retrieveSocialNetworksPublicationsbyGeoLocation(range,
							maxItem, typeValue, longitude, latitude,
							periodValue);
			log.debug("Social publications  by geo location "
					+ socialPublicationsbyGeoLocation.size());
			jsonResponse.put("socialGeo", socialPublicationsbyGeoLocation);
			setItemResponse(jsonResponse);

		} catch (Exception e) {
			log.error(e);
			setError(e.getMessage(), response);
		}
		return returnData();
	}
	
}
