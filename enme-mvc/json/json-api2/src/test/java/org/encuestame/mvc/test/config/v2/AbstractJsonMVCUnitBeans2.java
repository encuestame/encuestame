package org.encuestame.mvc.test.config.v2;

import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
* Abstract class to test JSON service based on @RequestBody 
* @author Picado, Juan juanATencuestame.org
* @since Feb 15, 2014 8:12:32 PM
*/

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@Scope("singleton")
@ContextConfiguration(locations = {"classpath:spring-test/encuestame-test-json-controller-context.xml",
    "classpath:spring-test/encuestame-test-rss-context.xml",
    "classpath:spring-test/encuestame-test-upload-context.xml"})
@WebAppConfiguration
public class AbstractJsonMVCUnitBeans2 extends AbstractSpringSecurityContext {

	 	protected MockMvc mockMvc;

//	    @Autowired
//	    private TodoService todoServiceMock;
	 	

	    @Resource
	    private FilterChainProxy springSecurityFilterChain;

	    @Autowired
	    private WebApplicationContext webApplicationContext;

	    @Before
	    public void setUp() {
	        //We have to reset our mock between tests because the mock objects
	        //are managed by the Spring container. If we would not reset them,
	        //stubbing and verified behavior would "leak" from one test to another.
	        //Mockito.reset(todoServiceMock);
	        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
	    }

		/**
		 * @return the webApplicationContext
		 */
		public WebApplicationContext getWebApplicationContext() {
			return webApplicationContext;
		}
}
