package org.encuestame.mvc.test.config.v2;

import org.encuestame.rest.api.test.json.v2.WebAppContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author Petri Kainulainen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class APIv2ControllerConfig{

	@Resource
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

  
}
