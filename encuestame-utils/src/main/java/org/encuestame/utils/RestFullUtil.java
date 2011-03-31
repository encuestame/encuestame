package org.encuestame.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class RestFullUtil {


    /**
     *
     */
    public static String formatHasgTag(String hashTag){
        if (hashTag != null) {
            hashTag = hashTag.toLowerCase();
        } else {
            hashTag = "";
        }
        return hashTag;
    }


    /**
     *
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String slugify(final String input) {
        if (input == null || input.length() == 0) return "";
        String toReturn = RestFullUtil.normalize(input);
        toReturn = toReturn.replace(" ", "-");
        toReturn = toReturn.toLowerCase();
        try {
            toReturn = URLEncoder.encode(toReturn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            toReturn = input;
            e.printStackTrace();
        }
        return toReturn;
    }

    /**
     *
     * @param input
     * @return
     */
    private static String normalize(String input) {
        if (input == null || input.length() == 0) return "";
        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]","");
    }

}
