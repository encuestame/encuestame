package org.encuestame.util;

import java.io.*;

/**
 * Created by jpicado on 25/06/16.
 */
public class FileUtils {

    /**
     * Copying One File to Another.
     * @param in
     * @param dst
     * @throws IOException
     */
    public static void copy(InputStream in, File dst) throws IOException {
        //InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}
