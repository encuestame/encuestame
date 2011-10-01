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
package org.encuestame.persistence.utils;

import static org.encuestame.persistence.utils.EncodingUtils.hexEncode;

/**
 * A StringKeyGenerator that uses a SecureRandom to generate hex-encoded String keys.
 * Defaults to 8 byte keys produced by the SHA1PRNG algorithm developed by the Sun Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 24, 2010 4:11:18 PM
 * @version $Id:$
 */
public final class SecureRandomStringKeyGenerator implements StringKeyGenerator {

    private final SecureRandomKeyGenerator keyGenerator;

    /**
     * Creates a secure random string key generator with the defaults.
     */
    public SecureRandomStringKeyGenerator() {
        keyGenerator = new SecureRandomKeyGenerator();
    }

    /**
     * Creates a fully customized string key generator.
     */
    public SecureRandomStringKeyGenerator(String algorithm, String provider, int keyLength) {
        keyGenerator = new SecureRandomKeyGenerator(algorithm, provider, keyLength);
    }

    public String generateKey() {
        return hexEncode(keyGenerator.generateKey());
    }

}