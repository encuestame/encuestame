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
package org.encuestame.mvc.controller.json.statistics;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HashTag statistics.
 * 
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 05, 2012
 */
public class HashTagJsonStats extends AbstractJsonController {

	/** Log. **/
	private Logger log = Logger.getLogger(this.getClass());

	/** **/
	private IFrontEndService service = getFrontService();

	/** **/
	private Integer INIT_RESULTS = 0;

	/**
	 * Get hashTags button stats.
	 * 
	 * @param tagName
	 * @param filter
	 * @param limit
	 * @param request
	 * @param response
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/common/hashtags/stats/button.json", method = RequestMethod.GET)
	public ModelMap getHashTagButtonStats(
			@RequestParam(value = "tagName", required = true) String tagName,
			@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "limit", required = false) Integer limit,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			final Map<String, Object> jsonResponse = new HashMap<String, Object>();
			final HashTagStatsBean tagStatsBean = new HashTagStatsBean();
			tagStatsBean.setTotalHits(service.getTotalUsageByHashTag(tagName,
					this.INIT_RESULTS, limit,
					TypeSearchResult.getTypeSearchResult(filter)));
			tagStatsBean.setTotalUsageBySocialNetwork(service
					.getSocialNetworkUseByHashTag(tagName, this.INIT_RESULTS,
							limit));
			tagStatsBean.setUsageByItem(service.getHashTagHitsbyName(tagName,
					TypeSearchResult.getTypeSearchResult(filter)));

			jsonResponse.put("hashTagButtonStats", tagStatsBean);
			setItemResponse(jsonResponse);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			setError(e.getMessage(), response);
		}
		return returnData();
	}

	 /**
     * HashTag stats bean.
     * @author Morales, Diana Paola paolaATencuestame.org
     * @since January 10, 2012
     */
	@JsonIgnoreProperties(ignoreUnknown = true)
	private class HashTagStatsBean implements Serializable {

		/** Serial **/
		private static final long serialVersionUID = -2620835370999916919L;

		/** Total hashTags usage in Poll, Survey and TweetPoll. **/
		@JsonProperty(value = "id")
		private Long usageByItem;

		/** Total HashTags usage in social network profiles. **/
		@JsonProperty(value = "id")
		private Long totalUsageBySocialNetwork;

		/** Total hits. **/
		@JsonProperty(value = "id")
		private Long totalHits;

		/** **/
		@JsonProperty(value = "id")
		private Long usageByVotes;

		/**
		 * @return the usageByItem
		 */
		@JsonIgnore
		public Long getUsageByItem() {
			return usageByItem;
		}

		/**
		 * @param usageByItem
		 *            the usageByItem to set
		 */
		public void setUsageByItem(Long usageByItem) {
			this.usageByItem = usageByItem;
		}

		/**
		 * @return the totalUsageBySocialNetwork
		 */
		@JsonIgnore
		public Long getTotalUsageBySocialNetwork() {
			return totalUsageBySocialNetwork;
		}

		/**
		 * @param totalUsageBySocialNetwork
		 *            the totalUsageBySocialNetwork to set
		 */
		public void setTotalUsageBySocialNetwork(Long totalUsageBySocialNetwork) {
			this.totalUsageBySocialNetwork = totalUsageBySocialNetwork;
		}

		/**
		 * @return the totalHits
		 */
		@JsonIgnore
		public Long getTotalHits() {
			return totalHits;
		}

		/**
		 * @param totalHits
		 *            the totalHits to set
		 */
		public void setTotalHits(Long totalHits) {
			this.totalHits = totalHits;
		}

		/**
		 * @return the usageByVotes
		 */
		@JsonIgnore
		public Long getUsageByVotes() {
			return usageByVotes;
		}

		/**
		 * @param usageByVotes
		 *            the usageByVotes to set
		 */
		public void setUsageByVotes(Long usageByVotes) {
			this.usageByVotes = usageByVotes;
		}
	}
} 