package org.encuestame.utils;

/**
 * Created by dmorales on 23/01/16.
 */
public class EnumerationUtils {/**
     *A common method for all enums since they can't have another base class
     * @param c enum type. All enums must be all caps.
     * @param string string case insensitive
     * @param <T> Enum type
     * @return corresponding enum, or null
     * http://stackoverflow.com/questions/604424/convert-a-string-to-an-enum-in-java
     */
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                string = string.trim().toUpperCase();
                return Enum.valueOf(c, string);
            } catch (IllegalArgumentException ex) {
//                System.out.println("enum-->"+string);
//                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
