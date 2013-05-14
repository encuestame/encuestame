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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * RESTFul utilities.
 * @author Picado, Juan juanATencuestame.org
 */
public class RestFullUtil {

    /**
     * No latin pattern.
     */
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");

    /**
     * Whitespace pattern.
     */
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    /**
     * Apply the correct format on a hastag.
     */
    public static String formatHasgTag(String hashTag) {
        if (hashTag != null) {
            hashTag = hashTag.toLowerCase();
        } else {
            hashTag = "";
        }
        return hashTag;
    }

    /**
     * Slugify a string
     * @param input
     * @return slug string
     * @throws UnsupportedEncodingException
     */
    public static String slugify(final String input) {
        if (input == null || input.length() == 0) {
            return "";
        }
        String toReturn = RestFullUtil.normalize(input);
        toReturn = toReturn.replaceAll("[^a-zA-Z]", " ");
        toReturn = toReturn.replaceAll("\\s+", " ").trim().toLowerCase();
        toReturn = toReturn.replace(" ", "-");
        toReturn = RestFullUtil.encodeUTF8(toReturn);
        return toReturn;
    }

    /**
     * Encode a string with UTF-8.
     * @param toReturn
     * @return
     */
    public static String encodeUTF8(final String toReturn) {
        try {
            return URLEncoder.encode(toReturn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return toReturn;
        }
    }

     /**
      * Convert the String input to a slug.
      */
      public static String toSlug(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
      }

    /**
     * Normalize a string
     * @param input
     * @return
     */
    private static String normalize(final String input) {
        if (input == null || input.length() == 0) return "";
        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]","");
    }

}
