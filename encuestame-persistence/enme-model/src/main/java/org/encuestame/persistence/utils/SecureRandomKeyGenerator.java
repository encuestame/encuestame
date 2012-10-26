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

import java.security.NoSuchAlgorithmException;

import java.security.NoSuchProviderException;
import java.security.SecureRandom;
/**
 * A KeyGenerator that uses SecureRandom to generate byte array-based keys.
 * Defaults to 8 byte keys produced by the SHA1PRNG algorithm developed by the Sun Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 24, 2010 4:02:38 PM
 * @version $Id:$
 */
public final class SecureRandomKeyGenerator implements KeyGenerator {

    private final SecureRandom random;

    private final int keyLength;

    /**
     * Creates a secure random key generator using the defaults.
     */
    public SecureRandomKeyGenerator() {
        this(DEFAULT_ALGORITHM, DEFAULT_PROVIDER, DEFAULT_KEY_LENGTH);
    }

    /**
     * Creates a secure random key generator with a custom key length.
     */
    public SecureRandomKeyGenerator(int keyLength) {
        this(DEFAULT_ALGORITHM, DEFAULT_PROVIDER, keyLength);
    }

    /**
     * Creates a secure random key generator that is fully customized.
     */
    public SecureRandomKeyGenerator(String algorithm, String provider, int keyLength) {
        this.random = createSecureRandom(algorithm, provider, keyLength);
        this.keyLength = keyLength;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public byte[] generateKey() {
        byte[] bytes = new byte[keyLength];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Create Secure Random.
     * @param algorithm
     * @param provider
     * @param keyLength
     * @return
     */
    private SecureRandom createSecureRandom(String algorithm, String provider, int keyLength) {
        try {
            SecureRandom random = SecureRandom.getInstance(algorithm, provider);
            random.setSeed(random.generateSeed(keyLength));
            return random;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Not a supported SecureRandom key generation algorithm", e);
        } catch (NoSuchProviderException e) {
            throw new IllegalArgumentException("Not a supported SecureRandom key provider", e);
        }
    }

    private static final String DEFAULT_ALGORITHM = "SHA1PRNG";

    private static final String DEFAULT_PROVIDER = "SUN";

    private static final int DEFAULT_KEY_LENGTH = 8;

}