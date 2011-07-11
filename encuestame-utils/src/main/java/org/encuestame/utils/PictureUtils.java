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
package org.encuestame.utils;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.encuestame.utils.exception.EnMeGenericException;

/**
 * Picture Utils.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2011
 */
public class PictureUtils {

    /**
     * Gravatar Url.
     */
    public static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    /**
     * Get Gravatar.
     *
     * @param email
     * @param size
     * @return
     */
    public static final String getGravatar(final String email, Integer size) {
        return getUrl(email, size);
    }

    /**
     * Get Gravatar URL
     *
     * @param email
     * @return
     */
    public static String getUrl(final String email, int size) {
        Validate.notNull(email, "email");
        final String hash = MD5Utils.md5Hex(email);
        String params = formatUrlParameters(size);
        final StringBuffer str = new StringBuffer(PictureUtils.GRAVATAR_URL);
        str.append(hash);
        str.append(params);
        return str.toString();
    }

    /**
     * Build gravatar params.
     *
     * @return
     */
    private static String formatUrlParameters(final int size) {
        final List<String> params = new ArrayList<String>();
        params.add("s=" + size);
        params.add("r=" + Rating.GENERAL_AUDIENCES.getCode());
        if (params.isEmpty()) {
            return "";
        } else {
            return "?" + StringUtils.join(params.iterator(), "&");
        }
    }

    /**
     * Download the generated gravatar image.
     *
     * @param email
     * @return
     * @throws EnMeGenericException
     */
    public static byte[] downloadGravatar(final String email, int size)
            throws EnMeGenericException {
        InputStream stream = null;
        try {
            URL url = new URL(getUrl(email, size));
            stream = url.openStream();
            return IOUtils.toByteArray(stream);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw new EnMeGenericException(e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * Gravatar Rating.
     *
     * @author Picado, Juan juanATencuestame.org
     * @since Jul 3, 2011
     */
    private enum Rating {
        GENERAL_AUDIENCES("g"), PARENTAL_GUIDANCE_SUGGESTED("pg"), RESTRICTED(
                "r"), XPLICIT("x");
        private String code;

        private Rating(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * Generate random RGB color.
     * @return
     */
    public static Color generateRGBRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    /**
     *
     * @return
     */
    public static String getRandomHexColor() {
        String hex1 = getRandomHex();
        String hex2 = getRandomHex();
        String hex3 = getRandomHex();
        String hex4 = getRandomHex();
        String hex5 = getRandomHex();
        String hex6 = getRandomHex();
        String color = "#" + hex1 + hex2 + hex3 + hex4 + hex5 + hex6;
        return color;
    }

    /**
     * get random hex
     *
     * @return int
     */
    private static String getRandomHex() {
        Random random = new Random();
        String[] hex = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F" };
        Integer randomNum = random.nextInt(hex.length);
        String sHex = hex[randomNum];
        return sHex;
    }

}
