package org.encuestame.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class RestFullUtil {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");

    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

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
     *
     * @param input
     * @return
     */
    private static String normalize(String input) {
        if (input == null || input.length() == 0) return "";
        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]","");
    }

}
