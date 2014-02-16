/*
 ************************************************************************************
 * Copyright (C) 2001-2013 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.test.json;

import java.io.IOException;

import javax.servlet.ServletException;

import org.encuestame.mvc.controller.jsonp.EmbebedJsonServices;
import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * {@link EmbebedJsonServices} TestCase.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 23, 2013
 */
@Category(DefaultTest.class)
public class EmbeddedJsonServicesTestCases extends AbstractJsonV1MvcUnitBeans {

	/** **/
	private Question question;

	/** **/
	private Poll poll;

	@Before
	public void initJsonService() {
		this.question = createQuestion("Why the sky is blue?", "html");
		this.poll = createDefaultPoll(this.question,
				getSpringSecurityLoggedUserAccount());

	}

	/**
	 * Test Embedded
	 * @throws ServletException
	 * @throws IOException
	 */
	//@Test
	public void testEmbedded() throws ServletException,
			IOException {
		final Long pollId = this.poll.getPollId();
		System.out.println("TEST CASS");
		initService("/api/jsonp/generate/code/poll/embedded", MethodJson.GET);
		setParameter("id", pollId.toString());
		setParameter("callback", "callback");
		setParameter("embedded_type", "IFRAME");
		//final JSONObject response = callJsonService();
	}

	/**
	 * Test type javascript jsonp.
	 * @throws ServletException
	 * @throws IOException
	 */
	//@Test
	public void testTypeJavascriptJSONP() throws ServletException,
			IOException {
		final Long pollId = this.poll.getPollId();
		initService("/api/jsonp/tweetpoll/embedded", MethodJson.GET);
		setParameter("id", pollId.toString());
		setParameter("callback", "callback");
		final JSONObject response = callJsonService();
	}

	/**
	 * Test Poll jsonp
	 * @throws ServletException
	 * @throws IOException
	 */
	// @Test
	public void testPollJSONP() throws ServletException,
			IOException {
		final Long pollId = this.poll.getPollId();
		initService("/api/jsonp/poll.json", MethodJson.GET);
		setParameter("id", pollId.toString());
		setParameter("callback", "callback");
		setParameter("embedded_type", "IFRAME");
		final JSONObject response = callJsonService();
  	}

	/**
	 * Test User json.
	 * @throws ServletException
	 * @throws IOException
	 */
	// @Test
	public void testUserJSONP() throws ServletException, IOException {
		initService("/api/jsonp/user.json", MethodJson.GET);
		setParameter("id", getUsernameLogged());
		setParameter("callback", "callback");
		final JSONObject response = callJsonService();
	}
}
