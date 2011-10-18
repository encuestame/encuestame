/*
 * Copyright 2007 Soren Davidsen, Tanesha Networks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.encuestame.utils.captcha;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import org.encuestame.utils.captcha.http.HttpLoader;
import org.encuestame.utils.captcha.http.SimpleHttpLoader;


public class ReCaptchaImpl implements ReCaptcha {

    public static final String PROPERTY_THEME = "theme";
    public static final String ENCODE = "UTF-8";
    public static final String PROPERTY_TABINDEX = "tabindex";
    public static final String HTTP_SERVER = "http://www.google.com/recaptcha/api";
    public static final String HTTPS_SERVER = "https://www.google.com/recaptcha/api";
    public static final String VERIFY_URL = "http://www.google.com/recaptcha/api/verify";

    private String privateKey;
    private String publicKey;
    private String recaptchaServer = HTTP_SERVER;
    private String verifyUrl = VERIFY_URL;
    private boolean includeNoscript = false;
    private HttpLoader httpLoader = new SimpleHttpLoader();

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public void setRecaptchaServer(String recaptchaServer) {
        this.recaptchaServer = recaptchaServer;
    }
    public void setIncludeNoscript(boolean includeNoscript) {
        this.includeNoscript = includeNoscript;
    }
    public void setVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
    }
    public void setHttpLoader(HttpLoader httpLoader) {
        this.httpLoader  = httpLoader;
    }

    public ReCaptchaResponse checkAnswer(String remoteAddr, String challenge, String response) {


        StringBuffer postParameters = new StringBuffer("privatekey=");
        try {
            postParameters.append("privatekey=");
            postParameters.append(URLEncoder.encode(privateKey, ENCODE));
            postParameters.append("&remoteip=");
            postParameters.append(URLEncoder.encode(remoteAddr, ENCODE));
            postParameters.append("&challenge=");
            postParameters.append(URLEncoder.encode(challenge, ENCODE));
            postParameters.append("&response=");
            postParameters.append(URLEncoder.encode(response, ENCODE));
        } catch (UnsupportedEncodingException e) {
            postParameters = new StringBuffer("");
        }

        //final String postParameters = "privatekey=" + URLEncoder.encode(privateKey,ENCODE) + "&remoteip=" + URLEncoder.encode(remoteAddr,ENCODE) +
        //    "&challenge=" + URLEncoder.encode(challenge,ENCODE) + "&response=" + URLEncoder.encode(response,ENCODE);

        final String message;
        try {
            message = httpLoader.httpPost(verifyUrl, postParameters.toString());

            if (message == null) {
                return new ReCaptchaResponse(false, "recaptcha-not-reachable");
            }
        }
        catch (ReCaptchaException networkProblem) {
            return new ReCaptchaResponse(false, "recaptcha-not-reachable");
        }

        String[] a = message.split("\r?\n");
        if (a.length < 1) {
            return new ReCaptchaResponse(false, "No answer returned from recaptcha: " + message);
        }
        boolean valid = "true".equals(a[0]);
        String errorMessage = null;
        if (!valid) {
            if (a.length > 1)
                errorMessage = a[1];
            else
                errorMessage = "recaptcha4j-missing-error-message";
        }

        return new ReCaptchaResponse(valid, errorMessage);
    }

    public String createRecaptchaHtml(String errorMessage, Properties options) {

        String errorPart = (errorMessage == null ? "" : "&amp;error=" + URLEncoder.encode(errorMessage));

        String message = fetchJSOptions(options);

        message += "<script type=\"text/javascript\" src=\"" + recaptchaServer + "/challenge?k=" + publicKey + errorPart + "\"></script>\r\n";

        if (includeNoscript) {
            String noscript = "<noscript>\r\n" +
                    "	<iframe src=\""+recaptchaServer+"/noscript?k="+publicKey + errorPart + "\" height=\"300\" width=\"500\" frameborder=\"0\"></iframe><br/>\r\n" +
                    "	<textarea name=\"recaptcha_challenge_field\" rows=\"3\" cols=\"40\"></textarea>\r\n" +
                    "	<input type=\"hidden\" name=\"recaptcha_response_field\" value=\"manual_challenge\"/>\r\n" +
                    "</noscript>";
            message += noscript;
        }

        return message;
    }

    public String createRecaptchaHtml(String errorMessage, String theme, Integer tabindex) {

        Properties options = new Properties();

        if (theme != null) {
            options.setProperty(PROPERTY_THEME, theme);
        }
        if (tabindex != null) {
            options.setProperty(PROPERTY_TABINDEX, String.valueOf(tabindex));
        }

        return createRecaptchaHtml(errorMessage, options);
    }

    /**
     * Produces javascript array with the RecaptchaOptions encoded.
     *
     * @param properties
     * @return
     */
    private String fetchJSOptions(Properties properties) {

        if (properties == null || properties.size() == 0) {
            return "";
        }

        String jsOptions =
            "<script type=\"text/javascript\">\r\n" +
            "var RecaptchaOptions = {";

        for (Enumeration e = properties.keys(); e.hasMoreElements(); ) {
            String property = (String) e.nextElement();

            jsOptions += property + ":'" + properties.getProperty(property)+"'";

            if (e.hasMoreElements()) {
                jsOptions += ",";
            }

        }

        jsOptions += "};\r\n</script>\r\n";

        return jsOptions;
    }
}
