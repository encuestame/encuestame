/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */
package org.encuestame.mvc.test.config;

import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.HandlerAdapter;

import javax.servlet.ServletRequestEvent;

/**
 * Abstract mvc unit beans.
 * @author Picado, Juan juanATencuestame.org
 * @since April 10, 2011
 */
@ContextConfiguration(locations = {
        "classpath:spring-test/encuestame-test-controller-context.xml",
        "classpath:spring-test/encuestame-test-upload-context.xml",
        "classpath:spring-test/encuestame-param-test-context.xml"})
public abstract class AbstractMultipartMvcUnitBeans extends AbstractSpringSecurityContext {

    protected MockMultipartHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected HandlerAdapter handlerAdapter;

    /**
     *
     */
    @Before
    public void setUp() {
       //mock servlet context.
       final MockServletContext servletContext = new MockServletContext("");
       request = new MockMultipartHttpServletRequest(servletContext);
       //to simulate request context listener.
       final RequestContextListener requestContextListener = new RequestContextListener();
       final ServletRequestEvent requestEvent = new ServletRequestEvent(servletContext, request);
             requestContextListener.requestInitialized(requestEvent);
       request.setRemoteAddr("80.43.23.54");
       response = new MockHttpServletResponse();
       handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
    }
}
