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
package org.encuestame.utils.security;

import java.io.Serializable;

/**
 * Unit Twitter Account Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 26, 2010 5:15:12 PM
 * @version Id:
 */
public class UnitTwitterAccountBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 4250537795415299836L;

    private Long accountId;

    private String account;

    private String type;

    private String secret;

    private String key;

    private String pin;

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(final String account) {
        this.account = account;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the accountId
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     *            the accountId to set
     */
    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the pinl
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pinl the pinl to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }
}
