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
package org.encuestame.oauth;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * Abstract layer suppor for REST OAuth calls.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public abstract class AbstractOAuthSupport {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * {@link RestTemplate}.
     */
    private final RestTemplate restTemplate;

    /**
     * {@link UriTemplate}.
     */
    protected UriTemplate authorizeUrlTemplate;


    /**
     * Define customizable list of {@link HttpMessageConverter}.
     * @param converters
     */
    public AbstractOAuthSupport(final List<HttpMessageConverter<?>> converters) {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setMessageConverters(converters);
        log.debug("OAuth Converters Size "+this.restTemplate.getMessageConverters().size());
        if (log.isDebugEnabled()) {
            for (HttpMessageConverter<?> httpMessageConverter : this.restTemplate.getMessageConverters()) {
                log.debug("--- OAuth Converters "+httpMessageConverter);
                for (MediaType medidaType : httpMessageConverter.getSupportedMediaTypes()) {
                    log.debug("------ Converter Media Type "+medidaType.toString());
                }
            }
        }
    }

    /**
     * Default Constructor.
     */
    public AbstractOAuthSupport() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * This constructor allows define own {@link ClientHttpRequestFactory}.
     * @param requestFactory {@link ClientHttpRequestFactory}.
     */
    public AbstractOAuthSupport(final ClientHttpRequestFactory requestFactory){
        this.restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * @return the authorizeUrlTemplate
     */
    public UriTemplate getAuthorizeUrlTemplate() {
        return authorizeUrlTemplate;
    }

    /**
     * @param authorizeUrlTemplate the authorizeUrlTemplate to set
     */
    public void setAuthorizeUrlTemplate(UriTemplate authorizeUrlTemplate) {
        this.authorizeUrlTemplate = authorizeUrlTemplate;
    }

    /**
     * @return the restTemplate
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}