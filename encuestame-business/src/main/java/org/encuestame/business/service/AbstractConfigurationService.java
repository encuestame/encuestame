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
package org.encuestame.business.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abtract Configuration Service.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 6, 2010 7:42:32 PM
 * @version $Id:$
 */
public abstract class AbstractConfigurationService extends AbstractDataSource {

    private String domainUrl;

    private String name;

    private String encoding;

    private String apiKeygoogle;

    private String proxyPass;

    private @Value("${application.picture.path}") String dataGlobalPath;

    /**
     * App Consumer Key.
     */
    public @Value("${twitter.oauth.consumerKey}") String consumerKey;

    /**
     * App consumer secret.
     */
    public @Value("${twitter.oauth.consumerSecret}") String consumerSecret;

    /**
     * @return the domainUrl
     */
    public String getDomainUrl() {
        return domainUrl;
    }

    /**
     * @param domainUrl
     *            the domainUrl to set
     */
    public void setDomainUrl(final String domainUrl) {
        this.domainUrl = domainUrl;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding
     *            the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the apiKeygoogle
     */
    public String getApiKeygoogle() {
        return apiKeygoogle;
    }

    /**
     * @param apiKeygoogle
     *            the apiKeygoogle to set
     */
    public void setApiKeygoogle(String apiKeygoogle) {
        this.apiKeygoogle = apiKeygoogle;
    }

    /**
     * @return the proxyPass
     */
    public String getProxyPass() {
        return proxyPass;
    }

    /**
     * @param proxyPass the proxyPass to set
     */
    public void setProxyPass(String proxyPass) {
        this.proxyPass = proxyPass;
    }

    /**
     * @return the dataGlobalPath
     */
    public String getDataGlobalPath() {
        if (!StringUtils.endsWith(dataGlobalPath, "/")) {
            dataGlobalPath += "/";
        }
        return dataGlobalPath;
    }

    /**
     * @param dataGlobalPath the dataGlobalPath to set
     */
    public void setDataGlobalPath(String dataGlobalPath) {
        this.dataGlobalPath = dataGlobalPath;
    }
}
