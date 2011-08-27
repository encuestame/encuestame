package org.encuestame.utils.captcha.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import org.encuestame.utils.captcha.ReCaptchaException;


public class SimpleHttpLoader implements HttpLoader {

    public String httpGet(String urlS) {
        InputStream in = null;
        URLConnection connection = null;
        try {
            URL url = new URL(urlS);
            connection = url.openConnection();

            // jdk 1.4 workaround
            setJdk15Timeouts(connection);

            in = connection.getInputStream();

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int rc = in.read(buf);
                if (rc <= 0)
                    break;
                else
                    bout.write(buf, 0, rc);
            }

            // return the generated javascript.
            return bout.toString();
        }
        catch (IOException e) {
            throw new ReCaptchaException("Cannot load URL: " + e.getMessage(), e);
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (Exception e) {
                // swallow.
            }
        }
    }

    public String httpPost(String urlS, String postdata) {
        InputStream in = null;
        URLConnection connection = null;
        try {
            URL url = new URL(urlS);
            connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            setJdk15Timeouts(connection);

            OutputStream out = connection.getOutputStream();
            out.write(postdata.getBytes());
            out.flush();

            in = connection.getInputStream();

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int rc = in.read(buf);
                if (rc <= 0)
                    break;
                else
                    bout.write(buf, 0, rc);
            }

            out.close();
            in.close();

            // return the generated javascript.
            return bout.toString();
        }
        catch (IOException e) {
            throw new ReCaptchaException("Cannot load URL: " + e.getMessage(), e);
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (Exception e) {
                // swallow.
            }
        }
    }

    /**
     * Timeouts are new from JDK1.5, handle it generic for JDK1.4 compatibility.
     * @param connection
     */
    private void setJdk15Timeouts(URLConnection connection) {
        try {
            Method readTimeoutMethod = connection.getClass().getMethod("setReadTimeout", new Class[]{ Integer.class });
            Method connectTimeoutMethod = connection.getClass().getMethod("setConnectTimeout", new Class[]{ Integer.class });
            if (readTimeoutMethod != null) {
                readTimeoutMethod.invoke(connection, new Object[]{ new Integer(10000) });
                //System.out.println("Set timeout.");
            }
            if (connectTimeoutMethod != null) {
                connectTimeoutMethod.invoke(connection, new Object[]{ new Integer(10000) });
                //System.out.println("Set timeout.");
            }
        }
        catch (Exception e) {
            // swallow silently
        }
    }


}
