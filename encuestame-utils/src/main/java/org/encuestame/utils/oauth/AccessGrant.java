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
package org.encuestame.utils.oauth;

import java.io.Serializable;

/**
 * OAuth2 access token.
 */
@SuppressWarnings("serial")
public final class AccessGrant implements Serializable {

    private final String accessToken;

    private final String refreshToken;

    public AccessGrant(String accessToken) {
        this(accessToken, null);
    }

    public AccessGrant(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * The access token value.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * The refresh token that can be used to renew the access token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}