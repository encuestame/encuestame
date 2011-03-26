package org.encuestame.core.security.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 *
 * HTML filtering utility for protecting against XSS (Cross Site Scripting).
 *
 * This code is licensed under a Creative Commons Attribution-ShareAlike 2.5
 * License http://creativecommons.org/licenses/by-sa/2.5/
 *
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http://code.iamcal.com/php/lib_filter/
 *
 * The trickiest part of the translation was handling the differences in regex
 * handling between PHP and Java. These resources were helpful in the process:
 *
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http://us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http://www.regular-expressions.info/modifiers.html
 *
 * A note on naming conventions: instance variables are prefixed with a "v";
 * global constants are in all caps.
 *
 * Sample use: String input = ... String clean = new HTMLInputFilter().filter(
 * input );
 *
 * If you find bugs or have suggestions on improvement (especially regarding
 * perfomance), please contact me at the email below. The latest version of this
 * source can be found at
 *
 * http://josephoconnell.com/java/xss-html-filter/
 *
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @author Picado, Juan juanATencuestame.org (refactor class)
 * @version 1.0
 */
public class HTMLInputFilter {


    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * flag determining whether to try to make tags when presented with
     * "unbalanced" angle brackets (e.g. "<b text </b>" becomes
     * "<b> text </b>"). If set to false, unbalanced angle brackets will be html
     * escaped.
     */
    public static final boolean ALWAYS_MAKE_TAGS = true;

    /**
     * flag determing whether comments are allowed in input String.
     */
    public static final boolean STRIP_COMMENTS = true;

    /** regex flag union representing /si modifiers in php **/
    protected static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE
            | Pattern.DOTALL;

    /**
     * set of allowed html elements, along with allowed attributes for each
     * element
     **/
    private Map<String, List<String>> vAllowed;

    /** counts of open tags for each (allowable) html element **/
    private Map<String, Integer> vTagCounts;

    /** html elements which must always be self-closing (e.g. "<img />") **/
    private String[] vSelfClosingTags;

    /**
     * html elements which must always have separate opening and closing tags
     * (e.g. "<b></b>")
     **/
    private String[] vNeedClosingTags;

    /** attributes which should be checked for valid protocols **/
    private String[] vProtocolAtts;

    /** allowed protocols **/
    private String[] vAllowedProtocols;

    /**
     * tags which should be removed if they contain no content (e.g. "<b></b>"
     * or "<b />")
     **/
    private String[] vRemoveBlanks;

    /** entities allowed within html markup **/
    private String[] vAllowedEntities;

    private boolean vDebug;

    /**
     * Constructor.
     */
    public HTMLInputFilter() {
        this(false);
    }

    /**
     * Constructor
     * @param debug enable debug.
     */
    public HTMLInputFilter(boolean debug) {
        vDebug = debug;

        vAllowed = new HashMap<String, List<String>>();
        vTagCounts = new HashMap<String, Integer>();

        ArrayList<String> a_atts = new ArrayList<String>();
        a_atts.add("href");
        a_atts.add("target");
        vAllowed.put("a", a_atts);

        ArrayList<String> img_atts = new ArrayList<String>();
        img_atts.add("src");
        img_atts.add("width");
        img_atts.add("height");
        img_atts.add("alt");
        vAllowed.put("img", img_atts);

        ArrayList<String> no_atts = new ArrayList<String>();
        vAllowed.put("b", no_atts);
        vAllowed.put("strong", no_atts);
        vAllowed.put("i", no_atts);
        vAllowed.put("em", no_atts);

        vSelfClosingTags = new String[] { "img" };
        vNeedClosingTags = new String[] { "a", "b", "strong", "i", "em" };
        vAllowedProtocols = new String[] { "http", "mailto" }; // no ftp.
        vProtocolAtts = new String[] { "src", "href" };
        vRemoveBlanks = new String[] { "a", "b", "strong", "i", "em" };
        vAllowedEntities = new String[] { "amp", "gt", "lt", "quot" };
    }

    protected void reset() {
        vTagCounts = new HashMap<String, Integer>();
    }

    protected void debug(String msg) {
        if (vDebug){
            log.debug(msg);
        }
    }

    // ---------------------------------------------------------------
    // my versions of some PHP library functions

    public static String chr(int decimal) {
        return String.valueOf((char) decimal);
    }

    public static String htmlSpecialChars(String string) {
        string = string.replaceAll("&", "&amp;");
        string = string.replaceAll("\"", "&quot;");
        string = string.replaceAll("<", "&lt;");
        string = string.replaceAll(">", "&gt;");
        return string;
    }

    // ---------------------------------------------------------------

    /**
     * given a user submitted input String, filter out any invalid or restricted
     * html.
     *
     * @param input
     *            text (i.e. submitted by a user) than may contain html
     * @return "clean" version of input, with only valid, whitelisted html
     *         elements allowed
     */
    public synchronized String filter(String input) {
        reset();
        String inputString = input;

        debug("************************************************");
        debug("              INPUT: " + input);

        inputString = escapeComments(inputString);
        debug("     escapeComments: " + inputString);

        inputString = balanceHTML(inputString);
        debug("        balanceHTML: " + inputString);

        inputString = checkTags(inputString);
        debug("          checkTags: " + inputString);

        inputString = processRemoveBlanks(inputString);
        debug("processRemoveBlanks: " + inputString);

        inputString = validateEntities(inputString);
        debug("    validateEntites: " + inputString);

        debug("************************************************\n\n");
        return inputString;
    }

    protected String escapeComments(String string) {
        final Pattern pattern = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(string);
        StringBuffer buf = new StringBuffer();
        if (matcher.find()) {
            String match = matcher.group(1); // (.*?)
            matcher.appendReplacement(buf, "<!--" + htmlSpecialChars(match) + "-->");
        }
        matcher.appendTail(buf);

        return buf.toString();
    }

    protected String balanceHTML(String s) {
        if (ALWAYS_MAKE_TAGS) {
            //
            // try and form html
            //
            s = regexReplace("^>", "", s);
            s = regexReplace("<([^>]*?)(?=<|$)", "<$1>", s);
            s = regexReplace("(^|>)([^<]*?)(?=>)", "$1<$2", s);

        } else {
            //
            // escape stray brackets
            //
            s = regexReplace("<([^>]*?)(?=<|$)", "&lt;$1", s);
            s = regexReplace("(^|>)([^<]*?)(?=>)", "$1$2&gt;<", s);

            //
            // the last regexp causes '<>' entities to appear
            // (we need to do a lookahead assertion so that the last bracket can
            // be used in the next pass of the regexp)
            //
            s = s.replaceAll("<>", "");
        }

        return s;
    }

    protected String checkTags(String s) {
        Pattern p = Pattern.compile("<(.*?)>", Pattern.DOTALL);
        Matcher m = p.matcher(s);

        StringBuffer buf = new StringBuffer();
        while (m.find()) {
            String replaceStr = m.group(1);
            replaceStr = processTag(replaceStr);
            m.appendReplacement(buf, replaceStr);
        }
        m.appendTail(buf);

        s = buf.toString();

        // these get tallied in processTag
        // (remember to reset before subsequent calls to filter method)
        for (String key : vTagCounts.keySet()) {
            for (int ii = 0; ii < vTagCounts.get(key); ii++) {
                s += "</" + key + ">";
            }
        }

        return s;
    }

    protected String processRemoveBlanks(String s) {
        for (String tag : vRemoveBlanks) {
            s = regexReplace("<" + tag + "(\\s[^>]*)?></" + tag + ">", "", s);
            s = regexReplace("<" + tag + "(\\s[^>]*)?/>", "", s);
        }

        return s;
    }

    protected String regexReplace(String regex_pattern, String replacement,
            String s) {
        Pattern p = Pattern.compile(regex_pattern);
        Matcher m = p.matcher(s);
        return m.replaceAll(replacement);
    }

    protected String processTag(String s) {
        // ending tags
        Pattern pattern = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String name = matcher.group(1).toLowerCase();
            if (vAllowed.containsKey(name)) {
                if (!inArray(name, vSelfClosingTags)) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) - 1);
                        return "</" + name + ">";
                    }
                }
            }
        }

        // starting tags
        pattern = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
        matcher = pattern.matcher(s);
        if (matcher.find()) {
            String name = matcher.group(1).toLowerCase();
            String body = matcher.group(2);
            String ending = matcher.group(3);

            // debug( "in a starting tag, name='" + name + "'; body='" + body +
            // "'; ending='" + ending + "'" );
            if (vAllowed.containsKey(name)) {
                String params = "";

                Pattern p2 = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2",
                        REGEX_FLAGS_SI);
                Pattern p3 = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)",
                        REGEX_FLAGS_SI);
                Matcher m2 = p2.matcher(body);
                Matcher m3 = p3.matcher(body);
                List<String> paramNames = new ArrayList<String>();
                List<String> paramValues = new ArrayList<String>();
                while (m2.find()) {
                    paramNames.add(m2.group(1)); // ([a-z0-9]+)
                    paramValues.add(m2.group(3)); // (.*?)
                }
                while (m3.find()) {
                    paramNames.add(m3.group(1)); // ([a-z0-9]+)
                    paramValues.add(m3.group(3)); // ([^\"\\s']+)
                }

                String paramName, paramValue;
                for (int ii = 0; ii < paramNames.size(); ii++) {
                    paramName = paramNames.get(ii).toLowerCase();
                    paramValue = paramValues.get(ii);

                    // debug( "paramName='" + paramName + "'" );
                    // debug( "paramValue='" + paramValue + "'" );
                    // debug( "allowed? " + vAllowed.get( name ).contains(
                    // paramName ) );

                    if (vAllowed.get(name).contains(paramName)) {
                        if (inArray(paramName, vProtocolAtts)) {
                            paramValue = processParamProtocol(paramValue);
                        }
                        params += " " + paramName + "=\"" + paramValue + "\"";
                    }
                }

                if (inArray(name, vSelfClosingTags)) {
                    ending = " /";
                }

                if (inArray(name, vNeedClosingTags)) {
                    ending = "";
                }

                if (ending == null || ending.length() < 1) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) + 1);
                    } else {
                        vTagCounts.put(name, 1);
                    }
                } else {
                    ending = " /";
                }
                return "<" + name + params + ending + ">";
            } else {
                return "";
            }
        }

        // comments
        pattern = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
        matcher = pattern.matcher(s);
        if (matcher.find()) {
            String comment = matcher.group();
            if (STRIP_COMMENTS) {
                return "";
            } else {
                return "<" + comment + ">";
            }
        }

        return "";
    }

    protected String processParamProtocol(String string) {
        string = decodeEntities(string);
        Pattern p = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
        Matcher m = p.matcher(string);
        if (m.find()) {
            String protocol = m.group(1);
            if (!inArray(protocol, vAllowedProtocols)) {
                // bad protocol, turn into local anchor link instead
                string = "#" + string.substring(protocol.length() + 1, string.length());
                if (string.startsWith("#//"))
                    string = "#" + string.substring(3, string.length());
            }
        }

        return string;
    }

    protected String decodeEntities(String string) {
        StringBuffer buf = new StringBuffer();

        Pattern pattern = Pattern.compile("&#(\\d+);?");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String match = matcher.group(1);
            int decimal = Integer.decode(match).intValue();
            matcher.appendReplacement(buf, chr(decimal));
        }
        matcher.appendTail(buf);
        string = buf.toString();

        buf = new StringBuffer();
        pattern = Pattern.compile("&#x([0-9a-f]+);?");
        matcher = pattern.matcher(string);
        while (matcher.find()) {
            String match = matcher.group(1);
            int decimal = Integer.decode(match).intValue();
            matcher.appendReplacement(buf, chr(decimal));
        }
        matcher.appendTail(buf);
        string = buf.toString();

        buf = new StringBuffer();
        pattern = Pattern.compile("%([0-9a-f]{2});?");
        matcher = pattern.matcher(string);
        while (matcher.find()) {
            String match = matcher.group(1);
            int decimal = Integer.decode(match).intValue();
            matcher.appendReplacement(buf, chr(decimal));
        }
        matcher.appendTail(buf);
        string = buf.toString();

        string = validateEntities(string);
        return string;
    }

    protected String validateEntities(String s) {
        // validate entities throughout the string
        Pattern pattern = Pattern.compile("&([^&;]*)(?=(;|&|$))");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String one = matcher.group(1); // ([^&;]*)
            String two = matcher.group(2); // (?=(;|&|$))
            s = checkEntity(one, two);
        }

        // validate quotes outside of tags
        pattern = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
        matcher = pattern.matcher(s);
        StringBuffer buf = new StringBuffer();
        if (matcher.find()) {
            String one = matcher.group(1); // (>|^)
            String two = matcher.group(2); // ([^<]+?)
            String three = matcher.group(3); // (<|$)
            matcher.appendReplacement(buf, one + two.replaceAll("\"", "&quot;")
                    + three);
        }
        matcher.appendTail(buf);

        return s;
    }

    protected String checkEntity(String preamble, String term) {
        if (!term.equals(";")) {
            return "&amp;" + preamble;
        }

        if (isValidEntity(preamble)) {
            return "&" + preamble;
        }

        return "&amp;" + preamble;
    }

    protected boolean isValidEntity(String entity) {
        return inArray(entity, vAllowedEntities);
    }

    private boolean inArray(String s, String[] array) {
        for (String item : array)
            if (item != null && item.equals(s))
                return true;

        return false;
    }
}
